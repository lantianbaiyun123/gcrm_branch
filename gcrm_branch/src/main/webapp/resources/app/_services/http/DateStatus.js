define([
    'app'
], function (app) {
    app.registerFactory('DateStatus', ['$http', 'APP_CONTEXT',
        function ($http, APP_CONTEXT) {
            return {
                get: function (paramObj, cbfn) {
                    $http({
                        method: 'post',
                        // url: '../resources/data/DateStatusByFixedPositionIdAndDateFromTo.json',
                        url: '/data/queryDateOccupation.json',
                        // url: APP_CONTEXT + 'occupation/queryDateOccupation',
                        data: JSON.stringify(paramObj)
                    }).then(function (response) {
                        cbfn(response.data);
                    });
                },
                //returning promise
                getMaxDate: function ( paramObj ) {
                    return $http({
                        method: 'get',
                        url: APP_CONTEXT + 'occupation/maxDate/' + paramObj.id
                    });
                }
            };
        }
    ]);
});