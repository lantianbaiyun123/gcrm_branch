package com.baidu.gcrm.ad.approval.record.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;

public interface IApprovalRecordRepository extends JpaRepository<ApprovalRecord, Long> {
    
    List<ApprovalRecord> findByAdSolutionId(Long adSolutionId);

    ApprovalRecord findByTaskId(String taskId);
    
    List<ApprovalRecord> findByAdContentId(Long adContentId);
    
    @Query("from ApprovalRecord where adContentId=?1 or (adSolutionId=(select adSolutionId from "
    		+ "AdSolutionContent where id=?1) and adContentId is null)")
    List<ApprovalRecord> findContentApprovalReord(Long adContentId);
    
    List<ApprovalRecord> findByAdSolutionIdAndAdContentId(Long adSolutionId, Long adContentId);
}
