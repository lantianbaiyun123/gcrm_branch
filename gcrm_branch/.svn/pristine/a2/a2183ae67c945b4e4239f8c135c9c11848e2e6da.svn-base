/**
 * AdProgram 广告方案 (Angular Service)
 *
 * --USAGES--
 *   1.basicSave( paramObj ,cbfn )
 *       保存基础信息
 *       {'hasContract':false,'advertiseSolutionView':{'advertiseSolution':{'id':71,'customerNumber':12345678,'operator':1}}}
 *   2.basicTempSave( paramObj ,cbfn )
 *       暂存基础信息
 *       {'hasContract':false,'advertiseSolutionView':{'advertiseSolution':{'customerNumber':12345678}}}
 *   3.basicGet(paramObj, cbfn)
 *       读取方案查看基础信息信息
 *   4.basicGetEdit(paramObj, cbfn)
 *       读取方案编辑基础信息信息
 */
define(['app'],
  function(app) {
    app.registerFactory('AdProgram', ['$http', '$log', 'APP_CONTEXT', 'Modal', '$filter',
      function($http, $log, APP_CONTEXT, Modal, $filter) {
        return {
          getDetail: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'adapprovalinfo/viewAdSolution',
              // url: '/data/adDetailInfo.json',
              params: paramObj
            });
          },
          getList: function(paramObj) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'adbaseinfo/querySolution',
              // url: '/data/adSolutionList.json',
              data: JSON.stringify(paramObj)
            });
          },
          getContentList: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'adsolution/content/queryAdContentList/' + paramObj.id
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
          basicTempSave: function (paramObj) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'adbaseinfo/save',
              data: JSON.stringify(paramObj)
            });
          },
          basicGet: function (paramObj) {
            return $http({
              method: 'get',
              // url: '../resources/data/preSave.json',
              url: APP_CONTEXT + 'adbaseinfo/view/' + paramObj.id
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
          submit: function(paramObj, cbfn) {
            $http({
              method: 'post',
              url: APP_CONTEXT + 'adbaseinfo/submitToApproval/' + paramObj.id
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          remove: function(paramObj, cbfn) {
            $http({
              method: 'post',
              url: APP_CONTEXT + 'adsolution/content/deleteAdsolutionContent/' + paramObj.id
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          infoGet: function(paramObj, cbfn) {
            if (paramObj.id && paramObj.activityId) {
              $http({
                method: 'get',
                url: APP_CONTEXT + 'adapprovalinfo/view',
                // url: '/data/adApprovalInfo.json',
                params: paramObj
              }).then(function(response) {
                cbfn(response.data);
              });
            } else {
              $log.error('id & activityId can not be empty');
            }
          },
          scheduleInfoGet: function(paramObj, cbfn) {
            if (paramObj.id) {
              $http({
                method: 'get',
                url: APP_CONTEXT + 'adapprovalinfo/schedule',
                // url: '../resources/data/adApprovalInfo.json',
                params: paramObj
              }).then(function(response) {
                cbfn(response.data);
              });
            } else {
              $log.error('id can not be empty');
            }
          },
          approvalRecordGet: function(paramObj, cbfn) {
            if (paramObj.id) {
              $http({
                method: 'get',
                url: APP_CONTEXT + 'ad/approval/queryRecord/' + paramObj.id,
                // url: '../resources/data/queryApprovalRecordByAdSolutionId.json',
                params: paramObj
              }).then(function(response) {
                cbfn(response.data);
              });
            } else {
              $log.error('id can not be empty');
            }
          },
          approvalRecordGetByContentId: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'ad/approval/queryContentRecord/' + paramObj.id,
              params: paramObj
            });
          },
          approvalInsertRecordGet: function(paramObj, cbfn) {
            $http({
              method: 'get',
              url: APP_CONTEXT + 'ad/approval/queryInsertedRecord/' + paramObj.id,
              // url: '../resources/data/queryApprovaInsertlRecordById.json',
              params: paramObj
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          approvalSubmit: function(paramObj, cbfn) {
            $http({
              method: 'post',
              url: APP_CONTEXT + 'ad/approval/save',
              // url: '',
              data: JSON.stringify(paramObj)
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          editRecordGet: function(paramObj, cbfn) {
            if (paramObj.id) {
              $http({
                method: 'get',
                url: APP_CONTEXT + 'modifyReord/queryAdModifyReocrds/' + paramObj.id,
                // url: '../resources/data/modifyRecords.json',
                params: paramObj
              }).then(function(response) {
                cbfn(response.data);
              });
            } else {
              $log.error('id can not be empty');
            }
          },
          editRecordGetByContentId: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'modifyReord/queryAdContentModifyReocrds/' + paramObj.id,
              // url: '../resources/data/modifyRecords.json',
              params: paramObj
            });
          },
          operatorChange: function(paramObj, cbfn) {
            $http({
              method: 'get',
              url: APP_CONTEXT + 'adbaseinfo/updateOperator/' + paramObj.adsolutionid + '/' + paramObj.ucid + '/' + paramObj.operator
                // url: '',
                //data : JSON.stringify(paramObj)
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          getApprovalRecordForContent: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'ad/approval/queryContentRecord/' + paramObj.id
            });
          },
          programChange: function(paramObj, cbfn) {
            $http({
              method: 'get',
              url: APP_CONTEXT + 'adapprovalinfo/changeAdSolution/' + paramObj.id
            }).then(function(response) {
              cbfn(response.data);
            });
          },
          findOperator: function(paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'adbaseinfo/findOperator/' + paramObj.id
            });
          }
        };
      }
    ]);
  });
