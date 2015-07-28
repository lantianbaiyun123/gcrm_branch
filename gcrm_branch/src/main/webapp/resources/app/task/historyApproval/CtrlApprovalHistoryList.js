/**
 * [操作历史列表页Ctrl]
 */
define([
  'app',
  '../../_services/PageSet',
  '../../_services/http/ApprovalHistory',
   '../../_filters/ApprovalResultFilter'
], function (app) {

  app.registerController('CtrlApprovalHistoryList', [
    '$scope',
    '$log',
    '$filter',
    'PageSet',
    'ApprovalHistory',
  function ($scope, $log, $filter, PageSet, ApprovalHistory) {
    PageSet.set({activeIndex:1,siteName:'historyList'});
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

    //查询列表
    function getList () {
      ApprovalHistory.getList().success(function (response) {
        if ( response.code === 200 ) {
          try {
            $scope.historyList = response.data.content;
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

});