define(['app'], function (app) {
  app.registerController('CtrlAdBasic', [
    '$scope',
    'AdBasic',
    'Customer',
  function ($scope, AdBasic, Customer) {

    //取消编辑
    $scope.basicCancel = function() {
      AdBasic.resetBasic($scope);
    };

    $scope.customerSelectedChanged = function (customerSelected) {
      if (!$scope.state.basicReview && customerSelected) {
        $scope.basic.contract = null;
        $scope.basic.hasContract = false;
        $scope.basic.checkContract = false;
        Customer.get({
          customerNumber: customerSelected.data,
          companyName: ''
        }).success(function (response) {
          $scope.basic.customerI18nView = response.data;
          $scope.basic.hasContract = response.data.hasContract;
        });
      }
    };
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

      AdBasic.save($scope);
    };

  }]);
});