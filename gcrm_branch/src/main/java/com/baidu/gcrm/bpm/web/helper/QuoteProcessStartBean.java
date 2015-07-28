package com.baidu.gcrm.bpm.web.helper;

public class QuoteProcessStartBean extends StartProcessBean{
	private String quoteId;
	
	/** 业务类型 */
	private String buType;
	public String getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getBuType() {
		return buType;
	}
	public void setBuType(String buType) {
		this.buType = buType;
	}

	@Override
	public void validate() {		
	}
	@Override
	public String toString() {
		return "QuoteProcessStartBean [buType=" + buType		
				+ ",quoteId=" + quoteId + "]";
	}


}