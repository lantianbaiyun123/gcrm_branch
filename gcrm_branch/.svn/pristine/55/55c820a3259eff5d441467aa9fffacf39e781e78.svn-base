define([
  'app',
  './ServiceNotice',
  'angular-summernote',
  'anuglar-ui-select2'
], function (app) {
  app.registerController('CtrlNotice', [
    '$scope',
    '$stateParams',
    '$location',
    'PageSet',
    'Notice',
  function ($scope, $stateParams, $location, PageSet, Notice) {
    $scope.notice = {
      id: $stateParams.id,
      receiveScope: 'external',
      isOwner: true,
      users: [],
      customers: []
    };
    $scope.state = {};
    $scope.e = {};
    $scope.btnPermit = {};

    if ($scope.notice.id) {
      PageSet.set({siteName:'noticeDetail'});
      Notice.initNoticeView($scope).then(function () {
        Notice.updateBtnPermit($scope);
        if ($stateParams.isEdit && $scope.btnPermit.edit) {
          Notice.setStateEdit($scope);
        } else {
          // 清掉isEdit
          $location.search({id: $scope.notice.id});
        }
      });
    } else {
      PageSet.set({siteName:'noticeAdd'});
      Notice.updateBtnPermit($scope);
      Notice.setStateEdit($scope);
    }
  }]);

  app.registerController('CtrlNoticeEdit', [
    '$scope',
    '$state',
    '$location',
    '$timeout',
    'PageSet',
    'Notice',
    'NoticeSuggest',
    'ModalC',
  function ($scope, $state, $location, $timeout, PageSet, Notice, NoticeSuggest, ModalC) {

    $scope.summernoteOpts = {
      height: 300,
      toolbar: [
        ['style', ['bold', 'italic', 'underline', 'clear']],
        ['fontsize', ['fontsize']],
        ['color', ['color']],
        ['para', ['ul', 'ol', 'paragraph']],
        ['height', ['height']],
        ['insert', ['link']]
      ],
      lang: $scope.lang
    };

    $scope.e.userOption = NoticeSuggest.getUser();
    $scope.e.adOwnerOption = NoticeSuggest.getAdOwner();

    $scope.saveSend = function () {
      if (Notice.validate($scope)) {
        Notice.saveSend($scope.e.formData).success(function (response) {
          if (response.code === 200 && response.data) {
            var noticeData = response.data;
            ModalC.success({
              content: '公告发送成功'
            }).then(function () {
              // Notice.updateView($scope, noticeData);
              $state.go('noticeList');
            });
          }
        });
      }
    };

    $scope.save = function () {
      if (Notice.validate($scope)) {
        Notice.save($scope.e.formData).success(function (response) {
          if (response.code === 200 && response.data) {
            var noticeData = response.data;
            ModalC.success({
              content: '公告保存成功'
            }).then(function () {
              Notice.updateView($scope, noticeData);
            });
          }
        });
      }
    };

    $scope.cancel = function () {
      if ($scope.notice.id) {
        // 详情编辑时，返回详情预览视图
        Notice.setStateView($scope);
        // 清掉isEdit
        $location.search({id: $scope.notice.id});
      } else {
        // 新建时，返回列表页
        $state.go('noticeList');
      }
    };

    function validate() {
      $scope.state.onSave = true;
    }

  }]);

  app.registerController('CtrlNoticeDetail', [
    '$scope',
    '$state',
    'PageSet',
    'Notice',
    'ModalC',
    '$sce',
  function ($scope, $state, PageSet, Notice, ModalC, $sce) {

    $scope.deliberatelyTrustDangerousContent = function() {
      return $sce.trustAsHtml($scope.notice.content);
    };

    $scope.copy = function () {
      var newData = angular.copy($scope.notice);
      delete newData.id;
      Notice.save(newData).success(function (response) {
        if (response.code === 200) {
          $state.go('notice.facade', {id: response.data.id, isEdit: 1});
        }
      });
    };

    $scope.send = function () {
      Notice.send($scope.notice.id).success(function (response) {
        if (response.code === 200 && response.data) {
          var noticeData = response.data;
          ModalC.success({
            content: '公告发送成功'
          }).then(function () {
            // Notice.updateView($scope, noticeData);
            $state.go('noticeList');
          });
        }
      });
    };

    $scope.edit = function () {
      Notice.setStateEdit($scope);
    };

  }]);
});
