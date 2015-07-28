!function(){"use strict";angular.module("angular-loading-bar",["chieffancypants.loadingBar"]),angular.module("chieffancypants.loadingBar",[]).config(["$httpProvider",function(n){var e=["$q","$cacheFactory","cfpLoadingBar",function(e,t,i){function a(){i.complete(),o=0,c=0}function r(e){var i,a=n.defaults;if("GET"!==e.method||e.cache===!1)return e.cached=!1,!1;i=e.cache===!0&&void 0===a.cache?t.get("$http"):void 0!==a.cache?a.cache:e.cache;var r=void 0!==i?void 0!==i.get(e.url):!1;return void 0!==e.cached&&r!==e.cached?e.cached:(e.cached=r,r)}var c=0,o=0;return{request:function(n){return n.ignoreLoadingBar||r(n)||(0===c&&i.start(),c++),n},response:function(n){return r(n.config)||(o++,o>=c?a():i.set(o/c)),n},responseError:function(n){return o++,o>=c?a():i.set(o/c),e.reject(n)}}}];n.interceptors.push(e)}]).provider("cfpLoadingBar",function(){this.includeSpinner=!0,this.includeBar=!0,this.parentSelector="body",this.$get=["$document","$timeout","$rootScope",function(n,e,t){function i(){e.cancel(u),p||(t.$broadcast("cfpLoadingBar:started"),p=!0,g&&l.append(f),a(.02))}function a(n){if(p){var t=100*n+"%";h.css("width",t),v=n,e.cancel(d),d=e(function(){r()},250)}}function r(){if(!(c()>=1)){var n=0,e=c();n=e>=0&&.25>e?(3*Math.random()+3)/100:e>=.25&&.65>e?3*Math.random()/100:e>=.65&&.9>e?2*Math.random()/100:e>=.9&&.99>e?.005:0;var t=c()+n;a(t)}}function c(){return v}function o(){t.$broadcast("cfpLoadingBar:completed"),a(1),u=e(function(){f.remove(),v=0,p=!1},500)}var d,u,s=this.parentSelector,l=n.find(s),f=angular.element('<div id="loading-bar"><div class="bar"><div class="peg"></div></div></div>'),h=f.find("div").eq(0),p=(angular.element('<div id="loading-bar-spinner"><div class="spinner-icon"></div></div>'),!1),v=0,g=(this.includeSpinner,this.includeBar);return{start:i,set:a,status:c,inc:r,complete:o,includeSpinner:this.includeSpinner,parentSelector:this.parentSelector}}]})}();