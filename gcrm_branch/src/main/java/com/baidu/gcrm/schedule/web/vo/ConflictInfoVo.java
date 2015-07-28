package com.baidu.gcrm.schedule.web.vo;

import java.util.Date;
import java.util.List;

public class ConflictInfoVo {
	private Date date;
	
	private List<Long> contentIds;
	
	public ConflictInfoVo() {
	}

	public ConflictInfoVo(Date date, List<Long> contentIds) {
		super();
		this.date = date;
		this.contentIds = contentIds;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Long> getContentIds() {
		return contentIds;
	}

	public void setContentIds(List<Long> contentIds) {
		this.contentIds = contentIds;
	}
	
}
