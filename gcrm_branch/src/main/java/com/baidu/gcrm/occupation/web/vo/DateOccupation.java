package com.baidu.gcrm.occupation.web.vo;

import java.util.Date;

public class DateOccupation {
	private Date date;
	
	private String status;
	
	private int busyCount;
	
	private int biddingCount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getBusyCount() {
		return busyCount;
	}

	public void setBusyCount(int busyCount) {
		this.busyCount = busyCount;
	}

	public int getBiddingCount() {
		return biddingCount;
	}

	public void setBiddingCount(int biddingCount) {
		this.biddingCount = biddingCount;
	}
	
}
