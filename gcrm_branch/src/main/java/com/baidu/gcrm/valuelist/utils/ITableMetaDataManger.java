package com.baidu.gcrm.valuelist.utils;

import java.util.List;
import java.util.Map;


/**
 * 值列表表信息管理
 * @author weichengke
 *
 */
public interface ITableMetaDataManger {
	/**
	 * 获得某个表的描述信息
	 * @param tableName
	 * @return
	 */
	TableMetaData getTableMetaData(String tableName);
	
	/**
	 * 加载所有表的描述信息
	 */
	void loadTableMetaData();
	
	/**
	 * 获取某个表信息的缓存key
	 * @return
	 */
	String getTableCacheKey(String tableName);
	
	/**
	 * 获取某个表某一条数据的缓存key
	 * @return
	 */
	String getRowKey(String tableName,Map<String,String> values);
	
	List<String> getTableNames();

	List<TableMetaData> getTableMetas();
}
