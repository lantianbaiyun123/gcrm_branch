define(['app'], function (app) {
    app.registerFilter('PriceTypeFilter', function () {
        var type = {
            unit: 'PRICE_TYPE_unit',
            ratio: 'PRICE_TYPE_ratio',
            rebate: 'PRICE_TYPE_rebate'
        };
        return function ( input ) {
            if( type[input] ) {
                return type[ input ];
            } else {
                return '--';
            }
        };
    });
});