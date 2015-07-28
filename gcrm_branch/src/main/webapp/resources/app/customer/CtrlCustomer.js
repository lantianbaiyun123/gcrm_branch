define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomer 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomer', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  '$http',
  'Modal',
  function ( $scope, $state, $stateParams, $timeout, $http, Modal ) {
    // $http.get('/data/generate.json');
  }
]);
  /**
   * End of Ctrl Code
   */
});