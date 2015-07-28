// Anything required here wil by default be combined/minified by r.js
// if you use it.
define(['app', 'routeDefs', 'app-http-interceptor', './_common/ytCommon'], function (app, translationsCN, translationsEN) {
  app.config(['$translateProvider', 'APP_CONTEXT', 'TRANSLATION_CN', 'TRANSLATION_EN',
    function($translateProvider, APP_CONTEXT, TRANSLATION_CN, TRANSLATION_EN) {
      $translateProvider.translations('zh-CN', TRANSLATION_CN);
      $translateProvider.translations('en-US', TRANSLATION_EN);
      var navigatorLanguage = window.navigator.language || window.navigator.browserLanguage;
      if (navigatorLanguage !== 'zh-CN') {
        navigatorLanguage = 'en-US';
      }
      $translateProvider.preferredLanguage(navigatorLanguage);
      $translateProvider.useLocalStorage();
    }
  ]);

  app.config(['routeDefsProvider', '$httpProvider',
    function (routeDefsProvider, $httpProvider) {

      // in large applications, you don't want to clutter up app.config
      // with routing particulars.  You probably have enough going on here.
      // Use a service provider to manage your routing.

      //post header setting to make it work
      $httpProvider.defaults.headers.post = {
        'Content-Type': 'application/json; charset=UTF-8',
        'X-Requested-With': 'XMLHttpRequest'
      };

      //put header setting to make it work
      $httpProvider.defaults.headers.put = {
        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
        'X-Requested-With': 'XMLHttpRequest'
      };

      //disable IE ajax request caching
      $httpProvider.defaults.headers.get = $httpProvider.defaults.headers.get || {};
      $httpProvider.defaults.headers.get['X-Requested-With'] = 'XMLHttpRequest';
      $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    }
  ]);

  app.run([
    '$couchPotato', '$state', '$stateParams', '$rootScope',
    function($couchPotato, $state, $stateParams, $rootScope) {

      // by assigning the couchPotato service to the lazy property, we
      // the register functions will know to run-time-register components
      // instead of config-time-registering them.
      app.lazy = $couchPotato;

      // angular-ui-project recommends assigning these services to the root
      // scope.  Others have argued that doing so can lead to obscured
      // dependencies and that making services directly available to html and
      // directives is unclean.  In any case, the ui-router demo assumes these
      // are available in the DOM, therefore they should be on $rootScope.
      $rootScope.$state = $state;
      $rootScope.$stateParams = $stateParams;

    }
  ]);

  app.controller('CtrlApp', ['$scope', '$http', '$rootScope', '$timeout', '$translate', '$location', '$anchorScroll', '$document', 'APP_CONTEXT', '$cookieStore', '$window', 'CURRENT_USER_NAME', '$q',
    function($scope, $http, $rootScope, $timeout, $translate, $location, $anchorScroll, $document, APP_CONTEXT, $cookieStore, $window, CURRENT_USER_NAME, $q) {
      $rootScope.CURRENT_USER_NAME = CURRENT_USER_NAME;
      //set current language show on top right
      var translateStorageKey = 'NG_TRANSLATE_LANG_KEY';
      var navigatorLanguage = $window.navigator.language || $window.navigator.browserLanguage;
      if (navigatorLanguage !== 'zh-CN') {
        navigatorLanguage = 'en-US';
      }
      //use local storage and fallback to cookie
      if ($window && $window.localStorage !== null) {
        $scope.currLanguage = $window.localStorage.getItem(translateStorageKey) || navigatorLanguage;
      } else {
        $scope.currLanguage = $cookieStore.get(translateStorageKey) || navigatorLanguage;
      }
      $rootScope.lang = $scope.currLanguage;
      $scope.currLanguage = 'LANG_CODE_' + $scope.currLanguage;
      $scope.changeLanguage = function(langKey) {
        setCookie('GCRM_LANG', langKey.replace(/-/g, '_'), 10 * 365);
        $q.all([$translate.use(langKey), $http.get(APP_CONTEXT + 'switchLang?lang=' + langKey.replace(/-/g, '_'))]).then(function(result) {
          if (result) {
            $window.location.reload();
          }
        });
      };

      $scope.gotoPanel = function(panelId) {
        $location.hash(panelId);
        $anchorScroll();
      };

      $scope.anchorTo = function(toWhere) {
        // set the location.hash to the id of
        // the element you wish to scroll to.
        $location.hash(toWhere);

        // call $anchorScroll()
        $anchorScroll();
      };

      // var containerWidth = 990;
      // $scope.shotCutIsShow = $document[0].body.clientWidth - containerWidth > 90 ;
      function setCookie(c_name, value, expiredays) {
        var exdate = new Date();
        exdate.setDate(exdate.getDate() + expiredays);
        document.cookie = c_name + '=' + escape(value) + ((expiredays == null) ? '' : ';expires=' + exdate.toGMTString()) + ';path=/';
      }

    }
  ]);

});
