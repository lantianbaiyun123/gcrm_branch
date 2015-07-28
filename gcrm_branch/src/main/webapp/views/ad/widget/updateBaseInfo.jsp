<!--基本信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<form action="" class="form-horizontal" class="baseInfoForm">
<input type="hidden" id="adSolutionId" name="advertiseSolutionView.advertiseSolution.id"
		value="${baseInfo.advertiseSolutionView.advertiseSolution.id}">
<input type="hidden" id="adSolutionNumber" name="advertiseSolutionView.advertiseSolution.number"
		value="${baseInfo.advertiseSolutionView.advertiseSolution.number}">
  <h5 class="bdt ptb10 pl10 ">客户基本信息</h5> 
  <div class="form-group">
      <label class="control-label col-md-2">公司名称：</label>
      <div class="col-md-4">
          <input placeholder="请输入一个公司实体名称" type="text" class="form-control" id="companyName" name="companyName" 
          		value="${baseInfo.customerView.customer.companyName}"	data-describedby="companyNameTitle" data-description="companyName" data-required="true">
          <input type="hidden" id="customerNumber" name="customerView.customer.customerNumber" value="${baseInfo.customerView.customer.customerNumber}">
      </div>
      <div class="col-md-5 help-block">“广告主-非直客”类型的客户，此处需填写代理的公司名称</div>
  </div>
  <div <c:if test="${empty baseInfo.customerView.customer.customerNumber}">class="hide"</c:if> id="companyInfo">
      <div class="form-group">
          <label class="control-label col-md-2">客户类型：</label>
          <p class="form-control-static col-md-3" id="customerType">${baseInfo.customerView.customerType}</p>
          <label class="control-label col-md-2">所属销售：</label>
          <p class="form-control-static col-md-4" id="sale">${baseInfo.customerView.customer.belongSales}</p>
      </div>
      <div class="form-group">
          <label class="control-label col-md-2">代理类型：</label>
          <p class="form-control-static col-md-3" id="agentType">${baseInfo.customerView.agentType}</p>
          <label class="control-label col-md-2">所属国家：</label>
          <p class="form-control-static col-md-4" id="country">${baseInfo.customerView.country.i18nName}</p>
      </div>
      <div class="form-group">
          <label class="control-label col-md-2">代理国家：</label>
         
          	<c:choose>
	        	<c:when test="${fn:length(baseInfo.customerView.customer.agentCountry) >0}">
	        		<c:forEach var="agentCountry" items="${baseInfo.customerView.agentCountry}">
		        		<p class="form-control-static col-md-3" id="agentCountry">${agentCountry.i18nName} </p>
	        		</c:forEach>
	        	</c:when>
	        	<c:otherwise>
	        		<p class="form-control-static col-md-3" id="agentCountry">--</p>
	        	</c:otherwise>
        	</c:choose>  
          <label class="control-label col-md-2">代理地区：</label>
          <p class="form-control-static col-md-4" id="agentArea">${baseInfo.customerView.agentRegional.i18nName}</p>
      </div>
  </div>
    <h5 class="bdt ptb10 pl10 ">所属合同</h5> 
    <div class="form-group">
    <label class="control-label col-md-2">是否有所属合同：</label>
    <div class="col-md-3">
      <label class="radio radio-inline">
        <input type="radio" value="1" name="hasContract" <c:if test="${baseInfo.hasContract==true}">checked="checked"</c:if> />  
        是
      </label>
      <label class="radio radio-inline">
        <input type="radio" value="0" name="hasContract" <c:if test="${baseInfo.hasContract==false}">checked="checked"</c:if> />  
        否
      </label>
    </div>
    </div>
    <div id="agentTypeBox" <c:if test="${baseInfo.hasContract==false}">class="hide"</c:if>>
        <div class="form-group">
          <label class="control-label col-md-2" for="registerTime">合同编号：</label>
          <div class="col-md-2">
              <input type="text" class="form-control" id="contractNumber" name="contract.number" 
              	value="${baseInfo.contract.number}" placeholder="合同编号" />  
          </div>
          <div class="col-md-3 help-block"></div>
        </div>
        <div id="contractInfo" <c:if test="${empty baseInfo.contract.number}">class="hide"</c:if>>
              <div class="form-group">
                  <label class="control-label col-md-2" for="registerTime" >合同概要：</label>
                  <p class="form-control-static col-md-4" id="contractSummary">${baseInfo.contract.summary}</p>
              </div>
              <div class="form-group">
                  <label class="control-label col-md-2" for="registerTime" >合同类型：</label>
                  <p class="form-control-static col-md-4" id="contractType">${baseInfo.contract.category}</p>
              </div>
              <div class="form-group">
                  <label class="control-label col-md-2" for="belongSales" >合同有效期：</label>
                  <p class="form-control-static col-md-4" id="contractexpDate">
                  	<fmt:formatDate value="${baseInfo.contract.beginDate}" pattern="dd-MM-yyyy"/>~
        			<fmt:formatDate value="${baseInfo.contract.endDate}" pattern="dd-MM-yyyy"/>
                  </p>
              </div>
        </div>
    </div>
    <h5 class="bdt ptb10 pl10 ">所属合同</h5>
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">广告预算：</label>
        <div class="col-md-2">
            <div class="input-group">
                <span class="input-group-addon">$</span>
                <input type="text" class="form-control" id="" name="advertiseSolutionView.advertiseSolution.budget" 
                value="${baseInfo.advertiseSolutionView.advertiseSolution.budget}"/>
            </div>  
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
    <div class="form-group">
        <label class="control-label col-md-2" for="registerTime">投放周期：</label>
        <div class="col-md-2">
            <input type="text" class="form-control date-time" id="periodStart" name="advertiseSolutionView.advertiseSolution.startTime" placeholder="" 
            	value="<fmt:formatDate value="${baseInfo.advertiseSolutionView.advertiseSolution.startTime}" pattern="dd-MM-yyyy"/>"/>
        </div>
        <label class="pull-left control-label">-</label>
        <div class="col-md-2">
            <input type="text" class="form-control date-time" id="periodEnd" name="advertiseSolutionView.advertiseSolution.endTime" placeholder="" 
            value="<fmt:formatDate value="${baseInfo.advertiseSolutionView.advertiseSolution.endTime}" pattern="dd-MM-yyyy"/>"/>
        </div>
        <div class="col-md-3 help-block"></div>
    </div>
</form>