define(['app'], function (app) {
    app.registerFilter('RecordOperateTypeFilter', function () {
        var type = {
            'modify': 'MODIFY',
            'insert': 'INSERT',
            'delete': 'DELETE'
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