define([
  'app',
  '_directives/ytCalendar',
  '_directives/periodLabel',
  '_directives/ytJqueryFileUpload',
  '_directives/ytAjaxupload',
  '_directives/ytInputRadio',
  'ad2/ModalPubApply',
  'material/MaterialEditModal',
  'resourcePosition/ModalSchedule'
], function(app) {
  app.registerController('CtrlAdContent', [
    '$scope',
    '$window',
    '$interval',
    'AdHttp',
    'AdContent',
    'AdContentUtil',
    'AdConstant',
    'GCRMUtil',
    'SolutionApprovalRecord',
    'ModalC',
    'ModalPubApply',
    'AdContentAdvertising',
  function ($scope, $window, $interval, AdHttp, AdContent, AdContentUtil, AdConstant, GCRMUtil, SolutionApprovalRecord, ModalC, ModalPubApply, AdContentAdvertising) {

    //初始化
    $scope.$parent.contentState = {};
    AdContent.init($scope);
    //查看审批记录
    $scope.btnApprovalRecord = function () {
      SolutionApprovalRecord.forContent($scope.ad.adSolutionContent.id, {windowClass: 'approval-record-modal'});
    };

    //取消一个添加的广告内容
    $scope.adContentAddCancel = function () {
      var index = $scope.$index;
      $scope.adContents.splice(index, 1);
      $scope.state.isGlobleEditing = false;
    };

    //取消编辑广告内容
    $scope.adContentEditCancel = function () {
      $scope.ad.review = true;
      $scope.state.isGlobleEditing = false;
    };

    //重新排期
    // $scope.btnReSchedule = function () {
    //   AdHttp.reSchedule({
    //     id: $scope.ad.adSolutionContent.id
    //   }).success(function (response) {
    //     if ( response.code === 200 ) {
    //       ModalC.success({
    //         content: AdConstant.rescheduleSuccess
    //       }).then(function (result) {
    //         $window.location.reload();
    //       });
    //     }
    //   });
    // };

    //确认广告内容,点击后确定国代的排期信息
    // $scope.btnConfirmContent = function () {
    //   var ad = $scope.ad;
    //   var scheduleNumber = ad.schedule.number;
    //   if ( ad.overdue ) {
    //     ModalC.confirm({
    //       content: AdConstant.confirmWarn,
    //       psInfo: AdConstant.confirmWarnPS
    //     }).then(function (result) {
    //      confirmContentSchedule(ad);
    //     });
    //   } else {
    //     confirmContentSchedule(ad);
    //   }

    //   function confirmContentSchedule(ad) {
    //     AdContent.confirmContentSchedule(ad).success(function (response) {
    //       if ( response.code === 200 ) {
    //         $window.location.reload();
    //       }
    //     });
    //   }
    // };

    //终止合作，广告内容
    $scope.btnTerminate = function () {
      ModalC.confirm({
        content: AdConstant.terminateWarn,
        psInfo: AdConstant.terminatePS
      }).then(function (result) {
        AdHttp.contentTerminate({
          id: $scope.ad.adSolutionContent.id
        }).success(function ( response ) {
          if ( response.code === 200 ) {
            $window.location.reload();
          }
        });
      });
    };

    //变更PO
    $scope.btnChangePO = function () {
      AdHttp.changePO({
        adcontentId: $scope.ad.adSolutionContent.id,
        contractNumber: $scope.basic.contract.data.number
      }).success(function (response) {
        if ( response.code === 200 ) {
          ModalC.success({
            content: AdConstant.successChangePO
          }).then(function (result) {
            $window.location.reload();
          });
        }
      });
    };

    //撤销内容
    $scope.btnCancelContent = function () {
      AdHttp.cancelContent({
        id: $scope.ad.adSolutionContent.id
      }).success(function (response) {
        // 撤销成功后
        if ( response.code === 200 ) {
          ModalC.success({
            content: AdConstant.successAdCancel
          }).then(function (result) {
            $window.location.reload();
          });
        }
      });
    };

    //删除一个广告内容
    $scope.adContentRemove = function() {
      var ad = $scope.ad,
          index = $scope.$index;
      var contentId = ad.solutionContentId || ad.adSolutionContent.id;
      if (contentId) {
        AdHttp.contentRemove({
          id: contentId
        }).success(function(response) {
          if (response.code === 200) {
            $scope.adContents.splice(index, 1);
            $scope.anchorTo( 'anchorContent' + (index - 1));
          }
        });
      } else {
        $scope.adContents.splice(index, 1);
        $scope.anchorTo( 'anchorContent' + (index - 1));
      }
    };

    //编辑广告内容
    $scope.btnEditContent = function () {
      AdContent.edit($scope);
    };

    //保存广告内容
    $scope.adContentSave = function(isManual) {
      if (isManual && !$scope.contentState.autoSave) {
        AdContent.activateValidator($scope);
      }
      if (($scope.contentState.autoSave && $scope.formAd.$dirty) || (isManual && $scope.formAd.$valid && AdContentAdvertising.isMinStockGood($scope.e))) {
        $scope.formAd.$setPristine();
        return AdContent.save($scope).then(function (result) {
          if (result.success) {

          } else if (result.errorList) {
            ModalC.alert({contentList: result.errorList});
            AdContent.activateValidator($scope);
          }
          return result;
        });
      }
      $scope.formAd.$setPristine();
    };

    //变更广告内容
    $scope.btnChangeContent = function () {
      AdContent.edit($scope, true);
    };

    //保存变更（提交内容审核）
    $scope.saveToApproval = function (type) {
      if ( type && $scope.formAd.$valid && AdContentAdvertising.isMinStockGood($scope.e)) {
        AdContent.deactivateValidator($scope);
        AdContent.saveToApproval($scope, type).success(function (response) {
          if (response.code === 200) {
            $window.location.reload();
          } else if ( response.code === 204 ) {
            var errorText = [];
            for (var i = 0; i < response.errorList.length; i++) {
              errorText.push(GCRMUtil.translate(response.errorList[i].key, AdContentUtil.resolveErrorArgsForAdContent($scope, response.errorList[i].args)));
            }
            ModalC.alert({contentList: errorText});
          }
        });

      } else {
        AdContent.activateValidator($scope);
        $scope.anchorTo( 'anchorContent' + $scope.$index );
      }
    };

    //cps切换商业类型，显示对应的备案比例
    $scope.selectIndusty = function() {
      if (!$scope.e.productQuotes || !$scope.e.productQuotes.length) {
        // $scope.e.advertiseQuotation.ratioMine = null;
        $scope.e.advertiseQuotation.productRatioMine = null;
        return false;
      }
      for (var i = 0; i < $scope.e.productQuotes.length; i++) {
        if ($scope.e.productQuotes[i].industryId === undefined) {
          $scope.e.advertiseQuotation.productRatioMine = $scope.e.productQuotes[i].ratioMine;
          // $scope.e.advertiseQuotation.ratioMine = $scope.e.productQuotes[i].ratioMine;
          break;
        }
        if ($scope.e.productQuotes[i].industryId === ~~$scope.e.industrySelected) {
          $scope.e.advertiseQuotation.productRatioMine = $scope.e.productQuotes[i].ratioMine;
          // $scope.e.advertiseQuotation.ratioMine = $scope.e.productQuotes[i].ratioMine;
          break;
        } else {
          $scope.e.advertiseQuotation.productRatioMine = null;
          // $scope.e.advertiseQuotation.ratioMine = null;
        }
      }
    };

    //提前上线申请弹窗
    $scope.popPubApply = function (isEdit) {
      var
        applyStatus = $scope.ad.onlineApplyApprovalStatus,
        opts = {
          applyStatus: applyStatus,
          customerId: $scope.basic.customerI18nView.customer.id,
          adSolutionContentApply: {
            adSolutionId: $scope.ad.adSolutionContent.adSolutionId,
            adSolutionContentId: $scope.ad.adSolutionContent.id,
            adSolutiionContentNumber: $scope.ad.adSolutionContent.number
          },
          attachments: []
        };


      if (!applyStatus) {
        showPubApply(opts);
      } else {
        AdHttp.pubApplyInfo({
          applyId: $scope.ad.onlineApplyId
        }).success(function (response) {
          if (response.code === 200) {
            angular.extend(opts, response.data);
            if (applyStatus === 'refused' || applyStatus === 'withdrawn') {
              showPubApply(opts);
            } else {
              showPubApplyDetail(opts);
            }
          }
        });
      }

      function showPubApply (opts) {
        ModalPubApply.applyView(opts).then(function (result) {
          if (result.btnType === 'ok' && result.response.code === 200) {
            ModalC.success({
              content: AdConstant.pubApplySubmitSuccess
            }).then(function () {
              $scope.ad.onlineApplyApprovalStatus = 'approving';
              $scope.ad.onlineApplyId = result.response.data.id;
            });
          }
        });
      }

      function showPubApplyDetail(opts) {
        ModalPubApply.detailView(opts).then(function (result) {
          if (result.btnType === 'withdraw' && result.response.code === 200) {
            ModalC.success({
              content: AdConstant.pubApplyWithdrawSuccess
            }).then(function () {
              $scope.ad.onlineApplyApprovalStatus = 'withdrawn';
            });
          } else {
            $window.location.reload();
          }
        });
      }
    };

    function adContentPostData() {
      var postData = $scope.e;
      if (!postData.adSolutionContent.adSolutionId) {
        postData.adSolutionContent.adSolutionId = $scope.solutionId;
      }
      postData.adSolutionContent.advertiser = $scope.e.advertiser.value;
      if ( $scope.e.advertiser.data ) {
        postData.adSolutionContent.advertiserId = $scope.e.advertiser.data;
      }
      postData.adSolutionContent.areaId = $scope.e.area.id;
      postData.adSolutionContent.positionId = $scope.e.position.id;
      postData.adSolutionContent.materialType = $scope.e.position.materialType;
      postData.periods = AdContentAdvertising.resolvePostPeriods($scope.e.datePeriods);
      if ( typeof $scope.e.industrySelected !== 'undefined' ) {
        postData.advertiseQuotation.industryType = $scope.e.industrySelected;
      }

      return postData;
    }
  }]);

  app.registerController('CtrlAdContentGeneral', [
    '$scope',
  function($scope) {

  }]);

  app.registerController('CtrlAdContentPosition', [
    '$scope',
    'AdContentPosition',
    'AdContentAdvertising',
    'AdContentPricing',
    'AdContentMaterial',
    'ModalSchedule',
  function ($scope, AdContentPosition, AdContentAdvertising, AdContentPricing, AdContentMaterial, ModalSchedule) {
    $scope.changeProduct = function(productId) {
      AdContentPricing.resetPrice($scope);
      AdContentPosition.getSiteList($scope, productId);
      AdContentAdvertising.clearPeriods($scope.e);
    };
    $scope.changeSite = function(productId, siteId) {
      AdContentPricing.resetPrice($scope);
      AdContentPosition.getChannelList($scope, productId, siteId);
      AdContentAdvertising.clearPeriods($scope.e);
    };
    $scope.changeChannel = function(channelId) {
      AdContentPricing.resetPrice($scope);
      AdContentPosition.getAreaList($scope, channelId);
      AdContentAdvertising.clearPeriods($scope.e);
    };
    $scope.changeArea = function(areaId) {
      AdContentPricing.resetPrice($scope);
      AdContentPosition.getPositionList($scope, areaId);
      AdContentAdvertising.clearPeriods($scope.e);
    };
    $scope.changePosition = function() {
      // 暂无“分成”这种价格种类，默认为“单价”
      $scope.e.advertiseQuotation.priceType = 'unit';
      AdContentPricing.clearBillingModel($scope.e);
      AdContentPricing.clearPrice($scope);

      AdContentAdvertising.resetPeriods($scope.e);

      //重置物料区
      AdContentMaterial.reset($scope.e);

      AdContentAdvertising.setPostionMaxDate($scope.e.position);
    };

    $scope.showSchedule = function () {
      var i18nMap = {
        'zh-CN': 'cnExtraName',
        'en-US': 'enExtraName'
      };
      var e = $scope.e;
      ModalSchedule.show({
        positionInfo: {
          positionId: e.position.id,
          // productName: item.adPlatformName,
          // siteName: item.siteName,
          // channelName: item.channelName,
          // areaName: e.area.i18nName,
          positionName: e.position.i18nData[i18nMap[$scope.lang]],
          salesAmount: e.position.salesAmount,
          rotationType: e.position.rotationType
        }
      });
    };
  }]);

  app.registerController('CtrlAdContentAdvertising', [
    '$scope',
    'AdContentPricing',
    'AdContentAdvertising',
    'Utils',
  function ($scope, AdContentPricing, AdContentAdvertising, Utils) {

    //添加时段
    $scope.addPeriod = function() {
      $scope.e.datePeriods.push({});
    };

    //时段中开始、结束时间的变更
    $scope.startTimeChange = function(period) {
      if (period.to && period.from) {
        AdContentAdvertising.queryOccupation($scope).then(function () {
          if ($scope.state.isAutoSave) {
            $scope.adContentSave();
          }
        });
      } else {
        // period.to = period.from;
        $scope.$broadcast('periodChanged', period, 'from');
      }
    };
    $scope.endTimeChange = function(period) {
      if (period.to && period.from) {
        AdContentAdvertising.queryOccupation($scope).then(function () {
          if ($scope.state.isAutoSave) {
            $scope.adContentSave();
          }
        });
      } else {
        $scope.$broadcast('periodChanged', period, 'to');
      }
    };
    //添加删除投放时段
    $scope.removePeriod = function(index) {
      //日历控件状态,如果方案类型为变更或者内容类型为变更，只能选择今日或今天往后的日期，如果存在今天之前的日期，不能删除，不能修改
      if ($scope.e.calendarFutureOnly) {
        if (typeof $scope.e.datePeriods[index].from === 'number') {
          if ($scope.e.datePeriods[index].from < Utils.getTodayZero().getTime()) {
            return false;
          }
        } else if (typeof $scope.e.datePeriods[index].from === 'string') {
          if (new Date($scope.e.datePeriods[index].from.replace(/-/g, '/')).getTime() < Utils.getTodayZero().getTime()) {
            return false;
          }
        }
      }
      $scope.e.datePeriods.splice(index, 1);
      AdContentAdvertising.queryOccupation($scope);
    };

    // 改变了预算
    $scope.handleBudgetChanged = function () {
      if ($scope.e.advertiseQuotation.billingModelId === 5) {
        // CPT
        AdContentPricing.updateCustomerQuotation($scope.e);
        AdContentPricing.updateDiscount($scope.e);
        // AdContentPricing.updateTotal($scope.e);
      } else if ($scope.e.advertiseQuotation.billingModelId === 4 || $scope.e.advertiseQuotation.billingModelId === 1) {
        // CPM、CPC
        AdContentPricing.updateTotalByBudget($scope.e);
        AdContentPricing.updateDailyAmount($scope.e);
      }
    };

    // 改变日投放量预估
    $scope.handleDailyAmountChanged = function () {
      // 更新总投放量、总价，进而更新预算
      if ($scope.basic.advertiseType !== 'zerotest') {
        AdContentPricing.updateTotalByDailyAmount($scope.e);
        AdContentPricing.updateBudget($scope.e);
      } else {
        AdContentPricing.updateTotalByDailyAmount($scope.e);
      }
    };

    $scope.handleSetDailyChanged = function () {
      if (!$scope.e.setDailyAmount) {
        $scope.e.advertiseQuotation.dailyAmount = null;
        if ($scope.basic.advertiseType==='zerotest') {
          $scope.e.advertiseQuotation.totalAmount = null;
        }
      } else {
        AdContentPricing.updateDailyAmount($scope.e);
      }
    };

  }]);

  app.registerController('CtrlAdContentPricing', [
    '$scope',
    'AdContentPricing',
    'AdContentAdvertising',
  function ($scope, AdContentPricing, AdContentAdvertising) {

    $scope.changePriceType = function(value) {
      $scope.e.advertiseQuotation.billingModelId = '';
      $scope.e.industrySelected = null; //清空行业类型在计费方式切换的时候
      AdContentPricing.clearPrice($scope);
      AdContentPricing.queryQuotionPattern($scope);
      AdContentAdvertising.resetPeriods($scope.e);
    };

    $scope.changeBillingModel = function () {
      $scope.e.industrySelected = null; //清空行业类型在计费方式切换的时候
      AdContentPricing.clearPrice($scope);
      AdContentPricing.queryQuotionPattern($scope);

      if ($scope.e.advertiseQuotation.billingModelId ===4) {
        $scope.e.setDailyAmount = true;
      } else {
        $scope.e.setDailyAmount = false;
      }

      AdContentAdvertising.resetPeriods($scope.e);
    };

    // 改变了客户报价
    $scope.handleCustomerQuotationChanged = function () {
      // 更新折扣
      AdContentPricing.updateDiscount($scope.e);
      // 更新总投放量、总价，进而更新预算
      AdContentPricing.updateBudget($scope.e);
    };

    // 改变了折扣
    $scope.handleDiscountChanged = function () {
      // 更新客户报价
      AdContentPricing.updateCustomerQuotationByDiscount($scope.e);
      // 更新总投放量、总价，进而更新预算
      AdContentPricing.updateBudget($scope.e);
    };

    $scope.setRatio = function (which) {
      var
        ratioParttern = new RegExp(/^([1-9]\d?(\.\d[1-9]0*|\.[1-9]0*)?|0\.(\d{1,2})0*|0|100(\.0+)?)$/),//[0-100]数字，两位小数
        aQ = $scope.e.advertiseQuotation;
      if ( aQ && ratioParttern.test(aQ[which]) ) {
        if ( which === 'ratioMine') {
          aQ.ratioThird = 0;
          aQ.ratioCustomer = Math.round(10000 - aQ.ratioMine*100)/100;
        } else if ( which === 'ratioCustomer') {
          aQ.ratioThird = Math.round(10000 - aQ.ratioMine*100 - aQ.ratioCustomer*100)/100;
          if (aQ.ratioThird < 0) {
            aQ.ratioThird = 0;
          }
        }
      }
    };

  }]);

  app.registerController('CtrlAdContentMaterial', [
    '$scope',
    'AdHttp',
    'AdContentUtil',
    'MaterialEditModal',
    'Material',
  function ($scope, AdHttp, AdContentUtil, MaterialEditModal, Material) {

    /**
     * materialSaveType:
     * 0: create: 新增
     * 1: change: 变更
     * 2: update: 修改
     * 3: recovery: 恢复
     */

    $scope.addMaterial = function () {
      // if ($scope.formAd.$dirty) {
      //   var promise  = $scope.adContentSave();
      //   promise.then(function (result) {
      //     materialEdit(0);
      //   });
      // } else {
        materialEdit(0);
      // }
    };

    $scope.modifyMaterial = function (material) {
      materialEdit(2, {materialApply: angular.copy(material)});
    };

    $scope.toggleMaterialDetail = function (m) {
      m.showDetail = !m.showDetail;
    };

    $scope.removeMaterial = function (index) {
      // Material.remove({
      //   applyId: $scope.e.adSolutionContent.materialApplyList[index].id
      // }).success(function (response) {
      //   if (response.code === 200) {
          $scope.e.adSolutionContent.materialApplyList.splice(index, 1);
          $scope.formAd.$setDirty();
      //   }
      // });
    };

    function materialEdit ( saveType, materialData ) {
      MaterialEditModal.show({
        saveType: saveType,
        adContentInfo: materialAdContentInfo(),
        materialData: materialData || {},
        isContentSave: true
      }).then(function (result) {
        var response = result.response;
        // if ( response.code === 200 ) {
          if (!$scope.e.adSolutionContent.materialApplyList) {
            $scope.e.adSolutionContent.materialApplyList = [];
          }
          var materialApplyList = $scope.e.adSolutionContent.materialApplyList;
          var materialApply = response.data.materialApply;
          var index = findMaterialIndex(materialApply);
          if (index>-1) {
            materialApplyList[index] = materialApply;
          } else {
            materialApplyList.push(materialApply);
          }
        // }
      });
    }

    function findMaterialIndex (materialApply) {
      var index = -1;
      if ($scope.e.adSolutionContent.materialApplyList.length) {
        for (var i = $scope.e.adSolutionContent.materialApplyList.length - 1; i >= 0; i--) {
          if ($scope.e.adSolutionContent.materialApplyList[i].id === materialApply.id) {
            index = i;
            break;
          }
        }
      }
      return index;
    }

    function materialAdContentInfo() {
      var info = {
        adContentId: $scope.e.adSolutionContent.id,
        materialType: $scope.e.position.materialType,
        textlinkLength: $scope.e.position.textlinkLength,
        areaRequired: $scope.e.position.areaRequired,
        size: $scope.e.position.size
      };
      return info;
    }
  }]);

});