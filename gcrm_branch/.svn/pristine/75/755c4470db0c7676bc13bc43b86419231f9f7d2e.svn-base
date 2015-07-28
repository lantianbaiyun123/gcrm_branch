
/**
 * [CtrlBenchmarkPriceEdit]
 * cotroller for feature 'benchmarkPriceEdit'
 */
define([
  'app',
  '../_common/ytCommon',
  '../_directives/ytJqueryFileUpload',
  '../_directives/ytAjaxupload',
  '../_directives/ytPlaceholder',
  '../benchmarkPrice/BenchmarkPriceSubmitModal',
  '../_services/http/BenchmarkPrice'
], function (app) {
  app.registerController('CtrlBenchmarkPriceEdit', [
    '$scope',
    '$state',
    '$log',
    '$filter',
    '$timeout',
    'Modal',
    'BenchmarkPrice',
    'BenchmarkPriceSubmitModal',
  function ($scope, $state, $log, $filter, $timeout, Modal, BenchmarkPrice, BenchmarkPriceSubmitModal) {
    var priceParttern = new RegExp(/^[0-9]+([.]\d{1,2})?$/);  //数字，两位小数
    var ratioParttern = new RegExp(/^([1-9]\d?(\.\d[1-9]0*|\.[1-9]0*)?|0\.(\d{1,2})0*|0|100(\.0+)?)$/); //[0-100]数字，两位小数
    var ratioPartternNoZero = new RegExp(/^([1-9]\d?(\.\d[1-9]0*|\.[1-9]0*)?|0\.([1-9]\d?|0[1-9])0*|100(\.0+)?)$/); //(0-100]数字，两位小数

    init();

    //覆写ng-parttern,单价需大于0
    $scope.testPrice = (function() {
      return {
        test: function(value) {
          return priceParttern.test(value) && value>0;
        }
      };
    })();
    $scope.editSiteArea = [];
    $scope.minDate = new Date();
    $scope.uploadOpts = {
      uploadedText: $filter('translate')('ADD'),
      formatErrorText: "UPLOAD_FILE_TYPE_FORBIDDEN"
    };

    $scope.setPriceSite = function (price, site) {
      if ( site ) {
        price.site = site;
      } else {
        var length = $scope.editSiteArea.length;
        for (var i = 0; i < length; i++) {
          if ( !$scope.editSiteArea[i].selected ) {
            price.site = $scope.editSiteArea[i];
            break;
          }
        }
      }
      $scope.check.onePrice(price);
      $timeout(function () {
        updateSiteArea($scope.editSiteArea);
      }, 100);
    };

    $scope.$watch('quotationMain.businessType', function ( newVal, oldVal ) {
      if ( oldVal && $scope.quotationMain.priceType) {
        $scope.checkPlatform = true;
      }

      if ( newVal && newVal !== oldVal) {
        //如果只有1个平台，默认选中
        var platforms = $scope.constant.platform,
            length = platforms.length,
            count = -1;
        for (var i=0; i < length; i++) {
          if (platforms[i].businessType === newVal) {
            $scope.quotationMain.advertisingPlatformId = platforms[i].value;
            count++;
          }
        }
        if (count) {
          $scope.quotationMain.advertisingPlatformId = null;
        }

        //clear
        $scope.editSiteArea = [];
        if ( !$scope.isEdit ) {
          clearPriceList();
        }
      }
    });

    $scope.$watch('quotationMain.advertisingPlatformId', function ( newVal, oldVal ) {
      $scope.getSiteArea(newVal).then(function (result) {
        $scope.editSiteArea = result;
        if ( newVal !== oldVal ) {
          //删掉priceList中多出的尾行
          // $scope.quotationList.splice($scope.editSiteArea.length);

          //编辑状态不清空price
          if ( !$scope.isEdit ) {
            $scope.quotationList = [];
          }

          //保证至少一个空行
          if( $scope.quotationList.length === 0 &&
              $scope.editSiteArea
          ) {
            clearPriceList();
          }

          //清空每行标杆价的site
          // for (var i = $scope.quotationList.length - 1; i >= 0; i--) {
          //   $scope.quotationList[i].site = {};
          // }
        }

        //回填编辑状态下的site
        if ( $scope.isEdit ) {
          for (var j = $scope.editSiteArea.length - 1; j >= 0; j--) {
            if( $scope.editSiteArea[j].value === $scope.quotationList[0].siteId ) {
              $scope.quotationList[0].site = $scope.editSiteArea[j];
              break;
            }
          }
        }

        updateSiteArea();

      });
    });

    $scope.setRatio = function (price, which) {
      if ( price.ratio && ratioParttern.test(price.ratio[which]) ) {
        if ( which === 'ratioMine') {
          price.ratio.ratioThird = 0;
          price.ratio.ratioCustomer = Math.round(10000 - price.ratio.ratioMine*100)/100;
        } else if ( which === 'ratioCustomer') {
          price.ratio.ratioThird = Math.round(10000 - price.ratio.ratioMine*100 - price.ratio.ratioCustomer*100)/100;
          if (price.ratio.ratioThird < 0) {
            price.ratio.ratioThird = 0;
          }
        }
      }
    };

    $scope.addPrice = function () {
      if ( $scope.quotationList.length < $scope.editSiteArea.length ) {
        var price = oneEmptyPrice();
        // $scope.quotationList.push(price);
        // 在第一行前插入行
        $scope.quotationList.splice(0, 0, price);
        // $scope.setPriceSite( $scope.quotationList[$scope.quotationList.length - 1] );
        $scope.setPriceSite( $scope.quotationList[0] );
      }
    };

    $scope.removePrice = function (index) {
      $scope.quotationList.splice(index, 1);
      updateSiteArea();
    };

    $scope.removeMaterial = function (price, index) {
      price.quotationMaterialList.splice(index, 1);
      $scope.check.onePrice(price);
    };

    $scope.submitPrice = function () {
      $scope.quotationSubmitDisable = true;

      var checkGoodFn = function () {
        savePrice(submitDatas);
      };

      var cancelFn = function () {
        $scope.quotationSubmitDisable = false;
      };

      if (validate()) {
        var submitDatas = packPriceData();
        BenchmarkPriceSubmitModal.check(submitDatas, {}, checkGoodFn, cancelFn);
      } else {
        $scope.quotationSubmitDisable = false;
      }
    };

    function init() {
      if ($scope.constantIsSet) {
        enableBusinessType();
      } else {
        $scope.$watch('constantIsSet', function (newVal) {
          if (newVal) {
            enableBusinessType();
          }
        });
      }
    }

    //获取当前用户所负责的业务类型
    function enableBusinessType () {
      BenchmarkPrice.getBusinessType().success(function (response) {
        if (response.code === 200 && response.data ) {
          for (var i = $scope.constant.businessType.length - 1; i >= 0; i--) {
            var businessType = $scope.constant.businessType[i],
                enabledTypes = response.data;
            if (enabledTypes.indexOf(businessType.value) > -1 ) {
              businessType.enabled = true;
            }
          }
        }
      });
    }

    //一条未经编辑的初始化价格
    function oneEmptyPrice () {
      var price = {
        isUnifyedCPS: true,
        unit: {},
        quotationMaterialList: [],
        ratio: {
          ratioThird: 0
        }
      };
      price.unit.cps = [{}];
      //克隆行业类型对象
      for (var industryId in $scope.constant.industryType) {
        price.unit.cps.push({
          industryId: industryId,
          industryTypeName: $scope.constant.industryType[industryId].industryTypeName,
          parentId: $scope.constant.industryType[industryId].parentId
        });
      }
      return price;
    }

    function clearPriceList () {
      var price = oneEmptyPrice();
      $scope.quotationList = [price];
      if ($scope.editSiteArea.length === 1) {
        $scope.setPriceSite($scope.quotationList[0]);
      }
    }

    function updateSiteArea () {
      $scope.editSiteAreaFull = false;
      var selectedCount = 0;
      for (var i = $scope.editSiteArea.length - 1; i >= 0; i--) {
        $scope.editSiteArea[i].selected = false;
        for (var j = $scope.quotationList.length - 1; j >= 0; j--) {
          if ( $scope.quotationList[j].site &&
               $scope.quotationList[j].site.value === $scope.editSiteArea[i].value
          ) {
            $scope.editSiteArea[i].selected = true;
            selectedCount++;
          }
        }
      }
      if ( selectedCount === $scope.editSiteArea.length ) {
        $scope.editSiteAreaFull = true;
      }
    }

    function packPriceData () {
      var data = {},
          dateFormat = 'yyyy-MM-dd';

      //克隆quotationMain
      data.quotationMain = {};
      for (var one in $scope.quotationMain) {
        data.quotationMain[one] = $scope.quotationMain[one];
      }
      data.quotationMain.startTime = $filter('date')($scope.quotationMain.startTime, dateFormat);
      data.quotationMain.endTime = $filter('date')($scope.quotationMain.endTime, dateFormat);

      data.quotationList = [];
      var length = $scope.quotationList.length;
      for (var i = 0; i < length; i++) {
        data.quotationList[i] = {};
        data.quotationList[i].siteId = $scope.quotationList[i].site.value;
        data.quotationList[i].quotationMaterialList = $scope.quotationList[i].quotationMaterialList;
        // data.quotationList[i].quotationList = {};
        for (var key in $scope.quotationList[i][$scope.quotationMain.priceType]) {
          data.quotationList[i][key] = $scope.quotationList[i][$scope.quotationMain.priceType][key];
        }
        if ( $scope.quotationMain.priceType === 'unit' ) {
          data.quotationList[i].cps = [];
          if ( $scope.quotationList[i].isUnifyedCPS ) {
            data.quotationList[i].cps.push($scope.quotationList[i].unit.cps[0]);
          } else {
            data.quotationList[i].cps = $scope.quotationList[i].unit.cps.slice(1);
          }
        }
      }
      return data;
    }

    function validate () {
      var isPlatformGood = $scope.check.platform();
      var isEffectiveTimeGood = $scope.check.effectiveTime();
      var isPriceAllGood = true;
      $scope.onCheck = true;
      for (var i = $scope.quotationList.length - 1; i >= 0; i--) {
        $scope.quotationList[i].checkPrice = true;
        if (!$scope.check.onePrice($scope.quotationList[i])) {
          isPriceAllGood = false;
        }
      }
      return (isPlatformGood && isPriceAllGood && isEffectiveTimeGood);
    }

    $scope.check = {
      platform: function () {
        $scope.checkPlatform = true;
        return !!$scope.quotationMain.advertisingPlatformId;
      },
      effectiveTime: function () {
        $scope.checkEffectiveTime = true;
        return !(!$scope.quotationMain.startTime || !$scope.quotationMain.endTime || $scope.quotationMain.startTime>$scope.quotationMain.endTime);
      },
      onePrice: function (price) {
        var priceType = $scope.quotationMain.priceType;
        var checkPrice = {
          unit: function () {
            //如果填写了值，校验结果push到checked中表示参与校验
            var checked = [];
            for (var one in price.unit ) {
              var isGood = false;
              if(one === 'cps') {
                isGood = true;
                var shouldPushIn = false;//是否填写cps值
                var from = 0,
                    to = 0;
                if (!price.isUnifyedCPS) {
                  from = 1;
                  to = price.unit[one].length - 1;
                }
                for ( var i = from; i<=to; i++ ) {
                  //大于0并符合patten
                  // if ( !(price.unit[one][i].value && ratioPartternNoZero.test(price.unit[one][i].value)) ) {
                  if ( price.unit[one][i].value && !ratioPartternNoZero.test(price.unit[one][i].value) ) {
                    isGood = false;
                  }
                  //有任一项填写，cps参与校验
                  if (typeof price.unit[one][i].value !== 'undefined' && price.unit[one][i].value !== '' ) {
                    shouldPushIn = true;
                  }
                }
                if (shouldPushIn) {
                  checked.push(isGood);
                }
              } else {
                isGood = !!(price.unit[one].value && priceParttern.test(price.unit[one].value));
                //如填写值，该项参与校验
                if (typeof price.unit[one].value !== 'undefined' && price.unit[one].value !== '') {
                  checked.push(isGood);
                }
              }
              // checked.push(isGood);
            }
            //价格至少填一项否则校验不通过
            var isAllGood = !!checked.length;
            //已填写的需全部校验通过
            for (var j = checked.length - 1; j >= 0; j--) {
              if (!checked[j]) {
                isAllGood = false;
                break;
              }
            }
            price.priceIsGood = isAllGood;
            return isAllGood;
          },
          ratio: function () {
            var isAllGood = true,
                ratio = price.ratio;
                if (!ratio) {
                  isAllGood = false;
                }
            //如果我方为空或值非法，not good
            if ( !(ratio.ratioMine && ratioPartternNoZero.test(ratio.ratioMine)) ) {
              isAllGood = false;
            } else {
              //如果客户方有值，则校验其是否合法
              if ( ratio.ratioCustomer && !ratioParttern.test(ratio.ratioMine) ){
                isAllGood = false;
              }
              //如果第三方有值，则校验其是否合法
              if ( ratio.ratioThird && !ratioParttern.test(ratio.ratioMine) ){
                isAllGood = false;
              }
            }
            //三方比例和必须等于100
            var sum = Number(ratio.ratioMine) + (Number(ratio.ratioCustomer) || 0) + (Number(ratio.ratioThird) || 0);
            if ( sum != 100 ) {
              isAllGood = false;
            }

            price.priceIsGood = isAllGood;
            return isAllGood;
          },
          rebate: function () {
            var isAllGood = !!( price.rebate &&
                                ((price.rebate.ratioRebate ||
                                  price.rebate.ratioRebate ) &&
                                  ratioPartternNoZero.test(price.rebate.ratioRebate)) );
            price.priceIsGood = !!isAllGood;//可能rebate未输入任何值，为undefined,
            return isAllGood;
          },
          material: function () {
            return !!price.quotationMaterialList.length;
          },
          site: function () {
            return !!(price.site && price.site.value);
          }
        };
        price.onCheck = true;
        //该价格是否校验OK
        return (checkPrice.site() && checkPrice.material() && checkPrice[priceType]());
      }
    };

    function savePrice(submitDatas) {
      BenchmarkPrice.save(submitDatas).success(function (response) {
        if ( response.code === 200 ) {
          var count = response.data.length;
          var numbersText = response.data.join("、");
          var msgText = $filter('translate')('QUOTATION_SAVE_SUCCESS_TEXT', { count: count }) + numbersText;
          $scope.msgs.listAlerts.push({
            type: 'info',
            msg: msgText
          });
          Modal.success({
            content: $filter('translate')('QUOTATION_SUBMIT_SUCCESS'),
            timeOut: 3000
            }, function () {
              $state.go('benchmarkPriceManagement.list');
          });
          $scope.quotationSubmitDisable = false;
        }
      });
    }

  }]);
});

/** billingModel = [
 *    {i18nName:CPC, id:1, name:CPC, type:0}
 *    {i18nName:CPA, id:3, name:CPA, type:0}
 *    {i18nName:CPT, id:5, name:CPT, type:0}
 *    {i18nName:CPS, id:2, name:CPS, type:1}
 *    {i18nName:CPM, id:4, name:CPM, type:0}
 *  ]
 */
