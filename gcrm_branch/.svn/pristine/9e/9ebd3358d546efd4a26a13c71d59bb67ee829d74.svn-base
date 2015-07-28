package com.baidu.gcrm.amp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_subscribe")
public class Subscribe implements java.io.Serializable {
	

	private static final long serialVersionUID = 5830960446149927491L;

	public enum SubscribeStatus {
		DISABLE,
		ENABLE;
	}
	
	public enum SubscribeType {
		OFFER_EXPIRE,
		DAILY_AD;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String email;
	
	@Column
	private String country;
	
	@Column(name="offer_type")
	private String offerType;
	
	@Column(name="sub_type")
	private String subType;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private SubscribeStatus status = SubscribeStatus.ENABLE;
	
	@Column(name="create_date")
	private Date createDate = new Date();
	
	@Column(name="create_user_id")
	private Long createUserId;
	
	@Column(name="last_update_date")
	private Date lastUpdateDate = new Date();
	
	@Column(name="last_update_user_id")
	private Long lastUpdateUserId;
	
	@Column
	private String remark;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SubscribeStatus getStatus() {
		return status;
	}

	public void setStatus(SubscribeStatus status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Long lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

}

