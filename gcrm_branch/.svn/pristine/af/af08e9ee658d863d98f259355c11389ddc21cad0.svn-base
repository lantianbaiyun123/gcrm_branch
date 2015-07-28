define([ 'app' ], function ( app ) {
  app.registerFactory( 'SolutionModifyRecord', [ '$modal', '$timeout', '$filter', 'AdProgram', 'STATIC_DIR', function ( $modal, $timeout, $filter, AdProgram, STATIC_DIR ) {
    return {
      show: function ( adSolutionId, adContentId, role, opts, cbfn) {
        var optsDefault = {
          title: 'RECORD_MODIFY_RECORD'
        };
        opts = angular.extend(optsDefault, opts);
        var render = function (response) {
            if(response.code === 200) {
                var modalDatas = {};
                angular.forEach(response.data, function (data, index) {
                  var contentKey = data.number || 0;
                  if(!modalDatas[contentKey]) {
                    modalDatas[contentKey] = [];
                  }
                  modalDatas[contentKey].push(data);
                });

                opts.modalDatas = modalDatas;

                var modalInstance = $modal.open({
                    templateUrl: STATIC_DIR + 'app/record/solutionModifyRecordModal.tpl.html',
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
        if ( adContentId ) {
          AdProgram.editRecordGetByContentId({
            id: adContentId,
            role: role
          }).success(render);
        } else {
          AdProgram.editRecordGet({
            id: adSolutionId,
            role: role
          }, render);
        }
      }
    };
  }]);

});