define([
  'app'
], function(app) {
  app.registerFactory('PublishPrice', ['$http', 'APP_CONTEXT',
    function($http, APP_CONTEXT) {
      return {
        post: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/queryQuotationPattern/',
            data: angular.toJson(paramObj)
          });
        }
      };
    }
  ]);
});
