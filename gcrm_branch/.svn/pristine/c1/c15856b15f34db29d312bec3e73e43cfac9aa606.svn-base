package com.baidu.gcrm.common.uc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;


/**
 * @author YanBing
 * 
 */
public class McpackRpcProxyFactoryBean extends UrlBasedRemoteAccessor implements
		FactoryBean<Object> {

	private Object serviceProxy;
	
	private String encoding = "UTF-8";
	
	
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		
		String serviceUrl = super.getServiceUrl();
		Class<?> serviceInterface = super.getServiceInterface();
		
		if (super.getServiceUrl() == null || serviceUrl.trim().equals("")) {
			throw new IllegalArgumentException("serviceUrl must be assigned");
		}
		if (serviceInterface == null || !serviceInterface.isInterface()) {
			throw new IllegalArgumentException(
					"serviceInterface must be a interface");
		}

		Class<?>[] clazz = new Class[] { serviceInterface };
		
		InvocationHandler handler = new McpackRpcProxyBean(serviceUrl, encoding);

		this.serviceProxy = Proxy.newProxyInstance(this.getBeanClassLoader()
					, clazz, handler);
		
	}

	public Object getObject() {
		return this.serviceProxy;
	}

	public Class<?> getObjectType() {
		return getServiceInterface();
	}

	public boolean isSingleton() {
		return true;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}

