package com.baidu.gcrm.bpm.vo;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.bpm.web.helper.Activity;

public class StartProcessResponse {
	private List<Activity> activities;
	
	private String processId;
	
	private String firstActivityId;
	
	private String actDefId;
	
	private Date processStartTime;
	
	private String startUser;

	public StartProcessResponse() {
	}
	

	public StartProcessResponse(List<Activity> activities, String processId, String firstActivityId, String actDefId) {
		super();
		this.activities = activities;
		this.processId = processId;
		this.firstActivityId = firstActivityId;
		this.actDefId = actDefId;
	}



	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getFirstActivityId() {
		return firstActivityId;
	}

	public void setFirstActivityId(String firstActivityId) {
		this.firstActivityId = firstActivityId;
	}


	public String getActDefId() {
		return actDefId;
	}


	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}


	public Date getProcessStartTime() {
		return processStartTime;
	}


	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}


	public String getStartUser() {
		return startUser;
	}


	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}
	
	
}
