/**
 * 过滤日历控件中的title提示
 * 根据
 */

define(['app'], function (app) {
  app.registerFilter('CalendarTdTitleFilter', ['$filter', function ($filter) {
    return function (date, billingModelId, totalCount) {
      var text = '';
      if (billingModelId === 5 && totalCount>1 && date.occupied) {
        text = $filter('translate')('CALENDAR_TD_OCCUPIED', {
          ed: date.occupied,
          total: totalCount
        });
      } else if (billingModelId === 4 && date.remained) {
        text = $filter('translate')('CALENDAR_TR_REMAINED', {
          remained: date.remained
        });
      } else if (billingModelId !== 4 &&  billingModelId !== 5 && date.biddingCount) {
        text = $filter('translate')('CALENDAR_TD_BIDDING', {
          biddingCount: date.biddingCount
        });
      }

      return text;
    };
  }]);
});