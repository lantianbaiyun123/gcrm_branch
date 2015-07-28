define(['app'], function (app) {
    app.registerFilter('BusinessTypeFilter', function () {
        var type = {
            SALE: 'BUSINESS_TYPE_SALE',
            CASH: 'BUSINESS_TYPE_CASH'
        };
        return function ( input ) {
            if( type[input] ) {
                return type[ input ];
            } else {
                return input;
            }
        };
    });
});