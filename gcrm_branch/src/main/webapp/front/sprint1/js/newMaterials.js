$.gcrm.editMaterials = function(){
	//$("#saveBaseInfo").add("#cancelBaseInfo").hide();
	var $saveBtn = $("#saveMaterials"),
	$cancelBtn = $("#cancelMaterials"),
	$editBtn = $("#editMaterials"),
	$Container = $("#J_materialsContainer"),
	$SubmitBox=$("#MaterialsSubmitBox");
	
	var alertfun=function(){
		alert(GCRM.util.message('alertDialog.lockTab.text'));
	}

	$("body").undelegate("#editMaterials", "click").delegate("#editMaterials", "click", function(){
		$("a[href='#detailMaterials']:eq(0)").attr("data-url","");	
		var customerNumber = $(".customerNumber").val();
		var $el = $("<form id='materialsForm' class='form-horizontal' method='POST' action='"+GCRM.constants.CONTEXT+"materials/doEditMaterials/"+customerNumber+"'></form>");
		$el.load(GCRM.constants.CONTEXT + "materials/gotoEditMaterials/"+customerNumber, function(){
			 $saveBtn.removeClass("disabled");
			//select 美化
			$el.find('select').addClass("col-md-12").selectpicker({});
			$Container.empty().append($el);
			$editBtn.addClass("hide");
			$SubmitBox.removeClass("hide");
			$saveBtn.removeClass("hide");
			$cancelBtn.removeClass("hide");

			/*newCustomer 专用*/
			$("#fileupload").attr("data-url",GCRM.constants.CONTEXT + "materials/doUploadFile");
			$("#detailMaterials .panel-body").removeClass("hide");
			
			var index = $('#materialsTable>tbody>tr').length;
			$('#fileupload').fileupload({
		        dataType: 'json',
		        add: function (e, data) {
		        	if(/.*.exe$/.test(data.files[0].name)==false){
		        		data.submit();
		        	}else{
		        		alert(GCRM.util.message('materials.extension.error'));
		        	}
		        	$saveBtn.addClass("disabled");
		        },
		        send:function (e, data) {
		        	index = index+1;
		        	
		        	var $type = $("#materialsTable").find("tbody>tr:last-child>td:eq(0) select:eq(0)").clone();
		        	var prefix = $type.attr('name').substring(0,$type.attr('name').length-5);
		        	
		        	var processBarId = prefix+'Bar';
		        	processBarId=processBarId.replace("[", "");
		        	processBarId=processBarId.replace("]", "");
		        	data.barId = processBarId;
		        	
		        	data.paramName = prefix+".name";
		        	
		            data.context = [
		            	'<tr>',
				        '    <td class="col-md-2"></td>',
				        '    <td></td>',
				        '    <td><div id="progress">',
				        '        <div class="bar" id="'+processBarId+'" style="width: 10%;"></div>',
				        '    </div></td>',
				        '    <td><a id='+prefix+'.deletePath'+' class="J_delMaterials" data-id="" data-url="" data-tempUrl="" href="###">'+GCRM.util.message('materials.delete')+'</a></td>',
				        '</tr>'
		            ].join("");
		            //var $type = $("#materialsTable").find("tbody>tr:last-child>td:eq(0)").clone(true);
		           
		            
		            var $tpl = $(data.context);

		            $tpl.find("td:eq(0)").html($type);
		        
		            $tpl.find("td:eq(1)").html(
		            		 '<input id='+prefix+'.name type="hidden" name="'+prefix+'.name" value="'+data.files[0].name+'">'
		            		+'<input id='+prefix+'.url type="hidden" name="'+prefix+'.url" value="'+data.files[0].url+'">'
		            		+'<input id='+prefix+'.tempUrl type="hidden" name="'+prefix+'.tempUrl" value="'+data.files[0].tempUrl+'">'
		            		+'<input id='+prefix+'.id type="hidden" name="'+prefix+'.id" value="'+data.files[0].id+'">'
		            		+'<input id='+prefix+'.exit type="hidden" name="'+prefix+'.exit" value="'+data.files[0].exit+'">'
		            		+'<input id='+prefix+'.customerNumber type="hidden" name="'+prefix+'.customerNumber" value="'+data.files[0].customerNumber+'">'
		            		+'<input id='+prefix+'.fieldName type="hidden" name="'+prefix+'.fieldName" value="'+data.files[0].fieldName+'">'
		            		
		            		+data.files[0].name);
		            //$tpl.find("td:eq(2)").find("#progress").attr("id","attachments"+index+"progress");
		            $('#materialsTable>tbody>tr:last-child').find('#fileupload').attr('name','attachments['+(index+1)+'].name');
		            $('#materialsTable>tbody>tr:last-child').find('#typeSelect').attr('name','attachments['+(index+1)+'].type');
		            data.context = $tpl.insertBefore('#materialsTable>tbody>tr:last-child');
		            //suweirong add
		            var selectIndex= $("#materialsTable").find("tbody>tr:last-child>td:eq(0) select:eq(0)").val();
		            $('select').addClass("col-md-12").selectpicker({});
		            $type.selectpicker('val',""+selectIndex);
		        },
		        done: function (e, data) {
		        	var paramName = data.paramName; 	
		        	url = paramName.replace('name','url');
		        	tempUrl = paramName.replace('name','tempUrl');
		        	id = paramName.replace('name','id');
		        	exit = paramName.replace('name','exit');
		        	customerNumber = paramName.replace('name','customerNumber');
		        	fieldName = paramName.replace('name','fieldName');
		        	deletePath = paramName.replace('name','deletePath');
		        	
		        	file = data.result;
		        	if(file.message!='success'){
		        		$("input[name='"+fieldName+"']").parent().parent().remove();
		        		alert(GCRM.util.message(file.message));
		        		return;
		        	}

		            $("input[name='"+url+"']").attr('value',file.url);
		            $("input[name='"+tempUrl+"']").attr('value',file.tempUrl);
		            $("input[name='"+id+"']").attr('value',file.id);
		            $("input[name='"+exit+"']").attr('value',true);//默认要保存
		            $("input[name='"+customerNumber+"']").attr('value',file.customerNumber);
		            $("input[name='"+fieldName+"']").attr('value',file.fieldName);
		            $("input[name='"+url+"']").attr('value',file.url);
		            //$("a[id='"+deletePath+"']").attr('onclick',"deleteFileBefore(this,file)");
		            $("a[id='"+deletePath+"']").attr('data-id',file.id);
		            $("a[id='"+deletePath+"']").attr('data-url',file.url);
		            $("a[id='"+deletePath+"']").attr('data-tempUrl',file.tempUrl);
		            console.log(file.name+"上传完成");
		            var progressId ="#"+data.barId;
		            
		            $(progressId).parent().text(GCRM.util.message('materials.upload.finished'));
		            //$.gcrm.deleteMaterials($("#J_materialsContainer"));
		            
		            $saveBtn.removeClass("disabled");
		        },
		        progress: function (e, data) {
			        var progress = parseInt(data.loaded / data.total * 100, 10);
			        var progressId ="#"+data.barId;
			        $(progressId).css(
			            'width',
			            progress + '%'
			        );
			        if(progress>=100)
			        	$.gcrm.popoverDel($("#J_materialsContainer").find(".J_delMaterials"));
			        console.log(progressId);
			    }
		    });
			$.gcrm.deleteMaterials($("#J_materialsContainer"));

			//end upload function
		});
		return false;
	});

	$("body").undelegate("#saveMaterials", "click").delegate("#saveMaterials", "click", function(){
		var customerNumber=$(".customerNumber").val();
		$("a[href='#detailMaterials']:eq(0)").attr("data-url",GCRM.constants.CONTEXT + "materials/gotoEditMaterials/"+customerNumber);
		$saveBtn.attr('disabled',true);
		$cancelBtn.attr('disabled',true);
		var customerNumber = $(".customerNumber").val();
		$.ajax({
			type: "POST",
			 url: $("#materialsForm").attr("action"),
			 data: $("#materialsForm").serialize(),
			 success: function(msg){
				if(msg.success){
					$Container.load(GCRM.constants.CONTEXT + 'materials/gotoMaterialsDetail/'+customerNumber,function(){
						if($("#detailMaterialsEmpty")[0]){
							$Container.addClass("hide").empty();
						}
						$SubmitBox.addClass("hide");
						modifyChangeBtn($("#editMaterials"),$("#J_materialsContainer"));
//						$saveBtn.addClass("hide");
//						$cancelBtn.addClass("hide");
					});
					
					$("div.pull-right :submit").val(GCRM.util.message('gcrm.title.customer.submit')).addClass("submitToApprove").attr("customerId", customerId);
					var customerId = $("#J_detailBaseInfo a[id='editBaseInfo']").attr("customerId");
					var $J_companyHeaderContainer = $("#J_companyHeader .panel-body");
					console.log("提交文件");
					$J_companyHeaderContainer.load(GCRM.constants.CONTEXT + 'customer/loadCompanyHeader/' + customerId);
					
					/*显示修改按钮*/
					reviseBtnShow();
					/*解除提交审核按钮*/
					enabledSubmit();
					
				} else { 
					alert('error');				
				}
				$saveBtn.attr('disabled',false);
				$cancelBtn.attr('disabled',false);
			}
		});
	});

	$("body").undelegate("#cancelMaterials", "click").delegate("#cancelMaterials", "click", function(){
		var customerNumber=$(".customerNumber").val();
		$("a[href='#detailMaterials']:eq(0)").attr("data-url",GCRM.constants.CONTEXT + "materials/gotoEditMaterials/"+customerNumber);
		$Container.load(GCRM.constants.CONTEXT + 'materials/gotoMaterialsDetail/'+customerNumber, function(){
			if($("#detailMaterialsEmpty")[0]||$("#materialsTable tbody tr").length<1){
				$Container.addClass("hide");
			}
			$SubmitBox.addClass("hide");
//			$saveBtn.addClass("hide");
//			$cancelBtn.addClass("hide");
			/*显示修改按钮*/
			reviseBtnShow();
		});
		return false;
	});
};
/*编辑 materials*/

