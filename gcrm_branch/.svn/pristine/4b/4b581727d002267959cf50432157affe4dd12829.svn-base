<!--公司标题-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="companyHeader">
	<h3 id="viewCompanyName">${customerView.customer.companyName}
		<small style="margin-left: 1em;color: #666666;">
			(<s:message code="gcrm.title.company.status.${customerView.customer.companyStatus}"/>)
		</small>
	</h3>
	<div class="pull-right"><a href="#"><s:message code="gcrm.title.customer.approve.history"/></a> | <a href="#"><s:message code="gcrm.title.customer.modify.history"/></a></div>
	<div class="clear-float"></div>
</div>

<c:choose>
	<c:when test="${customerView.approvalStatus == 'saving'}">
		<div class="alert alert-info alert-dismissable">
		   <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		      <p>客户信息修改，需重新进行资质审核</p>
		</div> 
	</c:when>
	<c:when test="${customerView.approvalStatus == 'approving'}">
		<div class="alert alert-info alert-dismissable">
		   <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		    <p> 客户信息审核中 北京时间: 2013.11.23 23:00审批已经到达 卢婷婷</p>
		</div>
	</c:when> 
	<c:when test="${customerView.approvalStatus == 'refused'}">
		<div class="alert alert-danger  alert-dismissable">
		   <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		   <p>资质审核 <b>未通过</b> 北京时间: 2013.11.23 23:00被 卢婷婷 打回，打回原因: 营业执照未上传</p>
		</div>
	</c:when>
	<c:when test="${customerView.approvalStatus == 'approved'}">
		<div class="alert alert-success  alert-dismissable">
		   <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		   <p>资质审核 <b>已通过</b></p>
		</div>
	</c:when>
</c:choose>

<div class="btn-group btn-group-justified">
        <a class="btn btn-default disabled" role="button">
        <s:message code="customer.title.customerNumber"/>:
        <c:choose>
        	<c:when test="true">
				${customerView.customer.customerNumber}
			</c:when>
			<c:otherwise>
				-- 
			</c:otherwise>
        </c:choose>  
        </a>
        <a class="btn btn-default disabled" role="button"><s:message code="customer.approvalStatus"/>:<s:message code="customer.approvalStatus.${customerView.approvalStatus}"/></a>
        <a class="btn btn-default disabled" role="button"><s:message code="customer.title.createTime"/>:<fmt:formatDate value="${customerView.customer.createTime}" pattern="yyyy-MM-dd" /></a>
		<a class="btn btn-default disabled" role="button"><s:message code="customer.title.createOperator"/>:${customerView.customer.createOperator}</a>
</div> 
<style>
	.companyHeader{
		padding-bottom:20px;
	}
	.companyHeader a.btn{
		background-color:#f8f8f8;
	}
	.companyHeader h3{
		display:inline-block;
		margin:0;
	}
	.clear-float{
		clear:both;
	}
</style>