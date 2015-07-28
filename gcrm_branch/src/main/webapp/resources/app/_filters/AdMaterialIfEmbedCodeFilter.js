define(['app'], function (app) {
    app.registerFilter('AdMaterialIfEmbedCodeFilter', function () {
        var types = [ 'NO', 'YES'];
        return function ( input ) {
            if ( input || input === 0 ) {
                return types[ input ];
            }
            return '';
        };
    });
});