package com.baidu.gcrm.log.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.util.BeanModifyUtil;
import com.baidu.gcrm.log.dao.ModifyRecordRepository;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.log.model.ModifyRecord.OperateType;
import com.baidu.gcrm.log.service.ModifyRecordService;

@Service
public class ModifyRecordServiceImpl implements ModifyRecordService {
	
	private static Logger log = LoggerFactory.getLogger(ModifyRecordServiceImpl.class);
	
	@Autowired
	private ModifyRecordRepository modifyRecordRepository;

	@SuppressWarnings("rawtypes")
	@Override
	public <T> void saveModifyRecord(Class<T> clazz, T newValue, T oldValue) {
		try {
			List<List> modifies = BeanModifyUtil.getModifyValues(clazz, newValue, oldValue);
			List<ModifyRecord> records = new ArrayList<ModifyRecord>();
			for(List modify:modifies){
				if(modify.size()>=3){
					ModifyRecord record = new ModifyRecord();
					record.setModifiedDataId(getId(clazz,newValue));
					record.setTableName(clazz.getSimpleName());
					record.setModifyField(modify.get(0).toString());
					Object value1 = modify.get(1);
					if(value1!=null){
						record.setNewValue(value1.toString());
					}
					Object value2 = modify.get(2);
					if(value2!=null){
						record.setOldValue(value2.toString());
					}
					if(value1!=null && value2!=null){
						record.setOperateType(OperateType.modify);
					}else if(value1==null && value2!=null){
						record.setOperateType(OperateType.delete);
					}else if(value1!=null && value2==null){
						record.setOperateType(OperateType.insert);
					}
					record.setCreateOperator(RequestThreadLocal.getLoginUserId());
					record.setCreateTime(new Date());
					records.add(record);
				}
			}
			modifyRecordRepository.save(records);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Override
	public <T> List<ModifyRecord> saveModifyRecordAndReturn(Class<T> clazz, T newValue, T oldValue) {
		List<ModifyRecord> records = new ArrayList<ModifyRecord>();
		try {
			List<List> modifies = BeanModifyUtil.getModifyValues(clazz, newValue, oldValue);
			for(List modify:modifies){
				if(modify.size()>=3){
					ModifyRecord record = new ModifyRecord();
					record.setModifiedDataId(getId(clazz,newValue));
					record.setTableName(clazz.getSimpleName());
					record.setModifyField(modify.get(0).toString());
					Object value1 = modify.get(1);
					if(value1!=null){
						record.setNewValue(value1.toString());
					}
					Object value2 = modify.get(2);
					if(value2!=null){
						record.setOldValue(value2.toString());
					}
					if(value1!=null && value2!=null){
						record.setOperateType(OperateType.modify);
					}else if(value1==null && value2!=null){
						record.setOperateType(OperateType.delete);
					}else if(value1!=null && value2==null){
						record.setOperateType(OperateType.insert);
					}
					record.setCreateOperator(RequestThreadLocal.getLoginUserId());
					record.setCreateTime(new Date());
					records.add(record);
				}
			}
			modifyRecordRepository.save(records);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return records;
	}

	@Override
	public void saveModifyRecord(List<ModifyRecord> records) {
		try {
			modifyRecordRepository.save(records);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Override
	public <T> void saveModifyRecord(Class<T> clazz,
			T value) {
		try {
			T oldValue = modifyRecordRepository.findOldValuesById(
					clazz, getId(clazz,value));
			this.saveModifyRecord(clazz, value, oldValue);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public <T> void insertRecord(Class<T> clazz,Long parentId,String content,String parameter){
		ModifyRecord record = new ModifyRecord();
		record.setModifiedDataId(parentId);
		record.setTableName(clazz.getSimpleName());
		record.setOperateType(OperateType.insert);
		record.setNewValue(content);
		record.setOldValue(parameter);
		record.setCreateTime(new Date());
		record.setCreateOperator(RequestThreadLocal.getLoginUserId());
		modifyRecordRepository.save(record);
	}
	
	public <T> void deleteRecord(Class<T> clazz,Long parentId,String content,String parameter){
		ModifyRecord record = new ModifyRecord();
		record.setModifiedDataId(parentId);
		record.setTableName(clazz.getSimpleName());
		record.setOperateType(OperateType.delete);
		record.setNewValue(content);
		record.setOldValue(parameter);
		record.setCreateOperator(RequestThreadLocal.getLoginUserId());
		record.setCreateTime(new Date());
		modifyRecordRepository.save(record);
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED) 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveModifyRecord(Object entity){
		try {
			Class clazz = entity.getClass();
			Object oldValue = modifyRecordRepository.findOldValuesById(
					clazz , getId(clazz,entity));
			this.saveModifyRecord(clazz, entity, oldValue);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public List<Map<String,Object>> findModifyRecord(String className,Long id,LocaleConstants locale){
		return modifyRecordRepository.findModifRecords(className, id,locale);
	}
	public List<Map<String,Object>> findModifyRecord(Map<String,List<Long>> classAndIdMap,LocaleConstants locale){
        return modifyRecordRepository.findModifRecords(classAndIdMap,locale);
    }
	private <T> Long getId(Class<T> clazz,T value) throws Exception{
		Object o = getPropertyValue(clazz,value,"id");
		if(o!=null){
			return (Long)o;
		}
		return null;
	}
	
	protected <T> Object getPropertyValue(Class<T> clazz,T value,String fieldName) throws Exception{
		Field field = clazz.getDeclaredField(fieldName);
		PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
				clazz);
		Method getMethod = pd.getReadMethod();
		return getMethod.invoke(value);
	}
	

	public List<ModifyRecord> findModifyRecord(String className,Long id){
		return modifyRecordRepository.findByTableNameAndModifiedDataIdOrderByCreateTimeDesc(className,id);
	}

	@Override
	public String getI18NMessage(String key, Locale locale) {
		return ServiceBeanFactory.getMessageSource().getMessage(key,null, locale);
	}

}
