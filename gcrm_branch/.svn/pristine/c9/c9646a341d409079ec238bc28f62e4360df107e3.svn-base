define([
  'app'
], function (app) {
  app.registerFactory('Material', ['$http', 'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        getList: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'material/queryAdSolutionContent',
            // url: '/data/materialList.json',
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
            // url: '/data/materialApplyDetail.json',
            params: paramObj
          });
        },
        /**
         * [物料审核信息]
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        save: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'material/saveMaterialApplyDetail',
            // url: '/data/materialDetail.json',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [撤销物料]
         * @param  paramObj: {applyId: 12}
         * @return HttpPromise
         */
        withdraw: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/withdrawMaterialApply/' + paramObj.applyId
          });
        },
        /**
         * [作废物料]
         * @param  paramObj: {applyId: 12}
         * @return HttpPromise
         */
        nullify: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/cancelMaterialApply/' + paramObj.applyId
          });
        },
        /**
         * [物料审核催办]
         * @param  paramObj: {applyId: 12}
         * @return HttpPromise
         */
        sendReminder: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + '/material/reminders/' + paramObj.applyId
          });
        },
        /**
         * [物料审核信息]
         * @param  paramObj:{}
         * @return HttpPromise
         */
        getApprovalInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/approveview/' + paramObj.id + '/' + paramObj.activityId
            // url: '/data/showMaterialApproveView.json',
            // params: paramObj
          });
        },
        /**
         * [物料审核提交]
         * @param   paramObj:{
                      "adMaterialApplyId":1,
                      "taskId":"4814839_act3",
                      "activityId":"4814839_act3"
                      "approvalStatus":1,
                      "approvalSuggestion":"ok,pass",
                      "processId":"1768505_gcrm_pkg_506_prs3",
                    }
         * @return HttpPromise
         */
        submitApproval: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'material/approve',
            // url: '/data/materialDetailInfo.json',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [物料审核记录]
         * @param  paramObj:{materialApplyId:31}
         * @return HttpPromise
         */
        approvalRecord: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + '/material/findApproveRecord',
            // url: '/data/materialApproveRecord.json',
            params: paramObj
          });
        },
        /**
         * [物料修改记录]
         * @param  paramObj:{materialApplyId:31}
         * @return HttpPromise
         */
        modifyRecord: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'material/findModifyRecord',
            // url: '/data/benchmarkPriceModifyRecords.json',
            params: paramObj
          });
        },
        remove: function (paramObj) {
          var url = APP_CONTEXT + 'material/delete/' + paramObj.applyId;
          return $http.get(url);
        }
      };
    }]);
});