package com.baidu.gcrm.ad.web.vo;

import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;

public class AdSolutionBaseInfoView {
	private CustomerI18nView customerI18nView;
	private AdvertiseSolutionView advertiseSolutionView;
	private Contract contract;
	private Boolean hasContract;
	private Long operatorId;
	private String operatorName;
	
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Boolean getHasContract() {
		return hasContract;
	}
	public void setHasContract(Boolean hasContract) {
		this.hasContract = hasContract;
	}
	public CustomerI18nView getCustomerI18nView() {
		return customerI18nView;
	}
	public void setCustomerI18nView(CustomerI18nView customerI18nView) {
		this.customerI18nView = customerI18nView;
	}
	public AdvertiseSolutionView getAdvertiseSolutionView() {
		return advertiseSolutionView;
	}
	public void setAdvertiseSolutionView(AdvertiseSolutionView advertiseSolutionView) {
		this.advertiseSolutionView = advertiseSolutionView;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
}
