define([
  'app',
  '../_services/http/Publication',
  '../_directives/ytJqueryFileUpload',
  '../_directives/ytAjaxupload',
  '../_directives/ytPlaceholder'
], function ( app ) {
  app.registerFactory( 'ForcePublishModal', [
    '$modal',
    'Publication',
    'STATIC_DIR',
  function ( $modal, Publication, STATIC_DIR ) {
    return {
      show: function (opts, cbfn) {
        var optsDefault = {};
        var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/publication/forcePublishModal.tpl.html',
            controller: 'CtrlForcePublish',
            windowClass: 'force-publish-modal',
            backdrop: 'static',
            resolve: {
              opts : function () {
                return angular.extend(optsDefault, opts);
              }
            }
        });

        modalInstance.result.then(function (result) {
            if (result.btnType === 'ok') {
              cbfn(result.response);
            }
        });
      }
    };
  }]);

  app.registerController('CtrlForcePublish', [
    '$scope',
    '$log',
    '$modalInstance',
    '$filter',
    '$q',
    'Utils',
    'Publication',
    'opts',
  function ($scope, $log, $modalInstance, $filter, $q, Utils, Publication, opts) {

      $scope.title = opts.title;
      $log.info(opts);
      $scope.certData = {};
      $scope.certData.id = opts.periodId;
      $scope.certData.certType = 'online_approval';
      $scope.certData.attachments = [];


      //附件上传控件配置
      $scope.attachmentUploadOpts = {
        uploadedText: $filter('translate')('UPLOAD_ATTACHMENT'),
        formatErrorText: "UPLOAD_FILE_TYPE_FORBIDDEN"
      };

      $scope.beginUpload = function () {
        $scope.submitDisabled = true;
      };

      $scope.uploaded = function () {
        $scope.submitDisabled = false;
      };

      $scope.removeAttachment = function (index) {
        $scope.certData.attachments.splice(index, 1);
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };

      $scope.ok = function () {
        $scope.submitDisabled = true;
        if (validate()) {
          var submitData = prepareSubmitData();
          var promise = Publication.forcePublish({
            id: $scope.certData.id,
            proofList: submitData
          });
          promise.success(function (response) {
            if ( response.code === 200 ) {
              $modalInstance.close({
                btnType: 'ok',
                response: response
              });
            }
          });
          promise['finally'](function () {
            $scope.submitDisabled = false;
          });
        } else {
          $scope.submitDisabled = false;
        }
      };

      function validate () {
        var certType = $scope.certData.certType;
            isGood = true;

        //凭证类型为线上审批流，校验url
        if ( certType === 'online_approval' ) {
          isGood = checkUrl();
        //凭证类型为附件,校验Attachment
        } else if ( certType === 'offline_attachment' ) {
          isGood = checkAttachment();
        }

        return isGood;
      }

      function checkUrl () {
        $scope.checkingUrl = true;
        return !!$scope.certData.processUrl && Utils.urlValidator($scope.certData.processUrl);
      }

      function checkAttachment () {
        $scope.checkAttachment = true;
        return !!$scope.certData.attachments.length;
      }

      function prepareSubmitData () {
        var submitData = [],
            certData = $scope.certData;
        if ( certData.certType === 'online_approval' ) {
          submitData.push({
            type: 'online_approval',
            approvalUrl: certData.processUrl
          });
        } else if ( certData.certType === 'offline_attachment' ) {
          submitData.attachments = angular.copy(certData.attachments);
          for (var i = certData.attachments.length - 1; i >= 0; i--) {
            submitData.push({
              type: 'offline_attachment',
              fileUrl: certData.attachments[i].fileUrl,
              downloadFilename: certData.attachments[i].downloadFileName,
              uploadFilename: certData.attachments[i].uploadFileName
            });
          }
        } else {
          submitData.push({type: 'none'});
        }

        return submitData;
      }
  }]);
});