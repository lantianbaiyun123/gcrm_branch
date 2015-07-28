package com.baidu.gcrm.ad.material.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.material.model.MaterialApprovalRecord;
import com.baidu.gcrm.common.LocaleConstants;

public interface MaterialApproveRecordRepository extends JpaRepository<MaterialApprovalRecord, Long>{
  
    @Query("SELECT R,u.realname,C.activityName FROM MaterialApprovalRecord R,User u,ActivityNameI18n C WHERE R.createOperator = u.ucid AND  R.actDefId = C.activityId AND C.processDefId =?1 AND   C.locale = ?2 AND R.adMaterialApplyId =?3 order by R.createTime ")
    List<Object> findRecordByMaterialApplyId(String processDefId ,LocaleConstants local ,Long materialApplyId);    
    
    @Query("select count(*) from MaterialApprovalRecord where adMaterialApplyId = ?1")
    public Long findRecordCountByMaterialApplyId(Long applyId);
}
