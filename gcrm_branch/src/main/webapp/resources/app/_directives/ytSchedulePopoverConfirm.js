define(['app'], function (app) {
    app.registerDirective('ytSchedulePopoverConfirm', ['$compile','$position', '$parse', '$log', '$timeout', function ($compile, $position, $parse, $log, $timeout) {
        return {
            restrict: 'A',
            scope: {
                ytPopoverOk: '&',
                ytPopoverCancel: '&',
                ytPopoverConfirmShow: '=',
                ytPopoverConfirmMsgs: '='
            },
            link: function (scope, element, attrs) {
                var $el = element[0];
                var scopePopover = scope.$new();
                scopePopover.placement = 'top';
                scopePopover.title = 'TOTAL_DAYS_ALERT';

                var popoverEl = angular.element('<div yt-schedule-popover-wrap></div>');
                var $popover = $compile(popoverEl)(scopePopover);
                element.after($popover);

                scope.$watch('ytPopoverConfirmShow', function (value) {
                    scopePopover.msgs = scope.ytPopoverConfirmMsgs;
                    $timeout(function (argument) {
                        updatePosition();
                        scopePopover.isOpen = value;
                    },0);
                });

                scopePopover.ok = function () {
                    scopePopover.isOpen = false;
                    scope.ytPopoverOk();
                };
                scopePopover.cancel = function () {
                    scopePopover.isOpen = false;
                    scope.ytPopoverCancel();
                };

                function updatePosition() {
                    scopePopover.position = $position.position(element);
                    scopePopover.position.top = element[0].offsetTop - $popover.height();
                    // scopePopover.position.left = element[0].offsetLeft - ($popover.width() - element[0].offsetWidth) / 2 ;
                    scopePopover.position.left = element[0].offsetLeft - ($popover.width() - 124) ;
                }
            }
        };
    }]);
    app.registerDirective('ytSchedulePopoverWrap', [ 'STATIC_DIR', function ( STATIC_DIR ) {
        return {
            restrict: 'EA',
            replace: true,
            templateUrl: STATIC_DIR + 'app/_directives/schedulePopoverConfirm.tpl.html',
            link: function(scope, element, attrs) {
                element.bind('click',
                function(event) {
                    event.preventDefault();
                    event.stopPropagation();
                });
            }
        };
    }]);
});