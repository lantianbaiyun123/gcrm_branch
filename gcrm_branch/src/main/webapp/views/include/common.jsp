<%@page import="com.baidu.gcrm.common.GcrmConfig"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="inc.jsp"%>
<%@ taglib prefix="gcrm" uri="http://gcrm.baidu.com/tags"%>
<script>
var GCRM = GCRM||{};
GCRM.constants = GCRM.constants || {};
GCRM.constants.CONTEXT = "<%=basePath%>";
GCRM.constants.CURRENT_USER_NAME = {"realname":"${SESSION_CURRENT_USER.realname}","ucid":"${SESSION_CURRENT_USER.ucid}"};
GCRM.constants.CMS_HOME_URL="<%=(String)application.getAttribute("CMS_HOME_URL")%>";
GCRM.messages =  <gcrm:message/>;
GCRM.rights =  <gcrm:rights/>;
GCRM.ownerOperFuncs =  <gcrm:ownerOperFuncs/>;
</script>
