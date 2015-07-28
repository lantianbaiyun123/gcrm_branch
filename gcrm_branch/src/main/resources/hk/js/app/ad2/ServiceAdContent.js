define(["app"],function(t){t.registerService("AdContent",["AdHttp","Position","AdContentUtil","$timeout",function(t,e,n,a){return{init:function(t){if(t.contentState={},t.e={advertiseQuotation:{ratioThird:0,reachEstimate:0}},t.basic.customerDirect&&(t.ad.advertiser=angular.copy(t.basic.customerDirect)),t.e.advertiser=t.ad.advertiser,t.e.adPlatformList=angular.copy(t.adPlatformList),1===t.e.adPlatformList.length){var e=t.e.adPlatformList[0].id;n.getSiteList(t,e)}t.e.priceTypes=[{label:"AD_CONTENT_UNIT_PRICE",value:"unit"},{label:"AD_CONTENT_FALL_INTO",value:"ratio"}],t.e.billingModels=angular.copy(t.billingModels),t.e.industryTypes=angular.copy(t.industry.industryTypes),t.e.isEstimated=[{label:"YES",value:!0},{label:"NO",value:!1}],t.e.priceDisabled=!0,t.e.advertiseMaterials=[],this.updateContentState(t)},edit:function(e,o,i){e.state.isGlobleEditing=!0,e.ad.review=!1,e.ad.isSaved=!0,t.getContentInfo({id:o.adSolutionContent.id}).success(function(t){if(200===t.code){angular.extend(e.e,t.data),e.e.adPlatformList=angular.copy(e.adPlatformList);var o=e.e;a(function(){o.industrySelected=o.advertiseQuotation.industryType,o.priceDisabled=!1,e.selectIndusty()},200);var r=o.adSolutionContent.approvalStatus;("unconfirmed"===r||"confirmed"===r)&&(o.showAlert=!0),(i||"update"===o.adSolutionContent.contentType)&&(o.calendarFutureOnly=!0),o.advertiser={value:o.adSolutionContent.advertiser,data:o.adSolutionContent.advertiserId},e.basic.customerDirect&&(o.advertiser=e.basic.customerDirect),o.area=n.findArea(o.adSolutionContent.areaId,o.areaVOList),o.position=n.findPostion(o.adSolutionContent.positionId,o.positionVOList),o.datePeriods=n.getDatePeriods(o.adSolutionContent.periodDescription),o.insertDatesTotal=o.adSolutionContent.totalDays,o.insertDates=n.getInsertDates(o.adSolutionContent.insertPeriodDescription),o.selectedInsertDates=o.insertDates,o.selectedInsertDays=o.insertDates.length,o.insertDatesCheckAll=!0,n.queryQuotionPattern(e)}})},updateContentState:function(t){var e=t.contentState,n=t.ad,a=t.BtnIndex;e.showBtnApprovalRecord=function(){return n.adSolutionContent=n.adSolutionContent||{},n.adSolutionContent.submitTime}(),e.canReschedule=function(){return t.basic.isOwner&&a.btn_adsol_detail_reschedule&&"approved"===n.adSolutionContent.approvalStatus&&n.schedule&&"released"===n.schedule.status&&!n.adSolutionContent.taskInfo}(),e.canConfirm=function(){return t.basic.isOwner&&a.btn_adsol_detail_cont_conf&&"unconfirmed"===n.adSolutionContent.approvalStatus&&"released"!==n.schedule.status}(),e.canModify=function(){var e=t.ad,n=e.canUpdate&&!t.state.isDetailView,o=t.state.isDetailView&&("refused"===e.adSolutionContent.approvalStatus||"saving"===e.adSolutionContent.approvalStatus||"unconfirmed"===e.adSolutionContent.approvalStatus&&"released"!==e.schedule.status);return t.basic.isOwner&&a.btn_adsol_detail_cont_mod&&a.btn_adsol_detail_cont_save&&(n||o)}(),e.canChange=function(){var e=t.ad,n=t.basic.contract;return t.basic.isOwner&&a.btn_adsol_detail_cont_change&&n&&n.data&&"VALID"===n.data.state&&e.adSolutionContent.poNum&&"confirmed"===e.adSolutionContent.approvalStatus&&e.canUpdate}(),e.canTerminate=function(){return t.basic.isOwner&&a.btn_adsol_detail_coop_term&&("unconfirmed"===n.adSolutionContent.approvalStatus||"approved"===n.adSolutionContent.approvalStatus&&n.schedule&&"released"===n.schedule.status&&!n.adSolutionContent.taskInfo)}(),e.canWithdraw=function(){return t.basic.isOwner&&a.btn_adsol_detail_contract_withdraw&&n.canWithdraw}(),e.canChangePo=function(){return t.basic.isOwner&&t.basic.isOwner&&a.btn_adsol_detail_po_create&&n.canCreatePO}(),e.canDelete=function(){return!(!t.basic.isOwner||!a.btn_adsol_detail_cont_save||t.state.isDetailView||t.state.isSolutionTypeUpdate)}(),e.showTaskInfo=function(){return"approved"==n.adSolutionContent.approvalStatus||"approving"==n.adSolutionContent.approvalStatus||"saving"==n.adSolutionContent.approvalStatus||"refused"==n.adSolutionContent.approvalStatus}()},confirmContentSchedule:function(e){return t.confirmContentSchedule({id:e.adSolutionContent.id,overdue:e.overdue})}}}]),t.registerService("AdContentUtil",["$filter","AdHttp","Position","PublishPrice",function(t,e,n,a){return{findArea:function(t,e){return this.find(t,e)},findPostion:function(t,e){return this.find(t,e)},find:function(t,e){var n;return angular.forEach(e,function(e){n||e.id!==t||(n=e)}),n},getDatePeriods:function(t){if(!t)return null;for(var e=[],n=[],a=t.split(";"),o=0;o<a.length;o++)e=a[o].split(","),e.length>1?n.push({from:e[0],to:e[1]}):a[o]&&n.push({from:a[o],to:a[o]});return n},getInsertDates:function(t){if(!t)return[];for(var e=[],n=t.split(";"),a=0;a<n.length;a++)e.push({date:n[a],checked:!0});return e},getSiteList:function(t,e){n.getSiteList({productId:e}).success(function(e){200===e.code&&(t.e.siteList=e.data,t.e.adSolutionContent.siteId=null,t.e.adSolutionContent.channelId=null,t.e.area=null,t.e.position=null)})},getChannelList:function(t,e,a){n.getChannelList({productId:e,siteId:a}).success(function(e){200===e.code&&(t.e.channelVOList=e.data,t.e.adSolutionContent.channelId=null,t.e.area=null,t.e.position=null)})},getAreaList:function(t,e){n.getAreaList({parentId:e}).success(function(e){200===e.code&&(t.e.areaVOList=e.data)})},getPositionList:function(t,e){n.getPositionList({parentId:e}).success(function(e){200===e.code&&(t.e.positionVOList=e.data)})},resolvePeriods:function(t){for(var e=t.length-1;e>=0;e--)if(t[e].from&&t[e].to){if(angular.isDate(t[e].from)?t[e].from=t[e].from.getTime():/\d/.test(t[e].from)||(t[e].from=new Date(t[e].from.replace(/-/g,"/")).getTime()),angular.isDate(t[e].to)?t[e].to=t[e].to.getTime():/\d/.test(t[e].to)||(t[e].to=new Date(t[e].to.replace(/-/g,"/")).getTime()),t[e].to<t[e].from){var n=t[e].to;t[e].to=t[e].from,t[e].from=n}}else t.splice(e,1);return t},queryInsertDates:function(t){var n=t.e.insertDates;if(!t.e.position.id||!t.e.datePeriods)return!1;var a=this.resolvePeriods(t.e.datePeriods),o={positionId:t.e.position.id,periods:a,insertDate:n};"confirmed"===t.e.adSolutionContent.approvalStatus?angular.extend(o,{oldContentId:t.e.adSolutionContent.id}):"update"===t.e.adSolutionContent.contentType&&angular.extend(o,{oldContentId:t.e.adSolutionContent.oldContentId});var i=this;e.queryInsertDates(o).success(function(e){if(200===e.code){t.e.datePeriods=e.data.datePeriod,t.e.datePeriods&&t.e.datePeriods.length||(t.e.datePeriods=[{}]),t.e.insertDates=e.data.insertDate,t.e.insertDatesTotal=e.data.totalDays,t.e.selectedInsertDays=t.e.insertDates&&t.e.insertDates.length,t.e.insertDatesCheckAll=!0;for(var n=0;n<t.e.insertDates.length;n++)t.e.insertDates[n].checked||(t.e.selectedInsertDays=t.e.selectedInsertDays-1,t.e.insertDatesCheckAll=!1);i.updateTotal(t)}})},clearPrice:function(t){t.e.advertiseQuotation.ratioMine=null,t.e.advertiseQuotation.ratioCustomer=null,t.e.advertiseQuotation.ratioThird=0,t.e.advertiseQuotation.reachEstimate=!1,t.e.advertiseQuotation.ratioConditionDesc="",t.e.advertiseQuotation.publishPrice=null,t.e.advertiseQuotation.customerQuote=null,t.e.advertiseQuotation.discount=null,t.e.advertiseQuotation.budget=null,t.e.advertiseQuotation.totalPrice=null,t.e.advertiseQuotation.dailyAmount=null,t.e.advertiseQuotation.totalAmount=null,t.e.cpsProductRatioMine=null,this.toggleValidator(t,!1)},resetPrice:function(t){this.clearPrice(t),t.e.priceDisabled=!0,t.e.advertiseQuotation.priceType=null,t.e.position=null},getAdIndex:function(t,e){for(var n=0;n<t.adContents.length;n++)if(t.adContents[n]&&t.adContents[n].adSolutionContent.id===~~e)return n;return-1},resolveErrorArgsForAdContent:function(e,n){for(var a=0;a<n.length;a++)n[a]=n[a]?t("translate")("AD_CONTENT_TITLE")+(this.getAdIndex(e,n[a])+1):t("translate")("AD_CONTENT_CURRENT");return n},queryQuotionPattern:function(t){var e={advertisingPlatformId:t.e.adSolutionContent.productId,siteId:t.e.adSolutionContent.siteId,billingModelId:t.e.advertiseQuotation.billingModelId};e.billingModelId||delete e.billingModelId,a.post(e).success(function(e){200===e.code&&e.data.result&&(e.data.result.length&&e.data.result[0]?2===~~t.e.advertiseQuotation.billingModelId?(t.e.productQuotes=e.data.result,(t.e.industrySelected||0===t.e.industrySelected)&&t.selectIndusty()):(t.e.advertiseQuotation.publishPrice=e.data.result[0].publishPrice,t.e.advertiseQuotation.productRatioMine=e.data.result[0].ratioMine,t.e.advertiseQuotation.productRatioCustomer=e.data.result[0].ratioCustomer||0,t.e.advertiseQuotation.productRatioThird=e.data.result[0].ratioThird||0):t.e.advertiseQuotation.discount=1)})},toggleValidator:function(t,e){t.e.contentSave=e,t.e.hiddenElValidator=e,t.e.positionChangeValidator=e,t.e.quotesValidator=e,t.e.materialValidator=e,t.e.codeContentValidator=e},updateTotal:function(t,e){try{e=e||t.e.advertiseQuotation.billingModelId,4===e?t.e.advertiseQuotation.totalAmount=(t.e.insertDatesTotal-(t.e.insertDates.length-t.e.selectedInsertDays)||0)*(t.e.advertiseQuotation.dailyAmount||0):5===e?t.e.advertiseQuotation.totalPrice=((t.e.advertiseQuotation.customerQuote||0)*(t.e.insertDatesTotal-(t.e.insertDates.length-t.e.selectedInsertDays)||0)).toFixed(2):1===e&&(t.e.advertiseQuotation.totalPrice=((t.e.advertiseQuotation.customerQuote||0)*(t.e.insertDatesTotal-(t.e.insertDates.length-t.e.selectedInsertDays)||0)*t.e.position.click).toFixed(2))}catch(n){}}}}])});