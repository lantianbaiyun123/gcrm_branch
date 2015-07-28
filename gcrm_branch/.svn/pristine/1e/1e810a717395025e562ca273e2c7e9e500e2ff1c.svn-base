/**
 * 将日期区间转化为nice labels
*  usage:
*     将{{contentView.adSolutionContent.insertPeriodDescription}}替换为
    <div ng-model="contentView.adSolutionContent.insertPeriodDescription" period-label></div>
    注：
    1、需在controller里引入该directive
    2、用period-lable="info"定制颜色:default, primary, success, info warning, danger
 */

define(['app'], function (app) {
  app.registerDirective('periodLabel', ['$parse', function ($parse) {
    return {
      template: '<span class="label label-{{periodLabelType}} period-label" ng-repeat="item in periodLabels" ng-style="labelStyle">{{item}}</span>',
      replace: true,
      require: 'ngModel',
      restrict: 'A',
      scope: {
        model: '=ngModel'
      },
      link: function (scope, element, attrs, ngModel) {
        scope.periodLabelType = attrs.periodLabel || 'default';
        scope.$watch("model", function(modelValue) {
          if (modelValue) {
            // 去掉最后的分号
            if ( modelValue[modelValue.length - 1] === ';' ) {
              modelValue = modelValue.substring(0, modelValue.length - 1);
            }
            modelValue = modelValue.replace(/,/g, ' ~ ');//替换逗号
            scope.periodLabels = modelValue.split(';');
            // scope.labelStyle = {
            //   display: "inline-block",
            //   fontSize: '100%',
            //   marginRight: '.5em',
            //   marginTop: '.3em',
            //   marginBottom: '.3em',
            //   padding: '.3em .6em .3em'
            // };
          }
        });
      }
    };
  }]);
});