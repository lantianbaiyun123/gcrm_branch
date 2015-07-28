define([
  'app',
  'ng-clip'
], function (app) {
  app.config(['ngClipProvider', function(ngClipProvider) {
    ngClipProvider.setPath(STATIC_DIR + 'vendor/zeroclipboard/ZeroClipboard.swf');
  }]);

  app.registerController( 'CtrlResourcePositionCodeModal', [
    '$scope',
    '$window',
    '$timeout',
    '$modalInstance',
    'opts',
    'Modal',
    'APP_CONTEXT',
    'STATIC_DIR',
  function ($scope,  $window, $timeout, $modalInstance, opts, Modal, APP_CONTEXT, STATIC_DIR) {

    $scope.e = {
      code: opts.rowData.positionCode
    };

    $scope.copyCode = function () {
      $scope.e.copied = true;
      $timeout(function () {
        $scope.e.copied = null;
      }, 5000);
    };

    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
  }]);
});