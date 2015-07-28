/**
 * [CtrlMaterialApproval description]
 * this is controller for feature 'materialApproval'
 */
define([
  'app',
  '../_common/ytCommon',
  '../_services/PageSet',
  '../_services/http/Material',
  '../_filters/MaterialApprovalStatusFilter',
  '../_filters/PositionFilter',
  '../_filters/AdSolutionStatusFilter',
  '../_directives/periodLabel',
  '../record/MaterialApprovalRecord',
  '../record/MaterialModifyRecord'
], function (app) {
app.registerController('CtrlMaterialApproval', [
    '$scope',
    '$state',
    '$stateParams',
    '$q',
    '$log',
    '$filter',
    'PageSet',
    'Modal',
    'Material',
    'MaterialApprovalRecord',
    'MaterialModifyRecord',
  function ($scope, $state, $stateParams, $q, $log, $filter, PageSet, Modal, Material, MaterialApprovalRecord, MaterialModifyRecord) {
    // controller code here
    PageSet.set({activeIndex:5,siteName:'task'});

    $scope.taskRecord.materialId =  $stateParams.materialId;
    $scope.taskRecord.foreignKey =  $stateParams.materialId;
    $scope.taskRecord.activityId = $stateParams.activityId;
    $scope.taskRecord.type = 'approval';

    $scope.submitApproval = function () {
      $scope.submitDisabble = true;   //禁用提交按钮
      $scope.checkApprovalStatus = true;
      //check审核意见、说明
      if ( $scope.form.approvalStatus !== 1 &&
           $scope.form.approvalStatus !== 0
      ) {
          return false;
      } else if( $scope.form.approvalStatus === 0) {
        $scope.checkApprovalSuggestion = true;
        if(!$scope.form.approvalSuggestion) {
          return false;
        }
      }

      var submitData = packApprovalData();

      var httpPromise = Material.submitApproval(submitData);
      httpPromise.success(function (response) {
        Modal.success({
          title: $filter('translate')('APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS'),
          content: $filter('translate')('APPROVAL_SYSTEM_JUMP'),
          timeOut: 3000
        }, function () {
          $scope.goNextTask();
        });
      });
      httpPromise['finally'](function (result) {
        $scope.submitDisabble = false;
      });
    };

    getApprovalInfo();

    $scope.modalApprovalRecord = function () {
      MaterialApprovalRecord.show($scope.taskRecord.materialId, {windowClass: 'material-approval-record'});
    };

    $scope.modalModifyRecord = function () {
      MaterialModifyRecord.show($scope.taskRecord.materialId, {windowClass: 'matterial-modify-record'});
    };

    function getApprovalInfo () {
      Material.getApprovalInfo({
        id: $scope.taskRecord.foreignKey,
        activityId: $scope.taskRecord.activityId
      }).success(function (response) {
        if ( response.code === 200 ) {
          $scope.approvalInfo = response.data;
          $scope.updateTask();
        } else if (response.code === 200 ) {
          var msg = '[' + response.code + ']' + response.message + '\n';
          Modal.alert({content: msg});
        }
      });
    }

    function packApprovalData () {
      var submitData = {
        adMaterialApplyId : $scope.taskRecord.materialId,
        taskId: $scope.taskRecord.taskId,
        activityId: $scope.taskRecord.activityId,
        approvalStatus: $scope.form.approvalStatus,
        approvalSuggestion: $scope.form.approvalSuggestion,
        processId: $scope.taskRecord.processId,
        actDefId: $scope.taskRecord.actDefId
      };

      return submitData;
    }
  }]);
});