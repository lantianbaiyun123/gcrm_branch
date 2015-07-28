package com.baidu.gcrm.occupation.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.occupation.model.AdvertisePositionDateRelation;

public interface IADPositionDateRelationRepository extends JpaRepository<AdvertisePositionDateRelation, Long> {
	List<AdvertisePositionDateRelation> findByPositionOccIdIn(Collection<Long> positionOccIds);
	
	@Modifying
	@Query("delete from AdvertisePositionDateRelation where adContentId=?1")
	int deleteByAdContentId(Long adContentId);
	
	@Modifying
	@Query("delete from AdvertisePositionDateRelation where adContentId in(?1)")
	int deleteByAdContentIds(List<Long> adContentIds);
	
	@Modifying
	@Query("delete from AdvertisePositionDateRelation where adContentId=?1 and positionOccId=?2")
	int deleteByAdContentIdAndPositionOccId(Long adContentId, Long positionOccId);
	
	@Modifying
    @Query("delete from AdvertisePositionDateRelation where adContentId=?1 and positionOccId in (?2)")
    int deleteByAdContentIdAndPositionOccId(Long adContentId, List<Long> positionOccIds);
	
	@Query("select count(*) from AdvertisePositionDateRelation where adContentId=?1 and positionOccId in (?2)")
    Long findCountsByAdContentIdAndPositionOccId(Long adContentId, List<Long> positionOccIds);
	
	@Query("from  AdvertisePositionDateRelation where positionOccId in(?1)")
	List<AdvertisePositionDateRelation> findByOccupations(Collection<Long> occupationIds);
	
    List<AdvertisePositionDateRelation> findByAdContentId(Long adContentId);
	
	@Query("from  AdvertisePositionDateRelation where adContentId=?1 and positionOccId in (?2)")
    List<AdvertisePositionDateRelation> findByAdContentIdAndOccIds(Long adContentId,
            Collection<Long> occupationIds);
	
	@Query("select distinct adContentId from  AdvertisePositionDateRelation where positionOccId in(?1)")
    List<Long> findAdContentIdByOccupation(Collection<Long> occupationIds);
	
	@Query("select r from AdvertisePositionDateRelation r, Schedule s where r.positionOccId in (?1) and r.adContentId=s.adContentId and s.status != 3")
	List<AdvertisePositionDateRelation> findRelationsWithScheduleByOccupations(Collection<Long> occupationIds);
}
