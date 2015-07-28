define([
       'app',
       './http/Account',
       './http/Customer',
       './http/Contract'
       ], function (app) {
  app.registerFactory('Select2Suggest',[
  '$timeout', 'Account', 'Customer', 'Contract','$filter', 'Position',
  function ($timeout, Account, Customer, Contract, $filter, Position) {
    return {
      getOperatorOption: function ( options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_OPERATOR'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            Account.get({
              'query': $.trim(query.term)
            }).success(function (response) {
              if ( response.data ) {
                data.results = response.data;
                query.callback(data);
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_OPERATOR');
          },
          formatResult: function(item) {
            return item.data.name;
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            return item.data.name;
          },
          width: 'element',
          initSelection : function (element, callback) {
            if(opts && opts.initSelectionCallback ){
              opts.initSelectionCallback(element, callback);
            }
          }
        };
      },
      getCustomerOption: function ( options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return  {
          id: function(priv) {
            return priv.value;
          },
          placeholder: (opts && opts.placeholder) || $filter('translate')('SELECTTOSUGGEST_INPUT_COMPANY'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            if ( query.term ) {
              Customer.getSuggest({
                'query': $.trim(query.term)
              },
              function(response) {
                if (response.length) {
                  data.results = response;
                }
                query.callback(data);
              });
            }
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_COMPANY');
          },
          formatResult: function(item) {
            return item.value;
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            return item.value;
          },
          width: 'element',
          allowClear: (function () {
            if ( opts && opts.allowClear ) {
              return opts.allowClear;
            }
            return false;
          })()
        };
      },
      getContractOption: function ( scope , options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          // apply css that makes the dropdown taller
          dropdownCssClass: "tableDrop",
          dropdownAutoWidth: true,
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_INPUT_AGREEMENT_NUMBER'),
          // minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            Contract.get({
              'query': $.trim(query.term),
              'customerNumber': scope.basic.customerSelected.data
            },
            function(response) {
              if (response.length) {
                data.results = response;
                data.results.unshift(dataHead);
                query.callback(data);
              } else {
                query.callback(data);
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_AGREEMENT_NUMBER');
          },
          formatResult: function(item) {
            //deal with xss  --by ccx
            // var xssReg = /[\<\>]/g;
            // if ( matches[i].name ) {
            //   matches[i].name = matches[i].name.replace( xssReg, '' );
            // }
            if (!item.data) {
              return '<div class="headDrop"><span class="dropCol1">'+$filter('translate')('SELECTTOSUGGEST_CONTRACT_NUMBER')+'</span><span class="dropCol2">'+ $filter('translate')('SELECTTOSUGGEST_CONTRACT_TYPE') +'</span><span class="dropCol3">'+ $filter('translate')('SELECTTOSUGGEST_SUMMARY') +'</span></div>';
            }
            return '<div class="bodyDrop"><span class="dropCol1">' + item.data.number + '</span><span class="dropCol2">' + $filter('translate')( 'CONTRACT_CATEGORY_' + item.data.category ) + '</span><span class="dropCol3">' + item.data.summary + '</span></div>';
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            if (!item.data) {
              return '';
            }
            return item.data.number;
          },
          escapeMarkup: function(m) {
            return m;
          }
        };
      },
      getCustomerOptionForAdOwner: function (options) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_INPUT_ADVERTISER_NAME'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            Customer.getSuggestForAdOwner({
              'query': $.trim(query.term)
            },
            function(response) {
              if (response.length) {
                data.results = response;
              }

              //如果无suggest结果返回，以用户输入的结果增加一个term
              if ( opts.allowInput && !data.results.length ) {
                data.results.push({
                  value: $.trim(query.term)
                });
              }
              query.callback(data);
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_ADVERTISER_NAME');
          },
          formatResult: function(item) {
            //非suggest的term，即用户输入的结果，显示‘无此公司’
            if ( !item.data ) {
              return item.value + '（' + $filter('translate')('SELECTTOSUGGEST_NO_ADVERTISER_NAME') + '）';
            }
            return item.value;
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            return item.value;
          },
          width: 'element',
          allowClear: (function () {
            if ( opts && opts.allowClear ) {
              return opts.allowClear;
            }
            return false;
          })()
        };
      },
      getBenchmarkPriceSubmitter: function ( options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_INPUT_OPERATOR_NAME'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            Account.get({
              'query': $.trim(query.term)
            }).success(function (response) {
              if ( response.data ) {
                data.results = response.data;
                for (var i = data.results.length - 1; i >= 0; i--) {
                  data.results[i].value = data.results[i].data.ucid;
                }
                query.callback(data);
              }
            });
            // Account.get({
            //   'query': $.trim(query.term)
            // },
            // function(response) {
            //   if (response.length) {
            //     data.results = response;
            //     for (var i = data.results.length - 1; i >= 0; i--) {
            //       data.results[i].value = data.results[i].data.ucid;
            //     }
            //   }
            //   query.callback(data);
            // });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_SUBMITTER');
          },
          formatResult: function(item) {
            return item.data.name;
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            return item.data.name;
          },
          width: 'element',
          initSelection : function (element, callback) {
            if(opts && opts.initSelectionCallback ){
              opts.initSelectionCallback(element, callback);
            }
          },
          allowClear: true
        };
      },
      positionNumSuggest: function ( options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.value;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_POSITION_NAME'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            Position.positionSuggest( { 'query': $.trim(query.term) } ).success( function ( response ) {
              if ( response.data ) {
                data.results = response.data;
                query.callback( data );
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_POSITION_NAME');
          },
          formatResult: function(item) {
            return item.value;
          },
          formatSelection: function(item) {
            if(opts && opts.formatSelectionCallback ){
              opts.formatSelectionCallback(item);
            }
            return item.value;
          },
          width: 'element',
          initSelection : function (element, callback) {
            if(opts && opts.initSelectionCallback ){
              opts.initSelectionCallback(element, callback);
            }
          },
          allowClear: true
        };
      },
      getUserOption: function ( options ) {
        var defaults = {},
            opts = angular.extend( defaults, options );
        return {
          id: function(priv) {
            return priv.id;
          },
          placeholder: $filter('translate')('SELECTTOSUGGEST_RESPONSIBLE_HANDLER'),
          minimumInputLength: 1,
          formatInputTooShort: function(term, minLength) {
            return $filter('translate')('SELECTTOSUGGEST_PLACEINPUT') + ' ' + minLength + ' ' + $filter('translate')('SELECTTOSUGGEST_CHARACTERS');
          },
          query: function(query) {
            var data = {
              results: []
            };
            var dataHead = {};
            Account.suggestUser( { 'userName': $.trim(query.term) } ).success( function ( response ) {
              if ( response.data ) {
                data.results = response.data;
                query.callback( data );
              }
            });
          },
          formatNoMatches: function() {
            return $filter('translate')('SELECTTOSUGGEST_NO_USER');
          },
          formatResult: function(item) {
            return item.realname + '(' + item.username + ')';
          },
          formatSelection: function(item) {
            ( opts.onSelect || angular.noop )( item );
            return item.realname + '(' + item.username + ')';
          },
          width: 'element'
        };
      }
    };
  }]);
});