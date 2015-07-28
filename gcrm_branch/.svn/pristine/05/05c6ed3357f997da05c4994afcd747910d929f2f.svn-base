package com.baidu.gcrm.common.i18n;

import static com.baidu.gcrm.common.i18n.Message.MESSAGE_ATTRIBUTE;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFrontFactory;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.i18n.model.I18nKV;


public class MessageHelper {
	public static String  getI18nForBean(String key) {  
		return getI18nForBean(key, Locale.ENGLISH);
    }
	
	public static String  getI18nForBean(String key, Locale locale) {  
		I18nKV i18n = ServiceBeanFrontFactory.getI18nService()
				.findByKeyAndLocale(key, LocaleConstants.valueOf(locale.toString()));
	    return i18n.getValue();
    }
	
	public static String  getMessage(String code, Object[] args, Locale locale) {  
        String msg = ServiceBeanFrontFactory.getMessageSource()
        		.getMessage(code, args, code, locale);
        return StringUtils.isNotBlank(msg) ? msg.trim() : code;  
    }
	
	public static String getMessage(String code, Object[] args, LocaleConstants localeConstants) {  
		String[] str = localeConstants.toString().split("_");
		Locale locale = Locale.ENGLISH;
		if(str != null){
			locale = new Locale(str[0], str[1]);
		}
        String msg = ServiceBeanFrontFactory.getMessageSource()
        		.getMessage(code, args, code, locale);
        return StringUtils.isNotBlank(msg) ? msg.trim() : code;  
    }
	
	public static String getMessage(String code, LocaleConstants local){
		String[] str = local.toString().split("_");
		Locale locale = Locale.ENGLISH;
		
		if(str != null){
			locale = new Locale(str[0], str[1]);
		}
		 
		return getMessage(code, null,locale);
	}

    public static void addSuccessAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.SUCCESS, args);
    }

    public static void addErrorAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.ERROR, args);
    }

    public static void addInfoAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.INFO, args);
    }

    public static void addWarningAttribute(RedirectAttributes ra, String message, Object... args) {
        addAttribute(ra, message, Message.Type.WARNING, args);
    }

    private static void addAttribute(RedirectAttributes ra, String message, Message.Type type, Object... args) {
        ra.addFlashAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }

    public static void addSuccessAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.SUCCESS, args);
    }

    public static void addErrorAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.ERROR, args);
    }

    public static void addInfoAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.INFO, args);
    }

    public static void addWarningAttribute(Model model, String message, Object... args) {
        addAttribute(model, message, Message.Type.WARNING, args);
    }

    private static void addAttribute(Model model, String message, Message.Type type, Object... args) {
        model.addAttribute(MESSAGE_ATTRIBUTE, new Message(message, type, args));
    }
    
    public static String generateI18nKey(Class<?> clazz,Serializable id) throws CRMBaseException{
        Table tableAnnotation =  clazz.getAnnotation(Table.class);
        if (tableAnnotation == null ) {
            throw new CRMBaseException("object is not a persistence entity!");
        }
    	return tableAnnotation.name() + "." + id;
    }
    
    public static String getI18nKeyPrefix(Class<?> clazz) throws CRMBaseException{
        Table tableAnnotation =  clazz.getAnnotation(Table.class);
        if (tableAnnotation == null ) {
            throw new CRMBaseException("object is not a persistence entity!");
        }
        return tableAnnotation.name();
    }
    
    public static String getI18nCacheKey(String i18nKey,LocaleConstants locale){
		return i18nKey + "." + locale.name();
		
	}
}
