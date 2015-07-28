package com.baidu.gcrm.common.vo.impl;

import java.util.Arrays;
import java.util.List;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

public class PositionConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		if(value instanceof Long){
			List<Long> ids = Arrays.asList((Long)value);
			List<PositionVO> position = ServiceBeanFactory.getPositionService().findI18nByPositionIds(ids, 
					RequestThreadLocal.getLocale());
			if(position!=null && position.size()>0){
				return position.get(0).getI18nName();
			}
		}
		return null;
	}

}
