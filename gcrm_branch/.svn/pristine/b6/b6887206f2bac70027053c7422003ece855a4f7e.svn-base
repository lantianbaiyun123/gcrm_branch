define( [ 'app' ], function ( app ) {
  app.registerFactory('Customer', ['$http', 'APP_CONTEXT', '$filter', 'Utils',
      function ( $http, APP_CONTEXT, $filter, Utils ) {
        function belongSalesSuggest ( paramObj ) {
          return $http({
            method: 'get',
            // url: '/data/belongSalesSuggest.json',
            url: APP_CONTEXT + 'customer/belongSalesSuggest',
            params: paramObj
          });
        }
        function agentCompanySuggest ( paramObj ) {
          return $http({
            method: 'get',
            url: APP_CONTEXT + 'customer/agentCompanySuggest',
            params: paramObj
          });
        }
        return {
          getSuggest: function (paramObj, cbfn) {
            $http({
              method: "get",
              // url: "../resources/data/customersSuggest.json",
              url: APP_CONTEXT + 'adbaseinfo/customersSuggest',
              params: paramObj
            }).then(function (response) {
              cbfn(response.data.data);
            });
          },
          getSuggestForAdOwner: function (paramObj, cbfn) {
            $http({
              method: "get",
              url: APP_CONTEXT + 'adsolution/content/customersSuggest',
              params: paramObj
            }).then(function (response) {
              cbfn(response.data.data);
            });
          },
          getSuggestPromise: function (paramObj) {
            return $http({
              method: "get",
              // url: "../resources/data/customersSuggest.json",
              url: '/gcrm/adbaseinfo/customersSuggest',
              params: paramObj
            }).then(function (response) {
              return response.data.data;
            });
            //why below not working
            //.success function(response) {
            //     return response.data;
            // }
            // The major difference between the 2 is that .then() call returns a promise (resolved with a value returned from a callback) while .success() is more traditional way of registering callbacks and doesn't return a promise.
            // http://stackoverflow.com/questions/16385278/angular-httppromise-difference-between-success-error-methods-and-thens-a
          },
          get: function (paramObj) {
            return $http({
              method: 'get',
              // url: "../resources/data/customersSuggest.json",
              url: '/gcrm/adbaseinfo/queryCustomer',
              params: paramObj
            });
          },
          getListQueryView: function  (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/customerListQueryView.json',
              url: APP_CONTEXT + 'customer/initQueryView'
            });
          },
          getList: function (paramObj) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/query',
              // url: '/data/customerList.json',
              data: angular.toJson(paramObj)
            });
          },
          /**
           * [销售转移]
           * @param   paramObj:{customerId: 122， salesId：123}
           * @return HttpPromise
           */
          salesTransfer: function (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/customerList.json',
              url: APP_CONTEXT + 'customer/updateSales/'+ paramObj.customerId +'/' + paramObj.salesId
              // params: paramObj
            });
          },
          getAccountDetail: function (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/validateEmail.json',
              url: APP_CONTEXT + 'account/edit/' + paramObj.accountId
            });
          },
          validateEmail: function (paramObj) {
            return $http({
              method: 'post',
              // url: '/data/validateEmail.json',
              url: APP_CONTEXT + 'account/sendVerifyCode',
              data: angular.toJson(paramObj)
            });
          },
          validateVerCode: function (paramObj) {
            return $http({
              method: 'post',
              // url: '/data/validateVerCode.json',
              url: APP_CONTEXT + 'account/verifyEmail',
              data: angular.toJson(paramObj)
            });
          },
          submitUserName: function (paramObj) {
            return $http({
              method: 'post',
              // url: '/data/customerSubmitUserName.json',
              url: APP_CONTEXT + 'account/saveAccount',
              data: angular.toJson(paramObj)
            });
          },
          /**
           * [发送催办]
           * @param   paramObj:{customerId: 122}
           * @return HttpPromise
           */
          sendReminder: function (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/customerList.json',
              url: APP_CONTEXT + 'customer/reminders/' + paramObj.customerId,
              params: paramObj
            });
          },
          withdraw: function (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/customerList.json',
              url: APP_CONTEXT + 'customer/withdrawCustomer/' + paramObj.customerId,
              params: paramObj
            });
          },
          nullify: function (paramObj) {
            return $http({
              method: 'get',
              // url: '/data/customerList.json',
              url: APP_CONTEXT + 'customer/discardCustomer/' + paramObj.customerId
              // params: paramObj
            });
          },
          recover: function (paramObj) {
            return $http({
              method: 'post',
              // url: '/data/customerList.json',
              url: APP_CONTEXT + 'customer/recoveryCustomer',
              data: angular.toJson(paramObj)
              // params: paramObj
            });
          },
          /**
         * [客户审核信息]
         * @param  paramObj:{id:1234, activityId:4814839_act3}
         * @return HttpPromise
         */
          getApprovalInfo: function (paramObj) {
            return $http({
              method: 'post',
              // url: '/data/customerDetailQualification.json',
              url: APP_CONTEXT + 'customer/approveview',
              data: angular.toJson(paramObj)
              // params: paramObj
            });
          },
          /**
           * [客户信息审核提交]
           * @param   paramObj:{
                        "adMaterialApplyId":1,
                        "taskId":"4814839_act3",
                        "activityId":"4814839_act3"
                        "approvalStatus":1,
                        "approvalSuggestion":"ok,pass",
                        "isPlusSign": 1,
                        "processId":"1768505_gcrm_pkg_506_prs3",
                      }
           * @return HttpPromise
           */
          submitApproval: function (paramObj) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/approve',
              // url: '/data/materialDetailInfo.json',
              data: angular.toJson(paramObj)
            });
          },
          /**
           * [客户信息审核记录]
           * @param  paramObj:{customerId:31}
           * @return HttpPromise
           */
          approvalRecord: function (paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'customer/findApproveRecord',
              // url: '/data/materialApproveRecord.json',
              params: paramObj
            });
          },
          /**
           * [客户修改记录]
           * @param  paramObj:{customerId:31}
           * @return HttpPromise
           */
          modifyRecord: function (paramObj) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'customer/findModifyRecord',
              // url: '/data/benchmarkPriceModifyRecords.json',
              params: paramObj
            });
          },
          getDetail: function ( paramObj ) {
            return $http({
              method: 'get',
              // url: '/data/customerDetail.json'
              url: APP_CONTEXT + 'customer/showCustomerDetailInfo/' + paramObj.id
            });
          },
          getEditInfo: function () {
            return $http({
              method: 'get',
              // url: '/data/customerEdit.json'
              url: APP_CONTEXT + 'customer/init'
            });
          },
          queryAgentCountries: function ( paramObj ) {
            //id: {代理区域id}
            return $http({
              method: 'get',
              // url: '/data/customerAgentCountry.json'
              url: APP_CONTEXT + 'customer/getAgentRegional/' + paramObj.id
            });
          },
          getBelongSalesSuggestOption: function ( opts ) {
            var translateFilter = $filter( 'translate' );
            return {
              id: function( item ) {
                //唯一的值
                return  item.id;
              },
              placeholder: translateFilter( 'SELECTTOSUGGEST_BELONG_SALES' ),
              minimumInputLength: 1,
              formatInputTooShort: function( term, minLength ) {
                //输入太短提示
                return translateFilter('SELECTTOSUGGEST_INPUT_MINIMAL_HINT', {
                  minLength: minLength
                });
              },
              formatNoMatches: function() {
                // 无匹配的提示
                return translateFilter( 'SELECTTOSUGGEST_NO_BELONG_SALES' );
              },
              query: function(query) {
                var data = {
                  results: []
                };
                var dataHead = {};
                belongSalesSuggest( {
                  'query': $.trim(query.term)
                }).success(function ( response ) {
                  if ( response.code === 200 ){
                    data.results = response.data;
                    query.callback(data);
                  }
                });
              },
              formatResult: function(item) {
                return item.name;
              },
              formatSelection: function(item) {
                if( opts && opts.cbfn ){
                  opts.cbfn( item );
                }
                return item.name;
              },
              width: 'element',
              allowClear: (function () {
                if ( opts && opts.allowClear ) {
                  return opts.allowClear;
                }
                return false;
              })(),
              initSelection: function () {
                // body...
              }
            };
          },
          saveToApprove: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/submit',
              data: angular.toJson( paramObj )
            });
          },
          save: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/tempSaveCustomer',
              data: angular.toJson( paramObj )
            });
          },
          getBelongSalesLeader: function ( paramObj ) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'customer/belongSalesLeader',
              params: paramObj
            });
          },
          addAgentCompany: function ( paramObj ) {
            //{name: "ddfdf"}
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/addAgent',
              data: angular.toJson( paramObj )
            });
          },
          getAgentCompanySuggestOption: function ( opts ) {
            var translateFilter = $filter( 'translate' );
            return {
              id: function( item ) {
                //唯一的值
                return  item.id;
              },
              placeholder: translateFilter( 'SELECTTOSUGGEST_BELONG_AGENT' ),
              minimumInputLength: 1,
              formatInputTooShort: function( term, minLength ) {
                //输入太短提示
                return translateFilter('SELECTTOSUGGEST_INPUT_MINIMAL_HINT', {
                  minLength: minLength
                });
              },
              formatNoMatches: function() {
                // 无匹配的提示
                return translateFilter( 'SELECTTOSUGGEST_NO_BELONG_AGENT' );
              },
              query: function(query) {
                var data = {
                  results: []
                };
                var dataHead = {};
                agentCompanySuggest( {
                  'query': $.trim(query.term)
                }).success(function ( response ) {
                  if ( response.code === 200 ){
                    data.results = response.data;
                    query.callback(data);
                  }
                });
              },
              formatResult: function(item) {
                return item.name;
              },
              formatSelection: function(item) {
                return item.name;
              },
              width: 'element',
              allowClear: (function () {
                if ( opts && opts.allowClear ) {
                  return opts.allowClear;
                }
                return false;
              })(),
              initSelection: function () {
                // body...
              }
            };
          },
          saveBasic: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/updateCustomer',
              data: angular.toJson( {
                customer: paramObj
              })
            });
          },
          saveContactor: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/saveOrUpdateContactPerson',
              data: angular.toJson( paramObj )
            });
          },
          removeContactor: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/deleteContactPerson',
              data: angular.toJson( paramObj )
            });
          },
          //处理基本信息的data
          resolveBasicData: function ( data ) {
            data = angular.copy( data );
            data.businessType = data.businessTypesSelected && data.businessTypesSelected.join();
            data.industry = data.industry && data.industry.id;
            data.belongSales = data.belongSales && data.belongSales.id;
            if ( data.agentCountrySelected && data.agentCountrySelected.length ) {
              data.agentCountry = Utils.joinWithKey( data.agentCountrySelected, 'id' );
            }
            data.currencyType = data.currencyType && data.currencyType.id;
            data.belongManager = data.belongManager && data.belongManager.id;
            //make sure belongManager is number
            if ( data.belongManager ) {
              data.belongManager = ~~data.belongManager;
            }
            data.country = data.country && data.country.id;
            data.registerTime = $filter( 'date' )( data.registerTime, 'yyyy-MM-dd' );
            if ( data.agentRegional && data.agentRegional.id ) {
              data.agentRegional = data.agentRegional.id;
            }
            if ( data.agentCompany && data.agentCompany.id ) {
              data.agentCompany = data.agentCompany.id;
            }
            delete data.agentRegionalShow;
            delete data.businessTypesSelected;
            delete data.customerTypes;
            delete data.industryTypes;
            delete data.companySizeTypes;
            delete data.currencyTypes;
            delete data.businessTypes;
            delete data.agentTypes;
            delete data.agentRegionals;
            delete data.agentCountries;
            delete data.agentCountrySelected;
            return data;
          },
          //处理联系人信息
          resolveContactData: function ( data ) {
            var resultData = [];
            // filter using name，and delete help keys
            for (var i = 0; i < data.length; i++) {
              if( data[ i ].name ) {
                var item = angular.copy( data[ i ] );
                delete item.isEditing;
                resultData.push( item );
              }
            }
            return resultData;
          },
          //处理业务机会信息
          resolveOpportunityData: function ( data ) {
            data = angular.copy( data );
            data.currencyType = data.currencyType && data.currencyType.id;
            if ( data.businessTypesSelected && data.businessTypesSelected.length ) {
              data.businessType = data.businessTypesSelected.join();
            }
            if ( data.billingModelsSelected && data.billingModelsSelected.length ) {
              data.billingModel = data.billingModelsSelected.join();
            }
            if ( data.platformsSelected ) {
              data.platformIds = Utils.joinWithKey( data.platformsSelected, 'id' );
            }
            delete data.payTypes;
            delete data.billingModels;
            delete data.currencyTypes;
            delete data.businessTypes;
            delete data.businessTypesSelected;
            delete data.billingModelsSelected;
            delete data.platformsSelected;
            delete data.platforms;
            return data;
          },
          updateCustomerOpportunity: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/updateOpportunity',
              data: angular.toJson( paramObj )
            });
          },
          updateCustomerQualification: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/updateQualification',
              data: angular.toJson( paramObj )
            });
          },
          saveOrUpdateAttachment: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/saveOrUpdateAttachment',
              data: angular.toJson( paramObj )
            });
          },
          removeAttachment: function ( paramObj ) {
            return $http({
              method: 'post',
              url: APP_CONTEXT + 'customer/deleteAttachment',
              data: angular.toJson( paramObj )
            });
          },
          queryAccountList: function ( paramObj ) {
            return $http({
              method: 'get',
              // url: '/data/accountList.json',
              url: APP_CONTEXT + 'account/query/' + paramObj.customerNumber
            });
          },
          removeAccount: function ( paramObj ) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'account/delete/' + paramObj.accountId
            });
          },
          updateAccount: function ( paramObj ) {
            return $http({
              method: 'get',
              url: APP_CONTEXT + 'account/updateState',
              params: paramObj
            });
          },
          queryContractList: function ( paramObj ) {
            return $http({
              method: 'post',
              // url: '/data/customerContractList.json',
              url: APP_CONTEXT + 'customer/queryContract',
              data: angular.toJson( paramObj )
            });
          }
        };
      }
    ]);
});