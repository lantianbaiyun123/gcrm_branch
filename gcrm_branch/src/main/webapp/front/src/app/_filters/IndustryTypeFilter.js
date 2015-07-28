define(['app'], function (app) {
    app.registerFilter('IndustryTypeFilter', function () {
        // var type = {
        //     ecommerce: 'AD_CONTENT_CUSTOMER_BUSINESS',
        //     game: 'AD_CONTENT_CUSTOMER_GAME',
        //     other: 'AD_CONTENT_CUSTOMER_OTHER'
        // };
        // return function ( input ) {
        //     if( type[input] ) {
        //         return type[ input ];
        //     } else {
        //         return '--';
        //     }
        // };
        var type = ['INDUSTRY_TYPE_ECOMMERCE', 'INDUSTRY_TYPE_GAME', 'INDUSTRY_TYPE_OTHER'],
            eName = ['Ecommerce-electronic', '', 'Ecommerce-other'];
        return function ( input, cate ) {
            if ( cate ==='eName' && input>-1 && input<3 ) {
                return eName[input];
            } else if( type[input] ) {
                return type[ input ];
            } else {
                return '--';
            }
        };
    });
});