package com.baidu.gcrm.quote.web.vo;

import java.util.List;

import com.baidu.gcrm.quote.model.QuotationMain;

public class QuotationMainAddVO {
	private QuotationMain quotationMain;
	private List<QuotationAddVO> quotationList;
	private boolean showUnit = false;
	private boolean showRatio = false;
	private boolean showRebate = false;
	
	public QuotationMain getQuotationMain() {
		return quotationMain;
	}
	public void setQuotationMain(QuotationMain quotationMain) {
		this.quotationMain = quotationMain;
	}
	
	public List<QuotationAddVO> getQuotationList() {
		return quotationList;
	}
	public void setQuotationList(List<QuotationAddVO> quotationList) {
		this.quotationList = quotationList;
	}
	public boolean isShowUnit() {
		return showUnit;
	}
	public void setShowUnit(boolean showUnit) {
		this.showUnit = showUnit;
	}
	public boolean isShowRatio() {
		return showRatio;
	}
	public void setShowRatio(boolean showRatio) {
		this.showRatio = showRatio;
	}
	public boolean isShowRebate() {
		return showRebate;
	}
	public void setShowRebate(boolean showRebate) {
		this.showRebate = showRebate;
	}
}
