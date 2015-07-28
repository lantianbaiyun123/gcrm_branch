define([
  'app',
  '../_filters/PositionFilter'
], function ( app ) {
  app.registerFactory( 'PositionPropDetailModal', [
    '$modal',
    'STATIC_DIR',
  function ( $modal, STATIC_DIR ) {
    return {
      show: function (opts) {
        var optsDefault = {};
        var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/resourcePosition/positionPropDetailModal.tpl.html',
            controller: 'CtrlModal',
            windowClass: 'position-prop-modal',
            resolve: {
              opts : function () {
                return angular.extend(optsDefault, opts);
              }
            }
        });
      }
    };
  }]);
});