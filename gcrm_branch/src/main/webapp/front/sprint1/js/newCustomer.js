
$(".panel-gcrm .panel-heading").prepend("<span class='black-block'>&nbsp;</span>");

var delLastTd=function(){
	$("#contactsEdit tbody tr,#contactsEdit thead tr").find("td:last,th:last").remove();
}

var modifyChangeBtn=function(btn,Container){
    var editBtn=btn;
    var container=Container;
    var modify="[ "+ GCRM.util.message("gcrm.title.customer.modify") +" ]";
    var add="[ "+ GCRM.util.message("gcrm.title.customer.add") +" ]";
    var isNewCustomerPage=!!$(".addcustomer")[0];
	if((!container.hasClass("hide"))&&editBtn.hasClass("J_init")){
		editBtn.removeClass("J_init").text(modify );	
	}else if(container.hasClass("hide")){
		if(isNewCustomerPage)
			editBtn.addClass("J_init");
		editBtn.text(add);
	}else{
		editBtn.text(modify);
	}
}



$(function(){
	//$.gcrm.editContact($("#detailConcatInfo"));
	$("body").undelegate(".submit","click").delegate(".submit", "click", function() {
		var id = $(this).attr("customerId");
		$.ajax({
			type: "POST",
	 		url: "submit",
	 		data: {"customer.id" : id},
	 		dataType: "json",
			success: function(msg){
				if(msg.success){
					var message = GCRM.util.message("gcrm.title.customer.submitToApprove");
					tempSaveModel('coutomerSubmitPass',message,2000,function(){
						window.open( GCRM.constants.CONTEXT + "customer/" + id, '_self' );
					});
					
				} else { 
					var message = GCRM.util.message("customer.submit.failed");
					$.each(msg.errors, function(i, item){ 
						message += "，" + GCRM.util.message(item);
					});
					alertModal("coutomerSubmitFail",message);
				}
			}
		});
		
		//方法定义在detail.js中，用于提示联系人上限为10
		initContactPromptBox();
});
	
	$("body").undelegate("#editContactInfo", "click").delegate("#editContactInfo", "click", function(){
	    
	    try{
	    	var customerNumber=$(".customerNumber").val();
	        var $self = $(this);
	        var id = $(this).attr("data-id");
	        $.gcrm.loadModule({
	        	moduleId :id,
	            container:$("#detailConcatInfo").find(".panel-body"),
	            url: $self.attr("data-url")+customerNumber || moduleConfig[id].url,
	            js:moduleConfig[id].js,
	            success:function(){
	            	if($("#editContactInfo"))
	            		$("#editContactInfo").addClass("hide");
	            	$("#detailConcatInfo .panel-body").removeClass("hide");
	            	
	        		$("#detailConcatInfo #J_addContactBtn").removeClass("disabled");
	            	
	            	/*JS修改DOM样式*/
	            	$("#detailConcatInfo #J_submitContact").hide().removeAttr("id");	            	
	            	$("#detailConcatInfo #J_addContactBtn").removeClass("btn-success").addClass("btn-link").css({"outline":"none","color":"#5bb959"}).wrap('<div id="J_addContactBtnBox" style="display:inline-block;"/>');
	            	$("#J_addContactBtn span").css("margin-right","0.2em");
	            	$("#contactsEdit").css("margin-bottom","0px");
	            	if($("#contactInfoSubmitBox").length==0){
		            	$("#detailConcatInfo").append($('<div id="contactInfoSubmitBox" style="border-top: 1px solid #cccccc;padding: 5px 20px;"><button class="btn btn-primary J_submitContact" id="J_submitContact">'+GCRM.util.message('view.save')+'</button><button  type="button" id="J_cancelContact" class="btn btn-default" style="margin-left:10px;">'+GCRM.util.message('view.cancel')+'</button></div>'));
		            } else if($("#contactInfoSubmitBox").hasClass("hide")){
		            	$("#contactInfoSubmitBox").removeClass("hide");
		            	$(".J_submitContact").attr("id","J_submitContact").show();
		            }
	            	
	            	initContactPromptBox();
	            	
	                moduleConfig[id].success();
	                
	            	/*表格为空时自动添加一行可编辑行*/
	            	if(!$("#contactsEdit tbody tr")[0]){
	            		$("#J_addContactBtn").click();
						$self.addClass("J_init").text("[ "+ GCRM.util.message("gcrm.title.customer.add") +" ]");
	            	}
	            	else if($("#contactsEdit tbody tr").length >= 10){
	            		$("#J_addContactBtn").addClass("disabled");
	            	}
	            		
	            }
	        });
	    }catch(e){
	        console.log(e);
	    }   	    
	});
	
	$("body").undelegate("#J_cancelContact", "click").delegate("#J_cancelContact", "click", function(){
		var $editContactInfo = $("#editContactInfo");
		$.gcrm.loadModule({
	        	moduleId :$editContactInfo.attr("data-id"),
	            container:$("#detailConcatInfo").find(".panel-body"),
	            url: $editContactInfo.attr("data-url")+$(".customerNumber").val() || moduleConfig[id].url,
	            js:moduleConfig[$editContactInfo.attr("data-id")].js,
	            success:function(){
	            	$("#J_addContactBtn .panel-body").removeClass("hide");
	            	$("#J_submitContact").closest("div").addClass("hide");
	            	$("#contactInfoSubmitBox").addClass("hide");
	            	
	            	/*显示修改按钮*/
					reviseBtnShow();
//					modifyChangeBtn($editContactInfo,$("#detailConcatInfo"));
					
					/*删除最后一个td*/
					delLastTd();
					
					if(!$("#contactsEdit tbody tr")[0]){
						$("#detailConcatInfo .panel-body").addClass("hide");
					}
	            }
	        });
	});
	

	$("body").undelegate("#editBusinessChance", "click").delegate("#editBusinessChance", "click", function(){
	    
	    try{
	    	var customerNumber=$(".customerNumber").val();
	        var $self = $(this);
	        var id = $(this).attr("data-id");
	        $(this).attr("customerid",customerNumber);
	        $("#saveBusinessChance,#cancelBusinessChance").attr("customerid",customerNumber);
	        $("#detailBusinessChance").find(".panel-body").empty().append($("<form id='businessChance' class='form-horizontal' method='POST' action='opportunity/preUpdateOpportunity?'></form>"));
	        $.gcrm.loadModule({
	        	moduleId :id,
	            container:$("#detailBusinessChance").find("#businessChance"),
	            url: $self.attr("data-url")+customerNumber || moduleConfig[id].url,
	            js:moduleConfig[id].js,
	            success:function(){
	            	if($("#editBusinessChance"))
	            		$("#editBusinessChance").addClass("hide");
	            	$("#BusinessChanceSubmitBox").removeClass("hide");
	            	$.gcrm.editBusinessChance();
	            	
	            	$("#detailBusinessChance .panel-body").removeClass("hide");
	            	
	                moduleConfig[id].success();
	                
	                
	            }
	        });
	    }catch(e){
	        console.log(e);
	    }   	    
	});

	$("body").undelegate("#editAgentQualification", "click").delegate("#editAgentQualification", "click", function(){
	   
	    try{
	    	var customerNumber=$(".customerNumber").val();
	        var $self = $(this);
	        var id = $(this).attr("data-id");
	   
	        if(customerNumber){
	        	$("#editAgentQualification,#saveAgentQualification,#cancelAgentQualification").attr("customerNumber",customerNumber);
	        }

	        $.gcrm.loadModule({
	        	moduleId :id,
	            container:$("#detailAgentQualification").find("#agentQualificationForm"),
	            url: $self.attr("data-url")+customerNumber || moduleConfig[id].url,
	            js:moduleConfig[id].js,
	            success:function(){
	            	
	            	if($("#editAgentQualification"))
	            		$("#editAgentQualification").addClass("hide");
	            	$.gcrm.editAgentQualification();
	            	
	            	$("#detailAgentQualification .panel-body").removeClass("hide");
	            	$("#AgentQualificationSubmitBox").removeClass("hide");
	            	
	                moduleConfig[id].success();
	            }
	        });

	    }catch(e){
	    	console.log(e);
	    }   	    
	});

	$.gcrm.editMaterials();
	
	$("body").delegate("#saveBaseInfo","click",function(){
		var $detailBusinessChance = $("#detailBusinessChance");
		var $detailAgentQualification = $("#detailAgentQualification");
		
		if($("select[name='customer.businessType']").val()==0){
			$detailAgentQualification.removeClass("hide");
			$detailBusinessChance.addClass("hide");
			$detailBusinessChance.find(".panel-body").addClass("hide");
			$detailBusinessChance.find("#editBusinessChance").addClass("J_init").text("[ "+ GCRM.util.message("gcrm.title.customer.add") +" ]");
    	}
		else{
			$detailBusinessChance.removeClass("hide");
			$detailAgentQualification.addClass("hide");
			$detailAgentQualification.find(".panel-body").addClass("hide");
			$detailAgentQualification.find("#editAgentQualification").addClass("J_init").text("[ "+ GCRM.util.message("gcrm.title.customer.add") +" ]");
		}
		
	});
	
	$("body").delegate("#J_cancelContact,#cancelBusinessChance,#cancelMaterials,#cancelAgentQualification","click",function(){
		$(".submit").removeAttr("disabled").removeClass("btn-default").addClass("btn-primary");
		
	});
	/*因为detailbaseInfo里面阻止了冒泡所以这里独立出来绑定*/
	$("#J_detailBaseInfo").delegate("#cancelBaseInfo","click",function(){
		$(".submit").removeAttr("disabled").removeClass("btn-default").addClass("btn-primary");
	});
	
    $("#editBaseInfo").click(function(){
        $("#cancelBaseInfo").removeAttr("disabled");
    });
    
    var tempStr="#editMaterials,#editAgentQualification,#editBusinessChance,#editContactInfo,#editBaseInfo";
    $("body").delegate(tempStr,"click",function(){
        $(tempStr).addClass('hide');
        $(".submit").attr("disabled","true").removeClass("btn-primary").addClass("btn-default");
    });
	
	var initsubmitAlert=function(){
	   var timId,mouseX,mouseY,isMouseIn;
       $("body").append("<div id='float'class='alert alert-warning hide' style='position:absolute;z-index:9999;'>"+ GCRM.util.message('hover.tips.first') +"<span style='color:#3276b1;'> "+ GCRM.util.message('hover.tips.second') +"</span>"+ GCRM.util.message('hover.tips.third') +"</div>");
               
        var opeaObj=$("#float")[0];
        
        var setFloatText=function(){
        	var target=$(opeaObj),
        		nowEdit="";
        	if(!$("#BaseInfoSubmitBox").hasClass("hide"))
        		nowEdit=GCRM.util.message("customer.title.base");
        	else if(!$("#J_addContactBtn").closest("div").hasClass("hide"))
        		nowEdit=GCRM.util.message("contact.info");
        	else if(!$("#BusinessChanceSubmitBox").hasClass("hide"))
        		nowEdit=GCRM.util.message("opportunity.title");
        	else if(!!$("#AgentQualificationSubmitBox")[0])
        		nowEdit=GCRM.util.message("qualification.title");
        	else if(!$("#MaterialsSubmitBox").hasClass("hide"))
        		nowEdit=GCRM.util.message("materials.title");
        	
        	target.find("span").text(" "+nowEdit||" "+GCRM.util.message("hover.tips.nowedit.text"));
        }
        
        $(".submit").closest("div").bind({
            mouseover:function(e){
            	if($(this).find(".submit").attr("disabled")==="disabled"){
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
	initsubmitAlert();
	
	/*删除联系表的最后一列td*/
	if($("#contactsEdit tbody tr")[0])
		delLastTd();
});

