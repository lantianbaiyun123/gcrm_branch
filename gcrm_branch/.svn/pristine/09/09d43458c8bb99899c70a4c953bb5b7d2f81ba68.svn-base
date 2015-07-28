<!--联系人信息-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			
<table class="table table-gcrm">
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
            <th class="w-50"><s:message code="view.operate"/></th>
        </tr>
    </thead>
    <tbody id="contactPersonTable">
        <tr>
            <td colspan="11" style="border:0px;">
            <c:choose>
			    <c:when test="${fn:length(customerBean.contacts) > 0}">
			        <input class="form-control" type="hidden" id="contactPersonIndex" value="${fn:length(customerBean.contacts)}">
			    </c:when>
			    <c:otherwise>
			        <input class="form-control" type="hidden" id="contactPersonIndex" value="0">
			    </c:otherwise>
			</c:choose>
			</td>
        </tr>
        
    	<c:forEach items="${customerBean.contacts}" var="contact" varStatus="contactStatus">
    		<c:set var="nameKey" value="contacts[${contactStatus.index}].name"/>
    		<c:set var="mobileKey" value="contacts[${contactStatus.index}].mobile"/>
    		<c:set var="telephoneKey" value="contacts[${contactStatus.index}].telephone"/>
    		<c:set var="emailKey" value="contacts[${contactStatus.index}].email"/>
  			<tr>
            	<td <c:if test="${!empty errors[nameKey]}">class='has-error'</c:if>>
            		<input class="form-control" type="text" name="contacts[${contactStatus.index}].name" maxlength="512" value="${contact.name}">
           		</td>
                <td>
                	<select class="form-control col-md-12" name="contacts[${contactStatus.index}].gender">
                   		<option <c:if test="${contact.gender == '1'}">selected="selected"</c:if> value="1"><s:message code="contact.sex.male"/></option>
                   		<option <c:if test="${contact.gender == '2'}">selected="selected"</c:if> value="2"><s:message code="contact.sex.female"/></option>
                   		<option <c:if test="${contact.gender == '3'}">selected="selected"</c:if> value="3"><s:message code="contact.sex.other"/></option>
                   	</select>
               	</td>
                <td><input class="form-control" type="text" maxlength="128" name="contacts[${contactStatus.index}].positionName" value="${contact.positionName}"></td>
                <td><input class="form-control" type="text" maxlength="128" name="contacts[${contactStatus.index}].superiorPosition" value="${contact.superiorPosition}"></td>
                <td><input class="form-control" type="text" maxlength="128" name="contacts[${contactStatus.index}].department" value="${contact.department}"></td>
                <td <c:if test="${!empty errors[mobileKey]}">class='has-error'</c:if>><input class="form-control" type="text" maxlength="32" name="contacts[${contactStatus.index}].mobile" value="${contact.mobile}" placeholder="Zone-number"></td>
                <td <c:if test="${!empty errors[telephoneKey]}">class='has-error'</c:if>><input class="form-control" type="text" maxlength="32" name="contacts[${contactStatus.index}].telephone" value="${contact.telephone}" data-pattern="[0-9\-]+" placeholder="Zone-number"></td>
                <td <c:if test="${!empty errors[emailKey]}">class='has-error'</c:if>><input class="form-control" type="text" maxlength="128" name="contacts[${contactStatus.index}].email" value="${contact.email}" data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" placeholder="ex.john@company.com"></td>
                <td>
                	<input class="form-control" type="checkbox" value="ENABLE" name="contacts[${contactStatus.index}].isLegalPerson" <c:if test="${contact.isLegalPerson == 'ENABLE'}">checked="checked"</c:if>>
				</td>
			 	<td>
                	<input class="form-control" type="checkbox" value="ENABLE" name="contacts[${contactStatus.index}].isDecisionMaker" <c:if test="${contact.isDecisionMaker == 'ENABLE'}">checked="checked"</c:if>>
				</td>
				<td>
					<a class="form-control-static" onclick="return delLine(this);" href="###"><s:message code="view.delete"/></a>
				</td>
        	</tr>
       	</c:forEach>

    </tbody>
</table>
<div>
  	<button type="button" id="contactPersonAddBtn" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span><s:message code="contact.addLine"/></button>
</div>

<fis:script>
	function addLine(isFocus){
		var currRowIndex = parseInt($("#contactPersonIndex").val());
		if(currRowIndex > 9){
			alert("add too much!");
			return false;
		}
		
		var htmlStr = '<tr>';
		htmlStr += '<td><input class="form-control" type="text" maxlength="512" name="contacts[' + currRowIndex + '].name"></td>';
		htmlStr += '<td><select class="form-control col-md-12" name="contacts[' + currRowIndex + '].gender">'+
					'<option value="1"><s:message code="contact.sex.male"/></option>'+
					'<option value="2"><s:message code="contact.sex.female"/></option>'+
					'<option value="3"><s:message code="contact.sex.other"/></option>'+
					'</select></td>';
					
		htmlStr += '<td><input class="form-control" type="text" maxlength="128" name="contacts[' + currRowIndex + '].positionName"></td>';	
		htmlStr += '<td><input class="form-control" type="text" maxlength="128" name="contacts[' + currRowIndex + '].superiorPosition"></td>';	
		htmlStr += '<td><input class="form-control" type="text" maxlength="128" name="contacts[' + currRowIndex + '].department"></td>';	
		htmlStr += '<td><input class="form-control" type="text" maxlength="32" name="contacts[' + currRowIndex + '].mobile" placeholder="Zone-number"></td>';
		htmlStr += '<td><input class="form-control" type="text" maxlength="32" name="contacts[' + currRowIndex + '].telephone" data-pattern="[0-9]+\-[0-9]+" placeholder="Zone-number"></td>';	
		htmlStr += '<td><input class="form-control" type="text" maxlength="128" name="contacts[' + currRowIndex + '].email"  data-pattern="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$" placeholder="ex.john@company.com"></td>';
		htmlStr += '<td><input class="form-control" type="checkbox" value="ENABLE" name="contacts[' + currRowIndex + '].isLegalPerson"></td>';	
		htmlStr += '<td><input class="form-control" type="checkbox" value="ENABLE" name="contacts[' + currRowIndex + '].isDecisionMaker"></td>';
		htmlStr += '<td><a class="form-control-static" onclick="return delLine(this);" href="###"><s:message code="view.delete"/></a></td>';
		htmlStr += '</tr>';
		
		$("#contactPersonTable").append(htmlStr);
		$("#contactPersonTable select:last").selectpicker({});
		if(isFocus){
			var currName = "#contactPersonTable input[name='contacts["+currRowIndex+"].name']";
			$(currName).focus();
		}
		$("#contactPersonIndex").val(currRowIndex+1);
		
		$('form.form-horizontal').validate($.extend({}, $.gcrm.validateConfig, {onChange : true}));
	}
	
	function delLine(thisObj){
		var currRowIndex = parseInt($("#contactPersonIndex").val());
		if(currRowIndex < 2){
			alert("can't delete!");
			return false;
		}
		$(thisObj).parent().parent().remove();
		$("#contactPersonIndex").val(currRowIndex-1);
		return true;
	}
	
	
	$(document).ready(function(){
		var currRowIndex = parseInt($("#contactPersonIndex").val());
		if(currRowIndex < 1){
			addLine(false);
		}
		$("#contactPersonAddBtn").click(function(){
			addLine(true);
		});
		
	});
	
</fis:script>