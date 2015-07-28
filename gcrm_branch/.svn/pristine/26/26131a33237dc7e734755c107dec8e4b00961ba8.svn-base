define([
  'app',
  '../_common/ytCommon',
  'anuglar-ui-select2'
], function (app) {
  app.registerController('CtrlAd2', [
    '$scope',
    '$stateParams',
    '$filter',
    'PageSet',
    'Select2Suggest',
    'CURRENT_USER_NAME',
    'AdProgram',
    'Customer',
    'Industry',
  function ($scope, $stateParams, $filter, PageSet, Select2Suggest, CURRENT_USER_NAME, AdProgram, Customer, Industry) {
    $scope.solutionId = $stateParams.id;
    $scope.ads = [];
    $scope.basic = $scope.basic || {};
    init();

    //关闭提示栏
    $scope.closeAlert = function( alert ) {
      if ( alert.type === 'info') {
        $scope.alerts.splice(0, 1);
      }
    };

    //保存基础信息
    $scope.basicSave = function() {
      $scope.basic.basicCheck = true;
      //公司名称check
      if (!$scope.basic.customerSelected || !$scope.basic.customerSelected.data) {
        $scope.basic.customerFocus = true;
        return false;
      }
      //执行人check
      $scope.basic.checkOperator = true;
      if (!$scope.basic.operatorSelected || !$scope.basic.operatorSelected.data) {
        $scope.basic.operatorFocus = true;
        return false;
      }
      //合同check
      $scope.basic.checkContract = true;
      var contractNumber;
      if ($scope.basic.hasContract) {
        if (!$scope.basic.contract || !$scope.basic.contract.data) {
          $scope.basic.contractFocus = true;
          return false;
        } else {
          contractNumber = $scope.basic.contract.data.number;
        }
      }

      AdProgram.basicSave({
        hasContract: $scope.basic.hasContract,
        advertiseSolutionView: {
          advertiseSolution: {
            id: $scope.solutionId,
            contractNumber: contractNumber,
            customerNumber: $scope.basic.customerSelected.data,
            operator: $scope.basic.operatorSelected.data.ucid
          }
        }
      }).success(function (response) {
        if (response.code === 200) {
          var adSolutionId = response.data.advertiseSolutionView.advertiseSolution.id;
          $scope.solutionId = adSolutionId;
          if ( !$stateParams.solutionId ) {
            $stateParams.solutionId = adSolutionId;
            //重载成为编辑页
            $state.transitionTo($state.current, $stateParams, {
              reload: true, inherit: false, notify: false
            });
          }
        }
      });
    };

    //继续添加广告
    $scope.addAd = function() {
      $scope.ads.push({});
      $scope.stateEditing = true;
    };


    function init() {
      initBasic();
    }

    //初始化基础信息
    function initBasic() {
      //执行人下拉suggest
      $scope.optionOperator = Select2Suggest.getOperatorOption();
      //公司名称下拉suggest
      $scope.optionCustomer = Select2Suggest.getCustomerOption();
      // //合同名称下拉suggest
      $scope.optionContract = Select2Suggest.getContractOption( $scope );

      //根据是否带solutionId参数判断是否为新增广告方案，作相应处理
      if ($scope.solutionId) {
        PageSet.set({
          siteName: 'adSolutionDetail',
          activeIndex: 1
        });
        AdProgram.getDetail({id: $scope.solutionId}).success(function (response) {
          if (response.code === 200) {
            var basicGetData = response.data;
            $scope.basicReview = true;
            $scope.basicReviewData = {
              customerI18nView: basicGetData.customerI18nView,
              contract: basicGetData.contract,
              hasContract: basicGetData.hasContract || basicGetData.contract,
              operatorName: basicGetData.account.name,
              solutionStatus: basicGetData.adSolution.approvalStatus,
              solutionType: basicGetData.adSolution.type,
              solutionNumber: basicGetData.adSolution.number
            };

            //判断是否是直客，是的话，将状态带至广告主
            if ($scope.basicReviewData.customerI18nView.customer.customerType === 'direct') {
              $scope.stateCustomerStraight = true;
              $scope.customerStraight = {
                data: basicGetData.customerI18nView.customer.customerNumber,
                value: basicGetData.customerI18nView.customer.companyName
              };
            }

            //info处理
            if (basicGetData.adSolution && basicGetData.adSolution.taskInfo) {
              $scope.alerts = [
                {
                  type: 'info',
                  msg: basicGetData.adSolution.taskInfo
                }
              ];
              if (basicGetData.adSolution.approvalStatus === 'refused') {
                $scope.alerts[0].type = 'danger';
              }
            }

            //如果是变更
            //1.隐藏修改基本信息按钮
            //2.显示变更的按钮
            //3.隐藏删除
            //4.隐藏继续添加内容按钮
            if ( basicGetData.adSolution.type === 'update' ) {
              $scope.adSolutionTypeUpdate = true;
            }
            //如果有旧的合同编号，则显示
            if ( basicGetData.oldSolutionNumber ) {
              $scope.oldSolutionNumber = basicGetData.oldSolutionNumber;
            }

            $scope.newDetailContents = angular.copy( basicGetData.approvalContentViews );

            initContent();
          }
        });
      } else {
        PageSet.set({
          siteName: 'adSolutionAdd',
          activeIndex: 1
        });
        $scope.basic.hasContract = false;
        $scope.basic.adProjectStatus = 1;
        $scope.basic.operatorSelected = {
          data: {
            ucid: CURRENT_USER_NAME.ucid,
            name: CURRENT_USER_NAME.realname
          }
        };//set default operator to current user
        $scope.solutionStatus = $filter('translate')('AD_BASIC_STATUS_WAITED');
        $scope.solutionType = $filter('translate')('AD_BASIC_TYPE_NEW');
        $scope.solutionStatus = 'saving';
        $scope.solutionType = 'create';

        if ($stateParams.customerNumber) {
          Customer.get({
            customerNumber: $stateParams.customerNumber,
            companyName: ''
          }).success(function (response) {
            $scope.basic.customerInfo = response.data;
            $scope.basic.customerSelected = {
              data: response.data.customer.customerNumber,
              value: response.data.customer.companyName
            };
            if (response.data.customer.customerType === 'nondirect') {
              $scope.basic.customerSelected = {
                data: response.data.customer.customerNumber,
                value: response.data.agentCompany.companyName
              };
            }
          });
        }
      }
    }

    function tempSaveData() {
      var tempData = {};
      tempData.hasContract = $scope.basic.hasContract;
      if ($scope.basic.hasContract) {
        tempData.advertiseSolutionView = {
          advertiseSolution: {
            contractNumber: $scope.basic.contract.data.number
          }
        };
      } else {
        tempData.advertiseSolutionView = {
          advertiseSolution: {}
        };
      }

      if ($scope.basic.operatorSelected && $scope.basic.operatorSelected.data) {
        tempData.advertiseSolutionView.advertiseSolution.operator = $scope.basic.operatorSelected.data.ucid;
      }
      tempData.advertiseSolutionView.advertiseSolution.customerNumber = $scope.basic.customerSelected.data;
      tempData.advertiseSolutionView.advertiseSolution.id = $scope.sulotionId;

      return tempData;
    }

    //初始化广告内容
    function initContent() {
      initIndustryTypes();
      //如果还没有广告内容，添加之
      if ( !$scope.newDetailContents.length ) {
        if ( $rootScope.BtnIndex.btn_adsol_detail_cont_save ) {
          $scope.addAd();
          $scope.anchorTo(0); //scroll到新加的广告内容
        }
      } else {
        $scope.ads = resolveAdData();
      }
    }

    function resolveAdData() {
      var
        arr = [],
        adData = $scope.newDetailContents;
      for (var i = 0; i < adData.length; i++) {
        var item = { review: true };
        item.reviewData = adData[i];
        item.reviewData.materialType = ~~findName(adData[i].positionVOList, adData[i].adSolutionContent.positionId, 'materialType');
        item.reviewData.positionByAreaSelection = adData[i].position;
        item.canUpdate = adData[i].canUpdate;
        arr.push(item);
      }
      return arr;
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

    function initIndustryTypes() {
      Industry.getIndustryTypes().success( function ( response ) {
        if ( response.code === 200 ) {
          var industryTypes = {};
          var length = response.data.length;
          for (var i = 0; i < length; i++) {
            var mainTypeName = response.data[i].industryTypeName;
            industryTypes[mainTypeName] = [];
            if ('subIndustryType' in response.data[i]) {
              var subLength = response.data[i].subIndustryType.length;
              for (var j = 0; j < subLength; j++) {
                industryTypes[mainTypeName].push(response.data[i].subIndustryType[j]);
              }
            } else {
              industryTypes[mainTypeName].push(response.data[i]);
            }
          }
          $scope.industry = {
            industryTypes: industryTypes
          };
        } else {
          $log.error( 'fatal error' );
        }
      });
    }

  }]);
});