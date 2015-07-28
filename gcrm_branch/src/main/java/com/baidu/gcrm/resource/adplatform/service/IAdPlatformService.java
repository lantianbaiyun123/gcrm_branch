package com.baidu.gcrm.resource.adplatform.service;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;

public interface IAdPlatformService {
    
    void saveAdPlatformAndRelation(AdPlatform adPlatform);
    
    void closeAdPlatform(AdPlatform adPlatform);
    
    void openAdPlatform(AdPlatform adPlatform);
    
    AdPlatform findById(Long id);
    
    AdPlatform findById(Long id, LocaleConstants locale);
    
    List<AdPlatform> findAll(LocaleConstants locale, boolean isQueryList);
    
    List<AdPlatform> findByStatus(LocaleConstants locale, AdPlatformStatus status);
    
    Long findUsedAmountById(Long id);
    
    List<I18nKV> findExistsName(Long id, List<LocaleVO> values);
    
    Map<String,String> findUsedAdPlatform(List<Long> adPlatformIds);
    
    public List<AdPlatformSiteRelation> findAccessSites4Platform(Long platformId,LocaleConstants locale);
}
