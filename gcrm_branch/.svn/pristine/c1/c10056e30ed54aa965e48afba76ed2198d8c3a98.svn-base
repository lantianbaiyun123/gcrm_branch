define(['app'], function (app) {
    app.registerFilter('MaterialApprovalStatusFilter', function () {
        var status = {
            0: 'MATERIAL_APPROVEL_STATUS_create',
            1: 'MATERIAL_APPROVEL_STATUS_submit',
            2: 'MATERIAL_APPROVEL_STATUS_confirm',
            3: 'MATERIAL_APPROVEL_STATUS_cancel',
            4: 'MATERIAL_APPROVEL_STATUS_refused'
        };
        return function ( input ) {
            if( status[input] ) {
                return status[ input ];
            } else {
                return '--';
            }
        };
    });
});