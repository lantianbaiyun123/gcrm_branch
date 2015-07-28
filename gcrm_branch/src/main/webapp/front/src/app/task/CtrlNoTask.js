/**
 * [CtrlNoTask description]
 * this is controller for feature 'noTasks'
 */
define(['app'
    ],
function (app) {
    app.registerController('CtrlNoTask', ['$scope', '$state', '$stateParams', '$window', '$timeout', 'Modal', 'Task',
        function ($scope, $state, $stateParams, $window, $timeout, Modal, Task) {
            $scope.taskRecord.adSolutionId =  null;   //方案ID
            $scope.taskRecord.activityId = null;
            $scope.updateTask(function () {
                $scope.goNextTask();
            });
        }
    ]);
});