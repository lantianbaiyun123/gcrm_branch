define([
    'app'
], function (app) {


app.registerFactory('Industry', ['$http', 'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      getIndustryTypes: function (paramObj, cbfn) {
        return $http({
            method: 'get',
            // url: '/data/industryType.json'
            url: APP_CONTEXT + 'quote/findIndustryType'
        });
      }
    };
  }
]);


});