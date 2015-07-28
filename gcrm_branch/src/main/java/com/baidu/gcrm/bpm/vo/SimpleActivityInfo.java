package com.baidu.gcrm.bpm.vo;

public class SimpleActivityInfo {
	private String activityName;

	private String performer;

	public SimpleActivityInfo() {
		super();
	}

	public SimpleActivityInfo(String activityName, String performer) {
		super();
		this.activityName = activityName;
		this.performer = performer;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}
}
