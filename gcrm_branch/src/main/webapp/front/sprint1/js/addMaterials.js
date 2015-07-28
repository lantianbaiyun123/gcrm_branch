$.gcrm = $.gcrm || {};
//$.gcrm = {};

$(function(){
	/*!
		整页验证
	*/
	$.gcrm.validate();

	$('select').addClass("col-md-12").selectpicker({});
	var index = $('#materialsTable>tbody>tr').size();
	$('#fileupload').fileupload({
        dataType: 'json',
        add: function (e, data) {
        	if(/.*.exe$/.test(data.files[0].name)==false){
        		data.submit();
        	}else{
        		alert(GCRM.util.message('materials.extension.error'));
        	}
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
		        '        <div class="bar" id="'+processBarId+'" style="width: 30%;"></div>',
		        '    </div></td>',
		        '    <td><a id='+prefix+'.deletePath'+' href="###">删除</a></td>',
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
           /* $('#materialsTable>tbody>tr:last-child').find('#fileupload').attr('name','attachments['+(index+1)+'].name');
            $('#materialsTable>tbody>tr:last-child').find('#typeSelect').attr('name','attachments['+(index+1)+'].type');*/
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
            $("input[name='"+exit+"']").attr('value',file.exit);
            $("input[name='"+customerNumber+"']").attr('value',file.customerNumber);
            $("input[name='"+fieldName+"']").attr('value',file.fieldName);
            $("input[name='"+url+"']").attr('value',file.url);
            $("a[id='"+deletePath+"']").attr('onclick',"deleteFileBefore(this,file)");
            console.log(file.name+"上传完成");
            var progressId ="#"+data.barId;
            
            $(progressId).parent().text(GCRM.util.message('materials.upload.finished'));
        },
        progress: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        var progressId ="#"+data.barId;
	        $(progressId).css(
	            'width',
	            progress + '%'
	        );
	        console.log(progressId);
	    }
    });

});


function deleteFileBefore(thisObj,file){
	var confirmInfo = GCRM.util.message('delete.confirm');
    if (!confirm(confirmInfo)){
    	return;
    }
	$.ajax({
        type: "POST",
        url: "../materials/doDeleteMaterial",
        data:{
        	id:file.id,
        	url:file.url,
        	tempUrl:file.tempUrl
        },
        dateType: "json",
        async: false,
        success: function(json) {
        	if(json.success){
        		$(thisObj).parent().parent().remove();
        	}else{
        		alert(GCRM.util.message(json.errors['message']));
        	}
        }
    });

}