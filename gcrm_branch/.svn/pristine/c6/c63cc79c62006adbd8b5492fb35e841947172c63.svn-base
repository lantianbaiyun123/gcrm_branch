/*编辑 agentQualification*/
$.gcrm.editAgentQualification = function(){
	var agentQualificationFormValidate = {
			rules: {
	
			    "parterTop1":{
			    	"maxlength":128,
			    },
			    "parterTop2":{
			    	"maxlength":128,
			    },
			    "parterTop3":{
			    	"maxlength":128,
			    },
			    "customerResources[0].industry":{
			    	"maxlength":128,
			    },
			    "customerResources[0].advertisersCompany1":{
			    	"maxlength":128,
			    },
			    "customerResources[0].advertisersCompany2":{
			    	"maxlength":128,
			    },
			    "customerResources[0].advertisersCompany3":{
			    	"maxlength":10,
			    },
			    "customerResources[1].industry":{
			    	"maxlength":128,
			    },
			    "customerResources[1].advertisersCompany1":{
			    	"maxlength":128,
			    },
			    "customerResources[1].advertisersCompany2":{
			    	"maxlength":128,
			    },
			    "customerResources[1].advertisersCompany3":{
			    	"maxlength":128,
			    },
			    "customerResources[2].industry":{
			    	"maxlength":128,
			    },
			    "customerResources[2].advertisersCompany1":{
			    	"maxlength":128,
			    },
			    "customerResources[2].advertisersCompany2":{
			    	"maxlength":128,
			    },
			    "customerResources[2].advertisersCompany3":{
			    	"maxlength":128,
			    },
			    "performanceHighlights":{
			    	"maxlength":512,
			    }
		   	}, 
		   	messages:{
		   		"parterTop1":  GCRM.util.message("qualification.parterTop.length.toolong"),
		   		"parterTop2": GCRM.util.message("qualification.parterTop.length.toolong"),
		   		"parterTop3": GCRM.util.message("qualification.parterTop.length.toolong"),
		   		"customerResources[0].industry": GCRM.util.message("qualification.parterTop.length.toolong"),
			    "customerResources[0].advertisersCompany1": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[0].advertisersCompany2": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[0].advertisersCompany3": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[1].industry": GCRM.util.message("costomerResource.industry.length.toolong"),
			    "customerResources[1].advertisersCompany1": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[1].advertisersCompany2": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[1].advertisersCompany3": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[2].industry": GCRM.util.message("costomerResource.industry.length.toolong"),
			    "customerResources[2].advertisersCompany1": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[2].advertisersCompany2": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    "customerResources[2].advertisersCompany3": GCRM.util.message("costomerResource.advertisersCompany.length.toolong"),
			    
			    "performanceHighlights": GCRM.util.message("qualification.performanceHighlights.length.toolong"),
			},
		   	errorPlacement: function(error,element) {
	   		
		   		element.closest(".form-group");
		   		element.parent().find('.help-block').html(error);
				if(element.parent().find('.help-block').text()==''){
					element.parent().parent().find('.help-block').html(error);
				}
		   	}
	};
	
	var $saveBtn = $("#saveAgentQualification"),
	$cancelBtn = $("#cancelAgentQualification"),
	$editBtn = $("#editAgentQualification"),
	$Container = $("#J_agentQualificationContainer"),
	$SubmitBox=$("#AgentQualificationSubmitBox");
	$("body").undelegate("#editAgentQualification", "click").delegate("#editAgentQualification", "click", function(){
		
		var customerNumber = $(this).attr("customerNumber");
		var $el = $("<form id='agentQualificationForm' class='form-horizontal' method='POST' action='../qualification/doEditQualification/"+customerNumber+"'></form>");

		$el.load("../qualification/gotoEditQualification/"+customerNumber, function(){
			//form验证
			//$.gcrm.validate($el);
			//select 美化
			$el.find('select').addClass("col-md-12").selectpicker({});
			
			$Container.empty().append($el);
			$editBtn.hide();
			$SubmitBox.removeClass("hide");
			$saveBtn.show();
			$cancelBtn.show();
			lockTab("detailAgentQualification");
			
			/*禁用提交广告方案按钮*/
			disabledSubmitAd();
		});

		return false;
	});

	$("body").undelegate("#saveAgentQualification", "click").delegate("#saveAgentQualification", "click", function(){
		var customerNumber = $(this).attr("customerNumber");
				
		/*验证*/
		var formState=$.gcrm.validate_form("#agentQualificationForm",agentQualificationFormValidate);
		if(formState==false){
			return;
		}		
		
		$.ajax({
			type: "POST",
			 url: $("#agentQualificationForm").attr("action"),
			 data: $("#agentQualificationForm").serialize(),
			 success: function(msg){
				if(msg.success){
					$Container.load('../qualification/gotoQualificationDetail/'+customerNumber,function(){
						if($("#editAgentQualificationEmpty")[0]){//说明是空白页，隐藏添加按钮
							$editBtn.hide();
						}else{
							$editBtn.show();
						}
						$SubmitBox.addClass("hide");
					});
					unlockTab("detailAgentQualification");
					
					/*解除禁用提交广告方案*/
					enabledSubmitAd();
				} else { 
					$.each(msg.errors, function(i, item){ 
						if(i=="businesstype.error"){
							alert(GCRM.util.message(item));
						}else{
							$('[name="'+ i +'"]').closest(".form-group").removeClass("has-success");
						      $('[name="'+ i +'"]').parent().addClass("has-error")
						      .removeClass("has-success").find('.help-block').text(GCRM.util.message(item));
						      if($('[name="'+ i +'"]').parent().find('.help-block').text()==''){
						       $('[name="'+ i +'"]').parent().parent().addClass("has-error")
						       .removeClass("has-success").find('.help-block').text(GCRM.util.message(item));
						      }
						}
						
					});
					
				}
			}
		});
		
	});

	$("body").undelegate("#cancelAgentQualification", "click").delegate("#cancelAgentQualification", "click", function(){
		var customerNumber = $(this).attr("customerNumber");
		
		$Container.load('../qualification/gotoQualificationDetail/'+customerNumber, function(){
			if($("#editAgentQualificationEmpty")[0]){//说明是空白页，隐藏添加按钮
				$editBtn.hide();
			}else{
				$editBtn.show();
			}
			$SubmitBox.addClass("hide");
			unlockTab("detailAgentQualification");
			
			/*解除禁用提交广告方案*/
			enabledSubmitAd();
		});
		return false;
	});
};
