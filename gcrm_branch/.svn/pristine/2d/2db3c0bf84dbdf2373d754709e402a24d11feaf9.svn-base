<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	org.springframework.context.ApplicationContext ac = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
	String type=request.getParameter("type")!=null?request.getParameter("type"):"1";
	com.baidu.gcrm.data.service.IFileTimerService service=(com.baidu.gcrm.data.service.IFileTimerService)ac.getBean("fileTimerServiceImpl");
	if(type.indexOf("1") >-1){
	service.generateADContentFileEveryFiveMinutes();
	}
		if(type.indexOf("2")>-1){
	service.generatePositionFileEveryFiveMinutes();
	}
		if(type.indexOf("3")>-1){
	service.readAndBackupFileTimer();
	}
		out.println("succeed!");
	%>
</body>
</html>