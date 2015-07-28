package com.baidu.gcrm.occupation1.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;

public interface PositionDateRelationRepository extends JpaRepository<AdvertisePositionDateRelation, Long> {
	
	@Modifying
	@Query("delete from AdvertisePositionDateRelation where adContentId=?1")
	int deleteByAdContentId(Long adContentId);
	
	@Modifying
	@Query("delete from AdvertisePositionDateRelation where adContentId in(?1)")
	int deleteByAdContentIds(List<Long> adContentIds);
	
    List<AdvertisePositionDateRelation> findByAdContentId(Long adContentId);
	
    List<AdvertisePositionDateRelation> findByAdContentIdIn(Collection<Long> adContentIds);
    
    @Query("select r from AdvertisePositionDateRelation r, PositionDate pd where r.positionOccId = pd.id"
            + " and r.adContentId in(?1) and pd.date >= ?2")
    List<AdvertisePositionDateRelation> findByAdContentIdInAndDateFrom(Collection<Long> adContentIds, Date fromDate);
    
    @Query("select r from AdvertisePositionDateRelation r, PositionDate pd where r.positionOccId = pd.id"
            + " and r.adContentId = ?1 and pd.date >= ?2")
    List<AdvertisePositionDateRelation> findByAdContentIdAndDateFrom(Long adContentId, Date fromDate);
    
    @Query("select pd.date from AdvertisePositionDateRelation r, PositionDate pd where r.positionOccId = pd.id"
            + " and r.adContentId = ?1 and pd.date between ?2 and ?3")
    List<Date> getDatesByContentIdAndDateBetween(Long adContentId, Date fromDate, Date toDate);
    
    @Query("select r from AdvertisePositionDateRelation r, PositionDate pd where r.positionOccId = pd.id"
            + " and r.adContentId = ?1 and pd.date in(?2)")
    List<AdvertisePositionDateRelation> findByAdContentIdAndDateIn(Long adContentId, Collection<Date> dates);
}
