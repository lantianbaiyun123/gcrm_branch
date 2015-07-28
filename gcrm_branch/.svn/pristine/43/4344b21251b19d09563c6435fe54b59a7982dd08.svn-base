package com.baidu.gcrm.occupation.service.bean;

import java.util.Date;

public class RotationPositionCountBean {
	private Long positionId;
	
	private Date date;
	
	private int reservedCount = 0;
	
	private int occupiedCount = 0;
	
	private int busyCount;
	
	public RotationPositionCountBean() {
	}

	public RotationPositionCountBean(Long positionId, Date date) {
		super();
		this.positionId = positionId;
		this.date = date;
	}


	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getReservedCount() {
		return reservedCount;
	}

	public void setReservedCount(int reservedCount) {
		this.reservedCount = reservedCount;
	}

	public int getOccupiedCount() {
		return occupiedCount;
	}

	public void setOccupiedCount(int occupiedCount) {
		this.occupiedCount = occupiedCount;
	}

	public int getBusyCount() {
		return this.reservedCount + this.occupiedCount;
	}

	public void setBusyCount(int busyCount) {
		this.busyCount = busyCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((positionId == null) ? 0 : positionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RotationPositionCountBean other = (RotationPositionCountBean) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (positionId == null) {
			if (other.positionId != null)
				return false;
		} else if (!positionId.equals(other.positionId))
			return false;
		return true;
	}
	
}
