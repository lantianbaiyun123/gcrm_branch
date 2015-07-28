<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
   