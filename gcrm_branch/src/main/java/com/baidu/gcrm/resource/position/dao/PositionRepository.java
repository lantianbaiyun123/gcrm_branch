package com.baidu.gcrm.resource.position.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionPropertyStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;

public interface PositionRepository extends JpaRepository<Position, Long>, PositionRepositoryCustom {
    
    @Modifying
    @Query("update Position set updateTime=?2,updateOperator=?3,status=?4 where id=?1")
    void updateStatusById(Long id,Date updateTime, Long updateOpreator, PositionStatus status);
    
    @Modifying
    @Query("update Position set updateTime=?2,updateOperator=?3,status=?4 where adPlatformId=?1")
    void updateStatusByAdPlatformId(Long adPlatformId,Date updateTime, Long updateOpreator, PositionStatus status);
    
    @Modifying
    @Query("update Position set updateTime=?4,updateOperator=?5,status=?6 where indexStr like ?3 and adPlatformId=?1 and siteId=?2")
    void updateStatusByIndexStr(Long adPlatformId, Long siteId, String indexStr,
            Date updateTime, Long updateOpreator, PositionStatus status);
    
    List<Position> findByAdPlatformIdAndSiteIdAndType(Long adPlatformId, Long siteId, PositionType type);
    
    List<Position> findByAdPlatformIdAndSiteIdAndTypeAndStatus(Long adPlatformId, Long siteId,
            PositionType type, PositionStatus status);
    
    List<Position> findByAdPlatformIdAndSiteIdOrderByIdDesc(Long adPlatformId, Long siteId);
    
    List<Position> findByParentId(Long parentId);
    
    List<Position> findByIdIn(Collection<Long> ids);
    
    Position findByPositionNumber(String number);
    
    List<Position> findByPositionNumberLikeAndType(String number, PositionType type, Pageable page);
    
    List<Position> findByParentIdAndStatus(Long parentId, PositionStatus status, Pageable page);
    
    List<Position> findByParentIdAndStatus(Long parentId, PositionStatus status);
    
    @Query("select p from Position p,AdPlatformSiteRelation r  where p.adPlatformId=r.adPlatformId and p.siteId=r.siteId and r.adPlatformId=?1  and r.status=?2 and p.status=?3 and p.type=?4")
    List<Position> findByAdPlatformAndStatusAndType(Long adPlatform, AdPlatformSiteRelationStatus relationStatus,
            PositionStatus status, PositionType type);
    
    List<Position> findByIndexStrLikeAndType(String indexStr, PositionType type);
    
    List<Position> findByIndexStrLikeAndTypeAndPropertyStatus(String indexStr, PositionType type,
            PositionPropertyStatus propertyStatus);
    
    List<Position> findByType(PositionType type);
    
    List<Position> findByIndexStrLikeAndTypeAndStatus(String indexStr, PositionType type,PositionStatus status);
    
    @Query("select p from Position p where p.parentId =?1 and p.rotationType is not null)")
    List<Position> findByParentIdAndNotNullRotationType(Long parentId);
    
    List<Position> findByStatusAndType(PositionStatus status, PositionType type);
}
