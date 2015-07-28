<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div class="container">
		<div class="container">
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.budget"/>：</label>
				    <div class="col-md-3">
				        <div class="input-group">
						   <span id="currencyType" name="currencyType">${opportunityView.currencyType}</span>
				           <span id="budget" name="budget">${opportunityView.budget}</span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.spendingTime"/>：</label>
				    <div class="col-md-3">
				        <span id="spendingTime" name="spendingTime">${opportunityView.spendingTime}</span>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.payment"/>：</label>
				    <div class="col-md-2">
						<c:if test="${opportunityView.payment==0}">
						   <span id="payment" name="payment"><s:message code="opportunity.payment.advance"/></span>
						</c:if>	
						<c:if test="${opportunityView.payment==1}">
						   <span id="payment" name="payment"><s:message code="opportunity.payment.after"/></span>
						</c:if>										    	
				    </div>
				
				    <div class="col-md-2">
				        <div class="input-group">
				            <span id="paymentPeriod" name="paymentPeriod">${opportunityView.paymentPeriod}</span>
				            <span class="input-group-addon">天</span>
				        </div>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.billingModel"/>：</label>
				    <span id="billingModel" name="billingModel">${opportunityView.billingModel}</span>
				    </div>
				</div> 
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.businessType"/>：</label>
				    <div class="col-md-2">
				    	<c:if test="${opportunityView.businessType==0}">
						   <span id="businessType" name="businessType"><s:message code="opportunity.businessType.realization"/></span>
						</c:if>	
				    	<c:if test="${opportunityView.businessType==1}">
						   <span id="businessType" name="businessType"><s:message code="opportunity.businessType.sales"/></span>
						</c:if>
				    </div>
				</div>
				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.advertisingPlatform"/>：</label>
					<span id="advertisingPlatforms" name="advertisingPlatforms">${opportunityView.advertisingPlatform}</span>
				</div>

				<div class="form-group">
				    <label class="control-label col-md-2"><s:message code="opportunity.description"/>：</label>
				    <div class="col-md-5">
				        <span id="description" name="description">${opportunityView.description}</span>
				    </div>
				</div>				
		</div>
	</div>


					
