define([
  'app',
  './ServiceRecordHttp'
 ], function ( app ) {
  app.registerFactory('PubApplyApprovalRecord', [
    '$modal',
    '$q',
    '$filter',
    'Modal',
    'RecordHttp',
    'STATIC_DIR',
  function ( $modal, $q, $filter, Modal, RecordHttp, STATIC_DIR ) {
    var optsDefault = {
      title: 'RECORD_APPROVAL_RECORD'
    };
    return {
      show: function (opts) {
        var defered = $q.defer();
        opts = angular.extend(optsDefault, opts);
        if (opts.applyId) {
          RecordHttp.pubApplyApproval({
            applyId: opts.applyId
          }).success(function (response) {
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
                defered.resolve(result);
              });
            } else if (response.code > 204) {
              var msg = '[' + response.code + ']' + response.message;
              Modal.alert({content: msg});
            }
          });
        }
        return defered.promise;
      }
    };
  }]);
});