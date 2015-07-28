define([
  'app',
  '_filters/DatePeriodFilter'
], function (app) {
  app.registerService('AdContent', [
    '$q',
    'AdHttp',
    'Position',
    'AdContentUtil',
    '$timeout',
    'GCRMUtil',
    'ModalC',
    'AdConstant',
    'AdContentState',
    'AdContentPosition',
    'AdContentAdvertising',
    'AdContentPricing',
    'AdContentMaterial',
  function ($q, AdHttp, Position, AdContentUtil, $timeout, GCRMUtil, ModalC, AdConstant, AdContentState, AdContentPosition, AdContentAdvertising, AdContentPricing, AdContentMaterial) {
    return {
      init: function (scope) {
        // scope.contentState = {};
        scope.e = {
          advertiseQuotation: {
            ratioThird: 0,
            reachEstimate: 0
          },
          adSolutionContent: {}
        };

        scope.ad.innerScope = scope;
        //如果是直客，将客户带至广告内容作为广告主
        if (scope.basic.customerDirect) {
          scope.ad.advertiser = angular.copy(scope.basic.customerDirect);
        }
        scope.e.advertiser = scope.ad.advertiser;


        scope.e.adPlatformList = angular.copy(scope.adPlatformList);
        //平台只有一个时默认选中
        if (scope.e.adPlatformList.length === 1) {
          var productId = scope.e.adPlatformList[0].id;
          AdContentPosition.getSiteList(scope, productId);
        }

        scope.e.priceTypes = [{
          label: 'AD_CONTENT_UNIT_PRICE',
          value: 'unit'
        },{
          label: 'AD_CONTENT_FALL_INTO',
          value: 'ratio'
        }];

        scope.e.billingModels = angular.copy(scope.billingModels);
        scope.e.industryTypes = angular.copy(scope.industry.industryTypes);
        scope.e.isEstimated = [{
          label: 'YES',
          value: true
        },{
          label: 'NO',
          value: false
        }];

        scope.e.priceDisabled = true;
        AdContentMaterial.reset(scope.e);
        // scope.e.advertiseMaterials = [];

        this.updateContentState(scope);

        if (scope.contentState.autoSave) {
          this.initEdit(scope);
        }

      },
      initEdit: function (scope) {
        scope.ad.review = false;  //是否为预览状态
        scope.ad.isSaved = true;  //是否为保存过的广告内容
        angular.extend(scope.e, scope.ad);
        this.resolveEditData(scope);
        AdContentMaterial.updateMaterialFileTypes(scope.e);
        // scope.autoSaveStart();
      },
      edit: function (scope, isChange) {
        scope.state.isGlobleEditing = true;
        scope.ad.review = false;  //是否为预览状态
        scope.ad.isSaved = true;  //是否为保存过的广告内容
        var AdContent = this;
        AdHttp.getContentInfo({
          id: scope.ad.adSolutionContent.id
        }).success(function (response) {
          if (response.code === 200) {
            angular.extend(scope.e, response.data);
            AdContent.resolveEditData(scope, isChange);
            AdContentMaterial.updateMaterialFileTypes(scope.e);
          }
        });
      },
      resolveEditData: function (scope, isChange) {
        //应只有变现类的平台，而返回的平台list是所有的平台
        scope.e.adPlatformList = angular.copy(scope.adPlatformList);
        var e = scope.e;

        //show alert,仅在内容状态审核通过的情况下才会提示‘若仅修改物料，请进入<a ui-sref="home">物料管理</a>页面操作’
        var approvalStatus = e.adSolutionContent.approvalStatus;
        // if ( approvalStatus === 'unconfirmed' || approvalStatus === 'confirmed' ) {
        if ( approvalStatus === 'approved' || approvalStatus === 'effective' ) {
          e.showAlert = true;
        }

        // 判断逻辑与业务不符暂时去掉 2014-10-17 PM确认
        //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
        // if ( isChange || e.adSolutionContent.contentType === 'update' ) {
        //   e.calendarFutureOnly = true;
        // }

        //广告主
        e.advertiser = {
          value: e.adSolutionContent.advertiser,
          data: e.adSolutionContent.advertiserId
        };
        if ( scope.basic.customerDirect ) {
          e.advertiser = scope.basic.customerDirect;
        }
        e.area = AdContentUtil.findArea(e.adSolutionContent.areaId, e.areaVOList);
        e.position = AdContentUtil.findPostion(e.adSolutionContent.positionId, e.positionVOList);
        if (scope.e.position) {
          //行业类型赋值
          $timeout(function () {
            e.industrySelected = e.advertiseQuotation.industryType;
            e.priceDisabled = false;
            scope.selectIndusty();
          }, 200);
        }
        if (scope.e.position && !scope.e.position.maxDate) {
          AdContentAdvertising.setPostionMaxDate(scope.e.position);
        }
        e.datePeriods = AdContentAdvertising.getDatePeriods(e);
        AdContentAdvertising.ifPeriodsEmpty(scope);
        // if (!e.datePeriods || !e.datePeriods.length) {
        //   e.datePeriods = [{}];
        // }
        e.totalDays = e.adSolutionContent.totalDays;
        // e.insertDates = AdContentAdvertising.getInsertDates(e.adSolutionContent.insertPeriodDescription);
        // e.selectedInsertDates = e.insertDates;
        // e.selectedInsertDays = e.insertDates.length;
        // e.insertDatesCheckAll = true;
        //更新总投放天数
        // AdContentAdvertising.updateSumDays(scope.e);

        promiseQuotionPattern = AdContentPricing.queryQuotionPattern(scope);
        promiseQuotionPattern.then(function () {
          $timeout(function () {
            scope.formAd.$setPristine();
          });
        });

        if (scope.e.advertiseQuotation.billingModelId === 4 || scope.e.advertiseQuotation.dailyAmount) {
          scope.e.setDailyAmount = true;
        }

        e.setMonitor = !!e.adSolutionContent.monitorUrl;
      },
       /**
       * 更新直客广告主至各广告内容
       * @param  {[type]} scope [description]
       * @return {[type]}       [description]
       */
      updateDirect: function (ad, customerDirect) {
        if (customerDirect) {
          ad.advertiser = angular.copy(customerDirect);
          ad.adSolutionContent.advertiser = customerDirect.value;
          ad.innerScope.e.advertiser = angular.copy(customerDirect);
        }
      },
      /**
       * 更新广告内容的各控制状态
       * @param  {[type]} scope [description]
       * @return {[type]}       [description]
       */
      updateContentState: function (scope) {
        contentState = scope.contentState;
        //审批记录按钮是否出现
        contentState.showBtnApprovalRecord = AdContentState.showBtnApprovalRecord(scope);

        //重新排期按钮是否出现
        // contentState.canReschedule = AdContentState.canReschedule(scope);

        //确认广告内容按钮是否出现
        // contentState.canConfirm = AdContentState.canConfirm(scope);

        //修改按钮是否出现
        contentState.canModify = AdContentState.canModify(scope);

        //自动保存
        contentState.autoSave = AdContentState.autoSave(scope);

        //变更按钮是否出现
        contentState.canChange = AdContentState.canChange(scope);

        //终止合作按钮是否出现
        contentState.canTerminate = AdContentState.canTerminate(scope);

        //撤销按钮是否出现
        contentState.canWithdraw = AdContentState.canWithdraw(scope);

        //变更PO按钮是否出现
        contentState.canChangePo = AdContentState.canChangePo(scope);

        //删除按钮是否出现
        contentState.canDelete = AdContentState.canDelete(scope);

        //是否显示taskinfo
        contentState.showTaskInfo = AdContentState.showTaskInfo(scope);
      },
      // confirmContentSchedule: function (ad) {
      //   return AdHttp.confirmContentSchedule({id: ad.adSolutionContent.id, overdue: ad.overdue});
      // },
      save: function (scope) {
        if (!(scope.e.adSolutionContent.id || scope.e.advertiser)) {
          return;
        }
        var defered = $q.defer();
        var postData = this.packSaveData(scope);
        var AdContent = this;
        AdHttp.save(postData).success(function (response) {
          if (response.code === 200) {
            angular.extend(scope.ad, response.data);
            scope.ad.canUpdate = true;
            scope.e.adSolutionContent.id = scope.ad.adSolutionContent.id;
            if (scope.ad.advertiseQuotation) {
              scope.e.advertiseQuotation.id = scope.ad.advertiseQuotation.id;
            }

            AdContentAdvertising.ifPeriodsEmpty(scope);

            AdContent.endEdit(scope);

            AdContentUtil.resolveMaterialAfterSaved(scope, response.data);

            AdContent.updateContentState(scope);

            AdContentUtil.setSaveTime(scope);

            scope.state.isSolutionChanged = true;
            defered.resolve({
              success: true
            });
          } else if ( response.code === 204 ) {
            var errorList = AdContentUtil.resolveErrorList(scope, response.errorList);
            defered.resolve({
              success: false,
              errorList: errorList
            });
          }
        });
        return defered.promise;
      },
      endEdit: function (scope) {
        //非自动保存时，切换回预览视图，并退出全局编辑状态
        if (!scope.contentState.autoSave) {
          scope.state.isGlobleEditing = false;
          scope.ad.review = true;
        }
      },
      activateValidator: function (scope) {
        scope.e.contentSave = true;
        scope.e.hiddenElValidator = true;
        scope.e.positionChangeValidator = true;
        scope.e.quotesValidator = true;
        // scope.e.materialValidator = true;
      },
      deactivateValidator: function (scope) {
        scope.e.contentSave = false;
        scope.e.hiddenElValidator = false;
        scope.e.positionChangeValidator = false;
        scope.e.quotesValidator = false;
        // scope.e.materialValidator = false;
      },
      saveToApproval: function (scope, type) {
        var postData = this.packSaveData(scope);
        postData.approvalType = type;
        return AdHttp.contentApproval(postData);
      },
      packSaveData: function(scope) {
        var e = scope.e;
        if (!e.adSolutionContent.adSolutionId) {
          e.adSolutionContent.adSolutionId = scope.solutionId;
        }
        if (e.advertiser) {
          e.adSolutionContent.advertiser = e.advertiser.value;
          if ( e.advertiser.data ) {
            e.adSolutionContent.advertiserId = e.advertiser.data;
          }
        }
        e.adSolutionContent.areaId = e.area && e.area.id;
        if (e.position) {
          e.adSolutionContent.positionId = e.position.id;
          e.adSolutionContent.materialType = e.position.materialType;
        } else {
          e.adSolutionContent.positionId = null;
          e.adSolutionContent.materialType = null;
        }
        e.periods = e.datePeriods && AdContentAdvertising.resolvePostPeriods(e.datePeriods);

        if (e.advertiseQuotation) {
          var adQ = e.advertiseQuotation;
          if ( typeof e.industrySelected !== 'undefined' ) {
            adQ.industryType = scope.e.industrySelected;
          }
        }

        return e;
      }
    };
  }]);

  app.registerService('AdContentUtil', [
    '$filter',
    '$timeout',
    '$q',
    'AdHttp',
    'Position',
    'PublishPrice',
    'Utils',
    'GCRMUtil',
  function ($filter, $timeout, $q, AdHttp, Position, PublishPrice, Utils, GCRMUtil) {
    return {
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
      getAdIndex: function (scope, adId) {
        for (var i = 0; i < scope.adContents.length; i++) {
          if ( scope.adContents[i] && scope.adContents[i].adSolutionContent.id === ~~adId ) {
            return i;
          } else {
            continue;
          }
        }
        return -1;
      },
      toggleValidator: function (scope, ifValidate) {
        scope.e.contentSave = ifValidate;
        scope.e.hiddenElValidator = ifValidate;
        scope.e.positionChangeValidator = ifValidate;
        scope.e.quotesValidator = ifValidate;
        // scope.e.materialValidator = ifValidate;
        scope.e.codeContentValidator = ifValidate;
      },
      resolveErrorArgsForAdContent: function (scope, args) {
        for (var i = 0; i < args.length; i++) {
          if ( args[i] ) {
            args[i] = $filter('translate')('AD_CONTENT_TITLE') + (this.getAdIndex(scope, args[i]) + 1);
          } else {
            args[i] = $filter('translate')('AD_CONTENT_CURRENT');
          }
        }
        return args;
      },
      resolveErrorList: function (scope, errorList) {
        var retList = [];
        for (var i = 0; i < errorList.length; i++) {
          var
            errorArgs = this.resolveErrorArgsForAdContent(scope, errorList[i].args);
            error = GCRMUtil.translate(errorList[i].key, errorArgs);
          retList.push(error);
        }
        return retList;
      },
      resolveMaterialAfterSaved: function (scope, data) {
        // if (!data.advertiseCodeMaterial) {
        //   scope.ad.advertiseCodeMaterial = {};
        // }
        if (data.emptyMaterial || !data.advertiseMaterials) {
          // scope.advertiseMaterials = [];
          scope.ad.adSolutionContent.materialApplyList = [];
        }
      },
      setSaveTime: function (scope) {
        scope.ad.saveTime = new Date();
      }
    };
  }]);

  app.registerService('AdContentState', [function () {
    return {
      //审批记录按钮是否出现
      showBtnApprovalRecord: function (scope) {
        return !!scope.ad.adSolutionContent.submitTime;
      },
      //重新排期按钮是否出现
      // canReschedule: function (scope) {
      //   var ad = scope.ad,
      //       BtnIndex = scope.BtnIndex;
      //   return (
      //     (!scope.OwnerOpers.btn_adsol_detail_reschedule || scope.basic.isOwner) &&
      //     BtnIndex.btn_adsol_detail_reschedule &&
      //     ad.adSolutionContent.approvalStatus === 'approved' &&
      //     ad.schedule && ad.schedule.status === 'released' &&
      //     !ad.adSolutionContent.taskInfo
      //   );
      // },
      //确认广告内容按钮是否出现
      // canConfirm: function (scope) {
      //   var ad = scope.ad;
      //   return (
      //     (!scope.OwnerOpers.btn_adsol_detail_cont_conf || scope.basic.isOwner) &&
      //     scope.BtnIndex.btn_adsol_detail_cont_conf &&
      //     ad.adSolutionContent.approvalStatus === 'unconfirmed' &&
      //     ad.schedule.status !== 'released'
      //   );
      // },
      //修改按钮是否出现
      canModify: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex,
            adSolution = scope.basic.adSolution;

        var canUpdate = (ad.canUpdate && !scope.state.isDetailView),
            contentSaving = ( scope.state.isDetailView &&
                              (ad.adSolutionContent.approvalStatus === 'refused' || ad.adSolutionContent.approvalStatus === 'saving')
                              // (
                                // (ad.adSolutionContent.approvalStatus === 'refused' || ad.adSolutionContent.approvalStatus === 'saving') ||
                                // (ad.adSolutionContent.approvalStatus === 'unconfirmed' && ad.schedule && ad.schedule.status !== 'released') ||
                                // (ad.adSolutionContent.approvalStatus === 'confirmed' && ad.schedule && ad.schedule.status === 'released' && !adSolution.contractType)
                              // )
                            );
        return (
          (!scope.OwnerOpers.btn_adsol_detail_cont_mod || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_cont_mod &&
          (canUpdate || contentSaving)
        );
      },
      //变更按钮是否出现
      canChange: function (scope) {
        var ad = scope.ad;
        var contract = scope.basic.contract;
        var BtnIndex = scope.BtnIndex;
        var isModify = !!(scope.state.isDetailView &&
                          (ad.adSolutionContent.approvalStatus === 'refused' || ad.adSolutionContent.approvalStatus === 'saving'));
        var isChange = !!(ad.adSolutionContent.approvalStatus === 'approved' || ad.adSolutionContent.approvalStatus === 'effective');
        return (
          (!scope.OwnerOpers.btn_adsol_detail_cont_change || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_cont_change &&
          // contract && contract.data &&  contract.data.state === 'VALID' &&
          // ad.adSolutionContent.poNum &&
          // ad.adSolutionContent.approvalStatus === 'confirmed' &&
          (isModify || isChange) && // 原修改按钮
          // ad.schedule.status !== 'released' &&
          ad.canUpdate
        );
      },
      //是否自动保存
      autoSave: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return (
          !scope.basic.oldSolutionNumber &&
          (!scope.OwnerOpers.btn_adsol_detail_cont_save || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_cont_save &&
          scope.ad.canUpdate && !scope.state.isDetailView
        );
      },
      //终止合作按钮是否出现
      canTerminate: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return (
          (!scope.OwnerOpers.btn_adsol_detail_coop_term || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_coop_term &&
          (
            // ad.adSolutionContent.approvalStatus === 'unconfirmed' ||
            // (
              ad.adSolutionContent.approvalStatus === 'approved' &&
              // ad.schedule && ad.schedule.status === 'released' &&
              !ad.adSolutionContent.taskInfo
            // )
          )
        );
      },
      //撤销按钮是否出现
      canWithdraw: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return (
          (!scope.OwnerOpers.btn_adsol_detail_contract_withdraw || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_contract_withdraw &&
          ad.canWithdraw
        );
      },
      //变更PO按钮是否出现
      canChangePo: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return (
          (!scope.OwnerOpers.btn_adsol_detail_po_create || scope.basic.isOwner) &&
          BtnIndex.btn_adsol_detail_po_create &&
          ad.canCreatePO
        );
      },
      //删除按钮是否出现
      canDelete: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return !(
          !(!scope.OwnerOpers.btn_adsol_detail_cont_save || scope.basic.isOwner) ||
          !BtnIndex.btn_adsol_detail_cont_save ||
          scope.state.isDetailView ||
          scope.state.isSolutionTypeUpdate
        );
      },
      //是否显示taskinfo
      showTaskInfo: function (scope) {
        var ad = scope.ad,
            BtnIndex = scope.BtnIndex;

        return (
          ad.adSolutionContent.approvalStatus == 'approved' ||
          ad.adSolutionContent.approvalStatus == 'approving' ||
          ad.adSolutionContent.approvalStatus == 'saving' ||
          ad.adSolutionContent.approvalStatus == 'refused'
        );
      }
    };
  }]);

  app.registerService('AdContentPosition', [
    'Position',
  function (Position) {
    return {
      getSiteList: function (scope, productId) {
        var e = scope.e;
        e.adSolutionContent.siteId = null;
        e.siteList = [];
        e.adSolutionContent.channelId = null;
        e.channelVOList = [];
        e.area = null;
        e.areaVOList = [];
        e.position = null;
        e.positionVOList = [];
        if (productId) {
          Position.getSiteList({productId: productId}).success(function (response) {
            if (response.code === 200) {
              e.siteList = response.data;
            }
          });
        }
      },
      getChannelList: function (scope, productId, siteId) {
        var e = scope.e;
        e.channelVOList = [];
        e.adSolutionContent.channelId = null;
        e.area = null;
        e.areaVOList = [];
        e.position = null;
        e.positionVOList = [];
        if (productId, siteId) {
          Position.getChannelList({productId: productId, siteId: siteId}).success(function (response) {
            if (response.code === 200) {
              e.channelVOList = response.data;
            }
          });
        }
      },
      getAreaList: function (scope, channelId) {
        scope.e.area = null;
        scope.e.areaVOList = [];
        scope.e.position = null;
        scope.e.positionVOList = [];
        if (channelId) {
          Position.getAreaList({parentId: channelId}).success(function (response) {
            if (response.code === 200) {
              scope.e.areaVOList = response.data;
            }
          });
        }
      },
      getPositionList: function (scope, areaId) {
        scope.e.position = null;
        scope.e.positionVOList = [];
        if (areaId) {
          Position.getPositionList({parentId: areaId}).success(function (response) {
            if (response.code === 200) {
              scope.e.positionVOList = response.data;
            }
          });
        }
      }
    };
  }]);

  app.registerService('AdContentAdvertising', [
    '$q',
    '$timeout',
    '$filter',
    'AdHttp',
    'AdContentUtil',
    'AdContentPricing',
    'Utils',
    'DateStatus',
  function ($q, $timeout, $filter, AdHttp, AdContentUtil, AdContentPricing, Utils, DateStatus) {
    return {
      setPostionMaxDate: function (position) {
        if (position && position.id) {
          DateStatus.getMaxDate({
            id: position.id
          }).success(function (response) {
            if (response.code === 200 && response.data) {
              position.maxDate = response.data;
            }
          });
        }
      },
      queryOccupation: function (scope) {
        var e = scope.e;
        var defered = $q.defer();
        // var insertDate = scope.e.insertDates;
        //查询插单时间
        if ( !e.position.id || !e.datePeriods) {
          return false;
        }
        var postPeriods = this.resolvePostPeriods(e.datePeriods);
        var postData = {
          positionId: e.position.id,
          billingModelId: e.advertiseQuotation.billingModelId,
          oldContentId: e.adSolutionContent.id,
          datePeriod: postPeriods
          // insertDate: insertDate
        };
        if ( e.adSolutionContent.approvalStatus === 'confirmed' ) {
          angular.extend( postData, { oldContentId: e.adSolutionContent.id } );
        } else if ( e.adSolutionContent.contentType === 'update' ) {
          angular.extend( postData, { oldContentId: e.adSolutionContent.oldContentId } );
        }
        var AdContentAdvertising = this;
        AdHttp.queryOccupation(postData).success(function(response) {
          if (response.code === 200) {
            // e.datePeriods = response.data.datePeriod;
            e.datePeriods = AdContentAdvertising.resolvePeriods(response.data.datePeriod);
            //如果所查period是空，置一个空的period
            AdContentAdvertising.ifPeriodsEmpty(scope);

            e.totalDays = response.data.totalDays;
            AdContentAdvertising.updateMinStock(e, response.data);

            //更新总投放天数
            // AdContentAdvertising.updateSumDays(e);
            // 更新总投放量、总价，进而更新预算
            AdContentPricing.updateBudget(e);
            // 更新客户报价
            AdContentPricing.updateCustomerQuotation(e);
            AdContentPricing.updateDiscount(e);

            AdContentPricing.updateDailyAmount(e);
            if (scope.basic.advertiseType === 'zerotest') {
              AdContentPricing.updateTotalByDailyAmount(e);
            }
            defered.resolve();
          }
        });
        return defered.promise;
      },
      updateMinStock: function (e, data) {
        e.minStock = data.minStock;
        e.minStockPeriods = $filter('DatePeriodFilter')(data.minStockPeriods);
        e.minStockHint = $filter('translate')('MIN_STOCK_HINT', {minStock: e.minStock});
      },
      isMinStockGood: function (e) {
        if (e.setDailyAmount && e.minStock<e.advertiseQuotation.dailyAmount) {
          return false;
        } else {
          return true;
        }
      },
      getDatePeriods: function (e) {
        var periodDesc = e.adSolutionContent.periodDescription;
        if ( !periodDesc ) {
          return null;
        }
        var datePeriod = [],
            resultDatePeriods = [],
            periodSplit = periodDesc.split(';');
        for (var i = 0; i < periodSplit.length; i++) {
          datePeriod = periodSplit[i].split(',');
          if (datePeriod.length > 1) {
            var period = {
              from: datePeriod[0],
              to: datePeriod[1]
            };
            if (e.datePeriodState && e.datePeriodState.length) {
              period.startDisabled = !e.datePeriodState[i].startChangeable;
              period.endDisabled = !e.datePeriodState[i].endChangeable;
            }
            resultDatePeriods.push(period);
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
      /**
       * 将后台返回的时间戳全部伪造成本地的
       * @param  {[type]} periods [description]
       * @return {[type]}         [description]
       */
      resolvePeriods: function (periods) {
        if (periods) {
          for (var i = periods.length - 1; i >= 0; i--) {
            // if ( !periods[i].from || !periods[i].to ) {
            //   periods.splice(i, 1);
            //   continue;
            // }
            if (periods[i].from) {
              periods[i].from = this.fakeLocalTimestamp(periods[i].from);
            }
            if (periods[i].to) {
              periods[i].to = this.fakeLocalTimestamp(periods[i].to);
            }
          }
        }
        return periods;
      },
      /**
       * 保证往后台发送的是日期字符串
       * @param  {[type]} periods [description]
       * @return {[type]}         [description]
       */
      resolvePostPeriods: function (periods) {
        for (var i = periods.length - 1; i >= 0; i--) {
          // if ( !periods[i].from || !periods[i].to ) {
          //   periods.splice(i, 1);
          //   continue;
          // }
          if (angular.isDate(periods[i].from)) {
            periods[i].from = $filter('date')(periods[i].from, 'yyyy-MM-dd');
          //如果不是日期字符串，则必定是服务器返回的服务器时间戳，将其伪造成当地日期
          } else if (periods[i].from && (!/-/.test(periods[i].from) || angular.isNumber(periods[i].from))) {
            // periods[i].from = this.fakeLocalDate(periods[i].from);
            periods[i].from = Utils.getDateString(periods[i].from);
          }

          if (angular.isDate(periods[i].to)) {
            periods[i].to = $filter('date')(periods[i].to, 'yyyy-MM-dd');
          //如果不是日期字符串，则必定是服务器返回的服务器时间戳，将其伪造成当地日期
          } else if ( periods[i].to && (!/-/.test(periods[i].to) || angular.isNumber(periods[i].to))) {
            periods[i].to = Utils.getDateString(periods[i].to);
          }

          if ( periods[i].from && periods[i].to && periods[i].to < periods[i].from ) {
            var temp = periods[i].to;
            periods[i].to = periods[i].from;
            periods[i].from = temp;
          }
        }
        return periods;
      },
      ifPeriodsEmpty: function (scope) {
        if (scope.e.position && (!scope.e.datePeriods || !scope.e.datePeriods.length) ) {
          scope.e.datePeriods = [{}];
        }
      },
      clearPeriods: function (e) {
        e.datePeriods = null;
        // e.insertDates = null;
      },
      resetPeriods: function (e) {
        e.datePeriods = [{}];
        // e.insertDates = null;
        // e.selectedInsertDays = 0;
        e.totalDays = 0;
        // e.sumDays = 0;
      },
      /**
       * 用服务器时间戳伪造一个当地时间时间戳，使其日期与服务器时间所表示的服务器日期一致
       * @param  {[type]} timestamp [description]
       * @param  {[type]} offset    [description]
       * @return {[type]}           [description]
       */
      fakeLocalTimestamp: function (timestamp, offset) {
        var fromOffset = offset || 480;  //默认为+8区偏离分钟数
        var localOffset = - (new Date()).getTimezoneOffset();

        //服务器的日期存的当地时间，比如2013-10-12，实际存的是2013-10-12 00:00:00 GMT+0800的毫秒时间戳, 即2013-10-11 16:00:00 UTC的毫秒时间戳，设为'beijing'，
        //此时间戳，在GMT-7为2013-10-11 09:00:00 GMT-0700
        //那么要在GMT-7体现2013-10-12 00:00:00，则 beijing + 8*60*60*1000 - (-7)*60*60*1000
        return (timestamp + (fromOffset - localOffset) * 60*1000);
      },
      fakeLocalDate: function (timestamp, offset) {
        var fakeTimeStamp =  this.fakeLocalTimestamp(timestamp, offset);
        var fakeDate = new Date(fakeTimeStamp);
        return Utils.getDateString(fakeDate);
      },
      // updateSumDays: function (e) {
      //   // e.sumDays = ((e.totalDays - (e.insertDates.length - e.selectedInsertDays)) || 0);
      //   e.sumDays = e.totalDays;
      // },
      clearEmptyPeriod: function (datePeriods) {
        if (!datePeriods) {
          return;
        }
        for (var i = datePeriods.length - 1; i > 0; i--) {
          var period = datePeriods[i];
          if (!period.from && !period.to) {
            datePeriods.splice(i, 1);
          }
        }
      }
    };
  }]);

  app.registerService('AdContentPricing', [
    '$q',
    'AdContentUtil',
    'PublishPrice',
  function ($q, AdContentUtil, PublishPrice) {
    /**
     *  billingModels = [
          {i18nName:CPC, id:1, name:CPC, type:0},
          {i18nName:CPS, id:2, name:CPS, type:1},
          {i18nName:CPA, id:3, name:CPA, type:0},
          {i18nName:CPM, id:4, name:CPM, type:0},
          {i18nName:CPT, id:5, name:CPT, type:0}
        ]
     */
    return {
      clearBillingModel: function (e) {
        e.advertiseQuotation.billingModelId = '';
      },
      clearPrice: function (scope) {
        var q = scope.e.advertiseQuotation;
        q.ratioMine = null;
        q.ratioCustomer = null;
        q.ratioThird = 0;
        q.reachEstimate = false;
        q.ratioConditionDesc = '';
        q.publishPrice = null;
        q.customerQuote = null;
        q.discount = null;
        q.budget = null;
        q.totalPrice = null;
        q.dailyAmount = null;
        q.totalAmount = null;

        if (scope.basic.advertiseType==='zerotest') {
          this.updateZero(q);
        }

        AdContentUtil.toggleValidator(scope, false);
      },
      resetPrice: function (scope) {
        this.clearPrice(scope);
        scope.e.priceDisabled = true;
        // scope.e.advertiseQuotation.priceType = null;
        // 暂无“分成”这种价格种类，默认为“单价”
        scope.e.advertiseQuotation.priceType = 'unit';
        //清空位置信息
        scope.e.position = null;
      },
      updateZero: function (advertiseQuotation) {
        advertiseQuotation.customerQuote = 0;
        advertiseQuotation.discount = 0;
        advertiseQuotation.budget = 0;
        advertiseQuotation.totalPrice = 0;
      },
      queryQuotionPattern: function (scope) {
        var defered = $q.defer();
        var q = scope.e.advertiseQuotation;
        var queryParams = {
          advertisingPlatformId: scope.e.adSolutionContent.productId,
          siteId: scope.e.adSolutionContent.siteId,
          billingModelId: scope.e.advertiseQuotation.billingModelId
        };
        if ( !queryParams.billingModelId ) {
          delete queryParams.billingModelId;
        }
        PublishPrice.post(queryParams).success(function (response) {
          if ( response.code === 200 && response.data.result ) {
            if ( response.data.result.length && response.data.result[0] ) {
              if ( ~~q.billingModelId === 2 ) {
                scope.e.productQuotes = response.data.result;
                if ( scope.e.industrySelected || scope.e.industrySelected === 0 ) {
                  scope.selectIndusty();
                }
              } else {
                q.publishPrice = response.data.result[0].publishPrice;
                q.productRatioMine = response.data.result[0].ratioMine;
                q.productRatioCustomer = response.data.result[0].ratioCustomer || 0;
                q.productRatioThird = response.data.result[0].ratioThird || 0;
              }
            } else if (scope.basic.advertiseType!=='zerotest') {
              // 未备案刊例价时，如果不是“0圆测试单”，折扣自动填写1，用户可修改。
              q.discount = '1.00';
            }
            defered.resolve();
          }
        });
        return defered.promise;
      },
      updateDiscount: function (e) {
        var q = e.advertiseQuotation;
        //如果存在刊例价，则折扣 = 客户报价 / 刊例价
        if (q.publishPrice) {
          q.discount = q.customerQuote / q.publishPrice || 0;
        }

        if (+q.discount > 0) {
          q.discount = (+q.discount).toFixed(2);
        }
      },
      updateBudget: function (e) {
        var q = e.advertiseQuotation;
        var billingModelId = q.billingModelId;

        if (billingModelId === 4 || billingModelId === 1) {
          //CPM 预算 = 客户报价 * 总投放量
          if (+q.totalAmount) {
            q.budget = q.customerQuote * q.totalAmount || 0;
          }
        } else if (billingModelId === 5) {
          //CPT 预算 = 客户报价 * 投放天数
          if (+q.customerQuote && +e.totalDays) {
            q.budget = q.customerQuote * e.totalDays || 0;
          }
        }

        if (+q.budget) {
          q.budget = (+q.budget).toFixed(2);
        }
      },
      updateTotalByBudget: function (e) {
        var q = e.advertiseQuotation;
        var billingModelId = q.billingModelId;
        var dailyAmount = q.dailyAmount || 0;
        var customerQuoteTotal = (q.customerQuote || 0) * e.totalDays;

        if (billingModelId === 4 || billingModelId === 1) {
          //CPM、CPC
          if (+q.budget && +q.customerQuote && e.totalDays) {
            q.totalAmount = Math.floor(q.budget / q.customerQuote || 0);
          }
        }
      },
      updateTotalByDailyAmount: function (e) {
        var q = e.advertiseQuotation;
        var billingModelId = q.billingModelId;
        var dailyAmount = q.dailyAmount || 0;
        if (+dailyAmount) {
          q.totalAmount = e.totalDays * dailyAmount;
        }
      },
      updateDailyAmount: function (e) {
        var q = e.advertiseQuotation;
        if (e.setDailyAmount && +q.budget && e.totalDays) {
          q.dailyAmount = Math.floor(q.totalAmount / e.totalDays || 0);
        }
      },
      updateCustomerQuotation: function (e) {
        var q = e.advertiseQuotation;
        var billingModelId = q.billingModelId;

        if ((billingModelId === 4 || billingModelId === 1) && q.totalAmount) {
          //CPM 客户报价 = 预算 / 总投放量
          q.customerQuote = q.budget / q.totalAmount || 0;
        } else if (billingModelId === 5  && e.totalDays) {
          //CPT 客户报价 = 预算 / 投放天数
          if (+q.budget && +e.totalDays) {
            q.customerQuote = q.budget / e.totalDays || 0;
          }
        }

        if (+q.customerQuote > 0) {
          q.customerQuote = (+q.customerQuote).toFixed(2);
        }
      },
      updateCustomerQuotationByDiscount: function (e) {
        var q = e.advertiseQuotation;
        //如果存在标杆价，客户报价 = 标杆价 * 折扣
        if (q.publishPrice) {
          q.customerQuote = q.publishPrice * q.discount || 0;
        }

        if (+q.customerQuote > 0) {
          q.customerQuote = (+q.customerQuote).toFixed(2);
        }
      }
    };
  }]);

  app.registerService('AdContentMaterial', [
    '$timeout',
    'AdConstant',
  function ($timeout, AdConstant) {
    return {
      reset: function (e) {
        e.materialFileTypes = [
          {id:0, i18nName: AdConstant.materialFileTypeImage},
          {id:1, i18nName: AdConstant.materialFileTypeFlash},
          {id:2, i18nName: AdConstant.materialFileTypeCode}
        ];

        e.adSolutionContent.materialEmbedCode = 0;
        e.adSolutionContent.materialEmbedCodeContent = null;
        e.adSolutionContent.materialTitle = null;
        e.adSolutionContent.materialUrl = null;
        e.adSolutionContent.materialFileType = null;
        e.adSolutionContent.materialApplyList = [];
        // e.advertiseMaterials = [];
        e.setMonitor = false;
        e.adSolutionContent.monitorUrl = null;

        this.updateMaterialFileTypes(e);
      },
      updateMaterialFileTypes: function (e) {
        if (e.position && e.position.materialType === 2) {
          e.adSolutionContent.materialFileType = 0;
          for (var i = e.materialFileTypes.length - 1; i > 0; i--) {
            e.materialFileTypes[i].disabled = true;
          }
        }
      }
    };
  }]);
});