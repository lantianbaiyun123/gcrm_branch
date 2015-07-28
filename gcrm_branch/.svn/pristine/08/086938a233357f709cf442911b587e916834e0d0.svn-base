package com.baidu.gcrm.occupation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.BaseTestContext;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.vo.ProcessQueryBean;
import com.baidu.gcrm.bpm.vo.ProcessQueryConditionBean;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gwfp.ws.BPMClientExtendWebService;
import com.baidu.gwfp.ws.dto.ProcessView;
import com.baidu.gwfp.ws.exception.GWFPException;

@Ignore
public class BPMTest extends BaseTestContext {
	@Autowired
	IBpmProcessService processService;
	@Autowired
	BPMClientExtendWebService bpmExtWebService;
	@Autowired
	BPMClientExtendWebService cmsClientBpmExtWebService;

	@Before
	public void init() throws Exception {
	}
	
	@Test
	public void testComplete() {
		CompleteActivityReq request = new CompleteActivityReq();
		request.setActivityId("4625996_act3");
		request.setProcessId("1723035_gcrm_pkg_506_prs1");
		request.setPerformer("anhuan");
		processService.completeActivity(request);
	}
	
	@Test
	public void testRemind() {
		RemindRequest request = new RemindRequest();
		request.setNotifyType(0);
		request.setReminder("anhuan");
		request.setType(RemindType.bidding);
		request.setKey("798");
		processService.remindByForeignKey(request);
	}
	
	@Test
	public void confirmSchedule() {
		ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
    	queryBean.setForeignKey("703");
    	queryBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
    	queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey("bidding.process.defineId"));
    	List<Integer> processState = new ArrayList<Integer>();
    	int runningState = 0;
    	processState.add(runningState);
    	queryBean.setProcessState(processState);
		CompleteActivityReq req = new CompleteActivityReq();
		int approved = 1;
		req.setApproved(approved);
		req.setPerformer(RequestThreadLocal.getLoginUsername());
		processService.completeActivityByCondition(queryBean, req);
	}
	
	@Test
	public void testQuery() {
		ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
    	queryBean.setForeignKey("846");
    	queryBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
    	queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey("bidding.process.defineId"));
    	/*List<Integer> processState = new ArrayList<Integer>();
    	int runningState = 0;
    	processState.add(runningState);
    	queryBean.setProcessState(processState);*/
    	List<ProcessQueryBean> processes = processService.queryProcess(queryBean);
    	System.out.println(processes.size());
	}
	
	@Test
	public void updateParticipant() {
		com.baidu.gwfp.ws.dto.ParticipantInfo info = new com.baidu.gwfp.ws.dto.ParticipantInfo();
		info.setParticipantId("sales");
		info.setResourceIds(new String[]{"lixiaoli"});
		com.baidu.gwfp.ws.dto.ParticipantInfo[] participantInfos = new com.baidu.gwfp.ws.dto.ParticipantInfo[]{info} ;
		
		try {
			bpmExtWebService.updateProcessParticipant("1796131_gcrm_pkg_506_prs2", participantInfos);
		} catch (GWFPException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void cmsClientTest() throws GWFPException {
		ProcessView[] processViews = cmsClientBpmExtWebService.getProcessOfUser("huyang01");
		System.out.println(processViews.length);
		if (processViews.length > 0) {
			System.out.println(processViews[0].getProcessName());
		}
	}
}
