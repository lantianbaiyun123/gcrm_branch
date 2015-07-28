define(["app","../_common/ytCommon","../_filters/PositionFilter","./CtrlPlatformAddAndEdit"],function(t){t.registerController("CtrlPlatformList",["$scope","$log","PageSet","Modal","$modal","Position","$window","$filter","STATIC_DIR",function(t,o,n,e,l,r,a,i,c){var d=i("translate");n.set({siteName:"platformList",activeIndex:2}),r.getPlatformList({}).success(function(o){200===o.code&&(t.list=o.data)}),t.btnEdit=function(t){l.open({templateUrl:c+"app/platform/platformModal.tpl.html",controller:"CtrlPlatformAddAndEdit",resolve:{opts:function(){return{type:"edit",platformId:t.id}}}})},t.btnShutdown=function(t){r.getPlatformUsedCount({id:t.id}).success(function(o){if(200===o.code){var n=l.open({templateUrl:c+"app/platform/platformShutdownAlertModal.tpl.html",controller:["$scope","$modalInstance","count","$filter","platformName",function(t,o,n,e,l){n&&(t.usedCountHint=e("translate")("SHUTDOWN_HINT",{usedCount:n})),t.platformName=l,t.ok=function(){o.close("ok")},t.cancel=function(){o.dismiss("cancel")}}],resolve:{count:function(){return o.data},platformName:function(){return t.i18nName}}});n.result.then(function(o){o&&r.shutdownPlatform({id:t.id}).success(function(t){200===t.code&&(e.success({content:d("PLATFORM_LIST_SHUTDOWN_SUCCESS")}),a.location.reload())})})}})},t.btnStartup=function(t){r.startUpPlatform({id:t.id}).success(function(t){200===t.code&&e.success({content:d("PLATFORM_LIST_STARTUP_SUCCESS")},function(){a.location.reload()})})},t.btnAdd=function(){l.open({templateUrl:c+"app/platform/platformModal.tpl.html",controller:"CtrlPlatformAddAndEdit",resolve:{opts:function(){return{type:"add"}}}})}}])});