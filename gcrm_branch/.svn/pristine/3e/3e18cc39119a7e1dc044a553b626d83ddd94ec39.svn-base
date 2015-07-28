/**
 * 
 */
package com.baidu.gcrm.notice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.notice.model.NoticeReceivers;

/**
 * @author shijiwen
 *
 */
public interface INoticeReceiversRepository extends JpaRepository<NoticeReceivers, Long>{

	//received=1为已读,这里取所有received=0的未读公告列表
	@Query("from NoticeReceivers where ucid=?1 and received != 1 order by id desc")
	public List<NoticeReceivers> findNoticeIdsByReceiverId(Long ucid);
	
	@Modifying
    @Query("update NoticeReceivers set received = 1 where noticeId = ?1 and ucid = ?2 ")
	public void readByReceiverId(Long noticeId, Long ucid);
	
}
