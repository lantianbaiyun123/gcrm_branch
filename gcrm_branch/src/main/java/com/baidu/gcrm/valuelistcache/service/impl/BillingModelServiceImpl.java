package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.model.I18N;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("billingModelServiceImpl")
public class BillingModelServiceImpl extends AbstractValuelistCacheService<BillingModel> {

	@Override
	protected BillingModel mapToEntity(Map<String, String> map) {
		
		BillingModel entity = new BillingModel();
		I18N i18n = new I18N();
		
		entity.setId(Long.valueOf(map.get("id")));
		entity.setName(map.get("name"));
		entity.setType(Integer.valueOf(map.get("type")));
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_billing_model";
	}
	
    public void setCacheDao(IValuelistCacheDao cacheDao){
        this.cacheDao = cacheDao;
    }	
}