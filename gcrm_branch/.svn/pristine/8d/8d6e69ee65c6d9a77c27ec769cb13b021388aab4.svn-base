$.gcrm = $.gcrm || {};
/*!
    编辑联系人
*/
$.gcrm.editContact = function(parentNode){
	var getTrIndex = function(trArray){
		var thisTrMount=trArray.length;
		if(thisTrMount == null || thisTrMount < 1){
			thisTrMount = -1;
		}else{
			thisTrMount = thisTrMount - 1;
		}
		return thisTrMount;
	};
	
	var operateArea=parentNode || {};		//Jquery Object to Operated
	var editUrl= GCRM.constants.CONTEXT + "contact/update";		//edit contact submit Url
    var delUrl=GCRM.constants.CONTEXT + "contact/delete";			//delete contact submit Url
	var addUrl=GCRM.constants.CONTEXT + "contact/saveOrUpdate";		//add contact submit Url
	var contactId = "";
	var trNode= function(){
		var contacttTrStr = '<tr>';
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="512" namesuffix="name" ></td>';
		contacttTrStr += '<td><select class="form-control J_sex" namesuffix="gender" >'+
					'<option value="1">'+GCRM.util.message('contact.sex.male')+'</option>'+
					'<option value="2">'+GCRM.util.message('contact.sex.female')+'</option>'+
					'<option value="3">'+GCRM.util.message('contact.sex.other')+'</option>'+
					'</select></td>';
					
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="128" namesuffix="positionName"></td>';	
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="128" namesuffix="superiorPosition"></td>';	
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="128" namesuffix="department"></td>';	
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="32" namesuffix="mobile" placeholder="Zone-number"></td>';
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="32" namesuffix="telephone" name="telephone" validateref="phone" placeholder="Zone-number"></td>';	
		contacttTrStr += '<td><input class="form-control" type="text" maxlength="128" namesuffix="email" name="email" validateref="email" placeholder="ex.john@company.com"></td>';
		contacttTrStr += '<td><input class="form-control" type="checkbox" value="ENABLE" namesuffix="isLegalPerson"></td>';	
		contacttTrStr += '<td><input class="form-control" type="checkbox" value="ENABLE" namesuffix="isDecisionMaker"></td>';
		contacttTrStr += '<td><a class="form-control-static J_temDelContact" href="###">' + GCRM.util.message('view.delete') + '</a></td>';
		contacttTrStr += '</tr>';
		return contacttTrStr;
	};
	var trArrays = operateArea.find("table>tbody>tr");
	var thisTrMount=getTrIndex(trArrays);
	var phoneRegExp=/^([0-9]+)-([0-9]+)$/;
    var emailRegExp=/^(\w|\.|-)+@(\w)+\.(\w{2,3})+$/;
    var valideEmailAndPhone = function(parentTr,inputList){
    	if($.trim(inputList[5].value) != '' && phoneRegExp.test(inputList[5].value)==false){
			$(inputList[5]).closest("td").removeClass("has-success").addClass("has-error");
			return true;
		}else{
			$(inputList[5]).closest("td").removeClass("has-error").addClass("has-success");
		}
		if($.trim(inputList[6].value) != '' && emailRegExp.test(inputList[6].value)==false){
			$(inputList[6]).closest("td").removeClass("has-success").addClass("has-error");
			return true;
		}else{
			$(inputList[6]).closest("td").removeClass("has-error").addClass("has-success");
		}
		return false;
    };

    //**定义向后台post数据的操作**//
	var postContact=function(inputList,parTr,$self,postUrl,postDate){
		var hasError=true;
		hasError = valideEmailAndPhone(parTr,inputList);
		if(hasError){
			return;
		}
		
		$.post(postUrl,postDate,function(){
			$.each(inputList,function(index,value){
				$(value).attr("disabled","true");
			});
			$(parTr.find('.J_sex')[0]).attr("disabled","true");
			$(parTr.find('button.selectpicker')[0]).removeClass().addClass("btn dropdown-toggle selectpicker disabled btn-default");
			$.each(parTr.find('ul.selectpicker>li'),function(index,value){
				$(value).addClass("disabled");
			});
			if($self.hasClass("J_submitContact"))
				$self.removeClass("J_submitContact").addClass("J_editContact").html(GCRM.util.message('view.edit'));
			if($self.hasClass("J_editOK"))
				$self.removeClass("J_editOK").addClass("J_editContact").html(GCRM.util.message('view.edit'));
		});
	};
	
	var validateTrs = function(trArrays){
		var hasError = false;
		$(trArrays).each(function (index, trObj) {
			var $trObj = $(trObj);
			var inputList = $trObj.find("input");
			var isDisabled = $(inputList[0]).attr("disabled");
			if(isDisabled){
				return true;
			}
			
			hasError = valideEmailAndPhone($trObj,inputList);
			if(hasError){
				return false;
			}
			
			var selectList = $(trObj).find('select');
			if($.trim(inputList[0].value) == ""){
				var hasOtherValue = false;
				for(var i = 1; i<inputList.length; i++){
					if(i < 7 && $.trim(inputList[i].value) != ''){
						hasOtherValue = true;
						break;
					}else if(inputList[i].checked){
						hasOtherValue = true;
						break;
					}
				}
				if(! hasOtherValue && selectList[0].selectedIndex > 0){
					hasOtherValue = true;
				}
				if(hasOtherValue){
					$(inputList[0]).closest("td").removeClass("has-success").addClass("has-error");
					hasError = true;
					return false;
				}
			}else{
				$(inputList[0]).closest("td").removeClass("has-error").addClass("has-success");
			}
		});

		return hasError;
	};
	
	
	var fillFromIndex = function(trArrays){
		var currFromIndex = 0;
		$(trArrays).each(function (index, trObj) {
			var inputList = $(trObj).find("input");
			var isDisabled = $(inputList[0]).attr("disabled");
			if(isDisabled){
				return true;
			}
			
			for(var i = 0; i<inputList.length; i++){
				var inputObj = $(inputList[i]);
				var currNameSuffix = inputObj.attr("namesuffix");
				inputObj.attr("name","contacts[" + currFromIndex + "]." + currNameSuffix);
			}
			
			var selectList = $(trObj).find('select');
			var selectObj = $(selectList[0]);
			var currSelectNameSuffix = selectObj.attr("namesuffix");
			selectObj.attr("name","contacts[" + currFromIndex + "]." + currSelectNameSuffix);
			
			currFromIndex++;
			
		});
		
		return currFromIndex;

	};
	
	/*选择业务机会和代理商资历*/
	var selectPanel=function($btn,$panel){
		if((!$panel.hasClass("hide"))&&$btn.hasClass("J_init"))
			$btn.click();
	};
	
	var popoverObj={
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'><a href='#' class='btn btn-primary  btn-md J_delContactOk'>"+ GCRM.util.message('view.ok') +"</a>  <a href='#' class='btn btn-default btn-md J_delContactCancel' >"+GCRM.util.message('view.cancel')+"</a></p>",
			'trigger':"click",
			'placement':"left",
			'title':GCRM.util.message('delete.confirm'),
			'delay':{show:100,hide:100}
		};
	
	var popoverInfoObj=function (title){
		var infoObj = {
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'> <a href='#' class='btn btn-default btn-md J_TempDelContactCancel' >"+GCRM.util.message('view.ok')+"</a></p>",
			'trigger':"click",
			'placement':"left",
			'title':title,
			'delay':{show:100,hide:100}
		};
		
		return infoObj;
	};

	/** 为表格中删除、编辑和确认三个按钮绑定click事件 begin**/
    operateArea.delegate("a:not(#editContactInfo)","click",function(){
        var $self = $(this);
        var parTr=$(this).closest("tr");
        var inputList=parTr.find("input");
        var contactId=$self.attr("refercontactid");
        //var customerNumber = $("#customerNumber").val();
        var customerNumber = $(".customerNumber").val();
        var postDate={
        		"customerNumber":customerNumber,
                "id":contactId,
                "name":encodeURI(inputList[0].value),
                "gender":parTr.find('select')[0].selectedIndex,
                "positionName":encodeURI(inputList[1].value),
                "superiorPosition":encodeURI(inputList[2].value),
                "department":encodeURI(inputList[3].value),
                "mobile":inputList[4].value,
                "telephone":inputList[5].value,
                "email":inputList[6].value,
                "isLegalPerson":inputList[7].checked ? 'ENABLE' : 'DISABLE',
                "isDecisionMaker":inputList[8].checked ? 'ENABLE' : 'DISABLE'
            };

        if($self.hasClass("J_editContact")){
            $.each(inputList,function(index,elementObj){
                $(elementObj).removeAttr("disabled");
            });
            
            $(parTr.find('.J_sex')[0]).removeAttr("disabled");
            $(parTr.find('button.selectpicker')[0]).removeClass().addClass("btn dropdown-toggle selectpicker btn-default");
            $.each(parTr.find('ul.selectpicker>li'),function(index,value){
                $(value).removeClass("disabled");
            });
            $self.html(GCRM.util.message('view.ok'));
            $self.removeClass("J_editContact").addClass("J_editOK");
            
        }else if($self.hasClass("J_editOK")){
        	var hasError = validateTrs(parTr);
    		if(hasError){
    			return false;
    		}
			postContact(inputList,parTr,$self,editUrl,postDate);

        }else if($self.hasClass("J_delContact")){
			$(".J_delContact").popover("hide");	
        }
        return false;
    }); /** 为表格中删除、编辑和确认三个按钮绑定click事件 end**/

	//**删除提示框的显示事件**//
    operateArea.delegate('.J_delContact','click',function (){
    	var $self=$(this);
		$self.popover('destroy');
		if(thisTrMount <= 0){
			$self.popover(popoverInfoObj(GCRM.util.message('contact.del.limit'))).popover('show');
		}else{
			$self.popover(popoverObj).popover('show');
		}
		contactId = $self.attr("refercontactid");
		return false;
	});
    
	//**确定删除的事件**//
	operateArea.delegate("a.J_delContactOk","click",function(){
		var $self=$(this);
		var postDate={"id":contactId};
		$.post(delUrl,postDate,function(data){
			if(data != null && data.success){
				thisTrMount--;
				$($self.closest("tr")).remove();
				if(thisTrMount<9){
					operateArea.find("#J_addContactBtn").removeClass("disabled");
				}
			}
		});
		
		return false;	  
	});
	
	operateArea.delegate("a.J_delContactCancel","click",function(){
		$(this).closest(".popover").remove();
		return false;
	});
	
	operateArea.delegate(".J_temDelContact","click",function(){
		var self = $(this);
		if((thisTrMount - 1)<9){
			operateArea.find("#J_addContactBtn").removeClass("disabled");
		}
		if(thisTrMount <= 0){
			self.popover('destroy');
			self.popover(popoverInfoObj(GCRM.util.message('contact.del.limit'))).popover('show');
		}else{
			thisTrMount--;
			$(self.closest("tr")).remove();
		}
		
		return false;	  
	});
	
	operateArea.delegate(".J_TempDelContactCancel","click",function(){
		$(this).closest(".popover").remove();
		return false;
	});
	

	//**增加联系人的事件**//
	operateArea.delegate('#J_addContactBtn',"click",function(){
		var trArrays = operateArea.find("table>tbody>tr");
		thisTrMount = getTrIndex(trArrays);
		if((thisTrMount + 1) > 9){
			operateArea.find("#J_addContactBtn").addClass("disabled");
			return false;
		}
		var currTrHtml = trNode();
		$(operateArea.find("table tbody:eq(0)")).append(currTrHtml);
		operateArea.find("table>tbody>tr:last select.J_sex").addClass("col-md-12").selectpicker({});
		thisTrMount++;
	});
	
	operateArea.delegate('#J_submitContact',"click",function(){
		
		$currForm = $("#saveContactInfoForm");
		var trArrays = operateArea.find("table>tbody>tr");
		thisTrMount = getTrIndex(trArrays);
		if(thisTrMount<9){
			$("#J_addContactBtn").removeClass("disabled");
		}
		var hasError = validateTrs(trArrays);
		if(hasError){
			return false;
		}else{
			var currFormIndex = fillFromIndex(trArrays);
			if(currFormIndex < 1){
				$("#J_cancelContact").click();
				
				/*打开业务机会的模块*/
				selectPanel($("#editBusinessChance"),$("#detailBusinessChance"));
				/*打开代理商资历的模块*/
				selectPanel($("#editAgentQualification"),$("#detailAgentQualification"));
				
				return false;
			}
		}
		var postDate = $currForm.serialize();
		$.post(addUrl,postDate,function(){
			
			/*newCustomer 专用*/
			if($("#editContactInfo")){
				//$("#editContactInfo").click();
		        $.gcrm.loadModule({
		        	moduleId :$("#editContactInfo").attr("data-id"),
		            container:$("#detailConcatInfo").find(".panel-body"),
		            url: $("#editContactInfo").attr("data-url")+$(".customerNumber").val() || moduleConfig[id].url,
		            js:moduleConfig[$("#editContactInfo").attr("data-id")].js,
		            success:function(){
		            	
		            	$("#detailConcatInfo .panel-body").removeClass("hide");
		            	$("#J_submitContact").closest("div").addClass("hide");
	            		$("#contactInfoSubmitBox").removeClass("hide").addClass("hide");
	            		           		
	            		/*显示修改按钮*/
						reviseBtnShow();
						
						/*切换修改和添加按钮*/
						var temp_editContactInfo=$("#editContactInfo");
						if(!$("#contactsEdit tbody tr")[0]){
							$("#detailConcatInfo .panel-body").addClass("hide");
							temp_editContactInfo.text("[ "+GCRM.util.message('gcrm.title.customer.add')+" ]");
						}else{
							modifyChangeBtn(temp_editContactInfo,$("#detailConcatInfo .panel-body"));
						}
						temp_editContactInfo=null;
						
						/*删除最后一个td*/
						delLastTd();
						/*解除提交审核按钮*/
						enabledSubmit();
						/*打开业务机会的模块*/
						selectPanel($("#editBusinessChance"),$("#detailBusinessChance"));
						/*打开代理商资历的模块*/
						selectPanel($("#editAgentQualification"),$("#detailAgentQualification"));
		            }
		        });
			}
		});
	});
	

};

