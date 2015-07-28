define( [ 'app', '../_common/ytCommon', './CtrlAddUser', './CtrlAddDataAuthority' ], function ( app ) {


app.registerController( 'CtrlUserDetail', [
  '$scope',
  'User',
  '$stateParams',
  '$window',
  '$modal',
  'STATIC_DIR',
  function ( $scope, User, $stateParams, $window, $modal, STATIC_DIR ) {
    if ( !$stateParams.ucId ) {
      return false;
    }
    function doQuery () {
      User.getDetail( { id: $stateParams.ucId } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.userVO = response.data.userVO;
          $scope.uc = {
            username: response.data.userVO.username,
            roleName: response.data.roleName,
            posParent: response.data.posParent,
            posSub: response.data.posSub,
            position: response.data.position
          };
          $scope.dataAuthority = {
            businessTypes: response.data.businessTypes,
            platforms: response.data.platforms,
            sites: response.data.sites,
            hasDataRights: response.data.hasDataRights
          };
          $scope.newRightsURL = response.data.newRightsURL;
        }
      });
    }
    doQuery();
    $scope.changeRole = function () {
      if ( $scope.newRightsURL ) {
        $window.open( $scope.newRightsURL );
      }
    };
    $scope.btnEditUser = function () {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/admin/addUser.tpl.html',
        controller: 'CtrlAddUser',
        resolve: {
          opts: function () {
            return {
              type: 'edit',
              rowData: $scope.userVO
            };
          }
        }
      });
    };
    $scope.btnEditDataAuthority = function () {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/admin/addDataAuthorityModal.tpl.html',
        controller: 'CtrlAddDataAuthority',
        resolve: {
          opts: function () {
            return {
              type: 'edit',
              username: $scope.userVO.username
            };
          }
        }
      });
      modalInstance.result.then( function () {
        doQuery();
      });
    };
  }
]);


});