define(["app"],function(e){e.registerFilter("DatePeriodFilter",function(){return function(e,n){return e&&(";"===e[e.length-1]&&(e=e.substring(0,e.length-1)),e=e.replace(/,/g,"~"),e=n?e.replace(/;/g,";\n"):e.replace(/;/g,";  ")),e}})});