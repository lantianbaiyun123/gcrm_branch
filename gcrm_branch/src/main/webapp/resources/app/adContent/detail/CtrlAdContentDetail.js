define([ 'app', '../../_common/ytCommon',
        '../../_filters/AdApprovalStatusFilter',
        '../../_filters/RecordOperateTypeFilter',
        '../../_filters/PriceTypeFilter',
        '../../_filters/AdSolutionTypeFilter',
        '../../_filters/AdMaterialIfEmbedCodeFilter',
        '../../_filters/BoolValueFilter',
        '../../_filters/IndustryTypeFilter',
        '../../_filters/urlHttpFilter',
        '../../_directives/periodLabel',
        '../../record/SolutionApprovalRecord'
        ], function ( app ) {
  app.registerController( 'CtrlAdContentDetail', [
   '$scope', '$stateParams', 'AdProgram', 'SolutionApprovalRecord', 'PageSet',
    function ( $scope, $stateParams, AdProgram, SolutionApprovalRecord, PageSet ) {
      if ( !$stateParams.id ) {
        return false;
      }
      PageSet.set({
        activeIndex: 1,
        siteName:'adContentDetail'
      });
      AdProgram.scheduleInfoGet({
          id: $stateParams.id
      }, function ( response ) {
        if ( response.code === 200 ) {
          $scope.solutionInfo = response.data;
        }
      });
      $scope.btnApprovalRecord = function () {
        SolutionApprovalRecord.forContent($stateParams.id, {windowClass: 'approval-record-modal'});
      };
    }
  ]);
});