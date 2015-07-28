/**
 * [物料详情页Ctrl]
 */
define([
  'app',
  '_services/http/Material',
  '_filters/PositionFilter',
  'record/MaterialApprovalRecord',
  './MaterialEditModal',
  '_directives/ytPopoverConfirm',
  '_directives/periodLabel'
],function (app) {

  app.registerController('CtrlMaterialDetail', [
    '$scope',
    '$log',
    '$stateParams',
    '$filter',
    '$q',
    '$timeout',
    'Modal',
    'PageSet',
    'Material',
    'MaterialEditModal',
    'MaterialApprovalRecord',
  function ( $scope, $log, $stateParams, $filter, $q, $timeout, Modal, PageSet, Material, MaterialEditModal, MaterialApprovalRecord) {
    PageSet.set({activeIndex:1, siteName:'materialDetail'});

    $scope.materialInfo = {};
    $scope.materialInfo.contentId = $stateParams.contentId;
    init();

    $scope.toggleDetail = function (index) {
      if ( !$scope.materialList[index].showDetail ) {
        Material.getDetail({
          applyId: $scope.materialList[index].applyId
        }).success(function (response) {
          if ( response.code === 200 ) {
            $scope.materialList[index].detail = response.data;
            $scope.materialList[index].showDetail = !$scope.materialList[index].showDetail;
          }
        });
      } else {
        $scope.materialList[index].showDetail = !$scope.materialList[index].showDetail;
      }
    };

    /**
     * materialSaveType:
     * 0: create: 新增
     * 1: change: 变更
     * 2: update: 修改
     * 3: recovery: 恢复
     */

    $scope.addMaterial = function () {
      materialEdit($scope.saveType);
    };

    $scope.editMaterial = function (material) {
      Material.getDetail({
        applyId: material.applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          materialEdit(2, response.data);
        }
      });
    };

    $scope.recoverMaterial = function (material) {
      Material.getDetail({
        applyId: material.applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          materialEdit(3, response.data);
        }
      });
    };

    $scope.withdrawMaterial = function (applyId) {
      Material.withdraw({
        applyId: applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          $scope.materialList = response.data.materialApplyVoList;
          renderList();
          $scope.saveType = 1; //reset，提交成功，当前saveType只能是1:变更
          Modal.success({
            content: $filter('translate')('MATERIAL_WITHDRAW_SUCCESS'),
            timeOut: 3000
          });
        }
      });
    };

    $scope.nullifyMaterial = function (applyId) {
      Material.nullify({
        applyId: applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          $timeout(function () {
            render(response.data);
          });
          Modal.success({
            content: $filter('translate')('MATERIAL_NULLIFY_SUCCESS'),
            timeOut: 3000
          });
        }
      });
    };

    $scope.sendReminder = function (applyId) {
      Material.sendReminder({
        applyId: applyId
      }).success(function (response) {
        if ( response.code === 200 ) {
          Modal.success({
            content: $filter('translate')('SUCCESS_REMIDER'),
            timeOut: 3000
          });
        }
      });
    };

    $scope.modalApprovalRecord = function (materialId) {
      MaterialApprovalRecord.show(materialId);
    };

    function init () {
      getDetailInfo().then(function () {
        $scope.addEnable = true;
        renderList();
      });
    }

    function renderList () {
      if ( $scope.materialList ) {
        for (var i = $scope.materialList.length - 1; i >= 0; i--) {
          //待提交、审核中、审核驳回，默认展开详细信息
          var applyState = ~~$scope.materialList[i].applyState;
          if ( applyState === 0 || applyState === 1 || applyState ===4 ) {
            $scope.toggleDetail(i);
          }
        }
      }
    }

    function materialEdit ( saveType, materialData ) {
      MaterialEditModal.show({
        saveType: saveType,
        adContentInfo: $scope.adContentInfo,
        materialData: materialData || {}
      }).then( function (result) {
        var response = response.response;
        if ( response.code === 200 ) {
          $scope.materialList = response.data.materialApplyVoList;
          renderList();
          $scope.saveType = 1;  //reset，提交成功，当前saveType只能是1:变更
          Modal.success({
            content: $filter('translate')('MATERIAL_SAVE_SUCCESS'),
            timeOut: 3000
          });
        }
      });
    }

    function getDetailInfo () {
      var deffered = $q.defer();
      Material.getDetailInfo({
        contentId: $scope.materialInfo.contentId
      }).success(function (response) {
        if ( response.code === 200 ) {
          render(response.data);
          deffered.resolve();
        }
      });
      return deffered.promise;
    }

    function render (data) {
      $scope.saveType = data.materialSaveType;
      $scope.adContentInfo = data.materialContentVo;
      $scope.materialList = data.materialApplyVoList;
    }
  }]);
});