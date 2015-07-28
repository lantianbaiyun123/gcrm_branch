package com.baidu.gcrm.quote.model;

import java.util.List;

public class QuotationIndustryType {
	private Long id;
	private String industryTypeName;
	private List<QuotationIndustryType> subIndustryType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIndustryTypeName() {
		return industryTypeName;
	}
	public void setIndustryTypeName(String industryTypeName) {
		this.industryTypeName = industryTypeName;
	}
	public List<QuotationIndustryType> getSubIndustryType() {
		return subIndustryType;
	}
	public void setSubIndustryType(List<QuotationIndustryType> subIndustryType) {
		this.subIndustryType = subIndustryType;
	}
}
