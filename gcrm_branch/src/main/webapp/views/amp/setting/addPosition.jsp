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
							            <p><s:message code="view.settings.position.add"/></p>
							    </blockquote>
						    </div>
						</div>
						
						<div class="span11">
						
						    <ul class="nav nav-tabs">
							    <li class="active"><a href="<s:url value="/setting/position/add" />"><s:message code="view.add"/></a></li>
							    <li><a href="<s:url value="/setting/position/edit" />"><s:message code="view.edit"/></a></li>
							</ul>
						    <br>
						    <ul class="breadcrumb">
						          <li><s:message code="view.settings.position.firstCloumn"/><s:message code="view.config"/></li>
						    </ul>
						    <form class="form-inline" id="first_form" action="" method="post">
						    	<div id="sourceInput">
						    		<div class="control-group firstColumn">
								    	<label class="control-label"><s:message code="view.lang"/>：</label>
								        <select class="input-medium" ref="locale" name="locale">
								        	<c:forEach items="${lan}" var="lang" varStatus="status">
								        		<option value="${lang}">${lang}</option>
											</c:forEach>
								        </select>
								    	<label class="control-label operatebtn"><s:message code="view.name"/>：
									        <input type="text" ref="position" name="positionName" class="input-medium">&nbsp;
									        <button type="button" class="close">&times;</button>
								        </label>
							        </div>
						        </div>
						        <div id="targetInput"></div>
						        <br>
						        <button type="button" class="btn addlang" cpfrom="sourceInput" cpto="targetInput">
						        	<s:message code="view.add"/>
					        	</button>
						        <button type="button" class="btn savebtn" refform="first_form" reflang="firstColumn" cpfrom="sourceInput" cpto="targetInput">
						        	<s:message code="view.save"/>
					        	</button>
						       
						    </form>
						    
						    <ul class="breadcrumb">
						          <li><s:message code="view.settings.position.secondCloumn"/><s:message code="view.config"/></li>
						    </ul>
						    <form class="form-inline" id="sec_form" action="" method="post">
						        <label class="control-label"><s:message code="view.settings.position.secondCloumn"/>：</label>
						        <select class="input-medium" name="pid" >
						            <c:forEach items="${topPosition}" var="position" varStatus="status">
						        		<option value="${position.id}">${position.i18nName}</option>
									</c:forEach>
						        </select>
						        <br>
						        <br>
						        <div id="sourceInput1">
							        <div class="control-group secondColumn">
								        <label class="control-label"><s:message code="view.lang"/>：</label>
								        <select class="input-medium" ref="locale" name="locale">
								        	<c:forEach items="${lan}" var="lang" varStatus="status">
								        		<option value="${lang}">${lang}</option>
											</c:forEach>
								        </select>
								        <label class="control-label operatebtn"><s:message code="view.name"/>：
									        <input type="text"  ref="position" name="positionName" class="input-medium">&nbsp;
									        <button type="button" class="close">&times;</button>
								        </label>
									</div>
								</div>
								<div id="targetInput1"></div>
							 	<br/>
							 	<button type="button" class="btn addlang" cpfrom="sourceInput1" cpto="targetInput1">
								 	<s:message code="view.add"/>
							 	</button>
						        <button type="button" class="btn savebtn" refform="sec_form" reflang="secondColumn" cpfrom="sourceInput1" cpto="targetInput1">
						        	<s:message code="view.save"/>
					        	</button>
						       
						    </form>
						
						    <ul class="breadcrumb">
						          <li><s:message code="view.settings.position.thirdCloumn"/><s:message code="view.config"/></li>
						    </ul>
						    <form class="form-inline" id="third_form" action="" method="post">
						        <label class="control-label"><s:message code="view.settings.position.firstCloumn"/>：</label>
						        <select class="input-medium" name="fir_pid" id="firSelect">
						           <c:forEach items="${topPosition}" var="position" varStatus="status">
						        		<option value="${position.id}">${position.i18nName}</option>
									</c:forEach>
						        </select>
						
						        <label class="control-label"><s:message code="view.settings.position.secondCloumn"/>：</label>
						        <select class="input-medium" name="pid" id="secSelect">
						        </select>
						        <br>
						        <br>
						        <div id="sourceInput2">
							        <div class="control-group thirdColumn">
								        <label class="control-label"><s:message code="view.lang"/>：</label>
								        <select class="input-medium" ref="locale" name="locale">
								        	<c:forEach items="${lan}" var="lang" varStatus="status">
								        		<option value="${lang}">${lang}</option>
											</c:forEach>
								        </select>
								        <label class="control-label operatebtn" for="positionName"><s:message code="view.name"/>：
									        <input type="text"  ref="position" name="positionName" class="input-medium">&nbsp;
									        <button type="button" class="close">&times;</button>
								        </label>
									</div>
								</div>
								<div id="targetInput2"></div>
								<br>
							 	<button type="button" class="btn addlang" cpfrom="sourceInput2" cpto="targetInput2">
								 	<s:message code="view.add"/>
							 	</button>
								<button type="button" class="btn savebtn" refform="third_form" reflang="thirdColumn" cpfrom="sourceInput2" cpto="targetInput2">
						        	<s:message code="view.save"/>
					        	</button>
						    </form>
						    
						
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				$(".addlang").click(function(){
					var fromElementId = $(this).attr("cpfrom");
					var toElementId = $(this).attr("cpto");
					$("#"+toElementId).append($("#"+fromElementId).html());
				});
				
				$(".close").live("click", function(){
					$(this).parent().parent().empty();
				});
				
				$(".savebtn").click(function(){
					
					var langClass = $(this).attr("reflang")
					var formData = processFromData(langClass);
					var formId = $(this).attr("refform");
					var pidStr = "#"+formId + " > select[name='pid']";
					formData["pid"] = $(pidStr).val();
					
					var fromElementId = $(this).attr("cpfrom");
					var toElementId = $(this).attr("cpto");
					
					$.ajax({
					   type: "POST",
					   dataType: "json",
					   url: "<%=basePath%>setting/position/save",
					   data: formData,
					   success: function(msg){
					     if(msg.type == "SUCCESS"){
					    	 $("#"+toElementId).empty();
					    	 $("#"+fromElementId+" [ref='locale']").val("");
					    	 $("#"+fromElementId+" [ref='position']").val("");
					     }
					   }
					});
					
				});
				
				$("#firSelect").change(function(){
			        var selectVal = $(this).val();
			        var secObj = $("#secSelect");
			        secObj.empty();
			        $.ajax({
					   type: "POST",
					   dataType: "json",
					   url: "<%=basePath%>setting/position/getSub",
					   data: {"pid":selectVal},
					   success: function(msg){
						   if (msg != null) {
					            $.each(msg, function(key, val) {
					                secObj.append('<option value="' + val.id + '">' + val.i18nName + '</option>');
					            });
					        }
					   }
					});
			    });
				
				$("#firSelect").change();
				
			});
			
			function processFromData(className){
				var postData = {};
				var idx = 0;
				$("."+className).each(function (index, domEle){
					var localeObj = $(domEle).find("[ref='locale']");
					var nameObj = $(domEle).find("[ref='position']");
					
					if(localeObj != null && localeObj.length > 0 
							&& nameObj != null && nameObj.length > 0){
						var currLocale = $(localeObj[0]).val();
						var currName = $(nameObj[0]).val();
						if($.trim(currLocale) != "" && $.trim(currName) != ""){
							var currLocaleKey = "locale" + idx;
							var currNameKey = "name" + idx;
							postData[currLocaleKey] = currLocale;
							postData[currNameKey] = currName;
							idx++;
						}
					}
				});
				
				postData["paramIndex"] = idx;
				return postData;
			}
		    
		
		</script>
		
		
	</body>
</html>