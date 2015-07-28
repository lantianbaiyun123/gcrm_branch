define( [ 'app', '../_common/ytCommon', '../_directives/ytInputDropdown', './CtrlAddUser', './CtrlAddDataAuthority' ], function ( app ) {


app.registerController( 'CtrlAdminUserList',[
  '$scope',
  '$timeout',
  'User',
  'PageSet',
  '$modal',
  '$state',
  '$window',
  'Modal',
  'STATIC_DIR',
  function ( $scope, $timeout, User, PageSet, $modal, $state, $window, Modal, STATIC_DIR ) {
    PageSet.set( { 'siteName': 'adminUserList', 'activeIndex': 6 } );
    $scope.q = {};
    // User.getList().success( function ( response ) {
    //   if ( response.code === 200 ) {
    //     $scope.userList = response.data.content;
    //   }
    // });
    User.getRoleList().success( function ( response ) {
      if ( response.code === 200 ) {
        $scope.q.roleTypes = response.data.allRoles;
      }
    });
    var query = {
      initQuery: function () {
        $scope.q = {};
        $scope.pager = {
          pageNumber: 1,
          pageSize: 10,
          pageSizeSlots: [10,20,30,50],
          totalCount: 0
        };
        $timeout( function () {
          query.doQuery();
        });
      },
      doQuery: function () {
        User.getList( this.getQueryParams() ).success( function (response) {
          if ( response.code === 200 ) {
            $scope.userList = response.data.content;
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
          }
        });
      },
      getQueryParams: function () {
        var postData = {
          realName: $scope.q.realName,
          email: $scope.q.email,
          ucName: $scope.q.ucName,
          pageSize: $scope.pager.pageSize,
          pageNumber: $scope.pager.pageNumber
        };
        if ( $scope.q.roleType ) {
          postData.roleId = $scope.q.roleType.id;
        }
        return postData;
      },
      resetPageNumber: function () {
        $scope.pager.pageNumber = 1;
      },
      dateFormat: 'yyyy-MM-dd'
    };
    query.initQuery();
    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue, oldValue ){
      if ( newValue && oldValue && newValue !== oldValue ) {
        query.doQuery();
      }
    });
    //点击了查询
    $scope.btnQuery = function () {
      query.resetPageNumber();
      query.doQuery();
    };
    //更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      query.resetPageNumber();
      query.doQuery();
    };
    //添加用户
    $scope.btnAddUser = function () {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/admin/addUser.tpl.html',
        controller: 'CtrlAddUser',
        resolve: {
          opts: function () {
            return {
              type: 'add'
            };
          }
        }
      });
      modalInstance.result.then( function () {
        query.doQuery();
      });
    };
    //作废
    $scope.btnDisable = function ( item ) {
      User.disable( {
        ucId: item.ucId,
        status: item.status
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          query.doQuery();
          Modal.success( { content: '已作废' } );
        }
      });
    };
    //启用
    $scope.btnEnable = function ( item ) {
      User.enable( {
        ucId: item.ucId,
        status: item.status
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          query.doQuery();
          Modal.success( { content: '已启用' } );
        }
      });
    };
    //查看详情
    $scope.navToUserDetail = function ( item ) {
      $window.open( '#/userDetail?ucId=' + item.ucId );
      // $state.go( 'userDetail', {
      //   userId: item.id
      // });
    };
    //
    $scope.btnAddDataAuthority = function ( item ) {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/admin/addDataAuthorityModal.tpl.html',
        controller: 'CtrlAddDataAuthority',
        resolve: {
          opts: function () {
            return {
              type: 'add',
              username: item.ucName
            };
          }
        }
      });
      modalInstance.result.then( function () {
        query.doQuery();
      });
    };
    // $scope.btnAddDataAuthority();
  }
]);


});