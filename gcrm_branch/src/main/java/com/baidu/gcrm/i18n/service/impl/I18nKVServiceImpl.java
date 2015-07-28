package com.baidu.gcrm.i18n.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.cache.CacheTemplate;
import com.baidu.gcrm.common.cache.DataLoader;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.i18n.dao.I18nKVDao;
import com.baidu.gcrm.i18n.dao.I18nKVDaoCustom;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.model.IBaseI18nModel;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

@Service
public class I18nKVServiceImpl implements I18nKVService{
    
    private static Logger logger = LoggerFactory.getLogger(I18nKVServiceImpl.class);
    
	@Autowired
	private I18nKVDao i18nDao;
	
	@Autowired
	I18nKVDaoCustom i18nDaoCustom;
	
	@Autowired
	CacheTemplate<I18nKV> ehcacheTemplate;
	
	@Override
	public I18nKV save(Class<?> clazz,Serializable id, LocaleConstants locale,
			String value) {
		String key;
        try {
            key = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("save i18n failed! object is not a persistence entity!");
            return null;
        }
		I18nKV i18n = buildI18nKVObject(key,locale,value);
		return i18nDao.save(i18n);
	}
	
	@Override
	public I18nKV findByKeyAndLocale(String key, LocaleConstants locale) {
		return i18nDao.findByKeyAndLocale(key, locale);
	}

	@Override
	public List<I18nKV> findByKey(String key) {
		return i18nDao.findByKey(key);
	}
	
	@Override
	public List<I18nKV> save(Class<?> clazz, Serializable id, List<LocaleVO> values) {
		List<I18nKV> entities = null;
		if(values != null && values.size() > 0){
			entities = new ArrayList<I18nKV> ();
			String key;
            try {
                key = MessageHelper.generateI18nKey(clazz, id);
            } catch (CRMBaseException e) {
                logger.error("save i18n failed! object is not a persistence entity!");
                return null;
            }
			for(LocaleVO temLocaleVO : values){
				entities.add(buildI18nKVObject(key,temLocaleVO));
			}
			
			i18nDao.save(entities);
		}
		
		return entities;
	}
	
	private I18nKV buildI18nKVObject(String key,LocaleConstants locale, String value){
        
        I18nKV i18n = new I18nKV();
        i18n.setKey(key);
        i18n.setLocale(locale);
        i18n.setValue(value);
        return i18n;
        
    }
	
	private I18nKV buildI18nKVObject(String key,LocaleVO localeVO){
		
		I18nKV i18n = new I18nKV();
		i18n.setKey(key);
		i18n.setLocale(localeVO.getLocale());
		i18n.setValue(localeVO.getValue());
		i18n.setExtraValue(localeVO.getExtraValue());
		return i18n;
		
	}

	@Override
	public void fillI18nInfo(List<? extends IBaseI18nModel> i18nModels, final LocaleConstants locale) {
	    if(CollectionUtils.isEmpty(i18nModels)){
	        return;
	    }
	    for(IBaseI18nModel temIBaseI18nModel : i18nModels){
	        this.fillI18nInfo(temIBaseI18nModel,locale);
	    }
	}
	
	@Override
    public void fillI18nInfo(IBaseI18nModel i18nModels,final LocaleConstants locale) {
		String i18n = getI18Info(i18nModels.getClass(),i18nModels.getId(),locale);
		i18nModels.setI18nName(i18n);
    }
	
	public String getI18Info(Class<?> clazz,Long id,final LocaleConstants locale) {
        I18nKV i18n = getAndLoadI18Info(clazz, id, locale);
        if(i18n != null){
            return i18n.getValue();
        }
        return null;
	}

    @Override
    public void save(I18nKV i18nKV) {
        i18nDao.save(i18nKV);
    }

    @Override
    public I18nKV findById(Long id) {
        return i18nDao.findOne(id);
    }

    @Override
    public void deleteById(Class<?> clazz, Serializable id) {
        String key;
        try {
            key = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("deleteI18nById failed! object is not a persistence entity!");
            return;
        }
        i18nDao.deleteByKey(key);
    }

    @Override
    public I18nKV findByIdAndLocale(Class<?> clazz, Serializable id,
            LocaleConstants locale) {
        String key;
        try {
            key = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("findByIdAndLocale failed! object is not a persistence entity!");
            return null;
        }
        return i18nDao.findByKeyAndLocale(key, locale);
    }

    @Override
    public List<I18nKV> findAll() {
        return i18nDao.findAll();
    }

    @Override
    public List<I18nKV> findExistsValue(Class<?> clazz, Long id, List<LocaleVO> values) {
        String keyPrefix;
        try {
            keyPrefix = MessageHelper.getI18nKeyPrefix(clazz);
        } catch (CRMBaseException e) {
            logger.error("findExistsValue failed! object is not a persistence entity!");
            return null;
        }
        return i18nDaoCustom.findExistsValue(keyPrefix, id, values);
    }

    @Override
    public List<I18nKV> findByAdPlatformIdAndSiteId(Long adPlatformId, Long siteId) {
        return i18nDaoCustom.findByAdPlatformIdAndSiteId(adPlatformId, siteId);
    }
    
    @Override
    public List<I18nKV> findByIndexStr(Long excludePositionId, String indexStr) {
        return i18nDaoCustom.findByIndexStr(excludePositionId, indexStr);
    }

    @Override
    public List<I18nKV> findById(Class<?> clazz, Serializable id) {
        String key;
        try {
            key = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("findById() failed! object is not a persistence entity!");
            return null;
        }
        return i18nDao.findByKey(key);
    }

    @Override
    public void deleteByKeyAndLocale(String key, LocaleConstants locale) {
        i18nDao.deleteByKeyAndLocale(key, locale);
    }

    @Override
    public void updateSubPositionExtraName(String indexStr, PositionType type,
            LocaleConstants locale) {
        i18nDaoCustom.updateSubPositionExtraName(indexStr, type, locale);
    }

    @Override
    public I18nKV save(Class<?> clazz, Serializable id, LocaleVO localeVO) {
        String key;
        try {
            key = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("save i18n failed! object is not a persistence entity!");
            return null;
        }
        I18nKV newI18nKV = buildI18nKVObject(key,localeVO);
        return i18nDao.save(newI18nKV);
    }

    @Override
    public I18nKV getAndLoadI18Info(Class<?> clazz, Long id,
            final LocaleConstants locale) {
        final String i18nKey;
        try {
            i18nKey = MessageHelper.generateI18nKey(clazz, id);
        } catch (CRMBaseException e) {
            logger.error("getI18Info failed! object is not a persistence entity!");
            return null;
        }
        String cacheKey = MessageHelper.getI18nCacheKey(i18nKey, locale);
        return ehcacheTemplate.fetch(cacheKey,null,new DataLoader<I18nKV>(){
            @Override
            public I18nKV loadData() {
                return findByKeyAndLocale(i18nKey, locale);
            }
        });
    }
    
    @Override
    public List<I18nKV> findByKeyNameLikeAndLocale(String keyName, LocaleConstants locale){
        return i18nDao.findByKeyNameLikeAndLocale(keyName+"%", locale);
    }
    
    public void setI18nDao(I18nKVDao i18nDao) {
        this.i18nDao = i18nDao;
    }

    public void setI18nDaoCustom(I18nKVDaoCustom i18nDaoCustom) {
        this.i18nDaoCustom = i18nDaoCustom;
    }

    public void setEhcacheTemplate(CacheTemplate<I18nKV> ehcacheTemplate) {
        this.ehcacheTemplate = ehcacheTemplate;
    }
}
