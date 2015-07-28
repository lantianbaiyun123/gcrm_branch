(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/error/error.tpl.html",
    "<div ui-view=\"errorType\"></div>\n" +
    "");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/error/error_404.tpl.html",
    "404");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/error/error_500.tpl.html",
    "<div class=\"error-500-img\">\n" +
    "  <h2>error 500</h2>\n" +
    "</div>");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/footer/footer.tpl.html",
    "<div class=\"footer\">\n" +
    "  <span>©2014 Baidu</span>\n" +
    "</div>");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/header/header.tpl.html",
    "<div class=\"scope-header\">\n" +
    "    <div id=\"navBarTop\" class=\"navbartop\">\n" +
    "        <div class=\"container\">\n" +
    "            <div class=\"row\">\n" +
    "                <div class=\"col-md-5 pull-right text-right\">\n" +
    "                    <!-- <a href=\"\" class=\"btn btn-xs btn-noborder\" ng-click='goFirstMyTask()'>\n" +
    "                        {{'HEAD_TODO' | translate}} <span class=\"icon icon-tip\" ng-show=\"myTaskCount\">{{myTaskCount}}</span>\n" +
    "                    </a> -->\n" +
    "\n" +
    "                    <div class=\"btn btn-xs btn-noborder btn-group\">\n" +
    "                        <a href=\"\" class=\"btn btn-xs btn-noborder dropdown-toggle\" data-toggle=\"dropdown\">\n" +
    "                            Language&nbsp;{{ currLanguage | translate }}\n" +
    "                        </a>\n" +
    "                        <ul style=\"z-index:1; min-width:100%; text-align: left;\" class=\"dropdown-menu\" role=\"menu\">\n" +
    "                            <li ng-click=\"changeLanguage('zh-CN')\" >\n" +
    "                                <a>{{ 'AD_ANCHOR_LANGUAGE_CH' | translate }}</a>\n" +
    "                            </li>\n" +
    "                            <li ng-click=\"changeLanguage('en-US')\" >\n" +
    "                                <a>{{ 'AD_ANCHOR_LANGUAGE_EN' | translate }}</a>\n" +
    "                            </li>\n" +
    "                        </ul>\n" +
    "                    </div>\n" +
    "                    <a href=\"\" class=\"btn btn-xs btn-noborder  dropdown-toggle\" data-toggle=\"dropdown\">\n" +
    "                       <span class=\"glyphicon glyphicon-user\"></span>\n" +
    "                      {{CURRENT_USER_NAME.realname}}\n" +
    "                    </a>\n" +
    "                    <ul style=\"z-index:1; text-align: left;\" class=\"dropdown-menu\" role=\"menu\">\n" +
    "                        <li>\n" +
    "                          <a ng-href=\"{{page.url}}\" ng-click=\"changePsw()\">{{'CHANGE_PSW' | translate}}</a>\n" +
    "                        </li>\n" +
    "                    </ul>\n" +
    "                    <a href=\"/gcrm/logout\" class=\"btn btn-xs btn-noborder\">\n" +
    "                      <span class=\"glyphicon glyphicon-log-out\"></span>\n" +
    "                      {{'LOGOUT' | translate}}\n" +
    "                    </a>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "    <nav class=\"navbar navbar-inverse\" role=\"navigation\">\n" +
    "        <!-- Brand and toggle get grouped for better mobile display -->\n" +
    "        <div class=\"container\">\n" +
    "            <div class=\"navbar-header\">\n" +
    "                <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\">\n" +
    "                    <span class=\"sr-only\">Toggle navigation</span>\n" +
    "                    <span class=\"icon-bar\"></span>\n" +
    "                    <span class=\"icon-bar\"></span>\n" +
    "                    <span class=\"icon-bar\"></span>\n" +
    "                </button>\n" +
    "                <a class=\"navbar-brand\" href=\"#\">\n" +
    "                </a>\n" +
    "            </div>\n" +
    "\n" +
    "            <!-- Collect the nav links, forms, and other content for toggling -->\n" +
    "            <div class=\"collapse navbar-collapse\">\n" +
    "                <ul class=\"nav navbar-nav navbar-right\">\n" +
    "                    <li  ng-repeat=\"item in headNavs\" ng-class=\"{ 'active' : activeIndex==$index }\" ng-click=\"navClick($index);\">\n" +
    "                      <a ng-if=\"item.url\" ng-href=\"{{item.url}}\" target=\"_blank\">{{ item.text | translate }}<span class=\"caret\" ng-if=\"item.sub.length\"></span></a>\n" +
    "                      <a ng-if=\"!item.url && item.text!='HEAD_TASK'\" ng-href=\"\">{{ item.text | translate }}<span class=\"caret\" ng-if=\"item.sub.length\"></span></a>\n" +
    "                      <a ng-if=\"item.text=='HEAD_TASK'\" ng-click=\"goFirstMyTask()\">{{ item.text | translate }}(<span ng-class=\"{'text-alert': myTaskCount }\">{{myTaskCount}}</span>)</a>\n" +
    "                      <ul class=\"dropdown-menu\" ng-if=\"item.sub.length\">\n" +
    "                        <li ng-repeat=\"page in item.sub\">\n" +
    "                          <a ng-href=\"{{page.url}}\">{{page.text}}</a>\n" +
    "                        </li>\n" +
    "                      </ul>\n" +
    "                    </li>\n" +
    "                </ul>\n" +
    "            </div><!-- /.navbar-collapse -->\n" +
    "        </div>\n" +
    "    </nav>\n" +
    "    <div class=\"container\">\n" +
    "        <ol class=\"breadcrumb\">\n" +
    "            <li ng-repeat=\"crumb in breadcrumb\" ng-class=\"{'active': $index == breadcrumb.length - 1, 'disabled': !crumb.url}\">\n" +
    "                <a ng-href=\"{{crumb.url}}\" ng-if=\"$index < breadcrumb.length - 1\">\n" +
    "                  {{crumb.text}}\n" +
    "                </a>\n" +
    "                <a ng-if=\"$index == breadcrumb.length - 1\">\n" +
    "                  {{crumb.text}}\n" +
    "                </a>\n" +
    "            </li>\n" +
    "        </ol>\n" +
    "    </div>\n" +
    "</div>");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/modal/errorModal.tpl.html",
    "<div class=\"modal-header\">\n" +
    "  <h4>{{title}}</h4>\n" +
    "</div>\n" +
    "<div class=\"modal-header\">\n" +
    "  <dl>\n" +
    "    <div ng-repeat=\"errorType in errors\">\n" +
    "      <dt>{{errorType.typeName}}</dt>\n" +
    "      <dd>\n" +
    "        <ul>\n" +
    "          <li ng-repeat=\"item in errorType.list\">\n" +
    "            {{item}}\n" +
    "          </li>\n" +
    "        </ul>\n" +
    "      </dd>\n" +
    "    </div>\n" +
    "  </dl>\n" +
    "</div>\n" +
    "<div class=\"modal-body clearfix\">\n" +
    "  <button type=\"button\" class=\"btn btn-primary pull-right\" ng-click=\"ok()\">{{'CONFIRM' | translate}}</button>\n" +
    "</div>\n" +
    "");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/modal/modal.tpl.html",
    "<!-- <div class=\"modal-header\">\n" +
    "    <h4>{{title}}</h4>\n" +
    "</div> -->\n" +
    "<div class=\"modal-header\">\n" +
    "  <h4 class=\"modal-title\" ng-if=\"content\" ng-bind-html=\"content\"></h4>\n" +
    "  <p ng-repeat=\"content in contentList\" ng-if=\"contentList\">{{content}}</p>\n" +
    "  <p class=\"modal-plus-info text-danger\" ng-if=\"psInfo\">{{psInfo}}</p>\n" +
    "</div>\n" +
    "<div class=\"modal-body\">\n" +
    "  <button type=\"button\" class=\"btn btn-primary\"  ng-click=\"ok()\">{{okText}}</button>\n" +
    "  <button type=\"button\" class=\"btn btn-default\" ng-show=\"showCancel\" ng-click=\"cancel()\">{{cancelText}}</button>\n" +
    "</div>\n" +
    "");
}]);
})();

(function(module) {
try { app = angular.module("app-templates"); }
catch(err) { app = angular.module("app-templates", []); }
app.run(["$templateCache", function($templateCache) {
  $templateCache.put("app/_common/modal/successModal.tpl.html",
    "<div class=\"alert alert-success\">\n" +
    "    <p>\n" +
    "        <span class=\"glyphicon glyphicon-ok\"></span>&nbsp;&nbsp;\n" +
    "        <strong ng-if=\"title\">{{ title }}&nbsp;&nbsp;</strong>\n" +
    "        {{ content }}\n" +
    "    </p>\n" +
    "  </div>");
}]);
})();
