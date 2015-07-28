/**
 * [description] 弹出提示浮层
 * [USAGE]
 * @param  {[type]} app [description]
 * @return {[type]}     [description]
 */
define(['app'], function ( app ) {
  app.registerController('CtrlModal', ['$scope', '$modalInstance', 'opts', '$filter',function ($scope, $modalInstance, opts, $filter) {
    var translateFilter = $filter('translate');
    $scope.title = opts.title;
    $scope.content = opts.content;
    $scope.contentList = opts.contentList;
    $scope.okText = opts.okText || translateFilter('CONFIRM');
    $scope.cancelText = opts.cancelText || translateFilter('CANCEL');
    $scope.nextText = opts.nextText || translateFilter('RETURN');
    $scope.showCancel = opts.showCancel || false;
    $scope.showReturn = opts.showReturn || false;
    $scope.modalDatas = opts.modalDatas;
    $scope.errors = opts.errors;

    //如果有附加的信息，加入
    if ( opts.psInfo ) {
        $scope.psInfo = translateFilter('PS') + opts.psInfo;
    }
    $scope.ok = function () {
        $modalInstance.close('ok');
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.back = function () {
        $modalInstance.close('back');
    };

  }]);
});