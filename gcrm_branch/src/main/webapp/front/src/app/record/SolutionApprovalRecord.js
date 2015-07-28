define([
  'app',
  '../_filters/ApprovalResultFilter',
  '../_filters/DatePeriodFilter'
 ], function ( app ) {
  app.registerFactory( 'SolutionApprovalRecord', [
    '$modal', '$timeout', '$filter', 'AdProgram', '$q', 'STATIC_DIR',
    function ( $modal, $timeout, $filter, AdProgram, $q, STATIC_DIR) {
    var optsDefault = {
      title: 'RECORD_APPROVAL_RECORD'
    };
    return {
      show: function ( adSolutionId, adContentId, opts, cbfn) {
        opts = angular.extend(optsDefault, opts);

        var render = function (response) {
          if(response.code === 200) {
            opts.modalDatas = response.data;
            //每条审核task取插单记录
            angular.forEach(opts.modalDatas, function (record, index) {
                AdProgram.approvalInsertRecordGet({
                  id: record.id
                }, function (response) {
                  if(response.code === 200) {
                    record.insertRecords = response.data;
                    record.insertShow = false;
                  }
                });
            });

            var modalInstance = $modal.open({
                templateUrl: STATIC_DIR + 'app/record/solutionApprovalRecordModal.tpl.html',
                controller: 'CtrlModal',
                windowClass: 'solution-approval-record',
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
        if (adContentId) {
          AdProgram.approvalRecordGetByContentId({
            id: adContentId
          }).success(render);
        } else {
          AdProgram.approvalRecordGet({
            id: adSolutionId
          }, render);
        }
      },
      forContent: function ( adContentId, opts) {
        opts = angular.extend(optsDefault, opts);
        AdProgram.getApprovalRecordForContent({id: adContentId}).success(function (response) {
          opts.modalDatas = response.data;
          //每条审核task取插单记录
          angular.forEach(opts.modalDatas, function (record, index) {
            AdProgram.approvalInsertRecordGet({
              id: record.id
            }, function (response) {
              if(response.code === 200) {
                record.insertRecords = response.data;
                record.insertShow = false;
              }
            });
          });

          var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/record/solutionApprovalRecordModal.tpl.html',
            controller: 'CtrlModal',
            windowClass: opts.windowClass,
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
        });
      }
    };
  }]);

});