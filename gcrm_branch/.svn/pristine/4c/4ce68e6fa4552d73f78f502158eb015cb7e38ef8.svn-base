package com.baidu.gcrm.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.ad.web.utils.ContractUrlUtilHelper;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.PassportAuthenticationFilter;
import com.baidu.gcrm.common.auth.SessionValueKey;
import com.baidu.gcrm.common.auth.ThreadLocalInfo;
import com.baidu.gcrm.user.dao.IUserRepositoryCustom;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;

@Controller
@RequestMapping("/")
public class IndexAction extends ControllerHelper{
	
	@Value("#{appproperties['cas.appid']}")
	public Integer appid;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IUserRepositoryCustom userRepositoryCustom;
	
	@Autowired
    ServletContext context;
	
	@RequestMapping
	public String index(Model model,HttpServletRequest request, HttpServletResponse response){
		return index(model,0,10, request);
	}
	
	@RequestMapping("/debug")
	public String debug(Model model,HttpServletRequest request, HttpServletResponse response){
		Map<String, List<String>> userRoleMap = userRepositoryCustom.getAllUserAndRoleName();
		request.setAttribute("data", userRoleMap);
		return "login_debug";
	}
	
	@RequestMapping("/testfis")
	public String testfis(Model model){
		return "home/testfis";
	}
	
	@RequestMapping("/switchLang")
	@ResponseBody
	public String switchLang(Locale currLocale){
	    return currLocale.toString();
	}
	
	@RequestMapping("/index/{pageNum}/{pageSize}")
	public String index(Model model, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, HttpServletRequest request){
		setLoginUser(request);
		model.addAttribute("page", null);
		return "redirect:/views/main.jsp#/home";
	}
	
	/**
	 * 登陆调试页面
	 * @param ucname
	 * @param request
	 * @return
	 */
	@RequestMapping("/login_debug/{ucname}")
	public String debugLogin(@PathVariable("ucname") String ucname, HttpServletRequest request){
		request.getSession().invalidate();
		IUserService userService = ServiceBeanFactory.getUserService();
		User currUser = userService.findByUsername(ucname);
		HttpSession session = request.getSession();
		setUserInfoToSession(session, currUser);
		return "redirect:/views/main.jsp#/home";
	}
	
	/**
	 * 设置登录用户
	 * @param request
	 */
	private void setLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long ucid = ThreadLocalInfo.getThreadUuid();
		if (null == ucid) {
			System.out.println("[IndexAction] : login user ucid is null... ");
			return;
		}
		System.out.println("[IndexAction] : login user ucid: " + ucid);
		// 设置当前登录用户
		User user = new User();
		user.setUcid(ucid);
		setUserInfoToSession(session, user);
	}
	
	/**
	 * 设置登录用户信息到session
	 * @param session
	 * @param user
	 */
	private void setUserInfoToSession(HttpSession session, User user) {
		if (null == user) {
			System.out.println("[IndexAction] : login user is null...");
			return;
		}
		IUserRightsService userRightsService = ServiceBeanFactory.getUserRightsService();
		// 设置当前登录用户
		if (null == session.getAttribute(SessionValueKey.SESSION_CURRENT_USER)) {
			IUserService userService = ServiceBeanFactory.getUserService();
			User currUser = null != user.getUsername() ? user : userService.findByUcid(user.getUcid());
			session.setAttribute(SessionValueKey.SESSION_CURRENT_USER, currUser);
			String username = currUser == null ? "" : currUser.getUsername();
			System.out.println("[IndexAction] : set login user to session, currUser:  " + username);
		}
		// 保存当前用户角色到session
		if (null == session.getAttribute(SessionValueKey.SESSION_CURRENT_USER_ROLE)) {
			IUserRightsService rightsService = ServiceBeanFactory.getUserRightsService();
			List<RightsRole> roleList = rightsService.findUserRolesByUcId(user.getUcid());
			session.setAttribute(SessionValueKey.SESSION_CURRENT_USER_ROLE, roleList);
			System.out.println("[IndexAction] : set login user role to session...");
		}
		// 设置当前登录用户功能权限信息
		if (null == session.getAttribute(SessionValueKey.SESSION_AUTH_URL_CODE)) {
			RightsUrlVO rightsUrlVO = userRightsService.findUserUrlVOs(user.getUcid());
			session.setAttribute(SessionValueKey.SESSION_AUTH_URL_CODE, rightsUrlVO);
			System.out.println("[IndexAction] : set login user rights vo to session ");
		}
		if (null == session.getAttribute(SessionValueKey.SESSION_AUTH_URL)) {
			List<String> userURLs = userRightsService.findUserUrls(user.getUcid());
			List<String> totalUrlList = userRightsService.findAllFuncUrls();
			totalUrlList.removeAll(userURLs);
			session.setAttribute(SessionValueKey.SESSION_AUTH_URL, totalUrlList);
			System.out.println("[IndexAction] : set login user rights url to session");
		}
	}
	
	@RequestMapping("errorPage")
	public String errorPage(){
		return "error";
	}
	
