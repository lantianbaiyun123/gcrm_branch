define([
  'app',
  '../../_services/http/Customer'
], function ( app ) {
  app.registerFactory( 'SalesTransferModal', [
    '$q',
    '$modal',
    'STATIC_DIR',
  function ( $q, $modal, STATIC_DIR ) {
    return {
      show: function (opts) {
        var deferred = $q.defer();
        var optsDefault = {};
        var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/customer/list/salesTransferModal.tpl.html',
            controller: 'CtrlSalesTransfer',
            windowClass: 'sales-transfer-modal',
            backdrop: 'static',
            resolve: {
              opts : function () {
                return angular.extend(optsDefault, opts);
              }
            }
        });

        modalInstance.result.then(function (result) {
          if (result.btnType === 'ok') {
            deferred.resolve(result.response);
          }
        });

        return deferred.promise;
      }
    };
  }]);

  app.registerController('CtrlSalesTransfer', [
    '$scope',
    '$log',
    '$modalInstance',
    '$filter',
    '$q',
    'Utils',
    'Customer',
    'opts',
  function ($scope, $log, $modalInstance, $filter, $q, Utils, Customer, opts) {

      $scope.title = opts.title;
      $scope.customerData = opts.customerData;

      $scope.salesSelect2 = Customer.getBelongSalesSuggestOption();

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };

      $scope.ok = function () {
        $scope.submitDisabled = true;
        if (validate()) {
          var submitData = prepareSubmitData();
          var promise = Customer.salesTransfer(submitData);
          promise.success(function (response) {
            if ( response.code === 200 ) {
              $modalInstance.close({
                btnType: 'ok',
                response: response
              });
            }
          });
          promise['finally'](function () {
            $scope.submitDisabled = false;
          });
        } else {
          $scope.submitDisabled = false;
        }
      };

      function validate () {
        $log.info($scope.customerData);
        return !!$scope.customerData.newSales.id;
      }

      function prepareSubmitData () {
        var submitData = {};
        submitData.customerId = $scope.customerData.customerId;
        submitData.salesId = $scope.customerData.newSales.id;

        return submitData;
      }
  }]);
});