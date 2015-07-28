define([
  'app',
  '../_services/Select2Suggest'
], function ( app ) {
  app.registerFactory('ModalPubApply', [
    '$modal',
    '$q',
    'STATIC_DIR',
  function ($modal, $q, STATIC_DIR) {
    return {
      applyView: function (opts) {
        var
          defered = $q.defer(),
          $modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/ad2/ModalPubApply.tpl.html',
          controller: 'CtrlModalPubApply',
          windowClass: 'modal-pub-apply',
          backdrop: 'static',
          resolve: {
            opts: function () {
              return opts;
            }
          }
        });
        $modalInstance.result.then(function (result) {
          defered.resolve(result);
        });
        return defered.promise;
      },
      detailView: function (opts) {
        var
          defered = $q.defer(),
          $modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/ad2/ModalPubApplyDetail.tpl.html',
          controller: 'CtrlModalPubApplyDetail',
          windowClass: 'modal-pub-apply',
          resolve: {
            opts: function () {
              return opts;
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

  app.registerController('CtrlModalPubApply', [
    '$scope',
    '$modalInstance',
    '$filter',
    'Modal',
    'opts',
    'ContractSuggest',
    'AdHttp',
  function ($scope, $modalInstance, $filter, Modal, opts, ContractSuggest, AdHttp) {
    $scope.apply = opts;
    if (opts.adSolutionContentApply.contractNumber) {
      var dates = opts.adSolutionContentApply.contractDate.split(',');
      $scope.apply.contract = {
        data: {
          number: opts.adSolutionContentApply.contractNumber,
          beginDate: dates[0],
          endDate: dates[1],
          category: opts.adSolutionContentApply.contractType,
          state: opts.adSolutionContentApply.contractState
        }
      };
    }
    $scope.apply.customerContract = ContractSuggest.getContractOption($scope.apply.customerId);

    $scope.ok = function (formApply) {
      $scope.apply.submitDisabled = true;
      if (formApply.$valid) {
        $scope.apply.adSolutionContentApply.contractNumber = $scope.apply.contract.data.number;
        $scope.apply.adSolutionContentApply.contractType = $scope.apply.contract.data.category;
        $scope.apply.adSolutionContentApply.contractState = $scope.apply.contract.data.state;
        var beginDate = $scope.apply.contract.data.beginDate;
        var endDate = $scope.apply.contract.data.endDate;
        if (!/-/.test(beginDate)) {
          beginDate = $filter('date')(beginDate, 'yyyy-MM-dd');
          endDate = $filter('date')(endDate, 'yyyy-MM-dd');
        }
        $scope.apply.adSolutionContentApply.contractDate = beginDate + ',' + endDate;
        var promise = AdHttp.pubApplySubmit({
          adSolutionContentApply: $scope.apply.adSolutionContentApply,
          attachments: $scope.apply.attachments
        });
        promise.success(function (response) {
          if ( response.code === 200 ) {
            $modalInstance.close({
              btnType: 'ok',
              response: response
            });
          }
        });
        promise['finally'](function () {
          $scope.apply.submitDisabled = false;
        });
      } else {
        $scope.apply.submitDisabled = false;
      }
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.removeAttachment = function (index) {
      $scope.apply.attachments.splice(index, 1);
    };

    $scope.beginUpload = function () {
      $scope.apply.submitDisabled = true;
    };

    $scope.uploaded = function () {
      $scope.apply.submitDisabled = false;
    };
  }]);

  app.registerController('CtrlModalPubApplyDetail', [
    '$scope',
    '$modalInstance',
    '$filter',
    'ModalC',
    'opts',
    'AdHttp',
    'GCRMUtil',
  function ($scope, $modalInstance, $filter, ModalC, opts, AdHttp, GCRMUtil) {
    $scope.applyDetail = opts;

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };

    $scope.pubApplyWithdraw = function () {
      AdHttp.pubApplyWithdraw({
        applyId: $scope.applyDetail.adSolutionContentApply.id
      }).success(function (response) {
        if (response.code === 200) {
          $modalInstance.close({
            btnType: 'withdraw',
            response: response
          });
        } else {
          closeAsFail(response);
        }
      });
    };

    $scope.pubApplyReminder = function () {
      AdHttp.pubApplyReminder({
        applyId: $scope.applyDetail.adSolutionContentApply.id
      }).success(function (response) {
        if ( response.code === 200 ) {
          ModalC.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        } else {
          closeAsFail(response);
        }
      });
    };

    function closeAsFail(response) {
      if (response.code === 206) {
        ModalC.alert({
          content: GCRMUtil.decodeGCRMError( response.errors )
        }).then(function (result) {
          $modalInstance.close({
            btnType: 'fail',
            response: response
          });
        });
      }
    }
  }]);

  app.registerFactory('ContractSuggest', [
    '$http',
    '$filter',
    'APP_CONTEXT',
    'Contract',
  function ($http, $filter, APP_CONTEXT, Contract) {
    return {
      getContractOption: function ( customerId , options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          // apply css that makes the dropdown taller
          dropdownCssClass: "pub-apply-contract",
          dropdownAutoWidth: true,
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('PUB_APPLY_CONTRACT_SUGGEST_RELATED_CONTRACT_NO'),
          // minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            var paramObj = {
              // query: $.trim(query.term),
              customerId: customerId
            };
            $http({
              method: 'get',
              url: APP_CONTEXT + 'adcontent/applyOnline/contractSuggest',
              // url: '/data/applyContractSuggest.json',
              params: paramObj
            }).success(function (response) {
              if (response.code === 200 && response.data && response.data.length) {
                data.results = response.data;
                data.results.unshift(dataHead);
                query.callback(data);
              } else {
                query.callback(data);
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('PUB_APPLY_CONTRACT_SUGGEST_NO_AGREEMENT_NUMBER');
          },
          formatResult: function(item) {
            if (!item.data) {
              return '<div class="headDrop">' +
                        '<span class="dropCol1">' + $filter('translate')('SELECTTOSUGGEST_CONTRACT_NUMBER') + '</span>' +
                        '<span class="dropCol2">' + $filter('translate')('SELECTTOSUGGEST_CONTRACT_TYPE') +'</span>' +
                        '<span class="dropCol3">' + $filter('translate')('STATUS') +'</span>' +
                        '<span class="dropCol4">' + $filter('translate')('AD_BASIC_CONTRACT_VALIDITY') +'</span>' +
                      '</div>';
            }
            var beginDate = $filter('date')(item.data.beginDate, 'yyyy-MM-dd'),
                endDate = $filter('date')(item.data.endDate, 'yyyy-MM-dd') || '';
            return '<div class="bodyDrop">'+
                      '<span class="dropCol1">' + item.data.number + '</span>'+
                      '<span class="dropCol2">' + $filter('translate')( 'CONTRACT_CATEGORY_' + item.data.category ) + '</span>'+
                      '<span class="dropCol3">' + $filter('translate')( 'CONTRACT_STATE_' + item.data.state ) + '</span>'+
                      // '<span class="dropCol4">' + item.data.summary + '</span>'+
                      '<span class="dropCol4">' + beginDate + ' ~ '+ endDate + '</span>'+
                    '</div>';
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            if (!item.data) {
              return '';
            }
            return item.data.number;
          },
          escapeMarkup: function(m) {
            return m;
          }
        };
      }
    };
  }]);
});