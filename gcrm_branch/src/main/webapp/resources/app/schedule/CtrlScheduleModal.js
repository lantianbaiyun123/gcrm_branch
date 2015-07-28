define([
  'app',
  '../_services/http/Schedules',
  '../_directives/ytBusySpin',
  '../_directives/periodLabel',
  '../_directives/ytCheckboxIndeterminate',
  '../_filters/MonthShortFilter',
  '../_filters/MonthMiniFilter',
  '../_filters/RotationTypeFilter',
  '../_filters/DatePeriodFilter'
], function ( app ) {
  app.registerController('CtrlScheduleModal', [
    '$scope',
    '$modalInstance',
    '$log',
    '$filter',
    '$q',
    '$timeout',
    'Schedules',
    'opts',
  function ($scope, $modalInstance, $log, $filter, $q, $timeout, Schedules, opts) {
    var today = new Date(),
        onceNumber = 62,
        colorArr = {
          reserved: 'success',
          confirmed: 'danger',
          locked: 'muted',
          expect: 'info'
        };


    $scope.today = {
      date: new Date(),
      dateString: getFullDateString(this.date).dateString,
      yesterday: (function () {
        var today = new Date();
        yesterdayDate = new Date(today.setDate(today.getDate() - 1));
        return getFullDateString(yesterdayDate).dateString;
      }())
    };

    $scope.current = {};
    $scope.showConfirm = opts.showConfirm || false;
    $scope.modalDatas = opts.modalDatas;
    $scope.readOnly = opts.readOnly;

    $scope.activeYear = today.getFullYear();
    $scope.monthList = [];
    $scope.activeMonthPosition =  0;
    $scope.busySpinShow = false;
    $scope.thDays = [];
    $scope.dayState = {};

    $scope.scheduleList = {};
    $scope.positionInfo = opts.positionInfo;
    $scope.processInfo = opts.processInfo;

    $scope.currentOccupyDatesTail = [];
    $scope.currentInsertDatesTail = [];
    $scope.currentAllowInsert = [];

    $scope.submitData = {};
    $scope.legendShow = false;

    $scope.suspendMsgs = [];
    $scope.warningMsgs = [];
    $scope.submitMsgs = [];
    $scope.currentContent = {};

    doInit();

    $scope.selectMonth = function (month, monthIndex) {
      var currentLength = $scope.thDays.length,
          targetLength = Math.ceil((month.lastDay - today)/1000/60/60/24) + 1,
          loadNumber = targetLength - currentLength;
      $scope.busySpinShow = true;
      activeMonthLi(monthIndex);
      if( currentLength >= targetLength) {
        $scope.activeMonthPosition = $scope.monthList[monthIndex].startPosition;
        $timeout(function () {
          //使每次点击，activeposition都是有有变化的
          $scope.activeMonthPosition = -1;
        });
        $scope.busySpinShow = false;
      } else {
        var promise = loadSchedule(loadNumber);
        promise.then(function () {
          $scope.activeMonthPosition = $scope.monthList[monthIndex].startPosition;
          $timeout(function () {
            $scope.activeMonthPosition = -1;
          });
        });
        promise['finally'](function () {
          $scope.busySpinShow = false;
        });
      }
    };

    $scope.changeMonthCheckbox = function (e, monthIndex, adContentKey) {
      // e.preventDefault();
      e.stopPropagation();
      var indexFrom = $scope.monthList[monthIndex].startIndex,
          indexTo = $scope.monthList[monthIndex].endIndex;
      var isChecked = $scope.scheduleList[adContentKey].monthCheckbox[monthIndex].checked;
      $scope.scheduleList[adContentKey].monthCheckbox[monthIndex].indeterminate = false;

      for(var i=indexFrom; i <= indexTo; i++) {
        $scope.scheduleList[adContentKey].days[i].checked = !isChecked;

        if( $scope.scheduleList[adContentKey].days[i].checkbox &&
            $scope.scheduleList[adContentKey].days[i].checked ^ $scope.scheduleList[adContentKey].days[i].originalChecked ) {
          $scope.scheduleList[adContentKey].days[i].changed = 'changed';
        } else {
          $scope.scheduleList[adContentKey].days[i].changed = '';
        }
      }

      $scope.scheduleList[adContentKey].checked = true;
    };

    $scope.scrollSetMonth = function (scrollPosition) {
      var length = $scope.monthList.length;
      for (var i=0; i < length; i++) {
        if ( scrollPosition >= $scope.monthList[i].startPosition &&
             scrollPosition < $scope.monthList[i].endPosition ) {
          activeMonthLi(i);
          break;
        }
      }
    };

    //滚动到右侧端了
    $scope.loadMore = function () {
      if ( $scope.busySpinShow ) {
        return false;
      }
      var loadNumber = onceNumber,
          currentLength = $scope.thDays.length,
          maxLength = Math.ceil(($scope.monthList[$scope.monthList.length-1].lastDay - today)/1000/60/60/24) + 1;
      if ( currentLength + loadNumber >= maxLength ) {
        loadNumber = maxLength - currentLength;
      }
      if ( loadNumber>0 ) {
        var promise = loadSchedule(loadNumber);
        promise.then(function () {});
        promise['finally'](function () {});
        $scope.$apply();
      }
    };

    $scope.reloadSchedule = function () {
      var thDaysLength = $scope.thDays.length,
          loadNumber = thDaysLength;

      $scope.busySpinShow = true;
      $scope.scheduleList = {};
      for (var i = $scope.thDays.length - 1; i >= 0; i--) {
        $scope.thDays[i].alreadyCount = 0;
      }
      var promise = loadSchedule(loadNumber, true);
      promise.then(function () {});
      promise['finally'](function () {
        $scope.busySpinShow = false;
      });
    };

    $scope.ownerClicked = function (adContentKey) {
      for ( var key in $scope.scheduleList ) {
        if ( key !== adContentKey ) {
          $scope.scheduleList[key].selected = false;
        } else {
          $scope.scheduleList[key].selected = true;
        }
      }
    };

    var preClickIndex;//remember which clicked
    var preChecked;
    var preAdContentKey;
    //点击了日期checkbox，并考虑是否hold shift-key
    $scope.dayClicked = function ( index, adContentKey, shift ) {
      if ( shift &&
           preClickIndex >= 0 &&
           preClickIndex !== index &&
           preAdContentKey === adContentKey ) {
        var from = preClickIndex,
            to = index;
        //reverse
        if ( preClickIndex > index ) {
          from = index;
          to = preClickIndex;
        }
        for (var i = from; i < to; i++) {
          if ( $scope.scheduleList[adContentKey].days[i].checkbox ) {
            $scope.scheduleList[adContentKey].days[i].checked = preChecked;
            isChanged(adContentKey, i);
          }
        }
      }
      if ( index >= 0 ) {
        preClickIndex = index;
        preAdContentKey = adContentKey;
        preChecked = $scope.scheduleList[adContentKey].days[index].checked;
        isChanged(adContentKey, index);

        //标记广告内容的排期在被动过
        $scope.scheduleList[adContentKey].checked = true;
      }

      var indexRange = getMonthIndexRange($scope.scheduleList[adContentKey].days[index].dateString);
      updateMonthCheckbox(adContentKey, indexRange);
    };

    $scope.nextAlways = opts.nextAlways;
    $scope.changedArr = [];
    function isChanged (adContentKey, index) {
      var keyIndex = adContentKey + '-' + index;
      if ( $scope.scheduleList[adContentKey].days[index].checkbox &&
           $scope.scheduleList[adContentKey].days[index].checked ^ $scope.scheduleList[adContentKey].days[index].originalChecked ) {
        $scope.scheduleList[adContentKey].days[index].changed = 'changed';
        $scope.changedArr.push(keyIndex);
      } else {
        $scope.scheduleList[adContentKey].days[index].changed = '';
        for (var i = $scope.changedArr.length - 1; i >= 0; i--) {
          if ( $scope.changedArr[i] === keyIndex ) {
            $scope.changedArr.splice(i,1);
          }
        }
      }
    }

    $scope.confirm = function () {
      $scope.busySpinShow = true;
      var promise = submitSchedule();
      promise.then(function () {
        $modalInstance.close('goNext');
      });
      promise['finally'](function () {
        $scope.busySpinShow = false;
      });
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.next = function () {
      validatedNext.emptyMsg();
      $scope.busySpinShow = true;
      var promise = validateSchedule();
      promise.then(function (msgData) {
        if ( msgData ) {
          validatedNext[msgData.state](msgData);
        }
      });
      promise['finally'](function () {
        $scope.busySpinShow = false;
      });
    };

    $scope.nextOk = function () {
      $scope.showConfirm = true;
      $scope.popoverConfirmShow = false;
    };

    $scope.nextCancel = function () {
      $scope.popoverConfirmShow = false;
    };

    $scope.prev = function () {
      $scope.showConfirm = false;
    };

    $scope.closeAlert = function (index) {
      $scope.suspendMsgs.splice(index, 1);
    };

    $scope.showLegend = function () {
      $scope.legendShow = !$scope.legendShow;
    };

    function doInit () {
      setMonthList();
      $scope.busySpinShow = true;
      var promise = loadSchedule(onceNumber);

      promise.then(function () {});
      promise['finally'](function () {
        $scope.busySpinShow = false;
      });
    }

    function activeMonthLi (monthIndex) {
      for ( var i = $scope.monthList.length - 1; i >= 0; i-- ) {
        $scope.monthList[i].active = false;
      }
      $scope.monthList[monthIndex].active = true;
      $scope.activeYear = $scope.monthList[monthIndex].year;
      if(!$scope.$$phase) {
        $scope.$apply();
      }
    }

    function setMonthList() {
      var currentMonth = today.getMonth() + 1,
          currentYear = today.getFullYear(),
          endPosition = 0;
          endIndex = -1;
      for ( var i = 0 ; i < 13; i++ ) {
        var month = currentMonth + i,
            year = currentYear,
            nextMonth = month + 1,
            nextMonthYear = currentYear,
            startPosition = endPosition,
            startIndex = endIndex + 1;
        if ( month > 12 ) {
          month = month - 12;
          year = year + 1;
        }
        if ( nextMonth > 12 ) {
          nextMonth = nextMonth - 12;
          nextMonthYear = nextMonthYear + 1;
        }

        var monthNum = month < 10 ? '0' + month : month;
        var nextMonthNum = nextMonth < 10 ? '0' + nextMonth : nextMonth;

        var timeString = nextMonthYear + '-' + nextMonthNum + '-01 00:00:00';

        var nextMonthFirstDay = NewDate(timeString);
        var dateSpan = Math.ceil((nextMonthFirstDay - today)/1000/60/60/24);
        endPosition = dateSpan * 42;
        endIndex = dateSpan - 1;
        var lastDay = nextMonthFirstDay;
        lastDay.setHours(lastDay.getHours() - 24 );

        $scope.monthList.push({
          year : year,
          num: month,
          monthString: year + '-' + monthNum,
          lastDay: lastDay,
          startIndex: startIndex,
          endIndex: endIndex,
          startPosition : startPosition,
          endPosition : endPosition
        });
      }

      $scope.monthList[0].active = true;
    }

    function loadSchedule (number, reload) {
      var deferred = $q.defer(),
          thDaysLength = $scope.thDays.length;
      var queryStartDate;
      if ( $scope.thDays.length && !reload ) {
        queryStartDate = $scope.thDays[$scope.thDays.length - 1].dateString;
      } else {
        var yesterday = $scope.today.date;
        yesterday.setDate(yesterday.getDate() - 1);
        queryStartDate = getFullDateString(yesterday).dateString;
        // queryStartDate = $scope.today.dateString;
      }
      if(!reload) {
        getDays(number);
      }
      Schedules.getScheduleInfo({
        adContentId: $scope.positionInfo.adContentId,
        positionId: $scope.positionInfo.positionId,
        // startDate: $scope.today.dateString,
        startDate: queryStartDate,
        days: number
      }).success(function (response) {
        if ( response.code === 200 &&
            response.data &&
            response.data.allDates ) {
          var allDates = response.data.allDates;

          updateDays(allDates);
          if ( response.data.scheduleList && response.data.scheduleList.length ) {
            for (var i = response.data.scheduleList.length - 1; i >= 0; i--) {
              scheduleAdContent.adContent = response.data.scheduleList[i];
              scheduleAdContent.adContentKey = response.data.scheduleList[i].id || 'current';
              scheduleAdContent.checkExist();
              scheduleAdContent.expendDays(number);
              scheduleAdContent.updateCheckbox();
              scheduleAdContent.updateOccupy(response.data.occupyStartIndex, response.data.occupyEndIndex);
              // locked和corfirm的occupyDates包含insertDates，不需重复update
              if ( response.data.scheduleList[i].state !== 'confirmed' &&
                   response.data.scheduleList[i].state !== 'locked' ) {
                scheduleAdContent.updateInsert(response.data.insertStartIndex, response.data.insertEndIndex);
              }
              if ( scheduleAdContent.adContentKey === 'current' ) {
                $scope.currentAllowInsert = scheduleAdContent.adContent.allowInsertDates;
              }
              scheduleAdContent.initMonthCheckbox();
            }
          } else {
            //没有返回scheduleList，但是还是要添加checkbox
            expendScheduleListEmpty(number);

            //如果无排期,不显示排期单
            $scope.noSchedule = true;
          }
          $timeout(function (argument) {
              deferred.resolve();
          },0);
        } else {
          expendScheduleListEmpty(number);
          $timeout(function (argument) {
              deferred.resolve();
          },0);
        }
      });
      return deferred.promise;
    }

    //从今天,或开始取，取n天
    function getDays ( number ) {
      var length = $scope.thDays.length,
          date = new Date();
      if( length ) {
        date = new Date($scope.thDays[length-1].dateString);
        date.setHours(date.getHours()+24);
      }
      for (var i = 0; i < number; i++) {
        $scope.thDays.push(getFullDateString(date));
        date.setHours(date.getHours()+24);
      }
      if (!$scope.$$phase) {
        $scope.$apply();
      }
    }

    function getFullDateString (date) {
      date = date || new Date();
      var result = {};
      var year = date.getFullYear(),
          month = date.getMonth() + 1,
          day = date.getDate();

      result.dayYear = year;
      result.dayMonth = month;
      result.dayNo = day;
      result.alreadyCount = 0;
      result.checkCount = 0;
      result.monthText = month;
      if( result.dayNo === 1 ) {
          result.dayNo = $filter('translate')($filter('MonthMiniFilter')(month));
      }

      month = month < 10 ? '0' + month : month;
      day = day < 10 ? '0' + day : day;
      result.dateString = year + '-' + month + '-' + day;
      return result;
    }

    function updateDays (allDates) {
      for ( var dateId in allDates ) {
        var datetime = new Date(allDates[dateId].date);
        tdIndex = Math.ceil((datetime - today)/1000/60/60/24);
        if ( tdIndex < 0 ) {
          continue;//时间已经过去了
        } else if ( $scope.thDays[tdIndex] ){
          $scope.thDays[tdIndex].dateId = dateId;
          $scope.thDays[tdIndex].isInsertDate = allDates[dateId].insertDate;
          $scope.thDays[tdIndex].total = allDates[dateId].totalAmount;

          $scope.dayState[dateId] = {
            thDaysIndex: tdIndex,
            isInsertDate: allDates[dateId].insertDate
          };
        }
      }
    }

    function expendScheduleListEmpty (number) {
      for(var adContentKey in $scope.scheduleList ) {
        var originalLength = $scope.scheduleList[adContentKey].days.length;
        for(var i = 0; i < number; i++) {
          $scope.scheduleList[adContentKey].days.push({
            checkbox: false,
            dateString: $scope.thDays[originalLength + i].dateString
          });
        }
      }
    }

    var scheduleAdContent = {
      adContent: {},
      adContentKey: '',
      checkExist: function () {
        if (!$scope.scheduleList[this.adContentKey]) {
          $scope.scheduleList[this.adContentKey] = {
            adContentId: this.adContent.adContentId,
            billingModelId: this.adContent.billingModelId,
            positionId: this.adContent.positionId,
            state: this.adContent.state,
            adContentNumber: this.adContent.adContentNumber,
            adContentName: this.adContent.adContentName,
            advertiser: this.adContent.advertiser,
            operator: this.adContent.operator,
            monthCheckbox: [],
            ngClass: this.adContentKey === 'current' ? 'current selected' : this.adContent.state,
            /**
             *    day = {
             *        ngClass: 状态类(单元格底色)
             *        isInsert: 是否为插单
             *        checkbox: 是否显示checkbox
             *        checked: 是否勾选
             *        disabled: 是否可选
             *    }
             */
            days: []
          };
          if(this.adContent.id) {
            $scope.scheduleList[this.adContentKey].scheduleId = this.adContent.id;
          } else {
            $scope.scheduleList[this.adContentKey].selected = true;
            $scope.scheduleList[this.adContentKey].checked = true;
            $scope.current.advertiser = this.adContent.advertiser;
            $scope.current.operator = this.adContent.operator;
          }
        }

        for(var i=0; i < 13; i++) {
          $scope.scheduleList[this.adContentKey].monthCheckbox.push({
            checked: false,
            indeterminate: false
          });
        }
      },
      expendDays: function (number) {
        var originalLength = $scope.scheduleList[this.adContentKey] ? $scope.scheduleList[this.adContentKey].days.length : 0;
        for(var i = 0; i < number; i++) {
          var day = {};
          day.dateString = $scope.thDays[originalLength + i].dateString;
          if ( $scope.thDays[originalLength + i].isInsertDate ||
               //如果该广告内容排期已经确认或锁定，即不可继续修改排期，关闭所有checkbox
               $scope.scheduleList[this.adContentKey].state === 'confirmed' ||
               $scope.scheduleList[this.adContentKey].state === 'locked' ||
               $scope.readOnly ) {
            day.checkbox = false;
          } else if( $scope.thDays[originalLength + i].dateId ) {
              //初始化checkbox为显示
            day.checkbox = true;
          }
          if ( $scope.readOnly ) {
            day.disabled = true;
          }
          $scope.scheduleList[this.adContentKey].days.push(day);
        }
      },
      updateCheckbox: function () {
        var adContent = this.adContent,
            adContentKey = this.adContentKey,
            allowInsertDates = this.adContent.allowInsertDates;
        if(allowInsertDates && allowInsertDates.length) {
          for (var i = allowInsertDates.length - 1; i >= 0; i--) {
            if ( $scope.dayState[allowInsertDates[i]] ) {
              var tdIndex = $scope.dayState[allowInsertDates[i]].thDaysIndex;
              if ( $scope.scheduleList[adContentKey] ) {
                $scope.scheduleList[adContentKey].days[tdIndex].checkbox = true;
              }
            }
          }
        }
      },
      updateOccupy: function (startIndex, endIndex) {
        if ( this.adContent.occupyDates &&
             this.adContent.occupyDates.length ) {
            //1、startIndex至endIndex为渲染部分，endIndex之后为尾巴
            //2、startIndex = -1，全部不渲染，
            //3、endIndex = -1，全部为尾巴
            //4、endIndex = length - 1，无尾巴
          var renderDates = [];
          if ( this.adContentKey === 'current' ) {
            if ( startIndex > -1 && endIndex >= startIndex ) {
              renderDates = this.adContent.occupyDates.slice(startIndex, endIndex + 1);
            }
            $scope.currentOccupyDatesTail = this.adContent.occupyDates.slice(endIndex + 1);
          } else {
            renderDates = this.adContent.occupyDates;
          }
          this.updateTds(renderDates);
        }
      },
      updateInsert: function (startIndex, endIndex) {
        if ( this.adContent.insertDates &&
             this.adContent.insertDates.length ) {
          //1、startIndex至endIndex为渲染部分，endIndex之后为尾巴
          //2、startIndex = -1，全部不渲染，
          //3、endIndex = -1，全部为尾巴
          //4、endIndex = length - 1，无尾巴
          var renderDates = [];
          if ( this.adContentKey === 'current' ) {
              if ( startIndex > -1 && endIndex >= startIndex ) {
                renderDates = this.adContent.insertDates.slice(startIndex, endIndex + 1);
              }
              $scope.currentInsertDatesTail = this.adContent.insertDates.slice(endIndex + 1);
          } else {
            renderDates = this.adContent.insertDates;
          }
          this.updateTds(renderDates, true);
        }
      },
      updateTds: function (dates, isInsert) {
        var adContent = this.adContent,
            adContentKey = this.adContentKey;
            // dateArray = dates.split(',');
        if ( dates.length ) {
          for (var i = dates.length - 1; i >= 0; i--) {
            var dayState = $scope.dayState[dates[i]];
            if (!dayState) {
              continue;//不存在该状态
            }
            var tdIndex = dayState.thDaysIndex;
            if ( $scope.scheduleList[adContentKey] ) {
              // 设置非当前广告内容的checkbox，只要是投放日期范围内checkbox出现
              if ( !isInsert && adContentKey !== 'current' ) {
                $scope.scheduleList[adContentKey].days[tdIndex].checkbox = true;
              }

              if ( $scope.scheduleList[adContentKey].days[tdIndex].checkbox ) {
                //是否勾选
                $scope.scheduleList[adContentKey].days[tdIndex].checked = true;
                $scope.scheduleList[adContentKey].days[tdIndex].originalChecked = true;

                //对confirm与locked进行勾选计数
                if ( !isInsert &&
                     ( $scope.scheduleList[this.adContentKey].state === 'confirmed' ||
                       $scope.scheduleList[this.adContentKey].state === 'locked' ) ) {
                  $scope.thDays[tdIndex].alreadyCount++;
                }

                //非当前内容的单元格背景色
                if ( adContentKey !== 'current' ) {
                  $scope.scheduleList[adContentKey].days[tdIndex].ngClass = colorArr[adContent.state];
                }

              }

              if ( adContentKey === 'current' ) {
                $scope.scheduleList[adContentKey].days[tdIndex].ngClass = colorArr['expect'];
              }

              //disabled确认或锁定的checkbox
              if ( adContent.state === 'confirmed' ||
                   adContent.state === 'locked' ||
                   $scope.readOnly ) {
                $scope.scheduleList[adContentKey].days[tdIndex].disabled = true;
              }

              if( isInsert ){
                $scope.scheduleList[adContentKey].days[tdIndex].isInsert = true;
              }
            }
          }
        }
      },
      initMonthCheckbox: function () {
        for ( var monthIndex = 0; monthIndex < 13; monthIndex++ ) {
          var indexRange = {
              monthIndex: monthIndex,
              start: $scope.monthList[monthIndex].startIndex,
              end: $scope.monthList[monthIndex].endIndex
          };

          updateMonthCheckbox(this.adContentKey, indexRange);
        }
      }
    };

    function updateMonthCheckbox (adContentKey, indexRange) {
      var empty = true,
          allChecked = true,
          allClear = true;

      $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].disabled = false;

      for( var i = indexRange.start; i <= indexRange.end; i++ ){
        if ( $scope.scheduleList[adContentKey].days[i] && $scope.scheduleList[adContentKey].days[i].checkbox ) {
          empty = false;
          if ( $scope.scheduleList[adContentKey].days[i].checked ) {
            allClear = false;
          } else if( $scope.scheduleList[adContentKey].days[i].checkbox ) {
            allChecked = false;
          }
        }

        if ( $scope.scheduleList[adContentKey].state === 'confirmed' ||
             $scope.scheduleList[adContentKey].state === 'locked' ) {
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].disabled = true;
        }
      }

      if ( empty ) {
        $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].checked = false;
        $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].indeterminate = false;
        $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].disabled = true;
      } else {
        if ( allChecked ) {
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].checked = true;
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].indeterminate = false;
        } else if( allClear ) {
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].checked = false;
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].indeterminate = false;
        } else {
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].checked = false;
          $scope.scheduleList[adContentKey].monthCheckbox[indexRange.monthIndex].indeterminate = true;
        }
      }
    }

    function getMonthIndexRange (dateString) {
      var monthString = dateString.substr(0, 7),
          length = $scope.monthList.length,
          monthIndex,
          startIndex,
          endIndex;
      for ( var i = 0; i < length; i++ ) {
        if ( $scope.monthList[i].monthString === monthString ) {
          monthIndex = i;
          startIndex = $scope.monthList[i].startIndex;
          endIndex = $scope.monthList[i].endIndex;
          break;
        }
      }

      return {
        monthIndex: monthIndex,
        start: startIndex,
        end: endIndex
      };
    }

    function packScheduleData () {
      for ( var j = $scope.thDays.length - 1; j >= 0; j-- ) {
        $scope.thDays[j].checkCount = 0;
      }

      var scheduleData = {
        processId: $scope.processInfo && $scope.processInfo.processId,
        activityId: $scope.processInfo && $scope.processInfo.activityId,
        adContentId: $scope.positionInfo.adContentId,
        positionId: $scope.positionInfo.positionId,
        // billingModelId: $scope.positionInfo.billingModelId,
        advertiser: $scope.positionInfo.advertiser,
        startDate: $scope.today.yesterday,
        days: $scope.thDays.length,
        endDate: getEndDate(),
        scheduleDates: [],
        conflictInfos: []
      };
      for( var adContentKey in $scope.scheduleList ) {
        if ( $scope.scheduleList[adContentKey].state === 'reserved' ||
             adContentKey === 'current' ) {
          var scheduleOne = {
            adContentId:  $scope.scheduleList[adContentKey].adContentId,
            // billingModelId:  $scope.scheduleList[adContentKey].billingModelId,
            positionId:  $scope.scheduleList[adContentKey].positionId,
            occupyDates: [],
            insertDates: [],
            allowInsertDates: []
          };
          if ( adContentKey !== 'current') {
            //初始为“广告内容没编辑过”，在下面的遍历再判断是否编辑过
            $scope.scheduleList[adContentKey].checked = false;
          }
          for (var i = $scope.scheduleList[adContentKey].days.length - 1; i >= 0; i--) {
            //判断该广告内容排期是否被编辑过
            if ( !$scope.scheduleList[adContentKey].checked &&
                $scope.scheduleList[adContentKey].days[i].checked ^ $scope.scheduleList[adContentKey].days[i].originalChecked ) {
                $scope.scheduleList[adContentKey].checked = true;
            }

            if ( $scope.scheduleList[adContentKey].days[i].checkbox &&
                 $scope.scheduleList[adContentKey].days[i].checked ) {
              if ($scope.scheduleList[adContentKey].days[i].isInsert) {
                scheduleOne.insertDates.push($scope.thDays[i].dateId);
              } else {
                scheduleOne.occupyDates.push($scope.thDays[i].dateId);
              }

              $scope.thDays[i].checkCount++;
            }
          }

          if ( adContentKey !== 'current' ) {
            scheduleOne.id = adContentKey;
          } else {
            scheduleOne.occupyDates = scheduleOne.occupyDates.concat($scope.currentOccupyDatesTail);
            scheduleOne.insertDates = scheduleOne.insertDates.concat($scope.currentInsertDatesTail);
            scheduleOne.allowInsertDates = $scope.currentAllowInsert;
            scheduleOne.advertiser = $scope.scheduleList[adContentKey].advertiser;
          }

          if ( $scope.scheduleList[adContentKey].checked ||
               adContentKey === 'current' ) {
            scheduleData.scheduleDates.push(scheduleOne);
          }
        }
      }

      //打包存在冲突的日期对象
      for (var k = $scope.thDays.length - 1; k >= 0; k--) {
        if ( $scope.thDays[k].checkCount + $scope.thDays[k].alreadyCount > $scope.thDays[k].total ) {
          var confilctOne = {
            date: $scope.thDays[k].dateString,
            contentIds: []
          };
          for ( adContentKey in $scope.scheduleList ) {
            if ( $scope.scheduleList[adContentKey].days[k].checked ) {
              confilctOne.contentIds.push($scope.scheduleList[adContentKey].adContentId);
            }
          }
          scheduleData.conflictInfos.push(confilctOne);
        }
      }

      return scheduleData;
    }

    function getEndDate () {
      var endDate = $scope.thDays[$scope.thDays.length - 1].dateString;
      for (var i = $scope.thDays.length - 1; i >= 0; i--) {
        if ( $scope.thDays[i].dateId ) {
          endDate = $scope.thDays[i].dateString;
          break;
        }
      }
      return endDate;
    }

    function validateSchedule () {
      var deferred = $q.defer();
      $scope.submitData = packScheduleData();
      Schedules.validateSchedule($scope.submitData).success(function (response) {
        if( response.code === 200 ){
          deferred.resolve(response.data);
        } else {
          deferred.resolve();
        }
      });
      return deferred.promise;
    }

      //校验后的几种情况
    var validatedNext = {
      emptyMsg: function () {
        $scope.suspendMsgs = [];
        $scope.submitMsgs = [];
        $scope.warningMsgs = [];
      },
      prepareValidateMsg: function (validateMsg) {
        var msg = {};
        if ( validateMsg && validateMsg.length ) {
          var length = validateMsg.length;
          for ( var i = 0 ; i < length; i++ ) {
            if ( !msg[validateMsg[i].type] ) {
              msg[validateMsg[i].type] = [];
            }

          //ng-repeat不允许数组值不唯一，将数组转成对象，并加入isCurrent属性表是是否为当前内容广告主
          if( validateMsg[i].conflictAdvertiser &&
              validateMsg[i].conflictAdvertiser.length ) {
              validateMsg[i].conflictAdvertisers = [];
            for ( var j = validateMsg[i].conflictAdvertiser.length - 1; j >= 0; j-- ) {
              var advertiser = {
                name: validateMsg[i].conflictAdvertiser[j]
              };
              if ( j === validateMsg[i].curIndex ) {
                advertiser.isCurrent = true;
              }
              validateMsg[i].conflictAdvertisers.push(advertiser);
            }
          }

          if ( validateMsg[i].type === 'overdue') {
            validateMsg[i].advertiser = $scope.current.advertiser;
            validateMsg[i].operator = $scope.current.operator;
          }

          msg[validateMsg[i].type].push(validateMsg[i]);
          }
        }

        return msg;
      },
      warning: function (msgData) {
        $scope.warningMsgs = msgData.validateMsg;
        $scope.submitMsgs = msgData.submitMsg;
        if ( !$scope.$$phase ) {
          $scope.$apply();
        }
        this.prependSubmitData(msgData.submitMsg);
        $scope.popoverConfirmShow = true;
      },
      suspend: function (msgData) {
        //clear the suspend msg
        $scope.suspendMsgs = [];
        // $scope.suspendMsgs[0] = {};
        if ( msgData.validateMsg && msgData.validateMsg.length ) {
          var msg = this.prepareValidateMsg(msgData.validateMsg);
          $scope.suspendMsgs[0] = msg;
          $scope.currentContent.currentAdvertiser = msgData.currentAdvertiser;
        }
      },
      success: function (msgData) {
        $scope.submitMsgs = msgData.submitMsg;
        this.prependSubmitData(msgData.submitMsg);
        $scope.showConfirm = true;
      },
      prependSubmitData: function (submitMsgs) {
        for (var i = submitMsgs.length - 1; i >= 0; i--) {
          if ( (submitMsgs[i].oldOccupyIds && submitMsgs[i].oldOccupyIds.length) ||
               (submitMsgs[i].oldInsertIds && submitMsgs[i].oldInsertIds.length) ) {
            var scheduleDates = $scope.submitData.scheduleDates;
            for (var j = scheduleDates.length - 1; j >= 0; j--) {
              if ( scheduleDates[j].adContentId === submitMsgs[i].adContentId ) {
                // scheduleDates[j].occupyDates = submitMsgs[i].oldOccupyIds.concat(scheduleDates[j].occupyDates);
                scheduleDates[j].oldOccupyIds = submitMsgs[i].oldOccupyIds;
                // scheduleDates[j].insertDates = submitMsgs[i].oldInsertIds.concat(scheduleDates[j].insertDates);
                scheduleDates[j].oldInsertIds = submitMsgs[i].oldInsertIds;
              }
            }
          }
        }
      }
    };

    function submitSchedule () {
      var deferred = $q.defer();
      Schedules.submitSchedule($scope.submitData).success(function (response) {
        if ( response.code === 200 ){
          deferred.resolve(response.data);
        }
      });
      return deferred.promise;
    }

    //解决ie new date()带参数bug问题
    function NewDate(timeString) {
      var dates = timeString.split(' '),ymd = dates[0].split('-'),
          time = dates[1].split(':');
      var date = new Date();
      date.setUTCFullYear(ymd[0], ymd[1] - 1, ymd[2]);
      date.setUTCHours(Number(time[0]), Number(time[1]), 0, 0);
      return date;
    }

  }]);
});