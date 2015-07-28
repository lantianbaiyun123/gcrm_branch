define([
  'app',
  '../_services/http/PersonalInfo',
  '../_services/http/Task',
  '../_services/http/Customer',
  '../_services/http/BenchmarkPrice',
  '../_services/http/Material',
  'highcharts',
  'highcharts-ng',
  '../notice/ModalNotice',
  '../notice/ServiceNoticeHttp'
], function ( app ) {

  app.registerController('CtrlHome', [
    '$scope',
    '$state',
    '$q',
    '$filter',
    '$log',
    'PageSet',
    'PersonalInfo',
    'Task',
    'Customer',
    'Ad',
    'BenchmarkPrice',
    'Material',
    'Modal',
    'HighchartsConfig',
    'ModalNotice',
    'NoticeHttp',
  function ($scope, $state, $q, $filter, $log, PageSet, PersonalInfo, Task, Customer, Ad, BenchmarkPrice, Material, Modal, HighchartsConfig, ModalNotice, NoticeHttp) {
    // PageSet.set({siteName: 'home'});
    var translateFilter = $filter('translate');
    var stateMap = {
      approval: 'task.facade.approval',
      schedule: 'task.facade.schedule',
      quote: 'task.facade.benchmarkPriceApproval',
      material: 'task.facade.materialApproval',
      customer: 'task.facade.customerApproval',
      onlineApply: 'task.facade.pubApplyApproval'
    };

    $scope.state = {};

    $scope.taskTypes =  {
      approval: {
        name: $filter('translate')('TASK_TYPE_APPROVAL'),
        tasks: []
      },
      operation: {
        name: $filter('translate')('TASK_TYPE_OPERATE'),
        tasks: []
      }
    };

    init();
    function init () {
      getTask();
      getDisplay().then(function () {
        if ($scope.display.moduleCount) {
          getModuleCount();
        }
      });
      getNoticeList();
    }

    $scope.goFirstTask = function (key) {
      var task = $scope.taskTypes[key].tasks[0],
          state = 'task.facade.noTask',
          stateParams = {};
      if ( task && task.subtype ) {
        state = stateMap[task.subtype];
        stateParams[task.foreignName] = task.foreignKey;
        stateParams.activityId = task.activityId;
        stateParams.processId = task.processId;
      }

      $state.go(state, stateParams);
    };

    $scope.goTask = function (task) {
      var
        state = 'task.facade.noTask',
        stateParams = {};
      if ( task && task.subtype ) {
        state = stateMap[task.subtype];
        stateParams[task.foreignName] = task.foreignKey;
        stateParams.activityId = task.activityId;
        stateParams.processId = task.processId;
      }

      $state.go(state, stateParams);
    };

    $scope.mySubmit = {
      customer: {btnIndex: 'btn_idx_recent_submit_cust', next: 'adSolution'},
      adSolution: {btnIndex: 'btn_idx_recent_submit_cust_adsol', next: 'quotation'},
      quotation: {btnIndex: 'btn_idx_recent_submit_cust_quotprice', next: 'material'},
      material: {btnIndex: 'btn_idx_recent_submit_cust_mater'}
    };

    var mySubmitFlag = true;
    $scope.selectMySubmit = function (tabKey) {
      var fnNameMap = {
        customer: 'getSubmitInfoCustomer',
        adSolution: 'getSubmitInfoAdSolution',
        quotation: 'getSubmitInfoQuotation',
        material: 'getSubmitInfoMaterial'
      };
      var fnName = fnNameMap[tabKey];
      PersonalInfo[fnName]().success(function (response) {
        if ( response.code === 200 ) {
          $scope.mySubmit[tabKey].list = response.data;
          $scope.mySubmit[tabKey].list.splice(5);

          //如果当前tab无数据，查询下一个tab
          var next = mySubmitNextTab(tabKey);
          if (!response.data.length && mySubmitFlag && next) {
            $scope.mySubmit[next].active = true;
          } else {
            mySubmitFlag = false;
          }
        }
      });
    };

    function mySubmitNextTab(tabKey) {
      var next = $scope.mySubmit[tabKey].next;
      if (next && !$scope.BtnIndex[$scope.mySubmit[next].btnIndex]) {
        next = mySubmitNextTab(next);
      }
      return next;
    }

    //客户信息催办
    $scope.sendReminderCustomer = function (customerId) {
      Customer.sendReminder({
        customerId: customerId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };
    //广告方案催办
    $scope.sendReminderAdSolution = function (adSolutionId) {
      Ad.press({
        id: adSolutionId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };
    //标杆价催办
    $scope.sendReminderQuotation = function (quotationMainId) {
      BenchmarkPrice.sendReminder({
        quotationMainId: quotationMainId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };

    //物料催办
    $scope.sendReminderMaterial = function (applyId) {
      Material.sendReminder({
        applyId: applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };

    $scope.operation = {};
    $scope.selectOperation = function (tabKey) {
      if ( !$scope.operation[tabKey] ) {
        $scope.operation[tabKey] = {};
      }
      var fnNameMap = {
        customer: 'getOperationInfoCustomer',
        ad: 'getOperationInfoAd',
        platform: 'getOperationInfoPlatform'
      };

      var itemArray = {
        customer: ['agent', 'advertiser', 'other'],
        ad: ['solution', 'contract']
      };

      var fnName = fnNameMap[tabKey];
      PersonalInfo[fnName]().success(function (response) {
        if ( response.code === 200 ) {
          if ( tabKey === 'platform') {
            $scope.operation[tabKey] = response.data;
            $scope.selectPlatform(0);
          } else {
            $scope.operation[tabKey].operationList = response.data.operationList;
            var lineData = prepareLineData(response.data.operationImage, itemArray[tabKey]);
            updateOperationLine(tabKey, lineData);
          }
        }
      });
    };

    $scope.selectPlatform = function (index) {
      setPlatform(index);
      $scope.operation.platformDetail = [];
      PersonalInfo.getOperationInfoOccupation({
        platformId: $scope.operation.platform[index].id
      }).success(function (response) {
        if ( response.code === 200 ) {
          $scope.operation.platformDetail = response.data;
        }
      });
    };

    $scope.noticeRead = function (index) {
      ModalNotice.show({
        noticeId: $scope.noticeList[index].id
      }).then(function (result) {
        if (result === 'ok') {
          $scope.noticeList.splice(index, 1);
        }
      });
    };

    $scope.toggleNoticeMore = function () {
      $scope.state.noticeShowMore = !$scope.state.noticeShowMore;
    };

    function setPlatform (index) {
      if ( $scope.operation.platform ) {
        for (var i = $scope.operation.platform.length - 1; i >= 0; i--) {
          $scope.operation.platform[i].active = false;
        }
        $scope.operation.platform[index].active = true;
      }
    }

    function getDisplay () {
      var deferred = $q.defer();
      PersonalInfo.getDisplay().success(function (response) {
        if ( response.code === 200 ) {
          $scope.display = {};
          for (var i = response.data.length - 1; i >= 0; i--) {
            var panel = response.data[i];
            $scope.display[panel.moduleType] = panel.display;
          }
        }
        deferred.resolve();
      });
      return deferred.promise;
    }

    //取待办列表
    function getTask () {
      Task.taskGet({}, function (response) {
        if(response.code === 200) {
          $scope.myTask = response.data;
          top5Task();
        } else {
          var msg = '[' + response.code + ']' + response.message;
          Modal.alert({content: msg});
        }
      });
    }
    function top5Task () {
      for ( var key in $scope.taskTypes ) {
        var tasks = $filter('filter')($scope.myTask, {type: key});
        $scope.taskTypes[key].count = tasks.length;
        $scope.taskTypes[key].tasks = tasks.slice(0,5);
      }
    }

    function updateOperationLine (key, lineData) {
      if ( !$scope.operation[key].lineConfig ) {
        $scope.operation[key].lineConfig = angular.copy(HighchartsConfig.line);
      }

      $scope.operation[key].lineConfig.xAxis.categories = lineData.categories;
      $scope.operation[key].lineConfig.series = lineData.series;
    }

    function prepareLineData (data, seriesKeys) {
      var categories = [],
          seriesData = {};
      for (var i = seriesKeys.length - 1; i >= 0; i--) {
        seriesData[seriesKeys[i]] = [];
      }
      var length = data.length;
      for ( var j = 0; j < length; j++ ) {
        obj = data[j];
        for ( var key in obj ) {
          if ( key === 'period' ) {
            categories.push(obj[key]);
          } else {
            seriesData[key].push(~~obj[key]);
          }
        }
      }

      var ret = {
        categories: categories,
        series: []
      };

      var skLength = seriesKeys.length;
      for ( var k = 0; k < skLength; k++ ) {
        var keyName = seriesKeys[k];
        ret.series.push({
          name: translateFilter('HOME_OPERATION_TYPE_' + keyName),
          data: seriesData[keyName],
          marker: {
            lineWidth: 2,
            symbol: 'circle'
          }
        });
      }

      return ret;
    }

    function getModuleCount () {
      PersonalInfo.getModuleCount().success(function (response) {
        if ( response.code === 200 ) {
          $scope.moduleCount = response.data;
        }
      });
    }

    function getNoticeList() {
      NoticeHttp.getReceiveList().success(function (response) {
        if (response.code === 200) {
          $scope.noticeList = response.data;
        }
      });
    }
  }]);

  app.registerService('HighchartsConfig', [function () {
    return {
      line: {
        //This is not a highcharts object. It just looks a little like one!
        options: {
          //This is the Main Highcharts chart config. Any Highchart options are valid here.
          //will be ovverriden by values specified below.
          chart: {
            type: 'line'
          },
          credits: {
            enabled: null
          },
          colors: ['#428bca', '#5cb85c', '#f0ad4e', '#910000', '#1aadce', '#2f7ed8',  '#8bbc21',
           '#492970', '#f28f43', '#77a1e5', '#c42525', '#a6c96a', '#0d233a' ],
          tooltip: {
            shared: true,
            crosshairs: true,
            borderColor: '#ccc',
            style: {
              padding: 10,
              fontWeight: 'normal'
            },
            useHTML: true,
            formatter: function() {
              var s = this.x +'<table style="margin-top:10px; width:100%">';
              var sCount = '';
              var total = 0;
              $.each(this.points, function(i, point) {
                sCount += '<tr><td style="text-align: right; padding-bottom: 8px; color: '+ point.series.color +'">'+ point.series.name +'：</td>' +
                              '<td style="text-align: right; padding-bottom: 8px; width:20px; color: '+ point.series.color +'"><b>'+ point.y +'</b></td></tr>';
                total += this.y;
              });
              s += sCount;
              s += '<tr><td style="text-align: right;border-top:1px solid #ccc; padding-top: 8px; border-top-width: 1px;">合计：</td>' +
                       '<td style="text-align: right;border-top:1px solid #ccc; padding-top: 8px; border-top-width: 1px; width:20px;"><b>'+ total +'</b></td></tr>';
              s += '</table>';
              return s;
            }
          },
          legend: {
            verticalAlign: 'top'
          }
        },

        //The below properties are watched separately for changes.

        //Series object (optional) - a list of series using normal highcharts series options.
        series: [
          // {
          //   name: '广告代理（线下）',
          //   marker: {
          //     lineWidth: 2,
          //     symbol: 'circle'
          //   },
          //   data: [10, 15, 12, 8, 7]
          // },{
          //   name: '其他',
          //   marker: {
          //     radius: 4,
          //     lineWidth: 2,
          //     symbol: 'circle'
          //   },
          //   data: [12, 13, 8, 9, 7]
          // }
        ],
         //Title configuration (optional)
        title: {
          text: null
        },
        //Boolean to control showng loading status on chart (optional)
        loading: false,
        //Configuration for the xAxis (optional). Currently only one x axis can be dynamically controlled.
        //properties currentMin and currentMax provied 2-way binding to the chart's maximimum and minimum
        xAxis: {
          title: {text: null},
          categories: [
          // '12/11 ~ 12/18', '12/19 ~ 12/26', '12/27 ~ 01/04', '01/05 ~ 01/12', '01/13 ~ 01/14'
          ],
          labels: {
            rotation: -45,
            style: {
              fontSize: 10,
              fontWeight: 'normal'
            }
          }
        },
        yAxis: {
          min: 0,
          title: {text: null},
          labels: {
            formatter: function() {
                return Math.round(this.value);
            }
          }
        },
        //Whether to use HighStocks instead of HighCharts (optional). Defaults to false.
        useHighStocks: false,
        //size (optional) if left out the chart will default to size of the div or something sensible.
        size: {
          width: 510,
          height: 230
        },
        //function (optional)
        func: function (chart) {
          //setup some logic for the chart
        }
      }
    };
  }]);
});
