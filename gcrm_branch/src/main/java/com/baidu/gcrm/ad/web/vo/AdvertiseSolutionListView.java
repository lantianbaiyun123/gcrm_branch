package com.baidu.gcrm.ad.web.vo;

public class AdvertiseSolutionListView {
	private Long id;// 广告方案id
	private String number;// 广告方案编号
	private String contract_number;// 合同名称
	private String contract_state;// 合同状态
	private String type;// 方案类型
	private String approval_status;// 方案状态
	private String companyName;//公司名称
	private String cmsUrl;//CMS合同访问路径
	private String contractStatus;
	private String operatorName;//执行人
	private String taskInfo;
	private String taskInfoMessage;
	private String contractType;
	private boolean isOwner;

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContract_number() {
		return contract_number;
	}

	public void setContract_number(String contract_number) {
		this.contract_number = contract_number;
	}

	public String getContract_state() {
		return contract_state;
	}

	public void setContract_state(String contract_state) {
		this.contract_state = contract_state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApproval_status() {
		return approval_status;
	}

	public void setApproval_status(String approval_status) {
		this.approval_status = approval_status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCmsUrl() {
		return cmsUrl;
	}

	public void setCmsUrl(String cmsUrl) {
		this.cmsUrl = cmsUrl;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public String getTaskInfoMessage() {
		return taskInfoMessage;
	}

	public void setTaskInfoMessage(String taskInfoMessage) {
		this.taskInfoMessage = taskInfoMessage;
	}
	
	public boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
}
