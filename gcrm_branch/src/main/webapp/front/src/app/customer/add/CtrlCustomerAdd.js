define([
  'app',
  '../../_common/ytCommon',
  '../../_filters/CustomerFilter',
  '../../_directives/ytInputDropdown',
  '../../_directives/ytInputCheckboxes',
  '../../_directives/ytInputRadio',
  '../../_directives/ytJqueryFileUpload',
  '../../_directives/ytAjaxupload',
  '../../_directives/checklistModel'
], function ( app ) {
  /**
   * CtrlCustomerAdd 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerAdd', [
  '$scope',
  '$log',
  '$state',
  '$stateParams',
  '$timeout',
  'Modal',
  'Customer',
  '$filter',
  'Utils',
  '$q',
  'PageSet',
  'CURRENT_USER_NAME',
  function ( $scope, $log, $state, $stateParams, $timeout, Modal, Customer, $filter, Utils, $q, PageSet, CURRENT_USER_NAME ) {
    PageSet.set({siteName:'customerAdd', activeIndex: 0});
    var dataUtil = {
      //初始化子模块：基本信息、联系人、业务机会、附件、资质认证
      initSub: function () {
        $scope.basic = { state: 'addEditing' };
        $scope.isOwner = true;  //添加时默认有权限
        $scope.contact = {
          state: 'addEditing',
          contacts: [{
            isEditing: true
          }]
        };
        $scope.opportunity = { state: 'addEditing' };
        $scope.attachment = {
          state: 'addEditing',
          attachments: []
        };
        $scope.qualification = { state: 'addEditing' };
        $scope.qualification.customerResources = [{},{},{}];
      },
      //设置编辑时需要用的数据
      setEditData: function ( editData ) {
        for (var i = 0; i < editData.currencyTypes.length; i++) {
          if ( editData.currencyTypes[i].sign === '$' ) {
            var temp = editData.currencyTypes[i];
            editData.currencyTypes.splice( i, 1);
            editData.currencyTypes.unshift( temp );
            break;
          }
        }
        // basic
        $scope.basic.customerTypes = editData.customerTypes;
        $scope.basic.industryTypes = editData.industrys;
        $scope.basic.companySizeTypes = editData.companySizes;
        $scope.basic.currencyTypes = editData.currencyTypes;
        $scope.basic.businessTypes = editData.businessType;
        $scope.basic.agentTypes = editData.agentTypes;
        $scope.basic.agentRegionals = editData.agentRegionals;
        $scope.basic.maxDate = new Date();
        // contacts

        //opportunity
        $scope.opportunity.currencyTypes = editData.currencyTypes;
        $scope.opportunity.businessTypes = editData.businessType;
        //qualification
        //attachment
        $scope.attachment.attachmentTypes = editData.attachmentTypes;
      },
      initSaving: function () {
        $scope.basic = { state: 'addEditing' };
        $scope.contact = { state: 'addEditing' };
        $scope.opportunity = { state: 'addEditing' };
        $scope.attachment = { state: 'addEditing' };
        $scope.qualification = { state: 'addEditing' };
      },
      setDetailData: function ( detailData, editData ) {
        if ( !detailData || !detailData.customer ) {
          return false;
        }
        //basic
        $scope.basic = angular.extend( $scope.basic, detailData.customer );
        $scope.basic.industry = detailData.industry;

        if ( detailData.agentCompany ) {
          $scope.basic.agentCompany = {
            id: detailData.agentCompany.id,
            name: detailData.agentCompany.companyName
          };
        }
        $scope.basic.country = detailData.country;
        $scope.basic.currencyType = detailData.currencyType;
        if ( detailData.belongSales ) {
          $scope.basic.belongSales = {
            id: detailData.belongSales.ucid,
            name: detailData.belongSales.realname
          };
        }
        $scope.basic.agentType = detailData.agentType;
        $scope.basic.agentRegional = detailData.agentRegional && detailData.agentRegional.id;
        $scope.basic.agentCountrySelected = detailData.agentCountry;
        $scope.basic.businessTypesSelected = detailData.customer.businessType && detailData.customer.businessType.split( ',' );

        if ( $scope.basic.agentRegional && $scope.basic.agentRegional) {
          Customer.queryAgentCountries( { id: $scope.basic.agentRegional } ).success( function ( response ) {
            if ( response.code === 200 ) {
              $scope.basic.agentCountries = response.data.agentCountries;
            }
          });
        }

        //如果无联系人，添加一条空联系人以供填写
        if (detailData.contacts && detailData.contacts.length) {
          $scope.contact.contacts = detailData.contacts;
        } else {
          $scope.contact.contacts = [{
            isEditing: true
          }];
        }

        if ( detailData.opportunityView ) {
          $scope.opportunity = angular.extend( $scope.opportunity, detailData.opportunityView );
          $scope.opportunity.currencyTypes = editData.currencyTypes;
          if ( detailData.opportunityView.billingModel ) {
            $scope.opportunity.billingModelsSelected = detailData.opportunityView.billingModel.split(',');
          }
          if ( detailData.opportunityView.businessType ) {
            $scope.opportunity.businessTypesSelected = detailData.opportunityView.businessType.split(',');
          }
          $scope.opportunity.platformsSelected = detailData.opportunityView.advertisingPlatforms;
        }
        $scope.qualification = detailData.qualification || {
          customerResources: [{},{},{}],
          state: 'addEditing'
        };
        $scope.qualification.state = 'addEditing';
        $scope.attachment.attachments = detailData.attachments;
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
    /**
     * 根据customerId来判断是新增，还是待提交状态
     */
    if ( $stateParams.customerId ) {
      dataUtil.initSaving();
      $q.all( [ Customer.getEditInfo(), Customer.getDetail( { id: $stateParams.customerId } ) ] ).then( function ( result ) {
        var editResponse = result[0].data;
        var detailResponse = result[1].data;
        dataUtil.setEditData( editResponse.data );
        dataUtil.setDetailData( detailResponse.data, editResponse.data );
        $scope.typeChangeNotAllowed = !detailResponse.data.typeChangeAllowed;
        $scope.isOwner = detailResponse.data.isOwner;

      });
    } else {
      dataUtil.initSub();
      Customer.getEditInfo().success( function ( response ) {
        if ( response.code === 200 ) {
          dataUtil.setEditData( response.data );
          //默认将当前用户作为销售及销售上级带出来
          $scope.basic.belongSales = {
            id: CURRENT_USER_NAME.ucid,
            name: CURRENT_USER_NAME.realname
          };
          Customer.getBelongSalesLeader({
            salerId: CURRENT_USER_NAME.ucid
          }).success( function ( response ) {
            if ( response.code === 200 ) {
              $scope.basic.belongManager = response.data;
            }
          });
        }
      });
    }
    //保存，需要校验
    $scope.saveToApproval = function () {
      $scope.basic.isValidating = true;
      if ( !$scope.basic.companyName ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      if ( !$scope.basic.customerType ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      if ( !$scope.basic.country ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      if ( $scope.basic.customerType !== 'offline' && ( !$scope.basic.industry || !$scope.basic.industry.id ) ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      // if ( !$scope.basic.registerTime ) {
      //   $scope.anchorTo( 'anchorCustomerTop' );
      //   return false;
      // }
      if ( !$scope.basic.url ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      if ( !$scope.basic.businessTypesSelected || !$scope.basic.businessTypesSelected.length ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      if ( !$scope.basic.belongSales ) {
        $scope.anchorTo( 'anchorCustomerTop' );
        return false;
      }
      //如果是非直客，校验代理商
      //--如果是线下，则校验代理类型、代理区域、代理国家
      if ( $scope.basic.customerType === 'nondirect' ) {
        if ( !$scope.basic.agentCompany ) {
          $scope.anchorTo( 'anchorCustomerTop' );
          return false;
        }
      } else if ( $scope.basic.customerType === 'offline' ) {
        if ( !$scope.basic.agentType || !$scope.basic.agentRegional) {
          $scope.anchorTo( 'anchorCustomerTop' );
          return false;
        }
        if ( !$scope.basic.agentCountrySelected || !$scope.basic.agentCountrySelected.length ) {
          $scope.anchorTo( 'anchorCustomerTop' );
          return false;
        }
      }
      $scope.disableToApproval = true;
      //校验都通过之后，提交审批
      Customer.saveToApprove( dataUtil.getAllPostParams() ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.basic.isValidating = false;
          Modal.success( { content: $filter('translate')('SUCCESS_SUBMIT_TO_APPROAVAL') }, function () {
            $state.go( 'customer.detail.facade', {
              customerId:  response.data.customer.id
            });
          });
        }
        $scope.disableToApproval = false;
      });
    };
    //暂存，不用校验,成功后，跳转到待提交的编辑页
    $scope.save = function () {
      Customer.save( dataUtil.getAllPostParams() ).success( function ( response ) {
        if ( response.code === 200 ) {
          Modal.success( { content: $filter('translate')('SUCCESS_TEMP_SAVE') }, function () {
            if ( !$stateParams.customerId ) {
              $stateParams.customerId = response.data.customer.id;
              //重载成为编辑页
              $state.transitionTo( $state.current, $stateParams, {
                reload: true, inherit: false, notify: false
              });
            }
          });
        }
      });
    };
    $scope.back = function () {
      $state.go( 'customer.list' );
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});