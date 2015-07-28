package com.baidu.gcrm.schedule.web.vo;

import java.util.List;

public class ScheduleValidateVO {
    
    private ValidateStatus state;
    public enum ValidateStatus {
        success,
        suspend,
        warning
    }
    
    private List<ScheduleValidateDetailMsgVO> validateMsg;
    private List<ScheduleSubmitMsgVO> submitMsg;
    private String currentAdvertiser;
    
    public ValidateStatus getState() {
        return state;
    }
    public void setState(ValidateStatus state) {
        this.state = state;
    }
    public List<ScheduleValidateDetailMsgVO> getValidateMsg() {
        return validateMsg;
    }
    public void setValidateMsg(List<ScheduleValidateDetailMsgVO> validateMsg) {
        this.validateMsg = validateMsg;
    }
    public List<ScheduleSubmitMsgVO> getSubmitMsg() {
        return submitMsg;
    }
    public void setSubmitMsg(List<ScheduleSubmitMsgVO> submitMsg) {
        this.submitMsg = submitMsg;
    }
	public String getCurrentAdvertiser() {
		return currentAdvertiser;
	}
	public void setCurrentAdvertiser(String currentAdvertiser) {
		this.currentAdvertiser = currentAdvertiser;
	}
    
}
