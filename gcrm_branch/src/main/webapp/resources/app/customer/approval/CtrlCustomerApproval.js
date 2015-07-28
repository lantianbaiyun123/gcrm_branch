/**
 * [CtrlCustomerApproval]
 */
define([
  'app',
  '../../_services/http/Customer',
  '../../record/CustomerApprovalRecord',
  '../../record/CustomerModifyRecord'
], function (app) {
  app.registerController('CtrlCustomerApproval', [
    '$scope',
    '$state',
    '$stateParams',
    '$q',
    '$log',
    '$filter',
    'PageSet',
    'Modal',
    'Customer',
    'CustomerApprovalRecord',
    'CustomerModifyRecord',
  function ($scope, $state, $stateParams, $q, $log, $filter, PageSet, Modal, Customer, CustomerApprovalRecord, CustomerModifyRecord) {
    PageSet.set({activeIndex:5,siteName:'task'});

    $scope.taskRecord.customerId =  $stateParams.customerId;
    $scope.taskRecord.foreignKey =  $stateParams.customerId;
    $scope.taskRecord.activityId = $stateParams.activityId;
    $scope.taskRecord.type = 'approval';
    $scope.form = {};
    $scope.plus = {};

    $scope.submitApproval = function () {
      $scope.submitDisabble = true;   //禁用提交按钮
      $scope.checkApprovalStatus = true;
      $scope.checkApprovalPlus = true;
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


      //校验加签
      if ( $scope.approvalInfo.addPlusOperate &&
           typeof $scope.plus.approvalPlus === 'undefined' ) {
        $scope.submitDisabble = false;
        return false;
      }

      var submitData = packApprovalData();

      var httpPromise = Customer.submitApproval(submitData);
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
      CustomerApprovalRecord.show($scope.taskRecord.customerId, {windowClass: 'customer-approval-record'});
    };

    $scope.modalModifyRecord = function () {
      CustomerModifyRecord.show($scope.taskRecord.customerId, {windowClass: 'customer-modify-record'});
    };

    function getApprovalInfo () {
      Customer.getApprovalInfo({
        customerId: $scope.taskRecord.foreignKey,
        activityId: $scope.taskRecord.activityId,
        processId: $scope.taskRecord.processId
      }).success(function (response) {
        if ( response.code === 200 ) {
          $scope.approvalInfo = response.data;
          $scope.customer = $scope.approvalInfo.customer;
          if ( $scope.customer.businessType ) {
            $scope.customer.businessTypes = $scope.customer.businessType.split(',');
          }
          $scope.contacts = $scope.approvalInfo.contacts;
          $scope.opportunityView = $scope.approvalInfo.opportunityView;
          $scope.qualification = $scope.approvalInfo.qualification;
          prepareAttachment($scope.approvalInfo.attachments);
          $scope.updateTask();
        } else if (response.code === 200 ) {
          var msg = '[' + response.code + ']' + response.message + '\n';
          Modal.alert({content: msg});
        }
      });
    }

    function prepareAttachment (attachments) {
      var length = attachments.length;
      if ( length ) {
        $scope.attachment = {};
        for ( var i = 0; i < length; i++ ) {
          var type = attachments[i].type;
          if ( !$scope.attachment[type] ) {
            $scope.attachment[type] = [];
          }
          $scope.attachment[type].push(attachments[i]);
        }
      }
    }

    function packApprovalData () {
      var submitData = {
        customerId : $scope.taskRecord.customerId,
        taskId: $scope.taskRecord.taskId,
        activityId: $scope.taskRecord.activityId,
        processId: $scope.taskRecord.processId,
        actDefId: $scope.taskRecord.actDefId,
        approvalStatus: $scope.form.approvalStatus,
        approvalSuggestion: $scope.form.approvalSuggestion,
        plusSign: $scope.plus.approvalPlus
      };

      return submitData;
    }
  }]);
});