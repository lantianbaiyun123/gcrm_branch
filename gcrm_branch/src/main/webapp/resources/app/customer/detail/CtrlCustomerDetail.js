define([
  'app',
  '../../_common/ytCommon',
  '../../_filters/CustomerFilter',
  '../../_directives/ytInputDropdown',
  '../../_directives/ytInputCheckboxes',
  '../../_directives/ytInputRadio',
  '../../_directives/ytJqueryFileUpload',
  '../../_directives/ytAjaxupload',
  '../../record/CustomerApprovalRecord',
  '../../record/CustomerModifyRecord',
  '../../_directives/checklistModel',
  '../../_directives/ytPopoverConfirm'
], function ( app ) {
  /**
   * CtrlCustomerDetail 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerDetail', [
  '$scope',
  '$state',
  '$filter',
  '$stateParams',
  '$timeout',
  'Modal',
  'Customer',
  '$window',
  'CustomerApprovalRecord',
  'CustomerModifyRecord',
  'PageSet',
  function ( $scope, $state,$filter, $stateParams, $timeout, Modal, Customer, $window, CustomerApprovalRecord, CustomerModifyRecord, PageSet ) {
    PageSet.set({siteName:'customerDetail', activeIndex: 0});
    var translate = $filter('translate');
    if ( !$stateParams.customerId ) {
      $state.go( 'error.four' );
    } else {
      //state: 1.addEditing 2.detailEditing 3. detailViewing
      $scope.basic = { state: 'detailViewing' };
      $scope.contact = { state: 'detailViewing' };
      $scope.opportunity = { state: 'detailViewing' };
      $scope.qualification = { state: 'detailViewing' };
      $scope.attachment = { state: 'detailViewing' };
      Customer.getDetail( { id: $stateParams.customerId } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.isOwner = response.data.isOwner;
          $scope.customerApplyInfo = response.data.customerApplyInfo;
          $scope.customerApplyInfo.companyStatus = response.data.customer.companyStatus;
          $scope.typeChangeNotAllowed = !response.data.typeChangeAllowed;
          // $scope.customerApplyInfo.approveState = response.data.approvalStatus;
          //basic
          $scope.basic = angular.extend( $scope.basic, response.data.customer );
          $scope.basic.industry = response.data.industry;
          if ( response.data.agentCompany ) {
            $scope.basic.agentCompany =  {
              id: response.data.agentCompany.id,
              name: response.data.agentCompany.companyName,
              companyStatus: response.data.agentCompany.companyStatus
            };
          }
          $scope.basic.country = response.data.country;
          $scope.basic.currencyType = response.data.currencyType;
          if ( response.data.belongSales ) {
            $scope.basic.belongSales = {
              id: response.data.belongSales.ucid,
              name: response.data.belongSales.realname
            };
          }
          if ( response.data.belongManager ) {
            $scope.basic.belongManager = {
              name: response.data.belongManager.realname,
              id: response.data.belongManager.ucid
            };
          }
          $scope.basic.agentType = response.data.agentType;
          if ( response.data.agentRegional ) {
            $scope.basic.agentRegionalShow = {
              i18nName: response.data.agentRegional.i18nName
            };
            $scope.basic.agentRegional = response.data.agentRegional && response.data.agentRegional.id;
            $scope.basic.agentCountries = response.data.agentRegional.agentCountries;
          }
          $scope.basic.agentCountrySelected = response.data.agentCountry;
          $scope.basic.businessTypesSelected = response.data.customer.businessType.split( ',' );
          $scope.basic.maxDate = new Date();


          // $scope.contact.contacts = response.data.contacts;
          //如果无联系人，添加一条空联系人以供填写
          if (response.data.contacts && response.data.contacts.length) {
            $scope.contact.contacts = response.data.contacts;
          } else {
            $scope.contact.contacts = [{
              isEditing: true,
              customerNumber: $scope.basic.id
            }];
          }

          if ( response.data.opportunityView ) {
            $scope.opportunity = angular.extend( $scope.opportunity, response.data.opportunityView );
            $scope.opportunity.currencyTypes = response.data.currencyTypes;
            if ( response.data.opportunityView.billingModel ) {
              $scope.opportunity.billingModelsSelected = response.data.opportunityView.billingModel.split(',');
            }
            if ( response.data.opportunityView.businessType ) {
              $scope.opportunity.businessTypesSelected = response.data.opportunityView.businessType.split(',');
            }
            $scope.opportunity.platformsSelected = response.data.opportunityView.advertisingPlatforms;
          }
          if ( response.data.qualification ) {
            $scope.qualification = angular.extend( $scope.qualification, response.data.qualification );
          }
          $scope.attachment.attachments = response.data.attachments;
          Customer.getEditInfo().success( function ( response ) {
            if ( response.code === 200 ) {
              dataUtil.setEditData( response.data );
              if ( $scope.customerApplyInfo.approveState === 'approved' ) {
                if ( $scope.basic.customerType === 'offline' ) {
                  $scope.basic.customerTypes = ['offline'];
                } else {
                  $scope.basic.customerTypes.splice( $scope.basic.customerTypes.indexOf('offline'), 1);
                }
              }
            }
          });
        }
      });

    }
    var dataUtil = {
      setEditData: function ( editData ) {
        // basic
        $scope.basic.customerTypes = editData.customerTypes;
        $scope.basic.industryTypes = editData.industrys;
        $scope.basic.companySizeTypes = editData.companySizes;
        $scope.basic.currencyTypes = editData.currencyTypes;
        $scope.basic.businessTypes = editData.businessType;
        $scope.basic.agentTypes = editData.agentTypes;
        $scope.basic.agentRegionals = editData.agentRegionals;
        // contacts
        //opportunity
        $scope.opportunity.currencyTypes = editData.currencyTypes;
        $scope.opportunity.businessTypes = editData.businessType;
        //qualification
        //attachment
        $scope.attachment.attachmentTypes = editData.attachmentTypes;
      },
      //拼接参数
      getAllPostParams: function () {
        var result = {};
        var customer = Customer.resolveBasicData( $scope.basic );
        var contacts = Customer.resolveContactData( $scope.contact.contacts );
        var attachment = angular.copy( $scope.attachment.attachments );
        result = {
          customer: customer,
          contacts: contacts,
          attachments: attachment
        };
        if ( $scope.basic.customerType === 'offline' ) {
          result.qualification = angular.copy( $scope.qualification );
        } else {
          result.opportunity = Customer.resolveOpportunityData( $scope.opportunity );
        }
        return result;
      }
    };
    //基本信息保存
    $scope.$on( 'basicSave', function () {
      Customer.saveBasic( Customer.resolveBasicData( $scope.basic ) ).success( function ( response ) {
        if ( response.code === 200 ) {
          Modal.success( { content: translate('SUCCESS_SAVE') }, function () {
            $window.location.reload();
            // $scope.basic.state = 'detailViewing';
          });
        }
      });
    });
    //业务机会保存
    $scope.$on( 'opportunitySave', function () {
      var opporData = Customer.resolveOpportunityData( $scope.opportunity );
      opporData.customerNumber = $scope.customerApplyInfo.customerId;
      Customer.updateCustomerOpportunity( opporData ).success( function ( response) {
        if ( response.code === 200 ) {
          Modal.success( { content: translate('SUCCESS_SAVE') }, function () {
            // $window.location.reload();
            $scope.opportunity.state = 'detailViewing';
          });
        }
      });
    });
    //代理商资质保存
    $scope.$on( 'qualificationSave', function () {
      $scope.qualification.customerNumber = $scope.customerApplyInfo.customerId;
      Customer.updateCustomerQualification( $scope.qualification ).success( function ( response ) {
        if ( response.code === 200 ) {
          Modal.success( { content: translate('SUCCESS_SAVE') }, function () {
            // $window.location.reload();
            $scope.qualification.state = 'detailViewing';
          });
        }
      });
    });
    $scope.saveToApproval = function () {
      $scope.disableToApproval = true;
      //没有必要再校验
      Customer.saveToApprove( dataUtil.getAllPostParams() ).success( function ( response ) {
        $scope.disableToApproval = false;
        if ( response.code === 200 ) {
          $scope.basic.isValidating = false;
          Modal.success( { content: translate('SUCCESS_SUBMIT_TO_APPROAVAL') }, function () {
            if ( $stateParams.customerId ) {
              $window.location.reload();
            } else {
              $state.go( 'customer.detail.facade', {
                customerId:  response.data.customer.id
              });
            }
          });
        }
      });
    };
    //催办
    $scope.sendReminder = function (customerId) {
      Customer.sendReminder({
        customerId: customerId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_REMIDER'),
            timeOut: 2000
          });
        }
      });
    };
    //撤销
    $scope.withdraw = function (customerId) {
      Customer.withdraw({
        customerId: customerId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_WITHDRAW'),
            timeOut: 2000
          }, function () {
            $window.location.reload();
          });
        }
      });
    };
    //作废
    $scope.nullify = function (customerId) {
      Customer.nullify({
        customerId: customerId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_NULLITY'),
            timeOut: 2000
          }, function () {
            $state.go( 'customer.list' );
          });
        }
      });
    };
    //恢复
    $scope.recover = function (customerId) {
      Customer.recover({
        customer: {
          id: customerId
        }
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_RECOVERY'),
            timeOut: 2000
          }, function () {
            $window.location.reload();
          });
        }
      });
    };
    //跳转到添加广告方案
    $scope.navToAd = function ( customerNumber ) {
      if ( customerNumber ) {
        $state.go( 'ad2.facade.facade', {
          customerNumber: customerNumber
        });
      }
    };
    //审批记录
    $scope.modalApprovalRecord = function () {
      CustomerApprovalRecord.show( $stateParams.customerId, {windowClass: 'customer-approval-record'});
    };
    //修改记录
    $scope.modalModifyRecord = function () {
      CustomerModifyRecord.show( $stateParams.customerId, {windowClass: 'customer-modify-record'});
    };

    //查询合同列表
    $scope.queryContractList = function () {
      Customer.queryContractList({
        customerId: $stateParams.customerId
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.contractList = {
            list: response.data
          };
        }
      });
    };
    //查询帐号列表
    $scope.queryAccountList = function () {
      $scope.$broadcast( 'queryAccountList' );
    };

    //关闭提示信息
    $scope.closeTaskInfo = function () {
      $scope.customerApplyInfo.taskInfo = "";
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});