package com.baidu.gcrm.opportunity.model;

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
@Table(name = "g_opportunity")
public class Opportunity  implements BaseOperationModel {

	private static final long serialVersionUID = 8122732577041708067L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="customer_number")
	private Long customerNumber;
	
	//在线广告投放预算
	@Column(name="budget")
	private Double budget;
	
	//货币
	@Column(name="currency_type")
	private String currencyType;
	
	//预算对应投放周期
	@Column(name="spending_time")
	private String spendingTime;
	
	//付款方式
	@Column(name="payment")
    @Enumerated(EnumType.ORDINAL)
	private PaymentType payment;		
	
	//付款周期
	@Column(name="payment_period")
	private String paymentPeriod;	
	
	//计费模式
	@Column(name="billing_model")
	private String billingModel;
	
	//合作模式
	@Column(name="business_type")
	private String businessType;

	@Column(name="description")
	private String description;

	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	
	@Column(name="last_update_time")
	private Date updateTime;
	
	@Column(name="last_update_operator")
	private Long updateOperator;

	/**
	 * 投放平台ID集
	 */
	@Transient
    private String platformIds;
	

	public Long getId() {
		return id;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public Double getBudget() {
		return budget;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public String getSpendingTime() {
		return spendingTime;
	}

	public PaymentType getPayment() {
		return payment;
	}

	public String getPaymentPeriod() {
		return paymentPeriod;
	}

	public String getBillingModel() {
		return billingModel;
	}

	public String getBusinessType() {
		return businessType;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Long getCreateOperator() {
		return createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public Long getUpdateOperator() {
		return updateOperator;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public void setSpendingTime(String spendingTime) {
		this.spendingTime = spendingTime;
	}

	public void setPayment(PaymentType payment) {
		this.payment = payment;
	}

	public void setPaymentPeriod(String paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}

	public void setBillingModel(String billingModel) {
		this.billingModel = billingModel;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}

    public String getPlatformIds() {
        return platformIds;
    }

    public void setPlatformIds(String platformIds) {
        this.platformIds = platformIds;
    }	
	

	
}
