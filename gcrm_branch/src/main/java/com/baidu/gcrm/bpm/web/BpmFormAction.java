package com.baidu.gcrm.bpm.web;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.AdPlanProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.ApprovalRecordBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.SmartFormFactory;
import com.baidu.gcrm.bpm.web.helper.SmartFormRequest;
import com.baidu.gcrm.common.CommonHelper;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.BPMClientExtendWebService;
import com.baidu.gwfp.ws.dto.ActivityContentResponse;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.ActivityDefine;
import com.baidu.gwfp.ws.exception.GWFPException;

@Controller
@RequestMapping("/adPlan")
public class BpmFormAction extends ControllerHelper{
	@Autowired
	protected BPMClientExtendWebService bpmExtWebService;
	
	@Autowired
	@Qualifier("adPlanProcessServiceImpl")
	protected IBpmProcessStartService adPlanProcessService;
	
	@Autowired
	protected IBpmProcessService processService;
	
	@RequestMapping("/preCreate")
	public String preCreate(Model model){
		return "adPlanForm/createAdPlan"; 
	}
	
	@RequestMapping("/createAdPlan")
	public String createAdPlanProcess(AdPlanProcessStartBean startBean,Model model){
		startBean.setPackageId("gcrm_pkg_506");
		startBean.setProcessDefineId("gcrm_pkg_506_prs1");
		startBean.setStartUser(super.getUserName());
		startBean.setProcessName("Submit Plan");

		try {
			StartProcessResponse startProcess = adPlanProcessService.startProcess(startBean);
	        model.addAttribute("activities", startProcess.getActivities());
		} catch (Exception e) {
	        e.printStackTrace();
        }
		
		return "adPlanForm/createSuccess";
	}
	
	@RequestMapping("/tasks")
	public String tasks(Model model) {
		List<Activity> activities = processService.getCurrentActivitiesOfUser(super.getUserName(), currentLocale);
		model.addAttribute("activities", activities);
		return "adPlanForm/tasks";
	}
	
	@Override
	protected String getUserName() {
		String userName = null;
		User user = RequestThreadLocal.getLoginUser();
		if(user != null){
			if(StringUtils.isNotEmpty(user.getUuapName())){
				userName = user.getUuapName();
			}else{
				userName = user.getUsername();
			}
		}
		return userName;
	}
	
	@RequestMapping("/requestForm")
	public String requestForm(SmartFormRequest smartFormRequest, Model model) throws GWFPException{
		String activityId = smartFormRequest.getActivityId();
		if(StringUtils.isNotBlank(activityId)){
			putBackableListIfExist(model,activityId);
		}
		putActivityData(smartFormRequest,model);
		model.addAttribute("activityId", activityId);
		model.addAttribute("processId", smartFormRequest.getProcessId());
		return "adPlanForm/"+SmartFormFactory.generateFormPage(smartFormRequest);
	}
	
	private void putBackableListIfExist(Model model, String activityId)
			throws GWFPException {
		ActivityDefine[] actDefines = bpmExtWebService.getBackableActivityDefineListByActivityId(activityId);
		if(ArrayUtils.isNotEmpty(actDefines)){
			model.addAttribute("actDefines", actDefines);
		}
	}
	
	private void putActivityData(SmartFormRequest smartFormRequest,Model model) throws GWFPException{
		String processId = smartFormRequest.getProcessId();
		String activityId = smartFormRequest.getActivityId();
		ActivityData[] activityDatas = bpmExtWebService.getProcessDataList(processId,activityId);
		if(ArrayUtils.isEmpty(activityDatas)){
			return;
		}
		for(ActivityData activityData : activityDatas){
			String dataValue = activityData.getValue();
			if(StringUtils.isNotBlank(dataValue)){
				dataValue = CommonHelper.reXssFilter(dataValue);
			}
			model.addAttribute(activityData.getDataName(),dataValue);
		}
	}
	
	@RequestMapping("/getApprovalRecords")
	public String getApprovalRecords(String processId, Model model) {
		List<ApprovalRecordBean> approvalRecords = processService.getApprovalRecords(processId);
		model.addAttribute("approvalRecords", approvalRecords);
		return "adPlanForm/approvalRecords";
	}
	
	@RequestMapping("/completeActivity")
	public String completeActivity(CompleteActivityReq request,Model model){
		request.setPerformer(getUserName());
		List<Activity> activities = processService.completeActivity(request).getActivities();
		model.addAttribute("activities", activities);
		
		return "adPlanForm/completeSuccess";
	}
}
