package com.baidu.gcrm.data.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.data.model.PublishDateStatistics;

public interface IPublishDateStatisticsRepository extends JpaRepository<PublishDateStatistics, Long> {
	
	@Modifying
	@Query("insert into PublishDateStatistics(adContentId, publishDateId, publishNumber)" +
			" select p.adContentId, pd.id, p.number from Publish p, PublishDate pd" +
			" where p.number = pd.publishNumber and pd.publishNumber = ?1")
	void initByPublishNumber(String publishNumber);
	
	@Query("select pds from PublishDateStatistics pds, PublishDate pd where pds.publishDateId = pd.id" +
			" and pds.adContentId = ?1 and pd.planStart <= ?2 and pd.planEnd >= ?2 order by pd.id")
	List<PublishDateStatistics> findByAdContentIdPublishDate(Long adContentId, Date date);
	
	List<PublishDateStatistics> findByPublishNumber(String publishNumber);
	
	PublishDateStatistics findByPublishDateId(Long publishDateId);
	
	@Modifying
	@Query("delete from PublishDateStatistics where publishDateId = ?1")
	int deleteByPublishDateId(Long publishDateId);
	
	@Modifying
	@Query("insert into PublishDateStatistics(adContentId, publishDateId, publishNumber) select p.adContentId, pd.id," +
			" p.number from Publish p, PublishDate pd where p.number = pd.publishNumber and pd.id = ?1")
	void initByPublishDateId(Long publishDateId);
	
	@Modifying
	@Query("delete from PublishDateStatistics where publishNumber = ?1")
	int deleteByPublishNumber(String publishNumber);
}
