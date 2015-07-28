package com.baidu.gcrm.occupation.helper;

import java.util.Date;

public class DatePeriod {
	private Date from;
	
	private Date to;
	
	public DatePeriod() {
	}

	public DatePeriod(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "DatePeriod [from=" + from + ", to=" + to + "]";
	}
}
