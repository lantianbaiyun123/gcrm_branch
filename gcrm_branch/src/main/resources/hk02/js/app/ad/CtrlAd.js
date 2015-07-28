define(["app","../_common/ytCommon","../_services/PageSet","../_services/Select2Suggest","../_services/http/Customer","../_services/http/Contract","../_services/http/AdProgram","../_services/http/Account","../_directives/ytAjaxupload","../_directives/ytFocusme","../_directives/ytCalendar","../_directives/periodLabel","../_filters/AdMaterialIfEmbedCodeFilter","../gcrm-util","anuglar-ui-select2","./CtrlAdContent"],function(t){t.registerController("CtrlAd",["$scope","$q","$log","$rootScope","$stateParams","$timeout","$location","$anchorScroll","PageSet","Modal","Customer","Contract","AdProgram","Account","Utils","$http","Select2Suggest","$filter","GCRMUtil","$state","CURRENT_USER_NAME","Ad","Industry",function(t,a,e,o,r,i,c,s,n,d,u,l,m,p,S,v,b,g,f,C,h,I,w){function y(t){for(var a=[],e=0;e<t.length;e++){var o={review:!0};o.reviewData=t[e],o.reviewData.materialType=~~N(t[e].positionVOList,t[e].adSolutionContent.positionId,"materialType"),o.reviewData.positionByAreaSelection=t[e].position,o.canUpdate=t[e].canUpdate,a.push(o)}return a}function N(t,a,e){var o=0;if(t=t||[],3==arguments.length){for(;o<t.length;o++)if(t[o].id==a)return t[o][e]?t[o][e]:""}else for(;o<t.length;o++)if(t[o].id==a)return t[o]}t.stateEditing=!0,t.ads=[],t.basic=t.basic||{},r.programId?(n.set({siteName:"adSolutionDetail",activeIndex:1}),t.programId=r.programId,t.stateEditing=!1,m.getDetail({id:r.programId}).success(function(a){if(200===a.code){var i=a.data;t.basicReview=!0,t.basicReviewData={customerI18nView:i.customerI18nView,contract:i.contract,hasContract:i.hasContract||i.contract,operatorName:i.account.name,programStatus:i.adSolution.approvalStatus,programType:i.adSolution.type},"direct"===t.basicReviewData.customerI18nView.customer.customerType&&(t.stateCustomerStraight=!0,t.customerStraight={data:i.customerI18nView.customer.customerNumber,value:i.customerI18nView.customer.companyName});var c=a.data;c.adSolution&&c.adSolution.taskInfo&&(t.alerts=[{type:"info",msg:c.adSolution.taskInfo}],"refused"===c.adSolution.approvalStatus&&(t.alerts[0].type="danger"));var s,n=c.adSolution.approvalStatus,d=c.approvalContentViews;if(d.cancelRecord&&d.cancelRecord.length&&C.go("adSolutionDetail",{id:r.programId}),"saving"===n)for(s=0;s<d.length;s++)"saving"!==d[s].adSolutionContent.approvalStatus&&"refused"!==d[s].adSolutionContent.approvalStatus&&C.go("adSolutionDetail",{id:r.programId});else if("refused"===n)for(s=0;s<d.length;s++)"refused"!==d[s].adSolutionContent.approvalStatus&&C.go("adSolutionDetail",{id:r.programId});else C.go("adSolutionDetail",{id:r.programId});"update"===c.adSolution.type&&(t.adSolutionTypeUpdate=!0),c.oldSolutionNumber&&(t.oldSolutionNumber=c.oldSolutionNumber),t.newDetailContents=angular.copy(c.approvalContentViews),w.getIndustryTypes().success(function(a){if(200===a.code){for(var o={},r=a.data.length,i=0;r>i;i++){var c=a.data[i].industryTypeName;if(o[c]=[],"subIndustryType"in a.data[i])for(var s=a.data[i].subIndustryType.length,n=0;s>n;n++)o[c].push(a.data[i].subIndustryType[n]);else o[c].push(a.data[i])}t.industry={industryTypes:o}}else e.error("fatal error")}),c.approvalContentViews.length?t.ads=y(c.approvalContentViews):o.BtnIndex.btn_adsol_detail_cont_save&&(t.addAd(),t.anchorTo(0))}})):(n.set({siteName:"adSolutionAdd",activeIndex:1}),t.basic.hasContract=!1,t.basic.adProjectStatus=1,t.basic.operatorSelected={data:{ucid:h.ucid,name:h.realname}},t.programStatus=g("translate")("AD_BASIC_STATUS_WAITED"),t.programType=g("translate")("AD_BASIC_TYPE_NEW"),t.programStatus="saving",t.programType="create",r.customerNumber&&u.get({customerNumber:r.customerNumber,companyName:""}).success(function(a){t.basic.customerInfo=a.data,t.basic.customerSelected={data:a.data.customer.customerNumber,value:a.data.customer.companyName},"nondirect"===a.data.customer.customerType&&(t.basic.customerSelected={data:a.data.customer.customerNumber,value:a.data.agentCompany.companyName})})),t.closeAlert=function(a){"info"===a.type&&t.alerts.splice(0,1)},t.addAd=function(){t.ads.push({}),t.stateEditing=!0},t.adContentRemove=function(a,e){d.confirm({content:g("translate")("AD_BASIC_CONFIRM_REMOVE_CONTENT")},function(){var o=e.solutionContentId||e.reviewData.solutionContent.id;o&&m.remove({id:o},function(e){200===e.code&&t.ads.splice(a,1)})})},t.adContentCancel=function(a){d.confirm({content:g("translate")("AD_BASIC_CONFIRM_CANCEL_CONTENT")},function(){t.ads.splice(a,1)}),t.stateEditing=!1},t.adProgramSubmit=function(){return t.programId?(t.disableToApproval=!0,void m.submit({id:t.programId},function(a){if(t.disableToApproval=!1,200===a.code)d.success({content:g("translate")("AD_BASIC_ALERT_SUBMIT_SUCCESS")},function(){C.go("adSolutionDetail",{id:a.data.id})});else if(204===a.code){for(var e=function(a){for(var e=0;e<t.ads.length;e++)if(t.s[e].reviewData&&t.s[e].reviewData.solutionContent.id===~~a)return e;return-1},o=function(t){for(var a=0;a<t.length;a++)t[a]=t[a]?g("translate")("AD_CONTENT_TITLE")+(e(t[a])+1):g("translate")("AD_CONTENT_CURRENT");return t},r=[],i=0;i<a.errorList.length;i++)r.push(f.translate(a.errorList[i].key,o(a.errorList[i].args)));d.alert({contentList:r})}})):!1},t.anchorTo=function(t){var a;a=-1===t?"anchorBasic":"anchorAdContent"+t,i(function(){c.hash(a),s()})},t.$watch("basic.customerSelected",function(a){a&&(t.basic.contract=null,t.basic.hasContract=!1,t.basic.checkContract=!1,u.get({customerNumber:a.data,companyName:""}).success(function(a){t.basic.customerInfo=a.data,t.basic.hasContract=a.data.hasContract}))}),t.$watch("basic.hasContract",function(a,e){a||(t.basic.contract=null),e||(t.basic.checkContract=!1)}),t.basicEdit=function(){return t.programId?(t.stateEditing=!0,void m.basicGetEdit({id:t.programId}).success(function(a){200===a.code&&(t.basic={operatorSelected:{data:{ucid:a.data.operatorId,name:a.data.operatorName}},customerSelected:{data:a.data.customerI18nView.customer.customerNumber,value:a.data.customerI18nView.customer.companyName},customerInfo:{customer:a.data.customerI18nView.customer}},i(function(){t.basic.contract={data:a.data.contract},t.basic.hasContract=a.data.hasContract},0),t.basicReview=!1,t.showCancelBtn=!0,t.programStatus=a.data.advertiseSolutionView.advertiseSolution.approvalStatus,t.programType=a.data.advertiseSolutionView.advertiseSolution.type)})):!1},t.basicCancel=function(){t.basicReview=!0,t.stateEditing=!1},t.basicTempSave=function(){if(t.basic.basicCheck=!0,!t.basic.customerSelected||!t.basic.customerSelected.data)return t.basic.customerFocus=!0,!1;t.basic.checkContract=!0;var a={};a.hasContract=t.basic.hasContract,a.advertiseSolutionView=t.basic.hasContract?{advertiseSolution:{contractNumber:t.basic.contract.data.number}}:{advertiseSolution:{}},t.basic.operatorSelected&&t.basic.operatorSelected.data&&(a.advertiseSolutionView.advertiseSolution.operator=t.basic.operatorSelected.data.ucid),a.advertiseSolutionView.advertiseSolution.customerNumber=t.basic.customerSelected.data,a.advertiseSolutionView.advertiseSolution.id=t.programId,m.basicTempSave(a).success(function(a){200===a.code&&(t.programId||(t.showCancelBtn=!1),t.programId=a.data.advertiseSolutionView.advertiseSolution.id,t.basic.tempSavePop={content:"暂存成功",show:!1},t.basic.tempSavePop.show=!0,i(function(){t.basic.tempSavePop.show=!1},3e3))})},t.basicSave=function(){if(t.basic.basicCheck=!0,!t.basic.customerSelected||!t.basic.customerSelected.data)return t.basic.customerFocus=!0,!1;if(t.basic.checkOperator=!0,!t.basic.operatorSelected||!t.basic.operatorSelected.data)return t.basic.operatorFocus=!0,!1;t.basic.checkContract=!0;var a;if(t.basic.hasContract){if(!t.basic.contract||!t.basic.contract.data)return t.basic.contractFocus=!0,!1;a=t.basic.contract.data.number}m.basicSave({hasContract:t.basic.hasContract,advertiseSolutionView:{advertiseSolution:{id:t.programId,contractNumber:a,customerNumber:t.basic.customerSelected.data,operator:t.basic.operatorSelected.data.ucid}}}).success(function(a){if(200===a.code){var e=a.data.advertiseSolutionView.advertiseSolution.id;t.programId=e,r.programId||(r.programId=e,C.transitionTo(C.current,r,{reload:!0,inherit:!1,notify:!1})),m.basicGet({id:e}).success(function(a){if(200===a.code){if(t.basicReview=!0,t.basicReviewData={customerI18nView:a.data.customerI18nView,contract:a.data.contract,hasContract:a.data.hasContract,operatorName:a.data.operatorName,programStatus:a.data.advertiseSolutionView.advertiseSolution.approvalStatus,programType:a.data.advertiseSolutionView.advertiseSolution.type},a.data.customerI18nView.customer&&("nondirect"===a.data.customerI18nView.customer.customerType?t.basicReviewData.customer=a.data.customerI18nView.agentCompany:"direct"===a.data.customerI18nView.customer.customerType&&(t.stateCustomerStraight=!0,t.customerStraight={data:a.data.customerI18nView.customer.customerNumber,value:a.data.customerI18nView.customer.companyName}),t.ads.length))if("direct"===a.data.customerI18nView.customer.customerType){t.stateCustomerStraight=!0;for(var e=t.ads.length-1;e>=0;e--)t.ads[e].reviewData.adSolutionContent.advertiser=a.data.customerI18nView.customer.companyName}else t.stateCustomerStraight=!1;t.ads.length?t.stateEditing=!1:(t.ads.push({}),t.anchorTo(0))}})}})},t.cancelChangeAd=function(){return r.programId?void I.cancelChangeAdSolution({id:r.programId}).success(function(t){200===t.code&&C.go("adSolutionDetail",{id:t.data})}):!1},t.optionOperator=b.getOperatorOption(),t.optionCustomer=b.getCustomerOption(),t.optionContract=b.getContractOption(t)}])});