package com.baidu.gcrm.resource.site.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.site.model.Site;

public interface ISiteRepository extends JpaRepository<Site, Long> {
    
    @Query("select distinct s from Site s,AdPlatformSiteRelation r where s.id=r.siteId and r.adPlatformId=?1  and r.status=?2 ")
    List<Site> findByAdPlatformId(Long adPlatformId, AdPlatformSiteRelationStatus status);
    
    List<Site> findByIdIn(Collection<Long> ids);
    
    @Query("select  distinct s.id from Site s,Position p where s.id=p.siteId and p.adPlatformId=?1 and p.status=?2")
    List<Long> findUsedSiteIdByAdPlatform(Long adPlatformId, PositionStatus status);
    
    @Query("select  distinct s.id from Site s,Position p where s.id=p.siteId and p.adPlatformId=?1 and s.id in (?2) and p.status=?3")
    List<Long> findUsedSiteIdByIds(Long adPlatformId, List<Long> siteIds, PositionStatus status);
    
    @Query("select distinct s from Site s,AdPlatformSiteRelation r,Participant p where s.id=r.siteId and r.adPlatformId=?1  and r.status=?2 and p.description = ?3 and p.username = ?4 and p.key = s.id")
    List<Site> findByAdPlatformIdAndRights(Long adPlatformId, AdPlatformSiteRelationStatus status, DescriptionType desType, String username);
    
}
