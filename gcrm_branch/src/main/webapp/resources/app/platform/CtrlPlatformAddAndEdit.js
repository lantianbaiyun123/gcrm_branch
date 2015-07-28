define(['app'], function (app) {
/**
 * [description] 添加平台、编辑平台的浮层控制
 */
app.registerController('CtrlPlatformAddAndEdit', [
  '$scope',
  'Position',
  'opts',
  '$modalInstance',
  '$parse',
  'Modal',
  '$window',
  '$filter',
  function ($scope, Position, opts, $modalInstance, $parse, Modal, $window, $filter ) {
    var translateFilter = $filter('translate');
    $scope.input = {};
    $scope.radio = {};
    //opts.type 类型两种 edit、add
    if ( opts.type === 'edit' ) {
      Position.get( { id: opts.platformId } ).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.sites = response.data.sites;
          $scope.platformId = response.data.id;
          $scope.input.cnName = response.data.cnName;
          $scope.input.enName = response.data.enName;
          $scope.radio.businessType = response.data.businessType;
        }
      });
      $scope.showPS = true;//show extra ps info when editing
    } else {
      //fetch the site list
      Position.get({}).success( function ( response ) {
        if ( response.code === 200 ) {
          $scope.sites = response.data.sites;
          for (var i = 0; i < $scope.sites.length; i++) {
            $scope.sites[i].allowCanceled = true;
          }
        }
      });
    }
    var validationList = [{
      key: 'input.cnName',
      message: translateFilter('PLACEHOLDER_CN_NAME')
    }, {
      key: 'input.enName',
      message: translateFilter('PLACEHOLDER_EN_NAME')
    }, {
      key: 'sites',
      message: translateFilter('PLATFORM_ADD_CHECK_SITE'),
      validateFunction: function ( value ) {
        var flag = false;
        for (var i = 0; i < value.length && !flag; i++) {
          if ( value[i].checked ) {
            flag = true;
          }
        }
        return flag;
      },
      clear: function (argument) {
        // body...
      }
    }];
    
    $scope.businessTypeToggle = function () {
      $scope.alerts = [];
    };
    $scope.alerts = [];
    //save Site modification
    $scope.ok = function () {
      $scope.alerts = [];
      if ( !$scope.alerts || !$scope.alerts.length ) {
        $scope.stateValidating = true;
        // $scope.alerts = [];//clear previous alert
        
        /* 如果业务类型为销售，不需要校验*/
        // if ( $scope.businessType === 'sales' ) {
        //   save();
        // } else {
          var validatedResult = validate( validationList, $scope );
          var flag = true;
          for (var i = 0; i < validationList.length && flag; i++) {
            if ( !validatedResult[i] ) {
              if ( $scope.radio.businessType === 'sales' && validationList[i].key === 'sites' ) {
                //如果业务类型为销售，不需要校验sites
                continue;
              }
              $scope.alerts = $scope.alerts || [];
              $scope.alerts.push({
                type: 'danger',
                msg: validationList[i].message,
                key: validationList[i].key
              });
              // $scope.alerts = [ { type: 'danger', msg: validationList[i].message } ];
              // flag = false;
            } else {
              clearAlert( validationList[i].key );
            }
          }
          //校验通过
          if ( !$scope.alerts.length ) {
            save();
          }
        // }
      }
    };
    //校验通过之后的保存
    function save () {
      var postData = {};
      if ( opts.type === 'edit' ) {
        postData.id = $scope.platformId;
      }
      postData.businessType = $scope.radio.businessType;
      
      postData.i18nData =  [{
        "locale": "zh_CN",
        "value": $scope.input.cnName
      },
      {
        "locale": "en_US",
        "value": $scope.input.enName
      }];
      if ( $scope.radio.businessType === 'sales' ) {
        //如果类型是销售，则固定传siteId 为0
        postData.sites = [{
          id: 0
        }];
      } else {
        postData.sites = [];
        for (var i = 0; i < $scope.sites.length; i++) {
          if ( $scope.sites[i].checked ) {
            postData.sites.push( { id: $scope.sites[i].id });
          }
        }
      }
      Position.savePlatform( postData ).success(function ( response ) {
        if ( response.code === 200 ) {
          if ( opts.type === 'edit' ) {
            $modalInstance.close();
            Modal.success( { content: translateFilter( 'PLATFORM_LIST_SAVE_SUCCESS' ) }, function () {
              $window.location.reload();
            });
          } else {
            $modalInstance.close();
            Modal.success( { content: translateFilter( 'PLATFORM_LIST_ADD_SUCCESS' ) }, function () {
              $window.location.reload();
            });
          }
        }
      });
    }
    
    function validate ( list, scope ) {
      var retArr = [],
          flag;
      for (var i = 0; i < list.length; i++) {
        flag = true;
        var item = list[i];
        if ( !item.validateFunction ) {
          $parse(item.key)(scope) || ( flag = false );
        } else {
          flag = item.validateFunction( scope[item.key] );
        }
        retArr.push( flag );
      }
      return retArr;
    }

    $scope.closeAlert = function(index) {
      $scope.alerts.splice(index, 1);
    };

    //监听中文名输入框变化
    $scope.changeCnName = function () {
      var cnNameKey = 'input.cnName';
      if ( $scope.stateValidating ) {
        if ( $scope.input.cnName ) {
          clearAlert( cnNameKey );
        } else {
          pushAlert( cnNameKey );
        }
      }
    };
    //监听英文名输入框变化
    $scope.changeEnName = function () {
      var enNameKey = 'input.enName';
      if ( $scope.stateValidating ) {
        if ( $scope.input.enName ) {
          clearAlert( enNameKey );
        } else {
          pushAlert( enNameKey );
        }
      }
    };
    var clearAlert = function ( key ) {
      for (var i = 0; i < $scope.alerts.length; i++) {
        if ( $scope.alerts[i].key === key ) {
          $scope.alerts.splice( i, 1 );
          break;
        }
      }
    };
    var pushAlert = function ( key ) {
      for (var i = 0; i < validationList.length; i++) {
        if ( validationList[i].key === key ) {
          $scope.alerts.push({
            type: 'danger',
            msg: validationList[i].message,
            key: validationList[i].key
          });
          break;
        }
      }
    };
    //toggle checkall
    $scope.toggle = function () {
      if ( !$scope.sites ) {
        return;
      }
      var i;
      if ( $scope.check.all ) {
        for ( i = 0; i < $scope.sites.length; i++ ) {
          if ( $scope.sites[i].allowCanceled ) {
            $scope.sites[i].checked = true;
          }
        }
      } else {
        for ( i = 0; i < $scope.sites.length; i++ ) {
          if ( $scope.sites[i].allowCanceled ) {
            $scope.sites[i].checked = false;
          }
        }
      }
      updateSiteAlert();
    };
    function updateSiteAlert () {
      if ( $scope.stateValidating ) {
        var flag = false,
            siteCheckKey = 'sites';
        for (var i = 0; i < $scope.sites.length; i++) {
          if ( $scope.sites[i].checked ) {
            flag = true;
            break;
          }
        }
        if ( flag ) {
          clearAlert( siteCheckKey );
        } else {
          pushAlert( siteCheckKey );
        }
      }
    }
    $scope.check = {};//make it work and steak to the parent model
    //check clicked,update checkall
    $scope.checkSingle = function ( site ) {
      site.checked = !site.checked;
      var flag = true;
      for (var i = 0; i < $scope.sites.length && flag; i++) {
        if ( $scope.sites[i].allowCanceled && $scope.sites[i].checked === false ) {
          flag = false;
        }
      }
      $scope.check.all = flag;
      updateSiteAlert();
    };
    //dismiss modal
    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
}]);


});