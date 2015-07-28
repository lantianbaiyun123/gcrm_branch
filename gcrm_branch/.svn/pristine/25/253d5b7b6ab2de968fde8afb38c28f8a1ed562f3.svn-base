define([
    'app'
], function (app) {
    app.registerFactory('Contract', ['$http',
        function ($http) {
            return {
                get: function (paramObj, cbfn) {
                    $http({
                        method: "get",
                        // url: "../resources/data/contractSuggest.json",
                        url: '/gcrm/adbaseinfo/contractSuggest',
                        params: paramObj
                    }).then(function (response) {
                        cbfn(response.data.data);
                    });
                },
                getSuggestPromise: function (paramObj) {
                    return $http({
                        method: "get",
                        // url: "../resources/data/contractSuggest.json",
                        url: '/gcrm/adbaseinfo/contractSuggest',
                        params: paramObj
                    }).then(function (response) {
                        return response.data.data;
                    });
                }
            };
        }
    ]);
});