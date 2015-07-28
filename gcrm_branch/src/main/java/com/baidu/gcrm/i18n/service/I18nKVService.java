package com.baidu.gcrm.i18n.service;

import java.io.Serializable;
import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.model.IBaseI18nModel;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

public interface I18nKVService {
    
    I18nKV findById(Long id);
    
    void deleteById(Class<?> clazz,Serializable id);
    
    void deleteByKeyAndLocale(String key, LocaleConstants locale);
    
	I18nKV findByKeyAndLocale(String key,LocaleConstants locale);
	
	I18nKV findByIdAndLocale(Class<?> clazz, Serializable id, LocaleConstants locale);
	
	List<I18nKV> findById(Class<?> clazz, Serializable id);
	
	List<I18nKV> findByKey(String key);
	
	List<I18nKV> findAll();
	
	void save(I18nKV i18nKV);

	I18nKV save(Class<?> clazz, Serializable id, LocaleConstants locale,String value);
	
	List<I18nKV> save(Class<?> clazz, Serializable id, List<LocaleVO> values);
	
	I18nKV save(Class<?> clazz, Serializable id, LocaleVO localeVO);
	
	void fillI18nInfo(List<? extends IBaseI18nModel> i18nModels, final LocaleConstants locale);
	
	void fillI18nInfo(IBaseI18nModel i18nModels, final LocaleConstants locale);
	
	String getI18Info(Class<?> clazz,Long id,final LocaleConstants locale);
	
	I18nKV getAndLoadI18Info(Class<?> clazz,Long id,final LocaleConstants locale);
	
	List<I18nKV> findExistsValue(Class<?> clazz, Long id, List<LocaleVO> values);
	
	List<I18nKV> findByAdPlatformIdAndSiteId(Long adPlatformId, Long siteId);
	
	List<I18nKV> findByIndexStr(Long excludePositionId, String indexStr);
	
	void updateSubPositionExtraName(String indexStr,PositionType type, LocaleConstants locale);
	
	List<I18nKV> findByKeyNameLikeAndLocale(String keyName,LocaleConstants locale);
}
