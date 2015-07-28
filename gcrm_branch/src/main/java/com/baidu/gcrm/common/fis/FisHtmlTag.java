package com.baidu.gcrm.common.fis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FisHtmlTag extends BodyTagSupport {
	private Logger logger = LoggerFactory.getLogger(FisHtmlTag.class);
	
	private static final long serialVersionUID = 8438496090522064355L;
	private static final String RESOURCES_PATH = "/resources";
	
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		ServletContext context = request.getSession().getServletContext();
		
		String path = context.getRealPath(RESOURCES_PATH + "/map");
		Resource resource = new Resource(path);
		String debug = request.getParameter("debug");
		if(StringUtils.isNotBlank(debug)){
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
			out.print("<html>");
			out.print(html);
			out.print("</html>");
		} catch (Exception e) {
			logger.equals("render fis error,reason:"+e.getMessage());
			throw new RuntimeException(e);
		}
		
		return super.doEndTag();
	}
}
