package com.baidu.gcrm.quote.approval.record.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_quote_approval_record")
public class QtApprovalRecord implements BaseOperationModel {
	
    private static final long serialVersionUID = 688424868623075532L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="quote_main_id")
	private Long quoteMainId;
	
	@Column(name="quote_content_id")
	private Long quoteContentId;
    
    @Column(name="task_id")
    private String taskId;
    
    @Column(name="activity_id")
    private String activityId;
    
    @Transient
    private String taskName;
    
    @Column(name="approval_status")
    private Integer approvalStatus;
    
    @Column(name="approval_suggestion")
    private String approvalSuggestion;
    
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	@Column(name="process_id")
	private String processId;
	
	@Transient
	private Date updateTime;
	
	@Transient
	private Long updateOperator;
	
	@Transient
	private String creater;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuoteMainId() {
		return quoteMainId;
	}

	public void setQuoteMainId(Long quoteMainId) {
		this.quoteMainId = quoteMainId;
	}

	public Long getQuoteContentId() {
		return quoteContentId;
	}

	public void setQuoteContentId(Long quoteContentId) {
		this.quoteContentId = quoteContentId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApprovalSuggestion() {
		return approvalSuggestion;
	}

	public void setApprovalSuggestion(String approvalSuggestion) {
		this.approvalSuggestion = approvalSuggestion;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	

	
}
