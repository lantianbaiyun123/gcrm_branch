define([
    'app'
], function (app) {
    app.registerFactory('AreaByChannel', ['$http','APP_CONTEXT',
        function ($http,APP_CONTEXT) {
            return {
                get: function (paramObj, cbfn) {
                    $http({
                        method: 'get',
                        url: APP_CONTEXT+'adsolution/content/queryPositionByParentId/',
                        params: paramObj
                    }).success(function (result) {
                        cbfn(result);
                    });
                }
            };
        }
    ]);
});