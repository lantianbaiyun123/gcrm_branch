
var moduleConfig = {
    "detailConcatInfo":{

        containerId :"detailConcatInfo",
        url:null,
        js:null,
        css:[],
        success:function(){}
    }, 
    "detailConcatInfoEmpty":{
        containerId :"detailConcatInfoEmpty",
        url:null,
        js:null,
        css:[],
        success:function(){}
    },
    "detailBusinessChance":{
        containerId :"detailBusinessChance",
        url:null,
        js:null,
        css:[],
        success:function(){}
    },
    "detailAgentQualification":{
        containerId :"detailAgentQualification",
        url:null,
        js:null,
        css:[],
        success:function(){}
    },
    "detailMaterials":{
        containerId :"detailMaterials",
        url:null,
        js:null,
        css:[],
        success:function(){}
    },
    "detailMaterialsEmpty":{
        containerId :"detailMaterialsEmpty",
        url:null,
        js:null,
        css:[]
    }
};

var lockTab=function(id){
	$('#myTab a[data-id!="'+id+'"]').each(function(){
		$(this).attr("canLoad","false");
	});
};
var unlockTab=function(id){
	$('#myTab a[data-id!="'+id+'"]').each(function(){
		$(this).removeAttr("canLoad");
	});
};

//console.log($("#myTab"));
$("#myTab").delegate("a", "click", function(){
    
    try{
        var $self = $(this);
        var canLoad = $self.attr("canLoad");
        if(canLoad != null && canLoad == 'false'){
        	//alert(GCRM.util.message('alertDialog.lockTab.text'));
        	alertModal("lockTabModal",GCRM.util.message('alertDialog.lockTab.text'));
    		return false;
        }
        var id = $self.attr("data-id");
        var activeId = $("#myTab").find("li.active > a").attr("data-id");
        if(id==activeId){
        	return;
        }
        $.gcrm.loadModule({
        	moduleId :id,
            container:$("#"+moduleConfig[id].containerId).find(".panel-body"),
            url: $self.attr("data-url") || moduleConfig[id].url,
            js:moduleConfig[id].js,
            success:function(){
                moduleConfig[id].success();
            }
        });
    }catch(e){
        console.log(e);
    }   
    
});
//$("#myTab").find("li.active > a").click();
$("#myTab").find("a:eq(0)").click();

//$("#J_detailBaseInfo .panel-heading").prepend("<span class='black-block'>&nbsp;</span>");

function refreshCompanyHeader(customerId) {
	var $J_companyHeaderContainer = $("#J_companyHeader .panel-body");
	$J_companyHeaderContainer.load('loadCompanyHeader/' + customerId);
}

//提交审核
$("body").delegate(".submitToApprove", "click", function() {
	var submitForbidden = false;
	$(".panel-footer").each(function() {
		if (!$(this).hasClass("hide")) {
			//alert(GCRM.util.message("customer.save.required"));
			alertModal("approveCustomerModal",GCRM.util.message("customer.save.required"));
			submitForbidden = true;
		}
	});
	if ($("#contactPersonTable :text:enabled").length != 0) {
		//alert(GCRM.util.message("customer.contact.save.required"));
		alertModal("approveContactModal",GCRM.util.message("customer.contact.save.required"));
		submitForbidden = true;
	}
	if (submitForbidden) {
		return;
	}
	var id = $(this).attr("customerId");
	$.ajax({
		type: "POST",
 		url: "submit",
 		data: {"customer.id" : id},
 		dataType: "json",
		success: function(msg){
			if(msg.success){
				$("div.pull-right :submit").val(GCRM.util.message("gcrm.title.customer.withdraw")).removeClass("submitToApprove");
				refreshCompanyHeader(id);
				var message = GCRM.util.message("gcrm.title.customer.submitToApprove");
				tempSaveModel('coutomerSubmitPass',message,2000,function(){
					window.open( GCRM.constants.CONTEXT + "customer/" + id, '_self' );
				});
			} else { 
				var message = GCRM.util.message("customer.submit.failed");
				$.each(msg.errors, function(i, item){ 
					message += "，" + GCRM.util.message(item);
				});
				alertModal("approveFailModal",message);
			}
		}
	});
});

var alertModal=function(id,alertText){
	var thisId=id || "";
	var thisAlertText=alertText || "";
	var domStr=['<div class="modal fade" id="'+ thisId +'" tabindex="-1" style="text-align:center; overflow:hidden;" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">',
	            '<div class="modal-dialog" style="min-width:270px; display:inline-block; width:auto; max-width:50%;">',
	            '<div class="modal-content">',
	              '<div class="modal-header" style="text-align:left;">',
	                '<h4 class="modal-title" id="myModalLabel">'+ thisAlertText +'</h4>',
	              '</div>',
	              '<div class="modal-body" style="text-align:right;padding:10px 20px 10px;">',
	                '<button type="button" class="btn btn-primary" data-dismiss="modal">'+GCRM.util.message('view.ok')+'</button>',
	              '</div>',
	             
	            '</div>',
	          '</div>',
	        '</div>'].join("");
	if($("#"+ thisId).length == 0 )
		$(domStr).modal('show');
	else
		$("#"+ thisId).modal('show');
}

