define( [ 'app' ], function ( app ) {
  /**
   * CtrlCustomerContacts 客户管理，客户基本信息
   */
app.registerController( 'CtrlCustomerContacts', [
  '$scope',
  '$state',
  '$filter',
  '$stateParams',
  '$timeout',
  'Modal',
  'Customer',
  function ( $scope, $state, $filter, $stateParams, $timeout, Modal, Customer ) {
    $scope.$on( 'customerAdd', function ( event, params ) {
      $scope.addRow();
    });
    $scope.$on( 'customerDetail', function ( event, params ) {
      $scope.contacts = params.contacts;
    });
    $scope.contact.genderTypes = [ 'male', 'female', 'others' ];
    //添加一个空白行，状态是编辑中
    $scope.addRow = function () {
      $scope.contact.contacts = $scope.contact.contacts || [];
      var newItem = {
        isEditing: true
      };
      if ( $scope.basic.id ) {
        newItem.customerNumber = $scope.basic.id;
      }
      $scope.contact.contacts.push( newItem );
    };
    /**
     * 删除一条联系人信息
     * 添加时，直接删除即可
     * 编辑时，需要发请求删除
     */
    $scope.removeRow = function ( item, index ) {
      if ( $scope.contact.contacts && $scope.contact.contacts.length ) {
        if ( !item.id ) {
          $scope.contact.contacts.splice( index, 1 );
        } else {
          Customer.removeContactor( item ).success( function ( response ) {
            if ( response.code === 200 ) {
              if ( $scope.contact.contacts && $scope.contact.contacts.length ) {
                $scope.contact.contacts.splice( index, 1 );
              }
            }
          });
        }
      }
    };
    $scope.setGenderType = function ( contact, choice ) {
      contact.gender = choice;
    };
    $scope.editRow = function ( item ) {
      item.isEditing = true;
    };
    $scope.saveRow = function ( item ) {
      if ( !item.name ) {
        Modal.alert( { content: $filter('translate')('CUSTOMER_INPUT_CONTACT') });
        return false;
      }
      Customer.saveContactor( item ).success( function ( response ) {
        if ( response.code === 200 ) {
          angular.extend( item, response.data, { isEditing: false } );
        }
      });
    };
  }
]);
  /**
   * End of Ctrl Code
   */
});