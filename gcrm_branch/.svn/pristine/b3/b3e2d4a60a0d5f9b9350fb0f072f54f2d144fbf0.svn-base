package com.baidu.gcrm.ad.approval.web.vo;

import java.util.List;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.model.AdvertiseSolutionType;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.customer.web.vo.CustomerI18nView;

public class AdApprovalInfoView {
	private AdvertiseSolution adSolution;
	private String adSolutionType;
	// 方案执行人
	private Account account;
	// 方案提交人
	private Account creatOperator;
	private Contract contract;
	private String contractUrl;
	private CustomerI18nView customerI18nView;
	private List<AdApprovalContentView> approvalContentViews;
	private String role;
	private boolean taskClose;
	private boolean canWithdraw = false;
	private boolean canUpdate = true;
	private boolean canCreatePO = true;
	private boolean canDelete = false;
	private List<AdContentCancelRecord> cancelRecord;
	private AdvertiseSolutionType type;
	private AdvertiseSolutionApproveState state;
	private String oldSolutionNumber;
	private String oldSolutionContratType;
	// 广告方案提交人、或者执行人是自己
	private boolean isOwner;

	public AdvertiseSolution getAdSolution() {
		return adSolution;
	}

	public void setAdSolution(AdvertiseSolution adSolution) {
		this.adSolution = adSolution;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public CustomerI18nView getCustomerI18nView() {
		return customerI18nView;
	}

	public void setCustomerI18nView(CustomerI18nView customerI18nView) {
		this.customerI18nView = customerI18nView;
	}

	public List<AdApprovalContentView> getApprovalContentViews() {
		return approvalContentViews;
	}

	public void setApprovalContentViews(
			List<AdApprovalContentView> approvalContentViews) {
		this.approvalContentViews = approvalContentViews;
	}

	public String getAdSolutionType() {
		return adSolutionType;
	}

	public void setAdSolutionType(String adSolutionType) {
		this.adSolutionType = adSolutionType;
	}

	public Account getCreatOperator() {
		return creatOperator;
	}

	public void setCreatOperator(Account creatOperator) {
		this.creatOperator = creatOperator;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isTaskClose() {
		return taskClose;
	}

	public void setTaskClose(boolean taskClose) {
		this.taskClose = taskClose;
	}

	public List<AdContentCancelRecord> getCancelRecord() {
		return cancelRecord;
	}

	public void setCancelRecord(List<AdContentCancelRecord> cancelRecord) {
		this.cancelRecord = cancelRecord;
	}

	public boolean isCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(boolean canWithdraw) {
		this.canWithdraw = canWithdraw;
	}

	public AdvertiseSolutionType getType() {
		return type;
	}

	public void setType(AdvertiseSolutionType type) {
		this.type = type;
	}

	public AdvertiseSolutionApproveState getState() {
		return state;
	}

	public void setState(AdvertiseSolutionApproveState state) {
		this.state = state;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	public String getOldSolutionNumber() {
		return oldSolutionNumber;
	}

	public void setOldSolutionNumber(String oldSolutionNumber) {
		this.oldSolutionNumber = oldSolutionNumber;
	}

	public String getOldSolutionContratType() {
		return oldSolutionContratType;
	}

	public void setOldSolutionContratType(String oldSolutionContratType) {
		this.oldSolutionContratType = oldSolutionContratType;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

	public boolean isCanCreatePO() {
		return canCreatePO;
	}

	public void setCanCreatePO(boolean canCreatePO) {
		this.canCreatePO = canCreatePO;
	}

	public boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
}