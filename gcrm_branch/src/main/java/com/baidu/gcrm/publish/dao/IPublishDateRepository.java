package com.baidu.gcrm.publish.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;

public interface IPublishDateRepository extends JpaRepository<PublishDate, Long> {
	List<PublishDate> findByPublishNumber(String publishNumber);
	
	List<PublishDate> findByPublishNumberAndStatus(String publishNumber, PublishPeriodStatus status);
	
	@Modifying
	@Query("delete from PublishDate where publishNumber = ?1")
	void deleteByPublishNumber(String publishNumber);
	
	@Query("select pd from PublishDate pd, Publish p where p.number = pd.publishNumber and p.scheduleNumber = ?1")
	List<PublishDate> findByScheduleNumber(String scheduleNumber);

	List<PublishDate> findByPublishNumberAndStatusNot(String publishNumber, PublishPeriodStatus status);
	
	List<PublishDate> findByPublishNumberAndPlanStartLessThan(String publishNumber, Date date);
	
}
