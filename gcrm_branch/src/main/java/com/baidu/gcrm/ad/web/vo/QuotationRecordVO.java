package com.baidu.gcrm.ad.web.vo;

public class QuotationRecordVO {
	
	private Long contentId;
	private boolean record = true;
	private Double discount;
	private boolean lessProductRatio = false;
	
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public boolean isRecord() {
		return record;
	}
	public void setRecord(boolean record) {
		this.record = record;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public boolean isLessProductRatio() {
		return lessProductRatio;
	}
	public void setLessProductRatio(boolean lessProductRatio) {
		this.lessProductRatio = lessProductRatio;
	}
	
	

}
