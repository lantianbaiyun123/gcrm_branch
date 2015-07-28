package com.baidu.gcrm.log.dao;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;

public interface ModifyRecordRepositoryCustome {
	
	public <T> T findOldValuesById(Class<T> clazz,Long id);
	
	public List<Map<String,Object>> findModifRecords(String className,Long id);
	public List<Map<String,Object>> findModifRecords(String className, Long id,LocaleConstants locale);
	
	public List<Map<String,Object>> findModifRecords(Map<String,List<Long>> classAndIdMap ,LocaleConstants locale);

	
}
