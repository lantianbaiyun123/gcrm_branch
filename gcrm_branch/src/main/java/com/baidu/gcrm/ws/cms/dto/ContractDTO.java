package com.baidu.gcrm.ws.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同信息DTO
 *
 */
public class ContractDTO implements Serializable{
    
    private static final long serialVersionUID = 160861120595643810L;

	private String number;//合同编号
	
	private String category;//协议类型
	
	private Long customerId;//客户id
	
	private String summary;//摘要
	
	private String state;//状态
	
	private Date beginDate;//开始日期
	
	private Date endDate;//结束日期
	
	private String applier;//申请人
	
	private String sales;//销售
	
	private String parentNumber;//父合同编号
	
	private Long advertiseSolutionId;//广告方案id

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
