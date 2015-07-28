package com.baidu.gcrm.opportunity.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "g_opportunity_platform")
public class OpportunityPlatform  implements java.io.Serializable {

	private static final long serialVersionUID = 8376943703707460996L;
	
	@Id
	@GeneratedValue
	private Long id;	
	
	@Column(name="opportunity_id")
	private Long opportunityId;	

	@Column(name="platform_id")
	private Long platformId;

	public Long getId() {
		return id;
	}

	public Long getOpportunityId() {
		return opportunityId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}


}
