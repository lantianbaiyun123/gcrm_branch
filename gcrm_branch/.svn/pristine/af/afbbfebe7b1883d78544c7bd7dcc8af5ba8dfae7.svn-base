define(['app'], function (app) {


/**
 *
 */
app.registerController( 'CtrlResourcePositionPropertyModal', [
  '$scope', 'opts', '$modalInstance', 'Modal', 'Position', '$filter',
  function ( $scope, opts, $modalInstance, Modal, Position, $filter ) {
    // opts三种，添加、查看、编辑,+ 1 批量修改属性
    var translateFilter = $filter( 'translate' );
    var successMessage = translateFilter( 'RESOURCE_POSITION_EDIT_PROPERTY_SUCCESS' );
    if ( opts.type === 'add' ) {
      $scope.title = translateFilter( 'RESOURCE_POSITION_BUTTON_ADDPROPERTY' );
      successMessage = translateFilter( 'RESOURCE_POSITION_ADD_PROPERTY_SUCCESS' );
    } else if ( opts.type === 'view' ) {
      $scope.title = translateFilter( 'RESOURCE_POSITION_BUTTON_VIEWPROPERTY' );
      $scope.stateViewing = true;
      Position.queryPosition( { id: opts.rowData.id } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.e = angular.extend( $scope.e, response.data );
        }
      });
    } else if ( opts.type === 'edit' ) {
      $scope.title = translateFilter( 'RESOURCE_POSITION_BUTTON_MODIFYPROPERTY' );
      $scope.rotationTypeDisable = true;
      Position.queryPosition( { id: opts.rowData.id } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.e = angular.extend( $scope.e, response.data );
        }
      });
    } else if ( opts.type === 'batch' ) {
      $scope.title = translateFilter( 'RESOURCE_POSITION_BUTTON_BATCHMODIFYPROPERTY' );
      $scope.batchModifyProperty = true;
      // Position.hasProperty( { id: opts.rowData.id } ).success( function ( response ) {
      //   if ( response.code === 200 ) {
      //     $scope.notPropertyYet = !response.data;
      //   }
      // });
    }

    $scope.e = {};
    $scope.e.rotationTypes = [{
      id: 1,
      name: translateFilter( 'POSITION_TYPE_ROTATION' )
    }, {
      id: 0,
      name: translateFilter( 'POSITION_TYPE_STATIC' )
    }];
    $scope.e.materialTypes = [{
      id: 0,
      name: translateFilter( 'POSTIION_MATERIAL_TYPE_IMAGE' )
    }, {
      id: 1,
      name: translateFilter( 'POSTIION_MATERIAL_TYPE_TEXT' )
    }, {
      id: 2,
      name: translateFilter( 'POSTIION_MATERIAL_TYPE_IMAGE_AND_TEXT' )
    }];

    $scope.ok = function () {
      $scope.myFormValidating = true;
      if (
            // (angular.isDefined( $scope.e.rotationType ) || !$scope.notPropertyYet) &&
            angular.isDefined( $scope.e.materialType) &&
            // ( $scope.e.salesAmount || opts.type === 'batch' ) ) {
            $scope.e.salesAmount ) {
        var postData = angular.copy( $scope.e );
        delete postData.rotationTypes;
        delete postData.materialTypes;
        if ( $scope.e.materialType === 0 ) {
          delete postData.textlinkLength;
        } else if ( $scope.e.materialType === 1 ) {
          delete postData.size;
          delete postData.areaRequired;
        }
        postData.id = opts.rowData.id;
        Position.updatePositionProperty( postData ).success( function ( response ) {
          if ( response.code === 200 ) {
            $modalInstance.close();
            Modal.success( { content: successMessage } );
          } else {
            if ( response.code === 204 ) {
              // 修改后的资源数量，不能cover住当前已预定+确认+锁定的资源，则右侧红字报错提示：此位置已全部预定，暂不能修改数量
              $scope.salesAmountDisable = true;
            }
          }
        });
      }
    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
    $scope.rotationTypeChange = function () {
      $scope.myFormValidating = false;
      if ( !$scope.e.rotationType ) {
        $scope.e.salesAmount = 1;
      }
    };
    $scope.materialTypeChange = function () {
      $scope.myFormValidating = false;
    };
  }]);



});