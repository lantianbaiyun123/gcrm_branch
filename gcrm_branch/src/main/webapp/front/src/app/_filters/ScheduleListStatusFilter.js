define(['app'], function (app) {
    app.registerFilter('ScheduleListStatusFilter', function () {
        var type = {
            'reserved': 'SCHEDULE_RESERVED',
            'confirmed': 'SCHEDULE_CONFIRMED',
            'locked': 'SCHEDULE_LOCKED',
            'released': 'SCHEDULE_RELEASED',
            'schedule.schedulestatus.confirmed': 'SCHEDULE_CONFIRMED',
            'schedule.schedulestatus.locked': 'SCHEDULE_LOCKED',
            'schedule.schedulestatus.reserved': 'SCHEDULE_RESERVED',
            'schedule.schedulestatus.released': 'SCHEDULE_RELEASED'
        };
        return function ( input ) {
            if( input in type ) {
                return type[ input ];
            } else {
                return '--';
            }
        };
    });
});