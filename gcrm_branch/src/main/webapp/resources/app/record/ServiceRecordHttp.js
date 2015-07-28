define(['app'], function (app) {
  app.registerFactory('RecordHttp', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      pubApplyApproval: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'adcontent/applyOnline/findApproveRecords/' + paramObj.applyId,
          // url: '/data/materialApproveRecord.json',
          params: paramObj
        });
      }
    };
  }]);
});