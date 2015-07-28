package com.baidu.gcrm.qualification.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="g_customer_resource")
public class CustomerResource implements java.io.Serializable{

	private static final long serialVersionUID = -3390535669395793569L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="agent_qualification_id")
	private Long agentQualificationId;
	
	private String industry;
	
	@Column(name="advertisers_company1")
	private String advertisersCompany1;
	
	@Column(name="advertisers_company2")
	private String advertisersCompany2;
	
	@Column(name="advertisers_company3")
	private String advertisersCompany3;
	
	@Transient
	private String advertisersCompany;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAgentQualificationId() {
		return agentQualificationId;
	}
	public void setAgentQualificationId(Long agentQualificationId) {
		this.agentQualificationId = agentQualificationId;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAdvertisersCompany1() {
		return advertisersCompany1;
	}
	public void setAdvertisersCompany1(String advertisersCompany1) {
		this.advertisersCompany1 = advertisersCompany1;
	}
	public String getAdvertisersCompany2() {
		return advertisersCompany2;
	}
	public void setAdvertisersCompany2(String advertisersCompany2) {
		this.advertisersCompany2 = advertisersCompany2;
	}
	public String getAdvertisersCompany3() {
		return advertisersCompany3;
	}
	public void setAdvertisersCompany3(String advertisersCompany3) {
		this.advertisersCompany3 = advertisersCompany3;
	}
    public String getAdvertisersCompany() {
        return advertisersCompany;
    }
    public void setAdvertisersCompany(String advertisersCompany) {
        this.advertisersCompany = advertisersCompany;
    }
	
}
