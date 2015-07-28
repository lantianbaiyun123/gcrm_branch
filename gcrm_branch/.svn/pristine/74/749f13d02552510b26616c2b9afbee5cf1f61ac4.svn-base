package com.baidu.gcrm.valuelist.dao;

import java.util.List;
import java.util.Map;

/**
 * 对缓存数据的增删查改
 * 暂时不提供按条件查询，目前没发现有这方面的需求
 * @author weichengke
 *
 */
public interface IValuelistCacheDao {
	
	/**
	 * 增加一条数据
	 * @param tableName
	 * @param values
	 */
	void add(String tableName,Map<String,String> values);
	
	/**
	 * 根据id加载一条数据
	 * @param tableName
	 * @param idVaue
	 */
	Map<String,String> get(String tableName,String idVaue);
	
	/**
	 * 根据主键列加载一条数据
	 * @param tableName
	 * @param values
	 */
	Map<String,String> get(String tableName,Map<String,String> values);
	
	/**
	 * 根据id删除一条数据
	 */
	void delete(String tableName,String idVaue);
	
	/**
	 * 根据主键删除一条数据
	 * @param tableName
	 * @param values
	 */
	void delete(String tableName,Map<String,String> values);
		
	/**
	 * 获取全部的数据
	 * @return
	 */
	List<Map<String,String>> findAll(String tableName);
	
	/**
	 * 获取一页数据
	 * @return
	 */
	List<Map<String,String>> findPage(String tableName,int numPerPage,int currentPage);

	void deleteByCacheKey(String tableName, String cacheKey);
	
}
