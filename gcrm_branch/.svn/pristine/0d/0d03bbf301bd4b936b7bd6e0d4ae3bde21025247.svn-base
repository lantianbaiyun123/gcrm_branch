package com.baidu.gcrm.amp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.customer.model.Customer;

@Entity
@Table(name = "g_offer")
public class Offer implements java.io.Serializable {
	
	private static final long serialVersionUID = -2714099419478522441L;

	public enum OfferStatus {
		DISABLE,
		ENABLE;
	}
	
	public enum OfferQueryIdType {
		OID,
		CID;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="offer_id")
	private String offerId;
	
	@ManyToOne
	@JoinColumn(name="customer_id",nullable=false)
	private Customer customer;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column
	private String country;
	
	@Column
	private Integer type;
	
	@Column(name="position_id")
	private Long positionId;
	
	@Column
	private String content;
	
	@Column(name="offer_url")
	private String offerUrl;
	
	@Column(name="opt_date")
	private Date optDate = new Date();
	
	@Column(name="opt_user_id",nullable=false)
	private Long optUserId;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private OfferStatus status = OfferStatus.ENABLE;
	
	@Column
	private String remark;
	
	@Transient
	private String queryIdType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOfferUrl() {
		return offerUrl;
	}

	public void setOfferUrl(String offerUrl) {
		this.offerUrl = offerUrl;
	}

	public Date getOptDate() {
		return optDate;
	}

	public void setOptDate(Date optDate) {
		this.optDate = optDate;
	}


	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
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

	public String getQueryIdType() {
		return queryIdType;
	}

	public void setQueryIdType(String queryIdType) {
		this.queryIdType = queryIdType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}



}

