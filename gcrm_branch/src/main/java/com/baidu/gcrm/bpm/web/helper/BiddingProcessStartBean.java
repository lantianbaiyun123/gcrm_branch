package com.baidu.gcrm.bpm.web.helper;

public class BiddingProcessStartBean extends StartProcessBean {

	private Long adContentId;
	
	@Override
	public void validate() {

	}

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	@Override
	public String toString() {
		return "BiddingProcessStartBean [adContentId=" + adContentId + "]";
	}

}
