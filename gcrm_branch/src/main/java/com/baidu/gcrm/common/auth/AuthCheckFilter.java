package com.baidu.gcrm.common.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.common.ServiceBeanFactory;


public class AuthCheckFilter implements Filter {
	private String errorPagePath;
	private String ajaxErrorCode;
	private String[] excludeUrls;
	
	public void init(FilterConfig config) throws ServletException {
		String excludeUrl = config.getInitParameter("excludeURL");
		excludeUrls = excludeUrl.split(";");
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(ArrayUtils.isNotEmpty(excludeUrls)){
			String requestUrl =  req.getRequestURI();
			for(String excludeUrl : excludeUrls){
				if(requestUrl.contains(excludeUrl)){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		// 获得访问路径
		String reqURI = req.getRequestURI();
		if (null != reqURI && reqURI.length() > 0) {
			reqURI = reqURI.substring(6, reqURI.length());
		}
		System.out.println("[AuthCheckFilter] : requestURI: " + reqURI);
		// 获得session中的URL列表
		HttpSession session = req.getSession();
		List<String> excludeUrList = (List<String>) session.getAttribute(SessionValueKey.SESSION_AUTH_URL);
		Long ucId = RequestThreadLocal.getLoginUserId();
		// 说明还没有过PassportAuthenticationFilter
		if (null == ucId) {
			System.out.println("[AuthCheckFilter] : login user ucId is null....");
			chain.doFilter(request, response);
			return;
		}
		if (null == excludeUrList) {
			System.out.println("[AuthCheckFilter] : SESSION_AUTH_URL is empty");
			IUserRightsService userRightsService = ServiceBeanFactory.getUserRightsService();
			List<String> userURLs = userRightsService.findUserUrls(ucId);
			if (null != userURLs) {
				for (String userUrl : userURLs) {
					System.out.println("[AuthCheckFilter] : user url: " + userUrl);
				}
			}
			List<String> totalUrlList = new ArrayList<String>();
			// 如果用户没有配置URL权限，则对所有URL都有权限
			// 否则获取排除的URL
			if (null != userURLs && userURLs.size() > 0) {
				totalUrlList = userRightsService.findAllFuncUrls();
				totalUrlList.removeAll(userURLs);
			}
			excludeUrList = totalUrlList; 
			session.setAttribute(SessionValueKey.SESSION_AUTH_URL, excludeUrList);
		}
		// 判断是否有权限访问指定路径
		boolean canNotExecute = false;
		
		SortedSet<String> authSet = new TreeSet<String>();
		if (null != excludeUrList)
		for (String url : excludeUrList) {
			authSet.add(url + "*");
			System.out.println("[AuthCheckFilter] : exclude url: "+url);
		}
		// 如果请求路径为空，则可以访问
		canNotExecute =  reqURI.equals("") ? false : UrlUtils.urlMatch(authSet, reqURI);
		
		if (canNotExecute) {
			System.out.println("[AuthCheckFilter] : can not access url: " + reqURI);
		}else {
			System.out.println("[AuthCheckFilter] : passed url: " + reqURI);
		}
		chain.doFilter(request, response);
		return;
		/*
		if (!canNotExecute) {
			chain.doFilter(request, response);
		} else {
			if (req.getParameter("isAjax") != null) {
				String template = "{\"status\":%s,\"statusInfo\":\"%s\"}";
				printMessage(res, template, ajaxErrorCode, errorPagePath);
				return;
			} else {
				if (!res.isCommitted()) {
					res.sendRedirect("/gcrm/errorPage");
				}
				return;
			}
		}
		*/
	}

	private void printMessage(HttpServletResponse httpResponse, String template, Object... args) throws IOException {
		PrintWriter writer = httpResponse.getWriter();
		writer.write(String.format(template, args));
		writer.flush();
		writer.close();
	}
	 
	@Override
	public void destroy() {}
	
	public void setErrorPagePath(String errorPagePath) {
		this.errorPagePath = errorPagePath;
	}

	public void setAjaxErrorCode(String ajaxErrorCode) {
		this.ajaxErrorCode = ajaxErrorCode;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
