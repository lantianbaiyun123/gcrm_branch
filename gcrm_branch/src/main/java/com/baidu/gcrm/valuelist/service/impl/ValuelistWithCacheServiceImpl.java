package com.baidu.gcrm.valuelist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.valuelist.dao.IValuelistCacheDao;
import com.baidu.gcrm.valuelist.dao.IValuelistDao;
import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ColumnMetaData;
import com.baidu.gcrm.valuelist.utils.QueryCondition;
import com.baidu.gcrm.valuelist.utils.TableMetaData;

@Service
public class ValuelistWithCacheServiceImpl implements IValuelistWithCacheService  {
	
	private static Logger log = LoggerFactory.getLogger(ValuelistWithCacheServiceImpl.class);

	@Autowired
	private IValuelistDao valuelistDao;
	
	@Autowired
	private IValuelistCacheDao valuelistCacheDao;

	/* (non-Javadoc)
	 * @see com.baidu.gcrm.valuelist.service.impl.IValuelistServiceWithCache#save(java.lang.String, java.util.Map)
	 */
	@Override
	public void save(String tableName, Map<String, String> params) {
		
		QueryCondition condition = new QueryCondition(tableName, params, null, null, null);
		
		Long id = valuelistDao.save(condition);
		params.put("id", id.toString());
		valuelistCacheDao.add(tableName, params);
	}

	/* (non-Javadoc)
	 * @see com.baidu.gcrm.valuelist.service.impl.IValuelistServiceWithCache#update(java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public void update(String tableName, String keyName, String keyValue,
			Map<String, String> params,String cacheKey) {
		Map<String,Object> conditionColumns = new HashMap<String, Object>();
		conditionColumns.put(keyName, keyValue);
		QueryCondition condition = new QueryCondition(tableName, params, null, conditionColumns, null);
		
		valuelistDao.update(condition);
		valuelistCacheDao.deleteByCacheKey(tableName, cacheKey);
		valuelistCacheDao.add(tableName, params);
	}

	/* (non-Javadoc)
	 * @see com.baidu.gcrm.valuelist.service.impl.IValuelistServiceWithCache#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String tableName, String keyName, String keyValue) {
		Map<String,Object> conditionColumns = new HashMap<String, Object>();
		conditionColumns.put(keyName, keyValue);
		QueryCondition condition = new QueryCondition(tableName, null, null, conditionColumns, null);
		
		valuelistDao.delete(condition);
		valuelistCacheDao.delete(tableName, keyValue);
	}

	/* (non-Javadoc)
	 * @see com.baidu.gcrm.valuelist.service.impl.IValuelistServiceWithCache#get(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String,String> get(String tableName, String keyName, String keyValue) {
		Map<String,Object> conditionColumns = new HashMap<String, Object>();
		Map<String,String> params = new HashMap<String, String>();
		
		conditionColumns.put(keyName, keyValue);
		
		List<ColumnMetaData> mds = valuelistDao.getTableInfo(tableName);
		for(ColumnMetaData md :mds){
			params.put(md.getColumnName(), "");
		}
		
		QueryCondition condition = new QueryCondition(tableName, params, null, conditionColumns, null);
		Map<String,String> result = null;
		result = valuelistDao.get(condition);
		//valuelistCacheDao.add(tableName, result);
		return result;
	}


	/* (non-Javadoc)
	 * @see com.baidu.gcrm.valuelist.service.impl.IValuelistServiceWithCache#getAll(java.lang.String)
	 */
	@Override
	public List<Map<String, String>> getAll(TableMetaData tableMetaData) {
		
		Map<String,String> params = new HashMap<String, String>();
		
		String tableName = tableMetaData.getTableName();
		List<ColumnMetaData> mds = valuelistDao.getTableInfo(tableName);
		for(ColumnMetaData md :mds){
			params.put(md.getColumnName(), "");
		}
		;
		QueryCondition condition = new QueryCondition(tableName, params, null, tableMetaData.getWhereConditions(), null);
		List<Map<String,String>> results = null;
		results = valuelistDao.getAll(condition);
		
		return results;
	}

	@Override
	public List<Map<String, String>> getAllFromCache(String tableName) {
		
		List<Map<String, String>> result = new ArrayList<Map<String,String>>();
		
		result = valuelistCacheDao.findAll(tableName);
		
		return result;
	}
	
	@Override
	public void refreshCache(TableMetaData tableMetaData){
	    String tableName = tableMetaData.getTableName();
		List<Map<String, String>> rows = getAll(tableMetaData);
		List<Map<String, String>> elements = getAllFromCache(tableName);
		
		int index4=0;//删除多少条数据
		int index5=0;//更新了多少条数据
		if(elements!=null){
			//更新缓存中的数据，去除缓存中的冗余数据
			for(Map<String,String> element : elements){
				//int index = 0;
				boolean flag = false;
				for(Map<String,String> row:rows){
					try{
						if(element.get("id").equals(row.get("id"))){
							flag = true;//在缓存中找到了当前数据
							if(!element.equals(row)){//如果当前数据和数据库中不等，则更新
								valuelistCacheDao.delete(tableName, element);
								valuelistCacheDao.add(tableName, row);
								index5++;
								log.debug(tableName+"-"+row.get("id")+" 数据刷新成功");
							}
							break;
						}
					}catch(Exception ex){
						log.error(tableName+"-"+row.get("id")+" 数据刷新失败");
					}
					
				}
				if(!flag){//数据库中没有当前数据，删除
					valuelistCacheDao.delete(tableName, element);
					index4++;
					log.debug(tableName+"-"+element.get("id")+"数据过期移除");
				}
			}
		}
		
		if(rows!=null){
			//添加数据中存在，缓存中不存在的数据
			int index = 0;//数据库中多少条数据
			int index2=0;//缓存中多少条数据
			int index3=0;//新加入多少条数据
			for(Map<String,String> row:rows){
				index++;
				boolean flag = false;//是否在缓存中找到这条数据
				try{
					for(Map<String,String> element : elements){
						if(element.get("id").equals(row.get("id"))){
							index2++;
							flag = true;
							break;
						}
					}
					if(!flag){//数据库中有，缓存中没有
						valuelistCacheDao.add(tableName, row);
						index3++;
						log.debug(tableName+"-"+row.get("id")+"加入缓存");
					}
				}catch(Exception ex){
					log.error(tableName+"-"+row.get("id")+" 数据刷新失败");
				}
			}
			log.debug("======数据库"+tableName+"共"+index+"条数据");
			log.debug("======缓存中存在"+index2+"条数据");
			log.debug("=====新加入了="+index3+"条数据");
			log.debug("=====删除了="+index4+"条数据");
			log.debug("=====更新了="+index5+"条数据");
		}
	}
}