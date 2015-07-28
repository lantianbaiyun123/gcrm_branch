define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomerAttachment 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerAttachment', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  '$log',
  'Modal',
  'Customer',
  '$window',
  function ( $scope, $state, $stateParams, $timeout, $log, Modal, Customer, $window ) {
    var beforeUploadedFileLength = 0;
    $scope.beginUpload = function () {
      beforeUploadedFileLength = $scope.attachment.attachments.length;
    };
    $scope.uploaded = function () {
      $timeout( function () {
        var list = $scope.attachment.attachments;
        for (var i = beforeUploadedFileLength; i < list.length; i++) {
          list[i].type = $scope.attachment.addType;
          list[i].isEditing = true;
          if ( $scope.basic.id ) {
            list[i].customerNumber = $scope.basic.id;
          }
        }
      });
    };
    /**
     * 删除一条联系人信息
     * 添加时，直接删除即可
     * 编辑时，需要发请求删除
     */
    $scope.removeRow = function ( item, index ) {
      if ( $scope.attachment.attachments && $scope.attachment.attachments.length ) {
        if ( !item.id || item.id === -1 ) {
          $scope.attachment.attachments.splice( index, 1 );
        } else {
          Customer.removeAttachment( item ).success( function ( response ) {
            if ( response.code === 200 ) {
              if ( $scope.attachment.attachments && $scope.attachment.attachments.length ) {
                $scope.attachment.attachments.splice( index, 1 );
              }
              // $window.location.reload();
              $scope.customerApplyInfo.approveState = 'saving';
              $scope.basic.companyStatus = 0;
            }
          });
        }
      }
    };
    $scope.editRow = function ( item ) {
      item.isEditing = true;
    };
    $scope.saveRow = function ( item ) {
      Customer.saveOrUpdateAttachment( item ).success( function ( response ) {
        if ( response.code === 200 ) {
          angular.extend( item, response.data, { isEditing: false } );
          // $window.location.reload();
          $scope.customerApplyInfo.approveState = 'saving';
          $scope.basic.companyStatus = 0;
        }
      });
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});