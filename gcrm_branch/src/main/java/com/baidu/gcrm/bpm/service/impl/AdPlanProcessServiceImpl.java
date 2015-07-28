package com.baidu.gcrm.bpm.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.AbstractStartProcessService;
import com.baidu.gcrm.bpm.web.helper.AdPlanProcessStartBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.CompleteAdPlanActivityReq;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.ProcessHelper;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.CreateProcessRequest;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
import com.baidu.gwfp.ws.dto.ProcessContext;

@Service("adPlanProcessServiceImpl")
public class AdPlanProcessServiceImpl extends AbstractStartProcessService {
	@Value("#{appproperties['adplan.activityData.lessThanEighty']}")
	private String lessThanEighty;
	@Value("#{appproperties['adplan.activityData.lessThanFifty']}")
	private String lessThanFifty;
	@Value("#{appproperties['adplan.activityData.withoutRecord']}")
	private String priceWithoutRecord;
	@Value("#{appproperties['adplan.activityData.insertOrder']}")
	private String insertOrder;
	@Value("#{appproperties['adplan.activityData.acturalRatioLessThanMine']}")
	private String acturalRatioLessThanMine;
	@Value("#{appproperties['adplan.activityData.needSuperiorApproval']}")
	private String needSuperiorApproval;
	@Autowired
	IUserRightsService userRightsService;
	
	@Override
	protected ActivityData[] buildPrepareActivityData(StartProcessBean startBean) {
		return new ActivityData[]{};
	}
	
	@Override
	protected void fillParticipant(StartProcessBean startBean, ProcessContext context) {
		AdPlanProcessStartBean adPlanStartBean = accept(startBean);
		List<ParticipantBean> participants = startBean.getParticipants();
		
		String startUser = "startUser";
		
		ParticipantInfo[] parts = context.getParticipantInfos();
		for (ParticipantInfo part : parts) {
			String participantId = part.getParticipantId();
			if(startUser.equals(participantId)){
				part.setResourceIds(new String[]{adPlanStartBean.getStartUser()});
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
		AdPlanProcessStartBean adPlanStartBean = accept(startBean);
		String adSolutionId = adPlanStartBean.getAdPlanId().toString();
		String foreignKey = adPlanStartBean.getForeignKey();
		if (!StringUtils.isEmpty(foreignKey)) {
			adSolutionId = foreignKey;
		}
		
		// 构建流程数据
		ActivityData[] datas = new ActivityData[]{
				new ActivityData(lessThanEighty, adPlanStartBean.getIsLessThanEightyPercent()),
				new ActivityData(lessThanFifty, adPlanStartBean.getIsLessThanFiftyPercent()),
				new ActivityData(priceWithoutRecord, adPlanStartBean.getIsWithoutRecord()),
				new ActivityData(acturalRatioLessThanMine, adPlanStartBean.getIsActualRatioLessThanMine()),
				new ActivityData(insertOrder, adPlanStartBean.getIsInsertOrder()),
				new ActivityData(needSuperiorApproval, adPlanStartBean.getNeedSuperiorApproval()),
				new ActivityData("adSolutionId", adSolutionId)};

		return datas;
	}
	
	@Override
	protected CreateProcessRequest createBpmRequest(StartProcessBean startBean) {
		AdPlanProcessStartBean adPlanStartBean = accept(startBean);

		CreateProcessRequest request = new CreateProcessRequest();

		// 设置流程启动的packageId,processDefId,processName
		String packageId = adPlanStartBean.getPackageId();
		String processDefineId = adPlanStartBean.getProcessDefineId();
		String startUserName = adPlanStartBean.getStartUser();
		
		request.setPackageId(packageId);
		request.setCreateUserName(startUserName);
		request.setProcessDefineId(processDefineId);
		
		request.setProcessName(adPlanStartBean.getProcessName());

		if (StringUtils.isEmpty(startBean.getForeignKey()) && adPlanStartBean.getAdPlanId() != null) {
			request.setForeignKey(adPlanStartBean.getAdPlanId().toString());
		} else {
			request.setForeignKey(startBean.getForeignKey());
		}
		return request;
	}
	
	private AdPlanProcessStartBean accept(StartProcessBean startBean) {
		if (startBean instanceof AdPlanProcessStartBean) {
			return (AdPlanProcessStartBean) startBean;
		}
		throw new RuntimeException("发起流程失败,startBean:" + startBean);
	}
	
	@Override
	public ActivityData[] buildCompleteActivityData(CompleteActivityReq request) {
		CompleteAdPlanActivityReq adPlanReq = accept(request);
		int approved = adPlanReq.getApproved();
		String reason = ProcessHelper.encodeApproveReason(adPlanReq.getReason());
		if (approved == 1) {
			ActivityData activityData = new ActivityData("BPM_AGREE_REASON", reason);
			return new ActivityData[]{activityData};
		} else {
			return new ActivityData[]{new ActivityData("BPM_REJECT_REASON", reason),
					new ActivityData("BPM_REJECT_TARGET", "act1")};
		}
	}
	
	private CompleteAdPlanActivityReq accept(CompleteActivityReq req) {
		if (req instanceof CompleteAdPlanActivityReq) {
			return (CompleteAdPlanActivityReq) req;
		}
		throw new RuntimeException("完成任务失败, CompleteActivityReq:" + req);
	}
}
