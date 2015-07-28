/**
 * [CtrlApproval description]
 * this is controller for feature 'approvalMain'
 */
define([
  'app',
  '../../_common/ytCommon',
  '../../_services/PageSet',
  '../../_services/http/AdProgram',
  '../../_filters/RecordOperateTypeFilter',
  '../../_filters/PriceTypeFilter',
  '../../_filters/AdSolutionTypeFilter',
  '../../_filters/AdMaterialIfEmbedCodeFilter',
  '../../_filters/BoolValueFilter',
  '../../_filters/IndustryTypeFilter',
  '../../_filters/urlHttpFilter',
  '../../_filters/DatePeriodFilter',
  '../../record/SolutionModifyRecord',
  '../../record/SolutionApprovalRecord',
  '../../resourcePosition/PositionPropDetailModal',
  '../../_directives/periodLabel'
], function (app) {
  app.registerController('CtrlApproval', [
    '$scope',
    '$state',
    '$stateParams',
    '$timeout',
    '$location',
    '$anchorScroll',
    '$modal',
    '$filter',
    '$log',
    'PageSet',
    'Modal',
    'Utils',
    'AdProgram',
    'SolutionModifyRecord',
    'SolutionApprovalRecord',
    'PositionPropDetailModal',
  function ($scope, $state, $stateParams, $timeout, $location, $anchorScroll, $modal, $filter, $log, PageSet, Modal, Utils, AdSolution, SolutionModifyRecord, SolutionApprovalRecord, PositionPropDetailModal) {
      PageSet.set({activeIndex:5,siteName:'task'});
      var foreignKeyArray = $stateParams.adSolutionId.split('_');

      if ($stateParams.adSolutionId) {
          $scope.taskRecord.adSolutionId =  $stateParams.adSolutionId.split('_')[0];   //方案ID
      }
      $scope.taskRecord.foreignKey =  $stateParams.adSolutionId;   //方案ID
      $scope.taskRecord.activityId = $stateParams.activityId; //流程进度ID
      $scope.taskRecord.type = 'approval';

      $scope.solutionInfo = $scope.solutionInfo || {};//方案信息
      $scope.form = $scope.form || {};
      $scope.form.approvalStatus = $scope.form.approvalStatus || null;//审批意见
      $scope.form.approvalSuggestion = $scope.form.approvalSuggestion || '';//审批其他说明
      $scope.approvalRecords = $scope.approvalRecords || {}; //审批记录
      $scope.editRecords = $scope.editRecords || [];  //修改记录

      if( $scope.taskRecord.adSolutionId && $scope.taskRecord.activityId) {
        AdSolution.infoGet({
          id: $scope.taskRecord.foreignKey,
          activityId: $scope.taskRecord.activityId
        }, function (response) {
          if (response.code === 200) {
            $scope.solutionInfo = response.data;
            $scope.taskRecord.autoNext = false;
            $scope.updateTask();
          //code 205 任务已经完成
          } else if (response.code === 205) {
            if (!$scope.taskRecord.autoNext) {
              // var msg = '[' + response.code + ']' + response.message + '\n';
              var msg = $filter('translate')('TASK_HAS_BEEN_DONE');
              Modal.alert({content: msg});
              $scope.taskRecord.foreignKey = null;
              $scope.taskRecord.adSolutionId = null;
              $scope.taskRecord.activityId = null;
              $scope.updateTask();
            } else {
              $scope.taskRecord.foreignKey = null;
              $scope.taskRecord.adSolutionId = null;
              $scope.taskRecord.activityId = null;
              $scope.updateTask(true);
            }
          } else {
            $scope.updateTask();
          }
        });
      } else {
        $scope.updateTask(true);
      }

      $scope.modalApprovalRecords = function () {
        var contentId = isSingleContent();
        SolutionApprovalRecord.show($scope.taskRecord.adSolutionId, contentId);
      };

      $scope.modalEditRecords = function () {
        var contentId = isSingleContent();
        SolutionModifyRecord.show($scope.taskRecord.adSolutionId, contentId, $scope.solutionInfo.role);
      };

      $scope.PositionProp = function (contentView) {
        var modalDatas = angular.copy(contentView.position);
        var content = contentView.adSolutionContent;

        modalDatas.positionStr = contentView.adSolutionContent.channelName;
        if ( content.areaName ) {
          modalDatas.positionStr = modalDatas.positionStr + ' - ' + content.areaName;
        }
        if ( content.positionName ) {
          modalDatas.positionStr = modalDatas.positionStr + ' - ' + content.positionName;
        }

        PositionPropDetailModal.show({
          modalDatas: modalDatas
        });
      };

      //to-do，联动包含相同日期的允许插单radio
      $scope.allowInsertChange = function (curInsert, infoViews) {
        // $log.info(infoViews);
        // $log.info(insertInfo);
        if ( ~~curInsert.allowInsert ) {
          var curDates = curInsert.insert;
          for (var i = infoViews.length - 1; i >= 0; i--) {
            var insertInfos = infoViews[i].insertInfos;
            for (var j = insertInfos.length - 1; j >= 0; j--) {
              var insertInfo = insertInfos[j],
                  dates = insertInfo.insert;
              if (curInsert !== insertInfo) {
                for (var k = curDates.length - 1; k >= 0; k--) {
                  if ( dates.indexOf(curDates[k]) > -1 ) {
                    insertInfo.allowInsert = Math.abs(~~curInsert.allowInsert - 1);
                  }
                }
              }
            }
          }
        }
      };

      $scope.submitApproval = function () {
        $scope.checkApprovalStatus = true;
        $scope.submitDisabble = true;   //禁用提交按钮
        //check审核意见、说明
        if ( $scope.form.approvalStatus !== 1 && $scope.form.approvalStatus !== 0 ) {
          return false;
        } else if ( $scope.form.approvalStatus === 0 ) {
          $scope.checkApprovalSuggestion = true;
          if(!$scope.form.approvalSuggestion) {
            return false;
          }
        }

        var data = packApprovalData();

        //如果意见为同意，且为非PM物料审核、非区域管理总监审核，check插单记录，
        var checkInsertGood = true;
        if ( ~~$scope.form.approvalStatus === 1 &&
            ( $scope.solutionInfo.role !== 'pm' &&
              $scope.solutionInfo.role !== 'country_leader') ) {
          $scope.checkAllowInsert = true;

          angular.forEach(data.insertRecords, function (record, index) {
              if ( typeof record.allowInsert === 'undefined') {
                checkInsertGood = false;
                return false;
              }
          });
        //否则忽略插单审批
        } else {
          data.insertRecords = [];
        }

        if ( checkInsertGood )  {
          AdSolution.approvalSubmit(data, function (response) {
            if ( response.code === 200 ) {
              $scope.submitSuccessful = true; //disable submit button
              $scope.taskRecord.autoNext = true;

              Modal.success({
                  title: $filter('translate')('APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS'),
                  content: $filter('translate')('APPROVAL_SYSTEM_JUMP'),
                  timeOut: 3000
              }, function () {
                  $scope.submitSuccessful = false;
                  $scope.goNextTask();
              });

            //轮播位插单冲突，提交失败
            } else if ( response.code === 205 ) {
              var jsonData = JSON.parse(response.message);
              angular.forEach(jsonData, function (conflictContentIds, adContentId) {
                  angular.forEach($scope.solutionInfo.approvalContentViews, function (adContentView, index) {
                      if( adContentView.adSolutionContent.id === ~~adContentId ) {
                          angular.forEach(conflictContentIds, function (conflictContentId, index) {
                              angular.forEach(adContentView.infoViews, function (infoView, index) {
                                  angular.forEach(infoView.insertInfos, function (insertInfo, index) {
                                      if( insertInfo.adContentId === ~~conflictContentId ) {
                                          insertInfo.conflict = true;
                                      }
                                  });
                              });
                          });
                      }
                  });
              });
              $scope.submitDisabble = false;
            } else {
              $scope.submitDisabble = false; //enable submit button
              $scope.taskRecord.foreignKey = null;
              $scope.taskRecord.adSolutionId = null;
              $scope.taskRecord.activityId = null;
              $scope.updateTask();
            }
          });
        } else {
          $scope.submitDisabble = false;
        }
      };

      function goNextAuto () {
        $timeout( function () {
          $scope.goNextTask();
        });
      }

      function isSingleContent () {
        var contentId = 0;
        if ( foreignKeyArray.length === 2 ) {
          contentId = foreignKeyArray[1];
        }
        return contentId;
      }

      function packApprovalData () {
        var data = {
          activityId: $scope.taskRecord.activityId,
          processId: $scope.taskRecord.processId,
          actDefId: $scope.taskRecord.actDefId,
          record: {
            adSolutionId: $scope.taskRecord.adSolutionId,
            taskId: $scope.taskRecord.taskId,
            approvalStatus: $scope.form.approvalStatus,
            approvalSuggestion: $scope.form.approvalSuggestion
          },
          insertRecords: []
        };

        if ( foreignKeyArray.length === 2 ) {
          data.record.adContentId = foreignKeyArray[1];
        }

        angular.forEach($scope.solutionInfo.approvalContentViews, function (content, index) {
          var contentId = content.adSolutionContent.id;
          angular.forEach(content.infoViews, function (insertView, index) {
            var period = insertView.date;
            angular.forEach(insertView.insertInfos, function (insertInfo, index) {
              insertInfo.conflict = false;
              if ( insertInfo.businessAllow === 1 || $scope.solutionInfo.role === 'cash_leader' || $scope.solutionInfo.role === 'starter_superior' ) {
                insertRecord = {
                    adsolutionContentId: contentId,
                    insertedAdsolutionContentId: insertInfo.adContentId,
                    insertPeriod: period,
                    allowInsert: insertInfo.allowInsert
                };
                data.insertRecords.push(insertRecord);
              //如果前道审批不允许插单，默认为不允许插单
              } else if( insertInfo.businessAllow === 0 &&
                         $scope.solutionInfo.role !== 'pm' &&
                         $scope.solutionInfo.role !== 'cash_leader' &&
                         $scope.solutionInfo.role !== 'country_leader' ) {
                insertRecord = {
                    adsolutionContentId: contentId,
                    insertedAdsolutionContentId: insertInfo.adContentId,
                    insertPeriod: period,
                    allowInsert: 0
                };
                data.insertRecords.push(insertRecord);
              }
            });
          });
        });
        return data;
      }
  }]);
});