package com.baidu.gcrm.ad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

@Entity
@Table(name = "g_advertise_solution")
public class AdvertiseSolution implements BaseOperationModel{

	private static final long serialVersionUID = 4950148248772610143L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String number;
	
	@Column(name="customer_number")
	private Long customerNumber;
	
	@Column(name="contract_number")
	private String contractNumber;
	
	private Long operator;
	
	@Column(name = "approval_status")
	@Enumerated(EnumType.ORDINAL)
	@ModifyCheckIgnore
	private AdvertiseSolutionApproveState approvalStatus;
	
	@Enumerated(EnumType.ORDINAL)
	private AdvertiseSolutionType type;
	
	@Column(name = "contract_type")
	@Enumerated(EnumType.STRING)

	private AdvertiseSolutionContractType contractType;
	@Column(name = "contract_status")
	@Enumerated(EnumType.STRING)
	private AdvertiseSolutionContractStatus contractStatus;
	
	private Float budget;
	
	@Column(name = "currency_type")
	private Integer currencyType;
	
	@Column(name = "start_time")
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date startTime;
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern="dd-MM-yyyy")
	private Date endTime;
	
	@Column(name = "task_info")
	@ModifyCheckIgnore
	private String taskInfo;
	
	private Integer locked;
	
	@Column(name = "create_time")
	@ModifyCheckIgnore
	private Date createTime;
	
	@Column(name = "create_operator")
	@ModifyCheckIgnore
	private Long createOperator;
	
	@Column(name = "last_update_time")
	@ModifyCheckIgnore
	private Date updateTime;
	
	@Column(name = "last_update_operator")
	@ModifyCheckIgnore
	private Long updateOperator;
	
	@Column(name = "old_solution_id")
	private Long oldSolutionId;
	
    @Column(name = "advertise_type")
    @Enumerated(EnumType.ORDINAL)
	private AdvertiseType advertiseType;

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public AdvertiseSolutionApproveState getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(AdvertiseSolutionApproveState approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public AdvertiseSolutionType getType() {
		return type;
	}

	public void setType(AdvertiseSolutionType type) {
		this.type = type;
	}

	public Float getBudget() {
		return budget;
	}

	public void setBudget(Float budget) {
		this.budget = budget;
	}

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
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
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		
	}

	@Override
	public Long getUpdateOperator() {
		return updateOperator;
	}

	@Override
	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
		
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public Long getOldSolutionId() {
		return oldSolutionId;
	}

	public void setOldSolutionId(Long oldSolutionId) {
		this.oldSolutionId = oldSolutionId;
	}

	public AdvertiseSolutionContractType getContractType() {
		return contractType;
	}

	public AdvertiseSolutionContractStatus getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(AdvertiseSolutionContractStatus contractStatus) {
		this.contractStatus = contractStatus;
	}

	public void setContractType(AdvertiseSolutionContractType contractType) {
		this.contractType = contractType;
	}

    public AdvertiseType getAdvertiseType() {
        return advertiseType;
    }

    public void setAdvertiseType(AdvertiseType advertiseType) {
        this.advertiseType = advertiseType;
    }
}
