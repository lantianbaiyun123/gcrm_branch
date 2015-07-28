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
                <div class="col-md-10"><s:message code="resource.position.add"/></div>
            </h3>
            
            
            <div class="panel panel-default">
                <div class="panel-body">
	                <form class="form-horizontal" id="areaForm" action="<%=basePath%>position/saveArea" method="post" enctype="multipart/form-data" role="form">
	                    <c:if test="${!empty position}">
                           <input type="hidden" name="id" value="${position.id}">
                        </c:if>
	                    <div class="form-group">
			                <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="resource.product"/>：</label>
			                <div class="col-md-3">
			                    <select class="form-control" id="adPlatformId" name="adPlatformId" >
	                                <c:forEach var="adPlatform" items="${adPlatformList}" varStatus="status">
	                                    <option value="${adPlatform.id}" 
	                                       <c:if test="${!empty position && position.adPlatformId == adPlatform.id}">selected="selected"</c:if>
	                                    >${adPlatform.i18nName}</option>
	                                </c:forEach>
	                            </select>
	                            
			                </div>
			            </div>
			            
			            <div class="form-group">
                            <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="resource.site"/>：</label>
                            <div class="col-md-3">
                                <select class="form-control" id="siteSelect" name="siteId" >
                                    <c:forEach var="site" items="${siteList}" varStatus="status">
                                        <option value="${site.id}" 
                                            <c:if test="${!empty position && position.siteId == site.id}">selected="selected"</c:if>
                                        >${site.i18nName}</option>
                                    </c:forEach>
                                </select>
                                
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="resource.position.channel"/>：</label>
                            <div class="col-md-3">
                                <select class="form-control" id="channelSelect" name="parentId" >
                                    <c:forEach var="channelVO" items="${channelVOList}" varStatus="status">
                                        <option value="${channelVO.id}" 
                                            <c:if test="${!empty position && position.parentId == channelVO.id}">selected="selected"</c:if>
                                        >${channelVO.i18nName}</option>
                                    </c:forEach>
                                </select>
                                
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="resource.position.area.guide"/>：</label>
                            <div class="col-md-3">
                                <input type="file" name="guideFile">
                                <c:out value="${position.guideFileDownloadName }"/>
                            </div>
                        </div>
			            
			            <div class="form-group">
				            <div class="col-sm-offset-2 col-sm-10">
					           <button type="button" id="addArea" class="btn btn-primary"><s:message code="view.save"/></button>
					           <button type="button" id="cancelAddSite" class="btn"><s:message code="view.cancel"/></button>
						    </div>
				        </div>
  
		            </form>
                </div>
                
            </div> 
            
        </div>
        
        <fis:require name="resources/lib/js/jquery.js"></fis:require>
        <fis:require name="resources/lib/js/jquery.ui.widget.min.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap.js"></fis:require>
        <fis:require name="resources/lib/bootstrap/js/bootstrap-select.js"></fis:require>
        <fis:script>
            $(document).ready(function(){
                var refreshSelect = function(queryUrl,selectId){
                    $.post(queryUrl,function(data,textStatus){
                        $(selectId).empty();
                        var optionHeml = "";
                        $.each( data, function(i, n){
                            optionHeml +="<option value='" + n.id + "'>" + n.i18nName + "</option>";
                        });
                        $(selectId).html(optionHeml);
                        $(selectId).parent().removeClass("has-error");
                        $(selectId).change();
                    });
                };
                
                
                $("#adPlatformId").change(function(){
                    var currAdPlatformId = $(this).val();
                    var baseQueryUrl = "<%=basePath%>site/queryByAdPlatform/" + currAdPlatformId;
                    refreshSelect(baseQueryUrl,"#siteSelect")
                });
                
                $("#siteSelect").change(function(){
                    var currSiteId = $(this).val();
                    var baseQueryUrl = "<%=basePath%>position/queryChannelBySiteId/" + currSiteId;
                    refreshSelect(baseQueryUrl,"#channelSelect")
                });
                
                $("#addArea").click(function(){
                    var currSiteId = $("#channelSelect option:selected").val();
                    if($.trim(currSiteId) == ''){
                        $("#channelSelect").parent().addClass("has-error");
                        return false;
                    }else{
                        $("#areaForm").submit();
                    }
                });
                
                $("#cancelAddSite").click(function(){
                    window.location.href = "<%=basePath%>position";
                });
                
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>