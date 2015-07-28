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
                <div class="col-md-10"><s:message code="resource.site.name"/></div>
                <div class="col-md-2 text-right">
                    <button type="button" id="siteList" class="btn btn-primary"><s:message code="resource.site.list"/></button>
                </div>
            </h3>
            
            
            <div class="panel panel-default">
                <div class="panel-body">
                    <input type="hidden" id="siteId" value="${site.id}">
                    <c:forEach var="siteI18n" items="${siteI18ns}" varStatus="status">
                        <div class="row form-group">
                            <div class="col-xs-1">
                                <c:out value="${siteI18n.locale}"/>
                            </div>
                            <div class="col-xs-2">
		                        <input type="text" id="name${siteI18n.id}" class="form-control" value="${siteI18n.value}">
                            </div>
	                       <div class="col-xs-3">
                                <a refername="name${siteI18n.id}" referlocale="${siteI18n.locale}" referid="${siteI18n.id}"  href="###" class="saveSite btn btn-primary"><s:message code="view.save"/></a>
	                       </div>
	                    </div>
                    </c:forEach>
		            
                </div>
            </div> 
            
        </div>
        
        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.suggestion.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                $(".saveSite").click(function(){
                    var i18nNameId = $(this).attr("refername");
                    var inputName = $("#"+i18nNameId).val();
                    var inputId = $(this).attr("referid");
                    var currlocale = $(this).attr("referlocale");
                    var currSiteId = $("#siteId").val();
                    if($.trim(inputName) == '' || $.trim(inputId) == ''){
                        return false;
                    }else{
                        $.post("<%=basePath%>site/updateI18nName", {"id": currSiteId,"i18nId": inputId,"value": inputName,"locale":currlocale },
                        function(data){
                            if(data.success){
                                alert(GCRM.util.message('view.operate.success'));
                            }else{
                                alert(GCRM.util.message('resource.position.name.duplicate'));
                            }
                            
                        });
                    }
                });
                
                $("#siteList").click(function(){
                    window.location.href = "<%=basePath%>site/query";
                });
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>