var tempSaveModel=function(id,alertText,timeOut,timeOutFunction){
	var _thisId = id || "";
	var _thisAlertText = alertText || "";
	var _thisTimeOut = timeOut || 2000;
	var _thisTimeOutFunction = timeOutFunction || null;
	var domStr = ['<div class="modal fade" id="'+ _thisId +'" tabindex="-1" style="text-align:center; overflow:hidden;" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">',
	            '<div class="alert alert-success" style="min-width:270px; display:inline-block; width:auto; margin-top:30px;">',
	                _thisAlertText,	             
	          '</div>',
	        '</div>'].join("");
	if($("#"+ _thisId).length == 0 )
		$(domStr).modal('show');
	else
		$("#"+ _thisId).modal('show');
	
	setTimeout(function(){
		
		$("#"+ _thisId).modal('hide');
		!!_thisTimeOutFunction ? _thisTimeOutFunction() : "" ;
		
	},_thisTimeOut);
}

var confirmModel=function(paramObj){
	var _thisId = paramObj.id || "";
	var _thisAlertText = paramObj.alertText || "";
	var _thisOkId =  _thisId + "-Ok";
	var _thisCancelId = _thisId + "-Cnacel";
	var domStr=['<div class="modal fade" id="'+ _thisId +'" tabindex="-1" style="text-align:center; overflow:hidden;" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">',
	            '<div class="modal-dialog" style="min-width:270px; display:inline-block; width:auto; max-width:50%;">',
	            '<div class="modal-content">',
	              '<div class="modal-header" style="text-align:left;">',
	                '<h4 class="modal-title" id="myModalLabel">'+ _thisAlertText +'</h4>',
	              '</div>',
	              '<div class="modal-body" style="text-align:right;padding:10px 20px 10px;">',
	                '<button type="button" class="btn btn-primary" data-dismiss="modal" id="'+_thisOkId+'">'+GCRM.util.message('view.ok')+'</button>',
	                '<button type="button" class="btn btn-default" id="'+_thisCancelId+'" data-dismiss="modal" style="margin-left: 5px;">'+GCRM.util.message('view.cancel')+'</button>',
	              '</div>',
	             
	            '</div>',
	          '</div>',
	        '</div>'].join("");
	if($("#"+ _thisId).length == 0 )
		$(domStr).modal('show');
	else
		$("#"+ _thisId).modal('show');
	if(!!paramObj.okFunction){
		$("body").delegate("#"+_thisOkId,"click",function(){
			paramObj.okFunction();
		});
	}
	if(!!paramObj.cancelFunction){
		$("body").delegate("#"+_thisCancelId,"click",function(){
			paramObj.cancelFunction();
		});
	}
	
}

var disabledSubmitAd=function(){
	if($(".submitAd"))
		$(".submitAd").attr("disabled","true").removeClass("btn-primary").addClass("btn-default");
}
var enabledSubmitAd=function(){
	if($(".submitAd"))
		$(".submitAd").removeAttr("disabled").removeClass("btn-default").addClass("btn-primary");
}

