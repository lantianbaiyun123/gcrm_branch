define(['app'], function ( app ) {
/**
 * usage:
 */
app.registerDirective('ytInputRadio', ['$filter', 'STATIC_DIR', function ( $filter, STATIC_DIR ) {
  return {
    replace: true,
    restrict: 'A',
    scope: {
      selected: '=radioSelected',
      choices: '=ytInputRadio',
      selectChange: '&',
      radioDisabled: '='
    },
    templateUrl: STATIC_DIR + 'app/_directives/ytInputRadio.tpl.html',
    link: function ( scope, element, attrs ) {
      scope.selected = scope.selected !== 0 && (scope.selected || '');
      if ( attrs.radioType ) {
        scope.typeName = angular.uppercase( attrs.radioType + '_' ) || '';
      } else {
        scope.isI18nList = true;
        scope.typeName = attrs.radioName || attrs.ytInputRadio;
      }
      scope.change = function ( choice, e ) {
        if ( scope.radioDisabled ) {
          return false;
        }

        //ng-click分别在label和input各触发了1次，label那次selected的值是还是旧值
        if ( e.target.tagName === 'INPUT') {
          if ( typeof scope.selected !== 'undefined') {
            if (scope.isI18nList) {
              scope.selected = choice.id;
            } else {
              scope.selected = choice;
            }
            // 用watch“scope.selected” 而不用 ng-change去监听选中值的变化，因为调用selectedChange()时，
            // scope.selected 的值仍为旧值，且无法传参
            // scope.selectChange();
          }
        }
      };
    }
  };
}]);


});