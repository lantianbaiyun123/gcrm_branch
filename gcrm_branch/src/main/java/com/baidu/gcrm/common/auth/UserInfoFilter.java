package com.baidu.gcrm.common.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.rigel.crm.rights.bo.AuthInfo;
import com.baidu.rigel.crm.rights.bo.PosRoleInfo;
import com.baidu.rigel.crm.rights.bo.User;
import com.baidu.rigel.crm.rights.bo.UserPosRole;
import com.baidu.rigel.crm.rights.service.NewPositionExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewRoleExtServiceWrapper;
import com.baidu.rigel.crm.rights.web.support.SessionValueKey;

public class UserInfoFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(UserInfoFilter.class);
	
	private String[] excludeUrls;
	
	public void init(FilterConfig config) throws ServletException {
		String excludeUrl = config.getInitParameter("excludeURL");
		excludeUrls = excludeUrl.split(";");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		if(ArrayUtils.isNotEmpty(excludeUrls)){
			String requestUrl =  req.getRequestURI();
			for(String excludeUrl : excludeUrls){
				if(requestUrl.contains(excludeUrl)){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		// 获得UCID以及对应的session
		Long ucid = ThreadLocalInfo.getThreadUuid();
		if (ucid == null) {
			// 如果UCID为空，表明未部署CasFilter，此filter不进行任务处理
			log.warn("could not get ucid from threadlocal, maybe not config CasFilter correctly!");
			chain.doFilter(request, response);
			return;
		}
		RequestThreadLocal.setRequestThreadLocal(req);
		HttpSession session = req.getSession();
		// 如果session值有效，则跳过此Filter
		Long sessionCurPosId = (Long) session.getAttribute(SessionValueKey.SESSION_CURRENT_POS_ID);
		if (BooleanUtils.isTrue((Boolean) session.getAttribute(SessionValueKey.SESSION_CACHE_VALID))
				&& sessionCurPosId != null) {
			chain.doFilter(request, response);
			return;
		}
		
		User u = null;
		Integer regAppId = (Integer) session.getAttribute(SessionValueKey.SESSION_CURRENT_USER_APPID);
		if (regAppId != null) {
			u = ServiceBeanFactory.getAcctExtService()
					.getUserByUcid(ucid, regAppId);
		}else{
			throw new ServletException(String.format("ucid( %d ) not found the regAppid in session!", ucid));
		}
		if (u == null) {
			throw new ServletException(String.format("ucid( %d ) not exists in usercenter!", ucid));
		} else {
			session.setAttribute(SessionValueKey.SESSION_REALNAME, u.getRealname());
			session.setAttribute(SessionValueKey.SESSION_USERNAME, u.getUcname());
			session.setAttribute(SessionValueKey.SESSION_USER_UNIT_ID, u.getUnitid());
			session.setAttribute(SessionValueKey.SESSION_USER_UNIT_NAME, u.getUnitname());
			session.setAttribute(com.baidu.gcrm.common.auth.SessionValueKey.SESSION_CURRENT_USER, u);
		}
		
	    NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
	    NewRoleExtServiceWrapper roleService = ServiceBeanFactory.getRoleService();
	 // 向session中设置功能权限TAG和功能权限URL
		TreeSet<String> authSet = new TreeSet<String>();
		TreeSet<String> authUrlSet = new TreeSet<String>();
	 			
	    List<UserPosRole> posRoleListByappid = positionService.getPosRoleListByUcid(ucid, regAppId);
	    for (UserPosRole upr : posRoleListByappid) {
				List<PosRoleInfo> rl = upr.getRoleinfo();
				for (PosRoleInfo r : rl) {
					List<AuthInfo> authResult = roleService.getAuthListByRoleTag(r.getRoletag(),
							regAppId);
					CollectionUtils.collect(authResult, new Transformer() {
						public Object transform(Object input) {
							return ((AuthInfo) input).getAuthtag();
						}
					}, authSet);
					for (AuthInfo aqi : authResult) {
						if (aqi.getAuthvalue() != null) {
							authUrlSet.addAll(Arrays.asList(aqi.getAuthvalue().split(";")));
						}
					}
			}
		}
	    authUrlSet = mergeUrls(authUrlSet);
		session.setAttribute(SessionValueKey.SESSION_AUTH_SET, authSet);
		session.setAttribute(SessionValueKey.SESSION_AUTH_URL_SET, authUrlSet);
		session.setAttribute(SessionValueKey.SESSION_CACHE_VALID, true);
		
		chain.doFilter(request, response);
	}
	
	protected TreeSet<String> mergeUrls(SortedSet<String> originalSet) {
		TreeSet<String> refactoredSet = new TreeSet<String>();
		Iterator<String> it = originalSet.iterator();
		while (it.hasNext()) {
			//匹配时需要带*
			String currentUrl = it.next() + "*";
			if (!UrlUtils.urlMatch(refactoredSet, currentUrl)) {
				refactoredSet.add(currentUrl);
			}
		}

		TreeSet<String> resultSet = new TreeSet<String>();

		it = refactoredSet.iterator();
		while (it.hasNext()) {
			String url = it.next();
			//最终结果集合去掉*
			resultSet.add(url.substring(0, url.length() - 1));
		}

		return resultSet;
	}

	@Override
	public void destroy() {}

}
