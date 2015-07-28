package com.baidu.gcrm.ad.content.dao;

import java.util.List;

import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.common.LocaleConstants;

public interface IAdSolutionContentApplyRepository  {

	public void saveAdContentApply(AdSolutionContentApply solutionContentApply);
	public AdSolutionContentApply findAdContentApply(Long id);
	public AdSolutionContentApply findAdContentApplyByConId(Long contentId);
	public void saveAdContentApplyApproval(AdContentApplyApprovalRecord adContentApplyApprovalRecord);
	public List<AdContentApplyApprovalRecord> findAdContentApplyApprovalRecords(String processDefId ,LocaleConstants local ,Long onlineApplyId);
	public List<AdSolutionContentApply> findByContractNumber(String contractNumber);
}
