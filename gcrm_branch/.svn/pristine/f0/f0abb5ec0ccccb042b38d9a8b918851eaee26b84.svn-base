package com.baidu.gcrm.ad;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.ad.approval.record.model.ApprovalInsertRecord;
import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.web.ApprovalRecordAction;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalRecordVO;

@Ignore
public class AdSolutionApprovalActionTest extends BaseTestContext {
	
	@Autowired
	ApprovalRecordAction approvalRecordAction;
	
	@Before
    public void init() throws Exception {
        TestUtils.initDatabase(dataSource, "datas/g_ad_approval_record.xml");
    }
	
	
	@Test
	public void testSave(){
	    ApprovalRecordVO vo = new ApprovalRecordVO();
	    ApprovalRecord record  = new ApprovalRecord();
	    record.setApprovalStatus(1);
	    record.setApprovalSuggestion("ok");
	    record.setTaskId("act1");
	    List<ApprovalInsertRecord> insertRecords = new ArrayList<ApprovalInsertRecord> ();
	    ApprovalInsertRecord insertRecord = new ApprovalInsertRecord();
	    insertRecord.setAllowInsert(0);
	    insertRecords.add(insertRecord);
	    vo.setRecord(record);
	    vo.setInsertRecords(insertRecords);
	    vo.setActivityId("act1");
	    approvalRecordAction.saveApprovalRecord(vo);
	    Long id = record.getId();
	    Assert.assertTrue(id != null && id.longValue() >0);
	}
	
	@Test
	public void testQueryRecord(){
	    Assert.assertNotNull(approvalRecordAction.queryApprovalInsertedRecord(1L).getData());
	    
	}
	
	@Test
    public void testQueryInsertedRecord(){
	    Assert.assertNotNull(approvalRecordAction.queryApprovalInsertedRecord(1L).getData());
    }
	
}
