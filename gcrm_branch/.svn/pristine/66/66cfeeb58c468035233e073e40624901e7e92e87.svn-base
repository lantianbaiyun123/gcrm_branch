package com.baidu.gcrm.log.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.log.model.ModifyRecord;
import com.baidu.gcrm.log.service.ModifyRecordService;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.service.QuotationService;

@Ignore
public class ModifyRecordServiceTest extends BaseTestContext{
	
	@Autowired
	private ModifyRecordService modifyRecordService;
	
	@Autowired
	private QuotationService quoteService;
	
	@Before
	public void init() throws Exception {
		TestUtils.initDatabase(dataSource, "datas/g_quotation.xml");
	}
	
	@Test
	@Rollback
	public void testSave(){
		Quotation quotation = new Quotation();
		quotation.setId(1l);
		quotation.setAdvertisingPlatformId(2l);
		quotation.setSiteId(5l);
		modifyRecordService.saveModifyRecord(quotation);
		//modifyRecordService.saveModifyRecord(Quotation.class, quotation);
		//quoteService.updateQuote(quotation);
		List<ModifyRecord> records=modifyRecordService.findModifyRecord(
				Quotation.class.getSimpleName(), quotation.getId());
		Assert.assertNotNull(records);
		System.out.println(JSON.toJSONString(records));
		for(ModifyRecord record:records){
			System.out.print(record.getModifyField()+":");
			System.out.print(record.getNewValue()+":");
			System.out.println(record.getOldValue());
		}
	}
	
	public void testFindModifyRecord(){
		List<Map<String, Object>> list = modifyRecordService.findModifyRecord(Quotation.class.getSimpleName(), 1l, null);
		Assert.assertNotNull(list);
		System.out.println(JSON.toJSONString(list));
	}

}
