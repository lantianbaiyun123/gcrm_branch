define([
  'app'
], function (app) {
  app.registerFactory('BenchmarkPrice', ['$http','APP_CONTEXT',
    function ($http,APP_CONTEXT) {
      return {
        getList: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'quote/findQuotationMainPage',
            // url: '/data/benchmarkPriceList.json',
            data: angular.toJson(paramObj)
          });
        },
        getDetail: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/findQuotationVOById',
            // url: '/data/benchmarkPriceDetail.json',
            params: paramObj
          });
        },
        checkConflict: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'quote/checkConflict',
            // url: '/data/benchmarkPriceCheckConflict.json',
            data: angular.toJson(paramObj)
          });
        },
        save: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'quote/submitQuotation',
            // url: '/data/benchmarkPriceListe.json',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [撤销标杆价]
         * @param  paramObj:{quotationMainId:6, returnType:1}, returnType=1返回标杆价详情
         * @return HttpPromise
         */
        withdraw: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/cancelQuotationApprove',
            params: paramObj
          });
        },
        /**
         * [作废标杆价]
         * @param  paramObj:{quotationMainId:6, returnType:1}, returnType=1返回标杆价详情
         * @return HttpPromise
         */
        nullify: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/cancelQuotation',
            params: paramObj
          });
        },
        /**
         * [标杆价审核催办]
         * @param  paramObj:{quotationMainId:6}
         * @return HttpPromise
         */
        sendReminder: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/remind/' + paramObj.quotationMainId
          });
        },
        /**
         * [当前标杆价]
         * @param  paramObj:{businessType:'CASH', advertisingPlatformId:'12'}, businessType='CASH'/'SALE'
         * @return HttpPromise
         */
        currentList: function (paramObj) {
          return $http({
            method: 'post',
            // url: '/data/benchmarkPriceCurrent.json',
            url: APP_CONTEXT + 'quote/findCurrentQuotation',
            // params: paramObj
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [当前标杆价当前用户可见平台列表]
         * @param  paramObj:{}
         * @return HttpPromise
         */
        currentPlatform: function (paramObj) {
          return $http({
            method: 'get',
            // url: '/data/benchmarkPriceCurrentPlatformUser.json',
            url: APP_CONTEXT + 'quote/findBusinessTypeByCurrentUser'
          });
        },
        /**
         * [标杆价审核信息]
         * @param  paramObj:{id:1234, activityId:4814839_act3}
         * @return HttpPromise
         */
        getApprovalInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'qouteapprovalinfo/view/' + paramObj.id + '/' + paramObj.activityId,
            // url: '/data/benchmarkPriceApprovalInfo.json',
            params: paramObj
          });
        },
        /**
         * [标杆价审核提交]
         * @param   paramObj:{
                      "record":{
                          "quoteMainId":1,
                          "taskId":"4814839_act3",
                          "taskName":"Submit quoMain",
                          "approvalStatus":1,
                          "approvalSuggestion":"ok,pass",
                          "processId":"1768505_gcrm_pkg_506_prs3",
                          "activityId":"4814839_act3"
                      },
                      "processId": "1768505_gcrm_pkg_506_prs3",
                      "activityId": "4814839_act3"
                    }
         * @return HttpPromise
         */
        submitApproval: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'quote/approval/saveApprovalRecord',
            // url: '/data/benchmarkPriceApproval.json',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * [标杆价审核记录]
         * @param  paramObj:{quotationMainId:31}
         * @return HttpPromise
         */
        approvalRecord: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/findApproveRecord',
            // url: '/data/benchmarkPriceApprovalRecord.json',
            params: paramObj
          });
        },
        /**
         * [标杆价修改记录]
         * @param  paramObj:{quotationMainId:31}
         * @return HttpPromise
         */
        modifyRecord: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'quote/modifyRecord',
            // url: '/data/benchmarkPriceModifyREcords.json',
            params: paramObj
          });
        },
        /**
         * [取所负责的业务类型]
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        getBusinessType: function (paramObj) {
          return $http({
            method: 'get',
            // url: '/data/businessType.json',
            url: APP_CONTEXT + 'quote/findBusinessType'
          });
        }
      };
    }
  ]);
});