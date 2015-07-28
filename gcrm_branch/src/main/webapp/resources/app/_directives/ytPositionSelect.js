define(['app'], function ( app ) {
/**
 * USAGE: <div yt-position-select="opts" position-selected="positionSelected"></div>
 *  opts = {
      level: 3  //调用层数，最大为4
      byRole: true //是否根据用户权限查询 平台和站点
    }
 */

  app.registerDirective('ytPositionSelect', [
    '$log',
    '$filter',
    'Position',
    'STATIC_DIR',
    function ( $log, $filter, Position, STATIC_DIR ) {
      return {
        scope: {
          positionSelected: '=',
          ytPositionSelect: '='
        },
        restrict: 'A',
        templateUrl: STATIC_DIR + 'app/_directives/ytPositionSelect.tpl.html',
        replace: true,
        link: function ( scope, element, attrs ) {
          var optsDefault = {
            level: 4
          };
          var opts = angular.extend(optsDefault, scope.ytPositionSelect);
          //get platform list at startup
          var platformListPromise;
          if ( opts.byRole ) {
            platformListPromise = Position.getPlatformListByRole();
          } else {
            platformListPromise = Position.getPlatformList4Radio();
          }
          platformListPromise.success(function ( response ) {
            if ( response.code === 200 ) {
              var temp = angular.copy( response.data ) || [];
              temp.unshift(scope.q['platform'].all);
              scope.q['platform'].list = temp;
            }
            scope.positionSelected = packSelected();
          });

          /**
           * [setChoice description]
           * @param {Number} index 点击的当前list的index
           * @param {String} type  选择的是 platform | site | channel | area | position
           */
          scope.setChoice = function ( index, type ) {
            clearChoice( type );
            var selected = scope.q[ type ].list[ index ];
            scope.q[ type ].selected = selected;
            if ( selected.id ) {
              // scope.positionSelected = angular.copy( selected );//output the value
              queryChoice( type );
            } else if ( scope.q[ type ].pre ) {
              //如果选择了全部，不改变当前选择的值并且认定为上一级的值
              // scope.positionSelected = angular.copy( scope.q[ scope.q[ type ].pre ].selected );
            } else {
              //选择了platform的全部，你厉害哦
              // $log.info( '你厉害哦' );
              // scope.positionSelected = null;//返回null
            }
            scope.positionSelected = packSelected();//output the selected values

          };

          var translateFilter = $filter('translate');
          scope.q = {
            platform: {
              all: {
                i18nName: translateFilter('POSITION_SELECT_ALL_PLATFORM'),
                id: undefined
              },
              list: [],
              next: 'site'
            },
            site: {
              all: {
                i18nName: translateFilter('POSITION_SELECT_ALL_SITE'),
                id: undefined
              },
              list: [],
              next: 'channel',
              pre: 'platform'
            },
            channel: {
              all: {
                i18nName: translateFilter('POSITION_SELECT_ALL_CHANNEL'),
                id: undefined
              },
              list: [],
              next: 'area',
              pre: 'site'
            },
            area: {
              all: {
                i18nName: translateFilter('POSITION_SELECT_ALL_AREA'),
                id: undefined
              },
              list: [],
              next: 'position',
              pre: 'channel'
            },
            position: {
              all: {
                i18nName: translateFilter('POSITION_SELECT_ALL_POSITION'),
                id: undefined
              },
              list: [],
              pre: 'area'
            }
          };

          //让ng-repeat不要排序
          scope.notSorted = function(obj){
            if (!obj) {
              return [];
            }
            return Object.keys(obj);
          };

          qInit();

          //根据初始化q，
          function qInit () {
            //使用层数设置
            if ( ~~opts.level === 3 ) {
              delete scope.q.position;
            }
            for ( var key in scope.q ) {
              scope.q[key].selected = angular.copy(scope.q[key].all);
            }
          }

          //清空所有的下级
          function clearChoice ( type ) {
            var cur = scope.q[type];
            while ( cur && cur.next ) {
              cur = scope.q[cur.next];
              if ( cur ) {
                cur.list = [];
                cur.selected = angular.copy( cur.all );
              }
            }
          }

          function queryChoice ( type ) {
            switch ( type ) {
              case 'platform':
                var siteListPromise;
                if ( opts.byRole ) {
                  siteListPromise = Position.getSiteListByRole({
                    'adPlatformId': scope.q[type].selected.id
                  });
                } else {
                  siteListPromise = Position.getSiteList4Radio({
                    'productId': scope.q[type].selected.id
                  });
                }
                siteListPromise.success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.site.list = response.data || [];
                    scope.q.site.list.unshift( scope.q.site.all );
                  }
                });
                break;
              case 'site':
                var channelListPromise;
                if ( opts.byRole ) {
                  channelListPromise = Position.getChannelListByRole({
                    'productId' : scope.q['platform'].selected.id,
                    'siteId' : scope.q[type].selected.id
                  });
                } else {
                  channelListPromise = Position.getChannelList({
                    'productId' : scope.q['platform'].selected.id,
                    'siteId' : scope.q[type].selected.id
                  });
                }
                channelListPromise.success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.channel.list = response.data || [];
                    scope.q.channel.list.unshift( scope.q.channel.all );
                  }
                });
                break;
              case 'channel':
                var areaListPromise;
                if ( opts.byRole ) {
                  areaListPromise = Position.getParentByRole({
                    'parentId': scope.q[type].selected.id
                  });
                } else {
                  areaListPromise = Position.getAreaList({
                    'parentId': scope.q[type].selected.id
                  });
                }
                areaListPromise.success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.area.list = response.data || [];
                    scope.q.area.list.unshift( scope.q.area.all );
                  }
                });
                break;
              case 'area':
                var positionListPromise;
                if ( opts.byRole ) {
                  positionListPromise = Position.getParentByRole({
                    'parentId': scope.q[type].selected.id
                  });
                } else {
                  positionListPromise = Position.getPositionList({
                    'parentId': scope.q[type].selected.id
                  });
                }
                positionListPromise.success( function ( response ) {
                  if ( response.code === 200 ) {
                    if ( scope.q.position ) {
                      scope.q.position.list = response.data || [];
                      scope.q.position.list.unshift( scope.q.position.all );
                    }
                  }
                });
                break;
              default:
            }
          }

          function packSelected () {
            var selectedObj = {};
            for ( var type in scope.q ) {
              selectedObj[type] = angular.copy(scope.q[type].selected);
            }
            return selectedObj;
          }
        }
      };
    }
  ]);
  // body...
  //
  //
  //
});