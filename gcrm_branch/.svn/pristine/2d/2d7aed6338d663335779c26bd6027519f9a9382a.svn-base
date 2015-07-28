function reviseBtnShow(){
           $("#editMaterials,#editAgentQualification,#editBusinessChance,#editContactInfo,#editBaseInfo").removeClass('hide');
        }
var businessType = -1;
$.gcrm_filter = function(dest,needReset){
    var tem_obj = {
        "-1":[
            $("[name='customer.agentType']"),
            $("[name='customer.agentRegional']"),
            $("[name='customer.agentCompany']"),
            $("[name='customer.industry']"),
            $("[name='customer.agentCountry']")
        ],
        "0":[
            $("[name='customer.agentType']"),
            $("[name='customer.agentRegional']")
        ],
        
        "1":[
            $("[name='customer.industry']")
        ],
        
        "2":[
            $("[name='customer.industry']"),
            $("[name='customer.agentCompany']")
        ],
        
        "3":[
            $("[name='customer.industry']")
        ]
        
    };

    var reset = function(t_arr){
        for (var i = t_arr.length - 1; i >= 0; i--) {
            var $el = t_arr[i];
            if(needReset){
	            if($el.is("select")){
	            	$el.selectpicker('val', '-1');
	            } else {
		            var tagName = $el.attr("type");
		            if(tagName == "radio" || tagName == "checkbox"){
		                $el.attr("checked",false);
		            } else {
		            	$el.val("");
		            }
           		 }
            $el.closest(".form-group").hide();
           }
        }
    };

    var init = function(t_arr){
    	if(!t_arr){
    		return;
    	}
        for (var i = t_arr.length - 1; i >= 0; i--) {
            var $el = t_arr[i];
            
            $el.closest(".form-group").show();
        };
    };
    
    var clear = function(){
    	 reset(tem_obj["-1"]);
    }
    
    reset(tem_obj[businessType]);
        
    init(tem_obj[dest]);
};   
jQuery.validator.addMethod("regex",function(value, element, params) { //扩展方法示例 　　　　　　　　　　　　　　　　　　　
	var exp = new RegExp(params);
	return exp.test(value); 
},GCRM.util.message('validator.error.text')); 


	
$.gcrm.editBaseInfo = function(){
	$("body").delegate("[name='customer.agentRegional']","click",function(){
        $("[name='customer.agentCountry']").each(function(){
            $(this).attr("checked",false);
        });
        var regional = $(this).val();
        var $country = $("#agentCountry_" + regional);
        $country.closest(".form-group").children(".col-md-5").hide();
        
        $country.show();
        $country.closest(".form-group").show();
     });

	$("body").delegate(".addAgentBtn","click",function(){
        $('.addAgentCompany').removeClass('hide');
        $("#addAgentName").focus();
    });
    $("body").delegate(".agentCompanyBtn","click",function(){
    	var addAgentName=$('#addAgentName').val();
    	$.ajax({
			type: "POST",
	 		url: "addAgent",
	 		data: {"companyName" : addAgentName},
	 		dataType: "json",
			success: function(msg){
				if(msg.success){
					$('#agentCompanyName').val(addAgentName);
					$('#agentCompany').val(msg.retBean);
					$("#agentCompany").blur();
					$('#agentCompanyName').focus();
					$('#addAgentName').val("");
					$('.addAgentCompany').addClass('hide');
				} else { 
					$.each(msg.errors, function(i, item){
						if(i == 'agentCompany')
							i = 'addAgentName';
						$('[name="'+ i +'"]').closest(".form-group")
							.children(".help-block").show().addClass('txt-impt').text(GCRM.util.message(item));
					});
				}
			}
		});  
    });

	var $editBtn = $("#editBaseInfo"),
	$J_baseInfoContainer = $("#J_detailBaseInfo .panel-body"),
	$BaseInfoSubmitBox = $("#BaseInfoSubmitBox");
	
	function tabChange(){
	   
		var type = $("[name='customer.businessType']").val();
		if(type == 0){
			$("#qualificationLi").show('fast');
			if($("#qualificationLi").hasClass("active")||$("#businessChanceLi").hasClass("active")){
				unlockTab("detailBusinessChance");
				
				$("#qualificationLi a:eq(0)").click();				
			}
			$("#businessChanceLi").hide('fast');
		}else{
			$("#businessChanceLi").show('fast');
			$("#detailBusinessChance .panel-heading,#detailBusinessChance .panel-heading a").show();
			if($("#addBusinessChance")){
				$("#BusinessChanceSubmitBox").addClass("hide");
			}
			if($("#qualificationLi").hasClass("active")||$("#businessChanceLi").hasClass("active")){
				 unlockTab("detailAgentQualification");
				
				$("#businessChanceLi a:eq(0)").click();
			}
			$("#qualificationLi").hide('fast');
		}
	}

	$("body").delegate("#editBaseInfo", "click", function(){
		var customerId = $(this).attr("customerId");
		var $el = $("<form id='baseInfoForm' class='form-horizontal' method='POST' action='afterEdit'></form>");
		
		$el.load("preEdit/"+customerId, function(){

			//select 美化
			$el.find('select').addClass("col-md-12").selectpicker({});

			$J_baseInfoContainer.empty().append($el);
			
			/*Suggestion 初始化*/
			$('#agentCompanyName').closest("div").css("position","relative");
	       	
	        $('#agentCompanyName').tagSuggest({
	            matchClass: 'autocomplete-suggestions',
	            tagContainer:'div',
	            tagWrap: 'div',
	            separator: " ",
	            url: "agentCompanySuggest",
	            inputName:"input[name='customer.agentCompany']",
	            deleteCallback:function(){
	            	$("#agentCompany").val("").blur();
	            },
	            onSelected:function(){
					$("#agentCompany").blur();
	            }
	        }); 
	        
	        /*修改Suggestion样式*/
	        $(".autocomplete-suggestions").css({
	        	position:"absolute",
	        	zIndex:9999,
	        	backgroundColor:"#fff",
	        	maxHeight:"200px",
	        	overflow:"auto",
	        	boxShadow:"0 2px 2px #cfcfcf",
	        	border:"1px solid #cfcfcf"
	        });

			if(!$("#suggestCss")[0])
		       	$("body").append([
		       		'<style type="text/css" id="suggestCss">',
						'div.autocomplete-suggestions>div {padding: 6px 20px;cursor: pointer;margin-top: 2px;text-align: left;}',
						'div.autocomplete-suggestions>div:nth-child(2n) {background: #f5f6f8;}',
						'div.autocomplete-suggestions>div:hover {background: #fff6f7;}',
					'</style>'
		       	].join(""));
			
			//DateTime Picker 初始化
			$('#J_detailBaseInfo .date-time').datetimepicker({
		        language:$('.date-time').attr('locale'),
		        weekStart: 1,
		        todayBtn:  0,
		        autoclose: 1,
		        todayHighlight: 1,
		        startView: 2,
		        minView: 2,
		        forceParse: 0,
		        format: 'dd-mm-yyyy',
		        pickerPosition:"bottom-right"
		    });

			$editBtn.addClass("hide");
			$BaseInfoSubmitBox.removeClass("hide");
			
			vali=$("#baseInfoForm").validate(setValidate);
			
			/*validate兼容性处理*/
			$el.find('.date-time').change(function(){
				$(this).blur();
			});
			$el.find('select').change(function(){
				$(this).click();
			});
			
			/*禁用提交广告方案的按钮*/
			disabledSubmitAd();
		});
		return false;
	});

	$('#J_detailBaseInfo .date-time').datetimepicker().on('hide', function(){
		$('#J_detailBaseInfo #registerTime').keyup();
	});
	
	$("#J_detailBaseInfo").delegate("#baseInfoForm","submit",function(){
		$("#baseInfoForm").validate(setValidate);
		return false;
	});

	var vali=$("#baseInfoForm").validate(setValidate);

	$("#J_detailBaseInfo").delegate("#saveBaseInfo", "click", function(){
		if(	!vali.form() ){
			return;
		}
		var $self = $(this);
		var customerId = $self.attr("customerId");
		var approvalStatusBefore = $("#approvalStatus").val();
		var approvalStatusAfter = -1;
		$.ajax({
			type: "POST",
			 url: $("#baseInfoForm").attr("action"),
			 data: $("#baseInfoForm").serialize(),
			 dataType: "json",
			 success: function(msg){
				if(msg.success){
					//unlockTab("detailBaseInfo");
					tabChange();
					reviseBtnShow();
					$("#detailAgentQualification,#detailBusinessChance,#detailConcatInfo,#detailMaterials").unbind("mouseover mousemove mouseout");

					if (customerId == null) {
						customerId = msg.retBean;
						$editBtn.attr("customerId", customerId);
						$("#cancelBaseInfo,#tempSaveBaseInfo,.submit").attr("customerId", customerId);
						$self.attr("customerId", customerId);
					} else {
						approvalStatusAfter = msg.retBean;
						if (approvalStatusBefore == 3 && approvalStatusAfter== 0) {
							window.open(GCRM.constants.CONTEXT + "customer/" + customerId, '_self');
							return;
						}
					}
					$J_baseInfoContainer.load('loadBaseInfo/' + customerId, function(){
						$editBtn.removeClass("hide");
						$BaseInfoSubmitBox.addClass("hide");

						var newxtState=$("#detailConcatInfo #editContactInfo").hasClass("J_init");
						if(newxtState){
							$("#editContactInfo").click();
						}
						
						refreshCompanyName(approvalStatusAfter);
					});
					$("#baseInfoForm").submit();
					
					/*解除提交审核按钮*/
					enabledSubmit();
					/*解除提交广告方案按钮*/
					enabledSubmitAd();
					
				} else {
					$.each(msg.errors, function(i, item){ 
						$('[name="'+ i +'"]').closest(".form-group")
							.children(".help-block").show().addClass('txt-impt-errors').text(GCRM.util.message(item));
					});
					vali.form();

				}
			}
		});	
	});
	
	function refreshCompanyName(approvalStatusAfter) {
		var companyName = $("#companyName").val();
		if (approvalStatusAfter == 0) {
			$(".page-header h3>font").text(companyName);
		} else if (approvalStatusAfter == 1) {
			$("#viewCompanyName").text(companyName);
		}
	}
	
	$("#J_detailBaseInfo").delegate("#tempSaveBaseInfo", "click", function(){
		var $self = $(this);
		$.ajax({
			 type: "POST",
			 url: GCRM.constants.CONTEXT + "/customer/tempSaveCustomer",
			 data: $("#baseInfoForm").serialize(),
			 dataType: "json",
			 success: function(msg){
				if(msg.success){ 
					//unlockTab("detailBaseInfo");
					var customerId = msg.retBean;
					$("#cancelBaseInfo,#saveBaseInfo,#editBaseInfo,.submit").attr("customerId", customerId);
					$self.attr("customerId", customerId);
					$(".help-block").removeClass('txt-impt-errors').text("");
					$("#customerId").val(customerId);
					$("#approvalStatus").val(0);
					//alert(GCRM.util.message("customer.save.success"));
					tempSaveModel("tempSaveModal",GCRM.util.message("customer.save.success"),2000);
				} else {
					$(".help-block").removeClass('txt-impt').text("");
					$.each(msg.errors, function(i, item){ 
						$('[name="'+ i +'"]').closest(".form-group")
							.children(".help-block").show().addClass('txt-impt-errors').text(GCRM.util.message(item));
					});
				}
			}
		});	
	});

	

	$("#J_detailBaseInfo").delegate("#cancelBaseInfo", "click", function(){
		var customerId = $(this).attr("customerId");
		$J_baseInfoContainer.load('loadBaseInfo/'+customerId, function(){
			//unlockTab("detailBaseInfo");
			reviseBtnShow();
			$editBtn.removeClass("hide");
			$BaseInfoSubmitBox.addClass("hide");
			
			/*解除提交广告方案按钮*/
			enabledSubmitAd();
		});
		return false;
	});

};

$(function(){
	$('#J_detailBaseInfo .panel-body select').addClass("col-md-12").selectpicker({});
	
	/*selectpicker插件和validate插件在select上的兼容性处理*/
	$('#J_detailBaseInfo .panel-body select').change(function(){
		$(this).click();
	});
	
	$.gcrm.editBaseInfo();
});
