<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<input type="hidden" id="companyName" value="${customerView.customer.companyName}" >
<input type="hidden" class="approvalStatus" value="${customerView.approvalStatus}" >
<input type="hidden" class="customerNumber" value="${customerView.customer.customerNumber}" >
<div class="J_baseInfoContainer">
<div class="row baseInfo-check">
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.companyName"/>：</div>
  <div class="col-md-8">${customerView.customer.companyName}</div></div>
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.businessType"/>：</div>
  <div class="col-md-8">
  	<c:if test="${customerView.customerType != null}">
  		<s:message code="customer.type.${customerView.customerType}"/>
  	</c:if>
  </div></div>
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.country"/>：</div>
  <div class="col-md-8">${customerView.country.i18nName}</div></div>
  <c:choose>
  	<c:when test="${customerView.customerType == 'offline'}">
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.agentType"/>：</div>
  	  <div class="col-md-8">
  	  	<c:if test="${customerView.agentType != null}">
  	  		<s:message code="customer.agentType.${customerView.agentType}"/>
  	  	</c:if>
  	  </div></div>
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.agentRegional"/>：</div>
  	  <div class="col-md-8">${customerView.agentRegional.i18nName} </div></div> 
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.agentCountry"/>：</div>
  	  <div class="col-md-8">
  	  	<c:forEach items="${customerView.agentCountry}" var="agentCountry">
   			 <span>${agentCountry.i18nName}</span>
   		</c:forEach>
  	  </div></div>
  	</c:when>
  	<c:when test="${customerView.customerType == 'nondirect'}">
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.agentCompany"/>：</div>
  	  <div class="col-md-8">${customerView.agentCompany.companyName}</div></div>
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.industry"/>：</div>
  	  <div class="col-md-8">${customerView.industry.i18nName}</div></div>  
  	</c:when>
  	<c:otherwise>
  	  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.industry"/>：</div>
  	  <div class="col-md-8">${customerView.industry.i18nName}</div></div>
  	</c:otherwise> 
  </c:choose> 
  
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.businessLicense"/>：</div>
  <div class="col-md-8">${customerView.customer.businessLicense}</div></div>
  
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.registerTime"/>：</div>
  <div class="col-md-8"><fmt:formatDate value="${customerView.customer.registerTime}" pattern="yyyy-MM-dd" /></div></div>
  
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.url"/>：</div>
  <div class="col-md-8"><a href="${customerView.customer.url}" target="_blank">${customerView.customer.url}</a></div></div>
  
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.type"/>：</div>
  <div class="col-md-8">
  <c:if test="${fn:contains(customerView.customer.type,'0')}">
	<s:message code="customer.title.type.sale"/>
  </c:if>
  <c:if test="${fn:contains(customerView.customer.type,'1')}">
	<s:message code="customer.title.type.cash"/>
  </c:if>
  </div></div> 	

  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.belongSales"/>：</div>
  <div class="col-md-8">${customerView.belongSales.name}</div></div>

  <div class="col-md-6"><div class="col-md-4"><s:message code="customer.title.belongManager"/>：</div>
  <div class="col-md-8">${customerView.customer.belongManager}</div></div>

  <div class="col-md-6"><div class="col-md-4"><s:message code="customer.title.companySize"/>：</div>
  <div class="col-md-8"><s:message code="customer.companySize.${customerView.companySize}"/></div></div>
  
  <div class="col-md-6"><div class="col-md-4"><s:message code="customer.title.currencyType"/>：</div>
  <div class="col-md-8">
  <c:if test="${not empty customerView.customer.registerCapital}">
  	${customerView.currencyType.i18nName}
 	${customerView.customer.registerCapital}
  </c:if>
  
  </div></div>
  
  <div class="col-md-6"><div class="col-md-4"><s:message code="customer.title.businessScope"/>：</div>
  <div class="col-md-8">${customerView.customer.businessScope}</div></div>
  
  <div class="col-md-6"><div class="col-md-4"><span class="txt-impt">*</span><s:message code="customer.title.address"/>：</div>
  <div class="col-md-8">${customerView.customer.address}</div></div>
  
  <div class="col-md-6"><div class="col-md-4"><s:message code="customer.title.description"/>：</div>
  <div class="col-md-8">${customerView.customer.description}</div></div>
</div>
</div>