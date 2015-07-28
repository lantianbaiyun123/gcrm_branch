define(['app'], function ( app ) {
/**
 * USAGE: <div yt-position-select position-selected="positionSelected"></div>
 */

  app.registerDirective('ytPositionSelect', [
    '$log',
    'Position',
    function ( $log, Position ) {
      return {
        scope: {
          positionSelected: '=',
          ytPositionSelect: '='
        },
        restrict: 'A',
        templateUrl: 'app/_directives/ytPositionSelect.tpl.html',
        replace: true,
        link: function ( scope, element, attrs ) {
          var optsDefault = {
            level: 5
          };
          var opts = angular.extend(optsDefault, scope.ytPositionSelect);

          //get platform list at startup
          Position.getPlatformList4Radio().success(function ( response ) {
            if ( response.code === 200 ) {
              var temp = angular.copy( response.data );
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
            $log.info(scope.positionSelected);

          };
          //清空所有的下级
          function clearChoice ( type ) {
            var cur = scope.q[type];
            while ( cur.next ) {
              cur = scope.q[cur.next];
              cur.list = [];
              cur.selected = angular.copy( cur.all );
            }
          }
          function queryChoice ( type ) {
            switch ( type ) {
              case 'platform':
                Position.getSiteList4Radio({
                  'productId': scope.q[type].selected.id
                }).success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.site.list = response.data;
                    scope.q.site.list.unshift( scope.q.site.all );
                  }
                });
                break;
              case 'site':
                Position.getChannelList({
                  'productId' : scope.q['platform'].selected.id,
                  'siteId' : scope.q[type].selected.id
                }).success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.channel.list = response.data;
                    scope.q.channel.list.unshift( scope.q.channel.all );
                  }
                });
                break;
              case 'channel':
                Position.getAreaList({
                  'parentId': scope.q[type].selected.id
                }).success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.area.list = response.data;
                    scope.q.area.list.unshift( scope.q.area.all );
                  }
                });
                break;
              case 'area':
                Position.getPositionList({
                  'parentId': scope.q[type].selected.id
                }).success( function ( response ) {
                  if ( response.code === 200 ) {
                    scope.q.position.list = response.data;
                    scope.q.position.list.unshift( scope.q.position.all );
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
          scope.q = {
            platform: {
              all: {
                i18nName: '全部投放平台',
                id: null
              },
              list: [],
              selected: {
                i18nName: '全部投放平台',
                id: null
              },
              next: 'site'
            },
            site: {
              all: {
                i18nName: '全部投放站点',
                id: null
              },
              list: [],
              selected: {
                i18nName: '全部投放站点',
                id: null
              },
              next: 'channel',
              pre: 'platform'
            },
            channel: {
              all: {
                i18nName: '全部频道',
                id: null
              },
              list: [],
              selected: {
                i18nName: '全部频道',
                id: null
              },
              next: 'area',
              pre: 'site'
            },
            area: {
              all: {
                i18nName: '全部区域',
                id: null
              },
              list: [],
              selected: {
                i18nName: '全部区域',
                id: null
              },
              next: 'position',
              pre: 'channel'
            },
            position: {
              all: {
                i18nName: '全部位置',
                id: null
              },
              list: [],
              selected: {
                i18nName: '全部位置',
                id: null
              },
              pre: 'area'
            }
          };
        }
      };
    }
  ]);
  // body...
  //
  //
  //
});