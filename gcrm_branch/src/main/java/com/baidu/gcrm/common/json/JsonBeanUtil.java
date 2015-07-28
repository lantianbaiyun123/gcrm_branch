package com.baidu.gcrm.common.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.page.PageWrapper;

public class JsonBeanUtil {
    
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_MESSAGE = 201;
    public static final int CODE_ERROR = 202;
    public static final int CODE_MESSAGE_ERROR = 203;
    public static final int CODE_ERROR_DISPLAY_DIRECTLY = 204;
    public static final int CODE_ERROR_IGNORE = 205;
    public static final int CODE_ERROR_MESSAGE = 206;
    public static final int CODE_SUCCESS_MESSAGE = 207;
    public static final int CODE_SESSION_TIMEOUT = 208;        // 登录超时，Ajax提示，直接跳转登陆页
    public static final int CODE_ERROR_TOTAL = 209;            // 批量保存整体错误代码


    public static <T> JsonBean<T> convertBeanToJsonBean(T data ) { 
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data, Map<String, String> errors) {
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        temJsonBean.setErrors(errors);
        
        if(errors != null && errors.size() > 0){
	        temJsonBean.setCode(CODE_ERROR);
	        temJsonBean.setMessage("errors");
        }
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data,
            Object listObject, int code) {
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        temJsonBean.setErrorList(listObject);
        temJsonBean.setCode(code);
        temJsonBean.setMessage("errors");
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data,
            Map<String, String> errors, int code) {
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        temJsonBean.setErrors(errors);
        
        if(errors != null && errors.size() > 0){
            temJsonBean.setCode(code);
            temJsonBean.setMessage("errors");
        }
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToErrorJsonBean(T data, String message) {
    	Map<String,String> errors = new HashMap<String,String>();
    	errors.put(message, message);
        return convertBeanToJsonBean(data,errors);
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data, String message) {
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        String i18nMessage = MessageHelper.getMessage(message, RequestThreadLocal.getLocale());
        temJsonBean.setMessage(i18nMessage);
        temJsonBean.setCode(CODE_MESSAGE);
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data, String message,int code) {
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        temJsonBean.setMessage(message);
        temJsonBean.setCode(code);
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<T> convertBeanToJsonBean(T data, Map<String, String> errors, String message) {
    	if (errors == null || errors.size() == 0) {
    		return convertBeanToJsonBean(data, message);
    	}
    	if (StringUtils.isEmpty(message)) {
    		return convertBeanToJsonBean(data, errors);
    	}
    	
        JsonBean<T> temJsonBean = new JsonBean<T>();
        temJsonBean.setData(data);
        temJsonBean.setErrors(errors);
	    temJsonBean.setCode(CODE_MESSAGE_ERROR);
	    temJsonBean.setMessage(message);
        
        return temJsonBean;
    }
    
    public static <T> JsonBean<JsonListBean<T>> convertListBeanToJsonBean(List<T> data){
    	JsonBean<JsonListBean<T>> temJsonBean = new JsonBean<JsonListBean<T>>();
    	JsonListBean<T> listBean = new JsonListBean<T>(data);
    	temJsonBean.setData(listBean);
    	return temJsonBean;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> JsonBean<T> convertPageBeanToJsonBean(T data ) {
        if(data instanceof PageWrapper){
			PageWrapper pageWrapper = (PageWrapper) data;
			Page page = new Page();
        	
        	page.setResult(pageWrapper.getContent());
        	//page.setPageNo(pageWrapper.getPageNumber());
        	page.setPageSize(pageWrapper.getPageSize());
        	page.setResultClass(pageWrapper.getResultClass());
        	page.setTotalCount(pageWrapper.getTotal());
        	
        	JsonBean temJsonBean = new JsonBean();
            temJsonBean.setData(page);
        	return temJsonBean;
        }else{
        	return convertBeanToJsonBean(data);
        }
    }
}
