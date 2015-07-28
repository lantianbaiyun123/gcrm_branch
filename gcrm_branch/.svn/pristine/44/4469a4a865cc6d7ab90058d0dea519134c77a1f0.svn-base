package com.baidu.gcrm.resource.adplatform.service;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;

public interface IAdPlatformSiteRelationService {
    
    List<AdPlatformSiteRelation> findByAdPlatformIdAndStatus(Long adPlatformId, AdPlatformSiteRelationStatus status);
    
    List<AdPlatformSiteRelation> findByAdPlatformIdInAndStatus(List<Long> adPlatformIds, AdPlatformSiteRelationStatus status);
    
    List<AdPlatformSiteRelation> findByAdPlatformIdAndSiteId(List<Long> adPlatformIds, List<Long> siteIds);
    
    void saveAdPlatformSiteRelation(AdPlatform adPlatform, boolean isCreate);
    
    int updateRelationStatusByAdPlatformId(Long adPlatformId, AdPlatformSiteRelationStatus status, Date updateTime, Long updateOpreator);
    
    List<AdPlatformSiteRelation> findAll();
}
