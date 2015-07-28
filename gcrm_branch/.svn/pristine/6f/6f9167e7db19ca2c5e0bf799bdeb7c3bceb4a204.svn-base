<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib  uri="http://www.springframework.org/tags/form" prefix="sf"%>
<script type="text/javascript" src="../resources/lib/js/jquery.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<body>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
	<div class="container">
		<div class="container">
			<h2>业务机会-新增</h2>
			<form class="form-horizontal" method="POST"
				action="<%=basePath%>/opportunity/addOpportunity">
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.budget"/>：</label>
				    <div class="col-md-3">
				        <div class="input-group">
				            <div class="input-group-btn">
								<select id="opportunity.currencyType" name="opportunity.currencyType" class="span2" style="width: 50px;">
									<c:forEach items="${currencyTypeList}" var="currencyType" varStatus="status">
									<option value="${currencyType.id}">${currencyType.name}</option>
									</c:forEach>
								</select> 
				           </div>
							<input type="text" id="opportunity.budget" name="opportunity.budget"
								value="${opportunityVO.opportunity.budget}">
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.spendingTime"/>：</label>
				    <div class="col-md-3">
				        <input type="text" placeholder="<s:message code="opportunity.registerTime"/>"
											name="opportunity.spendingTime" id="opportunity.spendingTime" class="form-control"
											value="${opportunityVO.opportunity.spendingTime}">
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.payment"/>：</label>
				    <div class="col-md-2">
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="0"><s:message code="opportunity.payment.advance"/>
				        </label>
				        <label class="radio radio-inline">
				            <input type="radio" name="opportunity.payment" value="1"><s:message code="opportunity.payment.after"/>
				        </label>
				    </div>
				
				    <div class="col-md-2">
				        <div class="input-group">
				            <input type="text" class="form-control" placeholder="<s:message code="opportunity.paymentPeriod"/>" name="opportunity.paymentPeriod" id="opportunity.paymentPeriod" value="${opportunityVO.opportunity.paymentPeriod}">
				            <span class="input-group-addon">天</span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.billingModel"/>：</label>
					    <div class="col-md-5">
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="分成">分成
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPC">CPC
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPT">CPT
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPM">CPM
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPS">CPS
				        </label>
				        <label class="checkbox checkbox-inline">
				            <input type="checkbox" name="opportunity.billingModel" value="CPA">CPA
				        </label>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.businessType"/>：</label>
				    <div class="col-md-2">
						<label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="0"><s:message code="opportunity.businessType.realization"/>
						</label> <label class="checkbox checkbox-inline"> <input
							type="checkbox" name="opportunity.businessType" value="1"><s:message code="opportunity.businessType.sales"/>
						</label>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.advertisingPlatform"/>：</label>
				    <div class="col-md-10" id="checkboxs0">
			        	<c:forEach items="${realizedAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}">${advertisingPlatform.name}
				        	</label>
						</c:forEach>				    
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2">&nbsp;</label>
				    <div class="col-md-10" id="checkboxs1">
			        	<c:forEach items="${salesAdvertisingPlatformList}" var="advertisingPlatform" varStatus="status">
		        			<label class="checkbox checkbox-inline">
				            	<input type="checkbox" name="platformIds" value="${advertisingPlatform.id}">${advertisingPlatform.name}
				        	</label>
						</c:forEach>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.description"/>：</label>
				    <div class="col-md-5">
				        <textarea placeholder="<s:message code="opportunity.description"/>" name="opportunity.description" id="opportunity.description" cols="30" rows="3" class="form-control" value="${opportunityVO.opportunity.description}"></textarea>
				    </div>
				</div>
				<!--<input type="text" id="aa" name="aa" value="${opportunityVO.realizedAdvertisingPlatformList[0].name}"/>  -->
				<input type="submit" id="createCustomer"
					class="btn btn-large btn-primary" value="保存机会" />
			</form>
		</div>
	</div>
	</body>
	<script>
	$( document ).ready(function() {

	});
	$(function() {
		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
		$("[name='platformIds']").each(function(index,element){
		    $(element).attr("disabled",true);
		  });	
	});

	$(function(){   
	     $("[name='opportunity.payment']").click(function(){
	     	var payment = $(this).val();
	     	if(payment==0){
	     		$("[name='opportunity.paymentPeriod']").value="";
	     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").hide();
	     	}
			else  if(payment==1){
	     		$("[name='opportunity.paymentPeriod']").closest(".col-md-2").show();
	     	}
	     });

	     $("[name='opportunity.businessType']").click(function(){
	      	var businessType = $(this).val();
	      	if(businessType==0){   		
	      		if($(this).is(":checked")){
	      			$("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){
	      				$(element).attr("disabled", false);
	      		  });	
	      		}else{
	      			$("[id='checkboxs0']").find("[name='platformIds']").each(function(index,element){    				
	      				$(element).attr("disabled",true);
	      				$(element).attr("checked", false);
	      		  });	
	      		}
	      	}else if(businessType==1){   		
	      		if($(this).is(":checked")){
	      			$("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
	      				$(element).attr("disabled", false);
	      				console.log(index, element);
	      		  });	
	      		}else{
	      			$("[id='checkboxs1']").find("[name='platformIds']").each(function(index,element){
	      				$(element).attr("disabled",true);
	      				$(element).attr("checked", false);
	      		  });	
	      		}
	      	}
	      });     

	})
	</script>
</html>
					
