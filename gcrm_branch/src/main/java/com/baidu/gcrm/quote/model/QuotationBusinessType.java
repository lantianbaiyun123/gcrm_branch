package com.baidu.gcrm.quote.model;

import java.util.List;

import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

public class QuotationBusinessType {
	private Long id;
	private String businessTypeName;
	private List<AdvertisingPlatform> platformList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	
	public List<AdvertisingPlatform> getPlatformList() {
		return platformList;
	}
	public void setPlatformList(List<AdvertisingPlatform> platformList) {
		this.platformList = platformList;
	}
}
