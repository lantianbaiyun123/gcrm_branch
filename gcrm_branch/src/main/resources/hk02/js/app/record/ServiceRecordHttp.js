define(["app"],function(p){p.registerFactory("RecordHttp",["$http","APP_CONTEXT",function(p,t){return{pubApplyApproval:function(n){return p({method:"get",url:t+"adcontent/applyOnline/findApproveRecords/"+n.applyId,params:n})}}}])});