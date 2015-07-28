<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form class="form-inline" role="form" id="customerSearchFrom" action="<%=basePath%>customer/query" method="post">
	<div class="row">
	
	    <div class="col-md-4">
	    	<div class="col-md-1"><p class="paddingTop6 paddingRight0 marginBottom0"><s:message code="customer.list.in"/>:</p></div>
	    	<div class="col-md-5 paddingRight0 paddingLeft0">
		    	<select class="form-control" name="queryType" id="market">
		            <option <c:if test="${condition.queryType == 'COMPANY'}">selected="selected"</c:if> value="COMPANY"><s:message code="customer.title.companyName"/></option>
		            <option <c:if test="${condition.queryType == 'SALES'}">selected="selected"</c:if> value="SALES"><s:message code="customer.title.belongSales"/></option>
		            <option <c:if test="${condition.queryType == 'AGENT'}">selected="selected"</c:if> value="AGENT"><s:message code="customer.title.agentCompany"/></option>
		            <option <c:if test="${condition.queryType == 'AGENT_CUSTOMER_NUMBER'}">selected="selected"</c:if> value="AGENT_CUSTOMER_NUMBER"><s:message code="customer.title.number"/></option>
		        </select>
	        </div>
	        <div class="col-md-6">
		        <input type="text" class="form-control" name="queryStr" value="${condition.queryStr}" >
		    </div>
	    </div>
	
		<div class="col-md-3 textCenter">
			<div class="displayInlineBlock textLeft">
			 <div class="pull-left"><p class="btn btn-link disabled fontBlack"><s:message code="customer.approvalStatus"/>:</p></div>
		        <div class="pull-left paddingLeft0" style="min-width: 115px;">
		            <select class="form-control" name="approvalStatus" id="country">
		                <option value=""><s:message code="customer.holder.choose"/></option>
		                <option <c:if test="${condition.approvalStatus == '0'}">selected="selected"</c:if> value="0"><s:message code="customer.approvalStatus.saving"/></option>
		                <option <c:if test="${condition.approvalStatus == '1'}">selected="selected"</c:if> value="1"><s:message code="customer.approvalStatus.refused"/></option>
		                <option <c:if test="${condition.approvalStatus == '2'}">selected="selected"</c:if> value="2"><s:message code="customer.approvalStatus.approving"/></option>
		                <option <c:if test="${condition.approvalStatus == '3'}">selected="selected"</c:if> value="3"><s:message code="customer.approvalStatus.approved"/></option>
		            </select>
		        </div>
		    </div>   
	    </div>
	
		<div class="col-md-3 paddingRight0">
	        <div class="col-md-4 paddingRight0"><p class="btn btn-link disabled fontBlack"><s:message code="customer.title.country"/>:</p></div>
	        <div class="col-md-8 paddingLeft0">
	            <input type="text"  class="form-control tags" id="searchCountry" class="span2" value="${customerView.country.i18nName}"/>
	            <input type="hidden" name="country" id="searchCountryId" class="form-control" class="span2" value="${customerView.country.id}"/>
	        </div>
	    </div>
	
		<div class="col-md-2 paddingLeft0">
	        <div class="col-md-4">
	        	<button type="button" id="listSearchBtn" class="btn btn-info"><s:message code="view.query"/></button>
	        </div>
	        <div class="col-md-6">
	        	<a id="moreSelect" class="btn btn-link" data-toggle="collapse" data-target="#searchBarRow2"><s:message code="customer.list.moreQueries"/></a>
	        </div>
	    </div>
	    
	</div>
	<div class="panel-collapse collapse row" id="searchBarRow2" >
	
		 <div class="col-md-4 paddingTop12">
	        <div class="pull-left "><p class="btn btn-link disabled fontBlack paddingLeft0"><s:message code="customer.title.businessType"/>:</p></div>
	        <div class="pull-left paddingLeft0" style="min-width:150px;">
	            <select class="form-control" name="businessType" id="clientType">
	                <option value=""><s:message code="customer.holder.choose"/></option>
	                <c:forEach items="${customerTypes}" var="customerType" varStatus="customerTypeStatus">
		                <option value="${customerType.value}"
		                    <c:if test="${condition.businessType == customerType.value}">
		                        selected="selected" 
		                    </c:if>
		                >
		                    <s:message code="customer.type.${customerType}"/>
		                </option> 
		            </c:forEach>
	            </select>
	        </div>
	    </div>
	    
		<div class="col-md-3 paddingTop12 textCenter">
			<div class="displayInlineBlock textLeft">
		        <div class="pull-left"><p class="btn btn-link disabled fontBlack"><s:message code="customer.title.agentRegional"/>:</p></div>
		        <div class="pull-left" style="min-width:115px;">
		            <select class="form-control" name="agentRegional" id="region" >
		                <option value=""><s:message code="customer.holder.choose"/></option>
		                <c:forEach var="agentRegional" items="${agentRegionals}" varStatus="status">
		                    <option <c:if test="${condition.agentRegional == agentRegional.id}">selected="selected"</c:if> value="${agentRegional.id}">${agentRegional.i18nName}</option>
				        </c:forEach>
		            </select>
		        </div>
		    </div>
	    </div>
	
	</div>
</form>
