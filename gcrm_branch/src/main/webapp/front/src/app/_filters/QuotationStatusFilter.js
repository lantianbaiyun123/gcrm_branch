define(['app'], function (app) {
    app.registerFilter('QuotationStatusFilter', function () {
        var type = {
            INVALID: 'QUOTATION_STATUS_INVALID',
            VALID: 'QUOTATION_STATUS_VALID',
            OVERDUE_INVALID: 'QUOTATION_STATUS_OVERDUE_INVALID',
            CANCEL: 'QUOTATION_STATUS_CANCEL'
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