package com.baidu.gcrm.common.fis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FisDivTag extends BodyTagSupport {
	
	private static final long serialVersionUID = 8438496090522064355L;
	private static final String RESOURCES_PATH = "/resources";
	
	private String id;
	private String cssClass;
	
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		ServletContext context = request.getSession().getServletContext();
		
		String path = context.getRealPath(RESOURCES_PATH + "/map");
		Resource resource = new Resource(path);
		String debug = request.getParameter("debug");
		if(debug != null && !debug.equals("")){
			resource.debug = true;
		}
		
		request.setAttribute("FIS_RESOURCE", resource);
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Resource resource = (Resource)request.getAttribute("FIS_RESOURCE");
		
		try {
			String html = super.bodyContent.getString();
			html = resource.replace(html);
			
			// 渲染
			JspWriter out = pageContext.getOut();
			out.print(String.format("<div id='%s' class='%s'>",id,cssClass));
			out.print(html);
			out.print("</div>");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return super.doEndTag();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	
}
