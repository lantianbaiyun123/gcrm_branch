package com.baidu.gcrm.occupation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 广告位置投放表，一个位置在每一个日期的占用情况
 * @author anhuan
 *
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=false)
@Table(name = "g_position_occupation")
public class PositionOccupation implements Serializable {
	
	private static final long serialVersionUID = -5746122527153455276L;
	
	public enum OccupationStatus {
		/* 禁用 */
		DISABLED,
		/* 启用 */
		ENABLED;
		public boolean isEnabled() {
			return this.equals(ENABLED);
		}
	}

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 位置id，对应唯一的位置
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 日期，格式yyyy-MM-dd
	 */
	@Column(name = "date")
	private Date date;
	
	/**
	 * 可售卖总数，对于轮播位>1，固定位=1
	 */
	@Column(name = "total_amount")
	private int totalAmount;
	
	/**
	 * 已占用（确认或锁定）数，<=totalAmount
	 */
	@Column(name = "sold_amount")
	private int soldAmount;
	
	/**
	 * 位置在当前日期是否可用，0表示不可用，1表示可用
	 */
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private OccupationStatus status;
	
	/**
	 * 当前对应的排期单号，排期单是确认或锁定状态时，排期单号记录才会记录在对应的投放时间表中
	 */
	@Column(name = "cur_schedule_number")
	private String curScheduleNumber;
	
	/**
	 * 历史所有的占用该位置（或被插单）的广告内容id，以“,”分割，按照时间顺序排列
	 */
	@Column(name = "history_schedules")
	@Size(max = 128)
	private String historySchedules;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSoldAmount() {
		return soldAmount;
	}

	public void setSoldAmount(int soldAmount) {
		this.soldAmount = soldAmount;
	}

	public OccupationStatus getStatus() {
		return status;
	}

	public void setStatus(OccupationStatus status) {
		this.status = status;
	}

	public String getCurScheduleNumber() {
		return curScheduleNumber;
	}

	public void setCurScheduleNumber(String curScheduleNumber) {
		this.curScheduleNumber = curScheduleNumber;
	}

	public String getHistorySchedules() {
		return historySchedules;
	}

	public void setHistorySchedules(String historySchedules) {
		this.historySchedules = historySchedules;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PositionOccupation other = (PositionOccupation) obj;
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

    @Override
    public String toString() {
        return "PositionOccupation [id=" + id + ", positionId=" + positionId + ", date=" + date + ", soldAmount="
                + soldAmount + "]";
    }

}
