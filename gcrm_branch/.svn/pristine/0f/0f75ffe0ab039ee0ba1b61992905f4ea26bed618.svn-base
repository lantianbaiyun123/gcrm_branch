/**
 * [CtrlBenchmarkPriceManagement]
 * cotroller for feature 'benchmarkPriceManagement'
 */
define(['app',
        '../_common/ytCommon',
        '../_services/http/Industry',
        '../_filters/QuotationStatusFilter',
        '../_filters/BusinessTypeFilter',
        '../_filters/QuotationApprovalStatusFilter',
        '../_filters/PriceTypeFilter'
], function (app) {
  app.registerController('CtrlBenchmarkPriceManagement', [
      '$scope',
      '$log',
      '$state',
      '$q',
      'PageSet',
      'BenchmarkPriceConstant',
      'Position',
  function ($scope, $log, $state, $q, PageSet, BenchmarkPriceConstant, Position) {
    //提示信息
    $scope.msgs = {};
    $scope.msgs.listAlerts = [];//列表页提示信息
    $scope.msgs.detailAlerts = [];//详情页提示信息

    $scope.constantIsSet = false;
    BenchmarkPriceConstant.then(function (result) {
      $scope.constant = result;
      $scope.constantIsSet = true;
      $scope.$broadcast('constantReady', $scope.constant);
    });

    $scope.getSiteArea = function ( platformId ) {
      var deferred = $q.defer();
      if ( platformId ) {
        Position.getQuoteSiteArea({
          platformId: platformId
        }).success(function (response) {
          var result = [];
          if( response.code === 200 ) {
            var length = response.data.length;
            if( length ) {
              for (var i = 0; i < length; i++) {
                result.push({
                  text: response.data[i].value,
                  value: response.data[i].data
                });
              }
            }
          }
          deferred.resolve(result);
        });
      }
      return deferred.promise;
    };
  }]);

  app.registerService('BenchmarkPriceConstant', [
    '$filter',
    '$q',
    'Position',
    'BillModel',
    'Industry',
  function ($filter, $q, Position, BillModel, Industry) {
    var deferred = $q.defer(),
        translateFilter = $filter('translate'),
        // platform = [],
        // billingModel = {},
        // industryType = [],
        constants = {
          //投放平台list
          platform : [],

          //站点区域list，初始化
          siteArea: [],

          //标杆价状态
          quotationStatus: [
            {
              //未生效
              text: translateFilter('QUOTATION_STATUS_INVALID'),
              value: 'INVALID'
            },
            {
              //生效
              text: translateFilter('QUOTATION_STATUS_VALID'),
              value: 'VALID'
            },
            {
              //超期失效
              text: translateFilter('QUOTATION_STATUS_OVERDUE_INVALID'),
              value: 'OVERDUE_INVALID'
            },
            {
              //作废
              text: translateFilter('QUOTATION_STATUS_CANCEL'),
              value: 'CANCEL'
            }
          ],

          //业务类型
          businessType: [
            {
              //销售
              text: translateFilter('BUSINESS_TYPE_SALE'),
              value: 'SALE'
            },
            {
              //变现
              text: translateFilter('BUSINESS_TYPE_CASH'),
              value: 'CASH'
            }
          ],

          //价格种类
          priceType: [
            {
              text: translateFilter('PRICE_TYPE_unit'),
              value: 'unit'
            },
            {
              text: translateFilter('PRICE_TYPE_ratio'),
              value: 'ratio'
            },
            {
              text: translateFilter('PRICE_TYPE_rebate'),
              value: 'rebate'
            }
          ],

          //标杆价审核状态
          approvalStatus: [
            {
              //待提交
              text: translateFilter('QUOTATION_APPROVAL_STATUS_SAVING'),
              value: 'SAVING'
            },
            {
              //审核中
              text: translateFilter('QUOTATION_APPROVAL_STATUS_APPROVING'),
              value: 'APPROVING'
            },
            {
              //审核通过
              text: translateFilter('QUOTATION_APPROVAL_STATUS_APPROVED'),
              value: 'APPROVED'
            },
            {
              //审核驳回
              text: translateFilter('QUOTATION_APPROVAL_STATUS_CANCEL'),
              value: 'CANCEL'
            }
          ],

          billingModel: {},

          //cps行业类型
          industryType: {}
        };

    function getPlatform () {
      var deferred = $q.defer();
      Position.getQuoteAllPlatform().success(function (response) {
        if ( response.code === 200 ) {
          var length = response.data.length;
          if ( length ) {
            var businessTypeMap = ['CASH', 'SALE'];
            for (var i = 0; i < length; i++) {
              constants.platform.push({
                text: response.data[i].i18nName,
                value: response.data[i].id,
                businessType: businessTypeMap[response.data[i].businessType]
              });
            }
          }
        }
        deferred.resolve();
      });
      return deferred.promise;
    }

    function getBillingModel () {
      var deferred = $q.defer();
      BillModel.get({}, function (response) {
        if (response.code === 200 ) {
          // billingmodel = {};
          var length = response.data.result.length;
          if ( length ) {
            for (var i = 0; i < length; i++) {
              constants.billingModel[response.data.result[i].id] = response.data.result[i];
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
          //   constants.industryType[response.data[i].id] = {
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

          constants.industryType = industryTypes;
        }
        deferred.resolve();
      });
      return deferred.promise;
    }


    $q.all( [getPlatform(), getBillingModel(), getIndustryType()] ).then(function () {
      deferred.resolve(constants);
    });

    return deferred.promise;
  }]);
});
