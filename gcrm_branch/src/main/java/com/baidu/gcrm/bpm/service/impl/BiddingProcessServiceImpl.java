package com.baidu.gcrm.bpm.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.bpm.service.AbstractStartProcessService;
import com.baidu.gcrm.bpm.web.helper.BiddingProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.CompleteBiddingActivityReq;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gwfp.ws.dto.ActivityContentResponse;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.CreateProcessRequest;
import com.baidu.gwfp.ws.dto.CreateProcessResponse;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
import com.baidu.gwfp.ws.dto.ProcessContext;

@Service("biddingProcessServiceImpl")
public class BiddingProcessServiceImpl extends AbstractStartProcessService {
	
	@Override
	protected ActivityData[] buildPrepareActivityData(StartProcessBean startBean) {
		return new ActivityData[]{};
	}
	
	@Override
	protected void fillParticipant(StartProcessBean startBean, ProcessContext context) {
		List<ParticipantBean> participants = startBean.getParticipants();
		
		if (CollectionUtils.isEmpty(participants)) {
			throw new CRMRuntimeException("process.start.participant.required");
		}
		
		ParticipantInfo[] parts = context.getParticipantInfos();
		for (ParticipantInfo part : parts) {
			String participantId = part.getParticipantId();
			if (participants.contains(new ParticipantBean(participantId))) {
				ParticipantBean participant = participants.get(participants.indexOf(new ParticipantBean(participantId)));
				List<String> names = participant.getUsernames();
				String[] userNames = names.toArray(new String[names.size()]);
				part.setResourceIds(userNames);
			} else {
				throw new CRMRuntimeException("process.start.participant.required");
			}

		}
	}

	@Override
	public ActivityData[] buildActivityData(StartProcessBean startBean) {
		BiddingProcessStartBean biddingStartBean = accept(startBean);
		// 构建流程数据
		ActivityData[] datas = new ActivityData[]{
				new ActivityData("adContentId", biddingStartBean.getAdContentId().toString())};
		return datas;
	}
	
	@Override
	protected CreateProcessRequest createBpmRequest(StartProcessBean startBean) {
		BiddingProcessStartBean biddingProcessStartBean = accept(startBean);

		CreateProcessRequest request = new CreateProcessRequest();

		// 设置流程启动的packageId,processDefId,processName
		String packageId = biddingProcessStartBean.getPackageId();
		String processDefineId = biddingProcessStartBean.getProcessDefineId();
		String startUserName = biddingProcessStartBean.getStartUser();
		
		request.setPackageId(packageId);
		request.setCreateUserName(startUserName);
		request.setProcessDefineId(processDefineId);
		
		request.setProcessName(biddingProcessStartBean.getProcessName());
		
		if (biddingProcessStartBean.getAdContentId() != null) {
			request.setForeignKey(biddingProcessStartBean.getAdContentId().toString());
		}
		return request;
	}
	
	@Override
	protected ActivityContentResponse[] after(StartProcessBean startBean, CreateProcessResponse response) {
		return response.getActivities();
	}
	
	private BiddingProcessStartBean accept(StartProcessBean startBean) {
		if (startBean instanceof BiddingProcessStartBean) {
			return (BiddingProcessStartBean) startBean;
		}
		throw new RuntimeException("发起流程失败,startBean:" + startBean);
	}
	
	@Override
	public ActivityData[] buildCompleteActivityData(CompleteActivityReq request) {
		CompleteBiddingActivityReq biddingReq = accept(request);
		String scheduleNumber = biddingReq.getScheduleNumber();
		ActivityData activityData = new ActivityData("scheduleNumber", scheduleNumber);
		return new ActivityData[]{activityData};
	}
	
	private CompleteBiddingActivityReq accept(CompleteActivityReq req) {
		if (req instanceof CompleteBiddingActivityReq) {
			return (CompleteBiddingActivityReq) req;
		}
		throw new RuntimeException("完成任务失败, CompleteActivityReq:" + req);
	}
}
