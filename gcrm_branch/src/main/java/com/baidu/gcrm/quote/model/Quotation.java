package com.baidu.gcrm.quote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

@Entity
@Table(name = "g_quotation")
public class Quotation extends BaseI18nModel implements Cloneable{
	@Id
	@GeneratedValue
	private Long id; 
	
	@Column(name = "advertising_platform_id")
	private Long advertisingPlatformId;
	@Transient
	private String advertisingPlatformName;
	
	@Column(name = "site_id")
	private Long siteId;
	@Transient
	private String siteName;
	
	@Column(name = "billing_model_id")
	private Long billingModelId;
	@Transient
	private String billingModelName;
	@Transient
	private Integer billingModelType;
	
	@Column(name = "ratio_mine")
	private Double ratioMine;
	
	@Column(name = "ratio_customer")
	private Double ratioCustomer;
	/**
	 * 返点比例
	 */
	@Column(name = "ratio_rebate")
	private Double ratioRebate;
	
	@Column(name = "ratio_third")
	private Double ratioThird;
	
	@Column(name = "publish_price")
	private Double publishPrice;
	
	@Column(name = "start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	
	@Column(name="price_type")
	@Enumerated
	private PriceType priceType;
	
	@Column(name="industry_type")
	private Integer industryId;
	
	/**
	 * 业务类型  0 销售  1 变现
	 */
	@Column(name = "business_type")
	@Enumerated(EnumType.ORDINAL)
	private BusinessType businessType;
	
	@Column(name = "quotation_main_id")
	private Long quotationMainId;
	
	@Transient
    private Integer status;
	
    public Long getId() {
	    return id;
    }

    public void setId(Long id) {
	   this.id = id;
    }

	public Long getAdvertisingPlatformId() {
		return advertisingPlatformId;
	}

	public void setAdvertisingPlatformId(Long advertisingPlatformId) {
		this.advertisingPlatformId = advertisingPlatformId;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getBillingModelId() {
		return billingModelId;
	}

	public void setBillingModelId(Long billingModelId) {
		this.billingModelId = billingModelId;
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

	public Double getPublishPrice() {
		return publishPrice;
	}

	public void setPublishPrice(Double publishPrice) {
		this.publishPrice = publishPrice;
	}

	public String getAdvertisingPlatformName() {
		return advertisingPlatformName;
	}

	public void setAdvertisingPlatformName(String advertisingPlatformName) {
		this.advertisingPlatformName = advertisingPlatformName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getBillingModelName() {
		return billingModelName;
	}

	public void setBillingModelName(String billingModelName) {
		this.billingModelName = billingModelName;
	}
	public Integer getBillingModelType() {
		return billingModelType;
	}

	public void setBillingModelType(Integer billingModelType) {
		this.billingModelType = billingModelType;
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

	public Double getRatioCustomer() {
		return ratioCustomer;
	}

	public void setRatioCustomer(Double ratioCustomer) {
		this.ratioCustomer = ratioCustomer;
	}

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public Double getRatioRebate() {
		return ratioRebate;
	}

	public void setRatioRebate(Double ratioRebate) {
		this.ratioRebate = ratioRebate;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public Long getQuotationMainId() {
		return quotationMainId;
	}

	public void setQuotationMainId(Long quotationMainId) {
		this.quotationMainId = quotationMainId;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Quotation clone() {
    	Quotation o = null;
		try {
			o = (Quotation) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public Integer getIndustryId() {
		return industryId;
	}

	public void setIndustryId(Integer industryId) {
		this.industryId = industryId;
	}
}
