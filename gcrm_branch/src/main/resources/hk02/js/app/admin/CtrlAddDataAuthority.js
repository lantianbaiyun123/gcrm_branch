define(["app","../_directives/checklistModel","../_directives/ytCheckboxIndeterminate"],function(e){e.registerController("CtrlAddDataAuthority",["$scope","$log","$modalInstance","User","opts","$filter",function(e,t,i,s,a){function l(){for(var t=[],i=e.roles.length-1;i>=0;i--){var s=e.roles[i];s.e&&t.push({username:a.username,roleTag:s.roleTag,busiTypeRights:angular.copy(s.e.busiTypeRights),platformRights:angular.copy(s.e.platformRights.list),siteRights:angular.copy(s.e.siteRights.list)})}return t}s.getAuthorityRoleList({username:a.username}).success(function(t){200===t.code&&(e.roles=t.data&&t.data.length?t.data:[{roleDesc:"统一",roleTag:""}])}),e.getAuthority=function(t){t.e||s.editAuthority({username:a.username,roleTag:t.roleTag}).success(function(i){200===i.code&&(t.e={},t.e.busiTypeRights=i.data.busiTypeRights,t.e.siteRights={allChecked:!1,indeterminate:!1,list:i.data.siteRights},e.updateAllCheck(t.e.siteRights),t.e.platformRights={allChecked:!1,indeterminate:!1,list:i.data.platformRights},e.updateAllCheck(t.e.platformRights))})},e.ok=function(){var e=l();s.saveAuthority({dataRightsVOList:e}).success(function(e){200===e.code&&i.close()})},e.cancel=function(){i.dismiss("cancel")},e.changeAllCheck=function(e){for(var t=e.list.length-1;t>=0;t--)e.list[t].hasRights=e.allChecked;e.indeterminate=!1},e.updateAllCheck=function(e){for(var t=!0,i=!0,s=e.list.length,a=0;s>a;a++){var l=e.list[a];l.hasRights?i=!1:t=!1}t?(e.allChecked=!0,e.indeterminate=!1):i?(e.allChecked=!1,e.indeterminate=!1):(e.allChecked=!1,e.indeterminate=!0)}}])});