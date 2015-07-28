define(['app'], function (app) {
    app.registerFilter('QuotationApprovalStatusFilter', function () {
        var type = {
            SAVING: 'QUOTATION_APPROVAL_STATUS_SAVING',
            APPROVING: 'QUOTATION_APPROVAL_STATUS_APPROVING',
            APPROVED: 'QUOTATION_APPROVAL_STATUS_APPROVED',
            CANCEL: 'QUOTATION_APPROVAL_STATUS_CANCEL'
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