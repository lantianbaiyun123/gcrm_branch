package com.baidu.gcrm.account.rights.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;

public class RightsTag extends BodyTagSupport {
	private static final long serialVersionUID = -806950097919634406L;

	@Override
	public int doEndTag() throws JspException {
		IUserRightsService userRightsService = ServiceBeanFactory.getUserRightsService();
		RightsUrlVO urlVO = userRightsService.findUserUrlVOs(RequestThreadLocal.getLoginUserId());
		String urlJson = JSON.toJSONString(urlVO);
		// 渲染
		JspWriter out = pageContext.getOut();
		try {
			out.println(urlJson);
		} catch (IOException ex) {
			throw new JspException(ex);
		}
		return super.doEndTag();
	}
}
