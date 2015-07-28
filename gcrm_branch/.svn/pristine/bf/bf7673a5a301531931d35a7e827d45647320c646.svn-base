define([
  'app',
  '../_services/Select2Suggest'
], function ( app ) {
  app.registerFactory('ModalPO', [
    '$modal',
    '$q',
    'STATIC_DIR',
  function ($modal, $q, STATIC_DIR) {
    return {
      show: function (opts) {
        var
          defered = $q.defer(),
          $modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/ad2/ModalPO.tpl.html',
          controller: 'CtrlModalPO',
          resolve: {
            opts: function () {
              return {
                type: opts.type,
                customerNumber: opts.customerNumber,
                adSolutionId: opts.adSolutionId
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

  app.registerController('CtrlModalPO', [
    '$scope',
    '$modalInstance',
    'opts',
    'Select2Suggest',
    'AdHttp',
  function ($scope, $modalInstance, opts, Select2Suggest, AdHttp) {
    if ( opts.type === 'direct' ) {
      $scope.title = 'AD_SOLUTION_DETAIL_PO_TITLE_EMPTY';
    } else if ( opts.type === 'frame' ) {
      $scope.title = 'AD_SOLUTION_DETAIL_PO_TITLE_FRAME';
    } else if ( opts.type === 'protocol' ) {
      $scope.title = 'AD_SOLUTION_DETAIL_PO_TITLE_PROTOCOL';
    }
    $scope.optionContract = Select2Suggest.getContractOption({
      basic: {customerSelected: {data: opts.customerNumber}}
    });
    $scope.po = {};
    $scope.ok = function () {
      if ( opts.type === 'direct' ) {
        AdHttp.findContractNumberPo({
          id: opts.adSolutionId,
          contractNumber: $scope.po.contract.data.number
        }).success(function ( response ) {
          if ( response.code === 200 ) {
            if ( response.data ) {
              $modalInstance.close( {
                type: opts.type,
                data: response.data
              } );
            }
          }
        });
      } else {
        //框架下单笔合同   协议下单笔合同
        AdHttp.createOtherContract({
          id: opts.adSolutionId,
          contractNumber: $scope.po.contract.data.number,
          contractType: opts.type
        }).success(function (response) {
          if ( response.code === 200 ) {
            //CMS返回成功后，显示已提交至商务人员提示
            // Modal.success({content: translatedText.btnContractTypeSuccess});
            $modalInstance.close( {
              type: opts.type,
              data: response.data
            });
          }
        });
      }
    };
    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  }]);
});