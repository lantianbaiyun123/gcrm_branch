package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("countryCacheServiceImpl")
public class CountryServiceImpl extends AbstractValuelistCacheService<Country> {

	@Override
	protected Country mapToEntity(Map<String, String> map) {
		Country entity = new Country();
		
		entity.setId(new Long(map.get("id")));
		entity.setName(map.get("name"));
		entity.setCode(map.get("code"));
		
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_country";
	}

}
