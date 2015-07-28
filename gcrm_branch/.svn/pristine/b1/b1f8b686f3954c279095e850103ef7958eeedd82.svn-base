<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.baidu.gcrm.amp.model.Position" %>
<!DOCTYPE html>
<html lang="en">
	<head>
	    <title><s:message code="view.settings"/></title>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	    <%@include file="../../include/common.jsp"%>
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
			                            	<a class="bgcolor" href="<%=basePath%>setting/country/list/ENABLE">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.settings.country"/></small>
		                            		</a>
			                            </li>
			                            <li>
			                            	<a href="<%=basePath%>setting/position/add">
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
							            <p><s:message code="view.settings.position.edit"/></p>
							    </blockquote>
						    </div>
						</div>
						
						<div class="span11">
						
						    <ul class="nav nav-tabs">
							    <li><a href="<%=basePath%>setting/position/add"><s:message code="view.add"/></a></li>
							    <li class="active"><a href="<%=basePath%>setting/position/edit"><s:message code="view.edit"/></a></li>
							</ul>
						    <br>
						    
						    
						     <table class="table table-bordered">
							    <tr>
							    	<td>
							    	<% Long topid = Position.DEFAULT_PID;%>
							    	<c:forEach items="${allPositionMap}" var="positionMap">
						    			<c:if test="${positionMap.key == topid}">
						    				<c:forEach items="${positionMap.value}" var="currTopPosition">
							    				<dl class="dashedborder" id="dl-${currTopPosition.id}">
							    					<dt>
							    						${currTopPosition.i18nName}
								    					<c:if test="${currTopPosition.status == Position.DISABLE}">
								    						<span class="label"><s:message code="view.delete"/></span>
								    					</c:if>
								    					<c:choose>
								    						<c:when test="${ empty allPositionMap[currTopPosition.id]}">
								    							<a href="javascript:void(0);" class="offset1 del_btn" rel="${currTopPosition.id}"><small><s:message code="view.delete"/></small></a>
								            					<a href="javascript:void(0);" class="edit_btn" rel="${currTopPosition.id}"><small><s:message code="view.edit"/></small></a>
								    						</c:when>
								    						<c:otherwise>
								    							<a href="javascript:void(0);" class="icon_btn1" rel="${currTopPosition.id}" data-open="1"><i class="icon-minus"></i></a>
								            					<a href="javascript:void(0);" class="edit_btn" rel="${currTopPosition.id}"><small><s:message code="view.edit"/></small></a>
								    						</c:otherwise>
								    					</c:choose>
							    					</dt>
							    					
							    					<%--second --%>
							    					<c:if test="${! empty allPositionMap[currTopPosition.id]}">
							    						<c:forEach items="${allPositionMap[currTopPosition.id]}" var="secondPosition">
							    							<dd style="margin-left:20px;"> | - - - ${secondPosition.i18nName}
							    							<%--third --%>
							    							<c:choose>
							    								<c:when test="${! empty allPositionMap[secondPosition.id]}">
								    								<a href="javascript:void(0);" class="icon_btn2" rel="${secondPosition.id}" data-line="${allPositionMap[secondPosition.id].size()}" data-open="0"><i class="icon-plus"></i></a>
								    								<a href="javascript:void(0);" class="edit_btn" rel="${secondPosition.id}"><small><s:message code="view.edit"/></small></a>
									    							<div id="div-${secondPosition.id}" class="hide">
														                <c:forEach items="${allPositionMap[secondPosition.id]}" var="thirdPosition">
														                    <c:if test="${! empty allPositionMap[secondPosition.id]}">
														                        <dd style="margin-left:70px"> |  - - - ${thirdPosition.i18nName}
															                        <a href="javascript:void(0);" class="offset1 del_btn" rel="${thirdPosition.id}"><small><s:message code="view.delete"/></small></a>
															                        <a href="javascript:void(0);" class="edit_btn" rel="${thirdPosition.id}"><small><s:message code="view.edit"/></small></a>
														                        </dd>
													                        </c:if>
													                    </c:forEach>
													                </div>
											                	</c:when>
											                	<c:when test="${secondPosition.status == Position.DISABLE }">
											                		<span class="label"><s:message code="view.delete"/></span>
											                	</c:when>
											                	<c:otherwise>
											                		<a href="javascript:void(0);" class="offset1 del_btn" rel="${secondPosition.id}"><small><s:message code="view.delete"/></small></a>
									                				<a href="javascript:void(0);" class="edit_btn" rel="${secondPosition.id}"><small><s:message code="view.edit"/></small></a></dd>
											                	</c:otherwise>
											                </c:choose>
											                
											                </dd>
							    							
							    						</c:forEach>
							    					</c:if>
								    		 	</dl>
							    		 	</c:forEach>
						    			</c:if>
									</c:forEach>
									
							    </td>
						    </tr>
					    </table>
						
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){});
		    
		
		</script>
		
		
	</body>
</html>