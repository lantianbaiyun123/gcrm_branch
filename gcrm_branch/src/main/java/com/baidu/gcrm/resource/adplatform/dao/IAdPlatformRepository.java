package com.baidu.gcrm.resource.adplatform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

public interface IAdPlatformRepository extends JpaRepository<AdPlatform, Long> {
    
    @Query("from  AdPlatform order by createTime desc")
    List<AdPlatform> findAllOrderByCreateTime();
    
    List<AdPlatform> findByStatus(AdPlatformStatus status);
    
    @Query("select distinct ap.id from AdPlatform ap, Position p where ap.id=p.adPlatformId and ap.id in (?1) and p.type=?2 and p.status=?3")
    List<Long> findByUsed(List<Long> adPlatformIds, PositionType type, PositionStatus positionStatus);
}
