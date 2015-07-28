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
<title>Insert title here</title>
</head>
<script type="text/javascript">
function valuelistSubmit(button,dataId){
	
	var uri= $(button).attr('name')+"/"+$('#tableName').val();
		uri ="/gcrm/valuelist/"+uri;
		if(dataId!=undefined){
			uri = uri+"/"+dataId;
		}
	window.location=uri;
}

function valuelistCheckBeforeSubmit(button,dataId){
	if(confirm("确定要执行"+$(button).text()+"吗？")){
		valuelistSubmit(button,dataId)
	}
}

</script>
<body>
	<label>选择要查看表</label>
	<select id="tableName">
		<c:forEach items="${tableNames}" var="tn">
			<c:choose>
				<c:when test="${tn==tableName}"><option value="${tn}" selected="selected">${tn}</option></c:when>
				<c:otherwise><option value="${tn}">${tn}</option></c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<button type="button" name="getAllFromCache" onclick="valuelistSubmit(this)">查看缓存数据</button>
	<!-- <button type="button" name="getAll" onclick="valuelistSubmit(this)">加载数据</button> -->
	<button type="button" name="doRefresh" onclick="valuelistSubmit(this)">刷新缓存</button>
	<button type="button" name="gotoAdd" onclick="valuelistSubmit(this)">添加</button>
	<c:if test="${not empty message}"><p>${message}</p></c:if>
	<table  class="table">
		<thead>
			<tr>
				<th>index</th>
				<c:forEach items="${tableInfo}" var="column" varStatus="status">
					<th>${column.columnName}</th>
				</c:forEach>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty datas}">
					<c:forEach items="${datas}" var="row" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<c:forEach items="${tableInfo}" var="col">
								<td>${row[col.columnName]}</td>
							</c:forEach>
							<td>
								<button type="button" name="gotoUpdate" onclick="valuelistSubmit(this,${row['id']})">修改</button>
								<button type="button" name="delete" onclick="valuelistCheckBeforeSubmit(this,${row['id']})">删除</button>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<c:if test="${empty message}"><p>${tableName}表没有数据！</p></c:if>
				</c:otherwise>
			</c:choose>
		</tbody>
		
	</table>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>