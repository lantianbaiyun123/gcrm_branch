package com.baidu.gcrm.common.fis;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CssHolderTag extends BodyTagSupport {
	private static final long serialVersionUID = -6488110466691177224L;

	@Override
	public int doEndTag() throws JspException {
		// 渲染
		JspWriter out = pageContext.getOut();
		try {
			out.println(Resource.STYLE_PLACEHOLDER);
		} catch (IOException ex) {
			throw new JspException(ex);
		}

		return super.doEndTag();
	}
}

