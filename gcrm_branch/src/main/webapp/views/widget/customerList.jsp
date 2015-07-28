<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
        <table class="table tableBorderBottom">
            <thead>
                <tr>
                    <th><s:message code="customer.title.number"/></th>
                    <th><s:message code="customer.title.companyName"/></th>
                    <th><s:message code="customer.title.businessType"/></th>
                    <th><s:message code="customer.title.country"/></th>
                    <th><s:message code="customer.title.agentRegional"/></th>
                    <th><s:message code="customer.title.agentCompany"/></th>
                    <th><s:message code="customer.title.belongSales"/></th>
                    <th><s:message code="customer.approvalStatus"/></th>
                    <th><s:message code="view.operate"/></th>
                    
                </tr>
            </thead>
            <tbody id="dataTable">
            
	           	<c:forEach items="${page.content}" var="customer" varStatus="customerStatus">
	           	   <c:choose>
                       <c:when test="${customer.status == '2'}">
                           <%--<c:set var="disabledClass" value="class='text-muted'"/> --%>
                           <c:set var="disabledClass" value="style=color:#999999"/>
                           
                       </c:when>
                       <c:otherwise><c:set var="disabledClass" value=""/></c:otherwise>
                   </c:choose>
	           	   
	    			<tr>
	                    <td <c:out value="${disabledClass}"/>>
                            <c:choose>
                                <c:when test="${fn:length(customer.customerNumber) > 9}">
                                    <c:out value="${customer.customerNumber}"/>
                                </c:when>
                                <c:otherwise>--</c:otherwise>
                            </c:choose>
	                    </td>
	                    <td <c:out value="${disabledClass}"/> title="<c:out value="${customer.companyName}"/>">
                           <c:choose>
                                <c:when test="${fn:length(customer.companyName) > 8}">
                                    <c:out value="${fn:substring(customer.companyName,0,8)}.."/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${customer.companyName}"/>
                                </c:otherwise>
                            </c:choose>
	                    </td>
	                    <td <c:out value="${disabledClass}"/>>
                            <c:choose>
                                <c:when test="${!empty customer.businessTypeName}">
                                    <s:message code="customer.type.${customer.businessTypeName}"/>
                               </c:when>
                               <c:otherwise>--</c:otherwise>
                           </c:choose>
	                    </td>
	                    <td <c:out value="${disabledClass}"/>><c:out value="${customer.countryName}"/></td>
	                    <td <c:out value="${disabledClass}"/>>
	                       <c:choose>
                               <c:when test="${!empty customer.agentRegionalName}"><c:out value="${customer.agentRegionalName}"/></c:when>
                               <c:otherwise>--</c:otherwise>
                           </c:choose>
	                    </td>
                        <td <c:out value="${disabledClass}"/> title="<c:out value="${customer.agentCompanyName}"/>">
                           <c:choose>
                                <c:when test="${fn:length(customer.agentCompanyName) > 8}">
                                    <c:out value="${fn:substring(customer.agentCompanyName,0,8)}.."/>
                                </c:when>
                                <c:when test="${!empty customer.agentCompanyName}">
                                    <c:out value="${customer.agentCompanyName}"/>
                                </c:when>
                                <c:otherwise>
                                    --
                                </c:otherwise>
                            </c:choose>
                        </td>
                        
	                    <td <c:out value="${disabledClass}"/>><c:out value="${customer.belongSalesName}"/></td>
	                    <td <c:out value="${disabledClass}"/>>
	                       <c:choose>
	                           <c:when test="${!empty customer.approvalStatusName}">
	                               <s:message code="customer.approvalStatus.${customer.approvalStatusName}"/>
                               </c:when>
	                           <c:otherwise>--</c:otherwise>
	                       </c:choose>
                        </td>
	                    <td <c:out value="${disabledClass}"/>>
	                        <div class="btn-group">
	                          <button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown">
	                               <s:message code="customer.list.detail"/><span class="caret"></span>
	                          </button>
	                          <ul class="dropdown-menu" role="menu">
	                            <li><a referid="${customer.id}" class="viewCustomer" href="###"><s:message code="customer.list.view"/></a></li>
	                            
	                             <c:choose>
	                               <c:when test="${customer.status != '2'}">
	                               
	                                   <li refid="${customer.id}" refname="${customer.companyName}" 
	                                       refsales="${customer.belongSalesName}" class="moveSales">
	                                       <a href="###"><s:message code="customer.list.changeSales"/></a>
	                                   </li>
	                                   
	                                   <li referid="${customer.id}" class="invalidCustomer"><a href="###"><s:message code="customer.list.invalid"/></a></li>
	                                   
	                                   <c:if test="${customer.approvalStatus == '2' && customer.status == '0'}">
		                                   <li refid="${customer.id}" ><a href="#"><s:message code="customer.list.cancel"/></a></li>
	                                       <li refid="${customer.id}" ><a href="#"><s:message code="customer.list.alert"/></a></li>
	                                   </c:if>
	                                   
	                                   <c:if test="${customer.approvalStatus == '3' && customer.status == '1'}">
	                                       <li refid="${customer.customerNumber}" refName="${customer.companyName}" class="addAccount"><a href="#"><s:message code="customer.list.addAccount"/></a></li>
                                       </c:if>
	                               </c:when>
	                               
	                               <c:otherwise>
	                                   <c:if test="${customer.approvalStatus == '0'}">
	                                       <li refid="${customer.id}" class="restoreCustomer"><a href="###"><s:message code="customer.list.restore"/></a></li>
	                                   </c:if>
	                               </c:otherwise>
	                           </c:choose>
	                            
	                          </ul>
	                        </div>
	                    </td>
	                </tr>
		       	</c:forEach>
            </tbody>
        </table>

	<fis:page currPage="${page.pageNumber}" totalCount="${page.total}" pageSize="${page.pageSize}" formId="customerSearchFrom"/>
