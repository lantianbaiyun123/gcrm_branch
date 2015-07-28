package com.baidu.gcrm.schedule1.web.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;

@Entity
public class ScheduleListVO implements Serializable {

	private static final long serialVersionUID = -2576260128905263579L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 广告投放时间段
	 */
	@Column(name = "period_description")
	@Size(max = 2000)
	private String periodDescription;
	
	/**
	 * 排期单状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private ScheduleStatus status;
	
	/**
     * 广告主
     */
	@Column(name = "advertisers")
	private String advertisers;
	/**
	 * 广告内容
	 */
	@Column(name = "description")
	private String description;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "ad_content_id")
	private Long adContentId;
	
	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
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

	public String getPeriodDescription() {
		if(StringUtils.isNotBlank(periodDescription)
				&& periodDescription.contains(",")){
			periodDescription = periodDescription.replace(",", "~");
			
			if(periodDescription.endsWith(";")){
				periodDescription = periodDescription.substring(0,periodDescription.length()-1);
			}
		}
		return periodDescription;
	}

	public void setPeriodDescription(String periodDescription) {
		this.periodDescription = periodDescription;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
}
