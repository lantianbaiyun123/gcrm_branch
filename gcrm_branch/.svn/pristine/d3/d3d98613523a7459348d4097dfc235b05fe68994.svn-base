package com.baidu.gcrm.ad.approval.record.dao;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.approval.record.model.ApprovalInsertRecord;

public interface IApprovalInsertRecordRepository extends JpaRepository<ApprovalInsertRecord, Long> {
    
    List<ApprovalInsertRecord> findByApprovalRecordId(Long approvalRecordId);
    
    @Query("select i from ApprovalInsertRecord i,ApprovalRecord a where i.approvalRecordId= a.id "
    		+ "and i.adsolutionContentId=?1 and i.insertedAdsolutionContentId=?2 and a.taskId like ?3")
    List<ApprovalInsertRecord> findApprovalInsertRecord(
    		Long adsolutionContentId, Long insertedAdsolutionContentId,String taskId);
    
    @Query("select i from ApprovalInsertRecord i,ApprovalRecord a where i.approvalRecordId= a.id "
    		+ "and i.adsolutionContentId=?1 and a.activityId=?2 and (i.isHistory=0 or i.isHistory is null)")
    List<ApprovalInsertRecord> findContentInsertRecord(Long adsolutionContentId,String activityId);
    
    List<ApprovalInsertRecord> findByAdsolutionContentIdAndAllowInsert(Long adsolutionContentId, Integer allowInsert);
    
    @Query("select i from ApprovalInsertRecord i,ApprovalRecord a where i.approvalRecordId=a.id and i.adsolutionContentId=?1 and i.insertedAdsolutionContentId=?2 and a.activityId in (?3)  and i.allowInsert=?4 and i.isHistory=?5 ")
    List<ApprovalInsertRecord> findCountByAllowInsert(Long adsolutionContentId, Long insertedAdsolutionContentId,
            List<String> activityIds, Integer allowInsert, Integer isHistory);
    
    @Query("select i from ApprovalInsertRecord i,ApprovalRecord a where i.approvalRecordId=a.id and i.adsolutionContentId=?1 and a.activityId in (?2) and (i.isHistory=0 or i.isHistory is null)")
    List<ApprovalInsertRecord> findInsertByContentId(Long contentId, List<String> activityIds);
    
    @Modifying
    @Query("update ApprovalInsertRecord set isHistory=1 where adsolutionContentId =?1")
    void updateAdContentHistoryRecord(Long adContentId);
    
    @Modifying
    @Query("update ApprovalInsertRecord set isHistory=1 where adsolutionContentId in (?1)")
    void updateToHistoryRecordInAdContentIds(Collection<Long> adContentIds);
}
