package com.baidu.gcrm.quote.dao;

public class QuotationCondition {
	private Long advertisingPlatformId;
	private Long siteId;
	private Long billingModelId;
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
}
