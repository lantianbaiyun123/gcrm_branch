define([
  'app',
  '../ServiceTaskHttp'
], function (app) {
  app.registerFactory('PubApplyApproval', [
    '$stateParams',
    'TaskHttp',
    'Modal',
  function ($stateParams, TaskHttp, Modal) {
    return {
      init: function (scope) {
        scope.form = {};
        // scope.taskRecord.foreignKey =  28;
        // scope.taskRecord.activityId = '5559751_act3';
        scope.taskRecord.foreignKey =  $stateParams.onlineApplyId;
        scope.taskRecord.activityId = $stateParams.activityId;
        scope.taskRecord.processId = $stateParams.processId;
        scope.taskRecord.type = 'approval';
        scope.applyId = scope.taskRecord.foreignKey;

        if (scope.taskRecord.foreignKey && scope.taskRecord.activityId) {
          this.getInfo(scope);
        } else {
          scope.updateTask(true);
        }
      },
      getInfo: function (scope) {
        var taskRecord = scope.taskRecord;
        TaskHttp.pubApplyApprovalInfo({
          onlineApplyId: taskRecord.foreignKey,
          activityId: taskRecord.activityId
          // processId: taskRecord.processId
        }).success(function (response) {
          if (response.code === 200) {
            scope.v = response.data;
            scope.updateTask();
          } else if ( response.code > 250 ) {
            var msg = '[' + response.code + ']' + response.message;
            Modal.alert({content: msg});
          } else {
            scope.updateTask();
          }
        });
      },
      submitApproval: function (submitData) {
        return TaskHttp.pubApplyApprovalSubmit(submitData);
      }
    };
  }]);
});
