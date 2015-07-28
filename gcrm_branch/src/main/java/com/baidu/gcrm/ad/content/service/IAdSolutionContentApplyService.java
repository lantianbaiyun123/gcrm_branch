package com.baidu.gcrm.ad.content.service;

import java.util.List;

import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentApplyVo;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.mail.OnlineApplyMailContent;
import com.baidu.gcrm.user.model.User;

public interface IAdSolutionContentApplyService {

	public AdSolutionContentApply saveAdContentApply(AdSolutionContentApplyVo adContentApplyVo);
	public AdSolutionContentApply findAdContentApply(Long contentApplyId);
	public AdSolutionContentApply findAdContentApplyByConId(Long contentId);
	public void saveAdContentApplyApproval(AdContentApplyApprovalRecord adContentApplyApprovalRecord);
	public AdSolutionContentApplyVo findAdSolutionContentApplDetial(Long applyId, LocaleConstants currentLocale);
	public void processApproveRequest(AdContentApplyApprovalRecord approvalRecord,User operateUser,LocaleConstants currentLocale);
	public List<AdContentApplyApprovalRecord> findApprovalRecordsByOnlineApplyId(Long applyId,LocaleConstants currentLocale);
	public void remindersContentByMail(Long id, LocaleConstants currentLocale);
	public AdSolutionContentApply withdrawOnlineApplyById(Long applyId,LocaleConstants locale);
	public List<Contract> findImmeContractsFromCMS(Long customerId);
	public OnlineApplyMailContent getMailContent4OnlineReqeust(AdSolutionContentApply apply,boolean approved);
	public List<AdSolutionContentApply> findByContractNumber(String contractNumber);
	public void terminateOnlineApplyProcess(AdSolutionContentApply apply);
}
