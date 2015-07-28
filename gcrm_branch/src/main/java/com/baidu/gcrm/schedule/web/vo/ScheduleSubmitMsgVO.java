package com.baidu.gcrm.schedule.web.vo;

import java.util.List;

public class ScheduleSubmitMsgVO {
    
    private Long adContentId;
    private String adContentNumber;
    private String advertiser;
    private Integer originalDays;
    private Integer nowDays;
    private String originalOccupy;
    private String nowOccupy;
    private String originalInsertOccupy;
    private String nowInsertOccupy;
    private boolean isAdd;
    private List<Long> oldOccupyIds;
    private List<Long> oldInsertIds;
    
    public Long getAdContentId() {
        return adContentId;
    }
    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }
    public String getAdContentNumber() {
        return adContentNumber;
    }
    public void setAdContentNumber(String adContentNumber) {
        this.adContentNumber = adContentNumber;
    }
    public String getAdvertiser() {
        return advertiser;
    }
    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }
    public Integer getOriginalDays() {
        return originalDays;
    }
    public void setOriginalDays(Integer originalDays) {
        this.originalDays = originalDays;
    }
    public Integer getNowDays() {
        return nowDays;
    }
    public void setNowDays(Integer nowDays) {
        this.nowDays = nowDays;
    }
    public boolean isAdd() {
        return isAdd;
    }
    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }
    public String getOriginalOccupy() {
        return originalOccupy;
    }
    public void setOriginalOccupy(String originalOccupy) {
        this.originalOccupy = originalOccupy;
    }
    public String getNowOccupy() {
        return nowOccupy;
    }
    public void setNowOccupy(String nowOccupy) {
        this.nowOccupy = nowOccupy;
    }
    public String getOriginalInsertOccupy() {
        return originalInsertOccupy;
    }
    public void setOriginalInsertOccupy(String originalInsertOccupy) {
        this.originalInsertOccupy = originalInsertOccupy;
    }
    public String getNowInsertOccupy() {
        return nowInsertOccupy;
    }
    public void setNowInsertOccupy(String nowInsertOccupy) {
        this.nowInsertOccupy = nowInsertOccupy;
    }
	public List<Long> getOldOccupyIds() {
		return oldOccupyIds;
	}
	public void setOldOccupyIds(List<Long> oldOccupyIds) {
		this.oldOccupyIds = oldOccupyIds;
	}
	public List<Long> getOldInsertIds() {
		return oldInsertIds;
	}
	public void setOldInsertIds(List<Long> oldInsertIds) {
		this.oldInsertIds = oldInsertIds;
	}
    
    
}
