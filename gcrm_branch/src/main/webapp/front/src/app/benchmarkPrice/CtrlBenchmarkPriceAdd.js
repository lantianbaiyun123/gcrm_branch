/**
 * [CtrlBenchmarkPriceAdd]
 * cotroller for feature 'benchmarkPriceAdd'
 */
define([
  'app',
  '../_common/ytCommon'
], function (app) {
  app.registerController('CtrlBenchmarkPriceAdd', [
    '$scope',
    '$state',
    '$log',
    'PageSet',
  function ($scope, $state, $log, PageSet) {
    PageSet.set({activeIndex:1,siteName:'benchmarkPriceAdd'});

    //add data
    $scope.quotationMain = {};
    $scope.quotationList = [];

    $scope.msgs.listAlerts = [];

  }]);
});
