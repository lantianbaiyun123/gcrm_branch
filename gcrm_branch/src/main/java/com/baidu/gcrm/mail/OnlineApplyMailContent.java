package com.baidu.gcrm.mail;

import java.util.Set;

public class OnlineApplyMailContent {
	/**	收件人 */
	private Set<String> sendTo;	
	/**	抄送 */
	private Set<String> cc;
	
	private String operator;
	
	private String customerName;
	
	private String contentNumber;
	
	private String contractNumber;
	
	private String adSolutionURL;
	
	private String adSolutionNumber;

	public Set<String> getSendTo() {
		return sendTo;
	}

	public void setSendTo(Set<String> sendTo) {
		this.sendTo = sendTo;
	}

	public Set<String> getCc() {
		return cc;
	}

	public void setCc(Set<String> cc) {
		this.cc = cc;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContentNumber() {
		return contentNumber;
	}

	public void setContentNumber(String contentNumber) {
		this.contentNumber = contentNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getAdSolutionURL() {
		return adSolutionURL;
	}

	public void setAdSolutionURL(String adSolutionURL) {
		this.adSolutionURL = adSolutionURL;
	}

	public String getAdSolutionNumber() {
		return adSolutionNumber;
	}

	public void setAdSolutionNumber(String adSolutionNumber) {
		this.adSolutionNumber = adSolutionNumber;
	}
	
}
