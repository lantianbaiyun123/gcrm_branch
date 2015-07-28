package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;

public class AgentRegionalConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		AgentRegional regional = ServiceBeanFactory.getAgentRegionalService().getByIdAndLocale(
				Long.parseLong(value.toString()),
				RequestThreadLocal.getLocale());
		if(regional!=null){
			return regional.getI18nName();
		}
		return null;
	}

}
