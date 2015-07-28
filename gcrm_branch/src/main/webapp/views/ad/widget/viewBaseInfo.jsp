<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<form action="" class="form-horizontal" id="baseInfo">
	<input type="hidden" id="adSolutionId" name="advertiseSolutionView.advertiseSolution.id"
		value="${baseInfo.advertiseSolutionView.advertiseSolution.id}">
    <h5 class="bdt ptb10 pl10">客户基本信息</h5>
    <div class="form-group">
        <label class="control-label col-md-2">公司名称：</label>
        <div class="col-md-3">
            <p class="form-control-static">${baseInfo.customerView.customer.companyName}</p>
        </div>
        <label class="control-label col-md-2">所属销售：</label>
        <p class="form-control-static col-md-4" id="sale">${baseInfo.customerView.customer.belongSales}</p>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">客户类型：</label>
        <p class="form-control-static col-md-3" id="customerType">${baseInfo.customerView.customerType}</p>
        <label class="control-label col-md-2">所属国家：</label>
        <p class="form-control-static col-md-4" id="country">${baseInfo.customerView.country.i18nName}</p>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2">代理类型：</label>
        <p class="form-control-static col-md-3" id="agentType">${baseInfo.customerView.agentType}</p>
        <label class="control-label col-md-2">代理地区：</label>
        <p class="form-control-static col-md-4" id="agentArea">${baseInfo.customerView.agentRegional.i18nName}</p>
    </div>
        <div class="form-group">
        <label class="control-label col-md-2">代理国家：</label>
        <c:choose>
        	<c:when test="${fn:length(baseInfo.customerView.customer.agentCountry) >0}">
        		<c:forEach var="agentCountry" items="${baseInfo.customerView.agentCountry}">
	        		<p class="form-control-static col-md-4" id="agentCountry">${agentCountry.i18nName} </p>
        		</c:forEach>
        	</c:when>
        	<c:otherwise>
        		<p class="form-control-static col-md-4" id="agentCountry">--</p>
        	</c:otherwise>
        </c:choose>        
    </div>

  <h5 class="bdt ptb10 pl10">所属合同</h5> 
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">合同编号：</label>
        <div class="col-md-3">
            <p class="form-control-static">${baseInfo.contract.number}</p>
        </div>
        <label class="control-label col-md-2" for="registerTime" >合同类型：</label>
        <p class="form-control-static col-md-4" id="contractType">${baseInfo.contract.category}</p>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime" >合同概要：</label>
        <p class="form-control-static col-md-3" id="contractSummary">${baseInfo.contract.summary}</p>
        <label class="control-label col-md-2" for="belongSales" >合同有效期：</label>
        <p class="form-control-static col-md-4" id="contractexpDate">
        	<fmt:formatDate value="${baseInfo.contract.beginDate}" pattern="dd-MM-yyyy"/>~
        	<fmt:formatDate value="${baseInfo.contract.endDate}" pattern="dd-MM-yyyy"/>
        </p>
    </div>

  <h5 class="bdt ptb10 pl10">广告预算</h5>
  <div class="form-group">
      <label class="control-label col-md-2" for="registerTime">广告预算：</label>
      <div class="col-md-4">
           <p class="form-control-static">${baseInfo.advertiseSolutionView.advertiseSolution.budget}</p>
      </div>
  </div>
  <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">投放周期：</label>
        <div class="col-md-2">
            <p class="form-control-static">
            	<fmt:formatDate value="${baseInfo.advertiseSolutionView.advertiseSolution.startTime}" pattern="dd-MM-yyyy"/>
            	~
            	<fmt:formatDate value="${baseInfo.advertiseSolutionView.advertiseSolution.endTime}" pattern="dd-MM-yyyy"/>
            </p>
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
</form>