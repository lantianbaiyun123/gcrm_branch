package com.baidu.gcrm.publish.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.Publish.PublishStatus;
import com.baidu.gcrm.publish.model.Publish.PublishType;

public interface IPublishRepository extends JpaRepository<Publish, Long> {
	Publish findByNumber(String number);
	
	List<Publish> findByNumberIn(Collection<String> numbers);
	
	Publish findByAdContentId(Long adContentId);
	
	@Modifying
	@Query("update Publish set type = ?1 where scheduleNumber = ?2")
	int updatePublishTypeByScheduleNumber(PublishType type, String scheduleNumber);
	
	Publish findByScheduleNumber(String scheduleNumber);
	
	List<Publish> findByStatus(PublishStatus status);
	
	List<Publish> findByStatusIn(Collection<PublishStatus> statuses);
	
	Publish findByAdContentIdAndStatus(Long adContentId, PublishStatus status);
	
	@Modifying
    @Query("update Publish set adSolutionNumber = ?1 where adContentId in(?2)")
    int updateAdSolutionNumberByAdContentIdIn(String adSolutionNumber, List<Long> adContentIds);
}
