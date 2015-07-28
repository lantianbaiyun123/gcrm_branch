<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<fis:html>
    <head>
        <title>GCRM</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%@ include file="../../include/common.jsp"%>
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
        <fis:require name="resources/lib/bootstrap/css/bootstrap-extend.css"></fis:require>
        <fis:require name="resources/css/common.css"></fis:require>
        
        <div class="container">
            <%@ include file="../../widget/nav.jsp"%>
        </div>
        
        <div class="container">   
            <c:if test='${message != null && message.message != "" }'>
                <div class="alert alert-info alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    ${message.message}
                </div>
            </c:if>
                            
            <h3 class="row">
                <div><s:message code="resource.site.list"/></div>
                <div class="text-right">
                    <button type="button" id="createSite" class="btn btn-primary">+ <s:message code="resource.site.add"/></button>
                    <button type="button" id="positionListBtn" class="btn btn-primary"><s:message code="resource.position.list"/></button>
                </div>
            </h3>
            
            
            <div class="panel panel-default">
                <div class="panel-body">
                    <table class="table">
                        <thead>
			                <tr>
			                    <th><s:message code="view.id"/></th>
			                    <th><s:message code="resource.product"/></th>
			                    <th><s:message code="resource.site"/></th>
			                    <th class="col-sm-2"><s:message code="view.operate"/></th>
			                </tr>
			            </thead>
			            <tbody id="dataTable">
	                        <c:forEach var="site" items="${siteList}" varStatus="status">
	                            <tr>
	                                <td>${site.id}</td>
	                                <td>${site.adPlatformName}</td>
	                                <td>${site.i18nName}</td>
	                                <td>
	                                    <a href="<%=basePath%>site/preUpdate/${site.id}" class="btn btn-primary"><s:message code="view.edit"/></a>
	                                    <a href="<%=basePath%>site/delete/${site.id}" class="btn btn-primary"><s:message code="view.delete"/></a>
	                                </td>
	                            </tr>
	                        </c:forEach>
                        </tbody>            
                    </table>
                </div>
            </div> 
            
        </div>
        
        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                $("#createSite").click(function(){
                    window.location.href = "<%=basePath%>site/preSave";
                });
                
                $("#positionListBtn").click(function(){
                    window.location.href = "<%=basePath%>position";
                });
                
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>