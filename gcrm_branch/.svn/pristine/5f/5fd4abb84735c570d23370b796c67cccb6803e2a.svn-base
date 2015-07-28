/**
 * [CtrlAd description]
 * this is controller for feature 'ad'
 */
define(['app',
        '../_common/ytCommon',
        '../_services/PageSet',
        '../_services/Select2Suggest',
        '../_services/http/Customer',
        '../_services/http/Contract',
        '../_services/http/AdProgram',
        '../_services/http/Account',
        '../_directives/ytAjaxupload',
        '../_directives/ytFocusme',
        '../_directives/ytCalendar',
        '../_directives/periodLabel',
        '../_filters/AdMaterialIfEmbedCodeFilter',
        '../gcrm-util',
        'anuglar-ui-select2',
        './CtrlAdContent'
        ],
function(app) {
  app.registerController('CtrlAd', [
    '$scope',
    '$q',
    '$log',
    '$rootScope',
    '$stateParams',
    '$timeout',
    '$location',
    '$anchorScroll',
    'PageSet',
    'Modal',
    'Customer',
    'Contract',
    'AdProgram',
    'Account',
    'Utils',
    '$http',
    'Select2Suggest',
    '$filter',
    'GCRMUtil',
    '$state',
    'CURRENT_USER_NAME',
    'Ad',
    'Industry',
    function ( $scope, $q, $log, $rootScope, $stateParams, $timeout, $location, $anchorScroll, PageSet, Modal, Customer, Contract, AdProgram, Account, Utils, $http, Select2Suggest, $filter, GCRMUtil, $state, CURRENT_USER_NAME, Ad, Industry ) {

    $scope.stateEditing = true; //控制修改按钮的显示
    $scope.ads = [];
    $scope.basic = $scope.basic || {};
    // controller code here
    if ( $stateParams.programId ) {
      PageSet.set({
        siteName: 'adSolutionDetail',
        activeIndex: 1
      });
      // load program,including basic info and ads
      $scope.programId = $stateParams.programId;
      $scope.stateEditing = false;
      AdProgram.getDetail({
        id: $stateParams.programId
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          /**
           * 处理基础信息
           */
          var basicGetData = response.data;
          $scope.basicReview = true;
          $scope.basicReviewData = {
            customerI18nView: basicGetData.customerI18nView,
            contract: basicGetData.contract,
            hasContract: basicGetData.hasContract || basicGetData.contract,
            // operatorName: basicGetData.operatorName,
            operatorName: basicGetData.account.name,
            programStatus: basicGetData.adSolution.approvalStatus,
            programType: basicGetData.adSolution.type
          };

          //判断是否是直客，是的话，将状态带至广告主
          if ( $scope.basicReviewData.customerI18nView.customer.customerType === 'direct' ) {
            $scope.stateCustomerStraight = true;
            $scope.customerStraight = {
              data: basicGetData.customerI18nView.customer.customerNumber,
              value: basicGetData.customerI18nView.customer.companyName
            };
          }
          /**
           * 处理基础信息..
           */
          var getDetailData = response.data;
          if ( getDetailData.adSolution && getDetailData.adSolution.taskInfo ) {
            $scope.alerts = [
              {
                type: 'info',
                msg: getDetailData.adSolution.taskInfo
              }
            ];
            if ( getDetailData.adSolution.approvalStatus === 'refused' ) {
              $scope.alerts[0].type = 'danger';
            }
          }
          // 方案状态为待提交时，判断：
          // 1）所有内容状态为{待提交，审核驳回}：查看详情跳转到ad页；
          // 2）其他跳转到adSolutionDetail页；
          // 方案状态为审核驳回时，判断：
          // 1）所有内容状态都为审核驳回，跳转到ad页；
          // 2）其他跳转到adSolutionDetail页；
          var status = getDetailData.adSolution.approvalStatus,
              i,
              contents = getDetailData.approvalContentViews;
          // 如果存在作废的广告内容，则跳转adSolutionDetail
          if ( contents.cancelRecord && contents.cancelRecord.length ) {
            $state.go('adSolutionDetail', {id: $stateParams.programId } );
          }
          if ( status === 'saving' ) {
            for (i = 0; i < contents.length; i++) {
              if ( contents[i].adSolutionContent.approvalStatus !== 'saving' &&
                  contents[i].adSolutionContent.approvalStatus !== 'refused' ) {
                $state.go('adSolutionDetail', {id: $stateParams.programId } );
              }
            }
          } else if ( status === 'refused' ) {
            for (i = 0; i < contents.length; i++) {
              if ( contents[i].adSolutionContent.approvalStatus !== 'refused' ) {
                $state.go('adSolutionDetail', {id: $stateParams.programId } );
              }
            }
          } else {
            $state.go('adSolutionDetail', {id: $stateParams.programId } );
          }

          //如果是变更，1.隐藏修改基本信息按钮
          //2.显示变更的按钮
          //3.隐藏删除
          //4.隐藏继续添加内容按钮
          if ( getDetailData.adSolution.type === 'update' ) {
            // $scope.hideEditBasic = true;
            // $scope.ifCancelChangeAdBtn = true;
            $scope.adSolutionTypeUpdate = true;
          }
          //如果有旧的合同编号，则显示
          if ( getDetailData.oldSolutionNumber ) {
            $scope.oldSolutionNumber = getDetailData.oldSolutionNumber;
          }

          $scope.newDetailContents = angular.copy( getDetailData.approvalContentViews );


          /**
           * 处理广告内容
           */
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
          //如果还没有广告内容，添加之
          if ( !getDetailData.approvalContentViews.length ) {
            if ( $rootScope.BtnIndex.btn_adsol_detail_cont_save ) {
              $scope.addAd();
              $scope.anchorTo(0); //scroll到新加的广告内容
            }
          } else {
            $scope.ads = resolveAdData( getDetailData.approvalContentViews );
            //动态获取行业类型
          }
        }
      });
      // var promiseBasicGet = AdProgram.basicGet({
      //   id: $scope.programId
      // });
      // var promiseGetDetail = AdProgram.getDetail({
      //   id: $stateParams.programId
      // });

      // $q.all( [ promiseBasicGet, promiseGetDetail] ).then(function (result) {
      //   if ( result[0].data.code === 200 ) {}

      //   if ( result[1].data.code === 200 ) {}
      // });
    } else {
      //set the breadcrumb
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
      $scope.programStatus = $filter('translate')('AD_BASIC_STATUS_WAITED');
      $scope.programType = $filter('translate')('AD_BASIC_TYPE_NEW');
      $scope.programStatus = 'saving';
      $scope.programType = 'create';

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
    //关闭提示栏
    $scope.closeAlert = function( alert ) {
      if ( alert.type === 'info') {
        $scope.alerts.splice(0, 1);
      }
    };
    //继续添加广告
    $scope.addAd = function() {
      $scope.ads.push({});
      $scope.stateEditing = true;
    };
    //删除一个广告内容
    $scope.adContentRemove = function(index, ad) {
      Modal.confirm({
        content: $filter('translate')('AD_BASIC_CONFIRM_REMOVE_CONTENT')
      },
      function() {
        var contentId = ad.solutionContentId || ad.reviewData.solutionContent.id;
        if (contentId) {
          AdProgram.remove({
            id: contentId
          },
          function(response) {
            if (response.code === 200) {
              $scope.ads.splice(index, 1);
            }
          });
        }
      });
    };
    //取消一个广告内容
    $scope.adContentCancel = function(index) {
      Modal.confirm({
        content: $filter('translate')('AD_BASIC_CONFIRM_CANCEL_CONTENT')
      }, function() {
        $scope.ads.splice(index, 1);
      });
      $scope.stateEditing = false;
    };
    //提交整个方案
    $scope.adProgramSubmit = function() {
      if (!$scope.programId) {
        return false;
      }
      $scope.disableToApproval = true;
      AdProgram.submit({
        id: $scope.programId
      }, function( response ) {
        $scope.disableToApproval = false;
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('AD_BASIC_ALERT_SUBMIT_SUCCESS')
          }, function() {
            //success and nav to 详情页
            $state.go('adSolutionDetail', { id: response.data.id});
          });
        } else if ( response.code === 204 ) {
          var getAdIndex = function ( adId ) {
            for (var i = 0; i < $scope.ads.length; i++) {
              if ( $scope.s[i].reviewData && $scope.s[i].reviewData.solutionContent.id === ~~adId ) {
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
    };
    $scope.anchorTo = function(index) {
      var toWhere;
      if ( index === -1 ) {
        toWhere = 'anchorBasic';
      } else {
        toWhere = 'anchorAdContent' + index;
      }
      $timeout(function () {
        // set the location.hash to the id of
        // the element you wish to scroll to.
        $location.hash(toWhere);

        // call $anchorScroll()
        $anchorScroll();
      });
    };

    //监听公司名称的改变，一旦有改变，重置合同的状态
    $scope.$watch('basic.customerSelected', function(newValue, oldValue) {
      if (newValue) {
        $scope.basic.contract = null;
        $scope.basic.hasContract = false;
        $scope.basic.checkContract = false;
        Customer.get({
          customerNumber: newValue.data,
          companyName: ''
        }).success(function (response) {
          $scope.basic.customerInfo = response.data;
          $scope.basic.hasContract = response.data.hasContract;
        });
      }
    });
    //监听是否有合同
    $scope.$watch('basic.hasContract', function(newValue, oldValue) {
      //为‘否’时，清空合同
      if (!newValue) {
        $scope.basic.contract = null;
      }

      //从‘否’切换为‘是’时，还原校验失败信息
      if (!oldValue) {
        $scope.basic.checkContract = false;
      }
    });

    //点击修改
    $scope.basicEdit = function() {
      if (!$scope.programId) {
        return false;
      }
      $scope.stateEditing = true;
      AdProgram.basicGetEdit({
        id: $scope.programId
      }).success(function (response) {
        if (response.code === 200) {
          $scope.basic = {
            operatorSelected: {
              data: {
                ucid: response.data.operatorId,
                name: response.data.operatorName
              }
            },
            customerSelected: {
              data: response.data.customerI18nView.customer.customerNumber,
              value: response.data.customerI18nView.customer.companyName
            },
            customerInfo: {
              customer: response.data.customerI18nView.customer
            }
          };
          $timeout(function() {
            $scope.basic.contract = {
              data: response.data.contract
            };
            $scope.basic.hasContract = response.data.hasContract;
          },0);

          $scope.basicReview = false;
          $scope.showCancelBtn = true;
          $scope.programStatus = response.data.advertiseSolutionView.advertiseSolution.approvalStatus;
          $scope.programType = response.data.advertiseSolutionView.advertiseSolution.type;
        }
      });
    };
    //点击取消
    $scope.basicCancel = function() {
      $scope.basicReview = true;
      $scope.stateEditing = false;
    };
    //点击暂存
    $scope.basicTempSave = function() {
      $scope.basic.basicCheck = true;
      //公司名称check
      if (!$scope.basic.customerSelected || !$scope.basic.customerSelected.data) {
        $scope.basic.customerFocus = true;
        return false;
      }

      //合同编号need check
      $scope.basic.checkContract = true;

      //post data 拼接
      var tempSaveData = {};
      // tempSaveData.hasContract = false;
      tempSaveData.hasContract = $scope.basic.hasContract;
      if ($scope.basic.hasContract) {
        tempSaveData.advertiseSolutionView = {
          advertiseSolution: {
            contractNumber: $scope.basic.contract.data.number
          }
        };
      } else {
        tempSaveData.advertiseSolutionView = {
          advertiseSolution: {}
        };
      }

      if ($scope.basic.operatorSelected && $scope.basic.operatorSelected.data) {
        tempSaveData.advertiseSolutionView.advertiseSolution.operator = $scope.basic.operatorSelected.data.ucid;
      }
      tempSaveData.advertiseSolutionView.advertiseSolution.customerNumber = $scope.basic.customerSelected.data;
      tempSaveData.advertiseSolutionView.advertiseSolution.id = $scope.programId;
      AdProgram.basicTempSave(tempSaveData).success(function (response) {
        if (response.code === 200) {
          //取消按钮控制
          if (!$scope.programId) {
            $scope.showCancelBtn = false;
          }
          //暂存提示控制
          $scope.programId = response.data.advertiseSolutionView.advertiseSolution.id;
          //暂存提示控制
          $scope.basic.tempSavePop = {
            'content': '暂存成功',
            'show': false
          };
          $scope.basic.tempSavePop.show = true;
          $timeout(function() {
            $scope.basic.tempSavePop.show = false;
          },
          3000);

        }
      });
    };
    //点击保存
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
            id: $scope.programId,
            contractNumber: contractNumber,
            customerNumber: $scope.basic.customerSelected.data,
            operator: $scope.basic.operatorSelected.data.ucid
          }
        }
      }).success(function (response) {
        if (response.code === 200) {
          var adId = response.data.advertiseSolutionView.advertiseSolution.id;
          $scope.programId = adId;
          if ( !$stateParams.programId ) {
            $stateParams.programId = adId;
            //重载成为编辑页
            $state.transitionTo($state.current, $stateParams, {
              reload: true, inherit: false, notify: false
            });
          }
          AdProgram.basicGet({
            id: adId
          }).success( function (response) {
            if (response.code === 200) {
              $scope.basicReview = true;
              $scope.basicReviewData = {
                customerI18nView: response.data.customerI18nView,
                contract: response.data.contract,
                hasContract: response.data.hasContract,
                operatorName: response.data.operatorName,
                programStatus: response.data.advertiseSolutionView.advertiseSolution.approvalStatus,
                programType: response.data.advertiseSolutionView.advertiseSolution.type
              };
              //如果是非直客，显示代理商的信息
              if (response.data.customerI18nView.customer) {
                if (response.data.customerI18nView.customer.customerType === 'nondirect') {
                  $scope.basicReviewData.customer = response.data.customerI18nView.agentCompany;
                } else if (response.data.customerI18nView.customer.customerType === 'direct') {
                  //判断是否是直客，是的话，将状态带至广告主
                  $scope.stateCustomerStraight = true;
                  $scope.customerStraight = {
                    data: response.data.customerI18nView.customer.customerNumber,
                    value: response.data.customerI18nView.customer.companyName
                  };
                  //如果已存在廣告内容（修改的時候）
                  //重置广告主的值
                  // if ( $scope.ads.length ) {
                  //   for (var i = $scope.ads.length - 1; i >= 0; i--) {
                  //     $scope.ads[i].CustomersName = {
                  //       data: response.data.customerI18nView.customer.customerNumber,
                  //       value: response.data.customerI18nView.customer.companyName
                  //     }; //editing
                  //     $scope.ads[i].reviewData.adSolutionContent.advertiser = response.data.customerI18nView.customer.companyName; //reviewing
                  //   }
                  // }
                }
                if ( $scope.ads.length ) {
                  //如果是直客，将广告方案下的所有内容的广告主都设置为该直客
                  if ( response.data.customerI18nView.customer.customerType === 'direct' ) {
                    $scope.stateCustomerStraight = true;
                    for (var i = $scope.ads.length - 1; i >= 0; i--) {
                      $scope.ads[i].reviewData.adSolutionContent.advertiser = response.data.customerI18nView.customer.companyName;
                    }
                  } else {
                    $scope.stateCustomerStraight = false;
                  }
                }
              }

              //如果还没有广告内容，添加之
              if (!$scope.ads.length) {
                $scope.ads.push({});
                $scope.anchorTo(0); //scroll到新加的广告内容
              } else {
                $scope.stateEditing = false; //重置编辑的状态
              }
            }
          });
        }
      });
    };
    //取消方案变更
    $scope.cancelChangeAd = function () {
      if ( !$stateParams.programId ) {
        return false;
      }
      Ad.cancelChangeAdSolution( { id: $stateParams.programId } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $state.go( 'adSolutionDetail', {id: response.data } );
        }
      });
    };
    //执行人下拉suggest
    $scope.optionOperator = Select2Suggest.getOperatorOption();
    //公司名称下拉suggest
    $scope.optionCustomer = Select2Suggest.getCustomerOption();
    // //合同名称下拉suggest
    $scope.optionContract = Select2Suggest.getContractOption( $scope );
    //resolve ad data to fit
    function resolveAdData( adResponseData ) {
      var arr = [];
      for (var i = 0; i < adResponseData.length; i++) {
        var item = { review: true };
        item.reviewData = adResponseData[i];
        item.reviewData.materialType = ~~findName(adResponseData[i].positionVOList, adResponseData[i].adSolutionContent.positionId, 'materialType');
        // item.reviewData.positionByAreaSelection = findName( adResponseData[i].positionVOList, adResponseData[i].adSolutionContent.positionId );
        item.reviewData.positionByAreaSelection = adResponseData[i].position;
        item.canUpdate = adResponseData[i].canUpdate;
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
  }]);
});