/**
 * [CtrlSchedule description]
 * this is controller for feature 'schdule'
 */
define([
  'app',
  '../../_common/ytCommon',
  '../../_services/PageSet',
  '../../_services/http/AdProgram',
  '../../_filters/AdSolutionTypeFilter',
  '../../_filters/RecordOperateTypeFilter',
  '../../_filters/PriceTypeFilter',
  '../../_filters/AdMaterialIfEmbedCodeFilter',
  '../../_filters/BoolValueFilter',
  '../../_filters/IndustryTypeFilter',
  '../../_filters/urlHttpFilter',
  '../../resourcePosition/PositionPropDetailModal',
  '../../_directives/periodLabel',
  './ScheduleModal'
], function (app) {
  app.registerController('CtrlSchedule', [
    '$scope',
    '$state',
    '$stateParams',
    '$timeout',
    '$location',
    '$anchorScroll',
    '$modal',
    '$filter',
    'PageSet',
    'Modal',
    'Utils',
    'AdProgram',
    'ScheduleModal',
  function ($scope, $state, $stateParams, $timeout, $location, $anchorScroll, $modal, $filter, PageSet, Modal, Utils, AdSolution, ScheduleModal) {
    // controller code here
    PageSet.set({activeIndex:5,siteName:'task'});

    if ($stateParams.adSolutionId) {
      $scope.taskRecord.adSolutionId =  $stateParams.adSolutionId.split('_')[0];
    }
    $scope.taskRecord.foreignKey =  $stateParams.adSolutionId;
    $scope.taskRecord.activityId = $stateParams.activityId;
    $scope.taskRecord.processId = $stateParams.processId;
    $scope.taskRecord.type = 'operation';
    $scope.solutionInfo = $scope.solutionInfo || {};

    if( $scope.taskRecord.adSolutionId &&
        $scope.taskRecord.activityId &&
        $scope.taskRecord.processId
    ) {
      AdSolution.scheduleInfoGet({
          id: $scope.taskRecord.foreignKey,
          activityId: $scope.taskRecord.activityId,
          processId: $scope.taskRecord.processId
      }, function (response) {
          if (response.code === 200) {
              $scope.solutionInfo = response.data;
              $scope.updateTask();
          } else if ( response.code > 250 ) {
              var msg = '[' + response.code + ']' + response.message;
              Modal.alert({content: msg});
          } else {
            $scope.updateTask();
          }
      });
    } else {
      $scope.updateTask(true);
    }

    $scope.makeSchedule = function () {
      var opt = getPositionInfo();
      ScheduleModal.show(opt, function () {
        Modal.success({
          title: $filter('translate')('SCHEDULE_SUBMIT_SUCCESS'),
          content: $filter('translate')('SCHEDULE_SYSTEM_JUMP'),
          timeOut: 3000
        }, function () {
          $scope.goNextTask();
        });
      });
    };

    function getPositionInfo () {
        var opt = {
          processInfo : {
            activityId: $scope.taskRecord.activityId,
            processId: $scope.taskRecord.processId
          },
          positionInfo : {
            adContentId: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.id,
            positionId: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.positionId,
            advertiser: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.advertiser,
            productName: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.productName,
            siteName: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.siteName,
            channelName: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.channelName,
            areaName: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.areaName,
            positionName: $scope.solutionInfo.approvalContentViews[0].adSolutionContent.positionName,
            salesAmount: $scope.solutionInfo.approvalContentViews[0].position.salesAmount,
            rotationType: $scope.solutionInfo.approvalContentViews[0].position.rotationType
          },
          nextAlways: true  //是否总是显示“下一步”
        };
        return opt;
    }
  }]);
});