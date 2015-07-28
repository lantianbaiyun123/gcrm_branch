$.gcrm = $.gcrm || {};
$.gcrm.addContact = function(){
	//**要先加载detailContactInfo.js**//、
	
	$.gcrm.editContact($("#detailConcatInfoEmpty"));	
	$("#detailConcatInfoEmpty").delegate("#addContact","click",function(){
		$(this).closest("div.text-center").css("display","none");
		$("#ContactTable-empty select").addClass("col-md-12").selectpicker({});
		$("#ContactTable-empty").css("visibility","visible");
		$(this).closest("div#detailConcatInfoEmpty").find("div.addContactBtn").css("display","block");
		
		/*禁用提交广告方案按钮*/
		disabledSubmitAd();
	});
};
