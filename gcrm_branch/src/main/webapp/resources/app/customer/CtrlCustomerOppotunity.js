define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomerOppotunity 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerOppotunity', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  'Modal',
  function ( $scope, $state, $stateParams, $timeout, Modal ) {
     $scope.$on( 'customerAdd', function ( event, params ) {
      //state: 1.addEditing 2.detailEditing 3. detailViewing
      $scope.state = 'addEditing';
      $scope.currencyTypes = params.currencyTypes;
    });
    $scope.$on( 'customerDetail', function ( event, params ) {
      $scope.oppotunity = params.opportunityView;
    });
    $scope.queryTypes = [
      {
        name: '广告代理',
        type: 'agent'
      },
      {
        name: '直客',
        type: 'straight'
      },
      {
        name: '非直客',
        type: 'not'
      },
      {
        name: '网盟',
        type: 'union'
      }
    ];
  }
]);
  /**
   * End of Ctrl Code
   */
});