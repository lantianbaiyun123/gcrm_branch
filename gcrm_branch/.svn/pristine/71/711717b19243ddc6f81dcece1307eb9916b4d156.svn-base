/**
 * [CtrlBenchmarkPriceApproval description]
 * this is controller for feature 'schdule'
 */
define([
  'app',
  '../../_common/ytCommon',
  '../../_services/PageSet',
  '../../_services/http/BenchmarkPrice',
  '../../_services/http/BillModel',
  '../../_services/http/Industry',
  '../../record/BenchmarkPriceApprovalRecord',
  '../../record/BenchmarkPriceModifyRecord',
  '../../_filters/QuotationStatusFilter',
  '../../_filters/BusinessTypeFilter',
  '../../_filters/QuotationApprovalStatusFilter',
  '../../_filters/PriceTypeFilter'
], function (app) {
app.registerController('CtrlBenchmarkPriceApproval', [
    '$scope',
    '$state',
    '$stateParams',
    '$q',
    '$log',
    '$filter',
    'PageSet',
    'Modal',
    'BenchmarkPrice',
    'BillModel',
    'BenchmarkPriceApprovalRecord',
    'BenchmarkPriceModifyRecord',
    'Industry',
  function ($scope, $state, $stateParams, $q, $log, $filter, PageSet, Modal, BenchmarkPrice, BillModel, BenchmarkPriceApprovalRecord, BenchmarkPriceModifyRecord , Industry) {
    // controller code here
    PageSet.set({activeIndex:5,siteName:'task'});

    $scope.taskRecord.quoteId =  $stateParams.quoteId;
    $scope.taskRecord.foreignKey =  $stateParams.quoteId;
    $scope.taskRecord.activityId = $stateParams.activityId;
    $scope.taskRecord.type = 'approval';

    $scope.constant = {};
    $scope.constant.industryType = {};
    $scope.constant.billingModel = [];

    $scope.submitApproval = function () {
      $scope.submitDisabble = true;   //禁用提交按钮
      $scope.checkApprovalStatus = true;
      //check审核意见、说明
      if ( $scope.form.approvalStatus !== 1 &&
           $scope.form.approvalStatus !== 0
      ) {
          return false;
      } else if( $scope.form.approvalStatus === 0) {
          $scope.checkApprovalSuggestion = true;
          if(!$scope.form.approvalSuggestion) {
              return false;
          }
      }

      var submitData = packApprovalData();

      var httpPromise = BenchmarkPrice.submitApproval(submitData);
      httpPromise.success(function (response) {
        if ( response.code === 200 ){
          Modal.success({
            title: $filter('translate')('APPROVAL_AUDIT_OPINION_SUBMIT_SUCCESS'),
            content: $filter('translate')('APPROVAL_SYSTEM_JUMP'),
            timeOut: 3000
          }, function () {
            $scope.goNextTask();
          });
        }
      });
      httpPromise['finally'](function (result) {
        $scope.submitDisabble = false;
      });
    };

    var prepareEditData = {
      unit: function (quotationList) {
        $scope.quotationList[0].unit = {};
        //CPS统一比例
        $scope.quotationList[0].unit.cps = [{}];
        var length = quotationList.length;
        for ( var i = 0; i < length; i++ ) {
          var billingModelName = $scope.constant.billingModel[quotationList[i].billingModelId].name.toLowerCase();

          if ( billingModelName === 'cps') {
            if (quotationList[i].industryId >= 0 ) {
              var industryName = $scope.constant.industryType[quotationList[i].industryId].industryTypeName,
                  parentId = $scope.constant.industryType[quotationList[i].industryId].parentId;
              $scope.quotationList[0].unit.cps.push({
                id: quotationList[i].id,
                industryId: quotationList[i].industryId,
                value: quotationList[i].ratioMine,
                industryTypeName: industryName,
                parentId: parentId
              });
            } else {
              $scope.quotationList[0].unit.cps[0].value = quotationList[i].ratioMine;
              $scope.quotationList[0].isUnifyedCPS = true;
            }
          } else {
            $scope.quotationList[0].unit[billingModelName] = {};
            $scope.quotationList[0].unit[billingModelName].id = quotationList[i].id;
            $scope.quotationList[0].unit[billingModelName].value = quotationList[i].publishPrice;
          }
        }

        //如果CPS统一比例或不维护的情况
        // if($scope.quotationList[0].unit.cps.length < 2) {
        //    $scope.quotationList[0].isUnifyedCPS = true;
        //   //克隆行业类型对象
        //   for (var id in $scope.constant.industryType) {
        //     var industryObj = {};
        //     for ( var key in $scope.constant.industryType[id] ) {
        //       industryObj[key] = $scope.constant.industryType[id][key];
        //     }
        //     $scope.quotationList[0].unit.cps.push(industryObj);
        //   }
        // }

        $log.info($scope.quotationList[0].unit.cps[0]);
      },
      ratio: function (quotationList) {
        $scope.quotationList[0].ratio = {};
        $scope.quotationList[0].ratio.id = quotationList[0].id;
        $scope.quotationList[0].ratio.ratioMine = quotationList[0].ratioMine;
        $scope.quotationList[0].ratio.ratioCustomer = quotationList[0].ratioCustomer;
        $scope.quotationList[0].ratio.ratioThird = quotationList[0].ratioThird;
      },
      rebate: function (quotationList) {
        $scope.quotationList[0].rebate = {};
        $scope.quotationList[0].rebate.id = quotationList[0].id;
        $scope.quotationList[0].rebate.ratioRebate = quotationList[0].ratioRebate;
      }
    };

    init();

    $scope.modalApprovalRecord = function () {
      BenchmarkPriceApprovalRecord.show($scope.taskRecord.quoteId, {windowClass: 'benchmark-price-approval-record'});
    };

    $scope.modalModifyRecord = function () {
      BenchmarkPriceModifyRecord.show($scope.taskRecord.quoteId, {windowClass: 'benchmark-price-modify-record'});
    };

    function init () {
      $q.all( [getBillingModel(), getIndustryType()] ).then(function () {
        getApprovalInfo();
      });
      // getBillingModel().then(function (result) {
      //   getApprovalInfo();
      // });
    }

    function getApprovalInfo () {
      BenchmarkPrice.getApprovalInfo({
        id: $scope.taskRecord.foreignKey,
        activityId: $scope.taskRecord.activityId
      }).success(function (response) {
        if ( response.code === 200 ) {
          renderDetail(response.data);
          $scope.updateTask();
        } else if ( response.code === 205 ) {
          var msg = '[' + response.code + ']' + response.message + '\n';
          Modal.alert({content: msg});
        } else {
          $scope.updateTask();
        }
      });
    }

    function packApprovalData () {
      var submitData = {
        processId: $scope.taskRecord.processId,
        activityId: $scope.taskRecord.activityId,
        actDefId: $scope.taskRecord.actDefId,
        record: {
          quoteMainId: $scope.taskRecord.quoteId,
          taskId: $scope.taskRecord.taskId,
          approvalStatus: $scope.form.approvalStatus,
          approvalSuggestion: $scope.form.approvalSuggestion,
          processId: $scope.taskRecord.processId,
          activityId: $scope.taskRecord.activityId
        }
      };

      return submitData;
    }

    function renderDetail (data) {
      $scope.quotationMain = data.quoteMainView.quotationMainVO.quotationMain;
      $scope.quotationMain.platformName = data.quoteMainView.quotationMainVO.platformName;
      $scope.quotationMain.siteName = data.quoteMainView.quotationMainVO.siteName;
      $scope.quotationMain.createrName = data.quoteMainView.quotationMainVO.createrName;

      $scope.quotationList = [{}];
      prepareEditData[$scope.quotationMain.priceType](data.quoteMainView.quotationViewList[0].quotationList);
      $scope.quotationList[0].quotationMaterialList = data.quoteMainView.quotationViewList[0].quotationMaterialList;
      $scope.quotationList[0].siteId = $scope.quotationMain.siteId;
    }

    var billingModel =[];
    function getBillingModel () {
      var deferred = $q.defer();
      BillModel.get({}, function (response) {
        if (response.code === 200 ) {
          var length = response.data.result.length;
          if ( length ) {
            for (var i = 0; i < length; i++) {
              $scope.constant.billingModel[response.data.result[i].id] = response.data.result[i];
            }
          }
        }
        deferred.resolve();
      });
      return deferred.promise;
    }

    function getIndustryType () {
      var deferred = $q.defer();
      Industry.getIndustryTypes({}).success( function (response) {
        if (response.code === 200 ) {
          // for (var i = response.data.length - 1; i >= 0; i--) {
          //   $scope.constant.industryType[response.data[i].id] = {
          //     industryId: response.data[i].id,
          //     industryTypeName: response.data[i].industryTypeName
          //   };
          // }

          var industryTypes = {};
          var length = response.data.length;
          for (var i = 0; i < length; i++) {
            var type = response.data[i],
                parentId = type.id,
                parentName = type.industryTypeName;
            if ('subIndustryType' in type) {
              var subTypes = type.subIndustryType;
              var subLength = subTypes.length;
              for (var j = 0; j < length; j++) {
                subTypes[j].parentId = parentId;
                subTypes[j].parentName = parentName;
                industryTypes[subTypes[j].id] = subTypes[j];
              }
            } else {
              type.parentId = parentId;
              type.parentName = parentName;
              industryTypes[type.id] = type;
            }
          }

          $scope.constant.industryType = industryTypes;
        }
        deferred.resolve();
      });
      return deferred.promise;
    }
  }]);
});