define(["app"],function(t){t.registerFactory("PersonalInfo",["$http","APP_CONTEXT",function(t,n){return{getDisplay:function(){return t({method:"get",url:n+"personal/findModule"})},getSubmitInfoCustomer:function(){return t({method:"get",url:n+"personal/findCustomer"})},getSubmitInfoAdSolution:function(){return t({method:"get",url:n+"personal/findAdSolution"})},getSubmitInfoQuotation:function(){return t({method:"get",url:n+"personal/findQuotation"})},getSubmitInfoMaterial:function(){return t({method:"get",url:n+"personal/findMaterialApplyContent"})},getOperationInfoCustomer:function(){return t({method:"get",url:n+"personal/findCustomerOperation"})},getOperationInfoAd:function(){return t({method:"get",url:n+"personal/findSolutionOperation"})},getOperationInfoPlatform:function(){return t({method:"get",url:n+"personal/findPlatformByCurrUser"})},getOperationInfoOccupation:function(e){return t({method:"get",url:n+"personal/findSiteOperation/"+e.platformId})},getModuleCount:function(){return t({method:"get",url:n+"personal/findModuleCount/"})}}}])});