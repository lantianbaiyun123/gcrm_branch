/**
 * Utils (Angular Service)
 *
 * --USAGES--
 *   decodeGCRMMessage( code )
 *       decode message from code for i18n
 *   decodeGCRMMessages( codeArray, messageSplitter)
 *       decode messageArray,and u can define splitter
 *
 */

define(['app'], function (app) {
  app.registerFactory('Utils', ['$http',
    function ($http) {
      return {
        joinWithKey: function (array, wantedKey, splitter) {
          if (!array || array.length === 0) {
            return "";
          }
          var arr = [];
          for (var i = 0; i < array.length; i++) {
            arr.push(array[i][wantedKey]);
          }

          return arr.join(splitter || ",");
        },
        substrByByte: function (source, length) {
          return (source + '').substr(0, length).replace(/([^\x00-\xff])/g, ' $1').substr(0, length).replace(/ ([^\x00-\xff])/g, '$1');
        },
        isLengthOverflow: function (source, maxLength) {
          if ((source + '').replace(/([^\x00-\xff])/g, 'aa').length > maxLength) {
            return true;
          }
          return false;
        },
        isLengthShort: function (source, minLength) {
          if ((source + '').replace(/([^\x00-\xff])/g, 'aa').length < minLength) {
            return true;
          }
          return false;
        },
        containSpecialCharactor: function (str, isNameZone) {
          var pattern = new RegExp("[`~!@$^&*()=|{}':;',\\[\\].<>/?~！@￥……&*（）——|{}【】‘；：”“'。，、？]");
          if (isNameZone) {
            pattern = new RegExp("[`~!@$^&*=|{}':;',<>/?~！@￥……&*——|{}‘；：”“'。，、？]");
          }
          return pattern.test(str);
        },
        getDateString: function (date) {
          var theDate = date || new Date();
          if (!angular.isDate(theDate)) {
            theDate = new Date(theDate);
          }
          var year = theDate.getFullYear(),
            month = theDate.getMonth() + 1,
            day = theDate.getDate();

          month = month < 10 ? '0' + month : month;
          day = day < 10 ? '0' + day : day;

          var dateString = year + '-' + month + '-' + day;
          return dateString;
        },
        getDateTimeString: function (date) {
          var theDate = date || new Date();
          if (!angular.isDate(theDate)) {
            theDate = new Date(theDate);
          }
          var dateString = this.getDateString(theDate),
            hh = theDate.getHours(),
            mm = theDate.getMinutes(),
            ss = theDate.getSeconds();
          hh = hh < 10 ? '0' + hh : hh;
          mm = mm < 10 ? '0' + mm : mm;
          ss = ss < 10 ? '0' + ss : ss;
          var dateTimeString = dateString + ' ' + hh + ':' + mm + ':' + ss;
          return dateTimeString;
        },
        toBoolean: function (value) {
          if (typeof value === 'function') {
            value = true;
          } else if (value && value.length !== 0) {
            var v = ("" + value).toLowerCase();
            value = !(v == 'f' || v == '0' || v == 'false' || v == 'no' || v == 'n' || v == '[]');
          } else {
            value = false;
          }
          return value;
        },
        urlValidator: function (value) {
          var URL_REGEXP = /^(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/;
          return URL_REGEXP.test(value);
        },
        getTodayZero: function () {
          var today = new Date();
          return new Date(today.getFullYear(), today.getMonth(), today.getDate());
        },
        isEmptyObj: function (obj) {
          for ( var name in obj ) {
            return false;
          }
          return true;
        }
      };
    }
  ]);

});
