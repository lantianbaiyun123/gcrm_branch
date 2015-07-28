package com.baidu.gcrm.resource.site.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.site.model.Site;

public interface ISiteService {
    
    List<Site> findAll(LocaleConstants locale);
    
    List<Site> findSiteByAdPlatform(Long adPlatformId,LocaleConstants locale);
    
    List<Long> findUsedSiteIdByAdPlatform(Long adPlatformId);
    
    Map<String,String> findUsedSiteIdByIds(Long adPlatformId, List<Long> siteIds, PositionStatus status);
    
    Site findSiteAndI18nById(Long id, LocaleConstants locale);
    
    List<Site> findByIdIn(Collection<Long> ids, LocaleConstants locale);
    
    /**
     * 查找用户平台下有权限的站点
     * @param adPlatformId
     * @param locale
     * @return
     */
    List<Site> findSiteByAdPlatformAndRights(Long adPlatformId, LocaleConstants locale, String username);
    
    Map<Long, String> findTimeZoneOffsetMap();
}
