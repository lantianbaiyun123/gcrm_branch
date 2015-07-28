package com.baidu.gcrm.quote.web.vo;

import com.baidu.gcrm.quote.model.QuotationMain;

public class QuotationMainVO implements Cloneable {
	private QuotationMain quotationMain;
	private String siteName;
	private String platformName;
	private String taskInfoMessage;
	private String createrName;
	private boolean isOwner;

	public QuotationMain getQuotationMain() {
		return quotationMain;
	}

	public void setQuotationMain(QuotationMain quotationMain) {
		this.quotationMain = quotationMain;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public QuotationMainVO clone() {
		QuotationMainVO o = null;
		try {
			o = (QuotationMainVO) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public String getTaskInfoMessage() {
		return taskInfoMessage;
	}

	public void setTaskInfoMessage(String taskInfoMessage) {
		this.taskInfoMessage = taskInfoMessage;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

}
