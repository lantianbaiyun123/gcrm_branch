define([
  'app',
  './ServiceNoticeHttp'
], function (app) {
  app.registerFactory('ModalNotice', [
    '$modal',
    '$q',
    '$timeout',
    '$filter',
    'STATIC_DIR',
    'NoticeHttp',
  function ($modal, $q, $timeout, $filter, STATIC_DIR, NoticeHttp) {
      return {
        show: function (opts) {
          var
            defered = $q.defer(),
            optsDefault = {};

          NoticeHttp.getRead({
            noticeId: opts.noticeId
          }).success(function (response) {
            if (response.code === 200 && response.data) {
              opts.notice = {
                title: response.data.title,
                content: response.data.content
              };
              var modalInstance = $modal.open({
                templateUrl: STATIC_DIR + 'app/notice/modalNotice.tpl.html',
                controller: 'CtrlModalNotice',
                // backdrop : 'static',
                windowClass: 'modal-notice',
                resolve: {
                  opts: function () {
                    return angular.extend(optsDefault, opts);
                  }
                }
              });
              modalInstance.result.then(function (result) {
                defered.resolve(result);
              });
            }
          });

          return defered.promise;
        }
      };
  }]);

  app.registerController('CtrlModalNotice', [
    '$scope',
    '$modalInstance',
    'opts',
    '$filter',
    '$sce',
  function ($scope, $modalInstance, opts, $filter, $sce) {
    $scope.notice = opts.notice;

    $scope.deliberatelyTrustDangerousContent = function() {
      return $sce.trustAsHtml($scope.notice.content);
    };

    $scope.ok = function () {
      $modalInstance.close('ok');
    };

    $scope.cancel = function () {
      $modalInstance.dismiss('cancel');
    };
  }]);

});