/**
 * [description] require `app`
 */
define([
  'app',
  '../_common/ytCommon',
  '../_directives/ytPositionSelect',
  './CtrlResourcePositionPropertyModal',
  './CtrlResourcePositionPictureModal',
  './CtrlResourcePositionCodeModal',
  '../_filters/PositionFilter',
  '../_services/Select2Suggest',
  'anuglar-ui-select2',
  './CtrlResourcePositionImportModal',
  './CtrlResourcePositionNameModal',
  './ModalSchedule',
  '../_directives/ytPopoverConfirm'
], function ( app ) {

//register controller
app.registerController('CtrlResourcePositionList', [
  '$scope',
  '$log',
  'PageSet',
  'Position',
  '$modal',
  'ModalSchedule',
  '$window',
  'Modal',
  'Select2Suggest',
  'APP_CONTEXT',
  '$state',
  '$filter',
  'STATIC_DIR',
  function ( $scope, $log, PageSet, Position, $modal, ModalSchedule, $window, Modal, Select2Suggest, APP_CONTEXT, $state, $filter, STATIC_DIR ) {
    // CONTROLLER BODY
    PageSet.set( {'siteName': 'resourcePositionList', 'activeIndex': 2} );
    $scope.positionOpts = { byRole: true };
    var translateFilter = $filter('translate');
    var constantsKey = {

    };
    $scope.toggleQueryMore = function () {
      $scope.queryMoreShow = !$scope.queryMoreShow;
    };
    // $scope.queryMoreShow = true;
    $scope.t = {};
    $scope.q = {};
    $scope.q.positionNumberOption = Select2Suggest.positionNumSuggest();
    //get platform list at startup
    Position.getPlatformListByRole().success(function ( response ) {
      if ( response.code === 200 ) {
        $scope.t.adPlatformList = response.data;
      }
    });

    var curPlatformId;
    var curSiteId;
    //选择了平台
    $scope.changePlatform = function ( platform ) {
      curPlatformId = platform.id;
      Position.getSiteListByRole({
        'adPlatformId': platform.id
      }).success(function ( response ) {
        if ( response.code === 200 ) {
          $scope.t.adSiteList = response.data;
          try {
            if ( !curSiteId ) {
              $scope.changeSite( $scope.t.adSiteList[0] );
            } else {
              $scope.changeSite( {
                id: curSiteId
              } );
              curSiteId = undefined;
            }
          } catch ( e ) {
            $log.error( e );
          }
        }
      });
    };
    //选择了站点
    $scope.changeSite = function ( site ) {
      if ( !site ) {
        return false;
      }
      $scope.siteActiveId = site.id;
      Position.getPositionTreeList({
        adPlatformId: curPlatformId,
        siteId: site.id
      }).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.contentList = resolveAuthority( response.data.content );
        }
      });
    };
    //查询
    $scope.btnQuery = function () {
      query.resetPageNumber();
      query.doQuery();
    };
    //当前页变化监听，相当于点击了分页
    $scope.$watch( "pager.pageNumber", function( newValue, oldValue ){
      if ( newValue && oldValue && newValue !== oldValue ) {
        query.doQuery();
      }
    });
    //更改分页大小
    $scope.setPageSize = function (size) {
      $scope.pager.pageSize = size;
      query.resetPageNumber();
      query.doQuery();
    };
    //qquery相关
    var query = {
      initQuery: function () {
        $scope.pager = {
          pageNumber: 1,
          pageSize: 10,
          pageSizeSlots: [10,20,30,50],
          totalCount: 0
        };
      },
      doQuery: function () {
        Position.getPositionQueryList( this.getQueryParams() ).success(function ( response ) {
          if ( response.code === 200 ) {
            $scope.isQuery = true;
            $scope.contentList = resolveAuthority( response.data.content );
            $scope.pager.totalCount = response.data.totalCount;
            $scope.pager.totalPages = response.data.totalPages;
          }
        });
      },
      getQueryParams: function () {
        var postData = {};
        postData.positionName = $scope.q.positionName;
        if ( $scope.q.positionNumber ) {
          postData.positionNumber = $scope.q.positionNumber.data;
        }
        postData.areaRequired = $scope.q.areaRequired;
        postData.adPlatformId = $scope.positionSelected.platform.id;
        postData.siteId = $scope.positionSelected.site.id;
        postData.channelId = $scope.positionSelected.channel.id;
        postData.areaId = $scope.positionSelected.area.id;
        postData.positionId = $scope.positionSelected.position.id;
        postData.pageSize = $scope.pager.pageSize;
        postData.pageNumber = $scope.pager.pageNumber;
        return postData;
      },
      resetPageNumber: function () {
        $scope.pager.pageNumber = 1;
      }
    };
    query.initQuery();
    //返回列表
    $scope.btnBackToList = function () {
      $scope.isQuery = false;
    };
    //管理所有的按钮点击
    $scope.btnClick = function ( typeString, item ) {
      btnClickHandler[ typeString ]( item );
    };
    var btnClickHandler = {
      viewOccupation: function ( item ) {
        // '查看投放情况'
        ModalSchedule.show({
          readOnly: true,
          positionInfo : {
            positionId: item.id,
            positionName: item.i18nExtraValue,
            salesAmount: item.salesAmount,
            rotationType: ( item.rotationType === 'yes' ? 1 : 0 )
          }
        });
      },
      // modifyOccupation: function ( item ) {
      //   // '修改投放情况'
      //   ScheduleModal.show({
      //     positionInfo : {
      //       positionId: item.id,
      //       // positionId: 382,
      //       advertiser: item.advertiser,
      //       productName: item.adPlatformName,
      //       siteName: item.siteName,
      //       channelName: item.channelName,
      //       areaName: item.areaName,
      //       positionName: item.name,
      //       salesAmount: item.salesAmount,
      //       rotationType: ( item.rotationType === 'yes' ? 1 : 0 )
      //     }
      //   }, function () {
      //     Modal.success({
      //       content: $filter('translate')('SCHEDULE_SUBMIT_SUCCESS'),
      //       timeOut: 3000
      //     });
      //   });
      // },
      viewImage: function ( item ) {
        // '查看图片'
        // TODO:需要图片链接
        $window.open( APP_CONTEXT + 'position/viewGuide/' + item.id );
      },
      modifyName: function ( item ) {
        //修改名称
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/modifyName.tpl.html',
          controller: 'CtrlResourcePositionNameModal',
          resolve: {
            opts: function () {
              return { rowData: item };
            }
          }
        });
        modalInstance.result.then( function () {
          refreshList();
        });
      },
      viewProperty: function ( item ) {
        // '查看属性'
        // 需要读取属性的信息
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addProperty.tpl.html',
          controller: 'CtrlResourcePositionPropertyModal',
          windowClass: 'resource-position-property-modal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'view'
              };
            }
          }
        });
      },
      viewCode: function (item) {
        // '查看代码'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/viewCode.tpl.html',
          controller: 'CtrlResourcePositionCodeModal',
          windowClass: 'resource-position-code-modal',
          resolve: {
            opts: function () {
              return {
                rowData: item
              };
            }
          }
        });
      },
      batchModifyProperty: function ( item ) {
        // '批量修改属性'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addProperty.tpl.html',
          controller: 'CtrlResourcePositionPropertyModal',
          windowClass: 'resource-position-property-modal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'batch'
              };
            }
          }
        });
        modalInstance.result.then( function () {
          refreshList();
        });
      },
      addProperty: function ( item ) {
        // '添加属性'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addProperty.tpl.html',
          controller: 'CtrlResourcePositionPropertyModal',
          windowClass: 'resource-position-property-modal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'add'
              };
            }
          }
        });
        modalInstance.result.then( function () {
          refreshList();
        });
      },
      modifyProperty: function ( item ) {
        // '修改属性'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addProperty.tpl.html',
          controller: 'CtrlResourcePositionPropertyModal',
          windowClass: 'resource-position-property-modal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'edit'
              };
            }
          }
        });
        modalInstance.result.then( function () {
          refreshList();
        });
      },
      addImage: function ( item ) {
        // '添加图片'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addPicture.tpl.html',
          controller: 'CtrlResourcePositionPictureModal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'add'
              };
            }
          }
        });
        modalInstance.result.then( function () {
          refreshList();
        });
      },
      modifyImage: function ( item ) {
        // '修改图片'
        var modalInstance = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/addPicture.tpl.html',
          controller: 'CtrlResourcePositionPictureModal',
          resolve: {
            opts: function () {
              return {
                rowData: item,
                type: 'edit'
              };
            }
          }
        });
      },
      activatePosition: function ( item ) {
        // 启用
        $log.info( 'activating' );
        var modal = $modal.open({
          templateUrl: STATIC_DIR + 'app/resourcePosition/activate.tpl.html',
          controller: ['$scope', '$modalInstance', function ( $scope, $modalInstance ) {
            $scope.ok = function () {
              $modalInstance.close('ok');
            };
            $scope.cancel = function () {
              $modalInstance.dismiss('cancel');
            };
          }]
        });
        modal.result.then( function ( result ) {
          if ( result === 'ok' ) {
            if ( item.type !== 'position' ) {
              Position.hasPosition( { id: item.id } ).success( function ( response ) {
                if ( response.code === 200 ) {
                  if ( !response.data ) {
                    Modal.alert( { content: translateFilter( 'RESOURCE_POSITION_LACK_POSITION' ) } );
                  } else {
                    enablePosition( item );
                  }
                }
              });
            } else {
              enablePosition( item );
            }
          }
        });
      },
      shutdownPosition: function ( item ) {
        // 禁用
        $log.info( 'shutting down' );
        Position.queryUsedAmount( { id: item.id } ).success( function ( response ) {
          if ( response.code === 200 ) {
            var modalInstance = $modal.open({
              templateUrl: STATIC_DIR + 'app/resourcePosition/shutdown.tpl.html',
              controller: ['$scope', '$modalInstance', 'opts', '$filter', function ($scope, $modalInstance, opts, $filter) {
                $scope.usedCount = opts.usedCount;
                $scope.usedCountHint = $filter('translate')('POSITION_SHUTDOWN_HINT', { usedCount: $scope.usedCount } );
                $scope.ok = function () {
                  Position.disablePosition( { id: opts.rowData.id } ).success( function ( response ) {
                    if ( response.code === 200 ) {
                      $modalInstance.close('ok');
                    }
                  });
                };
                $scope.cancel = function () {
                  $modalInstance.dismiss('cancel');
                };
              }],
              resolve: {
                opts: function () {
                  return {
                    rowData: item,
                    usedCount: response.data
                  };
                }
              }
            });
            modalInstance.result.then( function ( result ) {
              if ( result === 'ok' ) {
                var shutdownName;
                if ( item.type === 'position' ) {
                  shutdownName = item.name;
                } else {
                  shutdownName = item[ item.type + 'Name' ];
                }
                Modal.success( { content: translateFilter('RESOURCE_POSITION_SHUTDOWN_SUCCESS', { shutdownName: shutdownName } ) });
                refreshList();
              }
            });
          }
        });
      }
    };
    $scope.btnDownloadTemplate = function () {
      $window.open( APP_CONTEXT + 'position/downloadImportTemplate' );
    };
    $scope.btnImport = function () {
      var modalInstance = $modal.open({
        templateUrl: STATIC_DIR + 'app/resourcePosition/import.tpl.html',
        controller: 'CtrlResourcePositionImportModal'
      });
      modalInstance.result.then( function ( responseData ) {
        if ( responseData ) {
          Modal.success( { content: translateFilter( 'RESOURCE_POSITION_IMPORT_SUCCESS' ) }, function () {
            if ( responseData.adPlatformId && responseData.siteId ) {
              changePlatformAndSite( responseData.adPlatformId, responseData.siteId );
            }
          } );
        }
      });
    };

    function changePlatformAndSite ( platformId, siteId ) {
      $scope.isQuery = false;
      curPlatformId = platformId;
      curSiteId = siteId;
      for (var i = 0; i < $scope.t.adPlatformList.length; i++) {
        if ( $scope.t.adPlatformList[i].id === platformId ){
          $scope.t.adPlatformList[i].active = true;
          break;
        }
      }
    }
    //重新查询
    var refreshList = function () {
      if ( !$scope.isQuery ) {
        $scope.changeSite( {
          id: $scope.siteActiveId
        });
      } else {
        query.doQuery();
      }
    };
    //添加资源位
    $scope.btnNavAdd = function () {
      $state.go('resourcePositionAdd', {
        platformId: curPlatformId,
        siteId: $scope.siteActiveId
      });
    };
    function enablePosition ( item, cbfn ) {
      Position.enablePosition( { id: item.id } ).success( function ( response ) {
        if ( response.code === 200 ) {
          var activatedName;
          if ( item.type === 'position' ) {
            activatedName = item.name;
          } else {
            activatedName = item[ item.type + 'Name' ];
          }
          Modal.success( { content: translateFilter('RESOURCE_POSITION_ACTIVATE_SUCCESS', { activatedName: activatedName } ) }, cbfn );
          refreshList();
        } else if ( response.code === 204 ) {
          Modal.showError( response.errorList );
        }
      });
    }
    /**
     * 处理按钮的权限，如果没有按钮权限，直接从按钮中清除
     * modifyButtons
     * queryButtons
     */
    function resolveAuthority ( resultList ) {
      var buttonCodeList, i, j, resolvedName, item;
      if ( window.GCRM && window.GCRM.rights && window.GCRM.rights.menuCodeList && window.GCRM.rights.buttonCodeList && window.GCRM.rights.menuCodeList.length && window.GCRM.rights.buttonCodeList.length ) {
        buttonCodeList = window.GCRM.rights.buttonCodeList;
      } else {
        buttonCodeList = [
          "btn_pos_list_import",
          "btn_pos_list_add",
          "btn_pos_list_name_mod",
          "btn_pos_list_property_add",
          "btn_pos_list_launch_mod",
          "btn_pos_list_image_mod_add",
          "btn_pos_list_enable_disable",
          "btn_pos_list_property_view",
          "btn_pos_list_launch_view",
          "btn_pos_list_image_view"
        ];
      }
      for ( i = 0; i < resultList.length; i++) {
        item = resultList[i];
        //清除修改按钮
        for ( j = 0; item.modifyButtons && j < item.modifyButtons.length; j++) {
          switch ( item.modifyButtons[j] ) {
            case 'batchModifyProperty':
              resolvedName = 'btn_pos_list_property_add';
              break;
            // case 'modifyOccupation':
            //   resolvedName = 'btn_pos_list_launch_mod';
            //   break;
            case 'addImage':
              resolvedName = 'btn_pos_list_image_mod_add';
              break;
            case 'modifyImage':
              resolvedName = 'btn_pos_list_image_mod_add';
              break;
            case 'modifyName':
              resolvedName = 'btn_pos_list_name_mod';
              break;
            case 'addProperty':
              resolvedName = 'btn_pos_list_property_add';
              break;
            case 'modifyProperty':
              resolvedName = 'btn_pos_list_property_add';
              break;
          }
          if ( buttonCodeList.indexOf( resolvedName ) === -1 ) {
            item.modifyButtons.splice( j, 1 );
            j = j - 1;
          }
        }
        //清除查询按钮s
        for ( j = 0; item.queryButtons && j < item.queryButtons.length; j++) {
          switch ( item.queryButtons[j] ) {
            case 'viewImage':
              resolvedName = 'btn_pos_list_image_view';
              break;
            case 'viewProperty':
              resolvedName = 'btn_pos_list_property_view';
              break;
            case 'viewOccupation':
              resolvedName = 'btn_pos_list_launch_view';
              break;
          }
          if ( buttonCodeList.indexOf( resolvedName ) === -1 ) {
            item.queryButtons.splice( j, 1 );
            j = j - 1;
          }
        }
      }
      return resultList;
    }
}]);

