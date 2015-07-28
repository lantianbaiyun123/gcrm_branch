define( [ 'app' ], function ( app ) {
  app.registerController( 'CtrlCustomerContractList', [
    '$scope',
    '$window',
    function ( $scope, $window ) {
      $scope.navToContractDetail = function ( item ) {
        $window.open( item.detailUrl );
      };
  }]);
});