package com.baidu.gcrm.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.i18n.service.I18nKVService;

public class ServiceBeanFrontFactory {
	private static MessageSource messageSource;
	private static I18nKVService i18nService;
	private static ICustomerService customerService;
	private static SessionLocaleResolver sessionLocaleResolver = null;

	public static MessageSource getMessageSource() {
		return messageSource;
	}

	@Autowired
	@Qualifier("messageSource")
	public void setMessageSource(MessageSource messageSource) {
		ServiceBeanFrontFactory.messageSource = messageSource;
	}

	public static I18nKVService getI18nService() {
		return i18nService;
	}

	@Autowired
	public void setI18nService(I18nKVService i18nService) {
		ServiceBeanFrontFactory.i18nService = i18nService;
	}

	public static ICustomerService getCustomerService() {
		return customerService;
	}

	@Autowired
	public void setCustomerService(ICustomerService customerService) {
	    ServiceBeanFrontFactory.customerService = customerService;
	}

    public static SessionLocaleResolver getSessionLocaleResolver() {
        return sessionLocaleResolver;
    }
    
    @Autowired
    public void setSessionLocaleResolver(
            SessionLocaleResolver sessionLocaleResolver) {
        ServiceBeanFrontFactory.sessionLocaleResolver = sessionLocaleResolver;
    }
    
}
