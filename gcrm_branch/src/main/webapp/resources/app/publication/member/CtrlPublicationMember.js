define([
  'app',
  '../../_services/Select2Suggest',
  'anuglar-ui-select2'
], function (app) {


  /**
   * [上下线成员管理Ctrl]
   */
  app.registerController('CtrlPublicationMember', [
    '$scope',
    '$log',
    '$filter',
    'PageSet',
    'Publication',
    '$modal',
    'Modal',
    'STATIC_DIR',
  function ($scope, $log, $filter, PageSet, Publication, $modal, Modal, STATIC_DIR ) {
    PageSet.set( { activeIndex:4, siteName: 'publicationMember' } );
    $scope.t = {};
    Publication.getAvailablePlatform().success( function ( response ) {
      if ( response.code === 200 ) {
        $scope.t.platformList = response.data;
      }
    });
    $scope.changePlatform = function ( platform ) {
      Publication.getMemberList( { platformId: platform.id }).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.list = response.data;
        }
      });
    };
    /**
     * 删除user点击
     * @param  {obj} user  用户
     * @param  {obj} item  当前记录
     * @param  {number} index user所在item.user的索引
     * @return {void}       无
     */
    $scope.removeUser = function ( user, item, index ) {
      Publication.removeMember({
        id: item.id,
        ucid: user.ucid
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          //删除成功后将user在list中移除
          Modal.success( { content: $filter('translate')('SUCCESS_PUBLICATION_REMOVE_MEMBER') } );
          item.user.splice( index, 1 );
        }
      });
    };
    $scope.addMember = function ( item ) {
      var modal = $modal.open({
        templateUrl: STATIC_DIR + 'app/publication/member/add.tpl.html',
        controller: ['$scope', 'opts', 'Select2Suggest', '$timeout', '$modalInstance', '$log', function ( $scope, opts, Select2Suggest, $timeout, $modalInstance, $log ) {
          $log.info(opts, $scope);
          $scope.e = {};
          $scope.e.users = [];
          $scope.e.userOption = Select2Suggest.getUserOption( {
            onSelect: function ( item ) {
              $timeout( function () {
                $scope.e.users.push( item );
              });
            }
          });
          $scope.ok = function () {
            Publication.addMember({
              id: opts.rowData.id,
              channelId: opts.rowData.channelId,
              ucid: $scope.e.user.ucid
            }).success( function ( response ) {
              if ( response.code === 200 ) {
                $modalInstance.close({
                  user: $scope.e.user,
                  recordId: response.data
                });
              }
            });
          };

          $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
          };
        }],
        resolve: {
          opts: function () {
            return { rowData: item };
          }
        }
      });
      //添加成功后，将记录id回写，将user加入list
      modal.result.then( function ( result ) {
        if ( result ) {
          Modal.success( { content: $filter('translate')('SUCCESS_PUBLICATION_ADD_MEMBER') } );
          item.id = result.recordId;
          item.user = item.user || [];
          item.user.push( result.user );
        }
      });
    };
  }]);



});