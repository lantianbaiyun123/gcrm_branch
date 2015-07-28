define(['app'], function (app) {


/**
 * 
 */
app.registerController( 'CtrlResourcePositionNameModal', [
  '$scope', 'opts', '$modalInstance', 'Modal', 'Position', '$filter',
  function ( $scope, opts, $modalInstance, Modal, Position, $filter ) {
    Position.queryPosition( { id: opts.rowData.id } ).success( function ( response ) {
      if ( response.code === 200 && response.data.i18nData) {
        $scope.e.cnName = response.data.i18nData.cnName;
        $scope.e.enName = response.data.i18nData.enName;
      }
    });
    $scope.e = {};
    $scope.ok = function () {
      var postData = {};
      postData.id = opts.rowData.id;
      postData.i18nData = {
        cnName: $scope.e.cnName,
        enName: $scope.e.enName
      };
      Position.updatePositionName( postData ).success( function ( response ) {
        if ( response.code === 200 ) {
          $modalInstance.close();
          Modal.success( { content: $filter('translate')('RESOURCE_POSITION_EDIT_NAME_SUCCESS') });
        }
      });
    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
  }]);



});