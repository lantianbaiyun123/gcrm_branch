package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.SiteVO;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("siteCacheServiceImpl")
public class SiteServiceImpl extends AbstractValuelistCacheService<SiteVO> {

	@Override
	protected SiteVO mapToEntity(Map<String, String> map) {
	    SiteVO site = new SiteVO();
	    site.setId(new Long(map.get("id")));
	    site.setCode(map.get("code"));
		return site;
	}

	@Override
	protected void init() {
		this.tableName = "g_site";
	}

}
