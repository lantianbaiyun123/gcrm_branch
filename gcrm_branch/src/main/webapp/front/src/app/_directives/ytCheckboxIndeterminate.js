/**
 * Provides an easy way to toggle a checkboxes indeterminate property
 *
 * @example <input type="checkbox" yt-checkbox-indeterminate"isUnkown">
 */
define(['app'], function (app) {
  app.registerDirective('ytCheckboxIndeterminate', [
    function () {
      return {
        compile: function(tElm, tAttrs) {
          if (!tAttrs.type || tAttrs.type.toLowerCase() !== 'checkbox') {
            return angular.noop;
          }

          return function ($scope, elm, attrs) {
            $scope.$watch(attrs.ytCheckboxIndeterminate, function(newVal) {
              elm[0].indeterminate = !!newVal;
            });
          };
        }
      };
    }
  ]);
});