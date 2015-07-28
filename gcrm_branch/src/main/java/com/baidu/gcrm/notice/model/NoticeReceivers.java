/**
 * 
 */
package com.baidu.gcrm.notice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接受群体id与公告的对应关系
 * @author shijiwen
 *
 */
@Entity
@Table(name="g_notice_receivers")
public class NoticeReceivers implements Serializable {
	private static final long serialVersionUID = -2051512845451825780L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="notice_id")
	private Long noticeId;
	
	@Column(name="ucid")
	private Long ucid;

	@Column(name="notice_title")
	private String noticeTitle;
	
	@Column(name="received")
	private boolean received;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the noticeId
	 */
	public Long getNoticeId() {
		return noticeId;
	}

	/**
	 * @param noticeId the noticeId to set
	 */
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * @return the ucid
	 */
	public Long getUcid() {
		return ucid;
	}

	/**
	 * @param ucid the ucid to set
	 */
	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	/**
	 * @return the noticeTitle
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * @param noticeTitle the noticeTitle to set
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * @return the received
	 */
	public boolean isReceived() {
		return received;
	}

	/**
	 * @param received the received to set
	 */
	public void setReceived(boolean received) {
		this.received = received;
	}

}
