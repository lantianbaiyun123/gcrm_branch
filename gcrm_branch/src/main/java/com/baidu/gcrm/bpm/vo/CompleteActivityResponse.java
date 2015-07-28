package com.baidu.gcrm.bpm.vo;

import java.util.List;

import com.baidu.gcrm.bpm.web.helper.Activity;

public class CompleteActivityResponse {
	
	private boolean processFinish = false;
	
	private String processId;
	
	/**
	 * 如果流程结束，该列表为空
	 */
	private List<Activity> activities;

	public boolean isProcessFinish() {
		return processFinish;
	}

	public void setProcessFinish(boolean processFinish) {
		this.processFinish = processFinish;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
}
