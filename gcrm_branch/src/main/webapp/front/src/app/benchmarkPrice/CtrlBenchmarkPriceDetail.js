/**
 * [CtrlBenchmarkPriceDetail]
 * cotroller for feature 'benchmarkPriceDetail'
 */
define([
  'app',
  '../_common/ytCommon',
  '../_services/PageSet',
  '../_services/http/BenchmarkPrice',
  '../record/BenchmarkPriceApprovalRecord',
  '../_directives/ytPopoverConfirm'
], function (app) {
    app.registerController('CtrlBenchmarkPriceDetail', [
        '$scope',
        '$stateParams',
        '$log',
        'PageSet',
        'BenchmarkPrice',
        'BenchmarkPriceApprovalRecord',
    function ($scope, $stateParams, $log, PageSet, BenchmarkPrice, BenchmarkPriceApprovalRecord) {
        PageSet.set({activeIndex:1,siteName:'benchmarkPriceDetail'});

        $scope.quotationMainId = $stateParams.id;
        $scope.isEdit = true;

        $scope.closeMsg = function(index) {
          $scope.msgs.detailAlerts.splice(index, 1);
        };

        $scope.modalApprovalRecord = function () {
          BenchmarkPriceApprovalRecord.show($scope.quotationMainId);
        };

        $scope.withdrawPrice = function () {
          BenchmarkPrice.withdraw({
            quotationMainId: $scope.quotationMainId,
            returnType:1
          }).success(function (response) {
            if ( response.code === 200 && response.data ) {
              renderDetail(response.data);
            } else {

            }
          });
        };

        $scope.nullifyPrice = function () {
          BenchmarkPrice.nullify({
            quotationMainId: $scope.quotationMainId,
            returnType:1
          }).success(function (response) {
            if ( response.code === 200 && response.data ) {
              renderDetail(response.data);
            } else {

            }
          });
        };

        var prepareEditData = {
          /**
           * [initCps 初始化CPS各行业空值对象]
           * @return {[type]} [description]
           */
          initCps: function () {
            $scope.quotationList[0].unit = {};
            // //CPS统一比例值
            $scope.quotationList[0].unit.cps = [{}];
            $scope.quotationList[0].isUnifyedCPS = true;
            //克隆各行业类型对象
            for (var id in $scope.constant.industryType) {
              var industryObj = {};
              for ( var key in $scope.constant.industryType[id] ) {
                industryObj[key] = $scope.constant.industryType[id][key];
              }
              $scope.quotationList[0].unit.cps.push(industryObj);
            }
          },
          unit: function (quotationList) {
            $scope.quotationList[0].unit = {};
            //CPS统一比例值为第一个对象
            $scope.quotationList[0].unit.cps = [{}];
            /**
             * idMap = { industryId: cpsIndex }
             */
            var idMap = {},
                cpsIndex = 1;
            for (var industryId in $scope.constant.industryType) {
              var industryTypeName = $scope.constant.industryType[industryId].industryTypeName,
                  parentId = $scope.constant.industryType[industryId].parentId;
              idMap[industryId] = cpsIndex++;
              $scope.quotationList[0].unit.cps.push({
                industryId: industryId,
                industryTypeName: industryTypeName,
                parentId:parentId
              });
            }

            var length = quotationList.length;
            for ( var i = 0; i < length; i++ ) {
              var billingModelName = $scope.constant.billingModel[quotationList[i].billingModelId].name.toLowerCase();
              if ( billingModelName === 'cps') {
                if ( quotationList[i].industryId >= 0 ) {
                  $scope.quotationList[0].unit.cps[idMap[quotationList[i].industryId]].id = quotationList[i].id;
                  $scope.quotationList[0].unit.cps[idMap[quotationList[i].industryId]].value = quotationList[i].ratioMine;
                } else {
                  $scope.quotationList[0].isUnifyedCPS = true;
                  $scope.quotationList[0].unit.cps[0].value = quotationList[i].ratioMine;
                }
              } else {
                $scope.quotationList[0].unit[billingModelName] = {};
                $scope.quotationList[0].unit[billingModelName].id = quotationList[i].id;
                $scope.quotationList[0].unit[billingModelName].value = quotationList[i].publishPrice;
              }
            }
          },
          ratio: function (quotationList) {
            $scope.quotationList[0].ratio = {};
            $scope.quotationList[0].ratio.id = quotationList[0].id;
            $scope.quotationList[0].ratio.ratioMine = quotationList[0].ratioMine;
            $scope.quotationList[0].ratio.ratioCustomer = quotationList[0].ratioCustomer;
            $scope.quotationList[0].ratio.ratioThird = quotationList[0].ratioThird;

            this.initCps();
          },
          rebate: function (quotationList) {
            $scope.quotationList[0].rebate = {};
            $scope.quotationList[0].rebate.id = quotationList[0].id;
            $scope.quotationList[0].rebate.ratioRebate = quotationList[0].ratioRebate;

            this.initCps();
          }
        };

        if ( $scope.constantIsSet ) {
            getDetail();
        } else {
          $scope.$on('constantReady', function (constant) {
            getDetail();
            $scope.constantIsSet = true;
          });
        }

        function getDetail () {
          BenchmarkPrice.getDetail({
            quotationMainId: $scope.quotationMainId
          }).success(function (response) {
            if ( response.code === 200 && response.data ) {
              renderDetail(response.data);
            } else {

            }
          });
        }

        function renderDetail (data) {
          $scope.IS_OWNER = data.quotationMainVO.isOwner;
          $scope.quotationMain = data.quotationMainVO.quotationMain;
          $scope.quotationMain.platformName = data.quotationMainVO.platformName;
          $scope.quotationMain.siteName = data.quotationMainVO.siteName;
          $scope.quotationMain.createrName = data.quotationMainVO.createrName;
          $scope.msgs.detailAlerts = [];
          if (data.quotationMainVO.taskInfoMessage) {
            var oneMsg = {
              type: "info",
              msg: data.quotationMainVO.taskInfoMessage
            };
            if ($scope.quotationMain.approveStatus === 'CANCEL') {
              oneMsg.type = "danger";
            }
            $scope.msgs.detailAlerts.push(oneMsg);
          }

          $scope.quotationList = [{}];
          prepareEditData[$scope.quotationMain.priceType](data.quotationViewList[0].quotationList);
          $scope.quotationList[0].quotationMaterialList = data.quotationViewList[0].quotationMaterialList;
          $scope.quotationList[0].siteId = $scope.quotationMain.siteId;

          if ( $scope.quotationMain.status=='INVALID' &&
                  ( $scope.quotationMain.approveStatus=='SAVING' ||
                    $scope.quotationMain.approveStatus=='CANCEL' )
          ) {
            reviseTime();
          }

        }

        function reviseTime () {
          var todayTime = getTodayTime();
          //如果起始时间早于当天，将其改为当天
          if ($scope.quotationMain.startTime<todayTime) {
            $scope.quotationMain.startTime = todayTime;
          }
          //如果结束时间早于当天，将其清空
          if ($scope.quotationMain.endTime<todayTime) {
            $scope.quotationMain.endTime = '';
          }
        }

        function getTodayTime () {
          var now = new Date(),
            today = new Date(now.getFullYear() + '/' + (now.getMonth() + 1)  + '/' + now.getDate());
          return today.getTime();
        }
    }]);
});
