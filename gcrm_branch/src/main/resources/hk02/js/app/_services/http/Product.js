define(["app"],function(t){t.registerFactory("Product",["$http","APP_CONTEXT",function(t,e){return{get:function(n,u){t({method:"get",url:e+"adsolution/content/queryProduct",params:n}).success(function(t){u(t)})},getAll:function(){return t({method:"get",url:e+"quote/queryPlatform"})}}}])});