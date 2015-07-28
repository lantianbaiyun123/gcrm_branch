define([
    'app'
], function (app) {
    app.registerFactory('BillModel', ['$http','APP_CONTEXT',
        function ($http,APP_CONTEXT) {
            return {
                get: function (paramObj, cbfn) {
                    $http({
                        method: 'get',
                        url: APP_CONTEXT+'adsolution/content/queryBillModels'
                    }).success(function (result) {
                        cbfn(result);
                    });
                }
            };
        }
    ]);
});