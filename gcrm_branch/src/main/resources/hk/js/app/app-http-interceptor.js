define(["app","gcrm-util"],function(e){e.config(["$httpProvider",function(e){var a=["$q","$log","GCRMUtil","$location",function(e,a,t,n){function r(e){var a=$('<div tabindex="-1" class="modal fade ngAlert in" ng-class="{in: animate}" style="z-index: 1050; display: block;"><div class="modal-dialog"><div class="modal-content">\n  <div class="modal-header">\n   <h4 class="modal-title">'+e+'</h4>\n  </div>\n  <div class="modal-body">\n    <button type="button" class="btn btn-primary btn-interceptor-ok">OK</button>\n  </div>\n</div></div></div>'),t=$('<div class="modal-backdrop in" style="z-index: 1040;"></div>');a.appendTo($("body")),t.appendTo($("body")),a.fadeIn(500),$(".btn-interceptor-ok").on("click",function(){a.remove(),t.remove()})}function o(){var e,a,t=-1;return"Microsoft Internet Explorer"==navigator.appName?(e=navigator.userAgent,a=new RegExp("MSIE ([0-9]{1,}[.0-9]{0,})"),null!=a.exec(e)&&(t=parseFloat(RegExp.$1))):"Netscape"==navigator.appName&&(e=navigator.userAgent,a=new RegExp("Trident/.*rv:([0-9]{1,}[.0-9]{0,})"),null!=a.exec(e)&&(t=parseFloat(RegExp.$1))),t}var d=o();return{request:function(e){return d>0&&"GET"===e.method&&e.url&&!/\.js$|\.html$/.test(e.url)&&(e.params=e.params||{},e.params._=(new Date).getTime()),e},response:function(e){return e.data&&e.data.code&&200!==e.data.code&&(202===e.data.code?r(t.decodeGCRMError(e.data.errors)):201===e.data.code?r(e.data.message):203===e.data.code?(r(e.data.message),r(t.decodeGCRMError(e.data.errors))):208===e.data.code&&(window.location.href="/gcrm/login")),e},responseError:function(t){a.error(t);var r=t.status.toString();return 0===r.indexOf("4")||0===r.indexOf("5")&&n.path("/error/500"),e.reject(t)}}}];e.interceptors.push(a)}])});