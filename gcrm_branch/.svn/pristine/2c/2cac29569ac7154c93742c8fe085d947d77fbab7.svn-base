define(['app'], function (app) {
  app.registerFilter('PositionFilter', function () {
    var category = {
      status: {
        enable: 'POSITION_STATUS_ENABLE',
        disable: 'POSITION_STATUS_DISABLE'
      },
      rotationType: {
        yes: 'POSITION_TYPE_ROTATION',
        no: 'POSITION_TYPE_STATIC'
      },
      rotationTypeInt: {
        0: 'POSITION_TYPE_STATIC',
        1: 'POSITION_TYPE_ROTATION'
      },
      materialType: {
        0: 'POSTIION_MATERIAL_TYPE_IMAGE',
        1: 'POSTIION_MATERIAL_TYPE_TEXT',
        2: 'POSTIION_MATERIAL_TYPE_IMAGE_AND_TEXT',
        3: 'POSTIION_MATERIAL_TYPE_CUSTOMER'
      }
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