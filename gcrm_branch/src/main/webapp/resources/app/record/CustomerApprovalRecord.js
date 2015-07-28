define([ 'app',
  '../_services/http/Customer',
  '../_filters/ApprovalResultFilter',
  '../_filters/DatePeriodFilter'
 ], function ( app ) {
  app.registerFactory('CustomerApprovalRecord', [
    '$modal',
    '$timeout',
    '$filter',
    '$q',
    'Modal',
    'Customer',
    'STATIC_DIR',
  function ( $modal, $timeout, $filter, $q, Modal, Customer, STATIC_DIR ) {
    var optsDefault = {
      title: 'RECORD_APPROVAL_RECORD'
    };
    return {
      show: function ( customerId, opts, cbfn) {
        opts = angular.extend(optsDefault, opts);
        var render = function (response) {
          if(response.code === 200) {
            opts.modalDatas = response.data;
            var modalInstance = $modal.open({
              templateUrl: STATIC_DIR + 'app/record/approvalRecordModal.tpl.html',
              controller: 'CtrlModal',
              windowClass: 'approval-record-modal',
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
        if (customerId) {
          Customer.approvalRecord({
            customerId: customerId
          }).success(render);
        }
      }
    };
  }]);
});