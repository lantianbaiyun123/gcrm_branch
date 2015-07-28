/**
 * [标杆价列表页Ctrl]
 */
define([
  'app',
  '../_services/Select2Suggest',
  '../_services/http/Material',
  '../_directives/ytPositionSelect',
  '../_filters/DatePeriodFilter',
  'anuglar-ui-select2'
], function (app) {

  app.registerController('CtrlMaterialList', [
    '$scope',
    '$log',
    '$filter',
    'PageSet',
    'Material',
    'MaterialListConstant',
    'Select2Suggest',
    'Modal',
  function ($scope, $log, $filter, PageSet, Material, MaterialListConstant, Select2Suggest, Modal) {
    PageSet.set({activeIndex:1,siteName:'materialList'});

    //更多查询条件切换
    $scope.queryMoreShow = false;
    $scope.toggleQueryMore = function () {
      $scope.queryMoreShow = !$scope.queryMoreShow;
    };

    $scope.qTypes = MaterialListConstant.queryTypes;

    //初始化查询类型为第一项
    $scope.qForm = {
      queryType: $scope.qTypes[0]
    };
    $scope.qForm.positionSelected = {};
    $scope.materialList = [];

    //广告主下拉suggest
    $scope.advertiser= Select2Suggest.getCustomerOptionForAdOwner({
      allowClear: true
    });

    //签约公司下拉suggest
    $scope.customer = Select2Suggest.getCustomerOption({
      placeholder: $filter('translate')('MATERIAL_INPUT_CUSTOMER'),
      allowClear: true
    });

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

    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue ){
      if ( newValue ) {
        getList();
      }
    });

    $scope.btnQuery = function () {
      resetPageNumber();
      getList();
    };

    //查询类型下拉列表选中
    $scope.setQueryType = function (type) {
      $scope.qForm.queryType = type;
    };

    //催办
    $scope.sendReminder = function (applyId) {
      Material.sendReminder({
        applyId: applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 3000
          });
        }
      });
    };

    //打包查询参数
    function queryParam () {
      var paramObj = {},
          dateFormat = 'yyyy-MM-dd',
          qForm = $scope.qForm,
          pager = $scope.pager;

      paramObj.pageSize = pager.pageSize;
      paramObj.pageNumber = pager.pageNumber;

      paramObj.advertiser = qForm.advertiser;
      paramObj.queryType = qForm.queryType.type;
      paramObj.queryString = qForm.queryString;
      paramObj.beginThrowTime = $filter('date')(qForm.startTime, dateFormat);
      paramObj.endThrowTime = $filter('date')(qForm.endTime, dateFormat);
      paramObj.platformId = qForm.positionSelected.platform && qForm.positionSelected.platform.id;
      paramObj.siteId = qForm.positionSelected.site && qForm.positionSelected.site.id;
      paramObj.channelId = qForm.positionSelected.channel && qForm.positionSelected.channel.id;
      paramObj.areaId = qForm.positionSelected.area && qForm.positionSelected.area.id;
      paramObj.positionId = qForm.positionSelected.position && qForm.positionSelected.position.id;
      paramObj.customerId = qForm.customer && qForm.customer.data;

      return  paramObj;
    }

        //查询列表
    function getList () {
      var paramObj = queryParam();
      Material.getList(paramObj).success(function (response) {
        if ( response.code === 200 ) {
          try {
            $scope.materialList = response.data.content;
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

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }

  }]);

  app.registerService('MaterialListConstant', ['$filter', function ($filter) {
    var translateFilter = $filter('translate');
    return {
      queryTypes: [
        {
          name: translateFilter('MATERIAL_AD_CONTENT_NUMBER'),
          type: 'CONTENTID'
        },
        {
          name: translateFilter('MATERIAL_AD_SOLUTION_NUMBER'),
          type: 'SOLUTIONID'
        },
        {
          name: translateFilter('MATERIAL_RESOURCE_POSITION_NUMBER'),
          type: 'RESOURCEID'
        }
      ]
    };
  }]);
});