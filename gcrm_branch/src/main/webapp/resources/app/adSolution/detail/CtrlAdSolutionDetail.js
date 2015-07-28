define([
  'app',
  '_common/ytCommon',
  '_filters/AdSolutionTypeFilter',
  '_filters/AdContentCancelReasonFilter',
  'anuglar-ui-select2',
  '_services/Select2Suggest',
  '_directives/ytCalendar',
  'record/SolutionApprovalRecord',
  '_directives/ytJqueryFileUpload',
  '_directives/ytAjaxupload',
  '_directives/periodLabel',
  './CtrlModalPO',
  '_filters/PortionFilter',
  '_filters/IndustryTypeFilter'
], function (app) {
  app.registerController('CtrlAdSolutionDetail', [
    '$scope', 'PageSet', 'AdProgram', '$stateParams', '$state', 'Select2Suggest', 'Ad', '$q', 'Position', '$timeout', 'Modal', 'GCRMUtil', '$filter', 'SolutionApprovalRecord', '$modal', '$window', 'AdSolutionDetailConstant', 'PublishPrice', '$log', 'Industry', 'Utils', 'STATIC_DIR',
  function ($scope, PageSet, AdProgram, $stateParams, $state, Select2Suggest, Ad, $q, Position, $timeout, Modal, GCRMUtil, $filter, SolutionApprovalRecord, $modal, $window, AdSolutionDetailConstant, PublishPrice, $log, Industry, Utils, STATIC_DIR) {
    // 非法进入，跳转至404
    if ( !$stateParams.id ) {
      $state.go('error.four');
      return false;
    }
    //美化页面
    PageSet.set({
      siteName: 'adSolutionDetail',
      activeIndex: 1
    });

    AdProgram.getDetail({id: $stateParams.id}).success(function (response) {
      if (response.code === 200) {
        $scope.v = response.data;
        if ( $scope.v.adSolution.approvalStatus ) {
          //如果是驳回或者待提交状态，跳回到之前的页面
          // 方案状态为待提交时，判断：
          // 1）所有内容状态为{待提交，审核驳回}：查看详情跳转到ad页；
          // 2）其他跳转到adSolutionDetail页；
          // 方案状态为审核驳回时，判断：
          // 1）所有内容状态都为审核驳回，跳转到ad页；
          // 2）其他跳转到adSolutionDetail页；
          var status = response.data.adSolution.approvalStatus,
              flag,
              contents,
              i;
          if ( status === 'saving' ) {
            flag = true;
            contents = response.data.approvalContentViews;
            for (i = 0; i < contents.length; i++) {
              if ( contents[i].adSolutionContent.approvalStatus !== 'saving' &&
                  contents[i].adSolutionContent.approvalStatus !== 'refused' ) {
                // $state.go('ad.facade', {programId: $stateParams.id } );
                flag = false;
              }
            }
            // 如果存在作废的广告内容，则不需要跳转ad
            if ( flag && ( !response.data.cancelRecord || !response.data.cancelRecord.length ) ) {
              $state.go('ad.facade', {programId: $stateParams.id } );
            }
          } else if ( status === 'refused' ) {
            flag = true;
            contents = response.data.approvalContentViews;
            for (i = 0; i < contents.length; i++) {
              if ( contents[i].adSolutionContent.approvalStatus !== 'refused' ) {
                flag = false;
              }
            }
            // 如果存在作废的广告内容，则不需要跳转
            if ( flag && !( response.data.cancelRecord && response.data.cancelRecord.length ) ) {
              $state.go('ad.facade', {programId: $stateParams.id } );
            }
          }
          if ( status === 'saving' && !$scope.v.approvalContentViews.length &&
              !$scope.v.cancelRecord.length) {
            $state.go('ad.facade', {programId: $stateParams.id } );
          }
          if ( $scope.v.adSolution.approvalStatus === 'approving' ||
              ( $scope.v.adSolution.approvalStatus === 'confirmed' &&
                $scope.v.adSolution.contractType &&
                $scope.v.adSolution.contractStatus !== 'Revoked' &&
                !$scope.v.contract ) ) {
            //设置alert信息
            if ( $scope.v.adSolution.taskInfo ) {
              $scope.alerts = [{
                type: 'info',
                msg: $scope.v.adSolution.taskInfo
              }];
              //never in here
              // if ( $scope.v.adSolution.approvalStatus === 'refused' ) {
              //   $scope.alerts[0].type = 'danger';
              // }
            }
          } else {
            // ;
          }
        }
        //且当签约主体的客户类型是广告代理（线下）时，显示“协议下单笔合同”
        if ( $scope.v.customerI18nView.customer.customerType === 'offline' ) {
          $scope.contractTypes = [{
            name: AdSolutionDetailConstant.singleContract,
            type: 'single'
          }, {
            name: AdSolutionDetailConstant.protocol,
            type: 'protocol'
          }];
        } else {
          $scope.contractTypes = [{
            name: AdSolutionDetailConstant.singleContract,
            type: 'single'
          }, {
            name: AdSolutionDetailConstant.frame,
            type: 'frame'
          }];
        }
        //判断是否是直客，是的话，将状态带至广告主
        if ( response.data.customerI18nView.customer.customerType === 'direct' ) {
          $scope.stateCustomerStraight = {
            data: response.data.customerI18nView.customer.customerNumber,
            value: response.data.customerI18nView.customer.companyName
          };
        }
        //设置materialType
        // for (var i = 0; i < $scope.v.approvalContentViews.length; i++) {
        //   var item = { review: true };
        //   item.reviewData = adResponseData[i];
        //   item.reviewData.materialType = ~~findName(adResponseData[i].positionVOList, adResponseData[i].adSolutionContent.positionId, 'materialType');
        //   arr.push(item);
        // }
      }
    });
    //设置广告主的suggest
    $scope.optionCustomer = Select2Suggest.getCustomerOptionForAdOwner();
    //关闭提示栏
    $scope.closeAlert = function(index) {
      if ( $scope.alerts[index].type === 'info') {
        $scope.alerts.splice(index, 1);
      }
    };
    /**
     * [util description]
     * @type {Object}
     * usage:
     *   --findArea 在区域列表中找到当前
     *   --findPostion 在位置列表中找到当前
     *   --getDatePeriods 将投放时段的字符串转成array
     *   --getInsertDates 将插单时段的字符串转成array
     *   --getSiteList 获取站点列表
     *   --getChannelList 获取频道列表
     *   --getAreaList 获取区域列表
     *   --getPositionList 获取位置列表
     *   --resolvePeriods 查询插单时段时处理时段输入
     *   --queryInsertDates 查询插单时间
     *   --clearPrice 清除编辑时的价格相关的信息
     *   --getAdIndex 根据内容的id获取内容index
     *   --resolveErrorArgsForAdContent 提交广告方案的错误提示处理
     *   --updateApprovalStatus 内容确认后，内容状态变化，更新方案的状态
     */
    var util = {
      findArea: function (id, array) {
        return this.find(id, array);
      },
      findPostion: function (id, array) {
        return this.find(id, array);
      },
      find: function (id, array) {
        var result;
        angular.forEach(array, function (value, key) {
          if ( !result && value['id'] === id ) {
            result = value;
          }
        });
        return result;
      },
      getDatePeriods: function (periodDesc) {
        if ( !periodDesc ) {
          return null;
        }
        var datePeriod = [],
            resultDatePeriods = [],
            periodSplit = periodDesc.split(';');
        for (var i = 0; i < periodSplit.length; i++) {
          datePeriod = periodSplit[i].split(',');
          if (datePeriod.length > 1) {
            resultDatePeriods.push({
              from: datePeriod[0],
              to: datePeriod[1]
            });
          } else if ( periodSplit[i] ){
            //如果为单一一天，开始和结束时间都是当天
            resultDatePeriods.push({
              from: periodSplit[i],
              to: periodSplit[i]
            });
          }
        }
        return resultDatePeriods;
      },
      getInsertDates: function ( insertPeriodDescription ) {
        if ( !insertPeriodDescription ) {
          return [];
        }
        var insertDates = [],
            insertSplit = insertPeriodDescription.split(';');
        for (var i = 0; i < insertSplit.length; i++) {
          insertDates.push({
            date: insertSplit[i],
            checked: true
          });
        }
        return insertDates;
      },
      getSiteList: function (productId) {
        Position.getSiteList({productId: productId}).success(function (response) {
          if (response.code === 200) {
            $scope.e.siteList = response.data;
            $scope.e.adSolutionContent.siteId = null;
            $scope.e.adSolutionContent.channelId = null;
            $scope.e.area = null;
            $scope.e.position = null;
          }
        });
      },
      getChannelList: function (productId,siteId) {
        Position.getChannelList({productId: productId, siteId: siteId}).success(function (response) {
          if (response.code === 200) {
            $scope.e.channelVOList = response.data;
            $scope.e.adSolutionContent.channelId = null;
            $scope.e.area = null;
            $scope.e.position = null;
          }
        });
      },
      getAreaList: function (channelId) {
        Position.getAreaList({parentId: channelId}).success(function (response) {
          if (response.code === 200) {
            $scope.e.areaVOList = response.data;
          }
        });
      },
      getPositionList: function (areaId) {
        Position.getPositionList({parentId: areaId}).success(function (response) {
          if (response.code === 200) {
            $scope.e.positionVOList = response.data;
          }
        });
      },
      resolvePeriods: function (periods) {
        for (var i = periods.length - 1; i >= 0; i--) {
          if ( !periods[i].from || !periods[i].to ) {
            periods.splice(i, 1);
            continue;
          }
          if (angular.isDate(periods[i].from)) {
            periods[i].from = periods[i].from.getTime();
          } else if ( !/\d/.test(periods[i].from) ) {
            periods[i].from = (new Date(periods[i].from.replace(/-/g, '/'))).getTime();
          }
          if (angular.isDate(periods[i].to)) {
            periods[i].to = periods[i].to.getTime();
          } else if ( !/\d/.test(periods[i].to) ) {
            periods[i].to = (new Date(periods[i].to.replace(/-/g, '/'))).getTime();
          }
          if ( periods[i].to < periods[i].from ) {
            var temp = periods[i].to;
            periods[i].to = periods[i].from;
            periods[i].from = temp;
          }
        }
        return periods;
      },
      queryInsertDates: function ( insertDate ) {
        //查询插单时间
        if ( !$scope.e.position.id || !$scope.e.datePeriods) {
          return false;
        }
        var postPeriods = this.resolvePeriods($scope.e.datePeriods);
        var postData = {
          positionId: $scope.e.position.id,
          periods: postPeriods,
          insertDate: insertDate
        };
        if ( $scope.e.adSolutionContent.approvalStatus === 'confirmed' ) {
          angular.extend( postData, { oldContentId: $scope.e.adSolutionContent.id } );
        } else if ( $scope.e.adSolutionContent.contentType === 'update' ) {
          angular.extend( postData, { oldContentId: $scope.e.adSolutionContent.oldContentId } );
        }
        Ad.queryInsertDates(postData).success(function(response) {
          if (response.code === 200) {
            $scope.e.datePeriods = response.data.datePeriod;
            //如果所查period是空，置一个空的period
            if ( !$scope.e.datePeriods || !$scope.e.datePeriods.length ) {
              $scope.e.datePeriods = [{}];
            }
            $scope.e.insertDates = response.data.insertDate;
            $scope.e.insertDatesTotal = response.data.totalDays;

            $scope.e.selectedInsertDays = $scope.e.insertDates && $scope.e.insertDates.length;
            for (var i = 0; i < $scope.e.insertDates.length; i++) {
              if (!$scope.e.insertDates[i].checked) {
                $scope.e.selectedInsertDays = $scope.e.selectedInsertDays - 1;
              }
            }
            $scope.updateCPMTotalAmount();//更新cpm投放总量
          }
        });
      },
      clearPrice: function () {
        $scope.e.advertiseQuotation.ratioMine = 0;
        $scope.e.advertiseQuotation.ratioCustomer = 0;
        $scope.e.advertiseQuotation.ratioThird = 0;
        $scope.e.advertiseQuotation.reachEstimate = false;
        $scope.e.advertiseQuotation.ratioConditionDesc = '';
        $scope.e.advertiseQuotation.publishPrice = null;
        $scope.e.advertiseQuotation.customerQuote = null;
        $scope.e.advertiseQuotation.discount = null;
        $scope.e.advertiseQuotation.budget = null;
        $scope.e.advertiseQuotation.totalPrice = null;
        $scope.e.advertiseQuotation.dailyAmount = null;
        $scope.e.advertiseQuotation.totalAmount = null;
        $scope.e.cpsProductRatioMine = null;
        this.toggleValidator(false);
      },
      resetPrice: function () {
        this.clearPrice();
        $scope.e.priceDisabled = true;
        $scope.e.advertiseQuotation.priceType = null;
        //清空位置信息
        $scope.e.position = null;
      },
      getAdIndex: function ( adId ) {
        for (var i = 0; i < $scope.v.approvalContentViews.length; i++) {
          if ( $scope.v.approvalContentViews[i] && $scope.v.approvalContentViews[i].adSolutionContent.id === ~~adId ) {
            return i;
          } else {
            continue;
          }
        }
        return -1;
      },
      resolveErrorArgsForAdContent: function (args) {
        for (var i = 0; i < args.length; i++) {
          if ( args[i] ) {
            args[i] = $filter('translate')('AD_CONTENT_TITLE') + (this.getAdIndex(args[i]) + 1);
          } else {
            args[i] = $filter('translate')('AD_CONTENT_CURRENT');
          }
        }
        return args;
      },
      updateApprovalStatus: function () {
        var flag = true;
        for (var i = 0; i < $scope.v.approvalContentViews.length && flag; i++) {
          if ( $scope.v.approvalContentViews[i].adSolutionContent.approvalStatus !== 'confirmed' ) {
            flag = false;
          }
        }
        if ( flag ) {
          $scope.v.adSolution.approvalStatus = 'confirmed';
        }
      },
      queryQuotionPattern: function () {
        var queryParams = {
          advertisingPlatformId: $scope.e.adSolutionContent.productId,
          siteId: $scope.e.adSolutionContent.siteId,
          billingModelId: $scope.e.advertiseQuotation.billingModelId
        };
        if ( !queryParams.billingModelId ) {
          delete queryParams.billingModelId;
        }
        PublishPrice.post(queryParams).success(function (response) {
          if ( response.code === 200 && response.data.result ) {
            if ( response.data.result[0] ) {
              if ( ~~$scope.e.advertiseQuotation.billingModelId === 2 ) {
                $scope.e.productQuotes = response.data.result;
                if ( $scope.e.industrySelected || $scope.e.industrySelected === 0 ) {
                  $scope.selectIndusty();
                }
              } else {
                $scope.e.advertiseQuotation.publishPrice = response.data.result[0].publishPrice;
                $scope.e.advertiseQuotation.productRatioMine = response.data.result[0].ratioMine;
                $scope.e.advertiseQuotation.productRatioCustomer = response.data.result[0].ratioCustomer || 0;
                $scope.e.advertiseQuotation.productRatioThird = response.data.result[0].ratioThird || 0;
              }
            } else {
              $scope.e.advertiseQuotation.discount = 1;//未备案刊例价时折扣自动填写1，用户可修改。
            }
          }
        });
      },
      toggleValidator: function ( ifValidate ) {
        $scope.e.contentSave = ifValidate;
        $scope.e.hiddenElValidator = ifValidate;
        $scope.e.positionChangeValidator = ifValidate;
        $scope.e.quotesValidator = ifValidate;
        $scope.e.materialValidator = ifValidate;
        $scope.e.codeContentValidator = ifValidate;
      }
    };
    //cpm 总投放量联动
    $scope.updateCPMTotalAmount =  function () {
      updateTotal( 4 );
    };
    function updateTotal ( billType ) {
      try {
        billType = billType || $scope.e.advertiseQuotation.billingModelId;
        if ( billType === 4 ) {
          //CPM
          $scope.e.advertiseQuotation.totalAmount = (($scope.e.insertDatesTotal - ($scope.e.insertDates.length - $scope.e.selectedInsertDays)) || 0) * ($scope.e.advertiseQuotation.dailyAmount || 0);
        } else if ( billType === 5 ) {
          //CPT
          $scope.e.advertiseQuotation.totalPrice = (($scope.e.advertiseQuotation.customerQuote || 0) * (($scope.e.insertDatesTotal - ($scope.e.insertDates.length - $scope.e.selectedInsertDays)) || 0)).toFixed(2);
        } else if ( billType === 1) {
          //CPC
          $scope.e.advertiseQuotation.totalPrice = (( $scope.e.advertiseQuotation.customerQuote || 0 ) * (($scope.e.insertDatesTotal - ($scope.e.insertDates.length - $scope.e.selectedInsertDays)) || 0) * $scope.e.position.click).toFixed(2);
        }
      } catch ( e ) {

      }
    }
    //折扣变化，客户报价跟着变化
    $scope.updateCustomerQuote = function () {
      //如果存在刊例价，则客户报价=折扣×刊例价
      if ( $scope.e.advertiseQuotation.publishPrice ) {
        $scope.e.advertiseQuotation.customerQuote = ( $scope.e.advertiseQuotation.discount * $scope.e.advertiseQuotation.publishPrice ).toFixed(2);
      }
      updateTotal();
    };
    //客户报价变化，折扣跟着变化
    $scope.updateDiscount = function () {
      //如果存在刊例价，则客户报价=折扣×刊例价
      if ( $scope.e.advertiseQuotation.publishPrice ) {
        $scope.e.advertiseQuotation.discount = ( $scope.e.advertiseQuotation.customerQuote / $scope.e.advertiseQuotation.publishPrice ).toFixed(2);
      }
      updateTotal();
    };
    //cps切换商业类型，显示对应的备案比例
    $scope.selectIndusty = function () {
      if ( !$scope.e.productQuotes || !$scope.e.productQuotes.length ) {
        $scope.e.cpsProductRatioMine = undefined;
        return false;
      }
      for (var i = 0; i < $scope.e.productQuotes.length; i++) {
         if ( $scope.e.productQuotes[i].industryId === undefined ) {
          $scope.e.cpsProductRatioMine = $scope.e.productQuotes[i].ratioMine;
          break;
        }
        if ( $scope.e.productQuotes[i].industryId === ~~$scope.e.industrySelected ) {
          $scope.e.cpsProductRatioMine = $scope.e.productQuotes[i].ratioMine;
          break;
        } else {
          $scope.e.cpsProductRatioMine = undefined;
        }
      }
    };
    //点击了修改广告内容的按钮，修改广告内容
    $scope.btnEdit = function (ad, isChange) {
      $scope.currentContent = ad;
      $scope.isEditing = true;
      ad.editing = true;
      $q.all( [ Ad.getBillingModel(), Ad.getContentInfo({
        id: ad.adSolutionContent.id
      }), Industry.getIndustryTypes() ] ).then(function (response) {
        if ( !response[0] || !response[1] ||
            !response[0].data || !response[1] ) {
          return false;
        }
        var billingModelResponse = response[0].data,
            adContentResponse = response[1].data,
            industryTypesResponse = response[2].data;
        if ( billingModelResponse.code === 200 && adContentResponse.code === 200 ) {
          $scope.e = adContentResponse.data;
          $scope.e.industryTypes = industryTypesResponse.data;
          //行业类型赋值
          $timeout(function () {
            $scope.e.industrySelected = adContentResponse.data.advertiseQuotation.industryType;
            $scope.selectIndusty();
          }, 200);
          //show alert,仅在内容状态为待确认的情况下才会提示‘若仅修改物料，请进入<a ui-sref="home">物料管理</a>页面操作’
          if ( $scope.e.adSolutionContent.approvalStatus === 'unconfirmed' ||
              $scope.e.adSolutionContent.approvalStatus === 'confirmed' ) {
            $scope.e.showAlert = true;
          }
          //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
          // if ( $scope.v.adSolution.type === 'update' || $scope.e.adSolutionContent.contentType === 'update' ) {
          //   $scope.e.calendarFutureOnly = true;
          // }
          if ( isChange || $scope.e.adSolutionContent.contentType === 'update' ) {
            $scope.e.calendarFutureOnly = true;
          }
          //special deal
          $scope.e.advertiser = {
            value: $scope.e.adSolutionContent.advertiser,
            data: $scope.e.adSolutionContent.advertiserId
          };
          if ( $scope.stateCustomerStraight ) {
            $scope.e.advertiser = $scope.stateCustomerStraight;
          }
          $scope.e.area = util.findArea($scope.e.adSolutionContent.areaId, $scope.e.areaVOList);
          $scope.e.position = util.findPostion($scope.e.adSolutionContent.positionId, $scope.e.positionVOList);
          $scope.e.priceTypes = [{
            label: 'AD_CONTENT_UNIT_PRICE',
            value: 'unit'
          },{
            label: 'AD_CONTENT_FALL_INTO',
            value: 'ratio'
          }];
          $scope.e.isEstimated = [{
            label: 'YES',
            value: true
          },{
            label: 'NO',
            value: false
          }];
          $scope.e.billingModels = billingModelResponse.data.result;
          $scope.e.datePeriods = util.getDatePeriods($scope.e.adSolutionContent.periodDescription);
          $scope.e.insertDatesTotal = $scope.e.adSolutionContent.totalDays;
          $scope.e.insertDates = util.getInsertDates($scope.e.adSolutionContent.insertPeriodDescription);
          $scope.e.selectedInsertDates = $scope.e.insertDates;
          $scope.e.selectedInsertDays = $scope.e.insertDates.length;
          $scope.e.insertDatesCheckAll = true;
          util.queryQuotionPattern();
        }
      });
    };
    $scope.changeProduct = function (productId) {
      util.resetPrice();
      productId && util.getSiteList(productId);
    };
    $scope.changeSite = function (productId, siteId) {
      util.resetPrice();
      productId && siteId && util.getChannelList(productId, siteId);
    };
    $scope.changeChannel = function (channelId) {
      util.resetPrice();
      channelId && util.getAreaList(channelId);
    };
    $scope.changeArea = function ( areaId ) {
      util.resetPrice();
      areaId && util.getPositionList( areaId );
    };
    $scope.changePosition = function () {
      if ( $scope.e.position ) {
        $scope.e.priceDisabled = false;
        // $log.info( $scope.e.position );
      } else {
        $scope.e.advertiseQuotation.priceType = null;
        util.clearPrice();
        $scope.e.priceDisabled = true;
      }
      //激活价格
      $scope.e.advertiseQuotation.billingModelId = -1;
      // $log.info( '---' + $scope.e.position );
      //
      //激活投放时段
      $scope.e.datePeriods = [{}];
      $scope.e.insertDates = null;
      $scope.e.insertDatesTotal = 0;
      $scope.e.selectedInsertDays = 0;
      //重置资料区的东东
      $scope.e.adSolutionContent.materialEmbedCode = 0;
      $scope.e.adSolutionContent.materialTitle = null;
      $scope.e.adSolutionContent.materialUrl = null;
      $scope.e.advertiseMaterials = null;
    };
    $scope.changePriceType = function (value) {
      $scope.e.advertiseQuotation.billingModelId = 0;
      $scope.e.industrySelected = undefined;//清空行业类型在计费方式切换的时候
      util.clearPrice();
      util.queryQuotionPattern();
    };
    $scope.changeBillingModel = function () {
      $scope.e.industrySelected = undefined;//清空行业类型在计费方式切换的时候
      util.clearPrice();
      util.queryQuotionPattern();
    };
    //时段中开始、结束时间的变更
    $scope.startTimeChange = function ( period ) {
      if ( period.to && period.from ) {
        util.queryInsertDates($scope.e.insertDates);
      }
    };
    $scope.endTimeChange = function ( period ) {
      if ( period.to && period.from ) {
        util.queryInsertDates($scope.e.insertDates);
      }
    };
    //添加删除投放时段
    $scope.removePeriod = function (index) {
      //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
      if ( $scope.e.calendarFutureOnly ) {
        if ( typeof $scope.e.datePeriods[index].from === 'number' ) {
          if ( $scope.e.datePeriods[index].from < Utils.getTodayZero().getTime() ) {
            return false;
          }
        } else if ( typeof $scope.e.datePeriods[index].from === 'string' ) {
          if ( new Date( $scope.e.datePeriods[index].from.replace(/-/g, '/')).getTime() < Utils.getTodayZero().getTime() ) {
            return false;
          }
        }
      }
      $scope.e.datePeriods.splice(index, 1);
      util.queryInsertDates($scope.e.insertDates);
    };
    $scope.addPeriod = function () {
      $scope.e.datePeriods.push({});
    };
    //toggle全选插单时间选择
    $scope.toggleCheckAll = function () {
      if ( !$scope.e.insertDates ) {
        return;
      }
      var i;
      if ( !$scope.e.insertDatesCheckAll ) {
        for (i = 0; i < $scope.e.insertDates.length; i++) {
          $scope.e.insertDates[i].checked = false;
        }
        $scope.e.selectedInsertDays = 0;
      } else {
        for (i = 0; i < $scope.e.insertDates.length; i++) {
          $scope.e.insertDates[i].checked = true;
        }
        $scope.e.selectedInsertDays = $scope.e.insertDates.length;
      }
      $scope.updateCPMTotalAmount();//更新cpm投放总量//更新cpm投放总量
    };
    $scope.toggleCheck = function(insertDate) {
      insertDate.checked = !insertDate.checked;
      var flag = !!$scope.e.insertDates.length;
      var insertDays = 0;
      angular.forEach($scope.e.insertDates, function(value, key) {
        if (!value.checked) {
          flag = false;
        } else {
          insertDays++;
        }
      });
      $scope.e.selectedInsertDays = insertDays;
      $scope.e.insertDatesCheckAll = flag;
      $scope.updateCPMTotalAmount();//更新cpm投放总量
    };

    //提交审核
    // @param isChange 是否点的变更
    $scope.adContentApproval = function (newAdForm, ad, contentIndex, isChange) {
      if ( newAdForm.$valid ) {
        $scope.e.contentSave = false;
        $scope.e.hiddenElValidator = false;
        $scope.e.positionChangeValidator = false;
        $scope.e.quotesValidator = false;
        var postData = $scope.e;
        postData.adSolutionContent.advertiser = $scope.e.advertiser.value;
        if ( $scope.e.advertiser.data ) {
          postData.adSolutionContent.advertiserId = $scope.e.advertiser.data;
        }
        postData.adSolutionContent.areaId = $scope.e.area.id;
        postData.adSolutionContent.positionId = $scope.e.position.id;
        postData.adSolutionContent.materialType = $scope.e.position.materialType;
        postData.periods = $scope.e.datePeriods;
        if ( typeof $scope.e.industrySelected !== 'undefined' ) {
          postData.advertiseQuotation.industryType = $scope.e.industrySelected;
        }
        // postData.insertDate = $scope.e.selectedInsertDates;
        // push all checked insert dates to postdata
        var postInsertDate = [];
        for (var i = 0; i < $scope.e.insertDates.length; i++) {
          if ( $scope.e.insertDates[i].checked ) {
            postInsertDate.push($scope.e.insertDates[i]);
          }
        }
        postData.insertDate = postInsertDate;
        //cps时，productRatioMine值为
        if ( ~~$scope.e.advertiseQuotation.billingModelId === 2 ) {
          postData.advertiseQuotation.productRatioMine = $scope.e.cpsProductRatioMine;
        }
        //是否点击变更
        if ( isChange ) {
          postData.approvalType = 'change';
        } else {
          postData.approvalType = 'modify';
        }
        Ad.contentApproval(postData, function (response) {
          if (response.code === 200) {
            $window.location.reload();
            // ad = response.data;
            // $timeout(function () {
            //   ad.editing = false;
            // }, 0);
            // $scope.isEditing = false;
          } else if ( response.code === 204 ) {
            var errorText = [];
            for (var i = 0; i < response.errorList.length; i++) {
              errorText.push(GCRMUtil.translate(response.errorList[i].key, util.resolveErrorArgsForAdContent(response.errorList[i].args)));
            }
            Modal.alert({contentList: errorText});
          }
        });
      } else {
        $scope.e.contentSave = true;
        $scope.e.hiddenElValidator = true;
        $scope.e.positionChangeValidator = true;
        $scope.e.quotesValidator = true;
        $scope.e.materialValidator = true;
        $scope.e.codeContentValidator = true;
        $scope.anchorTo( 'anchorContent' + contentIndex );
      }
    };
    $scope.adContentCancel = function (ad) {
      ad.editing = false;
      $scope.isEditing = false;
    };
    $scope.btnSubmitAdSolution = function () {
      AdProgram.submit({
        id: $stateParams.id
      }, function( response ) {
        if ( response.code === 200 ) {
          Modal.alert({
            content: $filter('translate')('AD_BASIC_ALERT_SUBMIT_SUCCESS')
          }, function() {
            //success and nav to ?
          });
        } else if ( response.code === 204 ) {
          var errorText = [];
          for (var i = 0; i < response.errorList.length; i++) {
            errorText.push(GCRMUtil.translate(response.errorList[i].key, util.resolveErrorArgsForAdContent(response.errorList[i].args)));
          }
          Modal.alert({contentList: errorText});
        }
      });
    };
    //查看审批记录
    $scope.btnApprovalRecord = function (ad) {
      SolutionApprovalRecord.forContent(ad.adSolutionContent.id, {windowClass: 'approval-record-modal'});
    };
    //终止合作，广告内容
    $scope.btnTerminate = function ( ad, index ) {
      Modal.confirm({content: AdSolutionDetailConstant.terminateWarn, psInfo: AdSolutionDetailConstant.terminatePS}, function () {
        Ad.contentTerminate({id: ad.adSolutionContent.id}).success(function ( response ) {
          if ( response.code === 200 ) {
            $window.location.reload();
            //终止后，加入作废内容列表
            // $scope.v.cancelRecord = $scope.v.cancelRecord || [];
            // $scope.v.cancelRecord.push( response.data.cancel );
            // $scope.v.approvalContentViews.splice( index, 1 );
          }
        });
      });
    };
    //确认广告内容,点击后确定国代的排期信息
    $scope.btnConfirmContent = function (ad) {
      var scheduleNumber = ad.schedule.number;
      if ( ad.overdue ) {
        Modal.confirm({content: AdSolutionDetailConstant.confirmWarn, psInfo: AdSolutionDetailConstant.confirmWarnPS}, confirmContentSchedule);
      } else {
        confirmContentSchedule();
      }
      function confirmContentSchedule () {
        Ad.confirmContentSchedule({id: ad.adSolutionContent.id, overdue: ad.overdue}).success(function (response) {
          if ( response.code === 200 ) {
            // 将返回的物料单编号、排期单编号回填到广告内容中，
            // 2) 将对应内容的投放时间修改为国代排期的时间，去掉对比的表格
            // var materialNumber = response.data;
            // ad.materialApply = ad.materialApply || {};
            // ad.materialApply.number = materialNumber;
            // ad.afterConfirmSchedule = true;
            // ad.adSolutionContent.approvalStatus = 'confirmed';
            // util.updateApprovalStatus();
            $window.location.reload();
          }
        });
      }
    };
    //重新排期
    $scope.btnReSchedule = function (ad) {
      Modal.confirm({content: AdSolutionDetailConstant.rescheduleWarn}, function () {
        Ad.reSchedule({id: ad.adSolutionContent.id}).success(function (response) {
          if ( response.code === 200 ) {
            // 成功后，提示并刷新当前页
            Modal.success({content: AdSolutionDetailConstant.rescheduleSuccess}, function () {
              $window.location.reload();
            });
          }
        });
      });
    };
    //资料的添加删除
    $scope.$watch('e.materialUploaded', function(newVal) {
      if (newVal) {
        $scope.e.advertiseMaterials = $scope.e.advertiseMaterials || [];
        $scope.e.advertiseMaterials.push(newVal);
      }
    });
    $scope.removeMaterial = function(index) {
      var deleMaterials = $scope.e.advertiseMaterials;
      if (deleMaterials[index].id) {
        Ad.deleMaterials({
          id: deleMaterials[index].id
        },
        function(response) {
          //
        });
      }
      $scope.e.advertiseMaterials.splice(index, 1);
    };
    //撤销广告方案
    $scope.btnCancelAd = function () {
      Modal.confirm({content: AdSolutionDetailConstant.warnAdCancel }, function () {
        Ad.cancelAd({id: $stateParams.id}).success(function (response) {
          // 撤销成功后跳转回 添加页
          if ( response.code === 200 ) {
            Modal.success({content: AdSolutionDetailConstant.successAdCancel }, function () {
              try {
                $state.go( 'ad.facade', { programId: $scope.v.adSolution.id} );
              } catch ( e ) {
                $state.go( 'ad.facade' );
              }
            });
          }
        });
      });
    };
    //撤销内容
    $scope.btnCancelContent = function (ad) {
      Modal.confirm({content: AdSolutionDetailConstant.warnContentCancel }, function () {
        Ad.cancelContent({id: ad.adSolutionContent.id}).success(function (response) {
          // 撤销成功后
          if ( response.code === 200 ) {
            Modal.success({content: AdSolutionDetailConstant.successAdCancel }, function () {
              $window.location.reload();
            });
          }
        });
      });
    };
    //创建PO
    //判断有无合同编号 --有-直接回写PO号--无-弹出框，填写合同
    $scope.btnCreatePO = function () {
      Ad.findContactPO({id: $stateParams.id}).success(function (response) {
        if (response.code === 200) {
          //根据有无合同编号做不同操作
          if ( response.data ) {
            //回写PO号到广告内容中，在哪显示
            Modal.success({content: AdSolutionDetailConstant.btnCreatePOSuccess}, function () {
              $window.location.reload();
            });
            // //返回  回写PO号到每个广告
            // for (var i = 0; i < $scope.v.approvalContentViews.length; i++) {
            //   $scope.v.approvalContentViews[i].adSolutionContent.poNum = response.data.poNum;
            // }
          } else {
            popPO( 'direct' );
          }
        }
      });
    };

    /**
     * [popPO description]
     * @param  {[type]} argument [description]
     *         type == 'direct' 直接点击PO
     *         type == 'frame'  点击框架下单笔合同
     *         type == 'protocol' 点击协议下单笔合同
     * @return {[type]}          [description]
     */
    function popPO ( type ) {
      var $modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/adSolution/detail/POModal.tpl.html',
        controller: 'CtrlModalPO',
        resolve: {
          opts: function () {
            return {
              type: type,
              customerNumber: $scope.v.customerI18nView.customer.customerNumber,
              adSolutionId: $stateParams.id
            };
          }
        }
      });
      $modalInstance.result.then(function (result) {
        if ( result && result.type) {
          if ( result.type === 'direct' ) {
            //创建PO成功，找到匹配的合同
            if ( result.data ) {
              //将PO的编号回写至每个广告内容中，并在每个广告内容显示变更按钮
              Modal.success( { content: AdSolutionDetailConstant.POSuccess }, function () {
                $window.location.reload();
              });
            } else {
              createSingleContract();//创建单笔合同
            }
          } else {
            //框架下单笔合同   协议下单笔合同
            if ( result.data ) {
              $window.location.reload();
            } else {
              createSingleContract();//创建单笔合同
            }
          }
        }
      });
    }
    //创建单笔合同
    function createSingleContract () {
      Ad.createSingleContract({id: $stateParams.id}).success(function (response) {
        if ( response.code === 200 ) {
          if ( response.data ) {
            //显示'已提交至商务人员'的ALERT
            Modal.success( { content: AdSolutionDetailConstant.btnContractTypeSuccess }, function () {
              $window.location.reload();
            } );
            // $scope.v.adSolution.contractType = 'single';
            // $scope.v.adSolution.contractStatus = 'display';
          }
        }
      });
    }
    //单笔合同  框架下单笔合同   协议下单笔合同
    $scope.btnContractType = function ( type, bussinessChange ) {
      if ( type === 'single' ) {
        createSingleContract();
      } else {
        if ( bussinessChange ) {
          //框架下单笔合同   协议下单笔合同
          Ad.createSingleContract({
            id: $stateParams.id
          }).success(function (response) {
            if ( response.code === 200 ) {
              //CMS返回成功后，显示已提交至商务人员提示
              Modal.success({content: AdSolutionDetailConstant.btnContractTypeSuccess}, function () {
                $window.location.reload();
              });
            }
          });
        } else {
          //框架下单笔合同   协议下单笔合同
          popPO( type );
        }
      }
    };
    //查看作废广告内容
    $scope.btnViewCanceled = function (content) {
      $window.open( '#/adContentDetail?id=' + content.adSolutionContentId );
    };
    //撤销单笔合同
    $scope.btnCancelSingle = function () {
      Ad.revokeContract({id: $stateParams.id}).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({content: AdSolutionDetailConstant.successSingleCancel}, function () {
            $window.location.reload();
          });
        }
      });
    };
    //变更方案
    $scope.btnAdSolutionChange = function () {
      Ad.changeAdSolution({id: $stateParams.id}).success(function (response) {
        if ( response.code === 200 ) {
          // Modal.success({content: AdSolutionDetailConstant.successAdChange}, function () {
            $state.go('ad.facade', { programId: response.data.adSolution.id });
          // });
        }
      });
    };
    //方案修改
    $scope.btnAdModify = function () {
      Ad.modifyAd( { id: $stateParams.id } ).success(function ( response ) {
        if ( response.code === 200 ) {
          $window.location.reload();
        }
      });
    };
    //变更PO
    $scope.btnChangePO = function ( content ) {
      Ad.changePO({
        adcontentId: content.adSolutionContent.id,
        contractNumber: $scope.v.contract.number
      }).success( function ( response) {
        if ( response.code === 200 ) {
          Modal.success({ content: AdSolutionDetailConstant.successChangePO }, function () {
            $window.location.reload();
          });
        }
      });
    };
  }]);
  app.registerService('AdSolutionDetailConstant', ['$filter', function ($filter) {
    var translateFilter = $filter('translate');
    return {
      terminateWarn: translateFilter('AD_SOLUTION_DETAIL_WARN_TERMINATE'),
      terminatePS: translateFilter('AD_SOLUTION_DETAIL_WARN_TERMINATE_PS'),
      confirmWarn: translateFilter('AD_SOLUTION_DETAIL_WARN_CONFIRM'),
      confirmWarnPS: translateFilter('AD_SOLUTION_DETAIL_WARN_CONFIRM_PS'),
      rescheduleWarn: translateFilter('AD_SOLUTION_DETAIL_WARN_RESCHEDULE'),
      frame: translateFilter('AD_SOLUTION_DETAIL_FRAME'),
      protocol: translateFilter('AD_SOLUTION_DETAIL_PROTOCOL'),
      btnContractTypeSuccess: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_SUBMITTED_TO_BUSSINESS'),
      btnCreatePOSuccess: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_CREATED_PO'),
      rescheduleSuccess: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_RESCHEDULE_SEND'),
      singleContract: translateFilter('SINGLE_CONTRACT'),
      warnAdCancel: translateFilter('AD_SOLUTION_DETAIL_WARN_CANCEL_AD'),
      warnContentCancel: translateFilter('AD_SOLUTION_DETAIL_WARN_CANCEL_CONTENT'),
      successAdCancel: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_CANCELED'),
      successSingleCancel: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_CANCELED_SINGLE'),
      successAdChange: translateFilter('AD_SOLUTION_DETAIL_SUCCESS_AD_CHANGE'),
      POSuccess: translateFilter('AD_SOLUTION_DETAIL_PO_SUCCESS'),
      successChangePO: translateFilter('AD_SOLUTION_DETAIL_CHANGE_PO_SUCCESS')
    };
  }]);
});