/**
 * [Usage]
 * @param  {[type]} app [description]
 * @return {[type]}     [description]
 */
define(['app'], function (app) {
  app.registerDirective('ytPopoverConfirm', [
    '$compile',
    '$position',
    '$parse',
    '$log',
    '$timeout',
    '$filter',
    '$document',
  function ($compile, $position, $parse, $log, $timeout, $filter, $document) {
    return {
      restrict: 'A',
      scope: {
        ytPopoverConfirm: '&',
        ytPopoverCancel: '&',
        ytPopoverBeforeShow: '&',
        ytPopoverMsg: '='
      },
      link: function (scope, element, attrs) {
        var translateFilter = $filter('translate');
        var $el = element[0];
        var scopePopover = scope.$new();
        var appendToBody = attrs.appendToBody === 'true' ? true : false;
        scopePopover.placement = attrs.ytPopoverPlacement || 'top';
        scopePopover.title = attrs.ytPopoverTitle || translateFilter('ARE_YOU_SURE');
        scopePopover.okText = attrs.ytPopoverOkText || translateFilter('CONFIRM');
        scopePopover.okBtnClass = attrs.ytPopoverOkClass || 'btn-primary';
        scopePopover.msg = scope.ytPopoverMsg;

        var popoverEl = angular.element('<div yt-popover-wrap></div>');
        var $popover = $compile(popoverEl)(scopePopover);
        if ( appendToBody ) {
        $document.find('body').append($popover);
        } else {
          element.after($popover);
        }

        scopePopover.isOpen = false;
        scopePopover.isGoodToOpen = false;
        var ifDocumentClickedBind = false;
        var ifElementClickBind = false;

        scopePopover.$watch('isOpen', function (value) {
          if ( value ) {

            updateContent();
            $timeout(function () {
              updatePosition();
              scopePopover.isGoodToOpen = true;
            });
            // $timeout(function () {
                // body...
            $document.bind('click', documentClicked);
            // }, 0)
            if (ifElementClickBind) {
                element.unbind('click', elementClicked);
            }
            ifDocumentClickedBind = true;
          } else {
            if (ifDocumentClickedBind) {
              $document.unbind('click', documentClicked);
            }
            element.bind('click', elementClicked);
            ifElementClickBind = true;
            scopePopover.isGoodToOpen = false;
          }
        });

        scopePopover.ok = function () {
          $timeout(function () {
            scopePopover.isOpen = false;
            scope.ytPopoverConfirm();
            element.removeAttr("disabled");
          });
        };

        scopePopover.cancel = function () {
          scopePopover.isOpen = false;
          scope.ytPopoverCancel();
          element.removeAttr("disabled");
        };

        scope.$on('$destroy', function() {
          $popover.remove();
          scopePopover.$destroy();
        });

        function updateContent () {
          scopePopover.msg = scope.ytPopoverMsg;
          scopePopover.title = attrs.ytPopoverTitle || translateFilter('ARE_YOU_SURE');
          scopePopover.okText = attrs.ytPopoverOkText || translateFilter('CONFIRM');
          scopePopover.okBtnClass = attrs.ytPopoverOkClass || 'btn-primary';
        }

        function updatePosition () {
          scopePopover.position = appendToBody ? $position.offset(element) : $position.position(element);
          scopePopover.position.top = scopePopover.position.top - $popover.height();
          scopePopover.position.left = scopePopover.position.left - ($popover.width() - $position.position(element).width) / 2 ;
        }

        //when document clicked
        function documentClicked ( event ) {
          if ( scopePopover.isOpen && event.target !== element[0] ) {
            // scopePopover.$apply(function () {
            scopePopover.isOpen = false;
            // });
            element.removeAttr("disabled");
            if (!scopePopover.$$phase) {
                scopePopover.$apply();
            }
            element.removeAttr("disabled");
          }
        }

        //when clicked
        function elementClicked (e) {
          e.preventDefault();
          // e.stopPropagation();
          $timeout(function () {
            if (scopePopover.isOpen) {
              return;
            }
            var shouldShow = scope.ytPopoverBeforeShow();
            if (shouldShow || typeof shouldShow === 'undefined') {
              scopePopover.$apply(function() {
                scopePopover.isOpen = true;
              });
              element.attr("disabled", "disabled");
            }
          }, 0);
        }
      }
    };
  }]);
  app.registerDirective('ytPopoverWrap', [ 'STATIC_DIR', function( STATIC_DIR ) {
      return {
          restrict: 'EA',
          replace: true,
          templateUrl: STATIC_DIR + 'app/_directives/ytPopoverConfirm.tpl.html',
          link: function(scope, element, attrs) {
              element.bind('click',
              function(event) {
                  event.preventDefault();
                  event.stopPropagation();
              });
          }
      };
    }]
  );
});