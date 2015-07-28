package com.baidu.gcrm.customer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.customer.model.CustomerApprovalRecord;

public interface CustomerApproveRecordRepository extends JpaRepository<CustomerApprovalRecord, Long>{
  
    @Query("SELECT R,U.realname,C.activityName FROM CustomerApprovalRecord R,User U,ActivityNameI18n C WHERE R.createOperator = U.ucid AND  R.actDefId = C.activityId AND C.processDefId =?1 AND   C.locale = ?2 AND R.customerId =?3 order by R.createTime ")
    List<Object> findRecordByCustomerId(String processDefId ,LocaleConstants local ,Long customer);    
    
    @Query("select count(*) from CustomerApprovalRecord where customerId = ?1")
    public Long findRecordCountByCustomerId(Long customerId);
}
