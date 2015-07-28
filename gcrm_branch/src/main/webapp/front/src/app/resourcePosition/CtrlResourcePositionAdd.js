/**
 * [description] require `app`
 */
define(['app', '../_common/ytCommon', 'angular-ui-tree'], function ( app ) {


//register controller
app.registerController('CtrlResourcePositionAdd', [
  '$scope',
  '$log',
  'PageSet',
  'Position',
  'Modal',
  '$state',
  '$stateParams',
  '$filter',
  function ( $scope, $log, PageSet, Position, Modal, $state, $stateParams, $filter ) {
    // CONTROLLER BODY
    var translateFilter = $filter( 'translate' );
    PageSet.set( {'siteName': 'resourcePositionAdd', 'activeIndex': 2} );
    $scope.q = {};
    //get platform list at startup
    Position.getPlatformListByRole().success(function ( response ) {
      if ( response.code === 200 ) {
        $scope.q.adPlatformList = response.data;
        if ( $stateParams.platformId && $stateParams.siteId ) {
          $scope.q.platformId = $stateParams.platformId;
          $scope.q.siteId = $stateParams.siteId;
          $scope.changePlatform( function () {
            queryPositionTree();
          });
        }
      }
    });
    //选择了平台
    $scope.changePlatform = function ( cbfn ) {
      Position.getSiteListByRole({
        'adPlatformId': $scope.q.platformId
      }).success(function ( response ) {
        if ( response.code === 200 ) {
          $scope.q.adSiteList = response.data;
          $scope.q.siteId = null;
          $scope.q.positionTree = null;
          ( cbfn || angular.noop )();
          if ( cbfn && $stateParams.siteId ) {
            $scope.q.siteId = $stateParams.siteId;
          }
        }
      });
    };
    //选择了站点
    $scope.changeSite = function () {
      queryPositionTree();
    };
    var queryPositionTree = function () {
      // body...
      Position.getPositionTree({
        adPlatformId: $scope.q.platformId,
        siteId: $scope.q.siteId
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.q.positionTree = response.data.posotionData;
          //如果该平台&站点下没有位置信息，添加一条空的数据
          if ( !response.data.posotionData || !response.data.posotionData.length ) {
            $scope.q.positionTree = [{
              children: [],
              isNew: true
            }];
          }
        }
      });
    };
    //保存整棵树
    $scope.btnSavePosition = function () {
      $scope.stateValidating = true;
      if ( validateBeforeSubmit() ) {
        Position.savePosition( {
          adPlatformId: $scope.q.platformId,
          siteId: $scope.q.siteId,
          posotionData: $scope.q.positionTree
        }).success( function ( response ) {
          if ( response.code === 200 ) {
            Modal.success( { content: translateFilter( 'RESOURCE_POSITION_ADD_TREE_SUCCESS' ) }, function () {
              $state.go('resourcePositionList');
            } );
            // $scope.stateValidating = false;
            // queryPositionTree();
          }
        });
      }
    };

    //覆写ng-parttern,英文名称中不允许出现中文
    $scope.noChinese = (function() {
      var chineseParttern = /([^\x00-\xff])/g;
      return {
        test: function(value) {
          return !chineseParttern.test(value);
        }
      };
    })();

    //校验重名 和 空
    var validateBeforeSubmit = function (argument) {
      var flagijkDuplicate = false;//如果有任意的重名，则校验不通过
      var flagijkEmpty = false;//空的状态
      var channelList = $scope.q.positionTree;
      if ( !channelList ) {
        return;
      }
      var channelLength = channelList.length;
      var channelDuplicate = [];
      for (var i = 0; i < channelLength; i++) {
        var channel = channelList[ i ];
        //if duplicate
        var flagii = false;
        if ( channel.i18nData && channel.i18nData.cnName && channel.i18nData.enName ) {
          for (var ii = 0; ii < channelDuplicate.length; ii++) {
            //判断是否中文名、英文名都相同
            if ( channelDuplicate[ii].cnName === channel.i18nData.cnName ||
                 channelDuplicate[ii].enName === channel.i18nData.enName
            ) {
              flagii = true;
            }
          }
          /**
           * 如果有重名，设置isDuplicate值以显示提示
           * 如果没有重名，将中英文名加到duplicate数组
           */
          if ( flagii ) {
            channel.isDuplicate = true;
            flagijkDuplicate = true;
          } else {
            channel.isDuplicate = false;
            channelDuplicate.push( {
              cnName: channel.i18nData.cnName,
              enName: channel.i18nData.enName
            });
          }
        } else {
          //如果没填写中英文名
          channel.isDuplicate = false;
          flagijkEmpty = true;
        }
        //以下是区域及位置的校验，逻辑与以上的频道类似
        var areaList = channelList[ i ].children;
        if ( !areaList ) {
          break;
        }
        var areaListLength = areaList.length;
        var areaDuplicate = [];
        for (var j = 0; j < areaListLength; j++) {
          var area = areaList[ j ];
          var flagjj = false;
          if ( area.i18nData && area.i18nData.cnName && area.i18nData.enName ) {
            for (var jj = 0; jj < areaDuplicate.length; jj++) {
              if ( areaDuplicate[jj].cnName === area.i18nData.cnName ||
                   areaDuplicate[jj].enName === area.i18nData.enName
              ) {
                flagjj = true;
              }
            }
            if ( flagjj ) {
              area.isDuplicate = true;
              flagijkDuplicate = true;
            } else {
              area.isDuplicate = false;
              areaDuplicate.push( {
                cnName: area.i18nData.cnName,
                enName: area.i18nData.enName
              });
            }
          } else {
            area.isDuplicate = false;
            flagijkEmpty = true;
          }
          var positionList = areaList[ j ].children;
          if ( !positionList ) {
            break;
          }
          var positionListLength = positionList.length;
          var positionDuplicate = [];
          for (var k = 0; k < positionListLength; k++) {
            var position = positionList[ k ];
            //if duplicate
            var flagkk = false;
            if ( position.i18nData && position.i18nData.cnName && position.i18nData.enName ) {
              for (var kk = 0; kk < positionDuplicate.length; kk++) {
                if ( positionDuplicate[kk].cnName === position.i18nData.cnName ||
                     positionDuplicate[kk].enName === position.i18nData.enName
                ) {
                  flagkk = true;
                }
              }
              if ( flagkk ) {
                position.isDuplicate = true;
                flagijkDuplicate = true;
              } else {
                position.isDuplicate = false;
                positionDuplicate.push( {
                  cnName: position.i18nData.cnName,
                  enName: position.i18nData.enName
                });
              }
            } else {
              position.isDuplicate = false;
              flagijkEmpty = true;
            }
          }
        }
      }
      return !flagijkDuplicate && !flagijkEmpty;
    };
    //中英文keyup处理，当点击过保存的按钮的时候，校验空值及重名
    $scope.inputKeyup = function () {
      if ( $scope.stateValidating ) {
        validateBeforeSubmit();
      }
    };
    $scope.treeOptions = {
      // maxDepth: 3
      accept: function(sourceNode, destNodes, destIndex) {
        // var data = sourceNode.$modelValue;
        // var destType = destNodes.$element.attr('data-type');
        //
        // $log.info(sourceNode.depth(), destNodes.depth());
        //必须同层级才能移动
        return sourceNode.depth() === destNodes.depth() + 1; // only accept the same type
      }
    };
    /**
     * [remove description] 节点的删除
     * 需要判断本身及下游是否含有原始的数据节点
     * 有-不允许删除
     * 无-删除
     */
    $scope.removeNodeCustom = function ( scope ) {
      removeNode( scope );
    };
    var removeNode = function( scope ) {
      if ( allowRemove( scope ) ) {
        //TODO 是否需要确认
        // Modal.confirm( { content: '确认删除该节点？' }, function () {
          scope.remove();
        // });
      } else {
        Modal.alert( { content: translateFilter( 'RESOURCE_POSITION_ADD_REMOVE_NOT_ALLOWED' ) });
      }
    };
    //判断本身及下游是否含有原始的数据节点
    var allowRemove = function recursive ( scope ) {
      //如果不存在scope，返回false
      if ( !scope ) {
        return false;
      }
      //如果该节点是旧节点，不允许删除
      if ( !scope.$modelValue.isNew ) {
        return false;
      }
      //如果没有子节点了，允许
      if ( !scope.childNodes().length ) {
        return true;
      }
      //递归查找子节点
      for (var i = 0; i < scope.childNodes().length; i++) {
        return recursive( scope.childNodes()[i] );
      }
      return false;
    };
    // 展开/收起节点
    $scope.toggle = function(scope) {
      scope.toggle();
    };
    //添加频道
    $scope.addChannel = function ( treeModel ) {
      $scope.stateValidating = false;
      treeModel.unshift({
        children: [],
        isNew: true
      });
    };
    //添加区域
    $scope.addArea = function ( scope ) {
      $scope.stateValidating = false;
      newSubItem( scope );
    };
    //添加位置
    $scope.addPosition = function ( scope ) {
      $scope.stateValidating = false;
      newSubItem( scope );
    };
    //在孩子节点的末尾添加一个节点
    var newSubItem = function(scope) {
      var nodeData = scope.$modelValue;
      nodeData.children = nodeData.children || [];
      nodeData.children.unshift({
        children: [],
        isNew: true
      });
    };


}]);


//end of define
});