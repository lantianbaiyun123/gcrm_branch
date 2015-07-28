define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomeropportunity 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerOpportunity', [
  '$scope',
  '$state',
  '$stateParams',
  '$timeout',
  'Modal',
  'CtrlCustomerOpportunityPlatform',
  function ( $scope, $state, $stateParams, $timeout, Modal, CtrlCustomerOpportunityPlatform ) {
    $scope.opportunity = $scope.opportunity || {};
    $scope.opportunity.payTypes = [ 'advance', 'after' ];
    $scope.opportunity.billingModels = [ 'CPC', 'CPS', 'CPA', 'CPM', 'CPT' ];
    $scope.$watch( 'opportunity.businessTypesSelected', function ( newValue ) {
      if ( newValue ) {
        CtrlCustomerOpportunityPlatform.updatePlatforms( newValue ).then( function ( result ) {
          $scope.opportunity.platforms = result;
        });
      }
    }, true );
    /*
      详情时，修改基本信息
     */
    $scope.btnEdit = function () {
      $scope.tempOpportunity = angular.copy( $scope.opportunity );
      $scope.opportunity.state = 'detailEditing';
    };
    /* 取消编辑啊*/
    $scope.btnCancel = function () {
      angular.extend( $scope.opportunity, $scope.tempOpportunity, {
        state: 'detailViewing'
      });
    };
    //保存基本信息
    $scope.btnSave = function () {
      $scope.$emit( 'opportunitySave' );
    };
  }
]);
app.registerService( 'CtrlCustomerOpportunityPlatform', ['$q', 'Position', function ( $q, Position ) {
  var cache = {};
  function getAllPromise ( selectedTypes ) {
    var promiseList = [];
    for (var i = 0; i < selectedTypes.length; i++) {
      if ( !cache[ selectedTypes[ i ] ] ) {
        promiseList.push( Position.getPlatformListByBusinessType( { businessType: selectedTypes[ i ] } ) );
      }
    }
    return promiseList;
  }
  function joinCache ( selectedTypes ) {
    var resultArr = [];
    for ( var key in cache ) {
      if ( selectedTypes.indexOf( key ) !== -1 ) {
        resultArr = resultArr.concat( cache[ key ] );
      }
    }
    return resultArr;
  }
  return {
    updatePlatforms: function ( selectedTypes ) {
      var deferred = $q.defer();
      $q.all( getAllPromise( selectedTypes ) ).then( function ( result ) {
        var index = 0;
        for (var i = 0; i < selectedTypes.length; i++) {
          if ( !cache[ selectedTypes[ i ] ] ) {
            cache[ selectedTypes[ i ] ] = result[ index ].data.data;
            index = index + 1;
          }
        }
        deferred.resolve( joinCache( selectedTypes ) );
      });
      return deferred.promise;
    }
  };
}]);
  /**
   * End of Ctrl Code
   */
});