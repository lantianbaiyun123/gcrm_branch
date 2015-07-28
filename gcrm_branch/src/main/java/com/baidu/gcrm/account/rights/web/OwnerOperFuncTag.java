package com.baidu.gcrm.account.rights.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.common.ServiceBeanFactory;

/**
 * 只有数据所有者能操作的功能
 * @author zhanglei35
 *
 */
public class OwnerOperFuncTag extends BodyTagSupport {
	private static final long serialVersionUID = -8324473172599197635L;

	@Override
	public int doEndTag() throws JspException {
		IUserRightsService userRightsService = ServiceBeanFactory.getUserRightsService();
		List<String> ownerOperUrList = userRightsService.findOwnerOperFuncs();
		String urlJson = JSON.toJSONString(ownerOperUrList);
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
