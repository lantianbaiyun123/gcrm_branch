<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List,java.util.Map,java.util.Set,java.io.IOException"%>
<!DOCTYPE html>
<html>
    <head>
    <title>Magellan</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/gcrm/resources/vendor/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="/gcrm/resources/vendor/select2-3.4.5/select2.css">
    <link rel="stylesheet" href="/gcrm/resources/app/app.css">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="/gcrm/resources/lib/js/html5shiv.min.js"></script>
      <script src="/gcrm/resources/lib/js/respond.min.js"></script>
      <script src="/gcrm/resources/lib/js/es5-shim.js"></script>
    <![endif]-->
    <%
    Map<String, List<String>> userRoleMap = (Map<String, List<String>>)request.getAttribute("data");
    try {
		Set<String> roleSet = userRoleMap.keySet();
		for (String roleName : roleSet) {
			out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.print("<b>" + roleName  + "</b><br><br>");
			List<String> userList = userRoleMap.get(roleName);
			out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			for (String userInfo : userList) {
				String [] uInfo = userInfo.split(",");
//				out.print(uInfo[0] + "-" + uInfo[0] + "-" + uInfo[1]);
				out.print("<a href='/gcrm/login_debug/"+uInfo[0]+"'>"+uInfo[1]+"</a> ["+uInfo[2]+"] &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ");
			}
			out.print("<br/>------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------<br/>");
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
    %>
    </head>
</html>