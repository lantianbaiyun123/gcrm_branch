define(["app","../../_common/ytCommon","../../_filters/CustomerFilter","../../_directives/ytInputDropdown","../../_directives/ytInputCheckboxes","../../_directives/ytInputRadio","../../_directives/ytJqueryFileUpload","../../_directives/ytAjaxupload","../../_directives/checklistModel"],function(t){t.registerController("CtrlCustomerAdd",["$scope","$log","$state","$stateParams","$timeout","Modal","Customer","$filter","Utils","$q","PageSet","CURRENT_USER_NAME",function(t,e,a,n,i,s,o,c,r,u,d,p){d.set({siteName:"customerAdd",activeIndex:0});var l={initSub:function(){t.isOwner=!0,t.basic={state:"addEditing"},t.isOwner=!0,t.contact={state:"addEditing",contacts:[{isEditing:!0}]},t.opportunity={state:"addEditing"},t.attachment={state:"addEditing",attachments:[]},t.qualification={state:"addEditing"},t.qualification.customerResources=[{},{},{}]},setEditData:function(e){for(var a=0;a<e.currencyTypes.length;a++)if("$"===e.currencyTypes[a].sign){var n=e.currencyTypes[a];e.currencyTypes.splice(a,1),e.currencyTypes.unshift(n);break}t.basic.customerTypes=e.customerTypes,t.basic.industryTypes=e.industrys,t.basic.companySizeTypes=e.companySizes,t.basic.currencyTypes=e.currencyTypes,t.basic.businessTypes=e.businessType,t.basic.agentTypes=e.agentTypes,t.basic.agentRegionals=e.agentRegionals,t.basic.maxDate=new Date,t.opportunity.currencyTypes=e.currencyTypes,t.opportunity.businessTypes=e.businessType,t.attachment.attachmentTypes=e.attachmentTypes},initSaving:function(){t.basic={state:"addEditing"},t.contact={state:"addEditing"},t.opportunity={state:"addEditing"},t.attachment={state:"addEditing"},t.qualification={state:"addEditing"}},setDetailData:function(e,a){return e&&e.customer?(t.basic=angular.extend(t.basic,e.customer),t.basic.industry=e.industry,e.agentCompany&&(t.basic.agentCompany={id:e.agentCompany.id,name:e.agentCompany.companyName}),t.basic.country=e.country,t.basic.currencyType=e.currencyType,e.belongSales&&(t.basic.belongSales={id:e.belongSales.ucid,name:e.belongSales.realname}),t.basic.agentType=e.agentType,t.basic.agentRegional=e.agentRegional&&e.agentRegional.id,t.basic.agentCountrySelected=e.agentCountry,t.basic.businessTypesSelected=e.customer.businessType&&e.customer.businessType.split(","),t.basic.agentRegional&&t.basic.agentRegional&&o.queryAgentCountries({id:t.basic.agentRegional}).success(function(e){200===e.code&&(t.basic.agentCountries=e.data.agentCountries)}),t.contact.contacts=e.contacts&&e.contacts.length?e.contacts:[{isEditing:!0}],e.opportunityView&&(t.opportunity=angular.extend(t.opportunity,e.opportunityView),t.opportunity.currencyTypes=a.currencyTypes,e.opportunityView.billingModel&&(t.opportunity.billingModelsSelected=e.opportunityView.billingModel.split(",")),e.opportunityView.businessType&&(t.opportunity.businessTypesSelected=e.opportunityView.businessType.split(",")),t.opportunity.platformsSelected=e.opportunityView.advertisingPlatforms),t.qualification=e.qualification||{customerResources:[{},{},{}],state:"addEditing"},t.qualification.state="addEditing",void(t.attachment.attachments=e.attachments)):!1},getAllPostParams:function(){var e={},a=o.resolveBasicData(t.basic),n=o.resolveContactData(t.contact.contacts),i=angular.copy(t.attachment.attachments);return e={customer:a,contacts:n,attachments:i},"offline"===t.basic.customerType?e.qualification=angular.copy(t.qualification):e.opportunity=o.resolveOpportunityData(t.opportunity),e}};n.customerId?(l.initSaving(),u.all([o.getEditInfo(),o.getDetail({id:n.customerId})]).then(function(e){var a=e[0].data,n=e[1].data;l.setEditData(a.data),l.setDetailData(n.data,a.data),t.typeChangeNotAllowed=!n.data.typeChangeAllowed,t.isOwner=n.data.isOwner})):(l.initSub(),o.getEditInfo().success(function(e){200===e.code&&(l.setEditData(e.data),t.basic.belongSales={id:p.ucid,name:p.realname},o.getBelongSalesLeader({salerId:p.ucid}).success(function(e){200===e.code&&(t.basic.belongManager=e.data)}))})),t.saveToApproval=function(){if(t.basic.isValidating=!0,!t.basic.companyName)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.customerType)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.country)return t.anchorTo("anchorCustomerTop"),!1;if(!("offline"===t.basic.customerType||t.basic.industry&&t.basic.industry.id))return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.registerTime)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.url)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.businessTypesSelected||!t.basic.businessTypesSelected.length)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.belongSales)return t.anchorTo("anchorCustomerTop"),!1;if("nondirect"===t.basic.customerType){if(!t.basic.agentCompany)return t.anchorTo("anchorCustomerTop"),!1}else if("offline"===t.basic.customerType){if(!t.basic.agentType||!t.basic.agentRegional)return t.anchorTo("anchorCustomerTop"),!1;if(!t.basic.agentCountrySelected||!t.basic.agentCountrySelected.length)return t.anchorTo("anchorCustomerTop"),!1}t.disableToApproval=!0,o.saveToApprove(l.getAllPostParams()).success(function(e){200===e.code&&(t.basic.isValidating=!1,s.success({content:c("translate")("SUCCESS_SUBMIT_TO_APPROAVAL")},function(){a.go("customer.detail.facade",{customerId:e.data.customer.id})})),t.disableToApproval=!1})},t.save=function(){o.save(l.getAllPostParams()).success(function(t){200===t.code&&s.success({content:c("translate")("SUCCESS_TEMP_SAVE")},function(){n.customerId||(n.customerId=t.data.customer.id,a.transitionTo(a.current,n,{reload:!0,inherit:!1,notify:!1}))})})},t.back=function(){a.go("customer.list")}}])});