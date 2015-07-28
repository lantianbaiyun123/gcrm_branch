package com.baidu.gcrm.common.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.user.model.User;

public class RequestThreadLocal {
	private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();

	public static void setRequestThreadLocal(HttpServletRequest request) {
		requestThreadLocal.set(request);
	}

	public static User getLoginUser() {
		HttpServletRequest request = requestThreadLocal.get();
		HttpSession session = request == null ? null : request.getSession(false);
		User user =  session == null ? null : (User) session
				.getAttribute(SessionValueKey.SESSION_CURRENT_USER);
		if(user == null){
			LoggerHelper.info(RequestThreadLocal.class, "use the liyazhou test user!");
			return getTestUser();
		}
		return user;
	}
	
	private static User getTestUser(){
		User user = new User();
		user.setUsername("liuxiao006");
		user.setUcid(7426946L);
		user.setRealname("刘晓");
		user.setUuapName("liuxiao06");
		return user; 
	}
	
	public static Long getLoginUserId(){
		try {
			User user = getLoginUser();
			if(user!=null){
				return user.getUcid();
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getLoginUsername(){
		try {
			User user = getLoginUser();
			if(user!=null){
				return user.getUsername();
			}
		} catch (Exception e) {
		}
		return StringUtils.EMPTY;
	}
	
	public static String getLoginUuapName(){
		try {
			User user = getLoginUser();
			if(user!=null){
				return user.getUuapName();
			}
		} catch (Exception e) {
		}
		return StringUtils.EMPTY;
	}
	
	public static String getRequestIp() {
		HttpServletRequest request = requestThreadLocal.get();
		return request == null ? "" : request.getRemoteHost();
	}
	
	public static StringBuffer getRequestURL() {
		HttpServletRequest request = requestThreadLocal.get();
		return request == null ? new StringBuffer() : request.getRequestURL();
	}
	
	public static String getRequestURI() {
		HttpServletRequest request = requestThreadLocal.get();
		return request == null ? "" : request.getRequestURI();
	}
	
	public static LocaleConstants getLocale(){
		return requestThreadLocal.get()==null? LocaleConstants.zh_CN:
			LocaleConstants.getLocaleConstantsByName(
			       ServiceBeanFactory.getSessionLocaleResolver().resolveLocale(requestThreadLocal.get()).toString());
	}
	
	private static Object getObjectFromSession(String key){
		HttpServletRequest request = requestThreadLocal.get();
		HttpSession session = request == null ? null : request.getSession(false);
		return (session == null ? null : session.getAttribute(key));
	}
	
	/**
	 * 获取当前登录用户有权限的功能代码列表
	 * @return
	 */
	public static RightsUrlVO getLoginUserURLCodes(){
		return (RightsUrlVO) getObjectFromSession(SessionValueKey.SESSION_AUTH_URL_CODE);
	}
	
	/**
	 * 获取当前登录用户有权限的功能列表
	 * @return
	 */
	public static List<String> getLoginUserURLs(){
		return (List<String>) getObjectFromSession(SessionValueKey.SESSION_AUTH_URL);
	}
	
	public static List<RightsRole> getLoginUserRole(){
		return (List<RightsRole>) getObjectFromSession(SessionValueKey.SESSION_CURRENT_USER_ROLE);
	}
	
	public static String getContentPath() {
	    return requestThreadLocal.get().getContextPath();
	}
}
