define(['app'], function (app) {
    app.registerFilter('urlHttpFilter', function () {
        return function ( input ) {
            if( input.indexOf('http://') < 0) {
                return 'http://' + input;
            } else {
                return input;
            }
        };
    });
});