define(['app'], function(app) {
  app.registerFactory('GCRMUtil', [
    '$rootScope',
    function($rootScope) {
      var GCRM = window.GCRM;
      /*for dev*/
      if (!GCRM) {
        GCRM = {};
        require(['../../data/GCRM_MESSAGE'], function (response) {
          GCRM.messages = response;
        });
      }
      return {
        decodeGCRMMessageSingle: function (code) {
          if (!GCRM || !GCRM.messages) {
            return '';
          }
          return GCRM.messages[code];
        },
        decodeGCRMMessages: function (codeArray, messageSplitter) {
          if (!GCRM || !GCRM.messages) {
            return '';
          }
          var splitter = messageSplitter || ',';
          var decodeArr = [];
          for (var i = 0; i < codeArray.length; i++) {
            decodeArr.push(GCRM.messages[codeArray[i]]);
          }
          return decodeArr.join(splitter);
        },
        decodeGCRMError: function (errorObject, messageSplitter) {
          if (!GCRM || !GCRM.messages) {
            return '';
          }
          var _this = this;
          var splitter = messageSplitter || ',';
          var returnArr = [];
          angular.forEach(errorObject, function (value, key) {
            returnArr.push(_this.decodeGCRMMessageSingle(value));
          });
          return returnArr.join(splitter);
        },
        translate: function (code, args) {
          var message = GCRM.messages[code];
          var result = this.substitute(message, args);
          return result || "";
        },
        substitute: function (text, obj) {
          if (!text) {
            return text;
          }
          for (var key in obj) {
            text = text.replace(new RegExp("\\{" + key + "\\}", "g"), obj[key]);
          }
          return text;
        },
        checkOwnerPermit: function (operKey, isOwner) {
          var OwnerOpers = $rootScope.OwnerOpers;
          var needPermit = OwnerOpers[operKey];

          return (!needPermit || isOwner);
        }
      };
    }
  ]);
});
