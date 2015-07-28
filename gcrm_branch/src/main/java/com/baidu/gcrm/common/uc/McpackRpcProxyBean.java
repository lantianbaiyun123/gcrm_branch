package com.baidu.gcrm.common.uc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.baidu.rpc.client.McpackRpcProxy;
import com.baidu.rpc.exception.ExceptionHandler;

public class McpackRpcProxyBean implements InvocationHandler {

	private String serviceUrl;
	private String encoding;

	public McpackRpcProxyBean(String serviceUrl, String encoding) {
		this.serviceUrl = serviceUrl;
		this.encoding = encoding;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		return new McpackRpcProxy(serviceUrl, encoding, new ExceptionHandler())
				.invoke(proxy, method, args);
	}
}