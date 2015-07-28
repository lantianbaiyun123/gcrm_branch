define( [
  'app',
  './AddAccountModal',
  '../_directives/ytPopoverConfirm'
], function ( app ) {
app.registerController( 'CtrlCustomerAccountList', [
  '$scope',
  '$stateParams',
  '$filter',
  'Customer',
  'AddAccountModal',
  'Modal',
  function ( $scope, $stateParams, $filter, Customer, AddAccountModal, Modal ) {
    var translate = $filter('translate');

    $scope.$on( 'queryAccountList', queryAccountList );
    //初始查询
    // queryAccountList();

    //添加账户
    $scope.addAccount = function (account) {
      var accountData = {
        customerId: $scope.customerApplyInfo.customerId,
        companyName: $scope.basic.companyName,
        customerNumber: $scope.customerApplyInfo.customerNumber
      };
      AddAccountModal.show({
        accountData: accountData
      }).then(function (response) {
        if ( response && response.code === 200 ) {
          Modal.success({
            content: translate('SUCCESS_ACCOUNT_ADD'),
            timeOut: 3000
          });
        }
        queryAccountList();
      });
    };

    //编辑账号
    $scope.editAccount = function (account) {
      Customer.getAccountDetail({
        accountId: account.accountId
      }).success(function ( response ) {
        if ( response.code === 200 ) {
          var accountInfo = response.data,
              accountData = {
                customerId: $scope.basic.customerId,
                companyName: accountInfo.customerName,
                customerNumber: accountInfo.customerNumber,
                accountId: accountInfo.accountId,
                verCode: accountInfo.verifyCode,
                email: accountInfo.email,
                verifiedStatus: accountInfo.status
              };

          AddAccountModal.show({
            accountData: accountData
          }).then( function (response) {
            if ( response.code === 200 ) {
              queryAccountList();
              Modal.success({
                content: $filter('translate')('SUCCESS_ACCOUNT_ADD'),
                timeOut: 3000
              });
            }
          });
        }
      });
    };

    //删除账号
    $scope.removeAccount = function (accountId) {
      Customer.removeAccount({
        accountId: accountId
      }).success(function (response) {
        if ( response.code === 200 ) {
          queryAccountList();
          Modal.success({
            content: $filter('translate')('SUCCESS_ACCOUNT_REMOVE'),
            timeOut: 3000
          });
        }
      });
    };

    //启用、停用账号
    $scope.updateAccount = function (item) {
      Customer.updateAccount({
        accountId: item.accountId,
        status: item.status
      }).success(function (response) {
        if ( response.code === 200 ) {
          queryAccountList();
          Modal.success({
            content: $filter('translate')('SUCCESS_ACCOUNT_' + item.status),
            timeOut: 3000
          });
        }
      });
    };

    //查询账号信息
    function queryAccountList () {
      Customer.queryAccountList({
        customerNumber: $scope.basic.customerNumber
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.accountList = {
            list: response.data
          };
        }
      });
    }
  }
]);


});