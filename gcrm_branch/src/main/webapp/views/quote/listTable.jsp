<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fis" uri="http://gcrm.baidu.com/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<table class="table">
      <thead>
          <tr>
              <th>id</th>
              <th>广告平台</th>
              <th>站点</th>
              <th>计费模式</th>
              <th>刊例价</th>
              <th>我方报价</th>
              <th>客户报价</th>
              <th>开始时间</th>
              <th>结束时间</th>
              <th>操作</th>
              
              
          </tr>
      	</thead>
      <tbody id="dataTable">
      	<c:forEach items="${page.content}" var="quote" varStatus="quotetatus">	     
			<tr>
               <td>
                  	<c:out value="${quote.id}"/>
               </td>
               <td>
                  	<c:out value="${quote.advertisingPlatformName}"/>
               </td>
               <td>
                  	<c:out value="${quote.siteName}"/>
               </td>
               <td>
                  	<c:out value="${quote.billingModelName}"/>
               </td>
               <td>
               	<c:choose>
               		<c:when test="${quote.billingModelType==0}">
               			<c:out value="${quote.publishPrice}"/>
               		</c:when>	
               		<c:otherwise>
               			<c:out value="--"/>
               		</c:otherwise>
               	</c:choose>
               </td>
               <td>
               	<c:choose>
               		<c:when test="${quote.billingModelType==1}">
               			<c:out value="${quote.ratioMine}"/>
               		</c:when>	
               		<c:otherwise>
               			<c:out value="--"/>
               		</c:otherwise>
               	</c:choose>
               </td>
               <td>
               	<c:choose>
               		<c:when test="${quote.billingModelType==1}">
               			<c:out value="${quote.ratioThird}"/>
               		</c:when>	
               		<c:otherwise>
               			<c:out value="--"/>
               		</c:otherwise>
               	</c:choose>
               </td>
               <td><fmt:formatDate value="${quote.startTime}" pattern="yyyy-MM-dd"/></td>
               <td><fmt:formatDate value="${quote.endTime}" pattern="yyyy-MM-dd"/></td>
               
               <td>
                  	<a target="_blank" class="btn btn-primary" href="<c:url value='/quote/preUpdate/${quote.id}'/>">编辑</a>
                  	<input type="button" class="btn btn-primary" onclick="del(${quote.id})" value="删除"/>
               </td>
           </tr>
   		</c:forEach>
      </tbody>
  </table>
  <fis:page currPage="${page.number+1}" totalCount="${page.totalElements}" pageSize="${page.size}" formId="quoteSearchFrom"/>