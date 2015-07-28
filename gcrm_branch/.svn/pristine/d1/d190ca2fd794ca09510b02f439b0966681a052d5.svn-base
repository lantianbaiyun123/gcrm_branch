define([
  'app',
  '../_filters/CalendarTdTitleFilter'
], function (app) {
  app.registerFactory('DateStatus', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      get: function (paramObj) {
        var url = APP_CONTEXT + 'occupation/queryDateOccupation';
        var data = angular.toJson(paramObj);
        // return $http.get('/data/queryDateOccupation.json');
        return $http.post(url, data);
      },
      getMaxDate: function (paramObj) {
        var url = APP_CONTEXT + 'occupation/maxDate/' + paramObj.id;
        return $http.get(url);
      }
    };
  }]);

  app.registerDirective('ytCalendar', [
    '$timeout',
    '$rootScope',
    '$parse',
    'dateFilter',
    'DateStatus',
    '$position',
    '$compile',
    '$document',
    // 'ytCalendarMax',
  function ($timeout, $rootScope, $parse, dateFilter, DateStatus, $position, $compile, $document) {
    var remoteMaxDate;

    return {
      require: '?^ngModel',
      restrict: 'A',
      scope: {
        positionId: '=',
        billingModelId: '=',
        contentId: '='
      },
      priority: 1, //http://stackoverflow.com/questions/21084088/why-ngmodels-render-is-not-called-when-the-model-changes-in-angularjs
      link: function (scope, element, attrs, ngModel) {
        var today = new Date(); //only today
        var current; //keep tracing
        current = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        //设置最大最小天, 默认为一年
        var minDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
        var maxDate = new Date(minDate.getFullYear() + 1, minDate.getMonth(), minDate.getDate());
        var $popup;

        if (attrs.min) {
          scope.$watch($parse(attrs.min), function (value) {
            if (value) {
              var temp = new Date(value);
              if (temp > minDate) {
                minDate = new Date(temp.getFullYear(), temp.getMonth(), temp.getDate());
              }
            }
          });
        }

        if (attrs.max) {
          scope.$watch($parse(attrs.min), function (value) {
            if (value) {
              var temp = new Date(value);
              if (temp < maxDate) {
                minDate = new Date(temp.getFullYear(), temp.getMonth(), temp.getDate());
              }
            }
          });
        }

        //是否只能选今天及今天以后的
        // scope.$watch($parse(attrs.futureOnly), function (value) {
        //   if (value) {
        //     if (ngModel.$modelValue && !compareDate(ngModel.$modelValue, today)) {
        //       element.attr('disabled', 'disabled');
        //     }
        //   }
        // });
        var compareDate = function (date1, date2) {
          if (!date1 || !date2) {
            return;
          }
          date1 = resolveDate(date1);
          date2 = resolveDate(date2);
          return date1.getTime() >= date2.getTime();
        };
        //将date转化为当天的零点日期对象，date可能是字符串，可能是日期对象
        //如2014-05-04 ——> new Date('2014/05/04')
        //Tue May 06 2014 11:34:11 GMT+0800 (中国标准时间) -> new Date('2014/05/04')
        var resolveDate = function (date) {
          if (angular.isDate(date)) {
            return new Date(date.getFullYear(), date.getMonth(), date.getDate());
          } else if (angular.isNumber(date)) {
            return new Date(date);
          } else {
            return new Date(date.replace(/-/g, '/'));
          }
        };
        var dateFormat = 'yyyy-MM-dd'; //default dateformat
        var positionId; //get hold of positionId,when triggered
        var scopePopup = scope.$new(); //new a scope for not populating the origin one
        scopePopup.currentDate = current;
        scopePopup.isOpen = false;
        var ifDocumentClickedBind = false;
        var ifElementFocusBind = false;

        //watch model to trigger $render,which format the day for output
        scope.$watch("model", function () {
          ngModel.$render();
        });

        //one date selected
        scopePopup.select = function (date) {
          scopePopup.currentDate = date;
          documentClicked({});
          scopePopup.date = date;
          ngModel.$setViewValue(date);
          ngModel.$render();
        };
        // Outter change,reset the element value
        ngModel.$render = function () {
          var date = ngModel.$viewValue ? dateFilter(ngModel.$viewValue, dateFormat) : '';
          element.val(date);
          // scope.date = ngModel.$modelValue;
        };
        //refresh from outter
        scopePopup.refresh = function () {
          refresh();
        };
        //move month from outter
        scopePopup.move = function (direction) {
          if (direction === -1 &&
            current.getFullYear() === today.getFullYear() &&
            current.getMonth() === today.getMonth()) {
            return;
          }
          if (direction === 1 &&
            current.getFullYear() === maxDate.getFullYear() &&
            current.getMonth() === maxDate.getMonth()) {
            return;
          }
          current.setDate(1);
          current.setMonth(current.getMonth() + direction);
          refresh();
        };
        bootstrapCalendar();
        //bootstrapCalendar with specific positionId,append dom
        function bootstrapCalendar() {
          // popup element used to display calendar
          var popupEl = angular.element('<div yt-datepicker-popup-wrap></div>');
          popupEl.attr({
            'ng-model': 'date'
          });
          $popup = $compile(popupEl)(scopePopup);
          element.after($popup);
        }
        //inner watch isOpen state
        scopePopup.$on('periodChanged', function (event, period, key) {
          if (!ngModel.$viewValue && period.$$hashKey === scope.$parent.period.$$hashKey) {
            current = angular.copy(period[key]);
            $timeout(function () {
              open(true);
            });
          }
        });

        scopePopup.$watch('isOpen', function (value) {
          if (value) {
            // $timeout(function (argument) {
            //   updatePosition();
            // });
            // $timeout(function () {
            // body...
            $document.bind('click', documentClicked);
            // }, 0)
            if (ifElementFocusBind) {
              element.unbind('focus', elementFocused);
            }
            element[0].focus();
            ifDocumentClickedBind = true;
          } else {
            if (ifDocumentClickedBind) {
              $document.unbind('click', documentClicked);
            }
            element.bind('focus', elementFocused);
            ifElementFocusBind = true;
          }
        });

        function open(auto) {
          refresh(!auto).then(function () {
            if (scopePopup.isOpen) {
              return;
            }
            scopePopup.isOpen = true;

            element.attr("disabled", "disabled");
          });
        }
        //when focus
        function elementFocused(e) {
          e.preventDefault();
          e.stopPropagation();
          open();
          // $timeout(function () {
            // refresh(true).then(function () {
            //   if (scopePopup.isOpen) {
            //     return;
            //   }
            //   scopePopup.isOpen = true;
            //   // if (!scopePopup.$$phase) {
            //   //   scopePopup.$apply();
            //   // }

            //   element.attr("disabled", "disabled");
            // });
          // }, 100);
        }
        //when document clicked
        function documentClicked(event) {
          if (scopePopup.isOpen && event.target !== element[0]) {
            // scopePopup.$apply(function () {
              scopePopup.isOpen = false;
            // });
            if (!scopePopup.$$phase && !$rootScope.$$phase) {
              scopePopup.$apply();
            }
            element.removeAttr("disabled");
          }
        }
        //get data from server and refill calendar
        function refresh(isFocusIn) {
          if (isFocusIn) {
            if (!ngModel.$modelValue) {
              current = new Date(minDate);
            } else {
              current = new Date(ngModel.$modelValue);
            }
          }
          var promise = DateStatus.get({
            period: {
              from: dateFilter(getFrom(), dateFormat),
              to: dateFilter(getLastDayOfMonth(), dateFormat)
            },
            positionId: scope.positionId,
            billingModelId: scope.billingModelId,
            oldContentId: scope.contentId
          }).success(function (response) {
            var occupations = [];
            // var rotationTotal;//if rotation
            if (response.code === 200) {
              occupations = resolveOccupations(response.data.occupations);
              scopePopup.isRotation = response.data.rotation;
              scopePopup.billingModelId = response.data.billingModelId;
              scopePopup.totalCount = response.data.totalCount;

              if (response.data.rotation) {
                scopePopup.rotationTotal = response.data.totalCount;
              }
            }

            scopePopup.labels = 'SUNDAY_SHORT,MONDAY_SHORT,TUESDAY_SHORT,WEDNESDAY_SHORT,THURSDAY_SHORT,FRIDAY_SHORT,SATURDAY_SHORT'.split(',');

            var data = getVisibleDates(current);
            //fill status using data
            for (var i = data.length - 1; i >= 0; i--) {
              for (var j = occupations.length - 1; j >= 0; j--) {
                if (occupations[j].status === 'idle') {
                  occupations.splice(j, 1); //剔除闲置的
                } else if (data[i].date && occupations[j].date == data[i].date.getTime()) {
                  // data[i].status = occupations[j].status;
                  data[i].isFull = occupations[j].full;
                  data[i].biddingCount = occupations[j].biddingCount || 0;
                  data[i].occupied = occupations[j].occupied || 0;
                  data[i].remained = occupations[j].remained || 0;
                  if ((scopePopup.billingModelId === 4 || scopePopup.billingModelId === 5) && data[i].isFull) {
                    data[i].disabled = true;
                  }
                  occupations.splice(j, 1);
                }
              }
              if (data[i].date > maxDate || data[i].date < minDate) {
                data[i].disabled = true;
              }
            }
            var splittedData = split(data, 7);
            //在最后添加空白天，如果有～
            if (splittedData[splittedData.length - 1].length !== 7) {
              var length = splittedData[splittedData.length - 1].length;
              for (var k = length; k < 7; k++) {
                splittedData[splittedData.length - 1].push({
                  disabled: true
                });
              }
            }
            scopePopup.rows = splittedData;
            scopePopup.title = current.getFullYear() + '/' + (current.getMonth() + 1);
            // $timeout(function (argument) {
            updatePosition();
            // });
          });

          return promise;
        }
        /// ---- below utils
        //从哪天开始取，今天后（包括今天？）
        function getFrom() {
          var currentYear = current.getFullYear();
          var currentMonth = current.getMonth();
          //如果是同一个月，那么从今天之后开始找
          if (currentYear === today.getFullYear() &&
            currentMonth === today.getMonth()) {
            return today;
          }
          return new Date(currentYear, currentMonth, 1);
        }

        function getLastDayOfMonth() {
          var currentYear = current.getFullYear();
          var currentMonth = current.getMonth();
          if (currentYear === maxDate.getFullYear() &&
            currentMonth === maxDate.getMonth()) {
            return maxDate;
          }
          return new Date(new Date(currentYear, currentMonth + 1, 1) - 86400000);
        }

        function updatePosition() {
          var clientHeight = document.documentElement.clientHeight;
          var clientRectTop = element[0].getBoundingClientRect().top;
          scopePopup.position = $position.position(element);

          if (clientHeight - clientRectTop > 450) {
            scopePopup.position.top = scopePopup.position.top + element.prop('offsetHeight');
          } else {
            scopePopup.position.top = element[0].offsetTop - element[0].offsetHeight - $popup.height();
          }
        }

        function getVisibleDates(date, selected) {
          var year = date.getFullYear(),
            month = date.getMonth(),
            firstDate = new Date(year, month, 1);
          var blankDayNumber = firstDate.getDay() % 7;
          var numDates = new Date(year, month + 1, 0).getDate();

          var days = getDates(firstDate, numDates);
          for (var i = 0; i < numDates; i++) {
            var dt = new Date(days[i]);
            days[i] = makeDate(dt, 'd', (selected && selected.getDate() === dt.getDate() && selected.getMonth() === dt.getMonth() && selected.getFullYear() === dt.getFullYear()), dt.getMonth() !== month);
          }
          //fill {{blankDayNumber}} empty object to days
          for (var j = 0; j < blankDayNumber; j++) {
            days.unshift({
              disabled: true
            });
          }
          return days;
        }

        function makeDate(date, format) {
          return {
            date: date,
            label: dateFilter(date, format)
          };
        }
        // Split array into smaller arrays
        function split(arr, size) {
          var arrays = [];
          while (arr.length > 0) {
            arrays.push(arr.splice(0, size));
          }
          return arrays;
        }

        function getDates(startDate, n) {
          var dates = new Array(n);
          var current = startDate,
            i = 0;
          while (i < n) {
            dates[i++] = new Date(current);
            current.setDate(current.getDate() + 1);
          }
          return dates;
        }
        //切换模式
        scopePopup.toggleMode = function () {
          scopePopup.modePickMonth = !scopePopup.modePickMonth;
          if (scopePopup.modePickMonth) {
            scopePopup.availableMonths = getAvailableMonthList();
          }
        };
        scopePopup.pickMonth = function (item) {
          current.setMonth(item.month - 1);
          current.setFullYear(item.year);
          refresh();
          scopePopup.modePickMonth = !scopePopup.modePickMonth;
        };

        function getAvailableMonthList() {
          var max = new Date(maxDate);
          var min = new Date(minDate);
          var cur = current;
          if (!max || !min || max.getTime() < min.getTime()) {
            return [];
          }
          var retArr = [];
          var tempMonthFirstDay = (new Date(min)).setDate(1);
          while (tempMonthFirstDay <= max.getTime()) {
            var month = min.getMonth() + 1;
            if (month < 10) {
              month = '0' + month;
            }
            var item = {
              month: month,
              year: min.getFullYear()
            };
            if (sameMonth(min, cur)) {
              item.active = true;
            }
            retArr.push(item);

            min.setMonth(min.getMonth() + 1);
            tempMonthFirstDay = (new Date(min)).setDate(1);
          }
          return retArr;
        }

        function sameMonth(a, b) {
          if (a.getMonth() === b.getMonth() &&
            a.getFullYear() === b.getFullYear()) {
            return true;
          }
          return false;
        }

        function resolveOccupations(occupations) {
          var dates = angular.copy(occupations);
          for (var i = dates.length - 1; i >= 0; i--) {
            dates[i].date = fakeLocalTimestamp(dates[i].date);
          }
          return dates;
        }

        function fakeLocalTimestamp(timestamp, offset) {
          var fromOffset = offset || 480;  //默认为+8区偏离分钟数
          var localOffset = - (new Date()).getTimezoneOffset();

          //服务器的日期存的当地时间，比如2013-10-12，实际存的是2013-10-12 00:00:00 GMT+0800的毫秒时间戳, 即2013-10-11 16:00:00 UTC的毫秒时间戳，设为'beijing'，
          //此时间戳，在GMT-7为2013-10-11 09:00:00 GMT-0700
          //那么要在GMT-7体现2013-10-12 00:00:00，则 beijing + 8*60*60*1000 - (-7)*60*60*1000
          return (timestamp + (fromOffset - localOffset) * 60*1000);
        }
      }
    };
  }]);

  app.registerDirective('ytDatepickerPopupWrap', [
    'STATIC_DIR',
  function (STATIC_DIR) {
    return {
      restrict: 'EA',
      replace: true,
      templateUrl: STATIC_DIR + 'app/_directives/calendar.tpl.html',
      link: function (scope, element, attrs) {
        element.bind('click', function (event) {
          event.preventDefault();
          event.stopPropagation();
        });
      }
    };
  }]);
});