package com.baidu.gcrm.ad.web.vo;

import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;

public class AdvertiseSolutionView {
	private AdvertiseSolution advertiseSolution;
	private String advertiseSolutionApproveState;
	private String advertiseSolutionType;
	private CurrencyType currencyType;
	
	public AdvertiseSolution getAdvertiseSolution() {
		return advertiseSolution;
	}
	public void setAdvertiseSolution(AdvertiseSolution advertiseSolution) {
		this.advertiseSolution = advertiseSolution;
	}
	public String getAdvertiseSolutionApproveState() {
		return advertiseSolutionApproveState;
	}
	public void setAdvertiseSolutionApproveState(
			String advertiseSolutionApproveState) {
		this.advertiseSolutionApproveState = advertiseSolutionApproveState;
	}
	public String getAdvertiseSolutionType() {
		return advertiseSolutionType;
	}
	public void setAdvertiseSolutionType(String advertiseSolutionType) {
		this.advertiseSolutionType = advertiseSolutionType;
	}
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	
	
}
