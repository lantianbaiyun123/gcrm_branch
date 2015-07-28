package com.baidu.gcrm.log.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.log.model.ModifyRecord;

public interface ModifyRecordService {
	
	/**保存被修改数据的修改记录
	 * 
	 * @param clazz 被修改数据的类
	 * @param newValue 修改后的值
	 * @param oldValue 修改前的值
	 */
	public <T> void saveModifyRecord(Class<T> clazz,T newValue,T oldValue);
	
	/**
	 * 保存并返回被修改数据的修改记录
	 * @param clazz
	 * @param newValue
	 * @param oldValue
	 * @return 修改的记录列表
	 */
	public <T> List<ModifyRecord> saveModifyRecordAndReturn(Class<T> clazz,T newValue,T oldValue);
	
	/**
	 * 保存修改记录
	 * @param records 修改记录
	 */
	public void saveModifyRecord(List<ModifyRecord> records);
	
	/**
	 * 保存被修改数据的修改记录
	 * @param clazz 被修改数据的类
	 * @param vlaue 被修改数据
	 */
	public <T> void saveModifyRecord(Class<T> clazz,T vlaue);
	
	public void saveModifyRecord(Object entity);
	
	/**
	 * @param className 修改记录的类名称
	 * @param id  被修改数据的ID
	 * @return
	 */
	public List<ModifyRecord> findModifyRecord(String className,Long id);
	
	/**
	 * 获取国际化处理后的修改记录
	 * 
	 * @param className 修改记录的类名称
	 * @param id 被修改数据的ID
	 * @param locale 本地信息
	 * @return
	 */
	public List<Map<String,Object>> findModifyRecord(String className,Long id,LocaleConstants locale);
	
	
	public List<Map<String,Object>> findModifyRecord(Map<String,List<Long>> classAndIdMap,LocaleConstants locale);
	
	/**
	 * 记录新增对象信息
	 * @param clazz 新增对象的父类
	 * @param parentId 新增对象的父ID
	 * @param content  新增描述信息
	 */
	public <T> void insertRecord(Class<T> superClazz,Long parentId,String content,String parameter);
	/**
	 * 
	 * 记录对象删除信息
	 * 
	 * @param clazz 删除对象的父类
	 * @param parentId 删除对象的父ID
	 * @param content  删除描述信息
	 */
	public <T> void deleteRecord(Class<T> superClazz,Long parentId,String content,String parameter);
	
	public String getI18NMessage(String key,Locale locale);

}
