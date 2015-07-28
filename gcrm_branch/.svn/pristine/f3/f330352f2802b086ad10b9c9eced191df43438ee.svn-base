define([
 'app',
 '_common/ytCommon',
 '_common/modal/Modal',
 '_filters/ContractFilter',
 '_filters/AdSolutionTypeFilter',
 '_filters/DatePeriodFilter',
 '_directives/ytInputDropdown',
 '_services/Select2Suggest',
 'anuglar-ui-select2'
], function (app) {
  app.registerController('CtrlAdSolutionList', [
    '$scope',
    'PageSet',
    'AdProgram',
    'Select2Suggest',
    '$filter',
    'Modal',
    '$modal',
    '$state',
    'AdSolutionListConstant',
    '$stateParams',
    '$location',
    'Ad',
    '$window',
    'STATIC_DIR',
  function ($scope, PageSet, AdProgram, Select2Suggest, $filter, Modal, $modal, $state, AdSolutionListConstant, $stateParams, $location, Ad, $window, STATIC_DIR) {

      var translateFilter = $filter('translate');
      //初始化查询条件
      var query = {
        initQuery: function () {
          $scope.queryTypes = AdSolutionListConstant.queryTypes;
          $scope.solutionStatus = AdSolutionListConstant.solutionStatus;
          $scope.qForm = {};
          $scope.qForm.queryType = $scope.queryTypes[0];
          $scope.optionCustomer = Select2Suggest.getCustomerOption({placeholder: translateFilter('AD_SOLUTION_LIST_PLACEHOLDER_COMPANY_NAME')});
          $scope.pager = {
            pageNumber: 1,
            pageSize: 10,
            pageSizeSlots: [10,20,30,50],
            totalCount: 0
          };
          $scope.queryDate = {};
          if ( $stateParams.adNumber ) {
            $scope.queryString.adNumber = $stateParams.adNumber;
          }
          if ( $stateParams.customerId ) {
            $scope.fromCustomerList = true;
          } else {
            //页头
            PageSet.set({
              siteName: 'adSolutionList',
              activeIndex: 1
            });
          }
        },
        doQuery: function () {
          AdProgram.getList( this.getQueryParams() ).success(function ( response ) {
            if ( response.code === 200 ) {
              $scope.ads = response.data.result;
              $scope.pager.totalCount = response.data.totalCount;
              $scope.pager.totalPages = response.data.totalPages;
            }
          });
        },
        getQueryParams: function () {
          var qForm = $scope.qForm,
              queryString = qForm.queryString;
          if ( qForm.queryType.value === 'customerid' ) {
            queryString = qForm.customer.data;
          }
          return {
            cusid: $stateParams.customerId,
            queryType: qForm.queryType.value,
            queryStr: queryString,
            solutionStatus: qForm.solutionStatus,
            pageSize: $scope.pager.pageSize,
            pageNumber: $scope.pager.pageNumber,
            startDate: $filter('date')($scope.queryDate.startDate, this.dateFormat),
            endDate: $filter('date')($scope.queryDate.endDate, this.dateFormat)
          };
        },
        dateFormat: 'yyyy-MM-dd',
        resetPageNumber: function () {
          $scope.pager.pageNumber = 1;
        },
        clearQueryTypeText: function () {
          $scope.queryString.adNumber = null;
          $scope.queryString.contractNumber = null;
          $scope.queryString.advertiser = null;
          $scope.queryString.customer = null;
        }
      };
      query.initQuery();
      // query.doQuery();
      //更改分页大小
      $scope.setPageSize = function (size) {
        $scope.pager.pageSize = size;
        query.resetPageNumber();
        query.doQuery();
      };
      //监听查询类型，如果是所属销售，切换为其suggest input
      $scope.$watch('qForm.queryType', function ( newValue ) {
        if ( newValue ) {
          $scope.qForm.customer = undefined;
          $scope.qForm.queryString = '';
        }
      });
      //设置查询的方案状态
      $scope.setQueryStatus = function (index) {
        $scope.queryStatus = $scope.queryStatusMap[index];
      };
      //显示或隐藏广告内容
      $scope.toggle = function (index, ad) {
        if ( !ad.showDetail ) {
          AdProgram.getContentList( {id: ad.id }).success(function ( response ) {
            if ( response.code === 200 ) {
              if ( response.data.length ) {
                ad.contentList = response.data;
                ad.showDetail = !ad.showDetail;
              } else {
                Modal.alert({ content: AdSolutionListConstant.noContentWarn});
              }
            }
          });
        } else {
          ad.showDetail = !ad.showDetail;
        }
      };
      //点击了查询
      $scope.btnQuery = function () {
        query.resetPageNumber();
        query.doQuery();
      };
      //当前页变化监听，相当于点击了分页
      $scope.$watch( "pager.pageNumber", function( newValue ){
        if ( newValue ) {
          query.doQuery();
        }
      });
      //内容排期确认
      //先去查看内容是否超期，如果超期，则弹出确认框，如果没，则直接确认
      // $scope.optContentScheduleConfirm = function (content, ad) {
      //   Ad.overdue( { id: content.adSolutionContent.id } ).success( function ( response ) {
      //     if ( response.code === 200 ) {
      //       if ( response.data.overdue ) {
      //         Modal.confirm({content: translateFilter('AD_SOLUTION_DETAIL_WARN_CONFIRM'), psInfo: translateFilter('AD_SOLUTION_DETAIL_WARN_CONFIRM_PS')}, function () {
      //             Ad.confirmContentSchedule({id: content.adSolutionContent.id, overdue: response.data.overdue }).success(function (response) {
      //               if ( response.code === 200 ) {
      //                 Modal.alert({content: translateFilter('AD_SOLUTION_LIST_CONTENT_CONFIRMED')}, function () {
      //                   content.adSolutionContent.approvalStatus = 'confirmed';
      //                   content.adSolutionContent.periodDescription = content.schedulePeriod;
      //                   if ( checkAdConfirmed( ad ) ) {
      //                     ad.approval_status = 'confirmed';
      //                   }
      //                 });
      //               }
      //             });
      //         });
      //       } else {
      //         Ad.confirmContentSchedule({id: content.adSolutionContent.id, overdue: response.data.overdue }).success(function (response) {
      //           if ( response.code === 200 ) {
      //             Modal.alert({content: translateFilter('AD_SOLUTION_LIST_CONTENT_CONFIRMED')}, function () {
      //               content.adSolutionContent.approvalStatus = 'confirmed';
      //               content.adSolutionContent.periodDescription = content.schedulePeriod;
      //               if ( checkAdConfirmed( ad ) ) {
      //                 ad.approval_status = 'confirmed';
      //               }
      //             });
      //           }
      //         });
      //       }
      //     }
      //   });
      // };
      function checkAdConfirmed ( ad ) {
        var flag = true;
        for (var i = 0; i < ad.contentList.length; i++) {
          if ( ad.contentList[i].adSolutionContent.approvalStatus === 'unconfirmed' ) {
            flag = false;
            break;
          }
        }
        return flag;
      }
      $scope.optOperatorDetail = function (ad) {
        // if ( ad.approval_status === 'saving' ||
        //      ad.approval_status === 'refused' ) {
          //如果是未提交状态，跳转至广告方案添加页面
          // $state.go('ad.facade', {programId: ad.id});
        // } else {
          // $state.go('adSolutionDetail', {id: ad.id});
        // }
        $window.open( '#/ad2?id=' + ad.id, '_blank' );
      };
      $scope.optProgramChange = function (ad) {
        Modal.confirm({
            content: $filter('translate')('AD_SOLUTION_PROGRAM_CHANGE_COMFIRM') + '?'
          }, function() {
            Ad.changeAdSolution({
                id: ad.id
            }).success(function ( response ) {
              if( response && response.code === 200 ){
                Modal.success({content: translateFilter('AD_SOLUTION_LIST_AD_CHANGED')}, function () {
                  $state.go('ad.facade', { programId: response.data.adSolution.id });
                });
              }
            });
          }
        );
      };
      $scope.optOperatorTransfer = function (ad) {
        var modalInstance = $modal.open({
            templateUrl: STATIC_DIR + 'app/adSolution/list/operatorTransferModal.tpl.html',
            windowClass: 'operator-transfer-modal',
            controller: ['$scope', 'opts', '$modalInstance','Select2Suggest', '$filter','Modal', 'AdProgram', function ($scope, opts, $modalInstance,Select2Suggest,$filter,Modal, AdProgram) {
              var translateFilter = $filter('translate');
              $scope.title = opts.title;
              $scope.okText = 'ok';
              $scope.cancelText = 'cancel';

              AdProgram.findOperator({id: opts.adSolutionId}).success(function (response ) {
                if ( response.code === 200 ) {
                  $scope.originOperator = response.data;
                }
              });
              $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
              };
              $scope.ok = function () {
                $scope.stateValidating = true;
                if( $scope.modalCustomer ){
                  AdProgram.operatorChange({
                      adsolutionid: ad.id,
                      ucid: $scope.modalCustomer.ucid,
                      operator: $scope.modalCustomer.name
                    }, function( response ){
                      if( response.data && response.code === 200 ){
                        $modalInstance.close('ok');
                        Modal.success({content : translateFilter('AD_SOLUTION_OPERATOR_CHANGE')});
                      }
                    }
                  );
                }
              };
              /*转移执行人Suggestion*/
              $scope.modalOptionCustomer = Select2Suggest.getOperatorOption({
                formatSelectionCallback: function(item) {
                  $scope.modalCustomer = item.data;
                }
              });
            }],
            resolve: {
              opts : function () {
                return {
                  title: translateFilter('AD_SOLUTION_LIST_TRANSFER'),
                  adSolutionId: ad.id
                };
              }
            }
        });

        modalInstance.result.then(function (selectedItem) {
          query.doQuery();
        });
      };
      //催办
      $scope.optPress = function (ad) {
        Ad.press({
          id: ad.id
        }).success( function ( response ) {
          if ( response.code === 200 ) {
            Modal.success({content: translateFilter('AD_SOLUTION_LIST_PRESS_SUCCESS')});
          }
        });
      };
      //新窗打开cms链接
      $scope.optToCMS = function ( ad ) {
        $window.open( ad.cmsUrl );
      };
  }]);
  app.registerService('AdSolutionListConstant', ['$filter', function ($filter) {
    var translateFilter = $filter('translate');
    return {
      noContentWarn: translateFilter('AD_SOLUTION_LIST_NO_CONTENT'),
      solutionStatus: [
        'saving',
        'approving',
        'refused',
        'approved',
        // 'unconfirmed',
        // 'confirmed',
        'effective',
        'cancel'
      ],
      queryTypes: [
        {
          i18nName: translateFilter('AD_NUMBER'),
          value: 'number'
        },
        {
          i18nName: translateFilter('AD_CONTRACT'),
          value: 'contractnum'
        },
        {
          i18nName: translateFilter('ADVERTISER'),
          value: 'advertisers'
        },
        {
          i18nName: translateFilter('COMPANY_NAME'),
          value: 'customerid'
        },
        {
          i18nName: translateFilter('AD_SOLUTION_LIST_PO'),
          value: 'ponumber'
        }
      ]
    };
  }]);
});