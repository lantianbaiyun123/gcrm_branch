/**
 * opts = {
 *  accountData : {
      customerId: customer.id,
      companyName: customer.companyName,
      belongSalesName: customer.belongSalesName,
      accountId: 123,
      verCode: '1234',
      email: '123@baidu.com',
      verifiedStatus: 2
    }
*/
define([
  'app',
  '../_services/http/Customer',
  '../_directives/ytFocusme'
], function ( app ) {
  app.registerFactory( 'AddAccountModal', [
    '$q',
    '$modal',
    'STATIC_DIR',
  function ( $q, $modal, STATIC_DIR ) {
    return {
      show: function (opts) {
        var deferred = $q.defer();
        var optsDefault = {};
        var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/customer/addAccountModal.tpl.html',
            controller: 'CtrlAddAccount',
            windowClass: 'modal-lg',
            backdrop: 'static',
            resolve: {
              opts : function () {
                return angular.extend(optsDefault, opts);
              }
            }
        });

        modalInstance.result.then(function (result) {
          if ( result.btnType === 'close' ) {
            deferred.resolve();
          }
          if ( result.btnType === 'ok' ) {
            deferred.resolve(result.response);
          }
        });

        return deferred.promise;
      }
    };
  }]);

  app.registerController('CtrlAddAccount', [
    '$scope',
    '$log',
    '$modalInstance',
    '$filter',
    '$q',
    'Utils',
    'Customer',
    'opts',
  function ($scope, $log, $modalInstance, $filter, $q, Utils, Customer, opts) {

      $scope.accountData = opts.accountData;
      $scope.verifiedStatus = opts.accountData.verifiedStatus;

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };

      $scope.close = function (){
        $modalInstance.close({
          btnType: 'close'
        });
      };

      //发送验证码
      $scope.validateEmail = function () {
        var emailRegexp = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$/;
        if ( emailRegexp.test($scope.accountData.email) ) {
          Customer.validateEmail({
            customerNumber: $scope.accountData.customerNumber,
            email: $scope.accountData.email,
            accountId: $scope.accountData.accountId
          }).success(function (response) {
            if ( response.code === 200 ) {
              $scope.verifiedStatus = response.data.status;
              $scope.accountData.accountId = response.data.accountId;
              $scope.accountData.verCode = response.data.verifyCode;
            }
          });
        }
      };
      //置状态为未发送
      $scope.emailChanged  = function () {
        $scope.verifiedStatus = 0;
        $scope.accountData.verCode = '';
      };

      //发后台校验验证码
      $scope.next = function () {
        $scope.nextDisabled = true;
        Customer.validateVerCode({
          customerNumber: $scope.accountData.customerNumber,
          email: $scope.accountData.email,
          accountId: $scope.accountData.accountId,
          verifyCode: $scope.accountData.verCodeInput
        }).success(function (response) {
          if ( response.code === 200 ) {
            $scope.verifiedStatus = 'VERIFIED';
          }
          $scope.nextDisabled = false;
        });
      };

      //提交用户名
      $scope.ok = function () {
        $scope.submitDisabled = true;
        //3个字符以上数字、字母
        var userNameRegexp = /^[a-zA-Z0-9]{3,}$/;
        if ( userNameRegexp.test($scope.accountData.userName) ) {
          var promise = Customer.submitUserName({
            customerNumber: $scope.accountData.customerNumber,
            email: $scope.accountData.email,
            accountId: $scope.accountData.accountId,
            username: $scope.accountData.userName
          });
          promise.success(function (response) {
            if ( response.code === 200 ) {
              $scope.newUserName = response.data.username;
              $modalInstance.close({
                btnType: 'ok',
                response: response
              });
            } else if ( response.code === 206 ) {
              $scope.isUserNameError = true;
              $scope.userNameErrorText = response.message;
              $scope.userNameFocus = {focus: true};
            }
          });
          promise['finally'](function () {
            $scope.submitDisabled = false;
          });
        } else {
          $scope.submitDisabled = false;
        }
      };
      //重置用户名重复信息
      $scope.resetDuplication = function () {
        $scope.isUserNameError = false;
      };

  }]);
});