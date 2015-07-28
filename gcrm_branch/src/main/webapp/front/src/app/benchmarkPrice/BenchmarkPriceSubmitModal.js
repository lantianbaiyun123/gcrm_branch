define([ 'app' ], function ( app ) {
  app.registerFactory( 'BenchmarkPriceSubmitModal', [
    '$modal',
    '$timeout',
    '$filter',
    'Modal',
    'BenchmarkPrice',
    'STATIC_DIR',
  function ( $modal, $timeout, $filter, Modal, BenchmarkPrice, STATIC_DIR) {
    return {
      check: function (submitDatas, opts, checkGoodFn, cancelFn) {
        var optsDefault = {
          title: 'RECORD_MODIFY_RECORD'
        };
        opts = angular.extend(optsDefault, opts);
        BenchmarkPrice.checkConflict(submitDatas).success(function (response) {
          if(response.code === 200) {
            if (response.data && response.data.length) {
              var modalDatas = {};
              opts.modalDatas = prepareMsg(response.data);
              var modalInstance = $modal.open({
                templateUrl: STATIC_DIR + 'app/benchmarkPrice/submitModal.tpl.html',
                controller: 'CtrlModal',
                backdrop: 'static',
                windowClass: 'benchmark-price-submit-modal',
                resolve: {
                  opts : function () {
                    return opts;
                  }
                }
              });
              modalInstance.result.then(function (result) {
                if ( result === 'ok' ) {
                  checkGoodFn();
                }
                if ( result === 'back' ) {
                  cancelFn();
                }
              });
            } else {
              checkGoodFn();
            }
          } else {
            var msg = '[' + response.code + ']' + response.message;
            Modal.alert({content: msg});
          }
        });

        function prepareMsg (data) {
          var msgData = data || {},
              translateFilter = $filter('translate'),
              //类型为枚举1,2,3,4
              keyMap = [
                '', //fake
                'QUOTATION_CONFIRM_MAIN_MSG_TYPE_1',
                'QUOTATION_CONFIRM_MAIN_MSG_TYPE_2',
                'QUOTATION_CONFIRM_MAIN_MSG_TYPE_3',
                'QUOTATION_CONFIRM_MAIN_MSG_TYPE_4'
              ];

          for (var i = msgData.length - 1; i >= 0; i--) {
            var conflictType = msgData[i].conflictType;
            msgData[i].msgMain = translateFilter(keyMap[conflictType], {newPeriods: msgData[i].newPeriods});
          }

          return msgData;
        }
      }
    };
  }]);
});