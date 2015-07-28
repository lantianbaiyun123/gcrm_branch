$.gcrmList = $.gcrmList || {};
$.gcrmList.querySuccess = function(listObj,customerSuccessCallback){
	$(".pagination li").click(function(){
        var self = $(this);
        var licss = self.attr("class");
        var thisPageNum = self.attr("pagenum");
        if("disabled" == licss || "active" == licss){
            return;
        }
        $.gcrmList.query(listObj,thisPageNum,customerSuccessCallback);
        
    });
    
    $("#gcrmPageSize").change(function(){
        $.gcrmList.query(listObj,1,customerSuccessCallback);
    });
	
};
$.gcrmList.query = function(listObj,pageNum,customerSuccessCallback){
    var pageSizeVal = $("#gcrmPageSize").val();
    var formId = $("#gcrmPageSize").attr("refFormId");
    var formAction = $(formId).attr("action");
    var addParam = "";
    if(formAction.indexOf("\?") == -1){
        addParam += "?";
    }else{
        addParam += "&";
    }
    addParam += "pageNumber="+pageNum + "&pageSize=" + pageSizeVal + "&t=" + new Date().getTime();
    var url = formAction + addParam;
    var postData = $(formId).serialize();
    $(listObj).load(url, postData, function(){
    	if(customerSuccessCallback){
    		customerSuccessCallback();
    	}
    	
    	if($.gcrmList.querySuccess){
    		$.gcrmList.querySuccess(listObj,customerSuccessCallback);
    	}
    });
};


           
           

            