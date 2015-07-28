define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomerQualification 代理商资质
   */
app.registerController( 'CtrlCustomerQualification', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  'Modal',
  function ( $scope, $state, $stateParams, $timeout, Modal ) {
    $scope.qualification = $scope.qualification || {
      state: 'addEditing',
      customerResources: [{},{},{}]
    };
    /*
      详情时，修改基本信息
     */
    $scope.btnEdit = function () {
      $scope.tempQualification = angular.copy( $scope.qualification );
      if ( !$scope.qualification.customerResources || !$scope.qualification.customerResources.length ) {
        $scope.qualification.customerResources = [{},{},{}];
      }
      $scope.qualification.state = 'detailEditing';
    };
    /* 取消编辑啊*/
    $scope.btnCancel = function () {
      angular.extend( $scope.qualification, $scope.tempQualification, {
        state: 'detailViewing'
      });
    };
    //保存基本信息
    $scope.btnSave = function () {
      $scope.$emit( 'qualificationSave' );
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});