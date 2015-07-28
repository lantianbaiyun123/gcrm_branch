package com.baidu.gcrm.bpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.bpm.service.AbstractStartProcessService;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.web.helper.BaseStartProcessBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.CompleteBaseActivityReq;
import com.baidu.gcrm.bpm.web.helper.ParticipantBean;
import com.baidu.gcrm.bpm.web.helper.ProcessHelper;
import com.baidu.gcrm.bpm.web.helper.StartProcessBean;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.CreateProcessRequest;
import com.baidu.gwfp.ws.dto.ParticipantInfo;
import com.baidu.gwfp.ws.dto.ProcessContext;

@Service("baseProcessServiceImpl")
public class BaseProcessServiceImpl extends AbstractStartProcessService {
    @Autowired
    IUserRightsService userRightsService;

    @Resource(name = "defaultAssignmentHandler")
    IAssignmentHandler defaultAssignmentHandler;

    @Override
    protected ActivityData[] buildPrepareActivityData(StartProcessBean startBean) {
        return new ActivityData[] {};
    }

    @Override
    protected void fillParticipant(StartProcessBean startBean, ProcessContext context) {
        BaseStartProcessBean baseStartBean = accept(startBean);
        List<ParticipantBean> participants = startBean.getParticipants();

        String performerName = baseStartBean.getStartUser();

        ParticipantInfo[] parts = context.getParticipantInfos();
        for (ParticipantInfo part : parts) {
            String participantId = part.getParticipantId();

            String[] userNames;

            if (participants != null && participants.contains(new ParticipantBean(participantId))) {
                ParticipantBean participant = participants
                        .get(participants.indexOf(new ParticipantBean(participantId)));
                List<String> names = participant.getUsernames();
                userNames = names.toArray(new String[names.size()]);
                part.setResourceIds(userNames);
            } else if (baseStartBean.getAssignmentHandler() != null) {
                IAssignmentHandler assignmentHandler = baseStartBean.getAssignmentHandlerByParicipantId(participantId);
                if (assignmentHandler == null) {
                    defaultAssignmentHandler.assign(part, performerName, baseStartBean.getCustomParams());
                } else {
                    assignmentHandler.assign(part, performerName, baseStartBean.getCustomParams());
                }
            } else {
                defaultAssignmentHandler.assign(part, performerName, baseStartBean.getCustomParams());
            }
        }
    }

    /**
     * 构建ActivityData数据
     */
    @Override
    public ActivityData[] buildActivityData(StartProcessBean startBean) {
        BaseStartProcessBean baseStartBean = accept(startBean);
        Map<String, Object> activityDataParams = baseStartBean.getActivityDataMap();
        ActivityData[] datas;
        if (activityDataParams == null) {
            return new ActivityData[] {};
        }
        Set<String> keysSet = activityDataParams.keySet();
        int keyAmount = keysSet.size();
        datas = new ActivityData[keyAmount];
        boolean isBindActivityId = baseStartBean.isBindActivity();
        int i = 0;
        // 构建流程数据
        if (isBindActivityId) {
            String activityId = baseStartBean.getBindActivityId();
            for (String key : keysSet) {
                datas[i] = new ActivityData(key, activityDataParams.get(key).toString(), activityId);
                i++;
            }
        } else {
            for (String key : keysSet) {
                datas[i] = new ActivityData(key, activityDataParams.get(key).toString());
                i++;
            }
        }
        return datas;
    }

    @Override
    protected CreateProcessRequest createBpmRequest(StartProcessBean startBean) {
        BaseStartProcessBean baseStartBean = accept(startBean);

        CreateProcessRequest request = new CreateProcessRequest();

        // 设置流程启动的packageId,processDefId,processName
        String packageId = baseStartBean.getPackageId();
        String processDefineId = baseStartBean.getProcessDefineId();
        String startUserName = baseStartBean.getStartUser();

        request.setPackageId(packageId);
        request.setCreateUserName(startUserName);
        request.setProcessDefineId(processDefineId);

        request.setProcessName(baseStartBean.getProcessName());
        request.setForeignKey(baseStartBean.getForeignKey());

        return request;
    }

    private BaseStartProcessBean accept(StartProcessBean startBean) {
        if (startBean instanceof BaseStartProcessBean) {
            return (BaseStartProcessBean) startBean;
        }
        throw new RuntimeException("发起流程失败,startBean:" + startBean);
    }

    @Override
    public ActivityData[] buildCompleteActivityData(CompleteActivityReq request) {

        CompleteBaseActivityReq baseRequest = (CompleteBaseActivityReq) request;

        CompleteBaseActivityReq baseMainReq = accept(request);
        int approved = baseMainReq.getApproved();
        String reason = ProcessHelper.encodeApproveReason(baseMainReq.getReason());
        if (approved == 1) {
            List<ActivityData> activityDataList = null;
            ActivityData activityData = new ActivityData("BPM_AGREE_REASON", reason);

            if (baseRequest.getActivityDataList() == null) {
                activityDataList = new ArrayList<ActivityData>();
            } else {
                activityDataList = baseRequest.getActivityDataList();
            }
            activityDataList.add(activityData);
            return activityDataList.toArray(new ActivityData[activityDataList.size()]);
        } else {
            return new ActivityData[] { new ActivityData("BPM_REJECT_REASON", reason),
                    new ActivityData("BPM_REJECT_TARGET", "act1") };
        }
    }

    private CompleteBaseActivityReq accept(CompleteActivityReq req) {
        if (req instanceof CompleteBaseActivityReq) {
            return (CompleteBaseActivityReq) req;
        }
        throw new RuntimeException("完成任务失败, CompleteActivityReq:" + req);
    }
}
