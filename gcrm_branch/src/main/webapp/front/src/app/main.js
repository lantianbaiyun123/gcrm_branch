var cssMapPath = '';
var baseUrl = '../resources/app';
if ( window.GCRM && window.GCRM.constants ) {
  cssMapPath = window.GCRM.constants.CONTEXT;
} else {
  baseUrl = './app';
}
var configObj = {
  "waitSeconds" : 0,
  urlArgs: "v={{timeStamp}}",
  baseUrl: baseUrl,

  paths: {
    'css': '../vendor/requirejs/css',
    'css-builder': '../vendor/requirejs/css-builder',
    'normalize': '../vendor/requirejs/normalize',
    'angular'               : '../vendor/angular/angular',
    'angular-ui-router'     : '../vendor/angular/angular-ui-router',
    'angular-couch-potato'  : '../vendor/angular-couch-potato/angular-couch-potato',
    'angular-ui-bootstrap'  : '../vendor/ui-bootstrap/ui-bootstrap-tpls-0.10.0',
    'angular-loading-bar'   : '../vendor/angular-loading-bar/loading-bar',
    'angular-translate'     : '../vendor/angular-translate/angular-translate',
    'angular-translate-loader-static-files'     : '../vendor/angular-translate/angular-translate-loader-static-files',
    'angular-translate-storage-cookie'     : '../vendor/angular-translate/angular-translate-storage-cookie',
    'angular-translate-storage-local'     : '../vendor/angular-translate/angular-translate-storage-local',
    'ngCookies'             : '../vendor/angular/angular-cookies',
    'jquery'                : '../vendor/jquery/jquery',
    'select2'               : '../vendor/select2-3.4.5/select2',
    'anuglar-ui-select2'    : '../vendor/ui-select2-master/src/select2',
    'ajaxupload'            : '../vendor/ajaxupload/ajaxupload',
    'jquery-fileupload'     : '../vendor/jquery-fileupload/jquery.fileupload',
    'jquery.ui.widget'      : '../vendor/jquery-fileupload/jquery.ui.widget.min',
    'angular-ui-tree'       : '../vendor/angular-ui-tree/angular-ui-tree',
    'angular-animate'       : '../vendor/angular/angular-animate',
    'angular-sanitize'      : '../vendor/angular/angular-sanitize',
    'highcharts'            : '../vendor/highcharts/highcharts',
    'highcharts-ng'         : '../vendor/highcharts-ng/highcharts-ng',
    'angular-deferred-bootstrap' : '../vendor/angular-deferred-bootstrap/angular-deferred-bootstrap',
    'app-templates'         : './app-templates',
    'bootstrap-js'            : '../vendor/bootstrap/js/bootstrap',
    'summernote'            : '../vendor/summernote/summernote',
    'angular-summernote'    : '../vendor/summernote/angular-summernote',
    'ng-clip'         : '../vendor/ng-clip/ng-clip',
    'zeroclipboard'         : '../vendor/zeroclipboard/ZeroClipboard'
    },

  shim: {

    'angular': {
      exports   : 'angular',
      deps : ['jquery']
    },

    'angular-animate' : {
      deps :['angular']
    },

    'angular-sanitize' : {
      deps :['angular']
    },

    'angular-couch-potato': {
      deps :['angular']
    },
    'angular-ui-router': {
      deps      : ['angular']
    },

    'angular-ui-bootstrap' : {
      deps      : ['angular']
    },

    'angular-loading-bar': {
      deps      : ['angular']
    },

    'angular-translate': {
      deps      : ['angular']
    },

    'angular-translate-loader-static-files': {
      deps      : ['angular', 'angular-translate']
    },

    'angular-translate-storage-cookie': {
      deps      : ['angular', 'angular-translate']
    },
    'angular-translate-storage-local': {
      deps      : ['angular', 'angular-translate']
    },

    'ngCookies': {
      deps      : ['angular']
    },

    'select2': {
      deps      : ['angular', 'jquery']
    },

    'anuglar-ui-select2': {
      deps      : ['select2']
    },

    'ajaxupload': {
      deps      : ['jquery']
    },

    'app-templates': {
      deps      : ['angular']
    },

    'highcharts': {
      deps: ['angular', 'jquery']
    },

    'highcharts-ng': {
      deps: ['highcharts']
    },

    'angular-deferred-bootstrap': {
      deps: ['angular'],
      exports: 'deferredBootstrap'
    },

    'bootstrap-js': {
      deps: ['jquery']
    },

    'summernote': {
      deps: ['angular', 'jquery', 'bootstrap-js'],
      exports: 'summernote'
    },

    'angular-summernote': {
      deps: ['summernote']
    },

    'zeroclipboard': {
      deps: ['angular'],
      exports: 'ZeroClipboard'
    },

    'ng-clip': {
      deps: ['zeroclipboard']
    }

  },
  map: {
    '*': {
      // 'css': cssMapPath + 'resources/lib/requirejs/css.js' // or whatever the path to require-css is
      'css': cssMapPath + '../vendor/requirejs/css.js' // or whatever the path to require-css is
    }
  }
};
require.config(configObj);

