/**
 * 加载站点信息
 */
function loadSites(obj){
	$.ajax({
		type:"GET",
		url:"/gcrm/quote/querySites/"+$(obj).val(),
		dataType:"json",
		//async: false,
		success: function(json) {
			var data = json.retBean;    	
        	$("select[name='siteId'] option").remove();
        	if($("select[name='advertisingPlatformId']").val()>0){
        		$("select[name='siteId']").attr("disabled",false);
        		$("select[name='siteId']").append($("<option value=-1>==请选择==</option>"));
	        	for(var i=0; i< data.length;i++){
	        		$("select[name='siteId']").append($("<option value="+data[i].id+">"+data[i].i18nName+"</option>"));
	        	}
        	}else{
        		$("select[name='siteId']").append($("<option value=-1>==请选择==</option>"));
        		$("select[name='siteId']").attr("disabled",true);
        	}
        },
        error:function(){
        	alert("错误");
        }
	});
}

function calRatioThird(){
	var valueMine = $("input[name='ratioMine']").val();
	valueMine = Number(valueMine).toFixed(2);
	valueThird = Number(100-valueMine).toFixed(2);
	$("input[name='ratioMine']").val(valueMine);
	$("input[name='ratioThird']").val(valueThird);
}

function calRatioMine(){
	var valueThird = $("input[name='ratioThird']").val();
	valueThird = Number(valueThird).toFixed(2);
	valueMine = Number(100-valueThird).toFixed(2);
	$("input[name='ratioMine']").val(valueMine);
	$("input[name='ratioThird']").val(valueThird);
}

/*根据类型切换界面显示*/
function billingModelIdChange(type){
	if(type==0){
		$("input[name='publishPrice']").closest(".form-group").show();
		$("input[name='ratioMine']").closest(".form-group").hide();
		$("input[name='ratioMine']").val("");
		$("input[name='ratioThird']").closest(".form-group").hide();
		$("input[name='ratioThird']").val("");
	}else if(type==1){//分成
		$("input[name='publishPrice']").closest(".form-group").hide();
		$("input[name='publishPrice']").val("");
		$("input[name='ratioMine']").closest(".form-group").show();
		$("input[name='ratioThird']").closest(".form-group").show();
	}
}
