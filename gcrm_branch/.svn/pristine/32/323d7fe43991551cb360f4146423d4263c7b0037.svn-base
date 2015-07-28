define([
  'app'
], function (app) {
  app.registerFactory('Schedules', ['$http', 'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        /**
         * 获取排期单列表
         * @param  {object} paramObj [description]
         * @return {http promise} 
         */
        getList: function ( paramObj ) {
          return $http({
            method: "post",
            url: APP_CONTEXT + 'schedule/query',
            data: angular.toJson(paramObj)
          });
        },
        getDetail: function (paramObj, cbfn) {
          $http({
            method: "get",
            url: APP_CONTEXT + 'schedule/findSchedule/' + paramObj.scheduleId
          }).then(function (response) {
            cbfn(response.data);
          });
        },
        getScheduleInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'schedule/queryScheduleDate',
            // url: '/data/scheduleInfo.json',
            // data: JSON.stringify(paramObj)
            params: paramObj
          });
        },
        validateSchedule: function (paramObj) {
          return $http({
            // method: 'get',
            method: 'post',
            // url: '/data/scheduleValidateWarning.json',
            // url: '/data/scheduleValidateSuspend.json',
            // url: '/data/scheduleValidateSuccess.json',
            url: APP_CONTEXT + 'schedule/validateScheduleDate',
            data: JSON.stringify(paramObj)
          });
        },
        submitSchedule: function (paramObj) {
          return $http({
            method: 'post',
            // url: '../resources/data/scheduleSubmit.json',
            url: APP_CONTEXT + 'schedule/saveScheduleDate',
            data: JSON.stringify(paramObj)
            // params: paramObj
          });
        }
      };
    }
  ]);
});