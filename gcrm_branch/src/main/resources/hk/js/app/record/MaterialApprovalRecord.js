define(["app","../_services/http/Material","../_filters/ApprovalResultFilter","../_filters/DatePeriodFilter"],function(e){e.registerFactory("MaterialApprovalRecord",["$modal","$timeout","$filter","$q","Modal","Material","STATIC_DIR",function(e,r,t,a,l,o,i){var n={title:"RECORD_APPROVAL_RECORD"};return{show:function(r,t,a){t=angular.extend(n,t);var p=function(r){if(200===r.code){t.modalDatas=r.data;var o=e.open({templateUrl:i+"app/record/approvalRecordModal.tpl.html",controller:"CtrlModal",windowClass:"approval-record-modal",resolve:{opts:function(){return t}}});o.result.then(function(e){"ok"===e&&a()})}else{var n="["+r.code+"]"+r.message;l.alert({content:n})}};r&&o.approvalRecord({materialApplyId:r}).success(p)}}}])});