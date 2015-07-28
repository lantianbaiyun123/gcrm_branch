define( [ 'app', '../_common/ytCommon' ], function ( app ) {


app.registerController( 'CtrlAddUser', [
  '$scope',
  'User',
  '$modalInstance',
  'Modal',
  'opts',
  function ( $scope, User, $modalInstance, Modal, opts ) {
    if ( opts.type === 'edit' ) {
      $scope.e = opts.rowData;
      $scope.hideAccountInfo = true;
    } else {
      $scope.e = {};
    }
    
    $scope.ok = function () {
      User.save( $scope.e ).success( function ( response ) {
        if ( response.code === 200 ) {
          $modalInstance.close();
          Modal.success( { content: 'yeap' } );
        } else if ( response.code === 206 ) {
          $modalInstance.close();
          Modal.success( { content: response.message } );
        }
      });
    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
  }
]);


});