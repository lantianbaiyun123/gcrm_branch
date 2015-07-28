define(['app'], function (app) {
    app.registerFilter('DatePeriodFilter', function () {
        return function ( input, shouldWrap ) {
          var ret = input;
          if (input) {
            // 去掉最后的分号
            if ( input[input.length - 1] === ';' ) {
              input = input.substring(0, input.length - 1);
            }
            input = input.replace( /,/g, '~' );//替换逗号
            if (shouldWrap) {
              input = input.replace( /;/g, ';\n' );//加换行
            } else {
              input = input.replace( /;/g, ';  ' );//加2个空格
            }
          }
          return input;
        };
    });
});