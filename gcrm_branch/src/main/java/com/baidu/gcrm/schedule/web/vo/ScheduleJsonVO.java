package com.baidu.gcrm.schedule.web.vo;

import java.util.List;
import java.util.Map;

public class ScheduleJsonVO {
    private int occupyStartIndex;
    
    private int occupyEndIndex;
    
    private int insertStartIndex;
    
    private int insertEndIndex;
    
    private Map<String,ScheduleDateVO> allDates;
    
    private List<ScheduleInfoVO> scheduleList;

    public Map<String,ScheduleDateVO> getAllDates() {
        return allDates;
    }

    public void setAllDates(Map<String,ScheduleDateVO> allDates) {
        this.allDates = allDates;
    }

    public List<ScheduleInfoVO> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleInfoVO> scheduleList) {
        this.scheduleList = scheduleList;
    }

	public int getOccupyStartIndex() {
		return occupyStartIndex;
	}

	public void setOccupyStartIndex(int occupyStartIndex) {
		this.occupyStartIndex = occupyStartIndex;
	}

	public int getOccupyEndIndex() {
		return occupyEndIndex;
	}

	public void setOccupyEndIndex(int occupyEndIndex) {
		this.occupyEndIndex = occupyEndIndex;
	}

	public int getInsertStartIndex() {
		return insertStartIndex;
	}

	public void setInsertStartIndex(int insertStartIndex) {
		this.insertStartIndex = insertStartIndex;
	}

	public int getInsertEndIndex() {
		return insertEndIndex;
	}

	public void setInsertEndIndex(int insertEndIndex) {
		this.insertEndIndex = insertEndIndex;
	}

}
