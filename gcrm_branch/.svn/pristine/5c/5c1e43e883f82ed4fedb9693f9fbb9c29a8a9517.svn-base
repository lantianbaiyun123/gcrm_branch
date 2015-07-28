<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.baidu.gcrm.amp.common.OfferTypeConstants" %>
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
									<s:message code="view.msg"/>
								</li>
							</div>
							<div id="collapse1" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul class="nav nav-list">
			                            <li>
			                            	<a class="bgcolor" href="<s:url value="/subscribe/list" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.msg.subscribe.list"/></small>
		                            		</a>
			                            </li>
			                            <li>
			                            	<a href="<s:url value="/subscribe/add" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.msg.subscribe.add"/></small>
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
							            <p><s:message code="view.msg.subscribe.list"/></p>
							    </blockquote>
						    </div>
						</div>
						
						<div class="span11">
							<ul class="nav nav-tabs">
								<c:forEach items="${countryList}" var="country" varStatus="countryIdx">
									<li <c:choose>
											<c:when test="${!empty subscribe.country && subscribe.country == country.id}"> class="active" </c:when>
											<c:when test="${empty subscribe.country && countryIdx.index == 0}"> class="active" </c:when>
										</c:choose>>
								       <a href="<s:url value="/subscribe/list/" />?country=${country.id}">${country.i18nName}</a>
								    </li>
					        	</c:forEach>
							</ul>
						</div>
						
						
						<div class="span11">
						    <table class="table table-hover table-bordered table-condensed">
							    <thead>
							        <tr>
							            <th width="50px">ID</th>
							            <th width="90px"><s:message code="view.email"/></th>
							            <th width="5%"><s:message code="view.settings.country"/></th>
							            <th width="10%"><s:message code="view.type"/></th>
							            <th><s:message code="view.remark"/></th>
							            <th><s:message code="view.createTime"/></th>
							            <th><s:message code="view.operate"/></th>
							        </tr>
							    </thead>
							    <tbody>
							    	<c:forEach items="${page.content}" var="subscribe">
						        		<tr id="row_${subscribe.id}">
						                    <td>${subscribe.id}</td>
						                    <td>${subscribe.email}</td>
						                    <td>${subscribe.country}</td>
						                    <td>${subscribe.type}</td>
						                    <td>${subscribe.remark}</td>
						                    <td>${subscribe.createDate}</td>
						                    <td>
						                    	<a href="javascript:void(0);" data-id="${subscribe.id}" data-name="${subscribe.email}" class="unsubscribe">退订</a>
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
		
		<script type="text/javascript">
			$(document).ready(function(){
				
				$(".unsubscribe").click(function(){
				    var id = $(this).attr('data-id');
				    if (confirm("<s:message code='operate.confirm'/>")) {
				        $.post("/email/subscribe", {id: id, status: 0}, function(data){
				            if (data == 1) {
				                $('#row_' + id).remove();
				                $("#alert_msg").html(GCRM.util.message('view.operate') + " "+ GCRM.util.message('view.success'));
				            } else {
				                $("#alert_msg").html(GCRM.util.message('view.operate') + " "+ GCRM.util.message('view.fail'));
				            }
				       });
				    }
				});
				
			});
		
		</script>
		
		
	</body>
</html>