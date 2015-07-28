package com.baidu.gcrm.quote.approval.record.service;



import java.io.IOException;
import java.util.List;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.quote.approval.record.model.QtApprovalRecord;
import com.baidu.gcrm.quote.approval.record.web.vo.QtprovalRecordVO;
import com.baidu.gcrm.quote.model.QuotationMain;

public interface IQtApprovalRecordService {
	
    void saveApprovalRecord(QtApprovalRecord record);
    
	void saveApprovalRecordVO(QtprovalRecordVO vo);
	
	List<QtApprovalRecord> findByQuoteMainId(Long qtMainId, String processDefId,
            LocaleConstants currentLocale);
	QtApprovalRecord findByTaskId(String taskId);	
	void saveAndCompleteApproval(QtprovalRecordVO vo,Long id,LocaleConstants currentLocale);	
	void withDrawApproval(Long id);
	List<QtApprovalRecord> findRecordByQuoteMainId(Long quoteMainId);
	void findMail(Long mainId, LocaleConstants currentLocale) throws IOException;
}
