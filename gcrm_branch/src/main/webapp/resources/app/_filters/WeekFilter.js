define(['app'], function (app) {
  app.registerFilter('WeekFilter', function () {

    var category = {
      normal: [
        "SUNDAY",
        "MONDAY",
        "TUESDAY",
        "WEDNESDAY",
        "THURSDAY",
        "FRIDAY",
        "SATURDAY"
      ],
      'short': [
        "SUNDAY_SHORT",
        "MONDAY_SHORT",
        "TUESDAY_SHORT",
        "WEDNESDAY_SHORT",
        "THURSDAY_SHORT",
        "FRIDAY_SHORT",
        "SATURDAY_SHORT"
      ]
    };

    return function ( input, cate ) {
      if ( cate && category[cate] && category[cate][input] ) {
        return category[cate][input];
      } else {
        return '--';
      }
    };
  });
});