define(['app'], function (app) {
  app.registerFactory('Position',[
    '$http',
    'APP_CONTEXT',
    function ($http, APP_CONTEXT) {
      return {
        getSiteList: function ( paramObj ) {
          //{productId: 1}
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adsolution/content/querySiteByProductId',
              params: paramObj
          });
        },
        getChannelList: function ( paramObj ) {
          //{productId: 1,siteId: 11}
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adsolution/content/queryPositionBySiteId',
              params: paramObj
          });
        },
        getAreaList: function ( paramObj ) {
          //{parentId: 1}
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adsolution/content/queryPositionByParentId',
              params: paramObj
          });
        },
        getPositionList: function ( paramObj ) {
          //{parentId: 1}
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adsolution/content/queryPositionByParentId',
              params: paramObj
          });
        },
        getPlatformList: function ( paramObj ) {
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adPlatform/query',
              // url: '/data/platform.json',
              params: paramObj
          });
        },
        get: function (paramObj) {
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adPlatform/preSave',
              // url: '/data/platformAdd.json',
              params: paramObj
          });
        },
        shutdownPlatform: function ( paramObj ) {
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adPlatform/disable/' + paramObj.id
              // url: '/data/response.json'
          });
        },
        startUpPlatform: function ( paramObj ) {
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adPlatform/enable/' + paramObj.id
          });
        },
        getPlatformUsedCount: function ( paramObj ) {
          //{id : platformId}
          return $http({
              method: 'get',
              url: APP_CONTEXT + 'adPlatform/queryUsedCount/' + paramObj.id
              // url: '/data/platformUsedCount.json'
          });
        },
        savePlatform: function ( paramObj ) {
          return $http({
              method: 'post',
              url: APP_CONTEXT + 'adPlatform/save',
              // method: 'get',
              // url: '/data/response.json',
              data : JSON.stringify( paramObj )
          });
        },
        getPositionTree: function ( paramObj ) {
          return $http({
              method: 'post',
              url: APP_CONTEXT + 'position/preSavePosition',
              data : angular.toJson( paramObj )
              // method: 'get',
              // url: '/data/positionTree.json'
          });
        },
        getPlatformList4Radio: function () {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/queryProduct'
          });
        },
        getSiteList4Radio: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adsolution/content/querySiteByProductId',
            params: paramObj
          });
        },
        savePosition: function ( paramObj ) {
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/savePosition',
            data : angular.toJson( paramObj )
          });
        },
        getPositionTreeList: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/queryTree',
            // url: '/data/positionTreeList.json',
            params: paramObj
          });
        },
        getPositionQueryList: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/query',
            // url: '/data/positionTreeList.json',
            params: paramObj
          });
        },
        queryPosition: function ( paramObj ) {
          // 查询位置 {id: position.id}
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/queryPosition/' + paramObj.id
            // url: '/data/position.json'
          });
        },
        updatePositionProperty: function ( paramObj ) {
          // 修改位置
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/updatePositionProperty',
            data : angular.toJson( paramObj )
          });
        },
        updateGuideFileProperty: function ( paramObj ) {
          // 修改图片{id: positionid,guideFileName,guideFileDownloadName,guideUrl}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/updateGuideFileProperty',
            data : angular.toJson( paramObj )
          });
        },
        disablePosition: function ( paramObj ) {
          // 禁用{id: positionid}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/disable/' + paramObj.id,
            data : angular.toJson( paramObj )
          });
        },
        queryUsedAmount: function ( paramObj ) {
          // 禁用{id: positionid}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/queryUsedAmount/' + paramObj.id,
            data : angular.toJson( paramObj )
          });
        },
        enablePosition: function ( paramObj ) {
          // 启用{id: positionid}
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/enable/' + paramObj.id,
            data : angular.toJson( paramObj )
          });
        },
        positionSuggest: function ( paramObj ) {
          return $http({
            method: "get",
            url: APP_CONTEXT + 'position/positionNumSuggest',
            params: paramObj
          });
        },
        updatePositionName: function ( paramObj ) {
          // 修改位置的名称{id: positionid，"i18nData":{  "cnName":"test","enName":"test" } }
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/updatePositionName',
            data : angular.toJson( paramObj )
          });
        },
        hasPosition: function ( paramObj ) {
          /**
           * 查频道或区域下是否有位置
           * @param id
           * 返回data:true 或false
           */
          return $http({
            method: 'post',
            url: APP_CONTEXT + 'position/hasPositions/' + paramObj.id
          });
        },
        getPlatformListByRole: function () {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adPlatform/queryAdPlatform'
          });
        },
        getSiteListByRole: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'adPlatform/querySiteByAdPlatformId',
            params: paramObj
          });
        },
        getChannelListByRole: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/queryPositionBySiteId',
            params: paramObj
          });
        },
        getParentByRole: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/queryPositionByParentId',
            params: paramObj
          });
        },
        getPlatformListByBusinessType: function ( paramObj ) {
          //businessType: BusinessType
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'customer/initAdvertisingPlatform',
            // url: '/data/' + paramObj.type + 'customerPlatform.json',
            params: paramObj
          });
        },
        hasProperty: function ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'position/hasProperty/' + paramObj.id
          });
        },
        getQuoteAllPlatform: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT+'quote/queryPlatform'
            // url: '/data/queryplatform.json'
          });
        },
        /**
         * 根据投放平台id获取"站点/区域"列表，
         * @param paramObj: {platformId: 1}
         * @return HttpPromise
         */
        getQuoteSiteArea: function (paramObj) {
          return $http({
            method: 'get',
            url: APP_CONTEXT+'quote/siteSuggest',
            params: paramObj
          });
        }
      };
    }
  ]);
});