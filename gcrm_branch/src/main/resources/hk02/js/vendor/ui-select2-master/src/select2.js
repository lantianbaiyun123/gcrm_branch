define(["app"],function(e){e.registerValue("uiSelect2Config",{}).registerDirective("uiSelect2",["uiSelect2Config","$timeout",function(e,t){var i={};return e&&angular.extend(i,e),{require:"ngModel",priority:1,compile:function(e,n){var l,a,r,s=e.is("select"),u=angular.isDefined(n.multiple);return e.is("select")&&(a=e.find("optgroup[ng-repeat], optgroup[data-ng-repeat], option[ng-repeat], option[data-ng-repeat]"),a.length&&(r=a.attr("ng-repeat")||a.attr("data-ng-repeat"),l=jQuery.trim(r.split("|")[0]).split(" ").pop())),function(e,a,r,o){var c=angular.extend({},i,e.$eval(r.uiSelect2)),g=function(e){var t;return c.simple_tags?(t=[],angular.forEach(e,function(e){t.push(e.id)})):t=e,t},p=function(e){var t=[];return e?(c.simple_tags?(t=[],angular.forEach(e,function(e){t.push({id:e,text:e})})):t=e,t):t};if(s?(delete c.multiple,delete c.initSelection):u&&(c.multiple=!0),o&&(e.$watch(n.ngModel,function(e,t){e&&e!==t&&o.$render()},!0),o.$render=function(){if(s)a.select2("val",o.$viewValue);else if(c.multiple){var e=o.$viewValue;angular.isString(e)&&(e=e.split(",")),a.select2("data",p(e))}else angular.isObject(o.$viewValue)?a.select2("data",o.$viewValue):o.$viewValue?a.select2("val",o.$viewValue):a.select2("data",null)},l&&e.$watch(l,function(e,i){angular.equals(e,i)||t(function(){a.select2("val",o.$viewValue),a.trigger("change"),e&&!i&&o.$setPristine&&o.$setPristine(!0)})}),o.$parsers.push(function(e){var t=a.prev();return t.toggleClass("ng-invalid",!o.$valid).toggleClass("ng-valid",o.$valid).toggleClass("ng-invalid-required",!o.$valid).toggleClass("ng-valid-required",o.$valid).toggleClass("ng-dirty",o.$dirty).toggleClass("ng-pristine",o.$pristine),e}),!s&&(a.bind("change",function(t){t.stopImmediatePropagation(),e.$$phase||e.$root.$$phase||e.$apply(function(){o.$setViewValue(g(a.select2("data")))})}),c.initSelection))){var d=c.initSelection;c.initSelection=function(e,t){d(e,function(e){var i=o.$pristine;o.$setViewValue(g(e)),t(e),i&&o.$setPristine(),a.prev().toggleClass("ng-pristine",o.$pristine)})}}a.bind("$destroy",function(){a.select2("destroy")}),r.$observe("disabled",function(e){a.select2("enable",!e)}),r.$observe("readonly",function(e){a.select2("readonly",!!e)}),r.ngMultiple&&e.$watch(r.ngMultiple,function(e){r.$set("multiple",!!e),a.select2(c)}),t(function(){if(a.select2(c),a.select2("data",o.$modelValue),o.$render(),!c.initSelection&&!s){var e=o.$pristine;o.$setViewValue(g(a.select2("data"))),e&&o.$setPristine(),a.prev().toggleClass("ng-pristine",o.$pristine)}})}}}}])});