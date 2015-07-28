define([
  'app'
], function(app) {
  app.registerFactory('AdHttp', ['$http', 'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        submit: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/submitToApproval/' + paramObj.id
          });
        },
        basicGetEdit: function(paramObj) {
          return $http({
            method: 'get',
            // url: '../resources/data/preSave.json',
            url: APP_CONTEXT + 'adbaseinfo/preSave',
            params: paramObj
          });
        },
        basicSave: function (paramObj) {
          return $http({
            method: 'post',
            // url: '../resources/data/preSave.json',
            url: APP_CONTEXT + 'adbaseinfo/submit',
            // params: paramObj
            data: JSON.stringify(paramObj)
              // data : JSON.stringify(paramObj)
          });
        },
        /**
         * 保存广告内容（自动/手动点击）
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        save: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/saveAdsolutionContent',
            data: angular.toJson(paramObj)
          });
        },
        /**
         * 批量保存广告内容（方案提交前）
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        saveContents: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/saveAdsolutionContentBatch',
            data: angular.toJson(paramObj)
          });
        },
        getDetail: function(paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adapprovalinfo/viewAdSolution',
            // url: '/data/adDetailInfo.json',
            params: paramObj
          });
        },
        get: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContent/' + paramObj.id
          });
        },
        getSingle: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContentById/' + paramObj.id
          });
        },
        deleMaterials: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/deleteMaterial/' + paramObj.id
          });
        },
        getBillingModel: function() {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryBillModels'
          });
        },
        getContentInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContentById/' + paramObj.id
          });
        },
        cancelAd: function (paramObj) {
          //{id: adId}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adapprovalinfo/withdrawAD',
            params: paramObj
          });
        },
        removeAd: function (paramObj) {
          //{id: adId}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adbaseinfo/delete/' + paramObj.id
          });
        },
        cancelContent: function (paramObj) {
          //{id : contentId}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adapprovalinfo/withdrawSingleContent',
            params: paramObj
          });
        },
        // confirmContentSchedule: function (paramObj) {
        //   return $http({
        //     method: 'get',
        //     // url: '/data/confirmSchedule.json',
        //     url: APP_CONTEXT + 'adapprovalinfo/confirmSchedule',
        //     params: paramObj
        //   });
        // },
        /**
         * 变更广告内容提交审核
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        contentApproval: function (paramObj) {
          return $http({
            method: 'post',
            // url: APP_CONTEXT + 'adsolution/content/submitAdcontentToApproval/' + paramObj.adSolutionContent.id + '/' + paramObj.approvalType,
            url: APP_CONTEXT + 'adsolution/content/submitAdcontentToApproval/' + paramObj.adSolutionContent.id,
            data: angular.toJson(paramObj)
          });
        },
        contentTerminate: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/stopToAdContent/' + paramObj.id
          });
        },
        contentRemove: function (paramObj) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'adsolution/content/deleteAdsolutionContent/' + paramObj.id
            });
          },
        // reSchedule: function (paramObj) {
        //   return $http({
        //     method: 'post',
        //     url: APP_CONTEXT + 'process/reStartBidding/' + paramObj.id
        //   });
        // },
        findContactPO: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/findContractPo/' + paramObj.id
          });
        },
        findContractNumberPo: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/findContractNumberPo/' + paramObj.id + '/' + paramObj.contractNumber
          });
        },
        revokeContract: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/revokeContract/' + paramObj.id
          });
        },
        createOtherContract: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/createOtherContract/' + paramObj.id + '/' + paramObj.contractNumber + '/' + paramObj.contractType
          });
        },
        createSingleContract: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/createSingleContract/' + paramObj.id
          });
        },
        overdue: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/contractOverdue?id=' + paramObj.id
          });
        },
        press: function (paramObj) {
          //{id: adId}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/remind/' + paramObj.id
          });
        },
        modifyAd: function (paramObj) {
          //{id: adId}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/modifyAdSoulutionStatus/' + paramObj.id
          });
        },
        /**
         * 方案变更
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        changeAdSolution: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/changeAdSolution/' + paramObj.id
          });
        },
        /**
         * 方案变更取消
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        cancelChangeAdSolution: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/cancelChangeAdSolution/' + paramObj.id
          });
        },
        /**
         * 变更PO
         * @param  {[type]} paramObj [description]
         * @return {[type]}          [description]
         */
        changePO: function (paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/changePo/' + paramObj.adcontentId + '/' + paramObj.contractNumber
          });
        },
        queryOccupation: function (paramObj) {
          return $http({
            method: 'post',
            // url: '/data/insertDateByDatePeriod.json',
            url: '/gcrm/occupation/combinePeriods',
            data: angular.toJson(paramObj)
          });
        },
        pubApplySubmit: function (paramObj){
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adcontent/applyOnline/submit',
            data: angular.toJson(paramObj)
          });
        },
        pubApplyInfo: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adcontent/applyOnline/viewOnlineApply/' + paramObj.applyId
            // url: '/data/applyInfo.json'
          });
        },
        pubApplyWithdraw: function (paramObj) {
          return $http({
            method: 'get',
            // url: '/data/applyInfo.json'
            url: APP_CONTEXT + 'adcontent/applyOnline/withdrawnOnlineApply/' + paramObj.applyId
          });
        },
        pubApplyReminder: function (paramObj) {
          return $http({
            method: 'get',
            // url: '/data/applyInfo.json'
            url: APP_CONTEXT + 'adcontent/applyOnline/reminders/' + paramObj.applyId
          });
        }
      };
    }
  ]);
});
