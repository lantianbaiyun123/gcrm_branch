package com.baidu.gcrm.ad.model;

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
import com.baidu.gcrm.log.model.ModifyCheckIgnore;
import com.baidu.gcrm.quote.model.PriceType;


@Entity
@Table(name = "g_advertise_quotation")
public class AdvertiseQuotation implements BaseOperationModel,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@ModifyCheckIgnore
	private Long id;
	
	/**
	 * 广告方案内容ID
	 */
	@Column(name = "advertise_solution_content_id")
	@ModifyCheckIgnore
	private Long adSolutionContentId;
	
	/**
	 * 价格种类
	 */
	@Column(name = "price_type")
	@Enumerated(EnumType.ORDINAL)
	private PriceType priceType;
	
	/**
	 * 计费方式
	 */
	@Column(name = "billing_model_id")
	private Long billingModelId;
	
	@Transient
	@ModifyCheckIgnore
	private String billingModelName;
	/**
	 * 货币类型
	 */
	@Column(name = "currency_type")
	@ModifyCheckIgnore
	private Integer currencyType;
	/**
	 * 刊例价
	 */
	@Column(name = "publish_price")
	@ModifyCheckIgnore
	private Double publishPrice;
	/**
	 * 客户报价
	 */
	@Column(name = "customer_quote")
	private Double customerQuote;
	/**
	 * 流量数据
	 */
	@ModifyCheckIgnore
	@Column(name = "traffic_amount")
	private Long trafficAmount;
	/**
	 * 点击
	 */
	@ModifyCheckIgnore
	@Column(name = "click_amount")
	private Long clickAmount;
	/**
	 * 折扣
	 */
	@Column(name = "discount")
	private Double discount;
	/**
	 * 预算
	 */
	@Column(name = "budget")
	private Double budget;
	/**
	 * 总价
	 */
	@Column(name = "total_price")
	private Double totalPrice;
	/**
	 * 我方分成比例
	 */
	@ModifyCheckIgnore
	@Column(name = "product_ratio_mine")
	private Double productRatioMine;
	/**
	 * 客户分成比例
	 */
	@ModifyCheckIgnore
	@Column(name = "product_ratio_customer")
	private Double productRatioCustomer;
	/**
	 * 第三方分成比例
	 */
	@ModifyCheckIgnore
	@Column(name = "product_ratio_third")
	private Double productRatioThird;
	
	/**
	 * 行业类型
	 */
	@Column(name = "industry_type")
	private Integer industryType;
	/**
	 * 我方实际分成比例
	 */
	@Column(name = "ratio_mine")
	private Double ratioMine;
	/**
	 * 客户实际分成比例
	 */
	@Column(name = "ratio_customer")
	private Double ratioCustomer;
	/**
	 * 第三方实际分成比例
	 */
	@Column(name = "ratio_third")
	private Double ratioThird;
	
	/**
	 * 分成条件
	 */
	//@Column(name = "ratio_condition")
	//private String ratioCondition;
	/**
	 * 分成条件说明
	 */
	@Column(name = "ratio_condition_desc")
	private String ratioConditionDesc;
	/**
	 * 是否为预估值
	 */
	@Column(name = "reach_estimate")
	private Boolean reachEstimate;
	/**
	 * 日投放量预估
	 */
	@Column(name = "daily_amount")
	private Long dailyAmount;
	/**
	 * 总投放量
	 */
	@Column(name = "total_amount")
	private Long totalAmount;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdSolutionContentId() {
		return adSolutionContentId;
	}

	public void setAdSolutionContentId(Long adSolutionContentId) {
		this.adSolutionContentId = adSolutionContentId;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public Long getBillingModelId() {
		return billingModelId;
	}

	public void setBillingModelId(Long billingModelId) {
		this.billingModelId = billingModelId;
	}

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public Double getPublishPrice() {
		return publishPrice;
	}

	public void setPublishPrice(Double publishPrice) {
		this.publishPrice = publishPrice;
	}

	public Double getCustomerQuote() {
		return customerQuote;
	}

	public void setCustomerQuote(Double customerQuote) {
		this.customerQuote = customerQuote;
	}

	public Long getTrafficAmount() {
		return trafficAmount;
	}

	public void setTrafficAmount(Long trafficAmount) {
		this.trafficAmount = trafficAmount;
	}

	public Long getClickAmount() {
		return clickAmount;
	}

	public void setClickAmount(Long clickAmount) {
		this.clickAmount = clickAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getIndustryType() {
		return industryType;
	}

	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}

	public Double getProductRatioMine() {
		return productRatioMine;
	}

	public void setProductRatioMine(Double productRatioMine) {
		this.productRatioMine = productRatioMine;
	}


	public Double getProductRatioCustomer() {
		return productRatioCustomer;
	}

	public void setProductRatioCustomer(Double productRatioCustomer) {
		this.productRatioCustomer = productRatioCustomer;
	}

	public Double getProductRatioThird() {
		return productRatioThird;
	}

	public void setProductRatioThird(Double productRatioThird) {
		this.productRatioThird = productRatioThird;
	}

	public Double getRatioCustomer() {
		return ratioCustomer;
	}

	public void setRatioCustomer(Double ratioCustomer) {
		this.ratioCustomer = ratioCustomer;
	}

	public Double getRatioMine() {
		return ratioMine;
	}

	public void setRatioMine(Double ratioMine) {
		this.ratioMine = ratioMine;
	}

	public Double getRatioThird() {
		return ratioThird;
	}

	public void setRatioThird(Double ratioThird) {
		this.ratioThird = ratioThird;
	}

	public String getRatioConditionDesc() {
		return ratioConditionDesc;
	}

	public void setRatioConditionDesc(String ratioConditionDesc) {
		this.ratioConditionDesc = ratioConditionDesc;
	}

	public Boolean getReachEstimate() {
		return reachEstimate;
	}

	public void setReachEstimate(Boolean reachEstimate) {
		this.reachEstimate = reachEstimate;
	}

	public Long getDailyAmount() {
		return dailyAmount;
	}

	public void setDailyAmount(Long dailyAmount) {
		this.dailyAmount = dailyAmount;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
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

	public String getBillingModelName() {
		return billingModelName;
	}

	public void setBillingModelName(String billingModelName) {
		this.billingModelName = billingModelName;
	}
	
	public AdvertiseQuotation clone() {
		AdvertiseQuotation o = null;
		try {
			o = (AdvertiseQuotation) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	} 
}
