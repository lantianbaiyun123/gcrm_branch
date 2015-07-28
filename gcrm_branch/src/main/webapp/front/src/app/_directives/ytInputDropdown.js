define(['app'], function ( app ) {
/**
 * usage: <div yt-input-dropdown="basic.customerTypes" dropdown-selected="basic.customerType" dropdown-type="customer" />
 <div yt-input-dropdown="basic.industryTypes" dropdown-selected="basic.industryType" dropdown-type="i18n" />
 */
app.registerDirective('ytInputDropdown', ['$filter', 'STATIC_DIR', function ( $filter, STATIC_DIR ) {
  return {
    replace: true,
    restrict: 'A',
    scope: {
      selected: '=dropdownSelected',
      types: '=ytInputDropdown',
      dropdownDisabled: '='
    },
    templateUrl: STATIC_DIR + 'app/_directives/ytInputDropdown.tpl.html',
    link: function ( scope, element, attrs ) {
      /**
       * [pleaseText空选项文案]
       * 1、 不定义pleaseText，默认空选项文案为"请选择"
       * 2、 pleaseText=='false'，不加入空选项
       * @type {[type]}
       */
      var pleaseSelectText = attrs.pleaseText || $filter( 'translate' )( 'PLEASE_SELECT' );
      /**
       * 用dropdownType来区分两种列表，i18n==值列表，其他==枚举型列表
       * @type {[type]}
       */
      if ( attrs.dropdownType === 'i18n' ) {
        scope.isI18nList = true;
      } else {
        //uppercase type and and '_' to format,the output key for translate like this: CUSTOMER_offline
        scope.typeNamePrefix = angular.uppercase( attrs.dropdownType ) + '_';
      }
      //监听ytInputDropdown，一旦有值，便开始初始化
      var unwatchTypes = scope.$watch('types', function ( newValue ) {
        if ( newValue && newValue.length ) {
          scope.choices = angular.copy( newValue );
          if ( pleaseSelectText !== 'false' ) {
            unshiftPleaseSelect();
          } else {
            scope.noPleaseSelectText = true;
          }
          unwatchTypes();
          if ( !scope.selected ) {
            scope.setChoiceType(0);
          }
        }
      });
      //add select hint to
      scope.setChoiceType = function ( index ) {
        if ( index || scope.isI18nList || scope.noPleaseSelectText ) {
          scope.selected = scope.choices[ index ];
        } else if ( index === 0 ) {
          scope.selected = undefined;
        }
        // setText();
      };
      scope.$watch( 'selected', function ( value ) {
        setText( value );
      });
      //根据选中的值设置显示的字符
      function setText ( value ) {
        if ( !scope.isI18nList ) {
          if ( value ) {
            scope.buttonText = $filter( 'translate' )( scope.typeNamePrefix + value );
          } else {
            scope.buttonText = pleaseSelectText;
          }
        } else {
          if ( value ) {
            scope.buttonText = value.i18nName;
          } else {
            scope.buttonText = pleaseSelectText;
          }
        }
      }
      //在列表前面加入请选择
      function unshiftPleaseSelect () {
        if ( !scope.isI18nList ) {
          scope.choices.unshift( pleaseSelectText );
        } else {
          scope.choices.unshift( {
            i18nName: pleaseSelectText
          });
        }
      }
      //destroy
      // scope.$on('$destroy', function() {
      //   // Make sure that 删除请选择
      //   scope.types.splice( 0, 1 );
      // });
    }
  };
}]);


});