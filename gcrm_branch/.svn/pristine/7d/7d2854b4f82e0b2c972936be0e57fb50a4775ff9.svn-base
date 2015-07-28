<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
	<head>
	    <title><s:message code="view.settings"/></title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <%@include file="../../include/common.jsp"%>
		<script type="text/javascript" src="<%=basePath%>resources/js/bootstrap.min.js"></script>
	</head>
	<body>
		<%@include file="../../include/header.jsp"%>
		
		<div class="container-fluid">
			<div class="row-fluid">
			
				<div class="span2">
					<div class="according" id="according1">
						<div class="accordion-group">
							<div class="accordion-heading">
								<li class="accordion-toggle" data-toggle="collapse" data-parent="#according1">
									<s:message code="view.settings"/>
								</li>
							</div>
							<div id="collapse1" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul class="nav nav-list">
			                            <li>
			                            	<a class="bgcolor" href="<s:url value="/setting/country/list/ENABLE" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.settings.country"/></small>
		                            		</a>
			                            </li>
			                            <li>
			                            	<a href="<s:url value="/setting/position/add" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.settings.position"/></small>
		                            		</a>
			                            </li>
			                         </ul>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				
				<div class="span10">
					<div class="row-fluid">
						<div class="span11">
						    <div class="span4">
							    <blockquote>
							            <p><s:message code="view.settings.country.edit"/></p>
							    </blockquote>
						    </div>
						</div>
						
						<div class="span11">
						
						    <ul class="nav nav-tabs">
							    <li <c:if test="${status == 'ENABLE'}">class="active"</c:if>><a href="<s:url value="/setting/country/list/ENABLE" />"><s:message code="view.open"/></a></li>
							    <li <c:if test="${status == 'DISABLE'}">class="active"</c:if>><a href="<s:url value="/setting/country/list/DISABLE" />"><s:message code="view.close"/></a></li>
							</ul>
						    
						    
						    <table class="table table-hover table-bordered table-condensed">
						        <thead>
						            <th><a href="javascript:void(0);" rel="up"><s:message code="view.settings.country.domain"/></a></th>
						            <th><a href="javascript:void(0);" rel="up"><s:message code="view.settings.country.nation"/></a></th>
						           <%--  <th><a href="javascript:void(0);" rel="up"><s:message code="view.settings.country.nationEnName"/></a></th>--%>
						            <th><a href="javascript:void(0);" rel="up"><s:message code="view.settings.country.phoneCode"/></a></th>
						            <th><a href="javascript:void(0);" rel="up"><s:message code="view.settings.country.timeZone"/></a></th>
						            <th><s:message code="view.settings.country.remark"/></th>
						            <th><s:message code="view.operate"/></th>
						        </thead>
						        <tbody>
						        
						        	<c:forEach items="${countryList}" var="country">
						        		<tr>
						                    <td>${country.id}</td>
						                    <td>${country.i18nName}</td>
						                    <td>${country.phoneCode}</td>
						                    <td>${country.timeZone}</td>
						                    <td>${country.remark}</td>
						                    <td>
							                    <c:if test="${status == 'ENABLE'}"><a href="javascript:void(0);" rel="${country.id}" class="close_btn"><s:message code="view.close"/></a></c:if>
							                    <c:if test="${status == 'DISABLE'}"><a href="javascript:void(0);" rel="${country.id}" class="open_btn"><s:message code="view.open"/></a></c:if>
    										</td>
						                </tr>
						        		
						        	</c:forEach>
						        </tbody>
						
						    </table>
						
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		
		<!-- Modal -->
		<div id="remark_modal_div" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		    <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		        <h4 id="myModalLabel"></h4>
		    </div>
		    <div class="modal-body">
		        <div class="control-group">
		            <label class="control-label" for="inputRemark"><s:message code="view.settings.country.remark"/>：</label>
		            <div class="controls"><textarea rows="2" class="span12" name="remark"></textarea></div>
		        </div>
		    </div>
		    <input type="hidden" id="input_cid" value="">
		    <input type="hidden" id="input_status" value="">
		    <div class="modal-footer">
		        <button class="btn" data-dismiss="modal" aria-hidden="true"><s:message code="view.cancle"/></button>
		        <button id="submit_btn"  class="btn btn-primary"><s:message code="view.save"/></button>
		    </div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$(".open_btn, .close_btn").click(function(){
				    if ($(this).attr('class') == 'open_btn') {
				        $("#input_status").val("ENABLE");
				    } else {
				        $("#input_status").val("DISABLE");
				    }

				    $("#input_cid").val($(this).attr("rel"));
				    $("#remark_modal_div").modal('show');
				});

				$("#submit_btn").click(function(){
				    var remark = $("#remark_modal_div textarea").val();
				    var cid    = $("#input_cid").val();
				    var status = $("#input_status").val();
				    $.post("<%=basePath%>setting/country/update", {id: cid, remark: remark, status: status}, function(data){
				    	 if(msg.type == "SUCCESS"){
				    		 setTimeout("window.location.href='<%=basePath%>/setting/country/list/" + status + "'", 500);
				    	 }
				    });
				});
				
			});
		
		</script>
	</body>
</html>