package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.vo.ValueConvertHandler;

public class AccountConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		if(value != null){
			Account account = ServiceBeanFactory.getAccountService().findByUcid(
					Long.parseLong(value.toString()));
			if(account!=null){
				return account.getName();
			}
		}
		return null;
	}

}
