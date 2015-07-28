/**
 * Task 待办事项 (Angular Service)
 *
 * --USAGES--
 *   1.basicSave( paramObj ,cbfn )
 */
define(['app'],
function(app) {
  app.registerFactory('Task', ['$http', 'APP_CONTEXT',
  function( $http, APP_CONTEXT ) {
    return {
      /**
       * [获取待办列表]
       * @param  {[type]} paramObj [description]
       * @param  {[type]} cbfn     [description]
       * @return {[type]}          [description]
       */
      taskGet: function (paramObj, cbfn) {
        $http({
          method: 'get',
          url: APP_CONTEXT + 'process/tasks',
          // url: '../resources/data/tasksByUsername.json',
          params: paramObj
        }).then(function (response) {
          cbfn(response.data);
        });
      },
      /**
       * [待办数量及第1项待办]
       * @param  {[type]} paramObj [description]
       * @return HttpPromise response = {
              "code": 200,
              "data": [
                {
                  "firstAct": {
                    "processId": "1701442_gcrm_pkg_506_prs1",
                    "processName": "广告方案",
                          ...
                          ...
                    "type": "approval",
                    "subtype": "approval",
                    "foreignName": "adSolutionId",
                    "foreignKey": "4"
                    },
                  "count": 100
                }
              ],
            "message": "OK"
          }
       */
      toDoCount: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'process/taskCount',
          params: paramObj
        });
      }
    };
  }]);
});