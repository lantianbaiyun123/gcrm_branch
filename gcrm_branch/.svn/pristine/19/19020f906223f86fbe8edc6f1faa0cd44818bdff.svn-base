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
@Table(name = "g_quotation_main")
public class QuotationMain extends BaseI18nModel implements Cloneable{
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
	
	@Column(name = "start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	
	@Column(name = "end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	
	@Column(name="price_type")
	@Enumerated
	private PriceType priceType;
	
	@Column(name="quote_code")
	private String quoteCode;
	
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_operator")
	private Long createOperator;
	
	@Column(name="last_update_time")
    private Date updateTime;
    
    @Column(name="last_update_operator")
    private Long updateOperator;

	@Column(name = "cancel_time")
	private Date cancelTime;

	@Column(name = "cancel_operator")
	private Long cancelOperator;
	/**
	 * 标杆价状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private QuotationStatus status;
	/**
	 * 审批状态
	 */
	@Column(name = "approve_status")
	@Enumerated(EnumType.ORDINAL)
	private QuotationApproveStatus approveStatus;
	/**
	 * 审批任务提示
	 */
	@Column(name = "task_info")
	private String taskInfo;
	/**
	 * 定价说明
	 */
	@Column(name = "descreption")
	private String descreption;
	
	/**
	 * 业务类型  0 销售  1 变现
	 */
	@Column(name = "business_type")
	@Enumerated(EnumType.ORDINAL)
	private BusinessType businessType;

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

	public String getAdvertisingPlatformName() {
		return advertisingPlatformName;
	}

	public void setAdvertisingPlatformName(String advertisingPlatformName) {
		this.advertisingPlatformName = advertisingPlatformName;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
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

	public PriceType getPriceType() {
		return priceType;
	}

	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}

	public String getQuoteCode() {
		return quoteCode;
	}

	public void setQuoteCode(String quoteCode) {
		this.quoteCode = quoteCode;
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

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Long getCancelOperator() {
		return cancelOperator;
	}

	public void setCancelOperator(Long cancelOperator) {
		this.cancelOperator = cancelOperator;
	}

	public QuotationStatus getStatus() {
		return status;
	}

	public void setStatus(QuotationStatus status) {
		this.status = status;
	}

	public QuotationApproveStatus getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(QuotationApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getDescreption() {
		return descreption;
	}

	public void setDescreption(String descreption) {
		this.descreption = descreption;
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
    
    public QuotationMain clone() {
		QuotationMain o = null;
		try {
			o = (QuotationMain) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	} 
}
