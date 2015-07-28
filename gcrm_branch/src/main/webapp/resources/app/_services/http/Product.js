define([
  'app'
], function (app) {
  app.registerFactory('Product', ['$http','APP_CONTEXT',
    function ($http,APP_CONTEXT) {
      return {
        get: function (paramObj, cbfn) {
          $http({
            method: 'get',
            url: APP_CONTEXT+'adsolution/content/queryProduct',
            params: paramObj
          }).success(function (result) {
            cbfn(result);
          });
        },
        getAll: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT+'quote/queryPlatform'
            // url: '/data/queryplatform.json'
          });
        }
      };
    }
  ]);
});