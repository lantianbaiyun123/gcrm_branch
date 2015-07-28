package com.baidu.gcrm.valuelist.dao.impl;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.valuelist.dao.IValuelistDao;
import com.baidu.gcrm.valuelist.utils.ColumnMetaData;
import com.baidu.gcrm.valuelist.utils.MetaDataDesc;
import com.baidu.gcrm.valuelist.utils.QueryCondition;

@Repository
public class ValuelistDaoImpl implements IValuelistDao {
	private static Logger log = LoggerFactory.getLogger(ValuelistDaoImpl.class);
	 
	@PersistenceContext
	private EntityManager em;
	 
	@Autowired
	private DataSource dataSource;

	@Override
	public List<ColumnMetaData> getTableInfo(String tableName) {
		if(StringUtils.isEmpty(tableName)){
			return null;
		}
	
		Connection connection;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			log.info("获取connection信息失败");
			return null;
		}
		
		DatabaseMetaData metaData = null;
		ResultSet rs = null;
		List<ColumnMetaData> result = new ArrayList<ColumnMetaData>();
		try {
			//获取metadata信息
			metaData = connection.getMetaData();
			rs = metaData.getColumns(null,"%",tableName,"%");
			ColumnMetaData md = null;
			while( rs.next() )
			{
				md = new ColumnMetaData();
				md.setColumnName(rs.getString(MetaDataDesc.COLUMN_NAME.toString()));
				md.setColumnType(rs.getString(MetaDataDesc.TYPE_NAME.toString()));
				md.setNullAble((rs.getInt(MetaDataDesc.NULLABLE.toString())==0?false:true));
				result.add(md);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		}finally{
			try {
				if(null!=rs){
					rs.close();
				}
				if(null!=connection){
					connection.close();
				}
			} catch (SQLException e) {
				log.error("关闭连接失败："+e.getMessage());
			}
		}

		return result;
	}

	@Override
	public Long save(QueryCondition condition) {
		Query query = createSaveQuery(condition);
		query.executeUpdate();
		Query queryGetId = em.createNativeQuery("select last_insert_id() ");
		Long id = ((BigInteger)queryGetId.getSingleResult()).longValue();
		return id;
	}

	@Override
	public void delete(QueryCondition condition) {
		Query query = createDeleteQuery(condition);
		query.executeUpdate();
	}

	@Override
	public void update(QueryCondition condition) {
		Query query = createUpdateQuery(condition);
		query.executeUpdate();
	}

	@Override
	public Map<String, String> get(QueryCondition condition) {
		Query query = createSearchQuery(condition);
		
		Object[] values = (Object[])query.getSingleResult();
		int index=0;
		for(String colName : condition.getResultColumns().keySet()){
			condition.getResultColumns().put((colName) , values[index].toString());
			index++;
		}
		
		return condition.getResultColumns();
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<String> loadAllIds(String tableName){
		List<Object> ids = new ArrayList<Object>();
		List<String> idsString = new ArrayList<String>();
		
		String sql ="select id from "+ tableName;
		Query query = em.createNativeQuery(sql);
		ids = query.getResultList();
		for(Object id : ids){
			idsString.add(id.toString());
		}
		
		return idsString;
	}
	
	@SuppressWarnings("unchecked")
    @Override
	public List<Map<String, String>> search(QueryCondition condition) {
		
		Query query = createSearchQuery(condition);
		
		List<Map<String,String>> result= new ArrayList<Map<String,String>>();
		List<Object[]> values = query.getResultList();
		Map<String,String> rowData;
		
		for(int row=0;row<values.size();row++){
			int index=0;
			rowData = new HashMap<String, String>();
			for(String colName : condition.getResultColumns().keySet()){
				rowData.put((colName) , values.get(row)[index].toString());
				index++;
			}
			result.add(rowData);
		}
		
		return result;
	}

	private Query createSaveQuery(QueryCondition condition){
		
		Query query = em.createNativeQuery(condition.createSaveSql());
		
		int index=1;
		for(String colName : condition.getResultColumns().keySet()){
			query.setParameter(index, condition.getResultColumns().get(colName));
			index++;
		}
		if(condition.getConditionColumns()!=null){
			for(String colName : condition.getConditionColumns().keySet()){
				query.setParameter(index, condition.getConditionColumns().get(colName));
				index++;
			}
		}
		
		return query;
	}
	
	private Query createDeleteQuery(QueryCondition condition){
		
		Query query = em.createNativeQuery(condition.createDeleteSql());
		
		int index=1;
		if(condition.getConditionColumns()!=null){
			for(String colName : condition.getConditionColumns().keySet()){
				query.setParameter(index, condition.getConditionColumns().get(colName));
				index++;
			}
		}
		
		return query;
	}
	
	private Query createUpdateQuery(QueryCondition condition){
		
		Query query = em.createNativeQuery(condition.createUpdateSql());
		
		int index=1;
		for(String colName : condition.getResultColumns().keySet()){
			query.setParameter(index, condition.getResultColumns().get(colName));
			index++;
		}
		if(condition.getConditionColumns()!=null){
			for(String colName : condition.getConditionColumns().keySet()){
				query.setParameter(index, condition.getConditionColumns().get(colName));
				index++;
			}
		}
		
		return query;
	}

	
	private Query createSearchQuery(QueryCondition condition){
		
		Query query = em.createNativeQuery(condition.createSearchSql());
		
		int index=1;
		if(condition.getConditionColumns()!=null){
			for(String colName : condition.getConditionColumns().keySet()){
				query.setParameter(index, condition.getConditionColumns().get(colName));
				index++;
			}
		}
		
		return query;
	}

	@SuppressWarnings("unchecked")
    @Override
	public List<Map<String, String>> getAll(QueryCondition condition) {
		Query query = createSearchQuery(condition);
		List<Object[]> rows = (List<Object[]>)query.getResultList();
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		for(Object[] row : rows){
			int index=0;
			Map<String,String> result = new HashMap<String, String>();
			for(String colName : condition.getResultColumns().keySet()){
				if(row[index]!=null){
					result.put((colName) , row[index].toString());
				}else{
					result.put((colName) , null);
				}
				index++;
			}
			results.add(result);
		}
		return results;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> loadIdList(String tableName) {
        List<Long> ids = new ArrayList<Long>();
        String sql ="select id from "+ tableName;
        Query query = em.createNativeQuery(sql);
        ids = (List<Long>)query.getResultList();
        return ids;
    }
}
