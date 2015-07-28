package com.baidu.gcrm.common.fis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.baidu.gcrm.common.fis.Resource.ScriptPoolRenderOrder;

public class ScriptTag extends BodyTagSupport {
	private static final long serialVersionUID = 7054910759990476336L;

	private String type = ScriptPoolRenderOrder.normal.name();
	
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Resource resource = (Resource)request.getAttribute("FIS_RESOURCE");
		resource.addScriptPool(super.getBodyContent().getString(),type);
		return super.doEndTag();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
