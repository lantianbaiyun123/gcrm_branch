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
									<s:message code="view.offer"/>
								</li>
							</div>
							<div id="collapse1" class="accordion-body collapse in">
								<div class="accordion-inner">
									<ul class="nav nav-list">
			                            <li>
			                            	<a class="bgcolor" href="<s:url value="/offer/list/" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.offer.ad"/></small>
		                            		</a>
			                            </li>
			                            <li>
			                            	<a href="<s:url value="/offer/customer/list" />">
			                            		<i class=" icon-th-list"></i><small><s:message code="view.offer.customer"/></small>
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
						    <div class="span3">
						    <blockquote>
						            <p><s:message code="view.offer.customer.info"/></p>
						    </blockquote>
						    </div>
						    <div class="span2" style="margin-left:-30px;">
						        <a id="btn_open" class="btn btn-small" href="javascript:void(0);"><i class="icon-folder-open"></i><s:message code="view.add"/></a>
						        <a id="btn_close" class="btn btn-small" href="javascript:void(0);" style="display:none"><i class="icon-folder-close"></i><s:message code="view.close"/></a>
						    </div>
						</div>
						<style>
						.form-horizontal .control-label {
						    width:80px;
						}
						.form-horizontal .controls {
						    margin-left:80px;
						}
						</style>
						<div class="span11 hide" id="addUser" >
							<form class="form-horizontal" id="add_form" action="/offer/customer/save" method="post">
								<div class="span4">
								    <div class="control-group">
								        <label class="control-label" for="inputName"><s:message code="view.offer.customer.name"/>：</label>
								        <div class="controls">
								        	<input type="text" name="name" id="inputName" value="">
								        </div>
								    </div>
								    <div class="control-group">
								    	<label class="control-label" for="inputFullName"><s:message code="view.offer.customer.fullName"/>：</label>
								        <div class="controls">
								            <input type="text" name="fullName" id="inputFullName" value="">
								        </div>
								    </div>
								</div>
								<div class="span3">
								    <div class="control-group">
								        <label class="control-label" for="inputEmail"><s:message code="view.email"/>：</label>
								        <div class="controls">
								            <input type="text" name="email" id="inputEmail" value="">
								        </div>
								    </div>
								    <div class="control-group">
								        <label class="control-label" for="inputRemark"><s:message code="view.remark"/>：</label>
								        <div class="controls">
								        	<textarea rows="2" name="remark" id="inputRemark"></textarea>
								        </div>
								    </div>
								</div>
								<div class="control-group span7">
							    	<button type="submit" class="btn btn-primary" id="btn_save" rel="add"><s:message code="view.save"/></button>
							    </div>
								
							</form>
						</div>
						
						<div class="span11">
						
						    <hr>
						    
						    <%-- 
						    <form class="form-search" id="search_form" action="<?php echo $baseUrl1;?>" method="get">
						        <select class="input-medium" name="sfield">
						            <option value="uid" <?php if($sfield == 'uid'): ?>selected<?php endif;?>>UID</option>
						            <option value="name" <?php if($sfield == 'name'): ?>selected<?php endif;?>><?php echo _('客户名称');?></option>
						            <option value="email" <?php if($sfield == 'email'): ?>selected<?php endif;?>>Email</option>
						            <option value="ctime" <?php if($sfield == 'ctime'): ?>selected<?php endif;?>><?php echo _('创建时间');?></option>
						        </select>
						        <input type="hidden" value=<?php echo $type;?> name="type">
						        <input type="text" name="svalue" class="span3" value="<?php echo $svalue;?>">
						        <button type="button" id="search_btn" class="btn"><?php echo _('搜索');?></button>
						    </form>
						    --%>
						    
						    <table class="table table-hover table-bordered table-condensed">
							    <thead>
							        <tr>
							        <th>ID</th>
							            <th><s:message code="view.offer.customer.name"/></a></th>
							            <th><s:message code="view.offer.customer.fullName"/></th>
							            <th><s:message code="view.email"/></th>
							            <th><s:message code="view.createTime"/></th>
							            <th><s:message code="view.remark"/></th>
							            <th width="100"><s:message code="view.operate"/></th>
							        </tr>
							    </thead>
							    <tbody>
							    
							    	<c:forEach items="${page.content}" var="customer">
						        		<tr>
						                    <td>${customer.id}</td>
						                    <td>${customer.name}</td>
						                    <td>${customer.fullName}</td>
						                    <td>${customer.email}</td>
						                    <td>${customer.optDate}</td>
						                    <td>${customer.remark}</td>
						                    <td>
						                    	<%-- 
							                    <c:if test="${status == 'ENABLE'}"><a href="javascript:void(0);" rel="${country.id}" class="close_btn"><s:message code="view.close"/></a></c:if>
							                    <c:if test="${status == 'DISABLE'}"><a href="javascript:void(0);" rel="${country.id}" class="open_btn"><s:message code="view.open"/></a></c:if>
							                    --%>
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
				$("#btn_close, #btn_open").click(function(){
				    if ($(this).attr("id") == "btn_open") {
				        $(this).hide();
				        $("#btn_close").show();
				        $("#addUser").show();
				    } else {
				        $(this).hide();
				        $("#btn_open").show();
				        $("#addUser").hide();
				    }
				});
				
				
			});
			
			
		
		</script>
		
		
	</body>
</html>