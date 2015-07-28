define([
  'app',
  '../_directives/ytAjaxupload'
], function (app) {

app.registerController('CtrlDefaultAd', [
  '$scope',
  '$filter',
  'PageSet',
  'ModalC',
function ($scope, $filter, PageSet, ModalC) {
  //go coding
  PageSet.set({siteName:'defaultAd'});

  $scope.uploadOpts = {
    uploadingText: $filter('translate')('IMPORT_UPLOADING'),
    uploadedText: $filter('translate')('IMPORT_GO_ON'),
    tryAgainText: $filter('translate')('IMPORT_TRY_AGAIN'),
    beginUpload : "btnDisabled",
    uploadDone : "btnEnabled"
  };

  $scope.importRet = {};

  $scope.beginUpload = function () {
  };

  $scope.uploaded = function () {
    ModalC.success({
      content: $filter('translate')('IMPORT_SUCCESSFULLY')
    });
  };

}]);

});
