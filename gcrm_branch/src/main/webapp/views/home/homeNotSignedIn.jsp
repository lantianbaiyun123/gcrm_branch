<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>Hello World!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="../include/common.jsp"%>
</head>
<body>
<%@include file="../include/header.jsp"%>
<!-- Page content -->
<div class="container">
    <% /* Show a message. See support.web package */ %>
    <c:if test="${not empty message}">
        <c:choose>
            <c:when test="${message.type == 'WARNING'}">
                <c:set value="" var="alertClass"/>
            </c:when>
            <c:otherwise>
                <c:set value="alert-${fn:toLowerCase(message.type)}" var="alertClass"/>
            </c:otherwise>
        </c:choose>
        <div class="alert ${alertClass}">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <% /* Display a message by its code. If the code was not found, it will be displayed as default text */ %>
            <s:message code="${message.message}" arguments="${message.args}" text="${message.message}"/>
        </div>
    </c:if>
    
    <div class="hero-unit">
        <h1>Home page</h1>

        <p>Welcome to the Spring MVC Quickstart application! </p>

        <p>
            <a href='<%=basePath%>preSignUp' class="btn btn-primary btn-large"> Sign Up </a>
        </p>
    </div>
</div>
<!-- End of page content -->

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>