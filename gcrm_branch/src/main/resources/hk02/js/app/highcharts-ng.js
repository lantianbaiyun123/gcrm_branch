define(["app"],function(n){n.registerFactory("highchartsNGUtils",function(){return{indexOf:function(n,i,t){void 0===t&&(t=0),0>t&&(t+=n.length),0>t&&(t=0);for(var e=n.length;e>t;t++)if(t in n&&n[t]===i)return t;return-1},prependMethod:function(n,i,t){var e=n[i];n[i]=function(){var n=Array.prototype.slice.call(arguments);return t.apply(this,n),e?e.apply(this,n):void 0}},deepExtend:function n(i,t){if(angular.isArray(t)){i=angular.isArray(i)?i:[];for(var e=0;e<t.length;e++)i[e]=n(i[e]||{},t[e])}else if(angular.isObject(t))for(var r in t)i[r]=n(i[r]||{},t[r]);else i=t;return i}}}).registerDirective("highchart",["highchartsNGUtils",function(n){var i=0,t=function(n){var t=!1;return angular.forEach(n,function(n){angular.isDefined(n.id)||(n.id="series-"+i++,t=!0)}),t},e=["xAxis","yAxis"],r=function(i,t,r){var a={},c={chart:{events:{}},title:{},subtitle:{},series:[],credits:{},plotOptions:{},navigator:{enabled:!1}};return a=r.options?n.deepExtend(c,r.options):c,a.chart.renderTo=t[0],angular.forEach(e,function(t){angular.isDefined(r[t])&&(a[t]=angular.copy(r[t]),(angular.isDefined(r[t].currentMin)||angular.isDefined(r[t].currentMax))&&(n.prependMethod(a.chart.events,"selection",function(n){var e=this;i.$apply(n[t]?function(){i.config[t].currentMin=n[t][0].min,i.config[t].currentMax=n[t][0].max}:function(){i.config[t].currentMin=e[t][0].dataMin,i.config[t].currentMax=e[t][0].dataMax})}),n.prependMethod(a.chart.events,"addSeries",function(){i.config[t].currentMin=this[t][0].min||i.config[t].currentMin,i.config[t].currentMax=this[t][0].max||i.config[t].currentMax})))}),r.title&&(a.title=r.title),r.subtitle&&(a.subtitle=r.subtitle),r.credits&&(a.credits=r.credits),r.size&&(r.size.width&&(a.chart.width=r.size.width),r.size.height&&(a.chart.height=r.size.height)),a},a=function(n,i){var t=n.getExtremes();(i.currentMin!==t.dataMin||i.currentMax!==t.dataMax)&&n.setExtremes(i.currentMin,i.currentMax,!1)},c=function(n,i,t){(i.currentMin||i.currentMax)&&n[t][0].setExtremes(i.currentMin,i.currentMax,!0)},o=function(n){return angular.extend({},n,{data:null,visible:null})};return{restrict:"EAC",replace:!0,template:"<div></div>",scope:{config:"="},link:function(i,s){var u={},f=function(e){var r,a=[];if(e){var c=t(e);if(c)return!1;if(angular.forEach(e,function(n){a.push(n.id);var i=d.get(n.id);i?angular.equals(u[n.id],o(n))?(void 0!==n.visible&&i.visible!==n.visible&&i.setVisible(n.visible,!1),i.setData(angular.copy(n.data),!1)):i.update(angular.copy(n),!1):d.addSeries(angular.copy(n),!1),u[n.id]=o(n)}),i.config.noData){var s=!1;for(r=0;r<e.length;r++)if(e[r].data&&e[r].data.length>0){s=!0;break}s?d.hideLoading():d.showLoading(i.config.noData)}}for(r=d.series.length-1;r>=0;r--){var f=d.series[r];n.indexOf(a,f.options.id)<0&&f.remove(!1)}return!0},d=!1,h=function(){d&&d.destroy(),u={};var n=i.config||{},t=r(i,s,n);d=n.useHighStocks?new Highcharts.StockChart(t):new Highcharts.Chart(t);for(var a=0;a<e.length;a++)n[e[a]]&&c(d,n[e[a]],e[a]);n.loading&&d.showLoading()};h(),i.$watch("config.series",function(n){var i=f(n);i&&d.redraw()},!0),i.$watch("config.title",function(n){d.setTitle(n,!0)},!0),i.$watch("config.subtitle",function(n){d.setTitle(!0,n)},!0),i.$watch("config.loading",function(n){n?d.showLoading():d.hideLoading()}),i.$watch("config.credits.enabled",function(n){n?d.credits.show():d.credits&&d.credits.hide()}),i.$watch("config.useHighStocks",function(n,i){n!==i&&h()}),angular.forEach(e,function(n){i.$watch("config."+n,function(i,t){i!==t&&i&&(d[n][0].update(i,!1),a(d[n][0],angular.copy(i)),d.redraw())},!0)}),i.$watch("config.options",function(n,i,t){n!==i&&(h(),f(t.config.series),d.redraw())},!0),i.$watch("config.size",function(n,i){n!==i&&n&&d.setSize(n.width||void 0,n.height||void 0)},!0),i.$on("highchartsng.reflow",function(){d.reflow()}),i.$on("$destroy",function(){d&&d.destroy(),s.remove()})}}}])});