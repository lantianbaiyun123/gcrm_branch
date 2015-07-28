define(['app'], function (app) {
    app.registerFilter('RotationTypeFilter', function () {
        var type = {
            0: 'SCHEDULE_STATIC_POSITION',
            1: 'SCHEDULE_ROTATION_POSITION'
        };
        return function ( input ) {
            if( type[input] ) {
                return type[ input ];
            } else {
                return 'SCHEDULE_STATIC_POSITION';
            }
        };
    });
});