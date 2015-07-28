package com.baidu.gcrm.schedule.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;


@Entity
@Table(name = "g_schedule")
public class Schedule implements Serializable {

	private static final long serialVersionUID = -2576260128905263579L;
	
	/**
	 * 排期单状态
	 *
	 */
	public enum ScheduleStatus {
		/** 预定 */
		reserved,
		/** 确认 */
		confirmed,
		/** 锁定 */
		locked,
		/** 释放 */
		released;
		
		public static ScheduleStatus valueOf(Integer value){
			if(value == null){
				return null;
			}
			ScheduleStatus[] values = ScheduleStatus.values(); 
			for(ScheduleStatus status : values){
				if(status.ordinal() == value){
					return status;
				}
			}
			return null;
		}
		
		public boolean isReserved() {
			return this.equals(reserved);
		}
		
		public boolean isConfirmed() {
			return this.equals(confirmed);
		}
		
		public boolean isLocked() {
			return this.equals(locked);
		}
		
		public boolean isReleased() {
			return this.equals(released);
		}
	}

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 排期单编号，8位随机数字
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 广告内容id
	 */
	@Column(name = "ad_content_id")
	private Long adContentId;
	
	/**
	 * 位置id，对应唯一的位置
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 计费方式id
	 */
	@Column(name = "billing_model_id")
	private Long billingModelId;
	
	/**
	 * 投放时间段
	 */
	@Column(name = "occupy_period")
	@Size(max = 6000)
	private String occupyPeriod;
	
	/**
	 * 插单投放时间段
	 */
	@Column(name = "insert_period")
	@Size(max = 6000)
	private String insertPeriod;
	
	/**
	 * 广告投放时间段
	 */
	@Column(name = "period_description")
	@Size(max = 2000)
	private String periodDescription;
	
	/**
	 * 广告插单时间
	 */
	@Column(name = "insert_period_description")
	@Size(max = 2000)
	private String insertPeriodDescription;
	
	/**
	 * 排期单状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private ScheduleStatus status;
	
	@Column(name="completed")
	private Integer completed;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="confirm_time")
	private Date confirmTime;
	
	@Column(name="lock_time")
	private Date lockTime;
	
	@Column(name="release_time")
	private Date releaseTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	
	@Column(name="confirm_operator")
	private Long confirmOperator;
	
	/**
     * 广告主
     */
	@Transient
	private String advertisers;
	/**
	 * 广告内容
	 */
	@Transient
	private String description;
	
	@Transient
	private String statusName;
	
	public Long getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public Long getConfirmOperator() {
		return confirmOperator;
	}

	public void setConfirmOperator(Long confirmOperator) {
		this.confirmOperator = confirmOperator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getBillingModelId() {
		return billingModelId;
	}

	public void setBillingModelId(Long billingModelId) {
		this.billingModelId = billingModelId;
	}

	public String getOccupyPeriod() {
		return occupyPeriod;
	}

	public void setOccupyPeriod(String occupyPeriod) {
		this.occupyPeriod = occupyPeriod;
	}

	public String getInsertPeriod() {
		return insertPeriod;
	}

	public void setInsertPeriod(String insertPeriod) {
		this.insertPeriod = insertPeriod;
	}

	public String getPeriodDescription() {
		return periodDescription;
	}

	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
	}

	public String getInsertPeriodDescription() {
		return insertPeriodDescription;
	}

	public void setInsertPeriodDescription(String insertPeriodDescription) {
		this.insertPeriodDescription = insertPeriodDescription;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public Integer getCompleted() {
		return completed;
	}
	
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getAdvertisers() {
		return advertisers;
	}

	public void setAdvertisers(String advertisers) {
		this.advertisers = advertisers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
