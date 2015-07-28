package com.baidu.gcrm.log.test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.log.web.ModifyRecordAction;

@Ignore
public class ModifyRecordActionTest extends  BaseTestContext{

	@Autowired
	private  ModifyRecordAction modifyRecordAction;
	
	@Before
	public void init() throws Exception{
		TestUtils.initDatabase(dataSource, "datas/g_modify_record.xml");
	}
	
	@Test
	public void testQueryAdModifyReocrds(){
		Long id=1l;
		JsonBean<List<Map<String, Object>>> list = modifyRecordAction.queryAdModifyReocrds(id,"pm", new MockHttpServletRequest());
		System.out.println(JSON.toJSONString(list));
	}
}
