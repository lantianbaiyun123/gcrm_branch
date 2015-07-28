define([ 'app',
  '../_services/http/Material',
  '../_filters/ApprovalResultFilter',
  '../_filters/DatePeriodFilter'
 ], function ( app ) {
  app.registerFactory('MaterialApprovalRecord', [
    '$modal',
    '$timeout',
    '$filter',
    '$q',
    'Modal',
    'Material',
    'STATIC_DIR',
  function ( $modal, $timeout, $filter, $q, Modal, Material, STATIC_DIR) {
    var optsDefault = {
      title: 'RECORD_APPROVAL_RECORD'
    };
    return {
      show: function ( materialId, opts, cbfn) {
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
        if (materialId) {
          Material.approvalRecord({
            materialApplyId: materialId
          }).success(render);
        }
      }
    };
  }]);
});