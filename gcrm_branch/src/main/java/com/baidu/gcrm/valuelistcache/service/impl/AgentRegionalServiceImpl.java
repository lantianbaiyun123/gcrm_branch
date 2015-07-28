package com.baidu.gcrm.valuelistcache.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.web.helper.BaseI18nModelComparator;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.model.I18N;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Service("agentRegionalService")
public class AgentRegionalServiceImpl extends AbstractValuelistCacheService<AgentRegional>{
	
	@Autowired
	private IValuelistCacheDao cacheDao;
	
	@Autowired
	@Qualifier("countryCacheServiceImpl")
	private AbstractValuelistCacheService<Country> countryCacheService;
	
	public AgentRegional getById(String id,LocaleConstants locale){
		Map<String,String> map = null;
		
		map = cacheDao.get(tableName, id);
		if(map==null){
			return null;
		}
		
		return mapToEntity(map,locale,true);
	}
	
	public List<AgentRegional> getAll(LocaleConstants locale){
		List<AgentRegional> results = new ArrayList<AgentRegional>();
		List<Map<String,String>> maps ;
		maps = cacheDao.findAll(tableName);
		
		for(Map<String,String> map : maps){
			results.add(mapToEntity(map,locale,false));
		}
		
		return results;
	}
	
	
	private AgentRegional mapToEntity(Map<String,String> map,LocaleConstants locale,boolean isContainsCountry){
		if(map == null){
			new AgentRegional();
		}
		
		AgentRegional entity= new AgentRegional();
		
		entity.setId(new Long(map.get("id")));
		entity.setName(map.get("name"));
		if(isContainsCountry){
		Set<Country> agentCountries = getAgentCountries(map.get("id"),locale);
		entity.setAgentCountries(agentCountries);
		}
		return entity;
	}
	

	@Override
	protected void init() {
		this.tableName="g_agent_regional";
	}
	
	/**
	 * 获取当前区域的国家
	 * @param regionalId
	 * @return
	 */
	private Set<Country> getAgentCountries(String regionalId,LocaleConstants locale){
		List<Map<String,String>> maps = cacheDao.findAll("g_agenct_country");
		Set<Country> entities = new TreeSet<Country>(new BaseI18nModelComparator());
		
		//过滤当前区域的国家
		for(Map<String,String> map : maps){
			if(regionalId.equals(map.get("regional_id"))){
				Country country = countryCacheService.getByIdAndLocale(map.get("country_id"),locale);
				
				entities.add(country);
			}
		} 
		//Collections.sort(entities, new BaseI18nModelComparator());

		return entities;
	}
	
	/**
	 * 获取当前表的信息，包括i18n信息
	 * @param id
	 * @param locale
	 * @return
	 */
	public AgentRegional getByIdAndLocale(String id, LocaleConstants locale) {
		
		AgentRegional entity = getById(id,locale);
		if(entity == null){
			return null;
		}
		
		if(entity.getId()==null)
			return entity;
		
		I18N i18n = getI18N((Long)entity.getId(), locale.toString());
		if(i18n!=null){
			entity.setI18nName(i18n.getKeyValue());
		}
		
		return entity;
	}

	/**
	 * 获取当前表所有信息，包括i18n信息
	 * @param locale
	 * @return
	 */
	public List<AgentRegional> getAllByLocale(LocaleConstants locale) {
		List<AgentRegional> entities = new ArrayList<AgentRegional>();
		entities = getAll(locale);
		for(AgentRegional entity : entities){
			if(entity.getId()==null)
				continue;
			I18N i18n = getI18N((Long)entity.getId(), locale.toString());
			if(i18n!=null){
				entity.setI18nName(i18n.getKeyValue());
			}
		}
		
		return entities;
	}

    @Override
    protected AgentRegional mapToEntity(Map<String, String> map) {
        
        return mapToEntity(map,null,false);
    }
}
