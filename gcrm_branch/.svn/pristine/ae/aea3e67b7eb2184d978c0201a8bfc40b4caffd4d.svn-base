package com.baidu.gcrm.ad.approval.record.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_ad_approval_record")
public class ApprovalRecord implements BaseOperationModel {
	
    private static final long serialVersionUID = 688424868623075532L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="ad_solution_id")
	private Long adSolutionId;
	
	@Column(name="ad_content_id")
	private Long adContentId;
	
	@Column(name="process_id")
	private String processId;
    
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
	
	@Transient
    private String createOperatorName;
	
	@Transient
	private Date updateTime;
	
	@Transient
	private Long updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public Long getAdSolutionId() {
        return adSolutionId;
    }

    public void setAdSolutionId(Long adSolutionId) {
        this.adSolutionId = adSolutionId;
    }

    public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

	
}
