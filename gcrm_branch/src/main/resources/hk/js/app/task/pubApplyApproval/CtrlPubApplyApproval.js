define(["app","./ServicePubApplyApproval","../../record/PubApplyApprovalRecord"],function(t){t.registerController("CtrlPubApplyApproval",["$scope","PageSet","PubApplyApproval","PubApplyApprovalRecord",function(t,a,p,o){function e(){return t.submitDisabble=!0,t.checkApprovalStatus=!0,1!==t.form.approvalStatus&&0!==t.form.approvalStatus?(t.submitDisabble=!1,!1):0!==t.form.approvalStatus||(t.checkApprovalSuggestion=!0,t.form.approvalSuggestion)?!0:(t.submitDisabble=!1,!1)}function r(){var a={adContentApplyId:t.applyId,taskId:t.taskRecord.taskId,activityId:t.taskRecord.activityId,actDefId:t.taskRecord.actDefId,approvalStatus:t.form.approvalStatus,approvalSuggestion:t.form.approvalSuggestion};return a}a.set({activeIndex:5,siteName:"task"}),p.init(t),t.modalApprovalRecord=function(){o.show({applyId:t.applyId})},t.submitApproval=function(){if(!e())return!1;var a=r(),o=p.submitApproval(a);o.success(function(a){200===a.code&&Modal.success({title:$filter("translate")("APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS"),content:$filter("translate")("APPROVAL_SYSTEM_JUMP"),timeOut:3e3},function(){t.goNextTask()})}),o["finally"](function(){t.submitDisabble=!1})}}])});