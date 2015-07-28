package com.baidu.gcrm.log.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.log.service.ModifyRecordConstant;

public class ModifyRecordRepositoryImpl implements ModifyRecordRepositoryCustome{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findOldValuesById(Class<T> clazz, Long id) {
		Query query = entityManager.createQuery("from " + clazz.getSimpleName()+ " where id=?");
		query.setParameter(1, id);
		return (T)query.getSingleResult();
	}
	
	@Override
	public List<Map<String,Object>> findModifRecords(String className, Long id) {
		return findModifRecords(className,id,LocaleConstants.zh_CN);
	}

	@Override
	public List<Map<String,Object>> findModifRecords(String className, Long id,LocaleConstants locale) {
		Query query = entityManager.createNativeQuery("select r.modified_data_id as modifiedDataId,t.table_field,t.field_name,"
				+ "r.operate_type as operateType,r.old_value as oldValue,r.new_value as newValue,r.create_time as createTime,"
				+ "r.create_operator as createOperator from g_modify_record r left join g_modify_table_info t on r.table_name = t.table_name "
				+ "and r.modify_field = t.table_field and t.local=? where r.table_name=? and r.modified_data_id=? order by createTime desc");
		//query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setParameter(1, locale.name());
		query.setParameter(2, className);
		query.setParameter(3, id);
		List<Object[]> result = query.getResultList();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Object[] record : result){
			Map<String,Object> reMap = new HashMap<String,Object>();
			if(record !=null && record.length>=7){
				int i=0;
				reMap.put(ModifyRecordConstant.MODIFIEDDATAID_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.TABLEFIELD_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.FIELDNAME_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.OPERATETYPE_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.OLDVALUE_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.NEWVALUE_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.CREATETIME_KEY, record[i++]);
				reMap.put(ModifyRecordConstant.CREATEOPERATOR_KEY, record[i++]);
				list.add(reMap);
			}
		}
		return list;
	}

    @Override
    public List<Map<String, Object>> findModifRecords(Map<String, List<Long>> classAndIdMap, LocaleConstants locale) {
      
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select r.modified_data_id as modifiedDataId,t.table_field,t.field_name,");
        sqlBuffer.append("r.operate_type as operateType,r.old_value as oldValue,r.new_value as newValue,r.create_time as createTime,");
        sqlBuffer.append("r.create_operator as createOperator from g_modify_record r  join g_modify_table_info t on r.table_name = t.table_name ");
        sqlBuffer.append("and r.modify_field = t.table_field and t.local=?1  where 1<>1");
        Map<Integer,Object> parametersMap =  new HashMap<Integer,Object>();
        parametersMap.put(1, locale.name());
        Set<String> classNameList = classAndIdMap.keySet();
        StringBuffer conditionSql = new StringBuffer();
        Integer parameterNum = 1;
        for(String className:classNameList){
            parameterNum ++;
            conditionSql.append(" or ( r.table_name=?"+parameterNum+ " and r.modified_data_id in (?"+(parameterNum+1)+" ))");
            parametersMap.put(parameterNum, className);
            parameterNum ++; 
            parametersMap.put(parameterNum, classAndIdMap.get(className));
        }
        sqlBuffer.append(conditionSql);
        sqlBuffer.append(" order by createTime desc");
        conditionSql =null;
        Query query = entityManager.createNativeQuery(sqlBuffer.toString());
        
        //query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
       Set<Integer> numList = parametersMap.keySet();
        for(Integer num:numList){
            query.setParameter(num,parametersMap.get(num));
        }
        List<Object[]> result = query.getResultList();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for(Object[] record : result){
            Map<String,Object> reMap = new HashMap<String,Object>();
            if(record !=null && record.length>=7){
                int i=0;
                reMap.put(ModifyRecordConstant.MODIFIEDDATAID_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.TABLEFIELD_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.FIELDNAME_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.OPERATETYPE_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.OLDVALUE_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.NEWVALUE_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.CREATETIME_KEY, record[i++]);
                reMap.put(ModifyRecordConstant.CREATEOPERATOR_KEY, record[i++]);
                list.add(reMap);
            }
        }
        return list;
    }

}
