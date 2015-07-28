package com.baidu.gcrm.bpm.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.AbstractStartProcessService;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.CompleteQuoteActivityReq;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.ProcessHelper;
import com.baidu.gcrm.bpm.web.helper.QuoteProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.CreateProcessRequest;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
import com.baidu.gwfp.ws.dto.ProcessContext;

@Service("quoteProcessServiceImpl")
public class QuoteProcessServiceImpl extends AbstractStartProcessService {
	@Autowired
	IUserRightsService userRightsService;
	
	@Value("#{appproperties['quote.activityData.buType']}")
	private String buType;

	@Override
	protected ActivityData[] buildPrepareActivityData(StartProcessBean startBean) {
		return new ActivityData[]{};
	}
	
	@Override
	protected void fillParticipant(StartProcessBean startBean, ProcessContext context) {
		QuoteProcessStartBean quoteStartBean = accept(startBean);
		List<ParticipantBean> participants = startBean.getParticipants();
		String startUser = "startUser";
		
		ParticipantInfo[] parts = context.getParticipantInfos();
		for (ParticipantInfo part : parts) {
			String participantId = part.getParticipantId();
			if(startUser.equals(participantId)){
				part.setResourceIds(new String[]{quoteStartBean.getStartUser()});
				continue;
			}
			
			String[] userNames;
			if (participants != null && participants.contains(new ParticipantBean(participantId))) {
				ParticipantBean participant = participants.get(participants.indexOf(new ParticipantBean(participantId)));
				List<String> names = participant.getUsernames();
				userNames = names.toArray(new String[names.size()]);
			} else {
				List<User> users = userRightsService.findUserByRoleTag(participantId);
				if(CollectionUtils.isEmpty(users)){
					continue;
				}
				
				userNames = new String[users.size()];
				int i = 0;
				for(User user : users){
					userNames[i++] = user.getUuapName();
				}
			}
			part.setResourceIds(userNames);
		}
	}

	@Override
	public ActivityData[] buildActivityData(StartProcessBean startBean) {
		QuoteProcessStartBean quoteStartBean = accept(startBean);
		
		// 构建流程数据
		ActivityData[] datas = new ActivityData[]{
				new ActivityData("buType", quoteStartBean.getBuType()),				
				new ActivityData("quoteId",quoteStartBean.getQuoteId().toString())};
		
		return datas;
	}
	
	@Override
	protected CreateProcessRequest createBpmRequest(StartProcessBean startBean) {
		QuoteProcessStartBean quoteStartBean = accept(startBean);

		CreateProcessRequest request = new CreateProcessRequest();

		// 设置流程启动的packageId,processDefId,processName
		String packageId = quoteStartBean.getPackageId();
		String processDefineId = quoteStartBean.getProcessDefineId();
		String startUserName = quoteStartBean.getStartUser();
		
		request.setPackageId(packageId);
		request.setCreateUserName(startUserName);
		request.setProcessDefineId(processDefineId);
		
		request.setProcessName(quoteStartBean.getProcessName());

		if (quoteStartBean.getQuoteId() != null) {
			request.setForeignKey(quoteStartBean.getQuoteId().toString());
		}
		return request;
	}
	
	private QuoteProcessStartBean accept(StartProcessBean startBean) {
		if (startBean instanceof QuoteProcessStartBean) {
			return (QuoteProcessStartBean) startBean;
		}
		throw new RuntimeException("发起流程失败,startBean:" + startBean);
	}
	
	@Override
	public ActivityData[] buildCompleteActivityData(CompleteActivityReq request) {
		CompleteQuoteActivityReq quoteMainReq = accept(request);
		int approved = quoteMainReq.getApproved();
		String reason = ProcessHelper.encodeApproveReason(quoteMainReq.getReason());
		if (approved == 1) {
			ActivityData activityData = new ActivityData("BPM_AGREE_REASON", reason);
			return new ActivityData[]{activityData};
		} else {
			return new ActivityData[]{new ActivityData("BPM_REJECT_REASON", reason),
					new ActivityData("BPM_REJECT_TARGET", "act1")};
		}
	}
	
	private CompleteQuoteActivityReq accept(CompleteActivityReq req) {
		if (req instanceof CompleteQuoteActivityReq) {
			return (CompleteQuoteActivityReq) req;
		}
		throw new RuntimeException("完成任务失败, CompleteActivityReq:" + req);
	}
}
