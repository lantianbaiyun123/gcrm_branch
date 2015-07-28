package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

public class BillModelConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		BillingModel billModel = ServiceBeanFactory.getBillingModelServiceImpl().getById(value.toString());
		if(billModel!=null){
			return billModel.getI18nName();
		}
		return null;
	}

}
