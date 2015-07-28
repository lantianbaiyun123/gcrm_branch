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
							            <p><s:message code="view.msg.subscribe.add"/></p>
							    </blockquote>
						    </div>
						</div>
						
						
						<div class="span11">
						    <div class="tabbable">
						        <ul class="nav nav-tabs">
						            <li class="active"><a id="tab1" href="#tab1" data-toggle="tab"><s:message code="view.msg.subscribe.offer"/></a></li>
						            <li><a href="#tab2" id="tab2" data-toggle="tab"><s:message code="view.msg.subscribe.ad"/></a></li>
						        </ul>
						        <div class="tab-content">
						            <form class="form-inline" id="form1">
						                <div class="controls controls-row">
						                <span id="offer_span"><s:message code="view.msg.subscribe.offer"/>：</span>
						                <span class="hide" id="adstat_span"><s:message code="view.msg.subscribe.ad"/>：</span>
						                <c:set var="offerTypes" value="<%=OfferTypeConstants.values()%>"/> 
						                <select id="offerType" name="offerType" class="input-medium">
						                    <c:forEach items="${offerTypes}" var="offerType">
						                    	<option value="${offerType}">${offerType}</option>
								        	</c:forEach>
						                </select>
				                		<s:message code="view.settings.country.nation"/>：
						                <select name="country" class="input-medium">
							                <option value=""><s:message code="view.select"/></option>
							                <c:forEach items="${countryList}" var="country">
						                    	<option value="${country.id}">${country.id}- ${country.i18nName}</option>
								        	</c:forEach>
								        	
						                </select>
						                <s:message code="view.msg.subscribe"/>：
						                <div class="input-append"><input class="span6" name="email" value="" type="text">
						                	<span class="add-on">@baidu.com</span></div>
						                	<button type="button" id="subscribe_btn" class="btn btn-info"><s:message code="view.msg.subscribe"/></button>
						                </div>
						            </form>
						        </div>
						    </div>
						
							<div id="alert_block" class="alert alert-block hide">
							    <h4><s:message code="view.warning"/>!</h4> <s:message code="view.msg.subscribed"/>
							</div>
						</div>
					</div>
				</div>
				
				<div id="alert_del_div" class="modal hide fade">
			  		<div class="modal-header">
				    	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					    <h4 id="alert_title"></h4>
				  	</div>
				  	<div class="modal-body">
				    	<p id="alert_msg"></p>
				  	</div>
				  	<div class="modal-footer">
				    	<a href="#" data-dismiss="modal" aria-hidden="true" class="btn btn-success"><s:message code="view.ok"/></a>
				  	</div>
				</div>
				
				
			</div>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function(){
				var type = 1;
				$("#tab1, #tab2").click(function(){
				    var id = $(this).attr('id');
				    if (id == 'tab2') {
				        type = 2;
				        $("#offer_span").hide();
				        $("#adstat_span").show();
				    } else {
				        type = 1;
				        $("#offer_span").show();
				        $("#adstat_span").hide();
				    } 
				});
				
				
				$("#subscribe_btn").click(function(){

				    $(".alert").hide();
				    var country = $("#form1 select[name='country']").val();
				    var email  = $("#form1 input[name='email']").val();

				    if (country == '') {
				        $("#form1 select[name='country']").focus();
				        return;    
				    } 
				    if(email == '') {
				        $("#form1 input[name='email']").focus();
				        return;
				    }
				    var inner_type = $("#form1 select[name='offerType']").val();
				    $("#alert_title").html(GCRM.util.message('view.msg.subscribe.add'));
				    $.post("<%=basePath%>subscribe/save", {country: country, email: email, subType: type, offerType:inner_type}, function(data){
				        if (data.type == 'SUCCESS') {
				            $("#alert_msg").html(GCRM.util.message('view.msg.subscribe.add') + " " + GCRM.util.message('view.success'));
				            $("#alert_del_div").modal('show');
				        } else {
				            $("#alert_msg").html(GCRM.util.message('view.msg.subscribe.add') + " " + GCRM.util.message('view.fail'));
				            $("#alert_del_div").modal('show');
				        }
				    });

				});
				
				
			});
		
		</script>
		
		
	</body>
</html>