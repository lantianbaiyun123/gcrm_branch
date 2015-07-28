define([
  'app',
  './ServiceReportHttp'
], function (app) {
  app.registerService('Report', [
    'ReportHttp',
    '$window',
    'APP_CONTEXT',
  function (ReportHttp, $window, APP_CONTEXT) {
    return {
      initHao123: function (scope) {
        ReportHttp.hao123SiteList().success(function (response) {
          if (response.code === 200 && response.data) {
            scope.siteList = response.data;
            for (var i = scope.siteList.length - 1; i >= 0; i--) {
              scope.siteList[i].i18nName = scope.siteList[i].siteName;
              scope.siteList[i].value = scope.siteList[i].id;
            }
          }
        });
      },
      hao123TraceQuery: function (paramObj) {
        return ReportHttp.hao123Trace(paramObj);
      },
      hao123TraceExport: function (paramObj) {
        paramObj.queryAll = true;
        var
          params = [],
          url  = APP_CONTEXT + 'hao123/tracereport/download?';

        for ( var key in paramObj ) {
          //所有参数不可为空
          if (paramObj[key]) {
            params.push(key + '=' + paramObj[key]);
          }
        }
        url = url + params.join('&');
        // $log.info(url);
        $window.open(url, '_blank');
        // return ReportHttp.hao123TraceExport(paramObj);
      }
    };
  }]);
});