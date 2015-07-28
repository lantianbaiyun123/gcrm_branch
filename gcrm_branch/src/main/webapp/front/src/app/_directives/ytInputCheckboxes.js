define(['app'], function ( app ) {
/**
 * usage: 
 */
app.registerDirective('ytInputCheckboxes', ['$filter', 'STATIC_DIR', function ( $filter, STATIC_DIR ) {
  return {
    replace: true,
    restrict: 'A',
    scope: {
      selected: '=checkboxesSelected',
      choices: '=ytInputCheckboxes',
      checkboxesDisabled: '='
    },
    templateUrl: STATIC_DIR + 'app/_directives/ytInputCheckboxes.tpl.html',
    link: function ( scope, element, attrs ) {
      scope.selected = scope.selected || [];
      if ( attrs.checkType !== 'i18n' ) {
        scope.typeName = angular.uppercase( attrs.checkType + '_' ) || '';
      } else {
        scope.isI18nList = true;
      }
      var unwatchSelected = scope.$watch( 'selected', function ( value ) {
        if ( value && value.length ) {
          unwatchSelected();
          //set checked if any
          if ( attrs.checkType !== 'i18n' ) {
            angular.forEach( element.find('input'), function ( input ) {
              if ( scope.selected.indexOf( input.val() ) ) {
                input.checked = true;
              }
            });
          } else {
            scope.isI18nList = true;
          }
        }
      });
      scope.checkOne = function ( choice, index ) {
        if ( scope.selected.indexOf( choice ) !== -1 ) {
          scope.selected.splice( scope.selected.indexOf( choice ), 1 );
        } else {
          scope.selected.push( choice );
        }
      };
    }
  };
}]);


});