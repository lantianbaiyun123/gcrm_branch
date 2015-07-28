/**
 * [上下线管理Ctrl]
 */
define([
  'app',
  '../_directives/ytPositionSelect',
  '../_directives/ytPopoverConfirm',
  '../_directives/ytScrollfix',
  '../_services/http/Publication',
  '../_filters/WeekFilter',
  '../_filters/PositionFilter',
  './OperateTypeFilter',
  './ForcePublishModal'
], function (app) {
  app.registerController('CtrlPublicationMgmt', [
    '$scope',
    '$stateParams',
    '$log',
    '$filter',
    '$q',
    '$timeout',
    'PageSet',
    'Utils',
    'Modal',
    'PublicationConstant',
    'Publication',
    'ForcePublishModal',
  function ($scope, $stateParams, $log, $filter, $q, $timeout, PageSet, Utils, Modal, PublicationConstant, Publication, ForcePublishModal) {
    PageSet.set({activeIndex:4,siteName:'publicationMgmt'});
    var translate = $filter('translate');

    $scope.qTypes = PublicationConstant.queryTypes;
    $scope.positionOpts = { level: 3 };

    //当前查询状态, today, tomorrow, afterTomorrwo, free
    $scope.currentQuery = 'today';

    $scope.qForm = {
      queryType: $scope.qTypes[0]
    };

    $scope.tabset = {
      today: {},
      tomorrow: {},
      afterTomorrow: {}
    };

    $scope.history = {};

    $scope.todoQueryForm = {
      today: { operateType: 'all' },
      tomorrow: { operateType: 'all' },
      afterTomorrow: { operateType: 'all' }
    };

    $scope.pList = [];

    $scope.setOptType = function ( tabKey, index ) {
      var form = $scope.todoQueryForm[tabKey],
          operate = $scope.tabset[tabKey].operate,
          type = $scope.tabset[tabKey].operate[index];
      for (var i = operate.length - 1; i >= 0; i--) {
        operate[i].selected = false;
      }
      type.selected = true;
      form.operateType = type.name;
      $scope.todoQuery(tabKey);
    };

    $scope.setPlatform = function ( tabKey, platformId ) {
      var form = $scope.todoQueryForm[tabKey],
          tab = $scope.tabset[tabKey],
          platformList = tab.platformList,
          platform = platformList[platformId];

        for ( var id in platformList ) {
          platformList[id].selected = false;
        }

        platform.selected =  true;
        tab.siteList = angular.copy(platform.site);
        tab.channelList = [];

        form.siteId = 0;
        form.channelList = [];
        form.platformId = platformId;
        $scope.todoQuery(tabKey);
    };

    $scope.setSite = function ( tabKey, siteId ) {
      var form = $scope.todoQueryForm[tabKey],
          tab = $scope.tabset[tabKey],
          siteList = tab.siteList,
          site = siteList[siteId],
          channel = site.channel;
      for ( var id in siteList ) {
        siteList[id].selected = false;
      }

      site.selected = true;

      //数组形式的channelList
      var channelList = tab.channelList = [];
      for ( var cId in channel ) {
        channelList.push({
          selected: true,
          id: cId,
          name: channel[cId].name
        });
      }

      form.channelList = channelList;
      form.siteId = siteId;
      $scope.todoQuery(tabKey);
    };

    //让ng-repeat不要排序
    $scope.notSorted = function(obj){
      if (!obj) {
        return [];
      }
      return Object.keys(obj);
    };

    $scope.historyQuery = function () {
      $scope.isHistory = true;
      $scope.currentQuery = 'history';
      resetPageNumber();
      getDoneList();
    };

    //查询类型下拉列表选中
    $scope.setQueryType = function (type) {
      $scope.qForm.queryType = type;
    };

    $scope.btnQuery = function () {
      resetPageNumber();
      if ($scope.currentQuery === 'history') {
        $scope.historyQuery();
      } else {
        freeQuery();
      }
    };

    function freeQuery (expand) {
      $scope.isQuery = true;
      $scope.isHistory = false;
      $scope.currentQuery = 'free';
      getList(freeQueryCondition()).then(function () {
        if (expand) {
          expandAll();
        }
      });
    }

    $scope.todoQuery = function ( tabKey ) {
      $scope.isHistory = false;
      $scope.isQuery = false;
      $scope.tabset[tabKey].active = true;
      $scope.currentQuery = tabKey;
      var form = todoQueryCondition(tabKey),
          dateString = $scope.tabset[tabKey].dateString;
      getList(form).then(function () {
        //如果带申请单编号进入，执行一次按申请单编号查询，并切换至“查询列表”
        if ( $stateParams.applyNumber && !$scope.applyNumQueried) {
          applyNumQuery($stateParams.applyNumber);
          $scope.applyNumQueried = true;
        } else if ( tabKey==='today' ) {
          //1秒钟后占全部展开
          $timeout(function () {
            expandAll(dateString);
          }, 1000);
        } else {
          for (var i = $scope.pList.length - 1; i >= 0; i--) {
            $scope.pList[i].dateString = dateString;
          }
        }
      });
    };

    $scope.toggleDetail = function (p) {
      if ( !p.showDetail ) {
        Publication.getDetail({
          publishNumber: p.number,
          operateDate: p.dateString
        }).success(function (response) {
          if ( response.code === 200 ) {
            p.detail = response.data;
            var publishDate = p.detail.publishDate;
            for (var i = publishDate.length - 1; i >= 0; i--) {
              var num = i + 1;
              publishDate[i].indexText = translate('PUBLICATION_PERIOD_INDEX', {index: num});
            }
            p.showDetail = !p.showDetail;
          }
        });
      } else {
        p.showDetail = !p.showDetail;
      }
    };

    $scope.toggleDoneDetail = function (p) {
      if ( !p.showDetail ) {
        Publication.getDoneDetail({
          publishNumber: p.number
        }).success(function (response) {
          if ( response.code === 200 ) {
            p.detail = response.data;
            // var publishDate = p.detail.publishDate;
            // for (var i = publishDate.length - 1; i >= 0; i--) {
            //   var num = i + 1;
            //   publishDate[i].indexText = translate('PUBLICATION_PERIOD_INDEX', {index: num});
            // }
            p.showDetail = !p.showDetail;
          }
        });
      } else {
        p.showDetail = !p.showDetail;
      }
    };

    //分页
    $scope.pager = {
      pageSize: 10,
      pageSizeSlots: [10,20,30,50],
      pageNumber: 1,
      totalCount:0
    };
    //更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      resetPageNumber();
      doCurrentQuery();
    };
    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function ( newValue, oldValue ){
      if ( newValue && newValue !== oldValue ) {
        doCurrentQuery();
      }
    });

    //物料上线
    $scope.materialUpdate = function (number) {
      Publication.materialUpdate({
        number: number
      }).success(function (response) {
        if ( response.code === 200 ) {
          doCurrentQuery();
          updateConditionCount();
          Modal.success({
            content: translate('SUCCESS_PUBLICATION_MATERIAL_ONLINE'),
            timeOut: 2000
          });
        }
      });
    };

    //终止投放
    $scope.terminateMsg = '<p class="text-danger"><strong>' + translate('PUBLICATION_TERMINATE_HINT')  + '</strong></p>';
    $scope.terminate = function (number) {
      Publication.terminate({
        number: number
      }).success(function (response) {
        if ( response.code === 200 ) {
          doCurrentQuery();
          updateConditionCount();
          Modal.success({
            content: translate('SUCCESS_PUBLICATION_TERMINATE'),
            timeOut: 2000
          });
        }
      });
    };

    //上线
    $scope.publish = function (periodId) {
      Publication.publish({
        id: periodId
      }).success(function (response) {
        if ( response.code === 200 ) {
          doCurrentQuery();
          updateConditionCount();
          Modal.success({
            content: translate('SUCCESS_PUBLICATION_ONLINE'),
            timeOut: 2000
          });
        }
      });
    };

    //根据计划结束时间，显示不同的popoverConfirm
    $scope.publishFinishBtnClass = "btn-primary";
    $scope.isEarlyFinish = function (period) {
      var now = new Date();
      //提前下线
      if ( period.planEnd > now.getTime() ) {
        $scope.publishFinishMsg = '<p class="text-danger"><strong>' + translate('PUBLICATION_EARLY_OFFLINE_HINT') + '</strong></p>';
        $scope.publishFinishBtnClass = "btn-warning";
      } else {
        $scope.publishFinishMsg = '';
        $scope.publishFinishBtnClass = "btn-primary";
      }
      return true;
    };
    //下线
    $scope.publishFinish = function (periodId) {
      Publication.publishFinish({
        id: periodId
      }).success(function (response) {
        if ( response.code === 200 ) {
          doCurrentQuery();
          updateConditionCount();
          Modal.success({
            content: translate('SUCCESS_PUBLICATION_OFFLINE'),
            timeOut: 2000
          });
        }
      });
    };

    //强制上线
    $scope.forcePublish = function (periodId) {
      ForcePublishModal.show({
        periodId: periodId
      }, function (response) {
        if ( response.code === 200 ) {
          doCurrentQuery();
          updateConditionCount();
          Modal.success({
            content: translate('SUCCESS_PUBLICATION_FORCE_ONLINE'),
            timeOut: 2000
          });
        }
      });
    };

    //催办物料提交
    $scope.sendReminder = function (publishId) {
      Publication.sendReminder({
        publishId: publishId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };

    // $scope.queryT = function (items, key) {
    //   try {
    //     var conditions = [
    //       'adContentNumber',
    //       'advertisers',
    //       'number',
    //       'positionName',
    //       'scheduleNumber'
    //     ];

    //     var matched = false;
    //     for (var i = conditions.length - 1; i >= 0; i--) {
    //       var field = conditions[i];
    //       matched = item[field] && item[field].indexOf($scope.history[key].queryText || '') !== -1;
    //       if ( matched ) {
    //         return;
    //       }
    //     }
    //     return matched;
    //   } catch (e) {
    //     return true;
    //   }
    // };

    init();

    function init () {
      initCondition();
    }

    //初始化用户筛选器列表
    function initCondition () {
      Publication.getUserConditions().success(function (response) {
        if ( response.code === 200 ) {
          var dates = getDates();
          var translate = $filter('translate');
          for (var tabKey in response.data ) {
            var tab = response.data[tabKey],
                siteAll = {
                  name: translate('ALL'),
                  site: {
                    0: {
                      name: translate('ALL'),
                      selected: true
                    }
                  },
                  selected: true
                };
            //为每个投放平台加入"全部站点"
            for ( var platformId in tab.platformList ) {
              var platform = tab.platformList[platformId];
              // angular.extend(siteAll.site, platform.site);
              //叠加相同站点的total
              for ( var siteId in platform.site ) {
                if ( !siteAll.site[siteId] ) {
                  siteAll.site[siteId] = angular.copy(platform.site[siteId]);
                } else {
                  siteAll.site[siteId].total = siteAll.site[siteId].total + platform.site[siteId].total;
                  if ( siteAll.site[siteId].channel ) {
                    angular.extend(siteAll.site[siteId].channel, platform.site[siteId].channel);
                  } else if ( platform.site[siteId].channel ) {
                    siteAll.site[siteId].channel = angular.copy(platform.site[siteId].channel);
                  }
                }
              }

              platform.site[0] = {
                name: translate('ALL'),
                selected: true
              };
            }
            if ( tab.platformList ) {
              tab.platformList[0] = siteAll;
            }

            //初始化操作类型选中“全部”
            if ( tab.platformList ) {
              tab.operate[0].selected = true;
            }

            tab.dateString = Utils.getDateString(dates[tabKey]);
            var day = dates[tabKey].getDay();
            if ( tabKey === 'today' ) {
              day = 'TODAY';
            } else if ( tabKey === 'afterTomorrow' ) {
              day = 'PUBLICATION_AFTER_TOMORROW';
            } else {
              day = $filter('WeekFilter')(day, 'normal');
            }
            tab.date = tab.dateString.substr(5,5);
            tab.day = $filter('translate')(day);

            //传递today、tomorrow、afterTomorrow这几个tab的初始化数据
            $scope.tabset[tabKey] = angular.copy(tab);
            if (tab.platformList) {
              $scope.tabset[tabKey].siteList = angular.copy(tab.platformList[0].site);
            }
            $scope.tabset[tabKey].channelList = [];
            if ( tabKey === 'today' ) {
              $scope.tabset[tabKey].active = true;
            }
          }

          $scope.initialized = true;
        }
      });
    }

    function updateConditionCount () {
      Publication.getUserConditions().success(function (response) {
        if ( response.code === 200 ) {
          for ( var tabKey in response.data ) {
            var tab = response.data[tabKey],
                tabView = $scope.tabset[tabKey];
            //更新tab页total
            tabView.total = tab.total;

            //更新操作类型count
            var operate = tab.operate,
                operateView = tabView.operate;
            for ( var opt in operate ) {
              tabView.operate[opt].total = operate[opt].total;
            }


            //更新投放平台count
            var platformList = tab.platformList,
                platformListView = tabView.platformList,
                platformAllView = platformListView[0];
            //清空“投放平台-全部”下site的count，即置total=0
            for ( var siteId in platformAllView.site ) {
              platformAllView.site[siteId].total = 0;
            }
            for ( var plat in platformList) {
              platformListView[plat].total = platformList[plat].total;

              //更新平台下每个投放站点count
              var siteList = platformList[plat].site,
                  siteListView = platformListView[plat].site;
              for ( var siteKey in siteList ) {
                siteListView[siteKey].total = siteList[siteKey].total;
                //更新当前选中的platform的siteList
                if ( platformListView[plat].selected ) {
                  tabView.siteList[siteKey].total = siteList[siteKey].total;
                }

                //累加“投放平台-全部”下的site的count
                platformAllView.site[siteKey].total = platformAllView.site[siteKey].total + siteList[siteKey].total;
              }

            }
          }
        }
      });
    }

    //根据当前查询类型进行查询
    function doCurrentQuery () {
      if ( $scope.currentQuery === 'free' ) {
        freeQuery();
      } else if ( $scope.currentQuery === 'history') {
        getDoneList();
      } else {
        $scope.todoQuery($scope.currentQuery);
      }
    }

    //查询待处理申请单列表
    function getList (conditionForm) {
      var deferred = $q.defer();
      Publication.getList(conditionForm).success(function (response) {
        if ( response.code === 200 ) {
          $scope.pList = response.data.content;
          $scope.pager.totalCount = response.data.totalCount;
          $scope.pager.totalPages = response.data.totalPages;
          deferred.resolve();
        }
      });
      return deferred.promise;
    }

    //查询待已处理申请单列表
    function getDoneList () {
      var deferred = $q.defer();
      if ($scope.initialized) {
        Publication.getDoneList(freeQueryCondition()).success(function (response) {
          if ( response.code === 200 ) {
            $scope.history.list = response.data.content;
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
            deferred.resolve();
          }
        });
      }
      return deferred.promise;
    }

    //todoQuery查询条件
    function todoQueryCondition (tabKey) {
      var form =$scope.todoQueryForm[tabKey],
          conditionForm = {};
      conditionForm.pageNumber = $scope.pager.pageNumber;
      conditionForm.pageSize = $scope.pager.pageSize;
      conditionForm.operateType = form.operateType;
      conditionForm.productId = form.platformId;
      conditionForm.siteId = form.siteId;
      if ( form.channelList ) {
        var cArr = [],
            cList = form.channelList;
        for (var i = cList.length - 1; i >= 0; i--) {
          if ( cList[i].selected ) {
            cArr.push(cList[i].id);
          }
        }
        conditionForm.channelId = cArr.join(',');
      }
      conditionForm.operateDate = $scope.tabset[tabKey].dateString;

      return conditionForm;
    }

    function freeQueryCondition () {
      var conditionForm = {},
          form = $scope.qForm;
      conditionForm.pageNumber = $scope.pager.pageNumber;
      conditionForm.pageSize = $scope.pager.pageSize;
      conditionForm.queryType = form.queryType.type;
      conditionForm.queryStr = form.queryString;
      if ( form.positionSelected ) {
        conditionForm.productId = form.positionSelected.platform && form.positionSelected.platform.id;
        conditionForm.siteId = form.positionSelected.site && form.positionSelected.site.id;
        conditionForm.channelId = form.positionSelected.channel && form.positionSelected.channel.id;
        conditionForm.areaId = form.positionSelected.area && form.positionSelected.area.id;
      }

      return conditionForm;
    }

    function applyNumQuery (applyNumber) {
      //查询类型设置为上线申请单编号
      $scope.qForm.queryType = $scope.qTypes[5];
      $scope.qForm.queryString = applyNumber;
      freeQuery(true);
    }

    //展开的列表的所有二级详情
    function expandAll (dateString) {
      for (var i = $scope.pList.length - 1; i >= 0; i--) {
        $scope.pList[i].dateString = dateString;
        $scope.toggleDetail($scope.pList[i]);
      }
    }

    function getDates () {
      var dates = {};
      dates.today = new Date();
      dates.tomorrow = (function () {
        var today = new Date();
        tomorrow = new Date(today.setDate(today.getDate() + 1));
        return tomorrow;
      })();
      dates.afterTomorrow = (function () {
        var today = new Date();
        afterTomorrow = new Date(today.setDate(today.getDate() + 2));
        return afterTomorrow;
      })();
      return dates;
    }

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }

  }]);

  app.registerFilter('QueryFilter', function () {
    return function ( items, queryText, key ) {
      if (items) {
        if ( key !== 'moreEarly') {
          return items;
        } else if (queryText) {
          return items.filter(function(item, index, array) {
            var conditions = [
              'adContentNumber',
              'advertisers',
              'number',
              'positionName',
              'scheduleNumber'
            ];

            var matched = false;
            for (var i = conditions.length - 1; i >= 0; i--) {
              var field = conditions[i];
              matched = item[field] && item[field].indexOf(queryText || '') !== -1;
              if ( matched ) {
                break;
              }
            }
            return matched;
          });
        } else {
          return items;
        }
      }
    };
  });

  app.registerService('PublicationConstant', ['$filter', function ($filter) {
    var translate = $filter('translate');
    return {
      queryTypes: [
        {
          name: translate('AD_NUMBER'),
          type: 'adSolutionNumber'
        },
        {
          name: translate('CONTENT_NUMBER'),
          type: 'adContentNumber'
        },
        {
          name: translate('SCHEDULE_NUMBER'),
          type: 'scheduleNumber'
        },
        {
          name: translate('MATERIAL_NUMBER'),
          type: 'materialNumber'
        },
        {
          name: translate('POSITION_NUNBER'),
          type: 'resorucePostionNumber'
        },
        {
          name: translate('PUBLICATION_APPLY_NUMBER'),
          type: 'number'
        }
      ]
    };
  }]);
});