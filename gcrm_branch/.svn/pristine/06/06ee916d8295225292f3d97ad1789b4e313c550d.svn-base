package com.baidu.gcrm.quote.approval.record.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;

public interface IQtApprovalRecordRepository extends JpaRepository<QtApprovalRecord, Long> {
    
    List<QtApprovalRecord> findByQuoteContentId(Long adSolutionId);

    QtApprovalRecord findByTaskId(String taskId);
    
    List<QtApprovalRecord> findByQuoteMainId(Long quoteMainId);
    
  
    //List<QtApprovalRecord> findContentApprovalReord(Long adContentId);
    
}
