define([
      'app',
      '../_services/Select2Suggest',
      '../_common/ytCommon',
      '../_directives/ytJqueryFileUpload',
      '../_directives/ytAjaxupload',
      '../record/SolutionApprovalRecord',
      '../gcrm-util',
      '../_filters/IndustryTypeFilter',
      '../_filters/PortionFilter'
      ], function(app) {
app.registerController( 'CtrlAdContent',[
  '$scope',
  'Ad',
  '$stateParams',
  'Customer',
  'Select2Suggest',
  '$filter',
  'Industry',
  '$log',
  '$timeout',
  function ( $scope, Ad, $stateParams, Customer, Select2Suggest, $filter, Industry, $log, $timeout ) {
    //公司名称下拉suggest for ad owner,只有直客、非直客
    $scope.optionCustomer = Select2Suggest.getCustomerOptionForAdOwner({allowInput: true});
  }
]);

  /*
    每个内容的controller
   */
  app.registerController('CtrlAdContentItem', [
    '$scope',
    '$filter',
    'Modal',
    'BillModel',
    'Ad',
    'PublishPrice',
    'GCRMUtil',
    'SolutionApprovalRecord',
    'Industry',
    'Utils',
    '$timeout',
    '$log',
    'Position',
    function ( $scope, $filter, Modal, BillModel, Ad, PublishPrice, GCRMUtil, SolutionApprovalRecord, Industry, Utils, $timeout, $log, Position ) {
    if ($scope.stateCustomerStraight) {
      $scope.ad.CustomersName = $scope.customerStraight;
    }
    function findName(data, id, name) {
      var i = 0;
      data = data || [];
      if (arguments.length == 3) {
        for (; i < data.length; i++) {
          if (data[i].id == id) {
            if (data[i][name]) {
              return data[i][name];
            } else {
              return '';
            }
          }
        }
      } else {
        for (; i < data.length; i++) {
          if (data[i].id == id) {
            return data[i];
          }
        }
      }
    }
    $scope.ad.quotes = $scope.ad.quotes || {};
    $scope.ad.quotes.quoteTatioThird = 0;
    $scope.ad.quotes.reachEstimate = '0';
    //timeBuckets 时间段,
    $scope.ad.timeBuckets = [{
      fromMinDate: new Date(),
      toMinDate: new Date()
    }];
    $scope.ad.adDaysEles = [];
    var postAdTime = function(positionId, periods, insertDate) {
      //查询插单时间
      function resolvePeriods(periods) {
        for (var i = periods.length - 1; i >= 0; i--) {
          if ( !periods[i].from || !periods[i].to ) {
            periods.splice(i, 1);
            continue;
          }
          if (angular.isDate(periods[i].from)) {
            periods[i].from = periods[i].from.getTime();
          }
          if (angular.isDate(periods[i].to)) {
            periods[i].to = periods[i].to.getTime();
          }
        }
      }
      resolvePeriods(periods);
      var postData = {
        positionId: positionId.id,
        periods: periods,
        insertDate: insertDate
      };
      //查询插单时间的时候，如果是方案变更之后提交，需要把原内容id带过去，以过滤原内容的插单时间
      if ( $scope.ad.oldContentId ) {
        postData = angular.extend( postData, { oldContentId: $scope.ad.oldContentId } );
      }
      Ad.queryInsertDates(postData).success(function(response) {
        if (response.code === 200) {
          $scope.ad.timeBuckets = response.data.datePeriod;
          $scope.ad.adDaysEles = response.data.insertDate;
          $scope.ad.totalDays = response.data.totalDays;
          var insertDays = 0;
          if ( $scope.ad.adDaysEles ) {
            var adTimeCheckallModel = $scope.ad.adDaysEles.length;
            for (var i = $scope.ad.adDaysEles.length - 1; i >= 0; i--) {
              if (!$scope.ad.adDaysEles[i].checked) {
                if (adTimeCheckallModel) {
                  adTimeCheckallModel = false;
                }
              } else {
                insertDays++;
              }
            }
            $scope.ad.adTimeCheckallModel = !!adTimeCheckallModel;
          }
          var timeBuckets = $scope.ad.timeBuckets;
          if ( timeBuckets && timeBuckets.length ) {
            for (var j = timeBuckets.length - 1; j >= 0; j--) {
              timeBuckets[j].toMaxDate = response.data.maxDate;
              timeBuckets[j].fromMaxDate = timeBuckets[j].to;
              timeBuckets[j].fromMinDate = new Date();
              timeBuckets[j].toMinDate = timeBuckets[j].from;
            }
          } else {
            $scope.ad.timeBuckets = [{}];
          }
          $scope.insertDays = insertDays;
        }
      });
    };
    $scope.addTimeBuckets = function(ad) {
      ad.adTimeCheckallModel = false;
      $scope.ad.positionChangeValidator = false;
      $scope.ad.timeBuckets.push({
        fromMinDate: new Date(),
        toMinDate: new Date()
      });
    };
    $scope.delTimeBuckets = function(time, ad, index) {
      //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
      if ( $scope.ad.calendarFutureOnly ) {
        if ( time.from < Utils.getTodayZero().getTime() ) {
          return false;
        }
      }
      if ($scope.ad.timeBuckets.length > index) {
        $scope.ad.timeBuckets.splice(index, 1);
      }
      if ( !time.from || !time.to ) {
        return false;
      }
      postAdTime(ad.positionByAreaSelection, $scope.ad.timeBuckets, $scope.ad.adDaysEles);
    };

    $scope.adTimeCheckall = function(checked) {
      var insertDays = 0;
      angular.forEach($scope.ad.adDaysEles,
      function(value, key) {
        value.checked = checked;
        if ( !! value.checked) {
          insertDays++;
        }
      });
      $scope.insertDays = insertDays;
    };
    $scope.adTimeCancelCheckall = function(ele, ad) {
      ele.checked = !ele.checked;

      var flag = !!$scope.ad.adDaysEles.length;
      var insertDays = 0;
      angular.forEach($scope.ad.adDaysEles,
      function(value, key) {
        if (!value.checked) {
          flag = false;
        } else {
          insertDays++;
        }
      });
      $scope.insertDays = insertDays;
      ad.adTimeCheckallModel = flag;
    };
    $scope.startTimeChange = function(ad, index) {
      ad.adTimeCheckallModel = false;
      var time = $scope.ad.timeBuckets[index];
      // time.from = new Date(time.from).getTime();
      //var from =
      time.toMinDate = new Date(time.from).getTime(); //$filter('date')(from, 'yyyy-MM-dd');
      //time.toMinDate = time.from ? time.from : new Date();
      if ( !! time.to) {
        //postAdTime(time.from, time.to, index);
        postAdTime(ad.positionByAreaSelection, $scope.ad.timeBuckets, $scope.ad.adDaysEles);
      }
    };

    $scope.endTimeChange = function(ad, index) {
      ad.adTimeCheckallModel = false;
      var time = $scope.ad.timeBuckets[index];
      // time.to = new Date(time.to).getTime();
      //time.to = $filter('date')(time.to, 'yyyy-MM-dd')
      time.fromMaxDate = new Date(time.to).getTime();
      if ( !! time.from) {
        //postAdTime(time.from, time.to, index);
        postAdTime(ad.positionByAreaSelection, $scope.ad.timeBuckets, $scope.ad.adDaysEles);
      }
    };

    //物料信息 价格种类 计费方式 初始化变量
    $scope.ad.selection = '';

    //获得投放平台
    Position.getPlatformList4Radio({
      id: 1
    }).success(function(data) {
      if (data.code == "200") {
        $scope.ad.products = data.data;
        if ($scope.ad.products.length > 1) {
          // $scope.Terraceschecked = false;
        } else {
          // $scope.Terraceschecked = true;
          var newVal = $scope.ad.products[0].id;
          getAdCountry(newVal);
          //取消监听
          //deregChannelWatch();
        }
      }
    });

    //监听投放平台
    $scope.adProduct = function() {
      $scope.ad.siteByProductId = '';
      getAdCountry($scope.ad.adProductId);
      $scope.ad.channelBySiteSelection = '';
      $scope.channelBySiteEl = false;
      channelBySiteElChange();
      $scope.ad.channelBySites = [];
      $scope.ad.priceType = '';
    };

    //监听投放地点
    $scope.channelBySiteEl = false;
    $scope.siteByProductChange = function() {
      $scope.channelBySiteEl = true;
      channelBySiteElChange();
      $scope.ad.positionByAreaSelection = '';
      materialTypeReset();
      ChannelBySite($scope.ad.siteByProductId);
      $scope.ad.priceType = '';
    };

    //监听投放频道
    $scope.channelChange = function() {
      if ($scope.ad.channelBySiteSelection) {
        getAreaByChannel($scope.ad.channelBySiteSelection);

      } else {
        $scope.ad.areaByChannels = [];
        $scope.ad.positionByAreas = [];
        channelChangePriceTypeDataReset();
        $scope.ad.priceType = '';
      }
      $scope.ad.areaByChannelSelection = '';
      $scope.ad.positionByAreaSelection = '';

    };

    //监听投放区域
    $scope.areachange = function() {
      if ($scope.ad.areaByChannelSelection) {
        getPositionByArea($scope.ad.areaByChannelSelection);
        // $scope.ad.positionGuide = $scope.ad.areaByChannelSelection.hasGuideFile;
      } else {
        $scope.ad.positionByAreas = [];
        $scope.ad.selection = '';
        channelChangePriceTypeDataReset();
      }
      $scope.ad.positionByAreaSelection = '';
    };

    //监听投放位置
    $scope.positionChange = function() {
      if ($scope.ad.positionByAreaSelection) {
        cpcCountTotalPrice();
      } else {
        channelChangePriceTypeDataReset();
        $scope.ad.priceType = '';
      }
      materialTypeReset();
      $scope.ad.positionChangeValidator = false;
      $scope.ad.materialValidator = false;
    };

    //监听计费方式
    $scope.$watch('ad.selection', function(newVal, oldVal) {
      if ( !!newVal ) {
        var adProductId = $scope.ad.adProductId,
        siteByProductId = $scope.ad.siteByProductId;
        $scope.ad.industrySelected = undefined;//清空行业类型在计费方式切换的时候
        // $log.warn(100000);
        PublishPriceFn(adProductId, siteByProductId, newVal);
        if (newVal == 2) {
          if ($scope.ad.quotes.reachEstimate == null && $scope.ad.quotes.quoteTatioThird == null) {
            $scope.ad.quotes.reachEstimate = 0;
            $scope.ad.quotes.quoteTatioThird = 0;
          }
        }
      }
      $scope.ad.quotesValidator = false;
    });

    $scope.$watch( 'ad.quotes.cpsCustomersType', function( newVal, oldVal ) {
      if ( !!newVal ) {
        $scope.ad.sharedRevenue = true;
        var quotesLength = $scope.quotesData.length;
        for (var i = 0; i < quotesLength; i++) {
          var quotes = $scope.quotesData[i];
          if (quotes.industryType == newVal) {
            if (quotes.ratioMine) {
              $scope.ad.quotesMine = quotes.ratioMine;
              break;
            } else {
              $scope.ad.quotesMine = '';
            }
          } else {
            $scope.ad.quotesMine = '';
          }
        }
      }
    });

    //监听客户报价
    $scope.$watch('ad.quotes.customers', function( newVal, oldVal ) {
      if ( newVal ) {
        var cpcId = $scope.ad.selection;
        if (cpcId === 1) {
          cpcCountTotalPrice();
        } else {
          cptCountTotalPrice();
        }
      } else {
        $scope.ad.quotes.cptCotalPrice = '';
        $scope.ad.quotes.cpcCotalPrice = '';
      }
    });

    //监听投放总天数
    $scope.$watch('ad.totalDays',
    function(newVal, oldVal) {
      if ( !! newVal) {
        cptCountTotalPrice();
        cpcCountTotalPrice();
        cpmCountTotal();
      } else {
        $scope.ad.quotes.cpmCountTotal = '';
        $scope.ad.quotes.cpcCotalPrice = '';
        $scope.ad.quotes.cptCotalPrice = '';
      }
    });

    //监听cpm日投放量
    $scope.$watch('ad.quotes.dayTotal',
    function(newVal, oldVal) {
      if (newVal || newVal === 0) {
        cpmCountTotal();
      } else {
        $scope.ad.quotes.cpmCountTotal = '';
      }
    });

    //获得投放地点
    var getAdCountry = function(newVal) {
      Position.getSiteList({
        productId: newVal
      }).success(function (data) {
        if (data.code === 200) {

          if (data.data.length) {
            $scope.ad.siteByProducts = data.data;
          } else {
            $scope.ad.siteByProducts = [];
          }
        }
      });
    };

    //价格种类
    BillModel.get({},
    function(data) {
      if (data.code === 200) {
        $scope.atTypes = data.data.result;
      }
    });

    //投放平台地点变动 价格种类状态
    var channelBySiteElChange = function() {
      $scope.ad.priceType = '';
      $scope.ad.selection = '';
      $scope.ad.channelBySiteSelection = '';
      $scope.ad.areaByChannelSelection = '';
      $scope.ad.positionByAreaSelection = '';
      $scope.ad.hiddenElValidator = false;
      $scope.ad.areaByChannels = [];
      $scope.ad.positionByAreas = [];
    };

    //获得投放频道
    var ChannelBySite = function(newVal) {
      var adProductId = $scope.ad.adProductId;
      Position.getChannelList({
        productId: adProductId,
        siteId: newVal
      }).success(function (data) {
        if (data.code === 200) {
          $scope.ad.channelBySites = data.data;
        }
      });
    };

    //获得投放区域
    var getAreaByChannel = function(newVal) {
      Position.getAreaList({
        parentId: newVal
      }).success(function(data) {
        if (data.code === 200) {
          $scope.ad.areaByChannels = data.data;
        }
      });
    };

    //获取投放位置
    var getPositionByArea = function(newVal) {
      Position.getAreaList({
        parentId: newVal.id
      }).success(function(data) {
        if (data.code === 200) {
          $scope.ad.positionByAreas = data.data;
        }
      });
    };

    //位置为未选择状态 计费方式数据重置
    var channelChangePriceTypeDataReset = function() {
      $scope.ad.selection = '';
      $scope.ad.quotes.customers = '';
      $scope.ad.quotes.discount = '';
      $scope.ad.quotes.cptCotalPrice = '';
      $scope.ad.quotes.cpcCotalPrice = '';
      $scope.ad.quotesMine = '';
      $scope.ad.quotes.quoteTatioMine = '';
      $scope.ad.quotes.quoteTatioMt = '';
      $scope.ad.conditionDesc = '';
      $scope.ad.quotes.cpsCustomersType = '';
      $scope.ad.quotes.cpmCountTotal = '';
      $scope.ad.sharedRevenue = false;
      $scope.ad.quotes.adCpBudget = '';
      $scope.ad.quotes.reachEstimate = 0;
      $scope.ad.quotes.quoteTatioThird = 0;
    };

    //物料信息重置
    var materialTypeReset = function() {
      $scope.ad.codeStatues = 0;
      $scope.ad.materialTitle = '';
      $scope.ad.materialUrl = '';
      $scope.ad.advertiseMaterials = [];
      $scope.ad.codeContent = '';
      $scope.ad.timeBuckets = [{}];
      $scope.ad.adDaysEles = [];
      $scope.ad.adTimeCheckallModel = '';
      $scope.ad.totalDays = '';
      $scope.insertDays = '';
    };

    //(分成)实际分成比例 交互code
    //(分成) 我方blur
    $scope.quoteTatioMine = function() {
      var quoteTatioMine = $scope.ad.quotes.quoteTatioMine;
      var tempQuoteTatioThird = $scope.ad.quotes.quoteTatioThird;
      if (!parseInt(tempQuoteTatioThird, 10)) {
        if (quoteTatioMine || quoteTatioMine === 0) {
          quoteTatioMine = Number(quoteTatioMine);
          if (quoteTatioMine || quoteTatioMine === 0) {
            $scope.ad.quotes.quoteTatioMt = (100 - quoteTatioMine).toFixed(2);
            $scope.ad.quotes.quoteTatioMine = quoteTatioMine.toFixed(2);
          } else {
            $scope.ad.quotes.quoteTatioMt = '';
          }
        } else {
          $scope.ad.quotes.quoteTatioMt = '';
          $scope.ad.quotes.quoteTatioMine = '';
        }
      } else {
        $scope.ad.quotes.quoteTatioMine = Number(quoteTatioMine).toFixed(2);
      }
    };
    //(分成) 客户blur
    $scope.quoteTatioMt = function() {
      var quoteTatioMt = $scope.ad.quotes.quoteTatioMt;
      var tempQuoteTatioThird = $scope.ad.quotes.quoteTatioThird;
      if (!parseInt(tempQuoteTatioThird, 10)) {
        if (quoteTatioMt || quoteTatioMt === 0) {
          quoteTatioMt = Number(quoteTatioMt);
          if (quoteTatioMt || quoteTatioMt === 0) {
            $scope.ad.quotes.quoteTatioMine = (100 - quoteTatioMt).toFixed(2);
            $scope.ad.quotes.quoteTatioMt = quoteTatioMt.toFixed(2);
          } else {
            $scope.ad.quotes.quoteTatioMine = '';
          }
        } else {
          $scope.ad.quotes.quoteTatioMt = '';
          $scope.ad.quotes.quoteTatioMine = '';
        }
      } else {
        $scope.ad.quotes.quoteTatioMt = Number(quoteTatioMt).toFixed(2);
      }
    };
    //第三方 blur
    $scope.quoteTatioThird = function() {
      var quoteTatioMt = Number($scope.ad.quotes.quoteTatioThird);
      if (quoteTatioMt) {
        $scope.ad.quotes.quoteTatioThird = quoteTatioMt.toFixed(2);
      }
    };

    $scope.ad.codeStatues = 0;

    //获取分成数据
    var getQuote = function( adProductId, siteByProductId ) {
      PublishPrice.post({
        advertisingPlatformId: adProductId,
        siteId: siteByProductId
      }).success(function (response) {
        if ( response.code === 200 ) {
          var data = response.data.result;
          if (data && data.length > 0) {
            $scope.ad.quotes.ratioCustomer = data[0].ratioCustomer ? data[0].ratioCustomer : 0;
            $scope.ad.quotes.ratioMine = data[0].ratioMine ? data[0].ratioMine : 0;
            $scope.ad.quotes.ratioThird = data[0].ratioThird ? data[0].ratioThird : 0;
          } else {
            $scope.ad.quotes.ratioCustomer = '';
            $scope.ad.quotes.ratioMine = '';
            $scope.ad.quotes.ratioThird = '';
          }
        }
      });
    };

    //监听客户行业三类
    $scope.ad.quotesMineState = true;
    $scope.ad.sharedRevenue = false;

    publishPricesState = false;
    $scope.quotesData = [];
    $scope.ad.codeContent = '';
    //获取刊例价数据 和计费方式
    var PublishPriceFn = function(adProductId, siteByProductId, newVal) {
      PublishPrice.post({
        advertisingPlatformId: adProductId,
        siteId: siteByProductId,
        billingModelId: newVal
      }, function( response ) {
        if (response.code === 200) {
          if ( response.data.result &&  response.data.result.length > 0) {
            $scope.quotesData = response.data.result;
            $scope.ad.quotes.publishPrices = response.data.result[0].publishPrice;
            publishPricesState = true;
            $scope.ad.dollerIcon = true;
          } else {
            $scope.ad.quotes.publishPrices = '';
            publishPricesState = false;
            $scope.ad.dollerIcon = false;
            $scope.ad.quotes.discount = 1;//未备案刊例价时折扣自动填写1，用户可修改。
          }
          cptCountTotalPrice();
          cpcCountTotalPrice();
        }
      });
    };

    //客户报价输入 计算折扣
    $scope.customersQuote = function() {
      if (publishPricesState) {
        var customersQuote = $scope.ad.quotes.customers;
        if (customersQuote) {
          customersQuote = Number(customersQuote);
          var publishPrices = $scope.ad.quotes.publishPrices;
          if (publishPrices) {
            if (customersQuote) {
              $scope.ad.quotes.discount = (customersQuote / publishPrices).toFixed(2);
            } else {
              $scope.ad.quotes.discount = '';
            }
          }
        } else {
          $scope.ad.quotes.discount = '';
          $scope.ad.quotes.cptCotalPrice = '';
          $scope.ad.quotes.cpcCotalPrice = '';
        }
      }
    };

    //折扣 输入 计算客户报价
    $scope.discountQuote = function() {
      if (publishPricesState) {
        var discountQuote = $scope.ad.quotes.discount;
        if (discountQuote) {
          discountQuote = Number(discountQuote);
          var publishPrices = $scope.ad.quotes.publishPrices;
          if (discountQuote) {
            $scope.ad.quotes.customers = (discountQuote * publishPrices).toFixed(2);
          } else {
            $scope.ad.quotes.customers = '';
          }
        } else {
          $scope.ad.quotes.customers = '';
          $scope.ad.quotes.cptCotalPrice = '';
          $scope.ad.quotes.cpcCotalPrice = '';
        }
      }
    };

    //cpt总价计算
    var cptCountTotalPrice = function() {
      var customersQuote = Number($scope.ad.quotes.customers),
      totalDays = $scope.ad.totalDays;
      if (totalDays && customersQuote) {
        $scope.ad.quotes.cptCotalPrice = totalDays * customersQuote;
      }
    };

    //cpc总价计算
    var cpcCountTotalPrice = function() {
      var customersQuote = $scope.ad.quotes.customers,
      cpcClickNum = $scope.ad.positionByAreaSelection.click;
      if (customersQuote && cpcClickNum) {
        customersQuote = Number(customersQuote);
        var totalDays = $scope.ad.totalDays;
        if (customersQuote && totalDays) {
          $scope.ad.quotes.cpcCotalPrice = (totalDays * customersQuote * cpcClickNum).toFixed(2) || '';
        } else {
          $scope.ad.quotes.cpcCotalPrice = '';
        }
      }
    };

    //cpm总投放量计算
    var cpmCountTotal = function() {
      var dayTotal = Number($scope.ad.quotes.dayTotal),
      totalDays = $scope.ad.totalDays;
      if (dayTotal && totalDays) {
        $scope.ad.quotes.cpmCountTotal = dayTotal * totalDays;
      } else {
        $scope.ad.quotes.cpmCountTotal = '';
      }
    };

    //reset 实际三方分成比例
    var resetActualQuoteData = function() {
      $scope.ad.quotes.quoteTatioMt = '';
      $scope.ad.quotes.quoteTatioMine = '';
      $scope.ad.quotes.quoteTatioThird = 0;
    };

    //价格种类选择
    $scope.adPrice = function() {
      $scope.ad.quotesValidator = false;
      channelChangePriceTypeDataReset();
    };
    $scope.adBonus = function() {
      //清除价格种类数据
      channelChangePriceTypeDataReset();
      $scope.ad.selection = '';
      $scope.ad.quotesValidator = false;
      var adProductId = $scope.ad.adProductId,
      siteByProductId = $scope.ad.siteByProductId;
      if (adProductId && siteByProductId) {
        getQuote(adProductId, siteByProductId);
      }
    };
    $scope.ad.codeContentVaChange = function() {
      $scope.ad.codeContentValidator = false;
    };
    $scope.ad.deleCodeContent = function() {
      $scope.ad.codeContent = '';
    };

    //保存
    $scope.adContentSave = function(formState, contentIndex) {
      formState = formState.$valid;
      $scope.ad.contentSave = true;
      $scope.ad.hiddenElValidator = true;
      $scope.ad.positionChangeValidator = true;
      $scope.ad.quotesValidator = true;
      // $scope.ad.materialValidator = true;
      // $scope.ad.codeContentValidator = true;
      //remove this because meterial no longer required
      // if ( ~~$scope.ad.positionByAreaSelection.materialType !==3 &&
      //     (~~$scope.ad.positionByAreaSelection.materialType === 0 ||
      //     ~~$scope.ad.positionByAreaSelection.materialType === 2) && !$scope.ad.advertiseMaterials.length ) {
      //   return false;
      // }
      if (formState) {

        if ($scope.ad.priceType == 1 || $scope.ad.selection == 2) {
          var ratioTotalPrice = Number($scope.ad.quotes.quoteTatioMine) + Number($scope.ad.quotes.quoteTatioMt) + Number($scope.ad.quotes.quoteTatioThird);
          if (ratioTotalPrice != 100) {
            Modal.alert({
              content: $filter('translate')('AD_CONTENT_RATIOTOTALPRICE_ERROR')
            });
            return;
          }
        }

        var postDataDefaults = {
          adSolutionContent: {
            adSolutionId: $scope.programId,
            advertiser: $scope.ad.CustomersName.value,
            description: $scope.ad.companyDescribe,
            productId: $scope.ad.adProductId,
            siteId: $scope.ad.siteByProductId,
            channelId: $scope.ad.channelBySiteSelection,
            areaId: $scope.ad.areaByChannelSelection.id,
            positionId: $scope.ad.positionByAreaSelection.id,
            materialType: ~~$scope.ad.positionByAreaSelection.materialType
          }
        };
        if ( $scope.ad.CustomersName.data ) {
          postDataDefaults.adSolutionContent.advertiserId = $scope.ad.CustomersName.data;
        }
        var priceType = Number($scope.ad.priceType);
        var reachEstimate = +$scope.ad.quotes.reachEstimate;
        if (priceType) {
          angular.extend(postDataDefaults, {
            advertiseQuotation: {
              priceType: 1,
              productRatioMine: $scope.ad.quotes.ratioMine,
              productRatioCustomer: $scope.ad.quotes.ratioCustomer,
              productRatioThird: $scope.ad.quotes.ratioThird,
              ratioMine: $scope.ad.quotes.quoteTatioMine,
              ratioCustomer: $scope.ad.quotes.quoteTatioMt,
              ratioThird: $scope.ad.quotes.quoteTatioThird,
              reachEstimate: !!reachEstimate,
              ratioConditionDesc: $scope.ad.conditionDesc
            }
          });
        } else {
          switch ( $scope.ad.selection ) {

            //cpc
            case 1:
              angular.extend(postDataDefaults, {
                advertiseQuotation: {
                  priceType: 0,
                  billingModelId: 1,
                  publishPrice: $scope.ad.quotes.publishPrices,
                  customerQuote: $scope.ad.quotes.customers,
                  trafficAmount: $scope.ad.positionByAreaSelection.pv,
                  clickAmount: $scope.ad.positionByAreaSelection.click,
                  discount: $scope.ad.quotes.discount,
                  budget: $scope.ad.quotes.adCpBudget,
                  totalPrice: $scope.ad.quotes.cpcCotalPrice
                }
              });
              break;

              //cpa
            case 3:
              angular.extend(postDataDefaults, {
                advertiseQuotation: {
                  priceType: 0,
                  billingModelId: 3,
                  publishPrice: $scope.ad.quotes.publishPrices,
                  customerQuote: $scope.ad.quotes.customers,
                  discount: $scope.ad.quotes.discount,
                  budget: $scope.ad.quotes.adCpBudget
                }
              });
              break;

              //cpt
            case 5:
              angular.extend(postDataDefaults, {
                advertiseQuotation: {
                  priceType: 0,
                  billingModelId: 5,
                  publishPrice: $scope.ad.quotes.publishPrices,
                  customerQuote: $scope.ad.quotes.customers,
                  discount: $scope.ad.quotes.discount,
                  budget: $scope.ad.quotes.adCpBudget,
                  totalPrice: $scope.ad.quotes.cptCotalPrice
                }
              });
              break;

              //cps
            case 2:
              angular.extend(postDataDefaults, {
                advertiseQuotation: {
                  priceType: 0,
                  billingModelId: 2,
                  industryType: $scope.ad.industrySelected,
                  productRatioMine: $scope.ad.quotesMine,
                  ratioMine: $scope.ad.quotes.quoteTatioMine,
                  ratioCustomer: $scope.ad.quotes.quoteTatioMt,
                  ratioThird: $scope.ad.quotes.quoteTatioThird,
                  reachEstimate: !!reachEstimate,
                  ratioConditionDesc: $scope.ad.conditionDesc
                }
              });
              break;

              //cpm
            case 4:
              angular.extend(postDataDefaults, {
                advertiseQuotation: {
                  priceType: 0,
                  billingModelId: 4,
                  publishPrice: $scope.ad.quotes.publishPrices,
                  customerQuote: $scope.ad.quotes.customers,
                  discount: $scope.ad.quotes.discount,
                  dailyAmount: $scope.ad.quotes.dayTotal,
                  totalAmount: $scope.ad.quotes.cpmCountTotal,
                  budget: $scope.ad.quotes.adCpBudget
                }
              });
              break;
          }
          if (!publishPricesState) {
            postDataDefaults.advertiseQuotation.publishPrice = '';
          }
        }

        //物料信息判断
        switch ( ~~$scope.ad.positionByAreaSelection.materialType ) {

          //图片
          case 0:
            postDataDefaults.adSolutionContent.materialUrl = $scope.ad.materialUrl;
            postDataDefaults.advertiseMaterials = $scope.ad.advertiseMaterials;
            break;

          //文字
          case 1:
            postDataDefaults.adSolutionContent.materialTitle = $scope.ad.materialTitle;
            postDataDefaults.adSolutionContent.materialUrl = $scope.ad.materialUrl;
            break;

          //图片+文字
          case 2:
            postDataDefaults.adSolutionContent.materialTitle = $scope.ad.materialTitle;
            postDataDefaults.adSolutionContent.materialUrl = $scope.ad.materialUrl;
            postDataDefaults.advertiseMaterials = $scope.ad.advertiseMaterials;
            break;
        }

        //投放时间
        postDataDefaults.periods = $scope.ad.timeBuckets;
        postDataDefaults.insertDate = $scope.ad.adDaysEles;

        if ($scope.adSolutionContentId) {
          postDataDefaults.adSolutionContent.id = $scope.adSolutionContentId;
          postDataDefaults.advertiseQuotation.id = $scope.advertiseQuotationId;
        }

        //oldContentId,如果有，传过去
        if ( $scope.ad.oldContentId ) {
          postDataDefaults.adSolutionContent.oldContentId = $scope.ad.oldContentId;
        }
        //如果有上传代码，参数加入代码
        if ( $scope.ad.materailCodeUploaded ) {
          postDataDefaults.advertiseCodeMaterial = $scope.ad.materailCodeUploaded;
          postDataDefaults.advertiseCodeMaterial.adSolutionContentId = $scope.adSolutionContentId;
        }
        //save
        Ad.save( postDataDefaults, function ( response ) {
          if (response.code === 200) {
            $scope.ad.review = true;
            // $scope.ad.reviewData = response.data;
            //reset state editing
            $scope.$parent.$parent.$parent.stateEditing = false;
            var adContentId = response.data.adSolutionContent.id;
            Ad.getSingle({
              id: adContentId
            }, function ( response ) {
              $scope.ad.reviewData = response.data;
              $scope.ad.canUpdate = true;
              $scope.adSolutionContentId = response.data.adSolutionContent.id;
              $scope.ad.advertiseMaterials = response.data.advertiseMaterials;
              $scope.advertiseQuotationId = response.data.advertiseQuotation.id;
              $scope.ad.reviewData.adSolutionContent = response.data.adSolutionContent;
              //物料
              $scope.ad.reviewData.adSolutionContent.areaRequired = findName(response.data.positionVOList, response.data.adSolutionContent.positionId, 'areaRequired');
              $scope.ad.reviewData.adSolutionContent.size = findName(response.data.positionVOList, response.data.adSolutionContent.positionId, 'size');
              $scope.ad.reviewData.materialType = ~~findName(response.data.positionVOList, response.data.adSolutionContent.positionId, 'materialType');
              //计费方式
              $scope.ad.reviewData.advertiseQuotation.billingModelName = findName($scope.atTypes, response.data.advertiseQuotation.billingModelId, 'i18nName');

            });
          } else if ( response.code === 204 ) {
            var getAdIndex = function ( adId ) {
              for (var i = 0; i < $scope.ads.length; i++) {
                if ( $scope.ads[i].reviewData && $scope.ads[i].reviewData.adSolutionContent.id === ~~adId ) {
                  return i;
                } else {
                  continue;
                }
              }
              return -1;
            };
            var resolveErrorArgsForAdContent = function (args) {
              for (var i = 0; i < args.length; i++) {
                if ( args[i] ) {
                  args[i] = $filter('translate')('AD_CONTENT_TITLE') + (getAdIndex(args[i]) + 1);
                } else {
                  args[i] = $filter('translate')('AD_CONTENT_CURRENT');
                }
              }
              return args;
            };
            var errorText = [];
            for (var i = 0; i < response.errorList.length; i++) {
              errorText.push(GCRMUtil.translate(response.errorList[i].key, resolveErrorArgsForAdContent(response.errorList[i].args)));
            }
            Modal.alert({contentList: errorText});
          }
        });

      } else {
        $scope.anchorTo( contentIndex );
        return;
      }
    };

    $scope.ad.advertiseMaterials = [];

    $scope.removeMaterial = function(index) {

      var deleMaterials = $scope.ad.advertiseMaterials;
      if (deleMaterials[index].id) {
        Ad.deleMaterials({
          id: deleMaterials[index].id
        },
        function(response) {
          //
        });
      }
      $scope.ad.advertiseMaterials.splice(index, 1);
    };

    $scope.$watch('ad.materailUploaded',
    function(newVal) {
      if (newVal) {
        $scope.ad.advertiseMaterials = $scope.ad.advertiseMaterials || [];
        $scope.ad.advertiseMaterials.push(newVal);
      }
    });

    //取消编辑
    $scope.adContentCancelEditing = function() {
      Modal.confirm({
        content: $filter('translate')('AD_CONTENT_CANCEL_EDIT')
      },
      function() {
        $scope.ad.editing = false; //useless
        $scope.ad.review = true; //back to review
        $scope.$parent.$parent.$parent.stateEditing = false; //global editing
      });
    };

    //修改
    $scope.adContentModify = function() {
      if (!$scope.ad.reviewData.adSolutionContent.id) {
        return false;
      }
      $scope.ad.editing = true; //set state editing
      $scope.$parent.$parent.$parent.stateEditing = true;
      Ad.getSingle({
        id: $scope.ad.reviewData.adSolutionContent.id
      }, function(response) {
        $scope.ad.review = false;
        $scope.ad.CustomersName = {
          value: response.data.adSolutionContent.advertiser,
          data: response.data.adSolutionContent.advertiserId
        };
        $scope.ad.companyDescribe = response.data.adSolutionContent.description;

        //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
        if ( $scope.$parent.$parent.$parent.adSolutionTypeUpdate && response.data.adSolutionContent.contentType === 'update' ) {
          $scope.ad.calendarFutureOnly = true;
        }
        //oldContentId 如果有，保存的时候需要带过去
        $scope.ad.oldContentId = response.data.adSolutionContent.oldContentId;

        //基本信息 广告id
        $scope.programId = response.data.adSolutionContent.adSolutionId;
        $scope.adSolutionContentId = response.data.adSolutionContent.id;
        $scope.advertiseQuotationId = response.data.advertiseQuotation.id;
        //平台
        $scope.ad.products = response.data.adPlatformList;
        $scope.ad.adProductId = response.data.adSolutionContent.productId;

        //地点
        $scope.ad.siteByProducts = response.data.siteList;
        $scope.ad.siteByProductId = response.data.adSolutionContent.siteId;

        //频道
        $scope.ad.channelBySites = response.data.channelVOList;
        $scope.ad.channelBySiteSelection = response.data.adSolutionContent.channelId;

        //区域
        $scope.ad.areaByChannels = response.data.areaVOList;
        var channelSelection = response.data.adSolutionContent.areaId;
        $scope.ad.areaByChannelSelection = findName(response.data.areaVOList, channelSelection);

        //位置
        $scope.ad.positionByAreas = response.data.positionVOList;
        var positionByAreaSelection = response.data.adSolutionContent.positionId;
        $scope.ad.positionByAreaSelection = findName(response.data.positionVOList, positionByAreaSelection);

        //投放时间
        $scope.ad.timeBuckets = postPeriod(response.data.adSolutionContent.periodDescription);
        $scope.ad.adDaysEles = insertPeriod(response.data.adSolutionContent.insertPeriodDescription) || [];

        postAdTime($scope.ad.positionByAreaSelection, $scope.ad.timeBuckets, $scope.ad.adDaysEles);

        $scope.ad.adTimeCheckallModel = true;

        if ($scope.ad.adDaysEles) {
          $scope.insertDays = $scope.ad.adDaysEles.length;
        } else {
          $scope.ad.adDaysEles = [];
        }

        $scope.ad.totalDays = response.data.adSolutionContent.totalDays;

        //-报价情况
        //--刊例价
        $scope.ad.quotes.publishPrice = response.data.advertiseQuotation.publishPrice;

        var priceType = response.data.advertiseQuotation.priceType;
        if (priceType == 'unit') {
          $scope.ad.priceType = '0';
        } else {
          $scope.ad.priceType = '1';
        }
        $scope.ad.selection = response.data.advertiseQuotation.billingModelId; //billingmodel for priceType == 0  广告单价
        $scope.ad.quotes.customers = response.data.advertiseQuotation.customerQuote;
        $scope.ad.quotes.discount = response.data.advertiseQuotation.discount;
        $scope.ad.quotes.adCpBudget = response.data.advertiseQuotation.budget;
        $scope.ad.quotes.cpcCotalPrice = response.data.advertiseQuotation.totalPrice;
        $scope.ad.sharedRevenue = true;
        $scope.ad.quotes.cpsCustomersType = response.data.advertiseQuotation.industryType;
        $scope.ad.quotes.quoteTatioMt = response.data.advertiseQuotation.ratioCustomer;
        $scope.ad.quotes.quoteTatioMine = response.data.advertiseQuotation.ratioMine;
        $scope.ad.quotes.quoteTatioThird = response.data.advertiseQuotation.ratioThird;
        $scope.ad.quotes.reachEstimate = response.data.advertiseQuotation.reachEstimate;
        $scope.ad.conditionDesc = response.data.advertiseQuotation.ratioConditionDesc;
        $scope.ad.quotesMine = response.data.advertiseQuotation.productRatioMine;
        $scope.ad.quotes.dayTotal = response.data.advertiseQuotation.dailyAmount;
        $scope.ad.quotes.cpmCountTotal = response.data.advertiseQuotation.totalAmount;
        //below for 分成
        $scope.ad.quotes.ratioMine = response.data.advertiseQuotation.productRatioMine ? response.data.advertiseQuotation.productRatioMine: '';
        $scope.ad.quotes.ratioCustomer = response.data.advertiseQuotation.productRatioCustomer ? response.data.advertiseQuotation.productRatioCustomer: '';
        $scope.ad.quotes.ratioThird = response.data.advertiseQuotation.productRatioThird ? response.data.advertiseQuotation.productRatioThird: '';
        //物料信息
        var materialsState = +$scope.ad.positionByAreaSelection.materialType;
        if (materialsState === 0) {
          //图片
          $scope.ad.advertiseMaterials = response.data.advertiseMaterials;
        } else if (materialsState === 1) {
          //文字
          $scope.ad.materialTitle = response.data.adSolutionContent.materialTitle;
        } else if (materialsState === 2) {
          //文字+图片
          $scope.ad.materialTitle = response.data.adSolutionContent.materialTitle;
          $scope.ad.advertiseMaterials = response.data.advertiseMaterials;
        }
        $scope.ad.materialUrl = response.data.adSolutionContent.materialUrl;
        $scope.ad.materailCodeUploaded = response.data.advertiseCodeMaterial;
        //行业类型赋值
        $timeout( function () {
          $scope.ad.industrySelected = response.data.advertiseQuotation.industryType;
          $scope.selectIndusty($scope.ad);
          // $log.info($scope.ad.industrySelected);
          // $log.info(response.data.advertiseQuotation.industryType);
        }, 1000);
        //resolved periods
        function insertPeriod(times) {
          var i, timePeriod = [];
          if (times !== '') {
            timesArray = times.split(';');
          } else {
            return;
          }
          var length = timesArray.length;
          for (i = 0; i < length; i++) {
            timePeriod.push({
              date: timesArray[i],
              checked: true
            });
          }
          return timePeriod;
        }
        function postPeriod(times) {
          var i, timePeriod = [],
          timeBuckets = [];
          if (times !== '') {
            timesArray = times.split(';');
          } else {
            return;
          }
          var length = timesArray.length;
          for (i = 0; i < length; i++) {
            timePeriod = timesArray[i].split(',');
            if (timePeriod.length > 1) {
              timeBuckets.push({
                from: timePeriod[0],
                to: timePeriod[1]
              });
            } else if ( timesArray[i] ){
              timeBuckets.push({
                from: timesArray[i],
                to: timesArray[i]
              });
            }
          }
          return timeBuckets;
        }
      });

    };

    $scope.isSaveBtnDisabled = false;
    $scope.btnDisabled = function(){
        $scope.isSaveBtnDisabled = true;

    };
    $scope.btnEnabled = function(){
        $scope.isSaveBtnDisabled = false;
    };
    //查看审批记录
    $scope.btnApprovalRecord = function (ad) {
      SolutionApprovalRecord.forContent(ad.reviewData.adSolutionContent.id, {windowClass: 'approval-record-modal'});
    };
    //选择了行业类型
    $scope.selectIndusty = function ( ad ) {
      if ( !$scope.quotesData || !$scope.quotesData.length ) {
        $scope.ad.quotesMine = undefined;
      }
      for (var i = 0; i < $scope.quotesData.length; i++) {
        if ( $scope.quotesData[i].industryId === undefined ) {
          $scope.ad.quotesMine = $scope.quotesData[i].ratioMine;
          break;
        }
        if ( $scope.quotesData[i].industryId === ~~ad.industrySelected ) {
          $scope.ad.quotesMine = $scope.quotesData[i].ratioMine;
          break;
        } else {
          $scope.ad.quotesMine = undefined;
        }
      }
    };
    //上传了代码
    $scope.codeUploaded = function ( ad ) {
      $timeout( function () {
        if ( ad.materailCodeUploaded ) {
          ad.materailCodeUploaded;
        }
      });
    };
    //删除上传的代码
    $scope.removeCodeUploaded = function ( ad ) {
      ad.materailCodeUploaded = null;
    };
  }]);
});