define(['app', '../_directives/ytAjaxupload'], function (app) {


/**
 * 
 */
app.registerController( 'CtrlResourcePositionPictureModal', [
  '$scope', 'opts', '$modalInstance', 'Modal', 'Position', 'APP_CONTEXT', '$filter',
  function ( $scope, opts, $modalInstance, Modal, Position, APP_CONTEXT, $filter ) {
    // opts三种，添加、查看、编辑,+ 1 批量修改属性
    var translateFilter = $filter( 'translate' );
    var successMessage;
    if ( opts.type === 'add' ) {
      $scope.title =translateFilter( 'RESOURCE_POSITION_BUTTON_ADDIMAGE' );
      successMessage = translateFilter( 'RESOURCE_POSITION_ADD_IMAGE_SUCCESS' );
    } else if ( opts.type === 'edit' ) {
      $scope.title = translateFilter( 'RESOURCE_POSITION_BUTTON_MODIFYIMAGE' );
      $scope.originImage = APP_CONTEXT + 'position/viewGuide/' + opts.rowData.id;
      successMessage = translateFilter( 'RESOURCE_POSITION_EDIT_IMAGE_SUCCESS' );
    }
    $scope.e = {};
    $scope.uploadedCbfn = function () {
      
    };
    $scope.removePic = function () {
      $scope.e.fileUploaded = null;
    };
    $scope.ok = function () {
      if ( $scope.e.fileUploaded ) {
        var postData = $scope.e.fileUploaded;
        postData.id = opts.rowData.id;
        Position.updateGuideFileProperty( postData ).success( function ( response ) {
          if ( response.code === 200 ) {
            $modalInstance.close();
            Modal.success( { content: successMessage } );
          }
        });
        
      }
    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
  }]);



});