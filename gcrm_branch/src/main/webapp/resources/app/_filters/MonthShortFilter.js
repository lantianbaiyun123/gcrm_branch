define(['app'], function (app) {
    app.registerFilter('MonthShortFilter', function () {
        var month = {
            1: "JANUARY_SHORT",
            2: "FEBRUARY_SHORT",
            3: "MARCH_SHORT",
            4: "APRIL_SHORT",
            5: "MAY_SHORT",
            6: "JUNE_SHORT",
            7: "JULY_SHORT",
            8: "AUGUST_SHORT",
            9: "SEPTEMBER_SHORT",
            10: "OCTOBER_SHORT",
            11: "NOVEMBER_SHORT",
            12: "DECEMBER_SHORT"
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