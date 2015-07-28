define([
  'app',
  './CtrlScheduleModal',
   '../_directives/ytSchedulePopoverConfirm',
   '../_directives/ytScheduleTable'

 ], function ( app ) {
  app.registerFactory( 'ScheduleModal', [ '$modal', '$timeout', '$filter', 'AdProgram', 'STATIC_DIR', function ( $modal, $timeout, $filter, AdProgram, STATIC_DIR ) {
    return {
        show: function ( opts, cbfn ) {
            var _this = this;
            var optsDefault = {
              title: $filter('translate')('SCHEDULE_MODAL')
            };

            var modalInstance = $modal.open({
                templateUrl: STATIC_DIR + 'app/schedule/scheduleModal.tpl.html',
                controller: 'CtrlScheduleModal',
                windowClass: 'schedule-modal',
                backdrop: 'static',
                resolve: {
                  opts : function () {
                    return angular.extend(optsDefault, opts);
                  }
                }
            });

            modalInstance.result.then(function (result) {
                if (result === 'goNext') {
                    cbfn();
                }
            });
        }
    };
  }]);

});