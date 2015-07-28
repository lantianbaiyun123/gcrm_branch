package com.baidu.gcrm.resource.site.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.site.dao.ISiteRepository;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Service
public class SiteServiceImpl implements ISiteService {
    
    @Autowired
    ISiteRepository siteRepository;
    
    @Autowired
    I18nKVService i18nService;
    
    @Autowired
    AdvertisingPlatformServiceImpl adPlatformServiceImpl;

    @Override
    public List<Site> findSiteByAdPlatform(Long adPlatformId,
            LocaleConstants locale) {
        List<Site> siteList = siteRepository.findByAdPlatformId(adPlatformId, AdPlatformSiteRelationStatus.enable);
        if (locale != null) {
            i18nService.fillI18nInfo(siteList, locale);
        }
        return siteList;
    }
    

    @Override
	public List<Site> findSiteByAdPlatformAndRights(Long adPlatformId, LocaleConstants locale, String username) {
    	List<Site> siteList = siteRepository.findByAdPlatformIdAndRights(adPlatformId, AdPlatformSiteRelationStatus.enable, DescriptionType.site, username);
    	 if (locale != null) {
             i18nService.fillI18nInfo(siteList, locale);
         }
		return siteList;
	}

    @Override
    public Site findSiteAndI18nById(Long id, LocaleConstants locale) {
        Site site = siteRepository.findOne(id);
        if (locale != null && site != null) {
            i18nService.fillI18nInfo(site, locale);
        }
        return site;
    }

    @Override
    public List<Site> findAll(LocaleConstants locale) {
        List<Site> allSite =  siteRepository.findAll();
        if (locale != null) {
            i18nService.fillI18nInfo(allSite, locale);
        }
        return allSite;
    }

    @Override
    public List<Site> findByIdIn(Collection<Long> ids, LocaleConstants locale) {
        List<Site> siteList = siteRepository.findByIdIn(ids);
        if (locale != null) {
            i18nService.fillI18nInfo(siteList, locale);
        }
        return siteList;
    }

    @Override
    public List<Long> findUsedSiteIdByAdPlatform(Long adPlatformId) {
        return siteRepository.findUsedSiteIdByAdPlatform(adPlatformId, PositionStatus.enable);
    }


    @Override
    public Map<String, String> findUsedSiteIdByIds(Long adPlatformId,
            List<Long> siteIds, PositionStatus status) {
        Map<String,String> usedSiteMap = new HashMap<String,String> ();
        List<Long> siteIdList = siteRepository.findUsedSiteIdByIds(adPlatformId, siteIds, status);
        if (CollectionUtils.isEmpty(siteIdList)) {
            return usedSiteMap;
        }
        for (Long siteId : siteIdList) {
            usedSiteMap.put(siteId.toString(), "");
        }
        return usedSiteMap;
    }
    
    @Override
    public Map<Long, String> findTimeZoneOffsetMap() {
    	Map<Long, String> timeZoneOffsetMap = new HashMap<Long, String>();
    	List<Site> allSite =  siteRepository.findAll();
    	for (Site site : allSite) {
			timeZoneOffsetMap.put(site.getId(), site.getTimeZoneOffset());
		}
    	return timeZoneOffsetMap;
    }
}
