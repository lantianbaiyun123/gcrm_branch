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
                <div class="col-md-10"><s:message code="resource.position.name"/></div>
                <div class="col-md-2 text-right">
                    <button type="button" id="positionList" class="btn btn-primary"><s:message code="resource.position.list"/></button>
                </div>
            </h3>
            
            
            <div class="panel panel-default">
                <div class="panel-body">
                    <input type="hidden" id="positionId" value="${position.id}">
                    <c:forEach var="position" items="${positionI18n}" varStatus="status">
                        <div class="row form-group">
                            <div class="col-xs-1">
                                <c:out value="${position.locale}"/>
                            </div>
                            <div class="col-xs-2">
                                <input type="text" id="name${position.id}" class="form-control" value="${position.value}">
                            </div>
                           <div class="col-xs-3">
                                <a refername="name${position.id}" reflocale="${position.locale}" referid="${position.id}"  href="###" class="saveChannel btn btn-primary"><s:message code="view.save"/></a>
                           </div>
                        </div>
                    </c:forEach>
                    
                </div>
            </div> 
            
        </div>
        
        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                $(".saveChannel").click(function(){
                    var i18nNameId = $(this).attr("refername");
                    var inputName = $("#"+i18nNameId).val();
                    var inputId = $(this).attr("referid");
                    var locale = $(this).attr("reflocale");
                    var positionId = $("#positionId").val();
                    if($.trim(inputName) == '' || $.trim(inputId) == ''){
                        return false;
                    }else{
                        $.post("<%=basePath%>position/updatePositionI18nName", {"id": positionId,"i18nId": inputId,"value": inputName,"locale":locale },function(data){
                            if(data.success){
                                alert(GCRM.util.message('view.operate.success'));
                            }else{
                                alert(GCRM.util.message('resource.position.name.duplicate'));
                            }
                            
                        });
                    }
                });
                
                $("#positionList").click(function(){
                    window.location.href = "<%=basePath%>position";
                });
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>