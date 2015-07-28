package com.baidu.gcrm.qualification.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name="g_agent_qualification")
public class Qualification implements BaseOperationModel{

	private static final long serialVersionUID = -4621535503765365066L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="customer_number")
	private Long customerNumber;
	
	@Column(name="parter_top1")
	private String parterTop1;
	
	@Column(name="parter_top2")
	private String parterTop2;
	
	@Column(name="parter_top3")
	private String parterTop3;
	/**
	 * 为显示使用
	 */
	@Transient
	private String partner;
	
	@Column(name="performance_highlights")
	private String performanceHighlights;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	
	@Column(name="last_update_time")
	private Date updateTime;
	
	@Column(name="last_update_operator")
	private Long updateOperator;
	
	@Transient
	private List<CustomerResource> customerResources;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getParterTop1() {
		return parterTop1;
	}
	public void setParterTop1(String parterTop1) {
		this.parterTop1 = parterTop1;
	}
	public String getParterTop2() {
		return parterTop2;
	}
	public void setParterTop2(String parterTop2) {
		this.parterTop2 = parterTop2;
	}
	public String getParterTop3() {
		return parterTop3;
	}
	public void setParterTop3(String parterTop3) {
		this.parterTop3 = parterTop3;
	}
	public String getPerformanceHighlights() {
		return performanceHighlights;
	}
	public void setPerformanceHighlights(String performanceHighlights) {
		this.performanceHighlights = performanceHighlights;
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
	public List<CustomerResource> getCustomerResources() {
		return customerResources;
	}
	public void setCustomerResources(List<CustomerResource> customerResources) {
		this.customerResources = customerResources;
	}
    public String getPartner() {
        return partner;
    }
    public void setPartner(String partner) {
        this.partner = partner;
    }
	
}
