define(["app"],function(e){e.registerDirective("periodLabel",["$parse",function(){return{template:'<span class="label label-{{periodLabelType}} period-label" ng-repeat="item in periodLabels" ng-style="labelStyle">{{item}}</span>',replace:!0,require:"ngModel",restrict:"A",scope:{model:"=ngModel"},link:function(e,l,i){e.periodLabelType=i.periodLabel||"default",e.$watch("model",function(l){l&&(";"===l[l.length-1]&&(l=l.substring(0,l.length-1)),l=l.replace(/,/g," ~ "),e.periodLabels=l.split(";"))})}}}])});