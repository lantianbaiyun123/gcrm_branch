define(["app","../_common/ytCommon"],function(e){e.registerController("CtrlAddUser",["$scope","User","$modalInstance","Modal","opts",function(e,c,o,n,s){"edit"===s.type?(e.e=s.rowData,e.hideAccountInfo=!0):e.e={},e.ok=function(){c.save(e.e).success(function(e){200===e.code?(o.close(),n.success({content:"yeap"})):206===e.code&&(o.close(),n.success({content:e.message}))})},e.cancel=function(){o.dismiss("cancel")}}])});