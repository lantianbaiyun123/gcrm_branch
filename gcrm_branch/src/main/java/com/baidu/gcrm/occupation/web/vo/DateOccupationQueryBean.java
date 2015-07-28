package com.baidu.gcrm.occupation.web.vo;

import com.baidu.gcrm.occupation.helper.DatePeriod;

public class DateOccupationQueryBean {
	private DatePeriod period;
	
	private Long positionId;

	public DatePeriod getPeriod() {
		return period;
	}

	public void setPeriod(DatePeriod period) {
		this.period = period;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	
}
