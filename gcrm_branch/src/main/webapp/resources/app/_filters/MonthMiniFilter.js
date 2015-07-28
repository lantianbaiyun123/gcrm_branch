define(['app'], function (app) {
    app.registerFilter('MonthMiniFilter', function () {
        var month = {
            1: "JANUARY_MINI",
            2: "FEBRUARY_MINI",
            3: "MARCH_MINI",
            4: "APRIL_MINI",
            5: "MAY_MINI",
            6: "JUNE_MINI",
            7: "JULY_MINI",
            8: "AUGUST_MINI",
            9: "SEPTEMBER_MINI",
            10: "OCTOBER_MINI",
            11: "NOVEMBER_MINI",
            12: "DECEMBER_MINI"
        };
        return function ( input ) {
            if( month[input] ) {
                return month[ input ];
            } else {
                return '--';
            }
        };
    });
});