package com.baidu.gcrm.resource.adplatform.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;

public interface IAdPlatformSiteRelationRepository extends JpaRepository<AdPlatformSiteRelation, Long> {
    
    List<AdPlatformSiteRelation> findByAdPlatformIdAndStatus(Long adPlatformId, AdPlatformSiteRelationStatus status);
    
    List<AdPlatformSiteRelation> findByAdPlatformIdInAndStatus(List<Long> adPlatformIds, AdPlatformSiteRelationStatus status);
    
    List<AdPlatformSiteRelation> findByAdPlatformIdInAndSiteIdIn(List<Long> adPlatformIds, List<Long> siteIds);
    
    @Modifying
    @Query("update AdPlatformSiteRelation set status=?2,updateTime=?3,updateOperator=?4 where adPlatformId=?1 ")
    int updateRelationStatusByAdPlatformId(Long adPlatformId, AdPlatformSiteRelationStatus status, Date updateTime, Long updateOpreator);
}
