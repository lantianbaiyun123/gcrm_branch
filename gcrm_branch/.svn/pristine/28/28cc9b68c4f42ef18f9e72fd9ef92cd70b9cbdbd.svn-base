package com.baidu.gcrm.common.fis;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class JsHolderTag extends BodyTagSupport {
	private static final long serialVersionUID = 8438496090522064355L;

	@Override
	public int doEndTag() throws JspException {
		// 渲染
		JspWriter out = pageContext.getOut();
		try {
			out.println(Resource.SCRIPT_PLACEHOLDER);
		} catch (IOException ex) {
			throw new JspException(ex);
		}

		return super.doEndTag();
	}
}
