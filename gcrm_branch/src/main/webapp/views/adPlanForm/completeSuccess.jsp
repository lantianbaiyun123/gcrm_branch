<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.center{ width:960px; height:auto; margin:0px auto; min-height:450px;}
.cg1{ width:751px; height:auto; overflow:hidden; background-color:#e4f5c1; background-image:url(../images/duigou.png); background-repeat:no-repeat; background-position:80px 20px; border:1px solid #c6dd8f;  border-radius: 3px 3px 3px 3px; padding:43px 0 23px 207px }
.cg1_b{ width:751px; height:30px; font-size:20px; font-family:"微软雅黑";  color:#618b00; margin-bottom:10px;}
.cg1_c{ width:751px; height:30px; font-size:16px; font-family:"微软雅黑";  color:#618b00; padding-left:40px;}
.cg1_a{ width:751px; height:30px; margin-top:10px; }
.cg1_a b{ color:#333; font-weight:normal;}
.cg1_a strong{ color:#999; font-weight:normal;}
</style>
</head> 
<body>
	<div class="center">
		<font color="#333">

	    	<div class="cg1"> 
	    	<c:if test="${fn:length(activities) == 0}">
	    	<div class="cg1_b"><b>任务已提交</b>！流程已到最后一个环节。
			</div>
			</c:if>
			
	    	<c:if test="${fn:length(activities) == 1}">
	    	<div class="cg1_b"><b>任务已提交</b>！流程下一环节：
					<c:forEach items="${activities}" var="activity">
					${activity.activityName }   &nbsp;${activity.performer}
					</c:forEach>
			</div>
			</c:if>
	    
			<c:if test="${fn:length(activities) > 1}">
				<div class="cg1_b"><b>任务已提交</b>！流程下一环节：</div>
					<c:forEach items="${activities}" var="activity">
					<div class="cg1_c">
						${activity.activityName }   &nbsp;${activity.performer}
					</div>
					</c:forEach>
			</c:if>
	   		</div>
	</font></div>
</body>
</html>