define([
    'app'
], function (app) {
    app.registerFactory('ChannelBySite', ['$http','APP_CONTEXT',
        function ($http,APP_CONTEXT) {
            return {
                get: function (paramObj, cbfn) {
                    $http({
                        method: 'get',
                        url: APP_CONTEXT+'adsolution/content/queryPositionBySiteId',
                        params: paramObj
                    }).success(function (result) {
                        cbfn(result);
                    });
                }
            };
        }
    ]);
});