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
                <div><s:message code="resource.position.list"/></div>
                <div class="text-right">
                    <button type="button" id="createSite" class="btn btn-primary">+ <s:message code="resource.site.add"/></button>
                    <button type="button" id="createChannel" class="btn btn-primary">+ <s:message code="resource.position.channel.add"/></button>
                    <button type="button" id="createArea" class="btn btn-primary">+ <s:message code="resource.position.area.add"/></button>
                    <button type="button" id="createPosition" class="btn btn-primary">+ <s:message code="resource.position.add"/></button>
                </div>
            </h3>
            
            
            <div class="panel panel-default">
                <div class="panel-body" id="positionListPanel">
                    <%@ include file="./listPosition.jsp"%>
                </div>
                
            </div> 
            
        </div>
        
        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/jqueryValidation/jquery.validate.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:require name="resources/js/common.js"></fis:require>
        <fis:require name="resources/js/list.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                
                $('select').addClass("col-md-12").selectpicker();
                var bindDynamicList = function(){
                    $("select").addClass("col-md-12").selectpicker();
                    $(".editPosition").click(function(){
                        var currPositionId = $(this).attr("refid");
                        window.location.href = "<%=basePath%>position/preUpdate/" + currPositionId;
                    });
                    
                    $(".deletePosition").click(function(){
                        var currPositionId = $(this).attr("refid");
                        $.ajax({
	                         type: "POST",
	                         url: GCRM.constants.CONTEXT + "position/delete/" + currPositionId,
	                         dataType: "json",
	                         success: function(msg){
	                            if(msg.success){ 
	                                $.gcrmList.query($("#positionListPanel"),1,bindDynamicList);
	                            } else {
	                                alert(GCRM.util.message('resource.position.del.notAllow'));
	                            }
	                        }
	                    });
                    });
                    
                };
                bindDynamicList();
                $.gcrmList.querySuccess($("#positionListPanel"),bindDynamicList);
                $("#createSite").click(function(){
                    window.location.href = "<%=basePath%>site/preSave";
                });
                
                $("#createChannel").click(function(){
                    window.location.href = "<%=basePath%>position/preSaveChannel";
                });
                
                $("#createArea").click(function(){
                    window.location.href = "<%=basePath%>position/preSaveArea";
                });
                
                $("#createPosition").click(function(){
                    window.location.href = "<%=basePath%>position/preSavePosition";
                });
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>