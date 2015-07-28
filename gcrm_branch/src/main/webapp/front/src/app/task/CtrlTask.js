/**
 * [CtrlTask description]
 * this is controller for feature 'task'
 */
define([
  'app',
  '../_common/ytCommon',
  '../_services/PageSet',
  '../_services/http/Task',
  '../_directives/ytPlaceholder',
  '../_directives/ytInnerHeight',
  '../_directives/ytScrollfix'
        // 'css!./task'
], function (app) {
  app.registerController('CtrlTask', [
    '$scope',
    '$rootScope',
    '$state',
    '$stateParams',
    '$window',
    '$document',
    '$timeout',
    '$log',
    '$filter',
    '$q',
    'PageSet',
    'Modal',
    'Task',
  function ($scope, $rootScope, $state, $stateParams, $window, $document, $timeout, $log, $filter, $q, PageSet, Modal, Task) {
    var stateMap = {
      approval: 'task.facade.approval',
      schedule: 'task.facade.schedule',
      quote: 'task.facade.benchmarkPriceApproval',
      material: 'task.facade.materialApproval',
      customer: 'task.facade.customerApproval',
      onlineApply: 'task.facade.pubApplyApproval'
    };

    $scope.taskRecord = {};    //当前task
    $scope.nextTask = {};    //下一条待办任务
    $scope.otherFirst = {};   //其他类第一条待办
    $scope.myTask = [];
    // $scope.taskRecord = {};
    $scope.taskRecord.type = '';
    $scope.taskRecord.activityArrivedTime = '';
    $scope.taskRecord.autoNext = false;
    $scope.tasksReady = false;
    $scope.taskQuery = {
      queryText: '',
      taskType: 'approval',
      types: {
        approval: {
          name: $filter('translate')('TASK_TYPE_APPROVAL')
        },
        operation: {
          name: $filter('translate')('TASK_TYPE_OPERATE')
        }
      }
    };

    $scope.updateTask = function (goNext) {
      // var deferred = $q.defer();
      $scope.tasksReady = false;
      $scope.nextTask.foreignKey = null;
      $scope.nextTask.activityId = null;
      $scope.nextTask.type = null;
      Task.taskGet({}, function (response) {
        if(response.code === 200) {
          $rootScope.myTaskCount = response.data.length;
          taskMerge($scope.myTask, response.data);
          clearSelectedClass();
          setTask();
          $scope.tasksReady = true;
          // deffered.resolve();
          if (goNext) {
            $scope.goNextTask();
          }
        } else {
          var msg = '[' + response.code + ']' + response.message;
          Modal.alert({content: msg});
          // deffered.resolve();
        }
      });
      // return deffered.promise;
    };

    $scope.taskQuery.search = function (item) {
      try {
        var ifStartUser = false,
            ifProcessName = false,
            ifNumber = false;
        if ( item.startUser ) {
          ifStartUser = item.startUser.indexOf($scope.taskQuery.queryText || '') !== -1;
        }

        if ( item.processName ) {
          ifProcessName = item.processName.indexOf($scope.taskQuery.queryText || '') !== -1;
        }

        if ( item.number ) {
          ifNumber = item.number.indexOf($scope.taskQuery.queryText || '') !== -1;
        }

        return !!( (ifStartUser || ifProcessName || ifNumber) &&
                    item.type.indexOf($scope.taskQuery.taskType) !== -1 );
      } catch (e) {
        return true;
      }
    };

    $scope.goTask = function (item) {
      var state = 'task.facade.noTask';
      if(item.subtype) {
        state = stateMap[item.subtype];
      }
      var stateParams = {};
      stateParams[item.foreignName] = item.foreignKey;
      stateParams.activityId = item.activityId;
      //排期、客户审核需要processId
      stateParams.processId = item.processId;
      $scope.taskQuery.queryText = '';
      $state.go(state, stateParams);
    };

    $scope.goNextTask = function () {

      if($scope.nextTask.type) {
        $scope.goTask($scope.nextTask);
      } else {
        $state.go('task.facade.noTask');
      }
    };

    $scope.goFirst = function (taskType) {
      if( taskType !== $scope.taskRecord.type ) {
        $scope.goTask($scope.otherFirst);
      }
    };

    function taskMerge (oldTasks, newTasks) {
      if( !newTasks.length) {
        oldTasks.splice(0);
      } else if (!oldTasks.length) {
        for (var k=0;k<newTasks.length;k++) {
          oldTasks.push(newTasks[k]);
        }
      } else {
        var i=0,
            j=0;
        while (i<oldTasks.length && j<newTasks.length) {
          var left = oldTasks[i].activityArrivedTime,
            right = newTasks[j].activityArrivedTime;
          if (left > right) {
            //左侧当前去掉,
            oldTasks.splice(i,1);
          } else if (left === right) {
            //考虑第二排序条件
            var leftSub = oldTasks[i].activityId,
              rightSub = newTasks[j].activityId;
            if (leftSub < rightSub) {
              oldTasks.splice(i,1);
            } else if ( leftSub === rightSub ) {
              i++;
              j++;
            } else if ( leftSub > rightSub ) {
              oldTasks.splice(i,0, newTasks[j]);
              i++;
              j++;
            }
          } else if (left < right) {
            //右侧插入左侧
            oldTasks.splice(i,0, newTasks[j]);
            i++;
            j++;
          }
        }

        if ( i === oldTasks.length && j < newTasks.length) {
          var c = newTasks.slice(j);
          for (;j<newTasks.length;j++) {
            oldTasks.push(newTasks[j]);
          }
        } else

        if ( i < oldTasks.length && j === newTasks.length) {
          oldTasks.splice(i);
        }
      }
    }

    function setTask () {
      var currentIndex = -1,
          taskLength = $scope.myTask.length,
          noNext = true,
          noOtherFirst = true;
      if ($scope.taskRecord.foreignKey && $scope.taskRecord.activityId) {
        for (var i = 0; i < taskLength; i++) {
          if ( $scope.myTask[i].activityId === $scope.taskRecord.activityId) {
            setCurrentTask($scope.myTask[i]);
            $scope.taskRecord.index = i; //记住当前任务在当前任务列表中的位置
            currentIndex = i;
            $scope.myTask[i].ngClass = 'selected';
            break;
          }
          if ( noOtherFirst && $scope.myTask[i].type !== $scope.taskRecord.type ) {
            $scope.otherFirst = $scope.myTask[i];
            noOtherFirst = false;
          }
        }

        //向下查找同类待办任务，同时设置异类的第1个待办
        for(var j=currentIndex+1; j<taskLength; j++) {
          if ( $scope.myTask[j].type === $scope.taskRecord.type &&
              $scope.myTask[j].activityArrivedTime <= $scope.taskRecord.activityArrivedTime ) {
            if ( noNext ) {
              setNextTask($scope.myTask[j]);
              noNext = false;
            }
          } else if ( noOtherFirst &&
                      $scope.myTask[j].type !== $scope.taskRecord.type ) {
            $scope.otherFirst = $scope.myTask[j];
            noOtherFirst = false;
          }

          //如果下一待办和异类第一项待办均已设置，跳出循环
          if (!noNext && !noOtherFirst) {
            return;
          }
        }
      }

      //如向下查找无同类待办，从头开始查找待办
      if( noNext &&
        $scope.taskRecord.type
      ) {
        for ( var k=0; k < taskLength; k++) {
          if ( $scope.myTask[k].type === $scope.taskRecord.type &&
               $scope.myTask[k].activityId !== $scope.taskRecord.activityId
          ) {
            setNextTask($scope.myTask[k]);
            noNext = false;
          } else if ( noOtherFirst &&
                      $scope.myTask[k].type !== $scope.taskRecord.type
          ) {
            $scope.otherFirst = $scope.myTask[k];
            noOtherFirst = false;
          }

          if ( !noNext && !noOtherFirst) {
            return;
          }
        }
      }
    }

    function clearSelectedClass () {
      var length = $scope.myTask.length;
      for ( var i = 0; i < length; i++ ) {
        if ( $scope.myTask[i].ngClass ) {
          $scope.myTask[i].ngClass = '';
          break;
        }
      }
    }

    function setCurrentTask (task) {
      $scope.taskRecord.activityId = task.activityId;
      $scope.taskRecord.taskId =  task.activityId;
      $scope.taskRecord.processId =  task.processId;
      $scope.taskRecord.taskName =  task.activityName;
      $scope.taskRecord.actDefId =  task.actDefId;
      $scope.taskRecord.foreignName =  task.foreignName;
      $scope.taskRecord.type =  task.type;
      $scope.taskRecord.subtype =  task.subtype;
      $scope.taskRecord.activityArrivedTime =  task.activityArrivedTime;

      $scope.taskQuery.types[task.type].active = true;
    }

    function setNextTask (task) {
      $scope.nextTask.type = task.type;
      $scope.nextTask[task.foreignName] = task.foreignKey;
      $scope.nextTask.foreignName = task.foreignName;
      $scope.nextTask.foreignKey = task.foreignKey;
      $scope.nextTask.activityId = task.activityId;
      $scope.nextTask.processId = task.processId;
      $scope.nextTask.subtype = task.subtype;
    }
  }]);
});