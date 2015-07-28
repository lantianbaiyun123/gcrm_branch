define(['app'], function (app) {
  app.registerFilter('ApprovalStatusFilter', function () {
    var
    status = {
      common: {
        SAVING: 'APPROVAL_STATUS_SAVING',
        APPROVING: 'APPROVAL_STATUS_APPROVING',
        APPROVED: 'APPROVAL_STATUS_APPROVED',
        CANCEL: 'APPROVAL_STATUS_CANCEL'
      },
      quotation: {
        SAVING: 'QUOTATION_APPROVAL_STATUS_SAVING',
        APPROVING: 'QUOTATION_APPROVAL_STATUS_APPROVING',
        APPROVED: 'QUOTATION_APPROVAL_STATUS_APPROVED',
        CANCEL: 'QUOTATION_APPROVAL_STATUS_CANCEL'
      },
      material: {
        0: 'MATERIAL_APPROVEL_STATUS_create',
        1: 'MATERIAL_APPROVEL_STATUS_submit',
        2: 'MATERIAL_APPROVEL_STATUS_confirm',
        3: 'MATERIAL_APPROVEL_STATUS_cancel',
        4: 'MATERIAL_APPROVEL_STATUS_refused'
      }
    };
    return function ( input, category ) {
      var cate = category || 'common';
      if ( status[cate][input] ) {
        return status[cate][input];
      } else {
        return '--';
      }
    };
  });
});