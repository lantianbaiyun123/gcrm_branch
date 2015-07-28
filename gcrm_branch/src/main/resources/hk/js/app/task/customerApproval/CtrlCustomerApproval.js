define(["app","../../_services/http/Customer","../../record/CustomerApprovalRecord","../../record/CustomerModifyRecord"],function(t){t.registerController("CtrlCustomerApproval",["$scope","$state","$stateParams","$q","$log","$filter","PageSet","Modal","Customer","CustomerApprovalRecord","CustomerModifyRecord",function(t,o,a,e,s,r,c,p,u,i,n){function l(){u.getApprovalInfo({customerId:t.taskRecord.foreignKey,activityId:t.taskRecord.activityId,processId:t.taskRecord.processId}).success(function(o){if(200===o.code)t.approvalInfo=o.data,t.customer=t.approvalInfo.customer,t.customer.businessType&&(t.customer.businessTypes=t.customer.businessType.split(",")),t.contacts=t.approvalInfo.contacts,t.opportunityView=t.approvalInfo.opportunityView,t.qualification=t.approvalInfo.qualification,d(t.approvalInfo.attachments),t.updateTask(),t.approvalInfo.addPlusOperate&&(t.plus.approvalPlus=!1);else if(200===o.code){var a="["+o.code+"]"+o.message+"\n";p.alert({content:a})}else t.updateTask()})}function d(o){var a=o.length;if(a){t.attachment={};for(var e=0;a>e;e++){var s=o[e].type;t.attachment[s]||(t.attachment[s]=[]),t.attachment[s].push(o[e])}}}function f(){var o={customerId:t.taskRecord.customerId,taskId:t.taskRecord.taskId,activityId:t.taskRecord.activityId,processId:t.taskRecord.processId,actDefId:t.taskRecord.actDefId,approvalStatus:t.form.approvalStatus,approvalSuggestion:t.form.approvalSuggestion,plusSign:t.plus.approvalPlus};return o}c.set({activeIndex:5,siteName:"task"}),t.taskRecord.customerId=a.customerId,t.taskRecord.foreignKey=a.customerId,t.taskRecord.activityId=a.activityId,t.taskRecord.type="approval",t.form={},t.plus={},t.submitApproval=function(){if(t.submitDisabble=!0,t.checkApprovalStatus=!0,t.checkApprovalPlus=!0,1!==t.form.approvalStatus&&0!==t.form.approvalStatus)return t.submitDisabble=!1,!1;if(0===t.form.approvalStatus&&(t.checkApprovalSuggestion=!0,!t.form.approvalSuggestion))return t.submitDisabble=!1,!1;if(t.approvalInfo.addPlusOperate&&"undefined"==typeof t.plus.approvalPlus)return t.submitDisabble=!1,!1;var o=f(),a=u.submitApproval(o);a.success(function(o){200===o.code&&p.success({title:r("translate")("APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS"),content:r("translate")("APPROVAL_SYSTEM_JUMP"),timeOut:3e3},function(){t.goNextTask()})}),a["finally"](function(){t.submitDisabble=!1})},l(),t.modalApprovalRecord=function(){i.show(t.taskRecord.customerId)},t.modalModifyRecord=function(){n.show(t.taskRecord.customerId)}}])});