define(["app","../_services/Select2Suggest"],function(t){t.registerFactory("ModalPO",["$modal","$q","STATIC_DIR",function(t,e,o){return{show:function(c){var r=e.defer(),n=t.open({templateUrl:o+"app/ad2/ModalPO.tpl.html",controller:"CtrlModalPO",resolve:{opts:function(){return{type:c.type,customerNumber:c.customerNumber,adSolutionId:c.adSolutionId}}}});return n.result.then(function(t){r.resolve(t)}),r.promise}}}]),t.registerController("CtrlModalPO",["$scope","$modalInstance","opts","Select2Suggest","AdHttp",function(t,e,o,c,r){"direct"===o.type?t.title="AD_SOLUTION_DETAIL_PO_TITLE_EMPTY":"frame"===o.type?t.title="AD_SOLUTION_DETAIL_PO_TITLE_FRAME":"protocol"===o.type&&(t.title="AD_SOLUTION_DETAIL_PO_TITLE_PROTOCOL"),t.optionContract=c.getContractOption({basic:{customerSelected:{data:o.customerNumber}}}),t.po={},t.ok=function(){"direct"===o.type?r.findContractNumberPo({id:o.adSolutionId,contractNumber:t.po.contract.data.number}).success(function(t){200===t.code&&t.data&&e.close({type:o.type,data:t.data})}):r.createOtherContract({id:o.adSolutionId,contractNumber:t.po.contract.data.number,contractType:o.type}).success(function(t){200===t.code&&e.close({type:o.type,data:t.data})})},t.cancel=function(){e.dismiss("cancel")}}])});