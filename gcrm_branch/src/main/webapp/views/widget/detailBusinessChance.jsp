<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
    <dl class="dl-horizontal baseInfo-check bussinessChanceList">
	    <dt><s:message code="opportunity.budget"/>：</dt>
	    <dd>
	       <c:if test="${!empty opportunityView.budget}">
	           <c:out value="${opportunityView.currencyType}"/><c:out value="${opportunityView.budget}"/>
	       </c:if>
       </dd>
	    <dt><s:message code="opportunity.spendingTime"/>：</dt>
	    <dd><c:out value="${opportunityView.spendingTime}"/></dd>
	    <dt><s:message code="opportunity.payment"/>：</dt>
	    <c:if test="${opportunityView.payment==0}">
            <dd><s:message code="opportunity.payment.advance"/></dd>
        </c:if> 
        <c:if test="${opportunityView.payment==1}">
            <dd><s:message code="opportunity.payment.after"/></dd>
            <dt><s:message code="opportunity.paymentPeriod"/>：</dt>
            <dd><c:out value="${opportunityView.paymentPeriod}"/><s:message code="opportunity.day"/></dd>
            
        </c:if>
                        
	    <dt><s:message code="opportunity.billingModel"/>：</dt>
	    <dd><c:out value="${opportunityView.billingModel}"/></dd>
	    <dt><s:message code="opportunity.businessType"/>：</dt>
	    <dd>
	       <c:if test="${fn:contains(opportunityView.businessType,0)}">
              <span id="businessType" name="businessType"><s:message code="opportunity.businessType.realization"/></span>
           </c:if> 
           <c:if test="${fn:contains(opportunityView.businessType,1)}">
              <span id="businessType" name="businessType"><s:message code="opportunity.businessType.sales"/></span>
           </c:if>
	    </dd>
	    <dt><s:message code="opportunity.advertisingPlatform"/>：</dt>
	    <dd>
	       <c:out value="${opportunityView.advertisingPlatform}"/>
	    </dd>
	    <dt><s:message code="opportunity.description"/>：</dt>
	    <dd><c:out value="${opportunityView.description}"/></dd>
    </dl> 
</div>
<script type="text/javascript">
	moduleConfig.detailBusinessChance.success = function(){
		$('#detailBusinessChance .panel-body select').addClass("col-md-12").selectpicker({});
		$("#editBusinessChance").show();
		$.gcrm.editBusinessChance();
		$.gcrm.dealCancellation("#editBusinessChance");
	};
</script>