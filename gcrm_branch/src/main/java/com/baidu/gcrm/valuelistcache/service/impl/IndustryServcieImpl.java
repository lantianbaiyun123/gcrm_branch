package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.Industry;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("industryServiceImpl")
public class IndustryServcieImpl extends AbstractValuelistCacheService<Industry> {

	@Override
	protected Industry mapToEntity(Map<String, String> map) {
		Industry entity = new Industry();
		
		entity.setId(new Long(map.get("id")));
		entity.setName(map.get("name"));
		
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_industry";
	}

}
