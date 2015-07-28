define([
  'app'
], function (app) {
  app.registerFactory('Country', [
    '$http',
    '$filter',
    'APP_CONTEXT',
  function ($http, $filter, APP_CONTEXT) {
    function getSuggestList (paramObj) {
      return $http({
        method: 'get',
        url: APP_CONTEXT + 'customer/countrySuggest',
        // url: '/data/countrySuggest.json',
        params: paramObj
      });
    }

    return {
      getSuggest: function ( initObj ) {
        return {
          id: function (item) {
            return item.id;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_BELONGING_COUNTRY'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + minLength + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function (query) {
            var data = {
              results: []
            };
            getSuggestList({
              query: $.trim(query.term)
            }).success(function (response) {
              if (response.data.length) {
                data.results = response.data;
                for (var i = data.results.length - 1; i >= 0; i--) {
                  data.results[i].i18nName = data.results[i].name;
                }
              }
              query.callback(data);
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_COUNTRY');
          },
          formatResult: function(item) {
            return item.i18nName;
          },
          formatSelection: function(item) {
            if(initObj && initObj.formatSelectionCallback ){
              initObj.formatSelectionCallback(item);
            }
            return item.i18nName;
          },
          width: 'element',
          initSelection : function (element, callback) {
            if(initObj && initObj.initSelectionCallback ){
              initObj.initSelectionCallback(element, callback);
            }
          },
          allowClear: function () {
            if ( initObj && initObj.allowClear ) {
              return initObj.allowClear;
            }
            return true;
          }
        };
      }
    };
  }]);
});