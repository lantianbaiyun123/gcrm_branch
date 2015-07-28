package com.baidu.gcrm.quote.web.vo;

import java.util.List;

import com.baidu.gcrm.quote.material.model.QuotationMaterial;
import com.baidu.gcrm.quote.model.Quotation;

public class QuotationView {
    private List<Quotation> quotationList;
    private Long siteId;
    private List<QuotationMaterial> quotationMaterialList;
    private String siteName;
    private List<Long> deleteQutationIds;
    
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public List<Quotation> getQuotationList() {
		return quotationList;
	}
	public void setQuotationList(List<Quotation> quotationList) {
		this.quotationList = quotationList;
	}
	public List<QuotationMaterial> getQuotationMaterialList() {
		return quotationMaterialList;
	}
	public void setQuotationMaterialList(
			List<QuotationMaterial> quotationMaterialList) {
		this.quotationMaterialList = quotationMaterialList;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public List<Long> getDeleteQutationIds() {
		return deleteQutationIds;
	}
	public void setDeleteQutationIds(List<Long> deleteQutationIds) {
		this.deleteQutationIds = deleteQutationIds;
	}
}