$.gcrm.popoverDel=function(operateObj,popoverOpti){
	//**定义生成删除框的参数**//
	var popoverObj=popoverOpti || {
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'><a href='#' class='btn btn-primary  btn-md J_delMaterialsOk'>"+ GCRM.util.message("contact.delOk") +"</a>  <a href='#' class='btn btn-default btn-md J_delMaterialsCancel' >"+ GCRM.util.message("contact.delCancel") +"</a></p>",
			'trigger':"click",
			'placement':"left",
			'title':GCRM.util.message('delete.confirm'),
			'delay':{show:100,hide:100}
		};
	var operateArea=operateObj || {};
	operateArea.popover(popoverObj);
};

/*删除提示框*/
$.gcrm.deleteMaterials = function(parentNode){
	var operateArea=parentNode || {};

	var thisTrMount=operateArea.find("table>tbody>tr").length-1;

    //**为所有的删除按钮增加提示框**//
    $.gcrm.popoverDel(operateArea.find(".J_delMaterials"));

	/** 为文件删除按钮绑定事件 begin**/
   // operateArea.delegate(".J_delMaterials","click",function(){
    	//alert($(this).html());
    //	$(this).popover("show");
   // });

	//**删除提示框的显示事件**//
	operateArea.undelegate(".J_delMaterials",'shown.bs.popover').delegate(".J_delMaterials",'shown.bs.popover',function (){
		var $self=$(this);
		$self.next(".popover").css("zIndex",1011);
	});

	//**删除提示框隐藏事触发的事件**//
	operateArea.undelegate(".J_delMaterials",'hidden.bs.popover').delegate(".J_delMaterials",'hidden.bs.popover',function (){
		$(this).next(".popover").css("zIndex",1010);
	});

	//**确定删除的事件**//
	operateArea.undelegate("a.J_delMaterialsOk","click").delegate("a.J_delMaterialsOk","click",function(){
		var $self=$(this);
		var $tr = $(this).closest("tr");
		$tr.hide();
		$tr.find("input:eq(4)").val(false);
		return false;	   
	});

	//**取消删除的事件**//
	operateArea.undelegate("a.J_delMaterialsCancel","click").delegate("a.J_delMaterialsCancel","click",function(){
		$(".J_delMaterials").popover("hide");
		return false;
	});

	$(document).undelegate("*","click").delegate("*","click",function(){
		$(".J_delMaterials").popover("hide");
	});
};


