/**
 * [CtrlBenchmarkPriceCurrent]
 * cotroller for feature 'benchmarkPriceCurrent'
 */
define(['app',
        '../_common/ytCommon',
        '../_services/PageSet',
        '../_services/http/BenchmarkPrice'
], function (app) {
    app.registerController('CtrlBenchmarkPriceCurrent', [
        '$scope',
        '$log',
        'PageSet',
        'BenchmarkPrice',
    function ($scope, $log, PageSet, BenchmarkPrice) {
        PageSet.set({activeIndex:1, siteName:'benchmarkPriceCurrent'});

        BenchmarkPrice.currentPlatform().success(function (response) {
          if ( response.code === 200 ) {
            $scope.businessTypes = response.data;
            if ($scope.businessTypes.length) {
              $scope.setBusinessType($scope.businessTypes[0]);
              $scope.selectPlatform($scope.businessTypes[0].platformList[0]);
            }
          }
        });

        $scope.setBusinessType = function (businessType) {
          $scope.currentBusinessType = businessType.businessTypeName;
          $scope.selectPlatform(businessType.platformList[0]);
        };

        $scope.selectPlatform = function (platform) {
          for (var one in $scope.businessTypes) {
            if ($scope.businessTypes[one].platformList && $scope.businessTypes[one].platformList.length) {
              for (var j = $scope.businessTypes[one].platformList.length - 1; j >= 0; j--) {
                $scope.businessTypes[one].platformList[j].active = false;
              }
            }
            platform.active = true;
          }
          getQuotationList(platform.id);
        };

        function getQuotationList (platformId) {
          BenchmarkPrice.currentList({
            businessType: $scope.currentBusinessType,
            advertisingPlatformId: platformId
          }).success(function (response) {
            if ( response.code === 200 ) {
              $scope.currentQuotation = response.data;
              var quotationList = $scope.currentQuotation.quotationList;
              var length = quotationList.length;
              for (var i = 0; i < length; i++) {
                var quotationOne = quotationList[i];
                if (quotationOne.cps && quotationOne.cps.length) {
                  var cpsList = quotationOne.cps,
                      cpsLength = cpsList.length;
                  if (cpsLength === 1 && !cpsList[0].industryName) {
                    quotationOne.unifiedCPS = cpsList[0];
                  } else {
                    quotationOne.differentCPS = {};
                    for (var j = 0; j < cpsLength; j++) {
                      var parentName = $scope.constant.industryType[cpsList[j].industryId].parentName;
                      if (!quotationOne.differentCPS[parentName]) {
                        quotationOne.differentCPS[parentName] = [];
                      }
                      quotationOne.differentCPS[parentName].push(cpsList[j]);
                    }
                  }
                }
              }
              console.log(quotationList);
            }
          });
        }
    }]);
});
