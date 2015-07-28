package com.baidu.gcrm.common.vo.impl;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.vo.ValueConvertHandler;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.valuelistcache.model.BillingModel;

public class SiteConvert implements ValueConvertHandler {

	@Override
	public Object convert(Object value) {
		Site site = ServiceBeanFactory.getSiteService().findSiteAndI18nById(Long.parseLong(value.toString()),
				RequestThreadLocal.getLocale());
		if(site!=null){
			return site.getI18nName();
		}
		return null;
	}

}
