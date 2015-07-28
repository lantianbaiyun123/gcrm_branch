define(['app'], function (app) {
    app.registerFilter('AdProjectStatusFilter', function () {
        var types = [ '课程', '直播', '活动', '专题' ];
        return function ( input ) {
            return types[ input - 1 ];
        };
    });
});