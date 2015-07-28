package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("currencyTypeServiceImpl")
public class CurrencyTypeServiceImpl extends AbstractValuelistCacheService<CurrencyType> {

	@Override
	protected CurrencyType mapToEntity(Map<String, String> map) {
		CurrencyType entity = new CurrencyType();
		
		entity.setId(new Long(map.get("id")));
		entity.setName(map.get("name"));
		entity.setRadio(new Float(map.get("radio")));
		entity.setSign(map.get("sign"));
		return entity;
	}

	@Override
	protected void init() {
		this.tableName = "g_currency_type";
	}

}
