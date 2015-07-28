/**
 * PersonalInfo 个人相关信息(Angular Service)
 */
define(['app'],
function(app) {
  app.registerFactory('PersonalInfo', ['$http', 'APP_CONTEXT',
  function( $http, APP_CONTEXT ) {
    return {
      /**
       * [需要显示的模块]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getDisplay: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findModule'
          // url: '/data/homeDisplay.json',
        });
      },
      /**
       * [我的提交-客户]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getSubmitInfoCustomer: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findCustomer'
          // url: '/data/submitInfoCustomer.json',
        });
      },
      /**
       * [我的提交-广告方案]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getSubmitInfoAdSolution: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findAdSolution'
          // url: '/data/submitInfoAdsolution.json',
        });
      },
      /**
       * [我的提交-标杆价]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getSubmitInfoQuotation: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findQuotation'
          // url: '/data/submitInfoQuotation.json',
        });
      },
      /**
       * [我的提交-物料]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getSubmitInfoMaterial: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findMaterialApplyContent'
          // url: '/data/submitInfoMaterial.json',
        });
      },
      /**
       * [运营-客户]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getOperationInfoCustomer: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findCustomerOperation'
          // url: '/data/operationInfoCustomer.json',
        });
      },
      /**
       * [运营-方案与合同]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getOperationInfoAd: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findSolutionOperation'
          // url: '/data/operationInfoAd.json',
        });
      },
      /**
       * [运营-资源位占用站点]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getOperationInfoPlatform: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findPlatformByCurrUser'
          // url: '/data/operationInfoPlatform.json',
        });
      },
      /**
       * [运营-资源位占用情况]
       * @param  {[type]} paramObj = {platformId: 124} [description]
       * @return {[type]}          [description]
       */
      getOperationInfoOccupation: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findSiteOperation/' + paramObj.platformId
          // url: '/data/operationInfoOccupation.json'
        });
      },
      /**
       * [模块统计信息]
       * @param  {[type]} paramObj [description]
       * @return {[type]}          [description]
       */
      getModuleCount: function (paramObj) {
        return $http({
          method: 'get',
          url: APP_CONTEXT + 'personal/findModuleCount/'
          // url: '/data/moduleCountInfo.json'
        });
      }
    };
  }]);
});