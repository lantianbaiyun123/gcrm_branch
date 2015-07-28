/**
 * [CtrlBenchmarkPriceList]
 * cotroller for feature 'benchmarkPriceLIst'
 */
define(['app',
        '../../_common/ytCommon',
        '../../_services/PageSet'
], function (app) {
    app.registerController('CtrlBenchmarkPriceList', [
        '$scope',
        'PageSet',
    function ($scope, PageSet) {
        PageSet.set({activeIndex:0,siteName:'benchmarkPriceList'});

        $scope.alerts = [
            { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
            { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
        ];

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

    }]);
});
