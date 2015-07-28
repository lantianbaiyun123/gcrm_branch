/**
 * @ngdoc directive
 * @name yt.directive:ytPlaceholder
 * @element input
 * @function
 *
 * @description
 * 用于通用的placeholder（在input输入框中无内容时显示的占位文字）
 *
 * @example
   <input yt-placeholder="请输入名字">

 */
define(['app'], function (app) {
  app.registerDirective( 'ytPlaceholder', [ '$rootScope', '$position', function ($rootScope, $position ) {
    var uniquePlaceHolderId = 0;
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function ( scope, element, attrs, ctrl ) {
        // var placeHolderText = attrs.ytPlaceholder;
        // var msie = ~~( navigator.userAgent.toLowerCase().match( /msie (\d)/ ) || [] )[ 1 ];
        var isInputSupported = 'placeholder' in document.createElement('input');
        var isTextareaSupported = 'placeholder' in document.createElement('textarea');
        /*for ie below 9,placeholder attribute not supported*/
        if ( !isInputSupported || !isTextareaSupported) {
            var value;
      
            var placehold = function () {
                element.val(attrs.ytPlaceholder);
                element.addClass('placeholder-ie');
            };
            var unplacehold = function () {
                element.val('');
                element.removeClass('placeholder-ie');
            };
            
            scope.$watch(attrs.ngModel, function (val) {
              value = val || '';
            });

            element.bind('focus', function () {
               if(value === '') {
                unplacehold();
              }
            });
            
            element.bind('blur', function () {
               if (element.val() === '') {
                placehold();
              }
            });
            
            ctrl.$formatters.unshift(function (val) {
              if (!val) {
                placehold();
                value = '';
                return attrs.ytPlaceholder;
              }
              return val;
            });
            // var position = $position.position(element);
            // var elementId = attrs.id || ("iePlaceHolderId" + uniquePlaceHolderId++ );
            // var label = angular.element(
            //     "<label class='placeholder-ie' for='" + elementId + "' style='left:" +  ( position.left + 50 ) + "px;top:" + ( position.top + 18 ) + "px;'>" +
            //     placeHolderText +
            //     "</label>"
            // );
            // element.after(label);

            // /*trick to init the ueditor....*/
            // var unregister = scope.$watch(attrs.ngModel, function (n, o) {
            //     if (n) {
            //         label.hide();
            //         unregister();
            //     }
            // });

            // label.bind("click", function () {
            //     $(this).hide();
            // });
            // element.bind("focus", function () {
            //     label.hide();
            // });
            // element.bind("blur", function () {
            //     if (!element.val()) {
            //         label.show();
            //     }
            // });

            // scope.$on('$destroy', function() {
            //     element.unbind("blur").unbind("focus");
            // });
        } else {
            element.attr("placeholder", attrs.ytPlaceholder);
        }
      }
    };
  }]);
});