package com.baidu.gcrm.amp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

@Entity
@Table(name = "g_country")
public class Country extends BaseI18nModel implements java.io.Serializable{

	private static final long serialVersionUID = -2461990465249883198L;
	
	public enum CountryStatus{
		DISABLE,
		ENABLE;
	}
	
	@Id
	@Column(name="cid")
	private Long id;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private CountryStatus status = CountryStatus.ENABLE;
	
	@Column(name="time_zone",nullable=false)
	private Integer timeZone;
	
	@Column(name="phone_code")
	private Integer phoneCode;
	
	@Column(name="opt_user_id",nullable=false)
	private Long optUserId;
	
	@Column(name="opt_date",nullable=false)
	private Date optDate = new Date();
	
	@Column
	private String remark;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CountryStatus getStatus() {
		return status;
	}

	public void setStatus(CountryStatus status) {
		this.status = status;
	}

	public Integer getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Integer timeZone) {
		this.timeZone = timeZone;
	}

	public Integer getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(Integer phoneCode) {
		this.phoneCode = phoneCode;
	}

	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(Long optUserId) {
		this.optUserId = optUserId;
	}

}
