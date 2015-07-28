<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib  uri="http://www.springframework.org/tags/form" prefix="sf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<body>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
	<div class="container">
		<div class="container">
			<h2>业务机会-更新</h2>
			<form class="form-horizontal" method="POST"
				action="<%=basePath%>/opportunity/updateOpportunity">
			<input type="text" id="opportunity.id" name="opportunity.id"
				value="${opportunityVO.opportunity.id}" hidden="true">				
				<div class="form-group">
				    <label class="control-label col-md-2">在线广告投放预算：</label>
				    <div class="col-md-3">
				        <div class="input-group">
				            <div class="input-group-btn">
								<select id="opportunity.currencyType" name="opportunity.currencyType" class="span2" style="width: 50px;">
									<c:forEach items="${currencyTypeList}" var="currencyType" varStatus="status">
									<option value="${currencyType.id}"  <c:out value="${opportunityVO.opportunity.currencyType==currencyType.id?'selected=\"selected\"':'' }"/>>${currencyType.name}</option>
									</c:forEach>
								</select> 
				           </div>
							<input type="text" id="opportunity.budget" name="opportunity.budget"
								value="${opportunityVO.opportunity.budget}">
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">预算对应投放周期：</label>
				    <div class="col-md-3">
				        <input type="text" placeholder="请输入注册时间(dd-mm-yyyy)"
											name="opportunity.spendingTime" id="opportunity.spendingTime" class="form-control"
											value="${opportunityVO.opportunity.spendingTime}">
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">可接受的付款方式：</label>
				    <div class="col-md-2">
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="0" <c:out value="${opportunityVO.opportunity.payment==0?'checked':'' }"/>>预付款
				        </label>
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="1" <c:out value="${opportunityVO.opportunity.payment==1?'checked':'' }"/>>后付款
				        </label>
				    </div>
				
				    <div class="col-md-2">
				        <div class="input-group">
				            <input type="text" class="form-control" placeholder="需要的付款账期" name="opportunity.paymentPeriod" id="opportunity.paymentPeriod" value="${opportunityVO.opportunity.paymentPeriod}">
				            <span class="input-group-addon">天</span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">期望计费模式：</label>
				    <div class="col-md-5">
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="分成" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'分成')}">checked="checked"</c:if>>分成
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPC" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'CPC')}">checked="checked"</c:if>>CPC
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPT" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'CPT')}">checked="checked"</c:if>>CPT
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPM" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'CPM')}">checked="checked"</c:if>>CPM
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPS" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'CPS')}">checked="checked"</c:if>>CPS
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPA" <c:if test="${fn:contains(opportunityVO.opportunity.billingModel,'CPA')}">checked="checked"</c:if>>CPA
				        </label>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">合作业务类型：</label>
				    <div class="col-md-2">
						<label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="0" <c:if test="${fn:contains(opportunityVO.opportunity.businessType,'0')}">checked="checked"</c:if>>变现
						</label> 
						<label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="1" <c:if test="${fn:contains(opportunityVO.opportunity.businessType,'1')}">checked="checked"</c:if>>销售
						</label>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">投放平台：</label>
				    <div class="col-md-10">
			        	<c:forEach items="${realizedAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
		        				<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(opportunityVO.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.name}
				        	</label>
						</c:forEach>				    
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">&nbsp;</label>
				    <div class="col-md-10">
			        	<c:forEach items="${salesAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}" <c:if test="${fn:contains(opportunityVO.platformIds,advertisingPlatform.id)}">checked="checked"</c:if>>${advertisingPlatform.name}
				        	</label>
						</c:forEach>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">其他说明：</label>
				    <div class="col-md-5">
				        <textarea placeholder="其他说明" name="opportunity.description" id="opportunity.description" cols="30" rows="3" class="form-control">${opportunityVO.opportunity.description}</textarea>
				    </div>
				</div>
				<div>
				</div>

				<input type="submit" id="createCustomer"
					class="btn btn-large btn-primary" value="保存更新" />
			</form>
		</div>
	</div>
</body>
<fis:script>
$("[name='opportunity.paymentPeriod']").closest(".col-md-2").hidden();
$(function(){   
     $("[name='opportunity.payment']").click(function(){
     	var payment = $(this).val();
     	if(payment==0){
     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").hidden();
     	}
		else  if(payment==0){
     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").show();
     	}
     });
})
</fis:script>
</html>
					
