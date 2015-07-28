/**
 * 
 */
package com.baidu.gcrm.notice.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.notice.model.Notice;
import com.baidu.gcrm.notice.model.Notice.NoticeStatus;

/**
 * @author shijiwen
 *
 */
public interface INoticeRepository extends JpaRepository<Notice, Long>{
	
	@Query("from Notice where createOperator in (?1)")
	public List<Notice> findAllByCreateOperator(Collection<Long> ucidList);
	
    public List<Notice> findByStatus(NoticeStatus status);
	
	@Modifying
	@Query("update Notice a set a.status = ?2 where a.id = ?1")
	public void updateStatus(Long noticeId, NoticeStatus status);
	
	@Modifying
    @Query("update Notice a set a.status = ?2 where a.id in (?1)")
    public void updateBatchStatus(List<Long> noticeId, NoticeStatus status);
	
}
