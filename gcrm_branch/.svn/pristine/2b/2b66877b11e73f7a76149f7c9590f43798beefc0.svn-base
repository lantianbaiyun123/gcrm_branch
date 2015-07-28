package com.baidu.gcrm.common.fis;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class RequireHolderTag extends BodyTagSupport {
	private static final long serialVersionUID = -7273678691559870870L;
	
	private String name;

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Resource resource = (Resource)request.getAttribute("FIS_RESOURCE");
		
		try {
			resource.require(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return super.doEndTag();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}




}
