package com.baidu.gcrm.valuelist.service;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.valuelist.utils.TableMetaData;

public interface IValuelistWithCacheService {

	/**
	 * 增加一条数据，并加入缓存
	 * @param tableName
	 * @param params
	 */
	public void save(String tableName, Map<String, String> params);

	/**
	 * 更新一条数据
	 * @param tableName
	 * @param keyName
	 * @param keyValue
	 * @param params
	 */
	public void update(String tableName, String keyName,
			String keyValue, Map<String, String> params,String cacheKey);

	/**
	 * 删除一条数据并更新缓存
	 * @param tableName
	 * @param keyName
	 * @param keyValue
	 */
	public void delete(String tableName, String keyName,
			String keyValue);

	/**
	 * 加载一条数据，并放入缓存中
	 * @param tableName
	 * @param keyName
	 * @param keyValue
	 * @return
	 */
	public Map<String, String> get(String tableName, String keyName,
			String keyValue);

	/**
	 * 加载所有数据，并放入缓存
	 */
	public List<Map<String, String>> getAll(TableMetaData tableMetaData);
	
	/**
	 * 从缓存中加载所有数据
	 */
	public List<Map<String, String>> getAllFromCache(String tableName);

	/**
	 * 刷新缓存
	 * @param tableName
	 */
	void refreshCache(TableMetaData tableMetaData);
}