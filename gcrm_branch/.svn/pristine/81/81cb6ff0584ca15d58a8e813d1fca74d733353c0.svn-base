package com.baidu.gcrm.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

@Entity
@Table(name = "g_customer_approve_record")
public class CustomerApprovalRecord implements BaseOperationModel {

    private static final long serialVersionUID = 2160954356795679391L;

    /**
     * 物料审批记录ID
     */
    @Id
    @GeneratedValue
    @ModifyCheckIgnore
    private Long id;

    /**
     * 审批记录对应的客户ID
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 工作流 节点定义ID
     */
    @Column(name = "actdef_id")
    private String actDefId;

    /**
     * 工作流activityId
     */
    @Column(name = "activity_id")
    private String activityId;

    /**
     * 操作任务名称
     */
    @Transient
    private String taskName;

    /**
     * 审批状态 0：退回,1：通過，，2：拒絕
     */
    @Column(name = "approval_status")
    private Integer approvalStatus;

    /**
     * 审批意见
     */
    @Column(name = "approval_suggestion")
    private String approvalSuggestion;

    /**
     * 創建時間
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审批操作人
     */
    @Column(name = "create_operator")
    private Long createOperator;

    /**
     * 工作流 实例ID
     */
    @Column(name = "process_id")
    private String processId;

    @Transient
    private Date updateTime;

    @Transient
    private Long updateOperator;

    @Transient
    private String creater;
    /**
     * 是否加签
     */
    @Transient
    private boolean plusSign;
    
    
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getActDefId() {
        return actDefId;
    }

    public void setActDefId(String actDefId) {
        this.actDefId = actDefId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

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

    public boolean isPlusSign() {
        return plusSign;
    }

    public void setPlusSign(boolean plusSign) {
        this.plusSign = plusSign;
    }


}
