<!--资质认证材料-->
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<table id="materialsTable" class="table table-gcrm">
    <thead>
        <tr>
            <th class="col-md-2"><s:message code="materials.type"/></th>
            <th class="col-md-3"><s:message code="materials.filecontent"/></th>
            <th class="col-md-3"><s:message code="materials.progress"/></th>
            <th class="col-md-1"><s:message code="materials.operation"/></th>
        </tr>
    </thead>
    <tbody>
      	<c:forEach items="${customerBean.attachments}" var="attachment" varStatus="attachmentStatus">
 			<c:choose>  
			<c:when test="${not empty attachment.tempUrl or not empty attachment.url}"> 
				<tr>
	           	<td class="col-md-2">
	                <select id="typeSelect" class="form-control" name="attachments[${attachmentStatus.index}].type">
	                    <option value="0" <c:if test="${attachment.type==0}">selected="selected"</c:if> ><s:message code="${attachmentTypes[0]}"/></option>
	                    <option value="1" <c:if test="${attachment.type==1}">selected="selected"</c:if> ><s:message code="${attachmentTypes[1]}"/></option>
	                    <option value="2" <c:if test="${attachment.type==2}">selected="selected"</c:if> ><s:message code="${attachmentTypes[2]}"/></option>
	                    <option value="3" <c:if test="${attachment.type==3}">selected="selected"</c:if> ><s:message code="${attachmentTypes[3]}"/></option>
	                </select>
	            </td>    
				
				<td>
					<input id="attachments[${attachmentStatus.index}].name" type="hidden" name="attachments[${attachmentStatus.index}].name" value="${attachment.name}">
					<input id="attachments[${attachmentStatus.index}].url" type="hidden" name="attachments[${attachmentStatus.index}].url" value="${attachment.url}">
					<input id="attachments[${attachmentStatus.index}].tempUrl" type="hidden" name="attachments[${attachmentStatus.index}].tempUrl" value="${attachment.tempUrl}">
					<input id="attachments[${attachmentStatus.index}].id" type="hidden" name="attachments[${attachmentStatus.index}].id" value="${attachment.id}">
					<input id="attachments[${attachmentStatus.index}].exit" type="hidden" name="attachments[${attachmentStatus.index}].exit" value="${attachment.exit}">
					<input id="attachments[${attachmentStatus.index}].customerId" type="hidden" name="attachments[${attachmentStatus.index}].customerId" value="${attachment.customerId}">
					<input id="attachments[${attachmentStatus.index}].fieldName" type="hidden" name="attachments[${attachmentStatus.index}].fieldName" value="attachments[${attachmentStatus.index}].name">
						${attachment.name}
					</td>    
				<td>
					<div id="progress"> 
						<c:if test="${attachment.exit==false}"><div class="bar" style="width: 100%;"></div></c:if>    
						<c:if test="${attachment.exit==true}"><div ><s:message code="materials.exist"/></div></c:if>   
					</div>
				</td>    
				<td>
					<a id="attachments[${attachmentStatus.index}].deletePath" href="###" 
						onclick="deleteFileBefore(this,{url:'${attachment.url}',id:${attachment.id}, tempUrl:'${attachment.tempUrl}'})"><s:message code="materials.delete"/>
					</a>
				</td>
       		</tr>
			</c:when>
			</c:choose>
      	</c:forEach>
        <tr>
            <td class="col-md-2">
                <select id="typeSelect" class="form-control" name="attachments[0].type">
                    <option value="0"><s:message code="materials.type.business.license"/></option>
                    <option value="1"><s:message code="materials.type.credit.reference"/></option>
                    <option value="2"><s:message code="materials.type.advertising.cooperation.prove"/></option>
                    <option value="3"><s:message code="materials.type.customer.agent.certificate"/></option>
                </select>
            </td>
            <td class="col-md-3">
                <span class="btn btn-success fileinput-button">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span><s:message code="materials.button.add"/></span>
                    <!-- The file input field used as target for the file upload widget -->
                    <input id="fileupload" type="file" name="attachments[0].name" data-url="../materials/doUploadFile" multiple>
                </span>
            </td>
            <td class="col-md-3"></td>
            <td class="col-md-1"></td>
        </tr>
    </tbody>
 </table>