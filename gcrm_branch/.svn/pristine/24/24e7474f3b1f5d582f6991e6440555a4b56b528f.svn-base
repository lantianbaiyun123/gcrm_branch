package com.baidu.gcrm.common.page;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.servlet.support.RequestContext;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.i18n.SerializableResourceBundleMessageSource;
/**
 * 翻页标签
 *
 */
public class PageTag extends TagSupport {
	
	private static final long serialVersionUID = -3521620956896510036L;
	
	private int DEFAULT_PAGE_SIZE = 20;
	private int DISPLAY_PAGE_BUTTON_AMOUNT = 4;//显示翻页按钮数
	
	private int currPage= 1;
	private int totalCount = 0;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private String formId;
	private String url;
	

	public int doStartTag() throws JspException {
	    
		JspWriter out = pageContext.getOut();
		int totalPages = getTotalPages();
		
		try {
			
			int end = currPage + DISPLAY_PAGE_BUTTON_AMOUNT;
			if(end > totalPages){
				end = totalPages;
			}
			
			int start = end - DISPLAY_PAGE_BUTTON_AMOUNT;
			if(start <= 0){
				start = 1;
			}
			
			int previousPageNum = currPage-1;
			String previousStyle = "";
			int nextPageNum = currPage+1;
			String nextStyle = "";
			if (previousPageNum <= 0) {
				previousStyle = " class='disabled' ";
			}
			if (nextPageNum > totalPages) {
				nextStyle = " class='disabled' ";
			}
			
			StringBuilder pageStr = new StringBuilder();
			
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			RequestContext requestContext = new RequestContext(request);
			SerializableResourceBundleMessageSource messageSource = (SerializableResourceBundleMessageSource)ServiceBeanFactory.getMessageSource();
			Locale currLocale = requestContext.getLocale();
			String pageDesc = messageSource.getMessage("view.page", new Object[]{totalPages,totalCount}, currLocale);
			pageStr.append("<div class='col-md-6'>")
					.append("<div class='col-md-2'>")
					.append("<select id='gcrmPageSize' refFormId='#")
					.append(formId)
					.append("' class='form-control' name='companySize'>")
					.append("<option ")
					.append(pageSize == 10 ? "selected" : "")
					.append(" value='10'>10</option>")
					.append("<option ")
					.append(pageSize == 20 ? "selected" : "")
					.append(" value='20'>20</option>")
					.append("<option ")
					.append(pageSize == 30 ? "selected" : "")
					.append(" value='30'>30</option>")
					.append("<option ")
					.append(pageSize == 50 ? "selected" : "")
					.append(" value='50'>50</option>")
					.append("</select>")
					.append("</div>")
					.append("<div class='col-md-10'><form class='form-horizontal'><p class='form-control-static'>")
					.append(pageDesc)
					.append("</p></form></div>")
					.append("</div>");
			
			pageStr.append("<div class='pull-right'><ul class='pagination pagination-sm' style='margin:0;'>");
			pageStr.append("<li")
				.append(previousStyle)
				.append(" pagenum=")
                .append(previousPageNum)
				.append("><a href='###'>&laquo;</a></li>");
			for(int i = start;i <= end;i++){
				if(i == currPage){
					pageStr.append("<li ")
					    .append(" pagenum=")
					    .append(i)
					    .append(" class='active'><a href='###'>")
						.append(i)
						.append("</a></li>");
				}else{
				    
					pageStr.append("<li ")
					    .append(" pagenum=")
                        .append(i)
    					.append("><a href='###'>")
    					.append(i)
    					.append("</a></li>");
				}
			}
			
			pageStr.append("<li")
					.append(nextStyle)
					.append(" pagenum=")
                    .append(nextPageNum)
					.append("><a href='###'>&raquo;</a></li>");
			pageStr.append("</ul></div>");
			
			out.println(pageStr.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();

	}
	
	private int getTotalPages(){
		
		if(totalCount < 1){
			totalCount = 0;
			return totalCount;
		}
		
		if(pageSize <= 0){
			pageSize = DEFAULT_PAGE_SIZE;
		}
		
		int totalPages = totalCount/pageSize;
		if (totalCount%pageSize != 0) {
			totalPages += 1;
		}
		return totalPages;
	}


	public int getCurrPage() {
		return currPage;
	}


	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public String getFormId() {
		return formId;
	}


	public void setFormId(String formId) {
		this.formId = formId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	


}