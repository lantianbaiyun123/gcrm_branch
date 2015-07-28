define([
    'angular',
    'angular-couch-potato',
    'angular-ui-router',
    'angular-ui-bootstrap',
    'angular-loading-bar',
    'angular-translate',
    'angular-translate-loader-static-files',
    'angular-translate-storage-cookie',
    'angular-translate-storage-local',
    'app-templates',
    'angular-animate',
    'ngCookies',
    'angular-sanitize'
  ], function(angular, couchPotato) {

  var app = angular.module('app', ['scs.couch-potato', 'ui.router', 'ui.bootstrap', 'ui.bootstrap.tpls', 'chieffancypants.loadingBar', 'pascalprecht.translate', 'app-templates', 'ngCookies', 'ngAnimate', 'ngSanitize']);

  // have Couch Potato set up the registerXXX functions on the app so that
  // registration of components is as easy as can be
  couchPotato.configureApp(app);
  return app;
});
