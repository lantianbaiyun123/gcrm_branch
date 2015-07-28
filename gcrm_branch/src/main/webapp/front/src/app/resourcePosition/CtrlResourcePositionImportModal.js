define(['app'], function (app) {


app.registerController('CtrlResourcePositionImportModal', [
  '$scope',
  '$modalInstance',
  'Position',
  'Modal',
  '$timeout',
  '$modal',
  '$filter',
  'GCRMUtil',
  'STATIC_DIR',
  function ( $scope, $modalInstance, Position, Modal, $timeout, $modal, $filter, GCRMUtil, STATIC_DIR ) {
    $scope.q = {};
    //get platform list at startup
    Position.getPlatformListByRole().success(function ( response ) {
      if ( response.code === 200 ) {
        $scope.q.adPlatformList = response.data;
      }
    });
    //选择了平台
    $scope.changePlatform = function () {
      Position.getSiteListByRole({
        'adPlatformId': $scope.q.platformId
      }).success(function ( response ) {
        if ( response.code === 200 ) {
          $scope.q.adSiteList = response.data;
        }
      });
    };
    // 选择了站点
    $scope.changeSite = function () {
      if ( $scope.q.siteId && $scope.q.platformId ) {
        $scope.done = {
          adPlatformId: $scope.q.platformId,
          siteId: $scope.q.siteId
        };
      }
    };
    $scope.ok = function () {
      
    };
    $scope.cancel = function () {
      $modalInstance.dismiss( 'cancel' );
    };
    $scope.e = {};
    $scope.uploadedCbfn = function () {
      $timeout( function () {
        if ( $scope.e.fileUploaded && !$scope.e.fileUploaded.errorList ) {
          $modalInstance.close( $scope.e.fileUploaded );
        } else {
          var errorList = $scope.e.fileUploaded.errorList;
          var translateFilter = $filter('translate');
          var errors = [], text, errorTextList;
          angular.forEach( errorList, function ( value, key ) {
            errorTextList = [];
            if ( key !== 'resource.position.import.name.duplicate' ) {
              angular.forEach( value, function ( value ) {
                errorTextList.push( translateFilter( 'RESOURCE_POSITION_IMPORT_ERROR_ROW_COL' ,value ) );
              });
            } else {
              //如果是名字重复
              angular.forEach( value, function ( value ) {
                if ( value.otherCell ) {
                  errorTextList.push( translateFilter( 'RESOURCE_POSITION_IMPORT_ERROR_ROW_COL' ,value ) +
                    ' VS ' + translateFilter( 'RESOURCE_POSITION_IMPORT_ERROR_ROW_COL' ,value.otherCell ) );
                } else {
                  //与数据库中数据重复，仅显示x行x列
                  errorTextList.push( translateFilter( 'RESOURCE_POSITION_IMPORT_ERROR_ROW_COL' ,value ) );
                }
              });
            }
            errors.push({
              typeName: GCRMUtil.decodeGCRMMessageSingle( key ),
              list: errorTextList
            });
          });
          var errorModal = $modal.open({
            templateUrl: STATIC_DIR + 'app/resourcePosition/importError.tpl.html',
            controller: ['$scope', 'opts', '$modalInstance', function ( $scope, opts, $modalInstance ) {
              $scope.errors = opts.errors;
              $scope.ok = function () {
                $modalInstance.close();
              };
            }],
            resolve: {
              opts: function () {
                return { errors: errors };
              }
            }
          });
        }
      });
    };
  }]);

});