define([
  'app'
], function (app) {
  app.registerFactory('ApprovalHistory', ['$http', 'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        getList: function (paramObj) {
          return $http({
            method: 'post',
            // url: APP_CONTEXT + 'material/queryAdSolutionContent',
            url: '/data/historyList.json',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [取物料详情基本信息]
         * @param  {[type]} {materialApplyId：1} [description]
         * @return {[type]}          [description]
         */
        getDetailInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/view',
            // url: '/data/materialDetailInfo.json',
            params: paramObj
          });
        },
        /**
         * [取物料单详情]
         * @param  paprmObj: {materialApplyId：1} [description]
         * @return {[type]}          [description]
         */
        getDetail: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/queryMaterialApplyDetail',
            // url: '/data/materialDetail.json',
            params: paramObj
          });
        }
      };
    }]);
});