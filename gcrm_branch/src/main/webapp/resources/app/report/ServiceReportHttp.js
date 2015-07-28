define(['app'], function (app) {
  app.registerService('ReportHttp', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      hao123SiteList: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'adPlatform/17/sites'
          // url: '/data/hao123TraceSiteList.json'
        });
      },
      /**
       * [hao123Trace description]
       * @param  {
          "pageSize":10,
          "pageNumber":1,
          "beginStr":"2014-06-27",
          "endStr":"2014-07-16"
        }
       * @return {[type]}          [description]
       */
      hao123Trace: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'hao123/tracereport/show',
          // url: '/data/hao123Trace.json',
          data: angular.toJson(paramObj)
        });
      },
      /**
       * [hao123TraceExport description]
       * @param  {
          "isQueryAll":true,
          "beginStr":"2014-02-12",
          "endStr":"2014-08-30"
        }
       * @return {[type]}          [description]
       */
      hao123TraceExport: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'hao123/tracereport/download',
          // url: '/data/hao123Trace.json',
          param: paramObj
        });
      }
    };
  }]);
});