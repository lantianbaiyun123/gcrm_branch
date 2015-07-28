define(['app'], function (app) {
  app.registerController('CtrlHao123Trace', [
    '$scope',
    'PageSet',
  function ($scope, PageSet) {
    PageSet.set({activeIndex:6,siteName:'hao123Trace'});

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

    function getList() {

    }

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }

  }]);
});
