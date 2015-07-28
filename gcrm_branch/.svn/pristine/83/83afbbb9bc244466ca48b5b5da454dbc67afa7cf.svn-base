define(['app'], function (app) {
    app.registerFilter('AdApprovalStatusFilter', function () {
        var status = [ 'REJECT', 'AGREE' ];
        return function ( input ) {
            if( input === 0 || input === 1) {
                return status[ input ];
            } else {
                return '--';
            }
        };
    });
});