define( [ 'app', '../_filters/BusinessTypeFilter', 'anuglar-ui-select2' ], function ( app ) {
  /**
   * CtrlCustomerBasic 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerBasic', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  'Modal',
  'Customer',
  'Country',
  function ( $scope, $state, $stateParams, $timeout, Modal, Customer, Country ) {
    $scope.belongSalesSuggestOption = Customer.getBelongSalesSuggestOption({
      cbfn: function ( suggestItem ) {
        if ( !suggestItem || !suggestItem.id ) {
          return false;
        }
        // query sales' boss
        Customer.getBelongSalesLeader({
          salerId: suggestItem.id
        }).success( function ( response ) {
          if ( response.code === 200 ) {
            $scope.basic.belongManager = response.data;
          }
        });
      }
    });
    $scope.countrySuggestOption = Country.getSuggest({
      allowClear: false
    });
    $scope.agentCompanySuggestOption = Customer.getAgentCompanySuggestOption();
    // $scope.agentRegionalChange = function (choice) {
    //   if ( $scope.basic.agentRegional ) {
    //     $scope.basic.agentCountriesLoading = true;  //加载时禁用agentRegional radio，并显示loading info
    //     $scope.basic.agentCountrySelected = [];
    //     $scope.basic.agentCountries = [];
    //     Customer.queryAgentCountries( { id: $scope.basic.agentRegional.id } ).success( function ( response ) {
    //       if ( response.code === 200 ) {
    //         $scope.basic.agentCountries = response.data.agentCountries;
    //       }

    //       $scope.basic.agentCountriesLoading = false; //结束loading状态，恢复radio，隐藏loading info
    //     });
    //   }
    // };
    $scope.$watch('basic.agentRegional', function (newVal, oldVal) {
      //初始化回填时不更新, agentCountrySelected
      var isInit = (!oldVal && $scope.basic.agentCountrySelected && $scope.basic.agentCountrySelected.length);
      if (newVal && !isInit) {
        $scope.basic.agentCountriesLoading = true;  //加载时禁用agentRegional radio，并显示loading info
        $scope.basic.agentCountrySelected = [];
        $scope.basic.agentCountries = [];
        Customer.queryAgentCountries( { id: newVal } ).success( function ( response ) {
          if ( response.code === 200 ) {
            $scope.basic.agentCountries = response.data.agentCountries;
          }

          $scope.basic.agentCountriesLoading = false; //结束loading状态，恢复radio，隐藏loading info
        });
      }
    });
    /*
      详情时，修改基本信息
     */
    $scope.btnEdit = function () {
      $scope.tempBasic = angular.copy( $scope.basic );
      $scope.basic.state = 'detailEditing';
    };
    /* 取消编辑啊*/
    $scope.btnCancel = function () {
      angular.extend( $scope.basic, $scope.tempBasic, {
        state: 'detailViewing'
      });
    };
    //保存基本信息
    $scope.btnSave = function () {
      $scope.$emit( 'basicSave' );
    };
    //新增代理商
    $scope.addAgentCompany = function ( newAgentCompanyName ) {
      if ( newAgentCompanyName ) {
        Customer.addAgentCompany( {
          companyName: newAgentCompanyName
        }).success( function ( response ) {
          if ( response.code === 200 ) {
            $scope.basic.agentCompany = {
              id: response.data.id,
              name: response.data.companyName
            };
            $scope.basic.stateAddingAgent = false;
            // $scope.basic.stateAlreadyAgent = true;
          }
        });
      }
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});