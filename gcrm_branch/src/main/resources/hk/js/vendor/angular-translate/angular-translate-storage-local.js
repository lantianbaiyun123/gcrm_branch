angular.module("pascalprecht.translate").factory("$translateLocalStorage",["$window","$translateCookieStorage",function(t,a){var e={get:function(a){return t.localStorage.getItem(a)},set:function(a,e){t.localStorage.setItem(a,e)}},o="localStorage"in t&&null!==t.localStorage?e:a;return o}]);