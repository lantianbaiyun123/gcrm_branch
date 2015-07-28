package com.baidu.gcrm.ad.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="g_contract")
public class Contract implements Serializable{
	
	public enum ContractState{
		INVALID,//已作废
		VALID,//已生效
		TERMINATED,//已终止
		NOT_EFFECTIVE;//未生效
		public static ContractState valueOf(Integer value){
			if(value == null){
				return null;
			}
			
			ContractState[] states = ContractState.values();
			for(ContractState state : states){
				if(value.intValue() == state.ordinal()){
					return state;
				}
			}
			
			return null;
		}
	}
	
	public enum ContractCategory{
	    GJXY,//国际化框架协议
	    GJHT//国际化普通合同
	}
	
	private static final long serialVersionUID = -2054032894075199824L;

	@Id
	private String number;
	@Column(name = "category")
	private String category;
	
	@Column(name = "customer_id")
	private Long customerId;
	
	private String summary;
	
	@Column(name="state")
	@Enumerated(EnumType.STRING)
	private ContractState state;
	
	@Column(name = "begin_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date beginDate;
	
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "end_date")
	private Date endDate;
	
    @Column(name = "sync_date")
    private Date syncDate;
	
    @Column
	private String applier;
	
    @Column
	private String sales;
	
	@Column(name = "parent_number")
	private String parentNumber;
	
	@Column(name = "advertise_solution_id")
	private Long advertiseSolutionId;
	@Transient
	private String contractInfo;//合同在cms的审核进度


	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public ContractState getState() {
		return state;
	}

	public void setState(ContractState state) {
		this.state = state;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getParentNumber() {
		return parentNumber;
	}

	public void setParentNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}

	public Long getAdvertiseSolutionId() {
		return advertiseSolutionId;
	}

	public void setAdvertiseSolutionId(Long advertiseSolutionId) {
		this.advertiseSolutionId = advertiseSolutionId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

    
}
