define( [ 'app' ], function ( app ) {
  app.registerFactory( 'User', [
    '$http',
    'APP_CONTEXT',
    function ( $http, APP_CONTEXT ) {
      function changeStatus ( dataObj ) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'user/changeStatus',
          data: angular.toJson( dataObj )
        });
      }
      return {
        /**
         * 获取用户列表
         * @param  {object} paramObj [description]
         * @return {http promise}
         */
        getList: function ( dataObj ) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'user/query',
            // url: '/data/userList.json',
            data: angular.toJson( dataObj )
          });
        },
        getRoleList: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'user/initQueryView',
            // url: '/data/roleList.json',
            params: paramObj
          });
        },
        save: function ( dataObj ) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'user/saveUser',
            data: angular.toJson( dataObj )
          });
        },
        disable: function ( dataObj ) {
          return changeStatus( dataObj );
        },
        enable: function ( dataObj ) {
          return changeStatus( dataObj );
        },
        getDetail: function ( paramObj ) {
          return $http({
            method: 'get',
            // url: '/data/userDetail.json'
            url: APP_CONTEXT + 'user/view/' + paramObj.id
          });
        },
        getAuthorityRoleList: function ( paramObj ) {
          return $http({
            method: 'get',
            // url: '/data/authorityRoleList.json',
            url: APP_CONTEXT + 'user/getRoleList',
            params: paramObj
          });
        },
        editAuthority: function (paramObj) {
          return $http({
            method: 'get',
            // url: '/data/authorityEdit.djson',
            // url: '/data/authorityEdit.json',
            url: APP_CONTEXT + 'user/editDataRights',
            params: paramObj
          });
        },
        queryAuthority: function ( paramObj ) {
          return $http({
            method: 'get',
            // url: '/data/authority.json',
            url: APP_CONTEXT + 'user/initQueryView',
            params: paramObj
          });
        },
        saveAuthority: function ( dataObj ) {
          return $http({
            method: 'post',
            // url: '/data/authoritySave.json',
            url: APP_CONTEXT + 'user/saveDataRights',
            data: angular.toJson( dataObj )
          });
        }
      };
    }
  ]);
});