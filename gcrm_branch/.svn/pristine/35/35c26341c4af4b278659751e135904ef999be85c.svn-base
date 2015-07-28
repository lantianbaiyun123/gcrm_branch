package com.baidu.gcrm.ad.approval.record.service;

import java.util.Collection;
import java.util.List;

import com.baidu.gcrm.ad.approval.record.model.ApprovalInsertRecord;
import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalInsertRecordJsonVO;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalRecordVO;
import com.baidu.gcrm.common.LocaleConstants;

public interface IApprovalRecordService {
	
    void saveApprovalRecord(ApprovalRecord record);
    
	void saveApprovalRecordVO(ApprovalRecordVO vo);
	
	List<ApprovalRecord> findByAdSolutionId(Long adSolutionId, String processDefId,
            LocaleConstants currentLocale);
	
	Collection<ApprovalInsertRecordJsonVO> findByApprovalRecordId(Long approvalRecordId);
	
	Integer checkBusinessAllow(Long adsolutionContentId, Long insertedAdsolutionContentId,String taskId);
	List<ApprovalInsertRecord> findContentInsertRecord(Long adsolutionContentId,String activityId);

	ApprovalRecord findByTaskId(String taskId);
	
	void saveAndCompleteApproval(ApprovalRecordVO vo);
	
	List<ApprovalInsertRecord> findCountByNotAllowInsert(Long adsolutionContentId,
            Long insertedAdsolutionContentId,List<String> activityIds);
	
	List<ApprovalInsertRecord> findByContentId(Long contentId, List<String> activityIds);
	
	List<ApprovalRecord> findByContentId(Long adContentId, String processDefId,
            LocaleConstants currentLocale);
	
	void withdrawAD(Long adSolutionId);
	
	void withdrawSingleContent(Long contentId);
	
	List<ApprovalRecord> findByAdSolutionIdAndAdContentId(Long adSolutionId, Long adContentId);
}
