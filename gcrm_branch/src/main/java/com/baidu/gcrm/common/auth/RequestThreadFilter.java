package com.baidu.gcrm.common.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhanglei35
 * 获取HttpServletRequest，设置到ThreadLocal中<br/>
 * 在用户权限filter生效后，需要删除此filter, 并在用户权限认证filter里设置threadlocal
 */
public class RequestThreadFilter implements Filter {

	public RequestThreadFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		RequestThreadLocal.setRequestThreadLocal((HttpServletRequest) request); 
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
