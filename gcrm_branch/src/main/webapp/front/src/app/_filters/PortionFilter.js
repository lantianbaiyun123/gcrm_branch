define(['app'], function (app) {
  app.registerFilter('PortionFilter', function () {
    return function ( input ) {
      if ( input <= 1 ) {
        return ( input * 100 ).toFixed( 2 ) + '%';
      }
      return input;
    };
  });
});