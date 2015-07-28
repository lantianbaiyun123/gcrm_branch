package com.baidu.gcrm.bpm.web.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gwfp.ws.dto.ActivityContentResponse;

public class ProcessHelper {
	private static String CRM_TAG = "gcrm_tag";
	
	public static String encodeApproveReason(String reason) {
		return CRM_TAG + reason + CRM_TAG;
	}
	
	public static String decodeApproveReason(String reason) {
		String[] realReason = StringUtils.substringsBetween(reason, CRM_TAG, CRM_TAG);
		if (ArrayUtils.isEmpty(realReason)) {
			return reason;
		}
		return realReason[0];
	}
	
	public static CompleteActivityResponse convertActivityContentResponseToActivities(ActivityContentResponse[] response) {
		CompleteActivityResponse completeResponse = new CompleteActivityResponse();
		List<Activity> activities = new ArrayList<Activity>();
		if (ArrayUtils.isEmpty(response)) {
			completeResponse.setActivities(activities);
			return completeResponse;
		}
		for (ActivityContentResponse activityContent : response) {
			Activity activity = new Activity();
			activity.setActDefId(activityContent.getActivityDefineId());
			activity.setActivityId(activityContent.getActivityId());
			activity.setActivityName(activityContent.getActivityName());
			activity.setPerformer(activityContent.getPerformName());
			activities.add(activity);
			if (activityContent.isProcessFinish()) {
				completeResponse.setProcessFinish(true);
			}
		}
		completeResponse.setActivities(activities);
		completeResponse.setProcessId(response[0].getProcessId());
		return completeResponse;
	}
}
