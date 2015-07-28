/**
 * [客户列表页Ctrl]
 */
define([
  'app',
  '../../_services/http/Country',
  '../../_services/http/Customer',
  '../../_directives/ytInputDropdown',
  '../../_directives/ytPopoverConfirm',
  './SalesTransferModal',
  '../AddAccountModal',
  'anuglar-ui-select2'
], function (app) {

  app.registerController('CtrlCustomerList', [
    '$scope',
    '$log',
    '$filter',
    '$timeout',
    '$window',
    'PageSet',
    'Modal',
    'CustomerListConstant',
    'Customer',
    'Country',
    'SalesTransferModal',
    'AddAccountModal',
  function ($scope, $log, $filter, $timeout, $window, PageSet, Modal, CustomerListConstant, Customer, Country, SalesTransferModal, AddAccountModal) {
    PageSet.set({activeIndex:0,siteName:'customerList'});

    //查询条件
    $scope.qForm = {};
    //列表
    $scope.customerList = [];
    //所属国家suggest
    $scope.country = Country.getSuggest();
    //所属销售suggest
    $scope.belongSalesSuggestOption = Customer.getBelongSalesSuggestOption({allowClear:true});
    //所属代理商suggest
    $scope.agentCompanySuggestOption = Customer.getAgentCompanySuggestOption({allowClear:true});

    //监听查询类型，如果是所属销售，切换为其suggest input
    $scope.$watch('qForm.queryType', function ( newValue ) {
      if ( newValue ) {
        $scope.qForm.belongSales = undefined;
        $scope.qForm.belongAgent = undefined;
        $scope.qForm.queryString = '';
      }
    });

    //更多查询条件切换
    $scope.queryMoreShow = false;
    $scope.toggleQueryMore = function () {
      $scope.queryMoreShow = !$scope.queryMoreShow;
    };

    //pagenation
    $scope.pager = {
      pageSize: 10,
      pageSizeSlots: [10,20,30,50],
      pageNumber: 1,
      totalCount:0
    };
    //更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      resetPageNumber();
      getList();
    };
    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue, oldValue ){
      if ( newValue && newValue !== oldValue ) {
        getList();
      }
    });

    $scope.btnQuery = function () {
      resetPageNumber();
      getList();
    };

    //查看详情
    $scope.checkDetail = function ( customer ) {
      var url;
      if ( customer.approvalStatusName === 'saving' && !customer.hasBeenApproved ) {
        url  = '#/customer/add?customerId=' + customer.id;
        $window.open(url, '_blank');
      } else {
        url  = '#/customer/detail?customerId=' + customer.id;
        $window.open(url, '_blank');
      }
    };
    //销售转移
    $scope.salseTransfer = function (customer) {
      var customerData = {
        customerId: customer.id,
        companyName: customer.companyName,
        belongSalesName: customer.belongSalesName
      };
      SalesTransferModal.show({
        customerData: customerData
      }).then(function (response) {
        getList();
        Modal.success({
          content: translateFilter('SUCCESS_SALES_TRANSFER'),
          timeOut: 2000
        });
      });
    };
    //添加账户
    $scope.addAccount = function (customer) {
      var accountData = {
        customerId: customer.id,
        companyName: customer.companyName,
        customerNumber: customer.customerNumber
        // accountId: 123,
      };
      AddAccountModal.show({
        accountData: accountData
      }).then(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: translateFilter('SUCCESS_ACCOUNT_ADD'),
            timeOut: 3000
          });
          getList();
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
            content: translateFilter('SUCCESS_REMIDER'),
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
          getList();
          Modal.success({
            content: translateFilter('SUCCESS_WITHDRAW'),
            timeOut: 2000
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
          getList();
          Modal.success({
            content: translateFilter('SUCCESS_NULLITY'),
            timeOut: 2000
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
          resetPageNumber();
          getList();
          Modal.success({
            content: translateFilter('SUCCESS_RECOVERY'),
            timeOut: 2000
          });
        }
      });
    };

    init();
    function init () {
      CustomerListConstant.get().then(function (constant) {
        $scope.queryTypes = constant.queryTypes;
        $scope.approvalStatuses = constant.approvalStatuses;
        $scope.customerTypes = constant.customerTypes;
        $scope.agentRegions = constant.agentRegions;

        $timeout(function () {
          getList();
        });
      });
    }

    //查询列表
    function getList () {
      var paramObj = queryParam();
      Customer.getList(paramObj).success(function (response) {
        if ( response.code === 200 ) {
          try {
            $scope.customerList = response.data.content;
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
          } catch ( e ) {
            $log.info(response);
          }
        } else {
          $log.info(response);
        }
      });
    }

    //打包查询参数
    function queryParam () {
      var paramObj = {},
          pager = $scope.pager,
          qForm = $scope.qForm;

      paramObj.pageSize = pager.pageSize;
      paramObj.pageNumber = pager.pageNumber;

      if ( qForm.queryType ) {
        paramObj.queryType = qForm.queryType.value;
        if ( qForm.queryType.value === 'SALES' ) {
          paramObj.queryStr = qForm.belongSales && qForm.belongSales.name;
        } else if ( qForm.queryType.value === 'AGENT' ) {
          paramObj.queryStr = qForm.belongAgent && qForm.belongAgent.id;
        } else {
          paramObj.queryStr = qForm.queryString;
        }
      }
      paramObj.approvalStatus = qForm.approvalStatus;
      paramObj.customerType = qForm.customerType;
      paramObj.agentRegional = qForm.agentRegion && qForm.agentRegion.id;
      paramObj.country = qForm.country && qForm.country.id;

      return  paramObj;
    }

    function resetPageNumber() {
      $scope.pager.pageNumber = 1;
    }

  }]);

  app.registerService('CustomerListConstant', [
    '$q',
    '$filter',
    '$http',
    'Customer',
  function ($q, $filter, $http, Customer) {
    var deferred = $q.defer();
        translateFilter = $filter('translate'),
        constant = {
          queryTypes: [
            {
              i18nName: translateFilter('COMPANY_NAME'),
              value: 'COMPANY'
            },
            {
              i18nName: translateFilter('BELONGING_SALES'),
              value: 'SALES'
            },
            {
              i18nName: translateFilter('BELONGING_ANGENT_COMPANY'),
              value: 'AGENT'
            },
            {
              i18nName: translateFilter('CUSTOMER_ANGENT_NUMBER'),
              value: 'AGENT_CUSTOMER_NUMBER'
            }
          ],
          approvalStatuses: [],
          customerTypes: []
        };
    return {
      get: function () {
        var deferred = $q.defer();
        Customer.getListQueryView().success(function (response) {
          if ( response.code === 200 ) {
            var selections = response.data;
            constant.agentRegions = selections.agentRegionals;
            constant.approvalStatuses = selections.approveState;
            constant.customerTypes = selections.customerTypes;
          }
          deferred.resolve(constant);
        });
        return deferred.promise;
      }
    };
  }]);
});