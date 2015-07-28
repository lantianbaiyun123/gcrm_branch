<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
    <title>Hello World!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
     <%@include file="../include/common.jsp" %>
    <link type="text/css" rel="Stylesheet" href="<%=basePath%>resources/css/signin.css" media="all"/>
</head>
<body>
<%@include file="../include/header.jsp" %>
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

    <form class="form" action='<%=basePath%>j_spring_security_check' method="post">
        <h2 class="form-heading">Please Sign In</h2>
        <c:if test="${not empty param['error']}">
            <div class="alert alert-error">
                Sign in error. Please try again.
            </div>
        </c:if>
        <input type="text" class="input-block-level" placeholder="User name" name="j_username"/>
        <input type="password" class="input-block-level" placeholder="Password" name="j_password"/>
        <button class="btn btn-large btn-primary" type="submit">Sign In</button>
        <p class="form-text">New here? <a href='<%=basePath%>/preSignUp'>Sign Up.</a></p>
    </form>
</div>
</body>
</html>
