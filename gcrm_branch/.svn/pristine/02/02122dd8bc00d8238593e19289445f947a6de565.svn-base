define(['app'], function (app) {
  /**
   * AD基础信息处理
   * @param  {[type]} $filter            [description]
   * @param  {[type]} $stateParams       [description]
   * @param  {[type]} Ad          [description]
   * @param  {[type]} CURRENT_USER_NAME) {                                           return {        init: function (scope) {} [description]
   * @param  {[type]} initView:          function      (scope, adData [description]
   * @return {[type]}                    [description]
   */
  app.registerService('AdBasic', [
    '$filter',
    '$state',
    '$stateParams',
    '$timeout',
    'AdHttp',
    'CURRENT_USER_NAME',
    'AdConstant',
    'Customer',
    'AdContent',
    'AdContentPricing',
  function ($filter, $state, $stateParams, $timeout, AdHttp, CURRENT_USER_NAME, AdConstant, Customer, AdContent, AdContentPricing) {
    return {
      init: function (scope) {},
      /**
       * 初始化为预览视图
       * @param  {[type]} scope  [description]
       * @param  {[type]} adData [description]
       * @return {[type]}        [description]
       */
      initView: function (scope, adData) {
        scope.state.basicReview = true;
        if (adData.account) {
          scope.basic.operatorSelected = {
            data: adData.account
          };
        }
        scope.basic.customerSelected = {
          data: adData.customerI18nView.customer.customerNumber,
          value: adData.customerI18nView.customer.companyName
        };
        scope.basic.customerI18nView = adData.customerI18nView;
        scope.basic.contract = {
          data: adData.contract
        };

        //且当签约主体的客户类型是广告代理（线下）时，显示“协议下单笔合同”
        if ( scope.basic.customerI18nView.customer.customerType === 'offline' ) {
          scope.contractTypes = [{
            name: AdConstant.singleContract,
            type: 'single'
          }, {
            name: AdConstant.protocol,
            type: 'protocol'
          }];
        } else {
          scope.contractTypes = [{
            name: AdConstant.singleContract,
            type: 'single'
          }, {
            name: AdConstant.frame,
            type: 'frame'
          }];
        }

        scope.basic.hasContract = adData.hasContract || !!adData.contract;
        scope.basic.adSolution = adData.adSolution;

        scope.basic.solutionId = adData.adSolution.id;
        scope.basic.solutionNumber = adData.adSolution.number;
        scope.basic.solutionStatus = scope.basic.adSolution.approvalStatus; //创建时为‘saving’
        scope.basic.solutionType = scope.basic.adSolution.type; //创建时为‘create’
        scope.basic.canWithdraw = adData.canWithdraw;
        scope.basic.canCreatePO = adData.canCreatePO;
        scope.basic.oldSolutionNumber = adData.oldSolutionNumber;
        scope.basic.oldSolutionContractType = adData.oldSolutionContractType;
        scope.basic.canUpdate = adData.canUpdate;
        scope.basic.isOwner = adData.isOwner;
        scope.basic.canDelete = adData.canDelete;
        scope.basic.advertiseType = adData.adSolution.advertiseType;

        //info处理
        if (adData.adSolution && adData.adSolution.taskInfo) {
          scope.alerts = [{
            type: 'info',
            msg: adData.adSolution.taskInfo
          }];
          if (adData.adSolution.approvalStatus === 'refused') {
            scope.alerts[0].type = 'danger';
          }
        }
      },
      /**
       * 初始化为添加视图
       * @param  {[type]} scope [description]
       * @return {[type]}       [description]
       */
      initAdd: function (scope) {
        scope.basic.hasContract = false;
        scope.basic.adProjectStatus = 1;
        //set default operator to current user
        scope.basic.operatorSelected = {
          data: {
            ucid: CURRENT_USER_NAME.ucid,
            name: CURRENT_USER_NAME.realname
          }
        };
        scope.basic.solutionStatus = 'saving';
        scope.basic.solutionType = 'create';
        scope.basic.isOwner = true;

        if ($stateParams.customerNumber) {
          Customer.get({
            customerNumber: $stateParams.customerNumber,
            companyName: ''
          }).success(function(response) {
            scope.basic.customerI18nView = response.data;
            scope.basic.customerSelected = {
              data: response.data.customer.customerNumber,
              value: response.data.customer.companyName
            };
            scope.basic.hasContract = response.data.hasContract;

            //非直客
            if (response.data.customer.customerType === 'nondirect') {
              scope.basic.customerSelected = {
                data: response.data.customer.customerNumber,
                value: response.data.agentCompany.companyName
              };
            }

          });
        }
      },
      edit: function (scope) {
        if (!scope.solutionId) {
          return false;
        }

        scope.state.basicReview = false;
        scope.state.isGlobleEditing = true;
        scope.state.showBasicCancel = true;
      },
      resetBasic: function (scope) {
        AdHttp.basicGetEdit({
          id: scope.solutionId
        }).success(function (response) {
          if (response.code === 200) {
            scope.basic.operatorSelected = {
              data: {
                ucid: response.data.operatorId,
                name: response.data.operatorName
              }
            };
            scope.basic.customerSelected = {
              data: response.data.customerI18nView.customer.customerNumber,
              value: response.data.customerI18nView.customer.companyName
            };
            scope.basic.customerI18nView = response.data.customerI18nView;

            $timeout(function() {
              scope.basic.contract = {
                data: response.data.contract
              };
              scope.basic.hasContract = response.data.hasContract;

              //恢复状态
              scope.state.basicReview = true;
              scope.state.isGlobleEditing = false;
            }, 0);

          }
        });
      },
      save: function (scope) {
        var AdBasic = this;
        var basic = scope.basic;

        AdHttp.basicSave({
          hasContract: scope.basic.hasContract,
          advertiseSolutionView: {
            advertiseSolution: {
              id: basic.solutionId,
              contractNumber: basic.contract && basic.contract.data && basic.contract.data.number,
              customerNumber: basic.customerSelected.data,
              operator: basic.operatorSelected.data.ucid,
              advertiseType: basic.advertiseType
            }
          }
        }).success(function (response) {
          if (response.code === 200) {
            var adSolutionId = response.data.advertiseSolutionView.advertiseSolution.id;
            scope.solutionId = adSolutionId;
            if ( !$stateParams.id ) {
              $stateParams.id = adSolutionId;
              //重载成为编辑页
              $state.transitionTo($state.current, $stateParams, {
                reload: true, inherit: false, notify: false
              });
            } else {
              AdBasic.resetBasic(scope);
              AdBasic.updateAdContents(scope);
            }
          }
        });
      },
      /**
       * 更新直客广告主至各广告内容
       * @param  {[type]} scope [description]
       * @return {[type]}       [description]
       */
      updateAdContents: function (scope) {
        //直客广告主
        scope.basic.customerDirect = (function () {
          var ret = false;
          if (scope.basic.customerI18nView.customer.customerType === 'direct') {
            ret = {
              data: scope.basic.customerI18nView.customer.customerNumber,
              value: scope.basic.customerI18nView.customer.companyName
            };
          }
          return ret;
        }());

        if (scope.adContents.length) {
          for (var i = scope.adContents.length - 1; i >= 0; i--) {
            var ad = scope.adContents[i];
            AdContent.updateDirect(ad, scope.basic.customerDirect);
            if (scope.basic.advertiseType==='zerotest') {
              AdContentPricing.updateZero(ad.innerScope.e.advertiseQuotation);
            } else if (!ad.innerScope.e.advertiseQuotation.publishPrice && !ad.innerScope.e.advertiseQuotation.discount) {
              ad.innerScope.e.advertiseQuotation.discount = 1;
            }
          }
        }
      },
      /**
       * 更新直客广告主至各广告内容
       * @param  {[type]} scope [description]
       * @return {[type]}       [description]
       */
      updateDirect: function (scope) {
        //直客广告主
        scope.basic.customerDirect = (function () {
          var ret = false;
          if (scope.basic.customerI18nView.customer.customerType === 'direct') {
            ret = {
              data: scope.basic.customerI18nView.customer.customerNumber,
              value: scope.basic.customerI18nView.customer.companyName
            };
          }
          return ret;
        }());

        if (scope.basic.customerDirect && scope.adContents.length) {
          for (var i = scope.adContents.length - 1; i >= 0; i--) {
            var ad = scope.adContents[i];
            ad.advertiser = angular.copy(scope.basic.customerDirect);
            ad.adSolutionContent.advertiser = scope.basic.customerI18nView.customer.companyName;

            ad.innerScope.e.advertiser = angular.copy(scope.basic.customerDirect);
          }
        }
      }
    };
  }]);
});