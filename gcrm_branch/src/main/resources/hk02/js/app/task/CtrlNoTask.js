define(["app"],function(t){t.registerController("CtrlNoTask",["$scope","$state","$stateParams","$window","$timeout","Modal","Task",function(t){t.taskRecord.adSolutionId=null,t.taskRecord.activityId=null,t.updateTask(function(){t.goNextTask()})}])});