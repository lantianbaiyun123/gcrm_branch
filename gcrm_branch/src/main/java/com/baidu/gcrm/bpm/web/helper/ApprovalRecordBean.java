package com.baidu.gcrm.bpm.web.helper;


/**
 * 审批记录，即操作记录
 * 
 */
public class ApprovalRecordBean {
	/** 任务id */
	private String activityId;

	/** 任务名称 */
	private String activityName;

	/** 执行人 */
	private String performer;

	/** 开始时间 */
	private String startTime;

	/** 结束时间 */
	private String endTime;

	/** 备注 */
	private String remark;
	
	private boolean approved;
	
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

}
