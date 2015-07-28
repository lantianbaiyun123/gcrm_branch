<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html>
<fis:html>
    <head>
    <title>GCRM</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%@ include file="../include/common.jsp"%>
    <fis:styles/>
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>

    <![endif]-->
    </head>
    <body> 
        <fis:require name="resources/lib/bootstrap/css/bootstrap.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-select.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-datetimepicker.min.css"></fis:require>
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload.css"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/css/jquery.fileupload-ui.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>

        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.iframe-transport.js"></fis:require>
        
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/additional-methods.js"></fis:require>
        
        <fis:require name="resources/lib/js/jquery.form.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
        <fis:require name="resources/lib/jqueryUpload/js/jquery.fileupload.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>

        
        <div class="container">
            <%@ include file="../widget/nav.jsp"%>
        </div>
        
        
        
        <div class="container panel panel-gcrm">
           	<div class="page-header">
            	<h3>更新报价信息 </h3>
            </div>
            
            <div class="panel-body" id="customerListContainer">
				<form id='quoteUpdateForm' class='form-horizontal' method='POST' action='<c:url value="/quote/update"/>'>
					<input type="hidden" name="id" value="${quote.id}">
					<c:if test='${message != null && message != "" }'>
						<div class="alert alert-info alert-dismissable">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
						  	<strong><s:message code="${message}"/></strong> 
						</div>
					</c:if>
					<div class="form-group">
						<label class="col-md-2 control-label" for="advertisingPlatform">产品：</label>
						<div class="col-md-3">
							<%-- <select class="form-control col-md-3" name="advertisingPlatformId">
								<c:forEach items="${advertisingPlatforms}" var="advertisingPlatform">
									<option value="${advertisingPlatform.id}" 
										<c:if test="${advertisingPlatform.id==quote.advertisingPlatformId}"
									>selected</c:if>>
									${advertisingPlatform.name}</option>
								</c:forEach>
		                  	</select> --%>
		                  	<input type="hidden" name="advertisingPlatformId" value="${quote.advertisingPlatformId}">
		                  	<div class="col-md-4 setPad3"><p class="form-control-static">${quote.advertisingPlatformName}</p></div>
	                  	</div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-md-2" for="siteId">投放站点：</label>
						<div class="col-md-3">
							<input type="hidden" name="siteId" value="${quote.siteId}">
		                  	<div class="col-md-4 setPad3"><p class="form-control-static">${quote.siteName}</p></div>
		                  </div>
					</div>
					
					<div class="form-group">
						<label class="control-label col-md-2" for="billingModelId">计费方式：</label>
						<div class="col-md-3">
							<%-- <select class="form-control col-md-3" name="billingModelId" onchange="billingModelIdChange(this)">
		                  		<c:forEach items="${billingModels}" var="billingModel">
									<option value="${billingModel.id}" type="${billingModel.type}"
										<c:if test="${billingModel.id==quote.billingModelId}">selected</c:if>
									>
										${billingModel.name}
									</option>
								</c:forEach>
		                  	</select> --%>
		                  	<input type="hidden" name="billingModelId" value="${quote.billingModelId}">
		                  	<input type="hidden" name="billingModelType" value="${quote.billingModelType}">
		                  	<div class="col-md-4 setPad3"><p class="form-control-static">${quote.billingModelName}</p></div>
		                </div>
					</div>
					
					<%-- <div class="form-group">
						<label class="control-label col-md-2" for="product">报价名称：</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="name" placeholder="报价名称" value="${quote.name}">
		                </div>
					</div> --%>
					
					<div class="form-group" hidden="hidden">
						<label class="control-label col-md-2" for="product">刊例价：</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="publishPrice" placeholder="刊例价" value="${quote.publishPrice}">
		                </div>
		                <div class="col-md-3 help-block"></div>
					</div>
					
					<div class="form-group" hidden="hidden">
						<label class="control-label col-md-2" for="product">我方报价：</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="ratioMine" placeholder="我方报价" value="${quote.ratioMine}" onchange="calRatioThird()">
		                </div>
		                <div class="col-md-1 ">%</div>
		                <div class="col-md-3 help-block"></div>
					</div>
					
					<div class="form-group" hidden="hidden">
						<label class="control-label col-md-2" for="product">客户报价：</label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="ratioThird" placeholder="客户报价" value="${quote.ratioThird}" onchange="calRatioMine()">
		                </div>
		                <div class="col-md-1 ">%</div>
		                <div class="col-md-3 help-block"></div>
					</div>
					
					<div class="form-group">
					   <label class=" control-label col-md-2" for="startTime">开始时间</label>
					   <div class="col-md-3">
					       <input type="text" class="form-control date-time" name="startTime" value='<fmt:formatDate value="${quote.startTime}" pattern="yyyy-MM-dd"/>'>
					   </div>
					</div>
					
					<div class="form-group">
                       <label class=" control-label col-md-2" for="endTime">结束时间</label>
                       <div class="col-md-3">
                           <input type="text" class="form-control date-time" name="endTime" value='<fmt:formatDate value="${quote.endTime}" pattern="yyyy-MM-dd"/>'>
                       </div>
                    </div>
					
					<div class="form-group" >
					    <div class="col-sm-offset-2 col-sm-10">
					    	<button type="submit" class="btn btn-default">保存</button>
					    	<a href="<c:url value='/quote/preQuery'/>"  class="btn btn-default">返回列表</a>
					    </div>
					</div>
				</form>
       	 	</div>
       	 </div>
    </body>
    <fis:require name="resources/js/quote/utils.js"></fis:require>
    <fis:script>
		$('.date-time').datetimepicker({
		    language:"zh_CN",
		    weekStart: 1,
		    todayBtn:  1,
		    autoclose: 1,
		    todayHighlight: 1,
		    startView: 2,
		    minView: 2,
		    forceParse: 0,
		    format: 'yyyy-mm-dd',
		    pickerPosition:"bottom-right"
		});
		var quoteUpdateFormValidate = {
	    		rules:{
	    			publishPrice:{
	    				required:true,  
				    	number:true,
				    	min:0,
				    },
				    ratioMine:{
				    	required:true,  
				    	number:true,
				    	range:[0,100],
				    },
				    ratioThird:{
				    	required:true, 
				    	number:true,
				    	range:[0,100],
				    }
	    		},
	    		messages:{
	    			advertisingPlatformId:{
	    				min:"请选择产品",
	    			},
	    			site:{
	    				min:"请选择站点"
	    			},
	    			billingModelId:{
	    				min:"请选择计费模式",
	    			},
	    			publishPrice:{
	    				required:"刊例价不能为空",
				    	number:"请输入数字",
				    	min:"输入数字必须大于0",
				    },
				    ratioMine:{
				    	required:"我方报价不能为空",  
				    	range:"请输入[0,100]之间的数，小数点后保留两位",
				    	number:"请输入[0,100]之间的数，小数点后保留两位",
				    },
				    ratioThird:{
				    	required:"客户报价不能为空", 
				    	range:"请输入[0,100]之间的数，小数点后保留两位",
				    	number:"请输入[0,100]之间的数，小数点后保留两位",
				    }
	    		},
	    		errorPlacement: function(error,element) {
			   		element.closest(".form-group").find('.help-block').html(error);
			   	}
	    	};//end quoteNewFormValidate
	    	
    	
    	
    	$(document).ready(function(){
    		billingModelIdChange($("input[name='billingModelType']").val());
    		$("#quoteUpdateForm").validate(quoteUpdateFormValidate);
    	});
    </fis:script>
       
    <fis:scripts/>
</fis:html>