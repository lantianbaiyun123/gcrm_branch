<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<table class="table">
	<thead>
	    <tr>
	        <th><s:message code="view.id"/></th>
	        <th><s:message code="resource.product"/></th>
	        <th><s:message code="resource.site"/></th>
	        <th><s:message code="view.type"/></th>
	        <th><s:message code="resource.position.name"/></th>
	        <th><s:message code="view.operate"/></th>
	    </tr>
	</thead>
	<tbody id="dataTable">
	    <c:forEach var="position" items="${positionPage.content}" varStatus="status">
	        <tr>
	            <td>${position.id}</td>
	            <td><c:out value="${position.adPlatformName}"/></td>
	            <td><c:out value="${position.siteName}"/></td>
	            <td><s:message code="resource.position.${position.type.value}"/></td>
	            <td><c:out value="${position.i18nName}"/></td>
	            <td>
	                <a refid="${position.id}" class="editPosition btn btn-default"><s:message code="view.edit"/></a>
	                <a refid="${position.id}" class="deletePosition btn btn-default"><s:message code="view.delete"/></a>
	            </td>
	        </tr>
	    </c:forEach>
    </tbody>            
</table>
<fis:page currPage="${positionPage.number}" totalCount="${positionPage.totalElements}" pageSize="${positionPage.size}" formId="positionSearchFrom"/>
<form action="position/query" id="positionSearchFrom">
</form>
               