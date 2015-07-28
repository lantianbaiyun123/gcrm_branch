define([
  'app',
  '../_services/http/Customer',
  '../_filters/RecordOperateTypeFilter'
], function ( app ) {
  app.registerFactory( 'CustomerModifyRecord', [
    '$modal',
    '$log',
    '$timeout',
    '$filter',
    'Modal',
    'Customer',
    'STATIC_DIR',
  function ( $modal,$log, $timeout, $filter, Modal, Customer, STATIC_DIR) {
    return {
      show: function ( customerId, opts, cbfn) {
        var optsDefault = {
          title: 'RECORD_MODIFY_RECORD'
        };
        opts = angular.extend(optsDefault, opts);
        var render = function (response) {
            if(response.code === 200) {

                opts.modalDatas = response.data;

                var modalInstance = $modal.open({
                    templateUrl: STATIC_DIR + 'app/record/modifyRecordModal.tpl.html',
                    controller: 'CtrlModal',
                    windowClass: 'modification-record-modal',
                    resolve: {
                      opts : function () {
                        return opts;
                      }
                    }
                });

                modalInstance.result.then(function (result) {
                  if (result === 'ok') {
                    cbfn();
                  }
                });
            } else {
                var msg = '[' + response.code + ']' + response.message;
                Modal.alert({content: msg});
            }
        };
        if ( customerId ) {
          Customer.modifyRecord({
            customerId: customerId
          }).success(render);
        }
      }
    };
  }]);

});