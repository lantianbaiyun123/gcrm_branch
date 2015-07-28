package com.baidu.gcrm.ad.content.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name="g_adcontent_online_apply")
public class AdSolutionContentApply implements BaseOperationModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6334804919025124005L;

	
	@Id @GeneratedValue
	private Long id;
	@Column(name="advertise_solution_id")
	private Long adSolutionId;
	@Column(name="advertise_solution_content_id")
	private Long adSolutionContentId;
	@Column(name="advertise_solution_content_number") 
	private String adSolutiionContentNumber;
	@Column(name="apply_reason") 
	private String applyReason;
	@Column(name="task_info") 
	private String taskInfo;
	@Column(name="create_time") 
	private Date createTime;
	@Column(name="last_update_time") 
	private Date updateTime;
	@Column(name="create_operator") 
	private Long createOperator;
	@Column(name="update_operator")
	private Long updateOperator;
	@Column(name="contract_number")
	private String contractNumber;
	@Column(name="contract_type")
	private String contractType;
	@Column(name="contract_state")
	private String contractState;
	@Column(name="contract_date")
	private String contractDate;
	@Column(name = "approval_status")
	@Enumerated(EnumType.STRING)
	private ApprovalStatus approvalStatus;
	
	@Transient
	private String creator;
	@Transient
	private String creatorEmail;
	@Transient
    private String contractUrl;
	public enum ApprovalStatus {
		/** 待提交 */
		saving,
		/** 审核驳回 */
		refused,
		/** 审核中 */
		approving,
		/** 审核完成 */
		approved,
		/** 已撤销 */
		withdrawn,
		/**已终止 */
		terminated;
		public static ApprovalStatus valueOf(Integer value){
			if(value == null){
				return null;
			}
			ApprovalStatus[] values = ApprovalStatus.values(); 
			for(ApprovalStatus status : values){
				if(status.ordinal() == value){
					return status;
				}
			}
			return null;
		}
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

	@Override
	public Date getCreateTime() {
		// TODO Auto-generated method stub
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime=createTime;
		
	}

	@Override
	public Long getCreateOperator() {
		// TODO Auto-generated method stub
		return createOperator;
	}

	@Override
	public void setCreateOperator(Long createOperator) {
		this.createOperator=createOperator;
		
	}

	@Override
	public Date getUpdateTime() {
		// TODO Auto-generated method stub
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		// TODO Auto-generated method stub
		this.updateTime=updateTime;
	}

	@Override
	public Long getUpdateOperator() {
		// TODO Auto-generated method stub
		return updateOperator;
	}

	public Long getAdSolutionId() {
		return adSolutionId;
	}

	public void setAdSolutionId(Long adSolutionId) {
		this.adSolutionId = adSolutionId;
	}

	public Long getAdSolutionContentId() {
		return adSolutionContentId;
	}

	public void setAdSolutionContentId(Long adSolutionContentId) {
		this.adSolutionContentId = adSolutionContentId;
	}

	public String getAdSolutiionContentNumber() {
		return adSolutiionContentNumber;
	}

	public void setAdSolutiionContentNumber(String adSolutiionContentNumber) {
		this.adSolutiionContentNumber = adSolutiionContentNumber;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	@Override
	public void setUpdateOperator(Long updateOperator) {
		// TODO Auto-generated method stub
		this.updateOperator=updateOperator;
	}
	
	
	public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractState() {
		return contractState;
	}

	public void setContractState(String contractState) {
		this.contractState = contractState;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}



}
