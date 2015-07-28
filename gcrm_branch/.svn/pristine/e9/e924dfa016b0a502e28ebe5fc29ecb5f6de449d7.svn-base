package com.baidu.gcrm.common.auth;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.account.model.Account.AccountType;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.common.CommonHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.uc.CasClient;
import com.baidu.uc.protocol.CasCheckResponse;
import com.baidu.uc.svr.CasInfo;

public class PassportAuthenticationFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(PassportAuthenticationFilter.class);
	
	public final static String SERVERS = GcrmConfig.getConfigValueByKey("cas.servers");               // CAS服务器地址
	public final static String FORM_ACTION = GcrmConfig.getConfigValueByKey("cas.formaction");       // 登陆form表单action，处理登陆逻辑
	public final static String SELF = GcrmConfig.getConfigValueByKey("cas.selfu");                   // 登陆失败后进入的URL，可覆盖loginurl
	public final static String FROM = GcrmConfig.getConfigValueByKey("cas.fromu");					 // 登陆成功后进入的URL，可覆盖indexurl
	public final static int APP_ID = Integer.parseInt(GcrmConfig.getConfigValueByKey("cas.appid"));  // 登陆的系统的appid
	public final static String CAPTCHA = GcrmConfig.getConfigValueByKey("cas.captcha");              // 登陆页面获取验证码图片接口
	public final static String JUMP_URL = GcrmConfig.getConfigValueByKey("cas.jumpurl");             //跳转url，判断是否从其他的登陆系统跳转到当前系统，如果不允许，uclogin会跳转到登陆页面
	public final static String LOGIN_URL = GcrmConfig.getConfigValueByKey("cas.loginurl");	         //登陆url，当casclient检测到错误时重定向到此URL
	public final static String COOKIE_DOMAIN = GcrmConfig.getConfigValueByKey("cas.cookiedomain");   // 当前业务系统的cookie域，处于安全性考虑，请挂接系统务必设置成系统的全域名，如：www2.baidu.com。但在开发和测试阶段请大家注意，需要把cookieDomain设置成开发/测试环境的域名/机器名，否则每次访问会出现非正常跳转。百度禁止使用百度大域下的cookie。
	public final static int TIMEOUT = Integer.parseInt(GcrmConfig.getConfigValueByKey("cas.timeout"));  // socket连接后台的超时时间，可选参数。该参数在构造函数中未传入的情况下默认是1000ms。
	public final static String ERROR_CODES = GcrmConfig.getConfigValueByKey("cas.errno");               // UC错误代码 全部：http://cq01-uc-rd00.vm.baidu.com:8000/wordpress/?p=1249
	public final static String LOGOUT_URL = GcrmConfig.getConfigValueByKey("cas.logouturl");        // 退出URL（如果需要指定登出后跳转页面，可以加上&u=登出的URL）
    public final static String FINDPASS_URL = GcrmConfig.getConfigValueByKey("uc.usersecureinfo.findpwd");
    public final static String CHANGEPASS_URL = GcrmConfig.getConfigValueByKey("uc.usersecureinfo.changepwd");
    
	private final static String LANGUAGE_COOKIE_NAME = GcrmConfig.getConfigValueByKey("cookie.lang.name"); 
	
	
	private String[] excludeUrls;
	
	public void init(FilterConfig config) throws ServletException {
		String excludeUrl = config.getInitParameter("excludeURL");
		excludeUrls = excludeUrl.split(";");
	}
	
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//force change locale
		Cookie[] cookieArray = req.getCookies();
		if (cookieArray != null) {
		    String cookieLanguage = null;
		    for(Cookie temCookie : cookieArray) {
		        if (LANGUAGE_COOKIE_NAME.equals(temCookie.getName())) {
		            cookieLanguage = temCookie.getValue();
		            break;
		        }
		    }
		    SessionLocaleResolver localeResolver = ServiceBeanFactory.getSessionLocaleResolver();
		    if (cookieLanguage != null && localeResolver != null
		            && !cookieLanguage.equals(localeResolver.resolveLocale(req).toString())) {
		        localeResolver.setLocale(req, res,
		                org.springframework.util.StringUtils.parseLocaleString(cookieLanguage));
		    }
		}
		 // 保存当前request到ThreadLocal
	     RequestThreadLocal.setRequestThreadLocal(req);
		
		// web.xml配置的排除URL
		if(ArrayUtils.isNotEmpty(excludeUrls)){ 
			String requestUrl =  req.getRequestURI();
			for(String excludeUrl : excludeUrls){
				if(requestUrl.contains(excludeUrl)){
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		// 如果认证成功过，且session未失效，则不走UC
		HttpSession session = req.getSession();
		if (null != session.getAttribute(SessionValueKey.SESSION_CURRENT_USER_APPID)) {
			chain.doFilter(request, response);
	        System.out.println("[PassportAuthenticationFilter] : UC has already authed");
			return;
		}
		
		// Ajax请求, 文件上传不经过UC
		if (CommonHelper.isAjaxRequest(req) || CommonHelper.isFileUpload(req)) {
			// 判断登录状态
			if (null == session.getAttribute(SessionValueKey.SESSION_CURRENT_USER_APPID)) {
				String jsonResp = JSON.toJSONString(JsonBeanUtil.convertBeanToJsonBean(null, "session timeout, please login again!", JsonBeanUtil.CODE_SESSION_TIMEOUT));
				res.getWriter().print(jsonResp);
				return;
			}
			chain.doFilter(request, response);
			return;
		}		
		
		boolean bolAutoRedirect = true;//是否支持匿名登录. 是：false 否:true
		CasInfo objcasInfo = new CasInfo(SERVERS, APP_ID, COOKIE_DOMAIN, TIMEOUT);
		objcasInfo.setCookieDomain(COOKIE_DOMAIN);//必须设置
		objcasInfo.setJumpUrl(JUMP_URL);
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
		objcasInfo.setLoginUrl(basePath + "/" + LOGIN_URL);
		objcasInfo.setAutoRedirect(bolAutoRedirect);
		objcasInfo.setEncoding("UTF-8");
	    /**
	       * 构造对象
	       */      
	    CasClient objCc = new CasClient(req,res,objcasInfo);
	    CasCheckResponse objCcr = null;
	      try {
	         objCcr = objCc.validateST();
	      } catch(Exception e) {
	    	 String errno = req.getParameter("errno");
	         log.error("exception occured when validateST:" + e.getMessage());
	         //跳转到登陆页
	         if(StringUtils.isNotBlank(errno)){
	        	 res.sendRedirect(req.getContextPath() + "/" + LOGIN_URL + "?errno="+errno);
	         } else {
	        	 res.sendRedirect(req.getContextPath() + "/" + LOGIN_URL);
	         }
	         return;
	      }
	      if (objCcr == null) {
	    	 log.error("stop chain, CheckResponse is null.");
	         return;
	      }
	      
	      Long ucid = objCcr.getUcid();
		  Integer regAppid = objCcr.getReg_appid();
		  // 保存当前用户ucid到ThreadLocal
		  ThreadLocalInfo.setThreadUuid(ucid);
	      if(regAppid != null){
	    	  session.setAttribute(SessionValueKey.SESSION_CURRENT_USER_APPID, regAppid);
			  System.out.println("[PassportAuthenticationFilter] :  set login user appid from UC " + regAppid);
	      }
	      // 判断是否是外部用户，如果是则不能登录本系统
	      IAccountService accountService = ServiceBeanFactory.getAccountService();
	      AccountType accountType = accountService.findAccountTypeByUcId(ucid);
	      if (null != accountType && accountType.equals(AccountType.OUTER)) {
	    	  res.sendRedirect(req.getContextPath() + "/" + LOGIN_URL + "?e=this+account+is+not+allow+login");
	      }
	      
	      // 保存当前用户到session
	      if (null == session.getAttribute(SessionValueKey.SESSION_CURRENT_USER)) {
	    	  IUserService userService = ServiceBeanFactory.getUserService();
	    	  User currUser = userService.findByUcid(ucid);
	    	  session.setAttribute(SessionValueKey.SESSION_CURRENT_USER, currUser);
			  System.out.println("[PassportAuthenticationFilter] : set login user to session...");
	      }
	      // 保存当前用户角色到session
	      if (null == session.getAttribute(SessionValueKey.SESSION_CURRENT_USER_ROLE)) {
	    	  IUserRightsService rightsService = ServiceBeanFactory.getUserRightsService();
	    	  List<RightsRole> roleList = rightsService.findUserRolesByUcId(ucid);
	    	  session.setAttribute(SessionValueKey.SESSION_CURRENT_USER_ROLE, roleList);
	    	  System.out.println("[PassportAuthenticationFilter] : set login user role to session...");
	      }
	      chain.doFilter(request, response);
	}
}
