<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <%@include file="../include/common.jsp" %>
<link type="text/css" rel="Stylesheet" href="<%=basePath%>resources/css/signin.css" media="all"/>
<title>新增界面</title>
</head>
<script type="text/javascript">

</script>
<body>
	<label>当前表：<span style="font-size: 20px;position: ">${tableName}</span></label>
	<form id="valuelist" method="post" action="<c:url value='/valuelist/doUpdate'></c:url>">
		<input type="hidden" id="dataId" name="dataId" value="${dataId}"/>
		<input type="hidden" id="tableName" name="tableName" value="${tableName}"/>
		<input type="hidden" id="cacheKey" name="cacheKey" value="${cacheKey}"/>
		<table border="1" style="border-collapse: collapse">
		<thead>
			<tr><th>列名</th><th>值</th><th>类型</th></tr>
		</thead>
		<tbody>
			<c:forEach items="${tableInfo}" var="colName">
				<tr>
				
				<c:choose>
					<c:when test="${colName.columnName=='id' }">
						<td><input type="hidden" id="${colName.columnName}_tlv" name="${colName.columnName}_tlv" 
						   value="${data[colName.columnName]}"/></td>
					</c:when>
					<c:otherwise>
						<td><label>${colName.columnName}</label></td>
						<td><input type="text" id="${colName.columnName}_tlv" name="${colName.columnName}_tlv" 
						   value="${data[colName.columnName]}"/></td>
						<td>${colName.columnType }</td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		<input type="submit" value="保存"/>
		<input type="reset" value="默认值"/>
	</form>
	
	<c:if test="${not empty message}">
		<div id="message"> ${message}</div>
	</c:if>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>