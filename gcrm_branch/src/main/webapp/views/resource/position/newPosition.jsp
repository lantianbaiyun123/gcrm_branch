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
	                <form class="form-horizontal" id="positionForm" action="<%=basePath%>position/savePosition" method="post" role="form">
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
                                <select class="form-control" id="channelSelect" name="channelId">
                                    <c:forEach var="channelVO" items="${channelVOList}" varStatus="status">
                                        <option value="${channelVO.id}" 
                                            <c:if test="${!empty position && position.channelId == channelVO.id}">selected="selected"</c:if>
                                        >${channelVO.i18nName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><span class="txt-impt">*</span><s:message code="resource.position.area"/>：</label>
                            <div class="col-md-3">
                                <select class="form-control" id="areaSelect" name="parentId" >
                                    <c:forEach var="areaVO" items="${areaVOList}" varStatus="status">
                                        <option value="${areaVO.id}" 
                                            <c:if test="${!empty position && position.areaId == areaVO.id}">selected="selected"</c:if>
                                        >${areaVO.i18nName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.rotationType"/>：</label>
                            <div class="col-md-3">
                                <c:forEach var="rotationType" items="${rotationTypes}" varStatus="status">
						            <label class="radio radio-inline"> 
						                <input type="radio" value='${rotationType}' name="rotationType" 
						                    <c:if test="${rotationType == position.rotationType}">
						                        checked="checked"
						                    </c:if>
						                /><s:message code="resource.position.rotationType.${rotationType}"/>
						            </label> 
						        </c:forEach>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.areaRequired"/>：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" value="<c:out value="${position.areaRequired}"/>"  name="areaRequired">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.size"/>：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" value="<c:out value="${position.size}"/>"   name="size">
                            </div>
                        </div>
                        
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.salesAmount"/>：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" value="<c:out value="${position.salesAmount}"/>"  name="salesAmount">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.materialType"/>：</label>
                            <div class="col-md-5">
                                <c:forEach var="materialType" items="${materialTypes}" varStatus="status">
                                    <label class="radio radio-inline"> 
                                        <input type="radio" value='${materialType}' name="materialType" 
                                            <c:if test="${materialType == position.materialType}">
                                                checked="checked"
                                            </c:if>
                                        /><s:message code="resource.position.materialType.${materialType}"/>
                                    </label> 
                                </c:forEach>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.click"/>：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" value="<c:out value="${position.click}"/>" name="click">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.pv"/>：</label>
                            <div class="col-md-3">
                                <input type="text" class="form-control" value="<c:out value="${position.pv}"/>"  name="pv">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="control-label col-md-2"><s:message code="resource.position.status"/>：</label>
                            <div class="col-md-5">
                                <c:forEach var="positionStatus" items="${positionStatuss}" varStatus="status">
                                    <label class="radio radio-inline"> 
                                        <input type="radio" value='${positionStatus}' name="status" 
                                            <c:if test="${positionStatus == position.status}">
                                                checked="checked"
                                            </c:if>
                                        /><s:message code="resource.position.status.${positionStatus}"/>
                                    </label> 
                                </c:forEach>
                            </div>
                        </div>
                        
                        
			            <div class="form-group">
				            <div class="col-sm-offset-2 col-sm-10">
					           <button type="button" id="addPosition" class="btn btn-primary"><s:message code="view.save"/></button>
					           <button type="button" id="cancelAddPosition" class="btn"><s:message code="view.cancel"/></button>
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
            
                var refreshPositionSelect = function(queryUrl,selectId){
                    $.post(queryUrl,function(data){
                        $(selectId).empty();
	                    var optionHeml = "";
	                    $.each( data, function(i, n){
	                        optionHeml +="<option value='" + n.id + "'>" + n.i18nName + "</option>";
	                    });
	                    $(selectId).html(optionHeml);
	                    $(selectId).change();
	                    $(selectId).parent().removeClass("has-error");
                    });
                };
                
                $("#adPlatformId").change(function(){
                    var currAdPlatformId = $(this).val();
                    if($.trim(currAdPlatformId) != ''){
                        var baseQueryUrl = "<%=basePath%>site/queryByAdPlatform/" + currAdPlatformId;
                        refreshPositionSelect(baseQueryUrl,"#siteSelect");
                    }else{
                        $("#siteSelect").empty();
                    }
                    
                });
                
                $("#siteSelect").change(function(){
                    var currSiteId = $(this).val();
                    if($.trim(currSiteId) != ''){
                        var baseQueryUrl = "<%=basePath%>position/queryChannelBySiteId/" + currSiteId;
                        refreshPositionSelect(baseQueryUrl,"#channelSelect");
                    }else{
                         $("#channelSelect").empty();
                    }
                    
                });
                
                $("#channelSelect").change(function(){
                    var currchannelId = $(this).val();
                    if($.trim(currchannelId) != ''){
                        var baseQueryUrl = "<%=basePath%>position/queryPositionByParentId/" + currchannelId;
                        refreshPositionSelect(baseQueryUrl,"#areaSelect");
                    }else{
                        $("#areaSelect").empty();
                    }
                    
                });
                
                
                $("#cancelAddPosition").click(function(){
                    window.location.href = "<%=basePath%>position";
                });
                $("#addPosition").click(function(){
                    var currAreaId = $("#areaSelect option:selected").val();
                    var currRotationType = $("input[name='rotationType']:checked").val();
                    if($.trim(currAreaId) == ''){
                        $("#areaSelect").parent().addClass("has-error");
                        return false;
                    }else if($.trim(currRotationType) == ''){
                        $("input[name='rotationType']").parent().parent().addClass("has-error");
                        return false;
                    }else{
                        $("#positionForm").submit();
                    }
                });
                
            });
        </fis:script>
        <fis:scripts/>
    </body>
</fis:html>