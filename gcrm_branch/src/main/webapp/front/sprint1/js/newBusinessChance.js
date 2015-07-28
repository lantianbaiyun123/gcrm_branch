/*编辑 businessChance*/
$.gcrm.editBusinessChance = function(insertfun){
	
	var alertfun = insertfun || function(){
		alert(GCRM.util.message('alertDialog.lockTab.text'));
	};
	//$("#saveBaseInfo").add("#cancelBaseInfo").hide();
	$editBtn = $("#detailBusinessChance div.panel-heading"),
	$J_businessChanceContainer = $("#J_businessChanceContainer"),
	$BusinessChanceSubmitBox=$("#BusinessChanceSubmitBox");
//	$("body").undelegate("#editBusinessChance", "click").delegate("#editBusinessChance", "click", function(){
////		var customerNumber = $(this).attr("customerNumber");
//		var customerNumber=$(".customerNumber").val();
//		
//		var $el = $("<form id='businessChance' class='form-horizontal' method='POST' action='opportunity/preUpdateOpportunity?'></form>");
//		$el.load(GCRM.constants.CONTEXT+"opportunity/preUpdateOpportunity/"+customerNumber, function(){
//			//$.gcrm.validate($el);
//			$el.find('select').addClass("col-md-12").selectpicker({});
//			$J_businessChanceContainer.empty().append($el);
//			$editBtn.addClass("hide");
//			$BusinessChanceSubmitBox.removeClass("hide");
//			
//			/*关闭tab切换*/
//			$('#myTab a[href="#detailBusinessChance"]').attr("data-urlbackup",$('#myTab a[href="#detailBusinessChance"]').attr("data-url"));
//			$('#myTab a[href="#detailBusinessChance"]').attr("data-url","");
//			$('#myTab a[href!="#detailBusinessChance"]').each(function(){
//				$(this).attr("data-urlbackup",$(this).attr("data-url"));
//				$(this).attr("data-url","");
//				$(this).attr("data-toggle","");
//			});
//			$('#myTab').undelegate("a[href!='#detailBusinessChance']","click").delegate("a[href!='#detailBusinessChance']","click",alertfun);
//			moduleConfig.detailBusinessChance.success();
//		});
//		
//		return false;
//	});

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
		
//		var customerNumber = $(this).attr("customerNumber");
		var customerNumber=$(".customerNumber").val();
		$.ajax({
			type: "POST",
			 url: GCRM.constants.CONTEXT+"opportunity/updateOpportunity?customerNumber="+customerNumber,
			 data: $currForm.serialize(),
			 success: function(msg){
				if(msg.success){
					$J_businessChanceContainer.addClass("hide");
					$J_businessChanceContainer.load(GCRM.constants.CONTEXT+'opportunity/reloadOpportunity/'+customerNumber,function(){
						//$("#editBusinessChance").removeClass("hide");
						$BusinessChanceSubmitBox.addClass("hide");
						if($("#addBusinessChance")[0]){
							//$J_businessChanceContainer.addClass("hide");
						} else
							$J_businessChanceContainer.removeClass("hide");
						
						modifyChangeBtn($("#editBusinessChance"),$("#J_businessChanceContainer"));
						//moduleConfig.detailBusinessChance.success();
					});
					
					/*显示修改按钮*/
					reviseBtnShow();
					/*解除提交审核按钮*/
					enabledSubmit();
					
					if((!$("#detailMaterials").hasClass("hide"))&&$("#editMaterials").hasClass("J_init"))
						$("#editMaterials").click();
				} else {
					$.each(msg.errors, function(i, item){ 
						$('[name="'+ i +'"]').closest(".form-group").addClass("has-error")
							.removeClass("has-success").find(".help-block").text(GCRM.util.message(item));
					});
				}
			}
		});
		
	});

	$("body").undelegate("#cancelBusinessChance", "click").delegate("#cancelBusinessChance", "click", function(){
//		var customerNumber = $(this).attr("customerNumber");
		var customerNumber=$(".customerNumber").val();
		$J_businessChanceContainer.load(GCRM.constants.CONTEXT+'opportunity/reloadOpportunity/'+customerNumber, function(){
			
			moduleConfig.detailBusinessChance.success();
			if($("#addBusinessChance").length!=0){
				$J_businessChanceContainer.addClass("hide").empty();
			}
			$BusinessChanceSubmitBox.addClass("hide");
			/*显示修改按钮*/
			reviseBtnShow();
			$("#editBusinessChance").show();
		});
		return false;
	});
};

