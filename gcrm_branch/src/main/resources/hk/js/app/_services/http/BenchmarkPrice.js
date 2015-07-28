define(["app"],function(t){t.registerFactory("BenchmarkPrice",["$http","APP_CONTEXT",function(t,o){return{getList:function(e){return t({method:"post",url:o+"quote/findQuotationMainPage",data:angular.toJson(e)})},getDetail:function(e){return t({method:"get",url:o+"quote/findQuotationVOById",params:e})},checkConflict:function(e){return t({method:"post",url:o+"quote/checkConflict",data:angular.toJson(e)})},save:function(e){return t({method:"post",url:o+"quote/submitQuotation",data:angular.toJson(e)})},withdraw:function(e){return t({method:"get",url:o+"quote/cancelQuotationApprove",params:e})},nullify:function(e){return t({method:"get",url:o+"quote/cancelQuotation",params:e})},sendReminder:function(e){return t({method:"get",url:o+"quote/remind/"+e.quotationMainId})},currentList:function(e){return t({method:"post",url:o+"quote/findCurrentQuotation",data:angular.toJson(e)})},currentPlatform:function(){return t({method:"get",url:o+"quote/findBusinessTypeByCurrentUser"})},getApprovalInfo:function(e){return t({method:"get",url:o+"qouteapprovalinfo/view/"+e.id+"/"+e.activityId,params:e})},submitApproval:function(e){return t({method:"post",url:o+"quote/approval/saveApprovalRecord",data:angular.toJson(e)})},approvalRecord:function(e){return t({method:"get",url:o+"quote/findApproveRecord",params:e})},modifyRecord:function(e){return t({method:"get",url:o+"quote/modifyRecord",params:e})},getBusinessType:function(){return t({method:"get",url:o+"quote/findBusinessType"})}}}])});