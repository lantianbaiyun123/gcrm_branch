/**
 * AdProgram 广告方案 (Angular Service)
 *
 * --USAGES--
 *   查询插单时间。
 *   1.queryInsertDates( paramObj ,cbfn )
        param : {
            positionId:positionId.id,   //广告位置
            periods:periods,            //投放时间段  
            insertDate:insertDate       //插单时间
        }

        @return insertDateByDatePeriod.json 
 *       
 */
define(['app'
], function (app) {
    app.registerFactory('AdTime', ['$http',
        function ($http) {
            return {
                queryInsertDates : function(paramObj, cbfn){
                    //console.log(paramObj, "------------")
                    var defaults = {
                        pageNo: 1,
                        pageSize: 10
                    }; // default params for get orgnization list
                    // todo: plan to do it in interceptor
                    // angular.extend( paramObj, { _: new Date().getTime() }, defaults );//ie cache
                    $http({
                        method: 'post',
                        //url: '../resources/data/insertDateByDatePeriod.json', //for test data
                        url: '/gcrm/occupation/queryInsertDates', //should switch to real url
                        // params: angular.extend(defaults, paramObj)

                        data: JSON.stringify(paramObj)   //post 请求，
                    }).success(function (result) {
                        cbfn(result);
                    });
                }
            };
        }
    ]);
});