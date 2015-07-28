define([
  'app',
  './ServicePubApplyApproval',
  '../../record/PubApplyApprovalRecord',
  '../../_directives/periodLabel'
], function (app) {
  app.registerController('CtrlPubApplyApproval', [
    '$scope',
    '$filter',
    'PageSet',
    'PubApplyApproval',
    'PubApplyApprovalRecord',
    'Modal',
  function ($scope, $filter, PageSet, PubApplyApproval, PubApplyApprovalRecord, Modal) {
    PageSet.set({
      activeIndex:5,
      siteName:'task'
    });

    PubApplyApproval.init($scope);

    $scope.modalApprovalRecord = function () {
      PubApplyApprovalRecord.show({
        applyId: $scope.applyId
      });
    };

    $scope.submitApproval = function () {

      if (!checkBeforeSubmit()) {
        return false;
      }

      var submitData = packApprovalData();
      var httpPromise = PubApplyApproval.submitApproval(submitData);
      httpPromise.success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            title: $filter('translate')('APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS'),
            content: $filter('translate')('APPROVAL_SYSTEM_JUMP'),
            timeOut: 3000
          }, function () {
            $scope.goNextTask();
          });
        }
      });
      httpPromise['finally'](function (result) {
        $scope.submitDisabble = false;
      });
    };

    function checkBeforeSubmit() {
      $scope.submitDisabble = true;   //禁用提交按钮
      $scope.checkApprovalStatus = true;
      //check审核意见、说明
      if ( $scope.form.approvalStatus !== 1 &&
           $scope.form.approvalStatus !== 0 ) {
        $scope.submitDisabble = false;
        return false;
      } else if ( $scope.form.approvalStatus === 0 ) {
        $scope.checkApprovalSuggestion = true;
        if(!$scope.form.approvalSuggestion) {
          $scope.submitDisabble = false;
          return false;
        }
      }

      return true;
    }

    function packApprovalData() {
      var submitData = {
        adContentApplyId : $scope.applyId,
        taskId: $scope.taskRecord.taskId,
        activityId: $scope.taskRecord.activityId,
        actDefId: $scope.taskRecord.actDefId,
        processId: $scope.taskRecord.processId,
        approvalStatus: $scope.form.approvalStatus,
        approvalSuggestion: $scope.form.approvalSuggestion
      };

      return submitData;
    }
  }]);
});