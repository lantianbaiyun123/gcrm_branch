define(["app"],function(e){e.registerController("CtrlResourcePositionImportModal",["$scope","$modalInstance","Position","Modal","$timeout","$modal","$filter","GCRMUtil","STATIC_DIR",function(e,o,t,n,r,i,l,a,s){e.q={},t.getPlatformListByRole().success(function(o){200===o.code&&(e.q.adPlatformList=o.data)}),e.changePlatform=function(){t.getSiteListByRole({adPlatformId:e.q.platformId}).success(function(o){200===o.code&&(e.q.adSiteList=o.data)})},e.changeSite=function(){e.q.siteId&&e.q.platformId&&(e.done={adPlatformId:e.q.platformId,siteId:e.q.siteId})},e.ok=function(){},e.cancel=function(){o.dismiss("cancel")},e.e={},e.uploadedCbfn=function(){r(function(){if(e.e.fileUploaded&&!e.e.fileUploaded.errorList)o.close(e.e.fileUploaded);else{var t,n=e.e.fileUploaded.errorList,r=l("translate"),c=[];angular.forEach(n,function(e,o){t=[],"resource.position.import.name.duplicate"!==o?angular.forEach(e,function(e){t.push(r("RESOURCE_POSITION_IMPORT_ERROR_ROW_COL",e))}):angular.forEach(e,function(e){t.push(e.otherCell?r("RESOURCE_POSITION_IMPORT_ERROR_ROW_COL",e)+" VS "+r("RESOURCE_POSITION_IMPORT_ERROR_ROW_COL",e.otherCell):r("RESOURCE_POSITION_IMPORT_ERROR_ROW_COL",e))}),c.push({typeName:a.decodeGCRMMessageSingle(o),list:t})});{i.open({templateUrl:s+"app/resourcePosition/importError.tpl.html",controller:["$scope","opts","$modalInstance",function(e,o,t){e.errors=o.errors,e.ok=function(){t.close()}}],resolve:{opts:function(){return{errors:c}}}})}}})}}])});