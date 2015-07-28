define([
  'app',
], function ( app ) {
  app.registerFactory('ModalPubApplyInfo', [
    '$modal',
    '$q',
    'STATIC_DIR',
  function ($modal, $q, STATIC_DIR) {
    return {
      open: function (opts) {
        var
          defered = $q.defer(),
          $modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/ad2/ModalPubApplyInfo.tpl.html',
          controller: 'CtrlModalPubApplyInfo',
          windowClass: 'modal-pub-apply-info',
          backdrop: 'static',
          resolve: {
            opts: function () {
              return {
                contentNum: opts.contentNum,
                customerNumber: opts.customerNumber
              };
            }
          }
        });
        $modalInstance.result.then(function (result) {
          defered.resolve(result);
        });
        return defered.promise;
      }
    };
  }]);

  app.registerController('CtrlModalPubApplyInfo', [
    '$scope',
    '$modalInstance',
    'opts',
    'AdHttp',
  function ($scope, $modalInstance, opts, AdHttp) {
    $scope.apply = {};
    $scope.apply.contentNum = opts.contentNum;
    $scope.apply.customerContract = ContractSuggest.getContractOption({
      basic: {
        customerSelected: {
          data: opts.customerNumber
        }
      }
    });
    $scope.apply.attachment = {};
    $scope.ok = function () {

    };
    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.removeCodeUploaded = function () {
      $scope.apply.attachment = {};
    };
  }]);
});