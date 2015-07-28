define(['app'], function (app) {
  app.registerFilter('CustomerFilter', function () {
    var type = {
      offline: 'CUSTOMER_TYPE_OFFLINE',
      direct: 'CUSTOMER_TYPE_DIRECT',
      nondirect: 'CUSTOMER_TYPE_NONDIRECT',
      union: 'CUSTOMER_TYPE_UNION'
    };
    return function ( input ) {
      if ( input && type[input] ) {
        return type[ input ];
      }
      return input;
    };
  });
  app.registerFilter('CompanySizeFilter', function () {
    var type = {
      less_50: 'COMPANY_SIZE_LESS_50',
      less_100: 'COMPANY_SIZE_LESS_100',
      less_200: 'COMPANY_SIZE_LESS_200',
      less_500: 'COMPANY_SIZE_LESS_500',
      less_1000: 'COMPANY_SIZE_LESS_1000',
      more_1000: 'COMPANY_SIZE_MORE_1000'
    };
    return function ( input ) {
      if ( input && type[input] ) {
        return type[ input ];
      }
      return input;
    };
  });
  app.registerFilter('AgentTypeFilter', function () {
    var type = {
      exclusive: 'CUSTOMER_TYPE_OFFLINE',
      normal: 'CUSTOMER_TYPE_DIRECT',
      second: 'CUSTOMER_TYPE_NONDIRECT'
    };
    return function ( input ) {
      if ( input && type[input] ) {
        return type[ input ];
      }
      return input;
    };
  });
});