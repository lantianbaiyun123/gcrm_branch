define(['app'], function (app) {
  app.registerDirective('ytMaxlength', [function () {
    function  isLengthOverflow (source, maxLength) {
      if ((source + '').replace(/([^\x00-\xff])/g, 'aa').length > maxLength) {
        return true;
      }
      return false;
    }
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function (scope, element, attrs, ngModel) {
        //For DOM -> model validation
        ngModel.$parsers.unshift(function(value) {
           var valid = !isLengthOverflow(value, parseInt(attrs.ytMaxlength, 10));
           ngModel.$setValidity('ytMaxlength', valid);
           return valid ? value : undefined;
        });

        //For model -> DOM validation
        ngModel.$formatters.unshift(function(value) {
           ngModel.$setValidity('ytMaxlength', !isLengthOverflow(value, parseInt(attrs.ytMaxlength, 10)));
           return value;
        });
      }
    };
  }]);
});