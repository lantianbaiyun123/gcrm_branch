define(["app"],function(e){e.registerFactory("Schedules",["$http","APP_CONTEXT",function(e,t){return{getList:function(u){return e({method:"post",url:t+"schedule/query",data:angular.toJson(u)})},getDetail:function(u,n){e({method:"get",url:t+"schedule/findSchedule/"+u.scheduleId}).then(function(e){n(e.data)})},getScheduleInfo:function(u){return e({method:"get",url:t+"schedule/queryScheduleDate",params:u})},validateSchedule:function(u){return e({method:"post",url:t+"schedule/validateScheduleDate",data:JSON.stringify(u)})},submitSchedule:function(u){return e({method:"post",url:t+"schedule/saveScheduleDate",data:JSON.stringify(u)})}}}])});