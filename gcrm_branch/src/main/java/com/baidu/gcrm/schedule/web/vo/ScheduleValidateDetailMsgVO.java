package com.baidu.gcrm.schedule.web.vo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ScheduleValidateDetailMsgVO {
    
    private ValidateType type;
    private String advertiser;
    private Long adContentId;
    private int oldAmount;
    private int newAmount;
    private Date conflictDate;
    private List<String> conflictAdvertiser;
    private Collection<Long> conflictAdContents;
    private int curIndex;
    private String conflictPeriods;
    private String contractPeriods;
    
    public enum ValidateType {
        conflict,
        change,
        overdue
    }
    
    public String getAdvertiser() {
        return advertiser;
    }
    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }
    public int getOldAmount() {
        return oldAmount;
    }
    public void setOldAmount(int oldAmount) {
        this.oldAmount = oldAmount;
    }
    public int getNewAmount() {
        return newAmount;
    }
    public void setNewAmount(int newAmount) {
        this.newAmount = newAmount;
    }
    public Date getConflictDate() {
        return conflictDate;
    }
    public void setConflictDate(Date conflictDate) {
        this.conflictDate = conflictDate;
    }
    public List<String> getConflictAdvertiser() {
        return conflictAdvertiser;
    }
    public void setConflictAdvertiser(List<String> conflictAdvertiser) {
        this.conflictAdvertiser = conflictAdvertiser;
    }
    public ValidateType getType() {
        return type;
    }
    public void setType(ValidateType type) {
        this.type = type;
    }
    public Long getAdContentId() {
        return adContentId;
    }
    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }
    public Collection<Long> getConflictAdContents() {
        return conflictAdContents;
    }
    public void setConflictAdContents(Collection<Long> conflictAdContents) {
        this.conflictAdContents = conflictAdContents;
    }
	public int getCurIndex() {
		return curIndex;
	}
	public void setCurIndex(int curIndex) {
		this.curIndex = curIndex;
	}
	public String getConflictPeriods() {
		return conflictPeriods;
	}
	public void setConflictPeriods(String conflictPeriods) {
		this.conflictPeriods = conflictPeriods;
	}
	public String getContractPeriods() {
		return contractPeriods;
	}
	public void setContractPeriods(String contractPeriods) {
		this.contractPeriods = contractPeriods;
	}
    
}