// run is required to force the app to run, not because we need to interact
// with it.  Anything required here will by default be combined/minified by
// r.js if you use it.
require(['app', 'angular', 'angular-deferred-bootstrap', 'app-init' ], function(app, angular, deferredBootstrap) {
  /**
   * actrual bootstrap
   */
  function doBootstrap () {
    angular.element(document).ready(function () {
      angular.bootstrap(document, [app['name'], function () {
        angular.element(document).find('html').addClass('ng-app');
      }]);
    });
  }

  var
    appContext,
    staticDir,
    currentUsername,
    CMSHomeUrl,
    i18nPath;

  try {
    appContext = window.GCRM.constants.CONTEXT;
    staticDir = '../resources/';
  } catch (e) {
    appContext = '/gcrm/';//for dev
    staticDir = '';//for dev
  }

  try {
    currentUsername = window.GCRM.constants.CURRENT_USER_NAME;
    CMSHomeUrl = window.GCRM.constants.CMS_HOME_URL;
    i18nPath = '/gcrm/resources/app/_i18n/';
  } catch (e) {
    currentUsername = {
      ucid: 7426946,
      realname: '刘晓'
    };
    CMSHomeUrl = 'http://www.baidu.com';//for dev
    i18nPath = '/app/_i18n/';
  }

  deferredBootstrapper.bootstrap({
    element: document.body,
    module: app['name'],
    resolve: {
      'APP_CONTEXT': ['$q', '$timeout', function ($q, $timeout) {
        var deferred = $q.defer();
        $timeout(function () {
          deferred.resolve(appContext);
        });
        return deferred.promise;
      }],
      'STATIC_DIR': ['$q', '$timeout', function ($q, $timeout) {
        var deferred = $q.defer();
        $timeout(function () {
          deferred.resolve(staticDir);
        });
        return deferred.promise;
      }],
      'CURRENT_USER_NAME': ['$q', '$timeout', function ($q, $timeout) {
        var deferred = $q.defer();
        $timeout(function () {
          deferred.resolve(currentUsername);
        });
        return deferred.promise;
      }],
      'CMS_HOME_URL': ['$q', '$timeout', function ($q, $timeout) {
        var deferred = $q.defer();
        $timeout(function () {
          deferred.resolve(CMSHomeUrl);
        });
        return deferred.promise;
      }],
      'TRANSLATION_CN': ['$http', function ($http) {
        return $http.get(i18nPath + 'locale-zh-CN.json?v={{timeStamp}}');
      }],
      'TRANSLATION_EN': ['$http', function ($http) {
        return $http.get(i18nPath + 'locale-en-US.json?v={{timeStamp}}');
      }]
    }
  });
  // bootstrap();
});