define([
  'app'
], function (app) {
  app.registerFactory('SiteByProduct', ['$http','APP_CONTEXT',
    function ($http,APP_CONTEXT) {
      return {
        get: function (paramObj, cbfn) {
          $http({
            method: 'get',
            url: APP_CONTEXT+'adsolution/content/querySiteByProductId/',
            params: paramObj
          }).success(function (result) {
            cbfn(result);
          });
        },

        /**
         * 根据投放平台id获取"站点/区域"列表，
         * @param paramObj: {platformId: 1}
         * @return HttpPromise
         */
        getSiteArea: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT+'quote/siteSuggest',
            params: paramObj
          });
        }
      };
    }
  ]);
});