package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.common.vo.ViewConvertUtils;
import com.baidu.gcrm.customer.model.Customer;

public class CustomerConvert implements ValueConvertHandler {

	@Override
	@SuppressWarnings({ "rawtypes"})
	public Object convert(Object value) {
		if(value instanceof Long){
			Customer customer = ServiceBeanFactory.getCustomerService().findByCustomerNumber((Long)value);
			if(customer!=null){
				return ViewConvertUtils.viewConvert(customer);
			}
		}
		return null;
	}

}
