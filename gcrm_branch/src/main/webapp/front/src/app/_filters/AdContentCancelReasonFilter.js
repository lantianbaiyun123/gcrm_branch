define(['app'], function (app) {
    app.registerFilter('AdContentCancelReasonFilter', function () {
        var type = {
            end: 'AD_CONTENT_CANCEL_REASON_END',
            modify: 'AD_CONTENT_CANCEL_REASON_MODIFY',
            cancel: 'AD_CONTENT_CANCEL_REASON_CANCEL',
            change: 'AD_CONTENT_CANCEL_REASON_CHANGE',
            adChange: 'AD_CONTENT_CANCEL_REASON_AD_CHANGE',
            disable_position: 'AD_CONTENT_CANCEL_REASON_DISABLE_POSITION'
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