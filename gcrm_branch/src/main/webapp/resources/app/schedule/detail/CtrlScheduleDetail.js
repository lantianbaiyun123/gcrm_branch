/**
 * [CtrlScheduleList description]
 * this is controller for feature 'schedule'
 */
define(['app',
    '../../_filters/ScheduleListStatusFilter',
    '../../_services/PageSet',
    '../../_services/http/Schedules'
  ],
  function (app) {
    app.registerController('CtrlScheduleDetail', ['$scope', 'APP_CONTEXT', '$http', '$filter', '$stateParams', 'PageSet', "Schedules", '$state', '$window',
      function ($scope, APP_CONTEXT, $http, $filter, $stateParams, PageSet, Schedules, $state, $window) {
        var adContentId;
        $scope.navToAdSolutionDetail = function () {
          if ( adContentId ) {
            $window.open('#/adContentDetail?id=' + adContentId);
            // $state.go('adContentDetail', {
            //   id: adContentId
            // });
          }
        };
        PageSet.set({
          activeIndex: 2,
          siteName: 'scheduleDetail'
        });

        /*Date Format*/
        var format = "yyyy-MM-dd";

        var scheduleId = $stateParams.scheduleId;
        if (scheduleId) {
          Schedules.getDetail({
            "scheduleId": scheduleId
          }, function (response) {
            if ( response.code === 200 ) {
              adContentId = response.data.adContentId;
              var tempData = response.data;
              // console.log(tempData.createTime.split(" "));
              var tempCreateDateStr = (tempData.createTime || "").split(" ")[0].split('-'),
                tempConfirmDateStr = (tempData.confirmTime || "").split(" ")[0].split('-');
              var tempCreateTime = tempData.createTime ? $filter('date')(new Date(tempCreateDateStr[0], tempCreateDateStr[1] - 1, tempCreateDateStr[2], "", "", ""), format) : tempData.createTime;
              var tempConfirmTime = tempData.confirmTime ? $filter('date')(new Date(tempConfirmDateStr[0], tempConfirmDateStr[1] - 1, tempConfirmDateStr[2], "", "", ""), format) : tempData.confirmTime;

              $scope.headerObj = {
                number: tempData.number,
                guodai: tempData.guodai,
                status: tempData.status,
                createTime: tempCreateTime,
                confirmTime: tempConfirmTime
              };

              $scope.baseInfo = {
                companyname: tempData.companyname,
                advertiser: tempData.advertiser,
                description: tempData.description,
                contractnum: tempData.contractnum
              };

              $scope.platAndTime = {
                adPlatformName: tempData.adPlatformName,
                siteId: tempData.siteId,
                channelName: tempData.channelName,
                areaName: tempData.areaName,
                positionName: tempData.positionName,
                periodDescription: (tempData.periodDescription || '').split(","),
                insertPeriodDescription: (tempData.insertPeriodDescription || '').split(","),
                billingModel: tempData.billingModel
              };
            }
          });
        }

      }
    ]);
  });