define(['app'], function (app) {
  app.registerFactory('TaskHttp', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      pubApplyApprovalInfo: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'adapprovalinfo/viewOnlineApplyApproval',
          // url: '/data/pubApplyApprovalInfo.json',
          params: paramObj
        });
      },
      pubApplyApprovalSubmit: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'adcontent/applyOnline/approve',
          // url: '/data/pubApplyApprovalInfo.json',
          data: angular.toJson(paramObj)
        });
      }
    };
  }]);
});
