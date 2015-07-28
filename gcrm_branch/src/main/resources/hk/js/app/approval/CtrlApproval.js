define(["app","../_common/ytCommon","../_services/PageSet","../_services/http/AdProgram","../_filters/RecordOperateTypeFilter","../_filters/PriceTypeFilter","../_filters/AdSolutionTypeFilter","../_filters/AdMaterialIfEmbedCodeFilter","../_filters/BoolValueFilter","../_filters/IndustryTypeFilter","../_filters/urlHttpFilter","../_filters/DatePeriodFilter","../record/SolutionModifyRecord","../record/SolutionApprovalRecord","../resourcePosition/PositionPropDetailModal","../_directives/periodLabel"],function(o){o.registerController("CtrlApproval",["$scope","$state","$stateParams","$timeout","$location","$anchorScroll","$modal","$filter","$log","PageSet","Modal","Utils","AdProgram","SolutionModifyRecord","SolutionApprovalRecord","PositionPropDetailModal",function(o,t,e,a,r,n,i,s,l,d,c,u,p,f,I,S){function v(){var o=0;return 2===m.length&&(o=m[1]),o}function R(){var t={activityId:o.taskRecord.activityId,processId:o.taskRecord.processId,actDefId:o.taskRecord.actDefId,record:{adSolutionId:o.taskRecord.adSolutionId,taskId:o.taskRecord.taskId,approvalStatus:o.form.approvalStatus,approvalSuggestion:o.form.approvalSuggestion},insertRecords:[]};return 2===m.length&&(t.record.adContentId=m[1]),angular.forEach(o.solutionInfo.approvalContentViews,function(e){var a=e.adSolutionContent.id;angular.forEach(e.infoViews,function(e){var r=e.date;angular.forEach(e.insertInfos,function(e){e.conflict=!1,1===e.businessAllow||"cash_leader"===o.solutionInfo.role?(insertRecord={adsolutionContentId:a,insertedAdsolutionContentId:e.adContentId,insertPeriod:r,allowInsert:e.allowInsert},t.insertRecords.push(insertRecord)):0===e.businessAllow&&"pm"!==o.solutionInfo.role&&"cash_leader"!==o.solutionInfo.role&&"country_leader"!==o.solutionInfo.role&&(insertRecord={adsolutionContentId:a,insertedAdsolutionContentId:e.adContentId,insertPeriod:r,allowInsert:0},t.insertRecords.push(insertRecord))})})}),t}d.set({activeIndex:5,siteName:"task"});var m=e.adSolutionId.split("_");e.adSolutionId&&(o.taskRecord.adSolutionId=e.adSolutionId.split("_")[0]),o.taskRecord.foreignKey=e.adSolutionId,o.taskRecord.activityId=e.activityId,o.taskRecord.type="approval",o.solutionInfo=o.solutionInfo||{},o.form=o.form||{},o.form.approvalStatus=o.form.approvalStatus||null,o.form.approvalSuggestion=o.form.approvalSuggestion||"",o.approvalRecords=o.approvalRecords||{},o.editRecords=o.editRecords||[],o.taskRecord.adSolutionId&&o.taskRecord.activityId?p.infoGet({id:o.taskRecord.foreignKey,activityId:o.taskRecord.activityId},function(t){if(200===t.code)o.solutionInfo=t.data,o.taskRecord.autoNext=!1,o.updateTask();else if(205===t.code)if(o.taskRecord.autoNext)o.taskRecord.foreignKey=null,o.taskRecord.adSolutionId=null,o.taskRecord.activityId=null,o.updateTask(!0);else{var e="["+t.code+"]"+t.message+"\n";c.alert({content:e}),o.taskRecord.foreignKey=null,o.taskRecord.adSolutionId=null,o.taskRecord.activityId=null,o.updateTask()}}):o.updateTask(!0),o.modalApprovalRecords=function(){var t=v();I.show(o.taskRecord.adSolutionId,t,{windowClass:"approval-record-modal"})},o.modalEditRecords=function(){var t=v();f.show(o.taskRecord.adSolutionId,t,o.solutionInfo.role,{windowClass:"approval-record-modal"})},o.PositionProp=function(o){var t=angular.copy(o.position),e=o.adSolutionContent;t.positionStr=o.adSolutionContent.channelName,e.areaName&&(t.positionStr=t.positionStr+" - "+e.areaName),e.positionName&&(t.positionStr=t.positionStr+" - "+e.positionName),S.show({modalDatas:t})},o.submitApproval=function(){if(o.checkApprovalStatus=!0,o.submitDisabble=!0,1!==o.form.approvalStatus&&0!==o.form.approvalStatus)return!1;if(0===o.form.approvalStatus&&(o.checkApprovalSuggestion=!0,!o.form.approvalSuggestion))return!1;var t=R(),e=!0;1===~~o.form.approvalStatus&&"pm"!==o.solutionInfo.role&&"country_leader"!==o.solutionInfo.role?(o.checkAllowInsert=!0,angular.forEach(t.insertRecords,function(o){return"undefined"==typeof o.allowInsert?(e=!1,!1):void 0})):t.insertRecords=[],e?p.approvalSubmit(t,function(t){if(200===t.code)o.submitSuccessful=!0,o.taskRecord.autoNext=!0,c.success({title:s("translate")("APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS"),content:s("translate")("APPROVAL_SYSTEM_JUMP"),timeOut:3e3},function(){o.submitSuccessful=!1,o.goNextTask()});else if(205===t.code){var e=JSON.parse(t.message);angular.forEach(e,function(t,e){angular.forEach(o.solutionInfo.approvalContentViews,function(o){o.adSolutionContent.id===~~e&&angular.forEach(t,function(t){angular.forEach(o.infoViews,function(o){angular.forEach(o.insertInfos,function(o){o.adContentId===~~t&&(o.conflict=!0)})})})})}),o.submitDisabble=!1}else o.submitDisabble=!1,o.taskRecord.foreignKey=null,o.taskRecord.adSolutionId=null,o.taskRecord.activityId=null,o.updateTask()}):o.submitDisabble=!1}}])});