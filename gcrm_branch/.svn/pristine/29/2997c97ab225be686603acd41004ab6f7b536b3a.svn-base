var cssMapPath = '';
var baseUrl = '../resources/app';
if ( window.GCRM && window.GCRM.constants ) {
  cssMapPath = window.GCRM.constants.CONTEXT;
} else {
  baseUrl = './app';
}
var configObj = {

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
    'app-templates'         : './app-templates'
  },

  shim: {

    'angular': {
      exports   : 'angular',
      deps : ['jquery']
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
    }

  },
  map: {
    '*': {
      // 'css': cssMapPath + 'resources/lib/requirejs/css.js' // or whatever the path to require-css is
      'css': cssMapPath + '../../vendor/requirejs/css.js' // or whatever the path to require-css is
    }
  }
};
require.config(configObj);

// run is required to force the app to run, not because we need to interact
// with it.  Anything required here will by default be combined/minified by
// r.js if you use it.
require(['app', 'angular', 'app-init'], function(app, angular) {

  angular.element(document).ready(function() {

    angular.bootstrap(document, [app['name'], function() {

      // for good measure, put ng-app on the html element
      // studiously avoiding jQuery because angularjs.org says we shouldn't
      // use it.  In real life, there are a ton of reasons to use it.
      // karma likes to have ng-app on the html element when using requirejs.
      angular.element(document).find('html').addClass('ng-app');

    }]);

  });

});