package com.baidu.gcrm.bpm.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.ProcessHelper;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.common.CommonHelper;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gwfp.ws.BPMClientExtendWebService;
import com.baidu.gwfp.ws.dto.ActivityContentResponse;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.CompleteActivityRequest;
import com.baidu.gwfp.ws.dto.CreateProcessRequest;
import com.baidu.gwfp.ws.dto.CreateProcessResponse;
import com.baidu.gwfp.ws.dto.PreCreateProcessRequest;
import com.baidu.gwfp.ws.dto.ProcessContext;
import com.baidu.gwfp.ws.dto.ProcessView;
import com.baidu.gwfp.ws.exception.GWFPException;

public abstract class AbstractStartProcessService implements IBpmProcessStartService {

	protected BPMClientExtendWebService bpmExtWebService;
	
	protected abstract ActivityData[] buildPrepareActivityData(StartProcessBean startBean);

	protected abstract void fillParticipant(StartProcessBean startBean, ProcessContext context);

	protected abstract ActivityData[] buildActivityData(StartProcessBean startBean);
	
	protected abstract ActivityData[] buildCompleteActivityData(CompleteActivityReq request);

	protected abstract CreateProcessRequest createBpmRequest(StartProcessBean startBean);
	
	protected void validate(StartProcessBean startBean) throws Exception {
		startBean.validate();
	}


	protected ProcessContext prepare(StartProcessBean startBean) throws Exception {
		PreCreateProcessRequest preRequest = new PreCreateProcessRequest();
		preRequest.setPackageId(startBean.getPackageId());
		preRequest.setProcessDefineId(startBean.getProcessDefineId());

		try {
			return bpmExtWebService.preCreateProcess(preRequest);
		} catch (GWFPException e) {
			logStartProcessException(startBean, e);
			throw new CRMRuntimeException("process.prepare.start.error");
		}
	}

	@Override
	public StartProcessResponse startProcess(StartProcessBean startBean){
		CreateProcessResponse response = null;
		try {
			ProcessContext context = prepare(startBean);

			validate(startBean);

			fillParticipant(startBean, context);

			CreateProcessRequest request = createBpmRequest(startBean);
			request.setParticapants(context.getParticipantInfos());
			request.setActivityData(buildActivityData(startBean));

			String processName = request.getProcessName();
			if (StringUtils.isBlank(processName)) {
				request.setProcessName(generateDefaultProcessName(startBean.getStartUser(), context));
			} else {
				request.setProcessName(CommonHelper.xssFilter(processName));
			}
			
			response = bpmExtWebService.createProcess(request);

			ActivityContentResponse[] activityResponse = after(startBean, response);
			List<Activity> activities = ProcessHelper.convertActivityContentResponseToActivities(activityResponse).getActivities();
			
			return generateStartProcessResponse(response, activities);
		} catch (GWFPException e) {
			logStartProcessException(startBean, e);
			throw new CRMRuntimeException("process.start.error." + e.getErrorCode());
		} catch (Exception e) {
			logStartProcessException(startBean, e);
			throw new CRMRuntimeException("process.start.error");
		}
	}

	private StartProcessResponse generateStartProcessResponse(CreateProcessResponse response, List<Activity> activities) {
		ActivityContentResponse firstActivity = response.getActivities()[0];
		ProcessView processView = response.getProcessView();
		StartProcessResponse startResponse = new StartProcessResponse();
		startResponse.setActDefId(firstActivity.getActivityDefineId());
		startResponse.setActivities(activities);
		startResponse.setFirstActivityId(firstActivity.getActivityId());
		startResponse.setProcessId(processView.getProcessId());
		startResponse.setStartUser(processView.getCreateUser());
		startResponse.setProcessStartTime(processView.getProcessCreateTime());
		return startResponse;
	}


	protected ActivityContentResponse[] after(StartProcessBean startBean, CreateProcessResponse response){
		String processId = response.getProcessView().getProcessId();
		String activityId = response.getActivities()[0].getActivityId();
		String performer = startBean.getStartUser();

		CompleteActivityRequest completeRequest = new CompleteActivityRequest();

		completeRequest.setProcessId(processId);
		completeRequest.setActivityId(activityId);
		completeRequest.setPerformUser(performer);

		try {
			return bpmExtWebService.completeActivity(completeRequest);
		} catch (GWFPException e) {
			try {
				bpmExtWebService.terminalProcess(processId, Constants.SYSTEM_USERNAME, "完成提交任务失败，自动终止该流程");
			} catch (GWFPException e1) {
				LoggerHelper.err(getClass(), "自动终止流程失败, processId:{}。", processId);
			}
			LoggerHelper.err(getClass(), "{}完成任务失败，流程id：{}，任务id：{}，自动终止流程。", performer, processId, activityId);
			throw new CRMRuntimeException("activity.complete.error." + e.getErrorCode());
		}
	}
	
	protected String generateDefaultProcessName(String startUser, ProcessContext context) {
		return  startUser + "_" + context.getProcessDefineName();
	}
	
	private void logStartProcessException(StartProcessBean startBean, Exception e) {
		LoggerHelper.err(getClass(), "流程发起失败, 流程名称: {}, 发起人: {}, 流程变量: {}, 失败原因: {} ", startBean.getProcessName(),
				startBean.getStartUser(), startBean.toString(), e.getMessage());
	}
	
	@Override
	public CompleteActivityResponse completeActivity(CompleteActivityReq request) {
		try {
			CompleteActivityRequest completeReq = new CompleteActivityRequest();
			completeReq.setProcessId(request.getProcessId());
			completeReq.setActivityId(request.getActivityId());
			completeReq.setPerformUser(request.getPerformer());
			completeReq.setActivityData(buildCompleteActivityData(request));
			ActivityContentResponse[] response = bpmExtWebService.completeActivity(completeReq);
			String message = String.format("%s完成任务，流程id：%s，任务id：%s", completeReq.getPerformUser(), completeReq.getProcessId(), completeReq.getActivityId());
			LoggerHelper.info(getClass(), message);
			return ProcessHelper.convertActivityContentResponseToActivities(response);
			
		} catch (GWFPException e) {			
			String message = String.format("%s完成任务失败，流程id：%s，任务id：%s，原因: %s", request.getPerformer(), request.getProcessId(), request.getActivityId(), e.getMessage());
			LoggerHelper.err(getClass(), message);
			throw new CRMRuntimeException("activity.complete.error." + e.getErrorCode());
		}
	}
	
	@Autowired
	public void setBpmExtWebService(BPMClientExtendWebService bpmExtWebService) {
		this.bpmExtWebService = bpmExtWebService;
	}
}