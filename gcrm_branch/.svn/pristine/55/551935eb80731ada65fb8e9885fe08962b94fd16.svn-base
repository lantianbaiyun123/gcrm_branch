package com.baidu.gcrm.valuelist.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 表的基本信息描述
 * 包括缓存的key信息生成
 * @author weichengke
 *
 */
public class TableMetaData {
	private String tableName;//表名称
	private List<ColumnMetaData> columns;
	private List<String> cacheKeyColumns;//用于生成缓存key的列
	private Map<String,Object> whereConditions;
	
	
	public TableMetaData(){
		String[] defaultKeyColumns = {"id"};//默认以id为主键
		cacheKeyColumns = Arrays.asList(defaultKeyColumns);
	}
	
	public TableMetaData(String tableName){
		this.tableName = tableName;
	}
		
	/**
	 * 获取当前表信息的缓存key
	 * @return
	 */
	public String getTableCacheKey(){
		String key = "tableinfo_"+tableName+"_";
		return key;
	}
	
	/**
	 * 获取当前表某一条数据的缓存key
	 * @return
	 */
	public String getRowKey(Map<String,String> values){
		Assert.notNull(cacheKeyColumns,"columns for cache can not be null");
		StringBuffer key = new StringBuffer("rowinfo_"+tableName+"_");
		for(String cloumnName : cacheKeyColumns){
			key.append(cloumnName+"_"+values.get(cloumnName)+"_");
		}
		return key.toString();
	}
	
	//=====getter and setter=====//
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnMetaData> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnMetaData> columns) {
		this.columns = columns;
	}

	public List<String> getCacheKeyColumns() {
		return cacheKeyColumns;
	}

	public void setCacheKeyColumns(List<String> cacheKeyColumns) {
		this.cacheKeyColumns = cacheKeyColumns;
	}

    public Map<String, Object> getWhereConditions() {
        return whereConditions;
    }

    public void setWhereConditions(Map<String, Object> whereConditions) {
        this.whereConditions = whereConditions;
    }
}
