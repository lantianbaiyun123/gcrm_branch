package com.baidu.gcrm.common.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.baidu.gcrm.common.ServiceBeanFrontFactory;

public class MessageTag extends BodyTagSupport {

	private static final long serialVersionUID = -4106384557218507191L;

	private static final String QUOTE = "\"";
	
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		// 拼装JavaScript Map
		StringBuilder jsCode = new StringBuilder();
		jsCode.append("{");
		SerializableResourceBundleMessageSource messageSource = (SerializableResourceBundleMessageSource)ServiceBeanFrontFactory.getMessageSource();
		SessionLocaleResolver localeResolver = ServiceBeanFrontFactory.getSessionLocaleResolver();
		Locale locale = localeResolver.resolveLocale(request);
		Properties messages = messageSource.getAllProperties(locale);
		for (Map.Entry<Object, Object> entry : messages.entrySet()) {
			String key = (String)entry.getKey();
			String value = (String)entry.getValue();
			if(key.equals("devmode.notification")){
				System.out.println(value);
			}
			if (value != null) {
				value = StringUtils.replace(value, "\"", "\\\"");
				value = StringUtils.replace(value,"\n","\\n");
			}
			jsCode.append(QUOTE).append(key).append(QUOTE)
					.append(":")
					.append(QUOTE).append(value).append(QUOTE)
					.append(",");
		}
		jsCode.append("\"\":\"\" }");
		
		// 渲染
		JspWriter out = pageContext.getOut();
		try {
			out.println(jsCode);
		} catch (IOException ex) {
			throw new JspException(ex);
		}
		
		return super.doEndTag();
	}
}
