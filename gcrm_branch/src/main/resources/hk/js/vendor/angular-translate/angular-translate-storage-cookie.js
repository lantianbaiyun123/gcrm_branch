angular.module("pascalprecht.translate").factory("$translateCookieStorage",["$cookieStore",function(t){var e={get:function(e){return t.get(e)},set:function(e,r){t.put(e,r)}};return e}]);