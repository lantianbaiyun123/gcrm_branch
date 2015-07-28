define([
  'app'
], function(app) {
  app.registerFactory('Ad', ['$http', 'APP_CONTEXT',
    function($http, APP_CONTEXT) {
      return {
        save: function(paramObj, cbfn) {
          $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/saveAdsolutionContent',
            data: JSON.stringify(paramObj)
          }).then(function(response) {
            cbfn(response.data);
          });
        },
        get: function(paramObj, cbfn) {
          $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContent/' + paramObj.id
          }).success(function(result) {
            cbfn(result);
          });
        },
        getSingle: function(paramObj, cbfn) {
          $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContentById/' + paramObj.id
          }).success(function(result) {
            cbfn(result);
          });
        },
        deleMaterials: function(paramObj, cbfn) {
          $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/deleteMaterial/' + paramObj.id
          }).success(function(result) {
            cbfn(result);
          });
        },
        getBillingModel: function() {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryBillModels'
          });
        },
        getContentInfo: function(paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryAdContentById/' + paramObj.id
          });
        },
        cancelAd: function(paramObj) {
          //{id: adId}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adapprovalinfo/withdrawAD',
            params: paramObj
          });
        },
        cancelContent: function(paramObj) {
          //{id : contentId}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adapprovalinfo/withdrawSingleContent',
            params: paramObj
          });
        },
        confirmContentSchedule: function(paramObj) {
          return $http({
            method: 'get',
            // url: '/data/confirmSchedule.json',
            url: APP_CONTEXT + 'adapprovalinfo/confirmSchedule',
            params: paramObj
          });
        },
        contentApproval: function(paramObj, cbfn) {
          $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/submitAdcontentToApproval/' + paramObj.adSolutionContent.id + '/' + paramObj.approvalType,
            data: JSON.stringify(paramObj)
          }).then(function(response) {
            cbfn(response.data);
          });
        },
        contentTerminate: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adsolution/content/stopToAdContent/' + paramObj.id
          });
        },
        reSchedule: function(paramObj) {
          // { id: adContentId }
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'process/reStartBidding/' + paramObj.id
          });
        },
        findContactPO: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/findContractPo/' + paramObj.id
              // method: 'get',
              // url: '/data/noContract.json'
          });
        },
        findContractNumberPo: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/findContractNumberPo/' + paramObj.id + '/' + paramObj.contractNumber
          });
        },
        revokeContract: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/revokeContract/' + paramObj.id
          });
        },
        createOtherContract: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/createOtherContract/' + paramObj.id + '/' + paramObj.contractNumber + '/' + paramObj.contractType
          });
        },
        createSingleContract: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/createSingleContract/' + paramObj.id
          });
        },
        overdue: function(paramObj) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/contractOverdue?id=' + paramObj.id
          });
        },
        press: function(paramObj) {
          //{id: adId}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/remind/' + paramObj.id
          });
        },
        modifyAd: function(paramObj) {
          //{id: adId}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/modifyAdSoulutionStatus/' + paramObj.id
          });
        },
        changeAdSolution: function(paramObj) {
          /** 方案变更
           * @param {
           *          id: adId
           *        }
           */
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/changeAdSolution/' + paramObj.id
          });
        },
        cancelChangeAdSolution: function(paramObj) {
          /** 方案变更取消
           * @param {
           *          id: adId
           *        }
           */
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adapprovalinfo/cancelChangeAdSolution/' + paramObj.id
          });
        },
        changePO: function(paramObj) {
          /**
           * 变更PO
           * @param {Object} {
           *        adcontentId: adcontentId
           *        contractNumber: contractNumber
           * }
           */
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'adbaseinfo/changePo/' + paramObj.adcontentId + '/' + paramObj.contractNumber
          });
        },
        queryInsertDates: function(paramObj, cbfn) {
          var defaults = {
            pageNo: 1,
            pageSize: 10
          }; // default params for get orgnization list
          return $http({
            method: 'post',
            //url: '../resources/data/insertDateByDatePeriod.json', //for test data
            url: '/gcrm/occupation/queryInsertDates', //should switch to real url
            data: JSON.stringify(paramObj) //post 请求，
          });
        }
      };
    }
  ]);
});
