package com.baidu.gcrm.account.rights.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service
public class RightsRoleServiceImpl extends AbstractValuelistCacheService<RightsRole>{

	@Override
	protected RightsRole mapToEntity(Map<String, String> map) {
		RightsRole entity = new RightsRole();
		entity.setId(Long.valueOf(map.get("id")));
		entity.setRoleTag(map.get("role_tag"));
		entity.setRoleDesc(map.get("role_desc"));
		entity.setRoleId(Long.valueOf(map.get("role_id")));
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_rights_role";
	}

}
