define([
  'app',
  './ServiceNotice'
], function (app) {
  app.registerController('CtrlNoticeList', [
    '$scope',
    '$log',
    'PageSet',
    'Notice',
  function ($scope, $log, PageSet, Notice) {
    PageSet.set({activeIndex:6,siteName:'noticeList'});

    // pagenation
    $scope.pager = {
      pageSize: 10,
      pageSizeSlots: [10,20,30,50],
      pageNumber: 1,
      totalCount:0
    };

    // 更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      resetPageNumber();
      getList();
    };

    // 当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue ){
      if ( newValue ) {
        getList();
      }
    });

    // getList();

    function getList () {
      var pager = $scope.pager;
      Notice.getList({
        pageNumber: pager.pageNumber,
        pageSize: pager.pageSize
      }).success(function (response) {
        if ( response.code === 200 ) {
          try {
            $scope.noticeList = resolveNoticeList(response.data.content);
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
          } catch(e) {
            $log.info(e);
          }
        } else {
          $log.info(response);
        }
      });
    }

    function resolveNoticeList(noticeList) {
      for (var i = noticeList.length - 1; i >= 0; i--) {
        var
          notice = noticeList[i],
          receiversKey = 'customers',
          displayFild = 'value';

        if (notice.receiveScope === 'internal') {
          receiversKey = 'users';
          displayFild = 'realName';
        }

        var receiverText = (notice[receiversKey][0] && notice[receiversKey][0][displayFild]) || '';
        for (var j = 1, length = notice[receiversKey].length; j < length; j++) {
          var receiver = notice[receiversKey][j];
          receiverText = receiverText + '、' + receiver[displayFild];
        }

        notice.receiverText = receiverText;
      }
      return noticeList;
    }

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }
  }]);
});
