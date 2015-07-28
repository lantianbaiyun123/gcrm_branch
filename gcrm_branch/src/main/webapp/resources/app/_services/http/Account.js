define(['app'], function (app) {
  app.registerFactory('Account', [
    '$http',
    'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        get: function (paramObj, cbfn) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adbaseinfo/accountSuggest',
            params: paramObj
          });
        },
        suggestUser: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'publish/suggestUser',
            // url: '/data/suggestUser.json',
            params: paramObj
          });
        }
      };
    }
  ]);
});