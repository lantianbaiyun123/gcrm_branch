package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.common.vo.ViewConvertUtils;

public class ContractConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		if(value!=null){
			Contract contract = ServiceBeanFactory.getContractService().findByNumber(
					value.toString());
			if(contract!=null){
				return ViewConvertUtils.viewConvert(contract);
			}
		}
		return null;
	}

}
