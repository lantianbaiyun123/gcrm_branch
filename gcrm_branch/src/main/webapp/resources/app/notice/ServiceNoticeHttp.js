define([
  'app'
], function (app) {
  app.registerService('NoticeHttp', [
    '$http',
    'APP_CONTEXT',
  function ($http, APP_CONTEXT) {
    return {
      getList: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'notice/managelist',
          // url: '/data/noticeManageList.json',
          data: angular.toJson(paramObj)
        });
      },
      /**
       * 新建/编辑 公告
       * @param  {
          "notice": {
            "send": 1/0,
            "title": "hao123的一个公告",
            "content": "blablablablablablablabla",
            "receiveScope": "internal/external",
            "receivers": [
              {
                "email": "wujun@baidu.com",
                "id": 3,
                "realname": "吴俊",
                "role": "国代",
                "status": 1,
                "ucid": 10000000,
                "username": "wujun"
              },
              {
                "email": "dangqianyonghu@baidu.com",
                "id": 4,
                "realname": "当前用户",
                "role": "PM",
                "status": 1,
                "ucid": 1000,
                "username": "current"
              }
            ]
          }
        }
       * @return {[type]}          [description]
       */
      save: function (paramObj) {
        return $http({
          method: 'post',
          url: APP_CONTEXT + 'notice/save',
          // url: '/data/noticeDetail.json',
          data: angular.toJson(paramObj)
        });
      },
      getDetail: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/detail/' + paramObj.noticeId
          // url: '/data/noticeDetail.json'
        });
      },
      send: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/send/' + paramObj.noticeId
          // url: '/data/noticeDetail.json'
        });
      },
      getSuggestAdOwner: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/customersSuggest',
          params: paramObj
        });
      },
      getSuggestInnerUser: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/suggestUser',
          params: paramObj
        });
      },
      getReceiveList: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/receivelist'
          // url: '/data/noticeReceiveList.json'
        });
      },
      getRead: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'notice/read/' + paramObj.noticeId
          // url: '/data/noticeRead.json'
        });
      },
      read: function (paramObj) {
        return $http({
          method: 'get',
          // url: APP_CONTEXT + 'notice/read/' + paramObj.noticeId
          url: '/data/noticeRead.json'
        });
      }
    };
  }]);
});
