define(['app'], function (app) {
    app.registerFilter('OperateTypeFilter', function () {
        var type = {
            all: 'ALL',
            online: 'PUBLICATION_OPERATE_ONLINE',
            offline: 'PUBLICATION_OPERATE_OFFLINE',
            materialChange: 'PUBLICATION_OPERATE_MATERIAL_CHANGE'
        };
        return function ( input ) {
            if( type[input] ) {
                return type[ input ];
            } else {
                return '--';
            }
        };
    });

    app.registerFilter('OperationTypeFilter', function () {
        var type = {
            publish: 'PUBLICATION_OPERATION_PUBLISH',
            unpublish: 'PUBLICATION_OPERATION_UNPUBLISH',
            material_update: 'PUBLICATION_OPERATION_MATERIAL_UPDATE',
            terminate: 'PUBLICATION_OPERATION_TERMINATE',
            force_publish: 'PUBLICATION_OPERATION_FORCE_PUBLISH'
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