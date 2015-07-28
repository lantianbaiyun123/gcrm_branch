/**
 * 标杆价列表页ctrl
 * cotroller for feature 'benchmarkPriceList'
 */
define([
  'app',
  '../_common/ytCommon',
  '../_services/PageSet',
  '../_services/Select2Suggest',
  '../_services/http/BenchmarkPrice',
  '../_directives/ytPopoverConfirm',
  'anuglar-ui-select2'
], function (app) {

  app.registerController('CtrlBenchmarkPriceList', [
    '$scope',
    '$log',
    '$state',
    '$filter',
    '$timeout',
    'PageSet',
    'Modal',
    'Select2Suggest',
    'BenchmarkPrice',
  function ($scope, $log, $state, $filter, $timeout, PageSet, Modal, Select2Suggest, BenchmarkPrice) {
    PageSet.set({activeIndex:1,siteName:'benchmarkPriceList'});

    $scope.selectAll = {
      text: $filter('translate')('BENCHMARK_PRICE_SELECT_ALL'),
      value: ''
    };

    if ($scope.msgs.listAlerts.length) {
      $timeout(function () {
        $scope.msgs.listAlerts = [];//清空列表页提示信息
      }, 5000);
    }

    $scope.closeMsg = function(index) {
      $scope.msgs.listAlerts.splice(index, 1);
    };

    //更多查询条件切换
    $scope.queryMoreShow = false;
    $scope.toggleQueryMore = function () {
      $scope.queryMoreShow = !$scope.queryMoreShow;
    };

    //初始化查询表单的当前值
    $scope.queryForm = {
      platform: $scope.selectAll,
      siteArea: $scope.selectAll,
      status: $scope.selectAll,
      businessType: $scope.selectAll,
      approvalStatus: $scope.selectAll
    };

    //投放平台选择变化，联动站点区域select list
    $scope.$watch('queryForm.platform.value', function ( newVal, oldVal ) {
      if ( $scope.constant ) {
        if ( newVal ) {
          $scope.getSiteArea(newVal).then(function (result) {
            $scope.constant.siteArea = result;
          });
        } else {
          $scope.constant.siteArea = [];
        }
        $scope.queryForm.siteArea = $scope.selectAll;
      }
    });

    //改变查询表单某查询条件内容
    $scope.changeQueryForm = function (key, valueObj) {
      $scope.queryForm[key] = valueObj;
    };

    //提交人下拉suggest
    $scope.submitter = Select2Suggest.getBenchmarkPriceSubmitter();

    //pagenation
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
      getList();
    };

    //标杆价list
    $scope.priceList = [];

    $scope.btnQuery = function () {
      resetPageNumber();
      getList();
    };

    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue, oldValue ){
        if ( newValue && newValue !== oldValue ) {
          getList();
        }
    });

    $scope.withdrawPrice = function (quotationMainId) {
      BenchmarkPrice.withdraw({
        quotationMainId: quotationMainId
      }).success(function (response) {
        if ( response.code === 200 ) {
          getList();
          Modal.success({
            content: $filter('translate')('SUCCESS_WITHDRAW'),
            timeOut: 2000
          });
        }
      });
    };

    $scope.nullifyPrice = function (quotationMainId) {
      BenchmarkPrice.nullify({
        quotationMainId: quotationMainId
      }).success(function (response) {
        if ( response.code === 200 ) {
          getList();
          Modal.success({
            content: $filter('translate')('SUCCESS_NULLITY'),
            timeOut: 2000
          });
        }
      });
    };

    $scope.sendReminder = function (quotationMainId) {
      BenchmarkPrice.sendReminder({
        quotationMainId: quotationMainId
      }).success(function (response) {
        if ( response.code === 200 ) {
          getList();
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };


    init();
    function init () {
      getList();
    }

    function getList () {
      var paramObj  = queryParam();
      BenchmarkPrice.getList(paramObj).success(function (response) {
        if( response.code === 200 ) {
          try {
            $scope.priceList = response.data.result;
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
          } catch(e) {
            $log.info(response);
          }
        } else {
          $log.info(response);
        }
      });
    }

    function queryParam () {
      var paramObj = {},
          dateFormat = 'yyyy-MM-dd';
      paramObj.pageSize = $scope.pager.pageSize;
      paramObj.pageNumber = $scope.pager.pageNumber;
      paramObj.advertisingPlatformId = $scope.queryForm.platform.value;
      paramObj.siteId = $scope.queryForm.siteArea.value;
      paramObj.businessType = $scope.queryForm.businessType.value;
      paramObj.approveStatus = $scope.queryForm.approvalStatus.value;
      paramObj.status = $scope.queryForm.status.value;
      paramObj.startDate = $filter('date')($scope.queryForm.startDate, dateFormat);
      paramObj.endDate = $filter('date')($scope.queryForm.endDate, dateFormat);

      $scope.queryForm.operator && (paramObj.createOperator = $scope.queryForm.operator.value);

      return paramObj;
    }

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }

  }]);

});
