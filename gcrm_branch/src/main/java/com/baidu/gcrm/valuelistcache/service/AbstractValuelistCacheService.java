package com.baidu.gcrm.valuelistcache.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.model.IBaseI18nModel;
import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelistcache.model.I18N;

public abstract class AbstractValuelistCacheService <T> {
	
	@Autowired
	protected IValuelistCacheDao cacheDao;
	
	protected String tableName;
	
	protected String i18nTableName = "g_i18n";
	
	public AbstractValuelistCacheService(){
		init();
	}
	
	/**
	 * 根据id获取缓存当前表对象
	 * @param id
	 * @return
	 */
	public T getById(String id){
		Map<String,String> map = null;
		
		map = cacheDao.get(tableName, id);
		if(map==null){
			return null;
		}
		
		return mapToEntity(map);
	}
	
	/**
	 * 获取当前表所有的缓存对象
	 * @return
	 */
	public List<T> getAll(){
		List<T> results = new ArrayList<T>();
		List<Map<String,String>> maps ;
		maps = cacheDao.findAll(tableName);
		
		for(Map<String,String> map : maps){
			results.add(mapToEntity(map));
		}
		
		return results;
	}
	
	/**
	 * 将map映射为实体对象
	 * @param map
	 * @return
	 */
	protected abstract T mapToEntity(Map<String,String> map);
	
	/**
	 * 初始化工作，不如设定tableName
	 */
	protected abstract void init();
	
	
	protected I18N getI18N(Long id,String locale){
		I18N i18n = new I18N();
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("key_name",tableName+"."+id);
		params.put("locale",locale.toString());
		Map<String,String> values = cacheDao.get(i18nTableName, params);
		
		if(values==null)
			return null;
		
		i18n.setId(new Long(values.get("id")));
		i18n.setKeyName(values.get("key_name"));
		i18n.setKeyValue(values.get("key_value"));
		i18n.setLocale(values.get("locale"));
		
		return i18n;
	}
	
	/**
	 * 获取当前表的信息，包括i18n信息
	 * @param id
	 * @param locale
	 * @return
	 */
	@SuppressWarnings("unchecked")
    public T getByIdAndLocale(String id, LocaleConstants locale) {
		IBaseI18nModel entity = (IBaseI18nModel)getById(id);
		if(entity == null){
			return null;
		}
		
		if(entity.getId()==null)
			return (T)entity;
		if(locale!=null){
			I18N i18n = getI18N((Long)entity.getId(), locale.toString());
			if(i18n!=null){
				entity.setI18nName(i18n.getKeyValue());
			}
		}
		return (T)entity;
	}
	
	public T getByIdAndLocale(Serializable id, LocaleConstants locale) {
		return getByIdAndLocale(String.valueOf(id), locale);
	}

	/**
	 * 获取当前表所有信息，包括i18n信息
	 * @param locale
	 * @return
	 */
	public List<T> getAllByLocale(LocaleConstants locale) {
		List<T> entities = new ArrayList<T>();
		entities = getAll();
		if (locale == null) {
		    return entities;
		}
		for(T tmpEntity : entities){
			IBaseI18nModel entity = (IBaseI18nModel) tmpEntity;
			if(entity.getId()==null)
				continue;
			I18N i18n = getI18N((Long)entity.getId(), locale.toString());
			if(i18n!=null){
				entity.setI18nName(i18n.getKeyValue());
			}
		}
		
		return entities;
	}
	
	public Map<String,T> getAllMapByLocale(LocaleConstants locale) {
	    Map<String,T> entitieMap = new HashMap<String,T>();
	    List<T> entities = getAll();
        for(T tmpEntity : entities){
            IBaseI18nModel entity = (IBaseI18nModel) tmpEntity;
            if(entity.getId()==null)
                continue;
            I18N i18n = getI18N((Long)entity.getId(), locale.toString());
            if(i18n!=null){
                entity.setI18nName(i18n.getKeyValue());
            }
            
            entitieMap.put(entity.getId().toString(), tmpEntity);
        }
        
        return entitieMap;
    }
}
