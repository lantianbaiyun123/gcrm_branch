define(["app"],function(t){t.registerFactory("Ad",["$http","APP_CONTEXT",function(t,n){return{save:function(o,e){t({method:"post",url:n+"adsolution/content/saveAdsolutionContent",data:JSON.stringify(o)}).then(function(t){e(t.data)})},get:function(o,e){t({method:"get",url:n+"adsolution/content/queryAdContent/"+o.id}).success(function(t){e(t)})},getSingle:function(o,e){t({method:"get",url:n+"adsolution/content/queryAdContentById/"+o.id}).success(function(t){e(t)})},deleMaterials:function(o,e){t({method:"get",url:n+"adsolution/content/deleteMaterial/"+o.id}).success(function(t){e(t)})},getBillingModel:function(){return t({method:"get",url:n+"adsolution/content/queryBillModels"})},getContentInfo:function(o){return t({method:"get",url:n+"adsolution/content/queryAdContentById/"+o.id})},cancelAd:function(o){return t({method:"get",url:n+"adapprovalinfo/withdrawAD",params:o})},cancelContent:function(o){return t({method:"get",url:n+"adapprovalinfo/withdrawSingleContent",params:o})},confirmContentSchedule:function(o){return t({method:"get",url:n+"adapprovalinfo/confirmSchedule",params:o})},contentApproval:function(o,e){t({method:"post",url:n+"adsolution/content/submitAdcontentToApproval/"+o.adSolutionContent.id+"/"+o.approvalType,data:JSON.stringify(o)}).then(function(t){e(t.data)})},contentTerminate:function(o){return t({method:"post",url:n+"adsolution/content/stopToAdContent/"+o.id})},reSchedule:function(o){return t({method:"post",url:n+"process/reStartBidding/"+o.id})},findContactPO:function(o){return t({method:"post",url:n+"adbaseinfo/findContractPo/"+o.id})},findContractNumberPo:function(o){return t({method:"post",url:n+"adbaseinfo/findContractNumberPo/"+o.id+"/"+o.contractNumber})},revokeContract:function(o){return t({method:"post",url:n+"adbaseinfo/revokeContract/"+o.id})},createOtherContract:function(o){return t({method:"post",url:n+"adbaseinfo/createOtherContract/"+o.id+"/"+o.contractNumber+"/"+o.contractType})},createSingleContract:function(o){return t({method:"post",url:n+"adbaseinfo/createSingleContract/"+o.id})},overdue:function(o){return t({method:"post",url:n+"adapprovalinfo/contractOverdue?id="+o.id})},press:function(o){return t({method:"post",url:n+"adbaseinfo/remind/"+o.id})},modifyAd:function(o){return t({method:"post",url:n+"adbaseinfo/modifyAdSoulutionStatus/"+o.id})},changeAdSolution:function(o){return t({method:"post",url:n+"adapprovalinfo/changeAdSolution/"+o.id})},cancelChangeAdSolution:function(o){return t({method:"post",url:n+"adapprovalinfo/cancelChangeAdSolution/"+o.id})},changePO:function(o){return t({method:"post",url:n+"adbaseinfo/changePo/"+o.adcontentId+"/"+o.contractNumber})},queryInsertDates:function(n){return t({method:"post",url:"/gcrm/occupation/queryInsertDates",data:JSON.stringify(n)})}}}])});