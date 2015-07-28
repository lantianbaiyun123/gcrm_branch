<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form class="form-horizontal" id="saveContactInfoForm" action="contact/save" method="POST">
    
    <input class="form-control" id="customerNumber" type="hidden" name="customer.customerNumber" value="${customerNumber}">
	<c:choose>
	    <c:when test="${fn:length(customerBean.contacts) > 0}">
	        <input class="form-control" type="hidden" id="contactPersonIndex" value="${fn:length(customerBean.contacts)}">
	    </c:when>
	    <c:otherwise>
	        <input class="form-control" type="hidden" id="contactPersonIndex" value="0">
	    </c:otherwise>
	</c:choose>
	
	<table class="table table-gcrm" id="contactsEdit">
	    <thead>
	        <tr>
	            <th><s:message code="contact.name"/></th>
	            <th class="w-80"><s:message code="contact.sex"/></th>
	            <th><s:message code="contact.position"/></th>
	            <th><s:message code="contact.superior.position"/></th>
	            <th><s:message code="contact.dept"/></th>
	            <th><s:message code="contact.mobile"/></th>
	            <th><s:message code="contact.telephone"/></th>
	            <th><s:message code="view.email"/></th>
	            <th class="w-50"><s:message code="contact.legalPerson"/></th>
	            <th class="w-50"><s:message code="contact.decisionMaker"/></th>
	            <th class="w-80"><s:message code="view.operate"/></th>
	        </tr>
	    </thead>
	    <tbody id="contactPersonTable">
	        <c:forEach items="${contactList}" var="contact" varStatus="contactStatus">
	            <c:set var="nameKey" value="contacts[${contactStatus.index}].name"/>
	            <c:set var="mobileKey" value="contacts[${contactStatus.index}].mobile"/>
	            <c:set var="telephoneKey" value="contacts[${contactStatus.index}].telephone"/>
	            <c:set var="emailKey" value="contacts[${contactStatus.index}].email"/>
	            <tr>
	                <td>
	                    <input class="form-control" type="text" namesuffix="name" name="name" maxlength="512" value="${contact.name}" disabled>
	                </td>
	                <td>
	                    <select class="form-control J_sex" namesuffix="gender" name="gender" disabled>
	                        <option <c:if test="${contact.gender == '1'}">selected="selected"</c:if> value="1"><s:message code="contact.sex.male"/></option>
	                        <option <c:if test="${contact.gender == '2'}">selected="selected"</c:if> value="2"><s:message code="contact.sex.female"/></option>
	                        <option <c:if test="${contact.gender == '3'}">selected="selected"</c:if> value="3"><s:message code="contact.sex.other"/></option>
	                    </select>
	                </td>
	                <td><input class="form-control" type="text" maxlength="128" namesuffix="positionName" name="positionName" value="${contact.positionName}" disabled></td>
	                <td><input class="form-control" type="text" maxlength="128" namesuffix="superiorPosition" name="superiorPosition" value="${contact.superiorPosition}" disabled></td>
	                <td><input class="form-control" type="text" maxlength="128" namesuffix="department" name="department" value="${contact.department}" disabled></td>
	                <td><input class="form-control" type="text" maxlength="32" namesuffix="mobile" name="mobile" value="${contact.mobile}" placeholder="Zone-number" disabled></td>
	                <td><input class="form-control" type="text" maxlength="32" namesuffix="telephone" name="telephone" value="${contact.telephone}" validateref="phone" placeholder="Zone-number" disabled></td>
	                <td><input class="form-control" type="text" maxlength="128" namesuffix="email" name="email" value="${contact.email}"  validateref="email" placeholder="ex.john@company.com" disabled></td>
	                <td>
	                    <input class="form-control" type="checkbox" value="ENABLE" namesuffix="isLegalPerson" name="isLegalPerson" <c:if test="${contact.isLegalPerson == 'ENABLE'}">checked="checked"</c:if> disabled>
	                </td>
	                <td>
	                    <input class="form-control" type="checkbox" value="ENABLE" namesuffix="isDecisionMaker" name="isDecisionMaker" <c:if test="${contact.isDecisionMaker == 'ENABLE'}">checked="checked"</c:if> disabled>
	                    <input class="form-control" type="hidden" namesuffix="id" name="id"  value="${contact.id}" disabled>
	                </td>
	                <td>
	                    <a class="form-control-static J_delContact" refercontactid="${contact.id}" href="###"><s:message code="view.delete"/></a> <a class="form-control-static J_editContact" refercontactid="${contact.id}"  href="###"><s:message code="view.edit"/></a>
	                </td>
	            </tr>
	        </c:forEach>
	
	    </tbody>
	</table>
	
	<div>
	    <button type="button" id="J_addContactBtn" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span><s:message code="contact.addLine"/></button>
	    <button type="button" id="J_submitContact" class="btn btn-info"><s:message code="view.save"/></button>
	</div>
</form>
<script type="text/javascript">
	
	moduleConfig.detailConcatInfo.success = function(){
		if(!$("#detailConcatInfo").hasClass("has-loaded")){
	        $("#detailConcatInfo").addClass("has-loaded");
	        $.gcrm.editContact($("#detailConcatInfo"));
	        
	    }
		$.gcrm.dealCancellation("#J_addContactBtn,#J_submitContact,#contactsEdit thead tr th:last-child,#contactsEdit tbody tr td:last-child");
    	//初始化联系人10行的时候的提示信息
    	initContactPromptBox();
	    $("#detailConcatInfo select").addClass("col-md-12").selectpicker({});
	};
</script>