//	private void setLoginUser(HttpServletRequest request){
//		User user = new User();
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie : cookies) {
//			if (cookie.getName().equals("__cas__id__"+appid)) {
//				user = userService.findByUcid(Long.valueOf(cookie.getValue()));
//			}
//		}
//		request.getSession().setAttribute(SessionValueKey.SESSION_CURRENT_USER, user);
//	}
	
	/**
	 * UC登陆认证页面
	 * @param model
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/login")
	public String login(Model model,HttpServletRequest request) throws UnsupportedEncodingException{
		// UC安全中心找回密码链接（中英文国际化）
		String findPwdUrl =  GcrmConfig.getConfigValueByKey("uc.usersecureinfo.findpwd");
		// UC返回的错误信息
		String errMsg = request.getParameter("e");
		// 网站域名加项目路径
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		
		// 请求来源URL地址
		String fromu = request.getParameter("fromu");
		// 如果没有来源URL参数，则取app.properties配置的默认主页
		if (StringUtils.isBlank(fromu)) {
			fromu = PassportAuthenticationFilter.FROM;
			if(fromu != null && !fromu.startsWith("http")){
				fromu = basePath + fromu;
			}
		}
		
		// 登录失败跳转的地址
		String selfu = PassportAuthenticationFilter.SELF;
		if(selfu !=null && !selfu.startsWith("http")){
			selfu = basePath + "/" + selfu;
		}
		// 如果是内网访问地址, 则把登录失败地址也改为内网地址
		if (null != fromu && null != selfu && fromu.contains("gmll") && !selfu.contains("gmll")) {
			selfu = selfu.replace("gml", "gmll");
		}
		
		// 安全中心修改密码链接国际化
		if (LocaleConstants.en_US.toString().contains(currentLocale.toString())) {
			findPwdUrl = findPwdUrl.replace("@", "EN");
			model.addAttribute("lang_login", "en-us");
		}else {
			findPwdUrl = findPwdUrl.replace("@", "");
		}
		
		model.addAttribute("findpwd", findPwdUrl);
		model.addAttribute("errMsg", errMsg);
		model.addAttribute("formaction", PassportAuthenticationFilter.FORM_ACTION);
		model.addAttribute("fromu", fromu);
		model.addAttribute("selfu", selfu);
		model.addAttribute("captcha", PassportAuthenticationFilter.CAPTCHA);
		model.addAttribute("appid", PassportAuthenticationFilter.APP_ID);
		//set cms home url
        String cmsHomeKey = Constants.CMS_HOME_URL;
        if (context != null && context.getAttribute(cmsHomeKey) == null) {
            context.setAttribute(cmsHomeKey, ContractUrlUtilHelper.getContractHomeUrl());
        }
        return "login";
	}
	
	@RequestMapping("/logout")
	public void logout(Model model,HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		try {
			String basePath = request.getScheme()+"://"+request.getServerName()+":"
					+ request.getServerPort()+request.getContextPath(); 
			String logouturl = GcrmConfig.getConfigValueByKey("cas.logouturl");
			if(logouturl !=null && logouturl.endsWith("u=")){
				logouturl = logouturl + basePath + "/" + PassportAuthenticationFilter.SELF;
			}
			response.sendRedirect(logouturl);
		} catch (IOException e) {
			// ignore
		}
	}
}
