$.gcrm.editBusinessChance = function(insertfun){
	
	var alertfun = insertfun || function(){
		alert(GCRM.util.message('alertDialog.lockTab.text'));
	};
	$editBtn = $("#detailBusinessChance div.panel-heading"),
	$J_businessChanceContainer = $("#J_businessChanceContainer"),
	$BusinessChanceSubmitBox=$("#BusinessChanceSubmitBox");
	$("body").undelegate("#editBusinessChance", "click").delegate("#editBusinessChance", "click", function(){
		var customerNumber = $(this).attr("customerNumber");
		var $el = $("<form id='businessChance' class='form-horizontal' method='POST' action='opportunity/preUpdateOpportunity?'></form>");
		$el.load(GCRM.constants.CONTEXT+"opportunity/preUpdateOpportunity/"+customerNumber, function(){
			$el.find('select').addClass("col-md-12").selectpicker({});
			$J_businessChanceContainer.empty().append($el);
			$editBtn.hide();
			$BusinessChanceSubmitBox.removeClass("hide");
			
			lockTab("detailBusinessChance");
			moduleConfig.detailBusinessChance.success();
			
			/*禁用提交广告方案按钮*/
			disabledSubmitAd();
		});
		
		return false;
	});

	$("body").undelegate("#saveBusinessChance", "click").delegate("#saveBusinessChance", "click", function(){
		$currForm = $("#businessChance");
		
		var errorInfo = GCRM.util.message('opportunity.validate.number');
		var validator = $currForm.validate({
			rules:{
				"opportunity.budget":{
					digits: true,
					min: 0
				}
			},
			messages: {
				"opportunity.budget" : errorInfo
			}
		});
		var isValid = validator.form(); 
		if(!isValid){
			return false;
		}
		
		var customerNumber = $(this).attr("customerNumber");
		$.ajax({
			type: "POST",
			 url: GCRM.constants.CONTEXT+"opportunity/updateOpportunity?customerNumber="+customerNumber,
			 data: $currForm.serialize(),
			 success: function(msg){
				if(msg.success){
					$J_businessChanceContainer.load(GCRM.constants.CONTEXT+'opportunity/reloadOpportunity/'+customerNumber,function(){
						$editBtn.show();
						$BusinessChanceSubmitBox.addClass("hide");
						
						moduleConfig.detailBusinessChance.success();
						
						/*解除提交广告方案按钮*/
						enabledSubmitAd();
					});
				} else {
					$.each(msg.errors, function(i, item){ 
						if(i=="businesstype.error"){
							alert(GCRM.util.message(item));
						}else{
							$('[name="'+ i +'"]').closest(".form-group").addClass("has-error")
								.removeClass("has-success").find(".help-block").text(GCRM.util.message(item));
						}
					});
				}
			}
		});
		unlockTab("detailBusinessChance");
		
	});

	$("body").undelegate("#cancelBusinessChance", "click").delegate("#cancelBusinessChance", "click", function(){
		var customerNumber = $(this).attr("customerNumber");
		unlockTab("detailBusinessChance");
		
		$J_businessChanceContainer.load(GCRM.constants.CONTEXT+'opportunity/reloadOpportunity/'+customerNumber, function(){
			$editBtn.show();
			$BusinessChanceSubmitBox.addClass("hide");
			
			moduleConfig.detailBusinessChance.success();
			
			/*解除提交广告方案按钮*/
			enabledSubmitAd();
		});
		return false;
	});
};

