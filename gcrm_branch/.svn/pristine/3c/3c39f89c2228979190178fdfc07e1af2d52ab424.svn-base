define(['app'], function (app) {
    app.registerFilter('AdSolutionTypeFilter', function () {
        var type = {
            create: 'AD_SOLUTION_TYPE_CREATE',
            update: 'AD_SOLUTION_TYPE_UPDATE'
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