//register button filter
app.registerFilter('ResourcePositionListButtonFilter', function () {
  var buttonType = {
    'viewProperty': 'RESOURCE_POSITION_BUTTON_VIEWPROPERTY',
    'viewOccupation': 'RESOURCE_POSITION_BUTTON_VIEWOCCUPATION',
    'viewImage': 'RESOURCE_POSITION_BUTTON_VIEWIMAGE',
    'modifyName': 'RESOURCE_POSITION_BUTTON_MODIFYNAME',
    'batchModifyProperty': 'RESOURCE_POSITION_BUTTON_BATCHMODIFYPROPERTY',
    'addProperty': 'RESOURCE_POSITION_BUTTON_ADDPROPERTY',
    'modifyProperty': 'RESOURCE_POSITION_BUTTON_MODIFYPROPERTY',
    'addImage': 'RESOURCE_POSITION_BUTTON_ADDIMAGE',
    'modifyImage': 'RESOURCE_POSITION_BUTTON_MODIFYIMAGE',
    // 'modifyOccupation': 'RESOURCE_POSITION_BUTTON_MODIFYOCCUPATION',
    'viewCode': 'RESOURCE_POSITION_BUTTON_VIEWCODE'
  };
  return function ( input ) {
    if ( input in buttonType ) {
      return buttonType[ input ];
    }
    return '';
  };
});

//end of define
});