define(["app"],function(t){t.registerFactory("BillModel",["$http","APP_CONTEXT",function(t,e){return{get:function(n,o){t({method:"get",url:e+"adsolution/content/queryBillModels"}).success(function(t){o(t)})}}}])});