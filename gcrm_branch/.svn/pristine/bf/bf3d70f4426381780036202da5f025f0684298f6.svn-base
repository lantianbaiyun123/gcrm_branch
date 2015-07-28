define(['app'], function (app) {
    app.registerFilter('ContractFilter', function () {
        var type = {
            VALID: 'CONTRACT_STATUS_VALID',
            INVALID: 'CONTRACT_STATUS_INVALID'
        };
        return function ( input ) {
          if ( input && type[input] ) {
            return type[ input ];
          }
          return '--';
        };
    });
});