define(['app'], function (app) {
  app.registerDirective('ytFocusme', ['$timeout', '$parse', function ($timeout, $parse) {
    return {
      scope: {
        ytFocusme: '='
      },
      link : function (scope, element, attrs) {
        scope.$watch('ytFocusme.focus', function(value) {
          if(value === true) {
            $timeout(function() {
              element[0].focus();
              scope.ytFocusme.focus = false;
            });
          }
        });
      }
    };
  }]);
});
