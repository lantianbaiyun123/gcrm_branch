define(["app","../_services/http/Material","../_filters/RecordOperateTypeFilter"],function(e){e.registerFactory("MaterialModifyRecord",["$modal","$log","$timeout","$filter","Modal","Material","STATIC_DIR",function(e,t,o,r,a,l,i){return{show:function(t,o,r){var n={title:"RECORD_MODIFY_RECORD"};o=angular.extend(n,o);var d=function(t){if(200===t.code){o.modalDatas=t.data;var l=e.open({templateUrl:i+"app/record/modifyRecordModal.tpl.html",controller:"CtrlModal",windowClass:"modification-record-modal",resolve:{opts:function(){return o}}});l.result.then(function(e){"ok"===e&&r()})}else{var n="["+t.code+"]"+t.message;a.alert({content:n})}};t&&l.modifyRecord({materialApplyId:t}).success(d)}}}])});