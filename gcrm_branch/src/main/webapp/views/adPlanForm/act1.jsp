<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
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
            <form class="form-horizontal" method="POST" action="<%=basePath%>adPlan/createAdPlan">
        
			<div class="form-group">
			    <label class="control-label col-md-2">报价低于八折：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isLowEightDiscount" 
		    			<c:if test="${isLowEightDiscount == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isLowEightDiscount" 
		    			<c:if test="${isLowEightDiscount == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>  
			    </div>
			</div>
			</br>
			
			<div class="form-group">
			    <label class="control-label col-md-2">报价低于五折：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isLowFiveDiscount" 
		    			<c:if test="${isLowFiveDiscount == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isLowFiveDiscount" 
		    			<c:if test="${isLowFiveDiscount == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>
			    </div>
			</div>
			</br>
			
			<div class="form-group">
			    <label class="control-label col-md-2">是否有插单：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isBreakOrder" 
		    			<c:if test="${isBreakOrder == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isBreakOrder" 
		    			<c:if test="${isBreakOrder == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>
			    </div>
			</div>
			</br>
			
			<div class="form-group">
			    <label class="control-label col-md-2">未备案刊例价：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isNoPublishPrice" 
		    			<c:if test="${isNoPublishPrice == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isNoPublishPrice" 
		    			<c:if test="${isNoPublishPrice == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>
			    </div>
			</div>
			</br>
			
			<div class="form-group">
			    <label class="control-label col-md-2">没有分成比例：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isNoSplitPrice" 
		    			<c:if test="${isNoSplitPrice == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isNoSplitPrice" 
		    			<c:if test="${isNoSplitPrice == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>
			    </div>
			</div>
			</br>
			
			<div class="form-group">
			    <label class="control-label col-md-2">实际分成比例低于我方分成比例：</label>
			    <div class="col-md-3">
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="1" name="isRealLowMySplitPrice" 
		    			<c:if test="${isRealLowMySplitPrice == '1'}">
		    				checked="checked"
		    			</c:if>
		    			/>是
			        </label> 
		    	
		    		<label class="radio radio-inline"> 
		    			<input type="radio" value="0" name="isRealLowMySplitPrice" 
		    			<c:if test="${isRealLowMySplitPrice == '0'}">
		    				checked="checked"
		    			</c:if>
		    			/>否
			        </label>
			    </div>
			</div>
		</form>   
     </div>
   </body>
</fis:html>