package com.baidu.gcrm.quote.web.vo;

import java.util.List;

import com.baidu.gcrm.quote.material.model.QuotationMaterial;

public class QuotationAddVO {
	private Long siteId;
	private PriceTypeOther cpc;
	private PriceTypeOther cpt;
	private PriceTypeOther cpm;
	private PriceTypeOther cpa;
	private List<PriceTypeCps> cps;
	private String ratioRebate;
	private List<QuotationMaterial> quotationMaterialList;
	
	private String ratioMine;
	private String ratioCustomer;
	private String ratioThird;
	
	private String siteIi8nName;
	
	private Long id;
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public PriceTypeOther getCpc() {
		return cpc;
	}
	public void setCpc(PriceTypeOther cpc) {
		this.cpc = cpc;
	}
	public PriceTypeOther getCpt() {
		return cpt;
	}
	public void setCpt(PriceTypeOther cpt) {
		this.cpt = cpt;
	}
	public PriceTypeOther getCpm() {
		return cpm;
	}
	public void setCpm(PriceTypeOther cpm) {
		this.cpm = cpm;
	}
	public PriceTypeOther getCpa() {
		return cpa;
	}
	public void setCpa(PriceTypeOther cpa) {
		this.cpa = cpa;
	}
	public List<PriceTypeCps> getCps() {
		return cps;
	}
	public void setCps(List<PriceTypeCps> cps) {
		this.cps = cps;
	}
	public List<QuotationMaterial> getQuotationMaterialList() {
		return quotationMaterialList;
	}
	public void setQuotationMaterialList(
			List<QuotationMaterial> quotationMaterialList) {
		this.quotationMaterialList = quotationMaterialList;
	}
	public String getRatioMine() {
		return ratioMine;
	}
	public void setRatioMine(String ratioMine) {
		this.ratioMine = ratioMine;
	}
	public String getRatioCustomer() {
		return ratioCustomer;
	}
	public void setRatioCustomer(String ratioCustomer) {
		this.ratioCustomer = ratioCustomer;
	}
	public String getRatioThird() {
		return ratioThird;
	}
	public void setRatioThird(String ratioThird) {
		this.ratioThird = ratioThird;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSiteIi8nName() {
		return siteIi8nName;
	}
	public void setSiteIi8nName(String siteIi8nName) {
		this.siteIi8nName = siteIi8nName;
	}
	public String getRatioRebate() {
		return ratioRebate;
	}
	public void setRatioRebate(String ratioRebate) {
		this.ratioRebate = ratioRebate;
	}
}
