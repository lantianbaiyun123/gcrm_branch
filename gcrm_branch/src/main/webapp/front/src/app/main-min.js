require.config({
  "waitSeconds": 0,
  fileExclusionRegExp: /\.html$|^\.|svn/,
  // stubModules: ['require-css'],
  // name:'main',
  // baseUrl:  '../../../resources/js/app',
  // include:[
  //   './task/CtrlTask'

  // ],
  // separateCSS: true,
  baseUrl: '.',
  optimize: 'none',
  keepBuildDir: true,
  // dir: 'build',
  // removeCombined: true,
  optimizeCss: "standard",
  modules: [
    {
      name: 'main',
      include: ['require']
    },
    {
      name: 'home/CtrlHome',
      exclude: ['main', 'normalize']
    }
    // {
    //   name: 'task/CtrlNoTask',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'schedule/CtrlSchedule',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'benchmarkPrice/CtrlBenchmarkPriceApproval',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'material/CtrlMaterialApproval',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'schedule/detail/CtrlScheduleDetail',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'schedule/list/CtrlScheduleList',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'record/SolutionApprovalRecord',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'record/SolutionModifyRecord',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'approval/CtrlApproval',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'ad/CtrlAd',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'resourcePosition/CtrlResourcePositionList',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'resourcePosition/CtrlResourcePositionAdd',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'material/CtrlMaterialList',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'material/CtrlMaterialDetail',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'customer/add/CtrlCustomerAdd',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'customer/detail/CtrlCustomerDetail',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'admin/CtrlAdminUserList',
    //   exclude: ['main', 'normalize']
    // },
    // {
    //   name: 'admin/CtrlUserDetail',
    //   exclude: ['main', 'normalize']
    // }

  ],
  paths: {
    'css': '../../vendor/requirejs/css',
    'css-builder': '../../vendor/requirejs/css-builder',
    'normalize': '../../vendor/requirejs/normalize',
    'angular': '../../vendor/angular/angular',
    'angular-ui-router': '../../vendor/angular/angular-ui-router',
    'angular-couch-potato': '../../vendor/angular-couch-potato/angular-couch-potato',
    'angular-ui-bootstrap': '../../vendor/ui-bootstrap/ui-bootstrap-tpls-0.10.0',
    'angular-loading-bar': '../../vendor/angular-loading-bar/loading-bar',
    'angular-translate': '../../vendor/angular-translate/angular-translate',
    'angular-translate-loader-static-files': '../../vendor/angular-translate/angular-translate-loader-static-files',
    'angular-translate-storage-cookie': '../../vendor/angular-translate/angular-translate-storage-cookie',
    'angular-translate-storage-local': '../../vendor/angular-translate/angular-translate-storage-local',
    'ngCookies': '../../vendor/angular/angular-cookies',
    'jquery': '../../vendor/jquery/jquery',
    'select2': '../../vendor/select2-3.4.5/select2',
    'anuglar-ui-select2': '../../vendor/ui-select2-master/src/select2',
    'ajaxupload': '../../vendor/ajaxupload/ajaxupload',
    'jquery-fileupload': '../../vendor/jquery-fileupload/jquery.fileupload',
    'jquery.ui.widget': '../../vendor/jquery-fileupload/jquery.ui.widget.min',
    'angular-ui-tree': '../../vendor/angular-ui-tree/angular-ui-tree',
    'angular-animate': '../../vendor/angular/angular-animate',
    'angular-sanitize': '../../vendor/angular/angular-sanitize',
    'highcharts': '../../vendor/highcharts/highcharts',
    'highcharts-ng': '../../vendor/highcharts-ng/highcharts-ng',
    'angular-deferred-bootstrap': '../../vendor/angular-deferred-bootstrap/angular-deferred-bootstrap',
    'app-templates': './app-templates',
    'bootstrap-js'            : '../../vendor/bootstrap/js/bootstrap',
    'summernote'            : '../../vendor/summernote/summernote',
    'angular-summernote'    : '../../vendor/summernote/angular-summernote',
    'ng-clip'         : '../../vendor/ng-clip/ng-clip',
    'zeroclipboard'         : '../../vendor/zeroclipboard/ZeroClipboard'
  },

  shim: {

    'angular': {
      exports: 'angular',
      deps: ['jquery']
    },

    'angular-animate': {
      deps: ['angular']
    },

    'angular-sanitize': {
      deps: ['angular']
    },

    'angular-couch-potato': {
      deps: ['angular']
    },

    'angular-ui-router': {
      deps: ['angular']
    },

    'angular-ui-bootstrap': {
      deps: ['angular']
    },

    'angular-loading-bar': {
      deps: ['angular']
    },

    'angular-translate': {
      deps: ['angular']
    },

    'angular-translate-loader-static-files': {
      deps: ['angular', 'angular-translate']
    },

    'angular-translate-storage-cookie': {
      deps: ['angular', 'angular-translate']
    },
    'angular-translate-storage-local': {
      deps: ['angular', 'angular-translate']
    },

    'ngCookies': {
      deps: ['angular']
    },

    'select2': {
      deps: ['angular', 'jquery']
    },

    'anuglar-ui-select2': {
      deps: ['select2']
    },

    'ajaxupload': {
      deps: ['jquery']
    },

    'highcharts': {
      deps: ['angular', 'jquery']
    },

    'highcharts-ng': {
      deps: ['highcharts']
    },

    'app-templates': {
      deps: ['angular']
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
  }
});
