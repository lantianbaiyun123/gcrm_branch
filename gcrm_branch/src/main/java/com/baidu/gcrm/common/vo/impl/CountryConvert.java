package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.valuelistcache.model.Country;

public class CountryConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		Country country = ServiceBeanFactory.getCountryCacheServiceImpl().getByIdAndLocale(
				Long.parseLong(value.toString()),
				RequestThreadLocal.getLocale());
		if(country!=null){
			return country.getI18nName();
		}
		return null;
	}

}
