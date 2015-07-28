package com.baidu.gcrm.notice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name="g_notice")
public class Notice implements BaseOperationModel {

	private static final long serialVersionUID = -4457747646448836725L;
	
	@Id
    @GeneratedValue
    private Long id;

	@Column
    private String title;
    
    @Column
    private String content;
    
    @Column
    @Enumerated(EnumType.ORDINAL)
    private ReceiverScope scope;
    
    @Column
    @Enumerated(EnumType.ORDINAL)
    private NoticeStatus status;
    
    @Column
    private String receivers;
    
    @Column(name = "sent_time")
    private Date sentTime;
    
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_operator")
    private Long createOperator;
    
    @Column(name = "create_operator_name")
    private String createOperatorName;

    @Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
	    this.id = id;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public Long getCreateOperator() {
		return createOperator;
	}

	@Override
	public void setCreateOperator(Long createOperator) {
	    this.createOperator = createOperator;
	}

	@Override
	@Deprecated
	public Date getUpdateTime() {
		return null;
	}

	@Override
	@Deprecated
	public void setUpdateTime(Date updateTime) {
	}

	@Override
	@Deprecated
	public Long getUpdateOperator() {
		return null;
	}

	@Override
	@Deprecated
	public void setUpdateOperator(Long updateOperator) {
		
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the scope
	 */
	public ReceiverScope getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(ReceiverScope scope) {
		this.scope = scope;
	}

	/**
	 * @return the status
	 */
	public NoticeStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(NoticeStatus status) {
		this.status = status;
	}

	/**
	 * @return the receivers
	 */
	public String getReceivers() {
		return receivers;
	}

	/**
	 * @param receivers the receivers to set
	 */
	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	/**
	 * @return the sentTime
	 */
	public Date getSentTime() {
		return sentTime;
	}

	/**
	 * @param sentTime the sentTime to set
	 */
	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

	public enum NoticeStatus {
		sent,
		draft,
		invalid;
		
		public static NoticeStatus valueOf(Integer value){
			if(value == null){
				return null;
			}
			NoticeStatus[] values = NoticeStatus.values(); 
			for(NoticeStatus status : values){
				if(status.ordinal() == value){
					return status;
				}
			}
			return null;
		}
	}
	
	public enum ReceiverScope {
		internal,
		external;
		public static ReceiverScope valueOf(Integer value){
			if(value == null){
				return null;
			}
			ReceiverScope[] values = ReceiverScope.values(); 
			for(ReceiverScope scope : values){
				if(scope.ordinal() == value){
					return scope;
				}
			}
			return null;
		}
	}
	
}
