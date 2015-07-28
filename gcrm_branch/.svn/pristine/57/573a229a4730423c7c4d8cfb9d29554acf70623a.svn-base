package com.baidu.gcrm.valuelist.dao;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.valuelist.utils.ColumnMetaData;
import com.baidu.gcrm.valuelist.utils.QueryCondition;

public interface IValuelistDao {
	/**
	 * 获取表信息
	 * @param tableName
	 * @return
	 */
	List<ColumnMetaData> getTableInfo(String tableName);
	
	/**
	 * 保存一条数据
	 * @param condition
	 * @return
	 */
	Long save(QueryCondition condition);
	
	/**
	 * 删除一条数据
	 * @param condition
	 * @return
	 */
	void delete(QueryCondition condition);
	
	/**
	 * 更新一条数据
	 * @param condition
	 * @return
	 */
	void update(QueryCondition condition);
	
	/**
	 * 获取一条数据
	 * @param condition
	 * @return
	 */
	Map<String,String> get(QueryCondition condition);
	
	/**
	 * 按条件查询数据
	 * @param condition
	 * @return
	 */
	List<Map<String,String>> search(QueryCondition condition);
	
	/**
	 * 加上所有数据的id
	 * @param tableName
	 * @return
	 */
	List<String> loadAllIds(String tableName);
	
	/**
     * 加上所有数据的id
     * @param tableName
     * @return
     */
    List<Long> loadIdList(String tableName);
	
	
	/**
	 * 获取所有数据
	 */
	List<Map<String, String>> getAll(QueryCondition condition);
}