var disabledSubmit=function(){
	if($(".submit"))
		$(".submit").attr("disabled","true").removeClass("btn-primary").addClass("btn-default");
}
var enabledSubmit=function(){
	if($(".submit"))
		$(".submit").removeAttr("disabled").removeClass("btn-default").addClass("btn-primary");
}
//初始化禁用提交广告方案的按钮
var initsubmitAdAlert=function(){
	   var timId,mouseX,mouseY,isMouseIn;
    $("body").append("<div id='float'class='alert alert-warning hide' style='position:absolute;z-index:9999;'>"+ GCRM.util.message('hover.tips.first') +"<span style='color:#3276b1;'> "+ GCRM.util.message('hover.tips.second') +"</span>"+ GCRM.util.message('hover.tips.third') +"</div>");
            
     var opeaObj=$("#float")[0];
     
     var setFloatText=function(){
     	var target=$(opeaObj),
     		nowEdit="";
     	if(!$("#BaseInfoSubmitBox").hasClass("hide"))
     		nowEdit=GCRM.util.message("customer.title.base");
     	else if(!$("#BusinessChanceSubmitBox").hasClass("hide"))
     		nowEdit=GCRM.util.message("opportunity.title");
     	else if(!$("#AgentQualificationSubmitBox").hasClass("hide"))
     		nowEdit=GCRM.util.message("qualification.title");
     	else if(!$("#MaterialsSubmitBox").hasClass("hide"))
     		nowEdit=GCRM.util.message("materials.title");
     	else if(!$("#J_addContactBtn").closest("div").hasClass("hide"))
     		nowEdit=GCRM.util.message("contact.info");
     	
     	target.find("span").text(" "+nowEdit||" "+GCRM.util.message("hover.tips.nowedit.text"));
     }
     
     $(".submitAd").closest("div").bind({
         mouseover:function(e){
         	if($(this).find(".submitAd").attr("disabled")==="disabled"){
	                $("#float").removeClass('hide');
	                $(this).css("cursor","pointer");
	                setFloatText();
	                isMouseIn=true; 
         	}
         },
         mousemove:function(e){
         	if(isMouseIn){
	                mouseX=e.pageX;
	                mouseY=e.pageY;
	                opeaObj.style.display="none";
	                clearTimeout(timId);
	                timId=setTimeout(function(){
	                	if( document.documentElement.clientWidth - mouseX > 260 ){
	                		
		                	opeaObj.style.left=mouseX+8+"px";
		                	
	                	}else{
	                		
	                		opeaObj.style.left=mouseX-$(opeaObj).width()-38+"px";
	                		
	                	}
	                	opeaObj.style.top=mouseY-$(opeaObj).height()-15+"px";
	                	$(opeaObj).show();
	                },100);
         	}
         },
         mouseout:function(e){
         	if(isMouseIn){
	                clearTimeout(timId);
	                $("#float").css({"display":"none"});
	                isMouseIn=false;
         	}
         }
     });
	}
	
//联系人框的10行阻止增加
var initContactPromptBox=function(){
	 var timId,mouseX,mouseY,isMouseIn;
    $("body").append("<div id='ContactPrompt' class='alert alert-warning hide' style='position:absolute;z-index:9999;'>"+ GCRM.util.message('hover.contactInfo.add.text') +"</div>");
            
     var opeaObj=$("#ContactPrompt");
     var delegateObj=$("#detailConcatInfo .panel-body");
     if($("#J_addContactBtnBox").length ==0){
    	 $("#J_addContactBtn").wrap("<div id='J_addContactBtnBox' style='display:inline-block;'/>");
     }
     delegateObj.delegate("#J_addContactBtnBox","mouseover",function(e){
       	if($(this).find("button:eq(0)").hasClass("disabled")){
               opeaObj.removeClass('hide');
               $(this).css("cursor","pointer");
               isMouseIn=true; 
              
     		}
       	
     });
     delegateObj.delegate("#J_addContactBtnBox","mousemove",function(e){
       	if(isMouseIn){
               mouseX=e.pageX;
               mouseY=e.pageY;
               opeaObj.css("display","none");
               clearTimeout(timId);
               timId=setTimeout(function(){
               	if(document.documentElement.clientWidth - mouseX > 260){
	                	opeaObj.css("left",mouseX+8+"px");
               	}else{
               		opeaObj.css("left",mouseX-$(opeaObj).width()-38+"px");
               	}
               	opeaObj.css("top",mouseY-$(opeaObj).height()-15+"px");	
               	opeaObj.show();
               },100);
       	}
     });
     delegateObj.delegate("#J_addContactBtnBox","mouseout",function(e){
       	if(isMouseIn){
               clearTimeout(timId);
               opeaObj.css({"display":"none"});
               isMouseIn=false;
               
       	}	
	  });         
}


$(document).ready(function(){
	$("#invalidCustomer,#restoreCustomer").click(function() {
		var eleId = $(this).attr("id");
		var customerId = $(this).attr("customerId");
		var status = $(this).attr("toStatus");
		var url = GCRM.constants.CONTEXT + "customer/updateStatus";
	    if($.trim(status) == '' || $.trim(customerId) == ''){
	        return false; 
	    }else{
	        url += "/" + customerId + "/" + status;
	    }
	    confirmModel({
    		id: "invalidModel",
    		alertText: eleId == 'invalidCustomer' ? GCRM.util.message("customer.list.invalid.confirm") : GCRM.util.message("customer.list.restore.confirm"),
    		okFunction: function(){
    			
			    $.post(url,function(data){
			        if(data.success){
			        	if(eleId == 'invalidCustomer'){
				        	
		        			//refreshCompanyHeader(customerId);
		        			location.reload();				        			
//		        			$("#"+eleId).addClass("hidden");
//		        			$("#restoreCustomer").removeClass("hidden");				        	
			        	}else{
			        		$("#invalidCustomer").removeClass("hidden");
			        		location.reload();
			        	}
			        }else{
			        	alert(GCRM.util.message("view.operate.failed"));
			        }
	    		});
			}	
		});
	});
	
	$.gcrm.dealCancellation("#editBaseInfo,#editMaterials,#editAgentQualification");
	/*初始化提交广告方案按钮的提示框*/
	initsubmitAdAlert();
});
