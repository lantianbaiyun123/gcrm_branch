package com.baidu.gcrm.ad.content.web.vo;

import java.util.List;

import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.attachment.model.AttachmentModel;

public class AdSolutionContentApplyVo {
	
	private AdSolutionContentApply adSolutionContentApply;
	
	private List<AttachmentModel> attachments;
	
	private List<AdContentApplyApprovalRecord> approvalRecords;
	
	private List<Contract> contracts;

	public List<AdContentApplyApprovalRecord> getApprovalRecords() {
		return approvalRecords;
	}

	public void setApprovalRecords(
			List<AdContentApplyApprovalRecord> approvalRecords) {
		this.approvalRecords = approvalRecords;
	}

	public List<AttachmentModel> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentModel> attachments) {
		this.attachments = attachments;
	}

	public AdSolutionContentApply getAdSolutionContentApply() {
		return adSolutionContentApply;
	}

	public void setAdSolutionContentApply(
			AdSolutionContentApply adSolutionContentApply) {
		this.adSolutionContentApply = adSolutionContentApply;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

	

}
