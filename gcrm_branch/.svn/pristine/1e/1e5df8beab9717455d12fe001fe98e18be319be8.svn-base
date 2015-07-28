package com.baidu.gcrm.ad;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.TestUtils;
import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.content.web.AdSolutionContentApplyAction;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentApplyVo;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.web.utils.AutoSuggestBean;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;
import com.baidu.gcrm.attachment.service.IAttachmentModelService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.json.JsonBean;

@Ignore
public class AdContentOnlineApplyActionTest extends BaseTestContext {
	@Autowired
	private AdSolutionContentApplyAction action;
	@Autowired
	private IAdSolutionContentApplyService applyService;
	@Autowired
	private IAttachmentModelService attachService;
	
	
	@Before
    public void init() throws Exception {
        TestUtils.initDatabase(dataSource, "datas/g_adcontentonlineapply.xml");
    }
	
	@Test
	public void contractSuggest(){
		Long customerId=134L;
		JsonBean<List<AutoSuggestBean<Contract>>> object=action.contractSuggest(customerId);
		Assert.assertEquals(1, object.getData().size());
	}
   
	@Test
	public void submit(){
		AdSolutionContentApplyVo avo=new AdSolutionContentApplyVo();
		AdSolutionContentApply apply=new AdSolutionContentApply();
		apply.setAdSolutionId(11l);
		apply.setAdSolutionContentId(1l);
		apply.setAdSolutiionContentNumber("17006893-02");
		apply.setApplyReason("no reason");
		apply.setCreateOperator(1111l);
		apply.setCreateTime(new Date());
		AttachmentModel atta=new AttachmentModel();
		atta.setTransactionRecordId(-1l);
		atta.setFieldName("fieldname");
		atta.setModuleName(ModuleNameWithAtta.adcontentonlineapply);
		atta.setUrl("http://wwww");
		atta.setName("name");
		atta.setTempUrl("http://www.test123.com");
		avo.setAdSolutionContentApply(apply);
		avo.setAttachments(Arrays.asList(atta));
		action.saveAdContentApply(avo,null);
		AdSolutionContentApply apply2=applyService.findAdContentApply(apply.getId());
		//submit2();
	}
	
	public void submit2(){
		AdSolutionContentApplyVo avo=new AdSolutionContentApplyVo();
		AdSolutionContentApply apply=new AdSolutionContentApply();
		apply.setAdSolutionId(11l);
		apply.setAdSolutionContentId(1l);
		apply.setId(1l);
		apply.setAdSolutiionContentNumber("17006893-02wwwwwwwww");
		apply.setApplyReason("no reasonwwwwwwwwwwwwwww");
		apply.setCreateOperator(1111l);
		apply.setCreateTime(new Date());
		AttachmentModel atta=new AttachmentModel();
		atta.setTransactionRecordId(-1l);
		atta.setFieldName("fieldnamwe");
		atta.setModuleName(ModuleNameWithAtta.adcontentonlineapply);
		atta.setUrl("http://wwwwwwwwwwwwww");
		atta.setName("name");
		atta.setTempUrl("http://www.test123wwwww.com");
		avo.setAdSolutionContentApply(apply);
		avo.setAttachments(Arrays.asList(atta));
		action.saveAdContentApply(avo,null);
		AdSolutionContentApply apply2=applyService.findAdContentApply(apply.getId());
	
		AttachmentModel atta2=attachService.findByRecordAndModule(apply2.getId(), ModuleNameWithAtta.adcontentonlineapply).get(0);
		
		List<AdContentApplyApprovalRecord> list=applyService.findApprovalRecordsByOnlineApplyId(apply2.getId(), LocaleConstants.en_US);
	}
}
