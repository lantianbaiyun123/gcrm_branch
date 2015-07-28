package com.baidu.gcrm.cms;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.ws.cms.ICMSRequestFacade;
import com.baidu.gcrm.ws.cms.ICMSServiceFacade;

@Ignore
public class CmsWebServiceTest extends BaseTestContext{
	
    ICMSRequestFacade cmsRequestFacade;
    
    @Autowired
    ICMSServiceFacade cmsServiceFacade;
    
    @Before
    public void init() throws Exception {
        TestUtils.initDatabase(dataSource, "datas/g_contract.xml");
    }

    
    @Ignore
    public void testSendCustomer() {
        cmsRequestFacade.syncCustomer(251383554L);
    }
    
    @Ignore
    public void testSendAdSolution() {
        cmsRequestFacade.createContract(21L,null);
    }
    
    @Ignore
    public void testCreatePO() {
        cmsRequestFacade.createPO("TEST123132213123", 21l, 1000l,null);
    }
    
    @Ignore
    public void testCreateSingleContract() {
        cmsRequestFacade.createSingleContract("QEQEWQWEQ124324", Long.valueOf(21l));
    }
    
    @Ignore
    public void testCancelAdSolution() {
        System.out.println(cmsRequestFacade.cancelAdSolution(21L));
    }
    
    
    @Ignore
    public void testQueryPosition() {
        System.out.println(cmsServiceFacade.queryAllPosition());
    }
    
    @Test
    public void testQueryQuotation() {
    	cmsServiceFacade.queryAllQuotation();
    }
    
}
