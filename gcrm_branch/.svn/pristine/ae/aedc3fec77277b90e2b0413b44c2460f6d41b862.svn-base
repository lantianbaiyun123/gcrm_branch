define(['app'], function (app) {
  app.registerDirective('ytScheduleTable', ['$log', function ($log) {
    return {
      restrict: 'A',
      scope: {
        scrollEnd: '&',
        scrollToMonth: '&',
        scrollPosition: '=',
        colIndex: '=',
        shiftKey: '=',
        adContentKey: '=',
        inputClicked: '&',
        rowIndex: '=',
        ownerClicked: '&',
        readOnly: '='
      },
      link: function (scope, element, attrs) {
        var el = element[0];

        attrs.$observe('activeMonthPosition', function (value) {
          if(value>-1) {
            el.scrollLeft = value;
          }
        });

        element.bind('scroll', function () {
          // + 1 catches off by one errors in chrome
          // return el.scrollTop + el.clientHeight + 1 >= el.scrollHeight;
          // $log.info(el.scrollLeft + el.clientWidth + 1 >= el.scrollWidth);
          //fire when it reaches the right pose
          if ( el.scrollLeft + el.clientWidth + 1 >= el.scrollWidth ) {
            scope.scrollEnd();
          }
          scope.scrollToMonth({
            scrollPosition: el.scrollLeft
          });

        });

        var preClickIndex;
        element.bind('click', function (e) {
          if ( $(e.target).is('input') ) {
            var tdIndex = $(e.target).closest('td').index();
            scope.inputClicked({
              colIndex: tdIndex - 1,
              adContentKey: $(e.target).closest('tr').attr('ad-content-key'),
              shiftKey: e.shiftKey
            });

          } else if ( !scope.readOnly && $(e.target).closest('td').hasClass('headcol')) {
            //change adConent

            $(e.target).closest('tr').siblings().removeClass('selected');
            $(e.target).closest('tr').addClass('selected');
            scope.ownerClicked({
              adContentKey: $(e.target).closest('tr').attr('ad-content-key')
            });
          }
        });
      }
    };
  }]);
});