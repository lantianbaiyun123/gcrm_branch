$.gcrm.editMaterials = function(){
	//$("#saveBaseInfo").add("#cancelBaseInfo").hide();
	var $saveBtn = $("#saveMaterials"),
	$cancelBtn = $("#cancelMaterials"),
	$editBtn = $("#editMaterials"),
	$Container = $("#J_materialsContainer"),
	$SubmitBox=$("#MaterialsSubmitBox");
	
	$("body").undelegate("#editMaterials", "click").delegate("#editMaterials", "click", function(){
		
		var customerNumber = $("#customerNumber").val();
		var $el = $("<form id='materialsForm' class='form-horizontal' method='POST' action='../materials/doEditMaterials/"+customerNumber+"'></form>");
		$el.load("../materials/gotoEditMaterials/"+customerNumber, function(){
			//form验证
			//$.gcrm.validate($el);
			//select 美化
			$el.find('select').addClass("col-md-12").selectpicker({});
			$Container.empty().append($el);
			$editBtn.hide();
			$SubmitBox.removeClass("hide");
			$saveBtn.show();
			$cancelBtn.show();
			
			var index = $('#materialsTable>tbody>tr').length;
			$('#fileupload').fileupload({
		        dataType: 'json',
		        add: function (e, data) {
		        	if(/.*.exe$/.test(data.files[0].name)==false){
		        		data.submit();
		        	}else{
		        		//alert(GCRM.util.message('materials.extension.error'));
		        		alertModal("materialsExtensionModel",GCRM.util.message('materials.extension.error'));
		        	}
		        },
		        send:function (e, data) {
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
		            $('#materialsTable>tbody>tr:last-child').find('#fileupload').attr('name','attachments['+index+'].name');
		            $('#materialsTable>tbody>tr:last-child').find('#typeSelect').attr('name','attachments['+index+'].type');
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
		        		//alert(GCRM.util.message(file.message));
		        		alertModal("fileSaveSuccessModel",GCRM.util.message(file.message));
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
		            //console.log(file.name+"上传完成");
		            var progressId ="#"+data.barId;
		            
		            $(progressId).parent().text(GCRM.util.message('materials.upload.finished'));
		            //$.gcrm.deleteMaterials($("#J_materialsContainer"));
		            
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
			        //console.log(progressId);
			    }
		    });
			$.gcrm.deleteMaterials($("#J_materialsContainer"));
			lockTab("detailMaterials");
			
            /*禁用提交广告方案的按钮*/
			disabledSubmitAd();

		});
		return false;
	});

	$("body").undelegate("#saveMaterials", "click").delegate("#saveMaterials", "click", function(){
		var customerNumber = $("#customerNumber").val();
		$saveBtn.attr('disabled',true);
		$cancelBtn.attr('disabled',true);
		$("#materialsTable>tbody>tr:last-child").remove();
		$.ajax({
			type: "POST",
			 url: $("#materialsForm").attr("action"),
			 data: $("#materialsForm").serialize(),
			 success: function(msg){
				if(msg.success){
					var customerNumber = $("#customerNumber").val();
					$Container.load('../materials/gotoMaterialsDetail/'+customerNumber,function(){
						if($("#detailMaterialsEmpty")[0]){
							$editBtn.hide();
						}else{
							$editBtn.show();
						}
						$saveBtn.hide();
						$cancelBtn.hide();
						
						if($(".approvalStatus").val() == 'approved' && $(".m_approvalStatus").val() != 3)
							location.reload();
					});
				
					unlockTab("detailMaterials");
					
					/*解除禁用提交广告方案*/
					enabledSubmitAd();
					
					$("div.pull-right :submit").val(GCRM.util.message('gcrm.title.customer.submit')).addClass("submitToApprove").attr("customerId", customerId);
					var customerId = $("#J_detailBaseInfo a[id='editBaseInfo']").attr("customerId");
					var $J_companyHeaderContainer = $("#J_companyHeader .panel-body");
					$J_companyHeaderContainer.load('loadCompanyHeader/' + customerId);
					
				} else { 
					//alert('error');
					alertModal("saveMaterialsError",'Error');
				}
				$saveBtn.attr('disabled',false);
				$cancelBtn.attr('disabled',false);
			}
		});
	});

	$("body").undelegate("#cancelMaterials", "click").delegate("#cancelMaterials", "click", function(){
		var customerNumber = $("#customerNumber").val();	
		$Container.load('../materials/gotoMaterialsDetail/'+customerNumber, function(){
			if($("#detailMaterialsEmpty")[0]){
				$editBtn.hide();
			}else{
				$editBtn.show();
			}
			$SubmitBox.addClass("hide");
			$saveBtn.hide();
			$cancelBtn.hide();
			unlockTab("detailMaterials");
			
			/*解除禁用提交广告方案*/
			enabledSubmitAd();

		});
		return false;
	});
};
/*编辑 materials*/

$.gcrm.popoverDel=function(operateObj,popoverOpti){
	//**定义生成删除框的参数**//
	var popoverObj=popoverOpti || {
			'html':true,
			'content':"<p class='text-center popoverButtons' style='margin:0;'><a href='#' class='btn btn-primary  btn-md J_delMaterialsOk'>"+GCRM.util.message('materials.delete.yes')+"</a>  <a href='#' class='btn btn-default btn-md J_delMaterialsCancel' >"+GCRM.util.message('materials.delete.no')+"</a></p>",
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


    //**为所有的删除按钮增加提示框**//
    $.gcrm.popoverDel(operateArea.find(".J_delMaterials"));


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


