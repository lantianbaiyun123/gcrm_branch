define(["app"],function(e){e.registerDirective("ytInputRadio",["$filter","STATIC_DIR",function(e,t){return{replace:!0,restrict:"A",scope:{selected:"=radioSelected",choices:"=ytInputRadio",selectChange:"&",radioDisabled:"="},templateUrl:t+"app/_directives/ytInputRadio.tpl.html",link:function(e,t,i){e.selected=e.selected||"",i.radioType?e.typeName=angular.uppercase(i.radioType+"_")||"":(e.isI18nList=!0,e.typeName=i.ytInputRadio),e.change=function(t,i){return e.radioDisabled?!1:void("INPUT"===i.target.tagName&&"undefined"!=typeof e.selected&&(e.selected=e.isI18nList?t.id:t))}}}}])});