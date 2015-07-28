package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("advertisingPlatformServiceImpl")
public class AdvertisingPlatformServiceImpl extends AbstractValuelistCacheService<AdvertisingPlatform> {

	@Override
	protected AdvertisingPlatform mapToEntity(Map<String, String> map) {
		AdvertisingPlatform entity = new AdvertisingPlatform();
		
		entity.setId(new Long(map.get("id")));
		entity.setName(map.get("name"));
		
		String businessType = map.get("business_type");
		if (businessType != null) {
		    entity.setBusinessType(new Integer(businessType));
		}
		
		String status = map.get("status");
		if (status != null) {
		    entity.setStatus(Integer.valueOf(status));
		}
		
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_advertising_platform";
	}

}
