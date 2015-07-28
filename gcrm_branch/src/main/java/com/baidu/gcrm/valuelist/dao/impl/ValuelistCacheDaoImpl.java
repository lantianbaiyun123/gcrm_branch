package com.baidu.gcrm.valuelist.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.common.cache.EhcacheProxy;
import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;

@Repository
public class ValuelistCacheDaoImpl implements IValuelistCacheDao {
	private Logger log = LoggerFactory.getLogger(ValuelistCacheDaoImpl.class);

	@Autowired
	@Qualifier("valuelistehcacheProxy")
	private EhcacheProxy<Object> ehcacheProxy;
	
	@Autowired
	@Qualifier("valueListEhcache")
	private Ehcache cache;
	
	@Autowired
	private ITableMetaDataManger tableManager ;
	
	@Override
	public void add(String tableName, Map<String, String> values) {
		String key = tableManager.getRowKey(tableName, values);
		ehcacheProxy.put(key, values);
		log.debug("值列表"+key+"加入缓存");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> get(String tableName, String idVaue) {
		Map<String,String> values = new HashMap<String, String>();
		values.put("id", idVaue);
		String key = tableManager.getRowKey(tableName, values);
		log.debug("从缓存中加载值列表"+key);
		return (Map<String,String>)ehcacheProxy.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> get(String tableName, Map<String, String> values) {
		String key = tableManager.getRowKey(tableName, values);
		log.debug("从缓存中加载"+tableName+"的数据id="+values.get("id"));
		return (Map<String,String>)ehcacheProxy.get(key);
	}

	@Override
	public void delete(String tableName, String idVaue) {
		@SuppressWarnings("unchecked")
		List<String> keys = cache.getKeys();
		
		String tablePre = "rowinfo_"+tableName;
		for(String key : keys){
			if(key.contains(tablePre)&&key.contains("id_"+idVaue)){
				ehcacheProxy.remove(key);	
				log.debug("值列表"+key+"移除缓存");
			}
		}
	}

	@Override
	public void delete(String tableName, Map<String, String> values) {
		ehcacheProxy.remove(tableManager.getRowKey(tableName, values));
		log.debug("值列表"+tableName+":"+values.get("id")+"移除缓存");
	}
	
	@Override
	public void deleteByCacheKey(String tableName, String cacheKey) {
		if(StringUtils.isEmpty(cacheKey)){
			return;
		}
		
		ehcacheProxy.remove(cacheKey);
		log.debug("值列表"+tableName+":"+cacheKey+"移除缓存");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findAll(String tableName) {
		List<String> keys = cache.getKeys();
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		for(String key : keys){
			if(key.startsWith("rowinfo_"+tableName)){
				Element element = cache.get(key);
				if(element!=null){
					results.add((Map<String, String>) element.getObjectValue());
				}
			}
		}
		log.debug("缓存中取出"+tableName+"表的数据"+results.size()+"条");
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> findPage(String tableName, int numPerPage,
			int currentPage) {
		List<String> keys = cache.getKeys();
		int total = keys.size();
		int start = (currentPage-1)*numPerPage;
		if(start>total) {
			return Collections.emptyList();
		}
		int end = currentPage * numPerPage;
		if(end>total){
			end = total;
		}
		
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		for(int i=start;i<end;i++){
			results.add((Map<String, String>) cache.get(keys.get(i)));
		}
		return results;
	}
	
    public void setCache(Ehcache cache) {
        this.cache = cache;
    }
    
    public void setEhcacheProxy(EhcacheProxy<Object> ehcacheProxy) {
        this.ehcacheProxy = ehcacheProxy;
    }

    public void setTableManager(ITableMetaDataManger tableManager) {
        this.tableManager = tableManager;
    }
}