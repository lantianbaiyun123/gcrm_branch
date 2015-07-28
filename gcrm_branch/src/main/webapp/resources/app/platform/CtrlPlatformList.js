/**
 * [description] require `app`
 */
define(['app', '../_common/ytCommon', '../_filters/PositionFilter', './CtrlPlatformAddAndEdit'], function ( app ) {


//register controller
app.registerController('CtrlPlatformList', [
  '$scope',
  '$log',
  'PageSet',
  'Modal',
  '$modal',
  'Position',
  '$window',
  '$filter',
  'STATIC_DIR',
  function ( $scope, $log, PageSet, Modal, $modal, Position, $window, $filter, STATIC_DIR ) {
    var translateFilter = $filter( 'translate' );
    // CONTROLLER BODY
    PageSet.set( {siteName: 'platformList', activeIndex: 2} );

    Position.getPlatformList({}).success( function ( response ) {
      if ( response.code === 200 ) {
        $scope.list = response.data;
      }
    });
    //修改平台
    $scope.btnEdit = function ( item ) {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/platform/platformModal.tpl.html',
        controller: 'CtrlPlatformAddAndEdit',
        resolve: {
          opts: function () {
            return {
              type: 'edit',
              platformId: item.id
            };
          }
        }
      });
    };
    //关闭平台
    $scope.btnShutdown = function ( item ) {
      Position.getPlatformUsedCount( { id: item.id } ).success(function ( response ) {
        if ( response.code === 200 ) {
          var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/platform/platformShutdownAlertModal.tpl.html',
            controller: ['$scope', '$modalInstance', 'count', '$filter', 'platformName', function ($scope, $modalInstance, count, $filter, platformName ) {
              if ( count ) {
                $scope.usedCountHint = $filter('translate')('SHUTDOWN_HINT', {
                  usedCount: count
                });
              }
              $scope.platformName = platformName;
              $scope.ok = function () {
                $modalInstance.close('ok');
              };
              $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
              };
            }],
            resolve: {
              count: function () {
                return response.data;
              },
              platformName: function () {
                return item.i18nName;
              }
            }
          });
          modalInstance.result.then(function (response) {
            if ( response ) {
              Position.shutdownPlatform( { id: item.id } ).success( function ( response ) {
                if ( response.code === 200 ) {
                  Modal.success( { content: translateFilter( 'PLATFORM_LIST_SHUTDOWN_SUCCESS' ) } );
                  $window.location.reload();
                }
              });
            }
          });
        }
      });
    };
    //开启平台
    $scope.btnStartup = function ( item ) {
      Position.startUpPlatform( { id: item.id } ).success( function ( response ) {
        if ( response.code === 200 ) {
          Modal.success( { content: translateFilter( 'PLATFORM_LIST_STARTUP_SUCCESS' ) }, function () {
            $window.location.reload();
          });
        }
      });
    };
    //平台新增
    $scope.btnAdd = function ( ) {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/platform/platformModal.tpl.html',
        controller: 'CtrlPlatformAddAndEdit',
        resolve: {
          opts: function () {
            return {
              type: 'add'
            };
          }
        }
      });
    };
}]);


//end of define
});