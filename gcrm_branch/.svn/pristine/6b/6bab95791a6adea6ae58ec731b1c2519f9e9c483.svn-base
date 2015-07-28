define([
  'app',
  '../_directives/checklistModel',
  '../_directives/ytCheckboxIndeterminate'
], function ( app ) {
/**
 * 用户数据权限
 */

app.registerController( 'CtrlAddDataAuthority', [
  '$scope',
  '$log',
  '$modalInstance',
  'User',
  'opts',
  '$filter',
  function ( $scope, $log, $modalInstance, User, opts, $filter ) {
    User.getAuthorityRoleList({
      username: opts.username
    }).success( function (response) {
      if (response.code === 200) {
        if (response.data && response.data.length) {
          $scope.roles = response.data;
        } else {
          $scope.roles = [{
            roleDesc: '统一',
            roleTag: ''
          }];
        }
      }
    });

    $scope.getAuthority = function (role) {
      if ( !role.e ) {
        User.editAuthority({
          username: opts.username,
          roleTag: role.roleTag
        }).success( function ( response ) {
          if ( response.code === 200 ) {
            role.e = {};
            role.e.busiTypeRights = response.data.busiTypeRights;
            role.e.siteRights = {
              allChecked: false,
              indeterminate: false,
              list: response.data.siteRights
            };
            $scope.updateAllCheck(role.e.siteRights);
            role.e.platformRights = {
              allChecked: false,
              indeterminate: false,
              list: response.data.platformRights
            };
            $scope.updateAllCheck(role.e.platformRights);
          }
        });
      }
    };

    $scope.ok = function () {
      // var postData = angular.copy( $scope.e );
      // postData.username = opts.username;
      var postData = packPostData();
      User.saveAuthority({
        dataRightsVOList: postData
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          $modalInstance.close();
        }
      });

    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };

    $scope.changeAllCheck = function (checkBoxes) {
      for (var i = checkBoxes.list.length - 1; i >= 0; i--) {
        checkBoxes.list[i].hasRights = checkBoxes.allChecked;
      }
      checkBoxes.indeterminate = false;
    };

    $scope.updateAllCheck =  function (checkBoxes) {
      var allChecked = true,
          allClear = true;
      var length = checkBoxes.list.length;
      for (var i = 0; i < length; i++) {
        var one = checkBoxes.list[i];
        if ( one.hasRights ) {
          allClear = false;
        } else {
          allChecked = false;
        }
      }

      if ( allChecked ) {
          checkBoxes.allChecked = true;
          checkBoxes.indeterminate = false;
        } else if( allClear ) {
          checkBoxes.allChecked = false;
          checkBoxes.indeterminate = false;
        } else {
          checkBoxes.allChecked = false;
          checkBoxes.indeterminate = true;
        }

    };

    function packPostData () {
      var postData = [];
      for (var i = $scope.roles.length - 1; i >= 0; i--) {
        var role = $scope.roles[i];
        if (role.e) {
          postData.push({
            username: opts.username,
            roleTag: role.roleTag,
            busiTypeRights: angular.copy(role.e.busiTypeRights),
            platformRights: angular.copy(role.e.platformRights.list),
            siteRights: angular.copy(role.e.siteRights.list)
          });
        }
      }

      return postData;
    }
  }
]);


});