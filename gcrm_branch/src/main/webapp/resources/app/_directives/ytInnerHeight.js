define(['app'], function (app) {
  app.registerDirective('ytInnerHeight', [
    '$window',
    '$document',
    '$log',
  function ($window, $document, $log) {
    return {
      restrict: 'A',
      link: function (scope, element, attrs) {
        // $log.info($window);
        // $log.info($document);
        element.css({
          'overflow-y': 'auto'
        });

        var headerHeight = parseInt(attrs.ytInnerHeight, 10) || 0,
            footerHeight = parseInt(attrs.footerHeight, 10) || 0;

        scope.onResizeFunction = function() {
          // scope.windowHeight = $window.innerHeight;
          scope.windowHeight = $document[0].body.offsetHeight;
          var eleClasses = element[0].className;

          //when fixed in top
          if ( eleClasses.indexOf('yt-scrollfix')>-1 ) {
            element.css({
              height: scope.windowHeight - footerHeight
            });
          } else {
            element.css({
              height: scope.windowHeight - headerHeight
            });
          }
        };

        // Call to the function when the page is first loaded
        scope.onResizeFunction();

        angular.element($window).bind('resize', function() {
          scope.onResizeFunction();
          if (!scope.$$phase) {
            scope.$apply();
          }
        });

        angular.element($window).on('scroll', function () {
          scope.onResizeFunction();
          if (!scope.$$phase) {
            scope.$apply();
          }
        });
      }
    };
  }]);
});