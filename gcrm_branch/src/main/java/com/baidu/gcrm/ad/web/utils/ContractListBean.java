package com.baidu.gcrm.ad.web.utils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class ContractListBean implements Serializable{
	
	public enum ContractState{
		VALID,
		INVALID,
		TERMINATED;
		
		public ContractState valueOf(Integer value){
			if(value == null){
				return null;
			}
			
			ContractState[] states = ContractState.values();
			for(ContractState state : states){
				if(value == state.ordinal()){
					return state;
				}
			}
			
			return null;
		}
	}

	private static final long serialVersionUID = -2054032894075199824L;

	@Id
	private String number;
	@Column(name = "category")
	private String category;
	
	@Column(name = "customer_id")
	private Long customerNumber;
	
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
	
	
    @Column
	private String sales;
	@Transient
    private String detailUrl;
    
    
    
    
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

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
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

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

}
