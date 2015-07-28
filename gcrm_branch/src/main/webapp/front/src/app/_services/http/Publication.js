define([
  'app'
], function (app) {
  app.registerFactory('Publication', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      /**
       * [取当前用户的筛选器列表]
       * @return {[type]} [description]
       */
      getUserConditions: function () {
        return  $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/findConditionResult'
          // url: '/data/publicationMyConditions.json'
        });
      },
      /**
       * [取未处理列表]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getList: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'publish/findPublishList',
          // url: '/data/publicationList.json',
          data: angular.toJson( paramObj )
        });
      },
      /**
       * [取未处理二级列表-详情]
       * @param  {[type]} paramObj:{publishNumber： 1234567} [description]
       * @return {[type]}          [description]
       */
      getDetail: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/findPublishDateByPublishNumber',
          // url: '/data/publicationDetail.json',
          params: paramObj
        });
      },
      /**
       * [取已处理列表]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getDoneList: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'publish/findPublishDoneList',
          // url: APP_CONTEXT + 'publish/findPublishDoneList',
          // url: '/data/publicationDoneList.json',
          data: angular.toJson(paramObj)
        });
      },
      /**
       * [取已处理二级列表-详情]
       * @param  {[type]} paramObj:{publishNumber： 1234567} [description]
       * @return {[type]}          [description]
       */
      getDoneDetail: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/findPublishDetailByPublishNumber',
          // url: APP_CONTEXT + 'publish/findPublishRecordDetail',
          // url: '/data/publicationDoneDetail.json',
          params: paramObj
        });
      },
      /**
       * [上线]
       * @param  {[type]} paramObj:{id： 1234567} [description]
       * @return {[type]}          [description]
       */
      publish: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publication/publish/' + paramObj.id
          // url: '/data/publicationDetail.json',
          // params: paramObj
        });
      },
      /**
       * [强制上线]
       * @param  {[type]} paramObj:{
       *                    id： 1234567
       *                    type: [online_approval, offline_attachment, none],
       *                    approvalUr: 'http://jdjfdjfj',
       *                    fileUrl: 'http://jdfjdfjdjfdjf',
       *                    uploadFilename: 'dsfsdfdfddf',
       *                    downloadFilename: 'dfdfdfdfd'
                          }
       * @return {[type]}          [description]
       */
      forcePublish: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'publication/forcePublish',
          // url: '/data/publicationDetail.json',
          data: angular.toJson(paramObj)
        });
      },
      /**
       * [下线]
       * @param  {[type]} paramObj:{id： 1234567} [description]
       * @return {[type]}          [description]
       */
      publishFinish: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publication/unpublish/' + paramObj.id
          // url: '/data/publicationDetail.json',
          // params: paramObj
        });
      },
      /**
       * [终止投放]
       * @param  {[type]} paramObj:{number： 1234567} [上线单编号]
       * @return {[type]}          [description]
       */
      terminate: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publication/terminate/' + paramObj.number
          // url: '/data/publicationDetail.json',
          // params: paramObj
        });
      },
      /**
       * [物料上线]
       * @param  {[type]} paramObj:{number： 1234567} [上线单编号]
       * @return {[type]}          [description]
       */
      materialUpdate: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publication/materialUpdate/' + paramObj.number
          // url: '/data/publicationDetail.json',
          // params: paramObj
        });
      },
      /**
       * [催办提交物料]
       * @param  {[type]} paramObj:{publishId: 12} [上线单编号]
       * @return {[type]}          [description]
       */
      sendReminder: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/remindersPublish/' + paramObj.publishId
          // url: '/data/publicationDetail.json',
          // params: paramObj
        });
      },
      /**
       * 查询当前人员能看哪些平台
       */
      getAvailablePlatform: function () {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/findPlatform'
          // url: '/data/getAvailablePlatform.json'
        });
      },
      /**
       * 获取人员配置列表
       * @return {[type]} [description]
       */
      getMemberList: function ( paramObj ) {
        return  $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/findPublishOwner',
          // url: '/data/member.json',
          params: paramObj
        });
      },
      /**
       * 添加人员
       */
      addMember: function ( paramObj ) {
        //gcrm/publish/addPublishOwner?id=1&channelId=351&ucid=10000000
        return  $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/addPublishOwner',
          // url: '/data/response.json',
          params: paramObj
        });
      },
      /**
       * 删除人员
       */
      removeMember: function ( paramObj ) {
        //publish/deletePublishOwner?id=1&ucid=1000
        return  $http({
          method: 'get',
          url: APP_CONTEXT + 'publish/deletePublishOwner',
          // url: '/data/response.json',
          params: paramObj
        });
      }

    };
  }]);
});