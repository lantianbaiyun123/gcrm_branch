<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<fis:html>
    <head>
    <title>代办列表</title>
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
            <c:forEach items="${activities}" var="activity">
            <div class="form-group">
            	<b>[<fmt:formatDate value="${activity.activityArrivedTime}" pattern="yyyy-MM-dd HH:mm" />]&nbsp;${activity.startUser}提交&nbsp;${activity.processName}&nbsp;${activity.activityName}</b>
			    <a href="<%=basePath%>adPlan/requestForm?activityId=${activity.activityId}&processId=${activity.processId}&actDefId=${activity.actDefId}">
			    	审批
			    </a>
			    <a href="<%=basePath%>adPlan/getApprovalRecords?processId=${activity.processId}">查看审批记录</a>
			</div>
            </c:forEach>
            
    	 </div>
   </body>
</fis:html>