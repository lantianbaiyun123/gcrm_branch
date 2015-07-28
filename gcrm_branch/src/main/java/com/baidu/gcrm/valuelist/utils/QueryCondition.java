package com.baidu.gcrm.valuelist.utils;

import java.util.Map;



import org.apache.commons.lang.StringUtils;

/**
 * 查询的基本信息
 * @author weichengke
 *
 */
public class QueryCondition {
	
	private String tableName;	
	//查询结果
	private Map<String,String> resultColumns=null;
	
	//目前无用！
	private Map<String,String> newValueColumns = null;
	
	//查询条件列，用于生成where子句
	private Map<String,Object> conditionColumns=null;
	
	//用于存放排序信息，保留
	private Map<String,String> orderColumns = null;
	
	public QueryCondition(){}
	

	public QueryCondition(String tableName,Map<String, String> resultColumns,
			Map<String, String> newValueColumns,
			Map<String, Object> conditionColumns,
			Map<String, String> orderColumns) {

		this.tableName = tableName;
		this.resultColumns = resultColumns;
		this.newValueColumns = newValueColumns;
		this.conditionColumns = conditionColumns;
		this.orderColumns = orderColumns;
	}
	
	@SuppressWarnings("unused")
    public String createSaveSql(){
		StringBuffer sqlString = new StringBuffer("insert into "+tableName+" (");
		
		for(String colName : resultColumns.keySet()){
			sqlString.append(colName+",");
		}
		sqlString.setLength(sqlString.length()-1);
		
		sqlString.append(") values(");
		for(String colName : resultColumns.keySet()){
			sqlString.append("?,");
		}
		sqlString.setCharAt(sqlString.length()-1, ')');
		
		return sqlString.toString();
	}
	
	public String createDeleteSql(){
		StringBuffer sqlString = new StringBuffer("delete from "+tableName);
		if(conditionColumns!=null){
			sqlString.append(createWhereClause());
		}
		return sqlString.toString();
	}
	
	public String createUpdateSql(){
		StringBuffer sqlString = new StringBuffer("update "+tableName+" set ");
		
		for(String colName : resultColumns.keySet()){
			sqlString.append(colName+"=?,");
		}
		sqlString.setCharAt(sqlString.length()-1, ' ');
		if(conditionColumns!=null){
			sqlString.append(createWhereClause());
		}
		
		return sqlString.toString();
	}
	
	public String createSearchSql(){
		StringBuffer sqlString = new StringBuffer("select ");
		
		if(resultColumns==null ||resultColumns.size()==0 ){
			return null;
		}
		for(String colName:resultColumns.keySet()){
			sqlString.append(colName+",");
		}
		
		sqlString.setCharAt(sqlString.length()-1, ' ');
		sqlString.append("from "+ tableName);
		if(conditionColumns!=null){
			sqlString.append(createWhereClause());
		}
		
		return sqlString.toString();
	}
	
	private String createWhereClause(){
		StringBuffer sqlString = new StringBuffer(" where 1=1");
		for(String colName : conditionColumns.keySet()){
			sqlString.append(" and "+colName+"=?");
		}
		return sqlString.toString();
	}


	
	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public Map<String, String> getResultColumns() {
		return resultColumns;
	}


	public void setResultColumns(Map<String, String> resultColumns) {
		this.resultColumns = resultColumns;
	}


	public Map<String, String> getNewValueColumns() {
		return newValueColumns;
	}


	public void setNewValueColumns(Map<String, String> newValueColumns) {
		this.newValueColumns = newValueColumns;
	}


	public Map<String, Object> getConditionColumns() {
		return conditionColumns;
	}


	public void setConditionColumns(Map<String, Object> conditionColumns) {
		this.conditionColumns = conditionColumns;
	}


	public Map<String, String> getOrderColumns() {
		return orderColumns;
	}


	public void setOrderColumns(Map<String, String> orderColumns) {
		this.orderColumns = orderColumns;
	}	
	
	public String toString(){
		String condition = createSearchSql();
		if(StringUtils.isEmpty(condition)){
			return null;
		}

		return condition;
	}
}
