package com.baidu.gcrm.customer.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.customer.web.helper.AgentType;
import com.baidu.gcrm.customer.web.helper.CompanySize;
import com.baidu.gcrm.customer.web.helper.CustomerDataState;
import com.baidu.gcrm.customer.web.helper.CustomerType;

@Entity
@Table(name = "g_customer")
public class Customer implements BaseOperationModel {
	private static final long serialVersionUID = 1817925177261459436L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "customer_number")
	private Long customerNumber;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_size")
	@Enumerated(EnumType.ORDINAL)
	private CompanySize companySize;

	@Column(name = "registration_time")
	private Date registerTime;

	@Column(name = "registered_capital")
	private Long registerCapital;

	@Column(name = "currency_type")
	private Integer currencyType;

	@Column(name = "address")
	private String address;

	@Column(name = "url")
	private String url;

	@Column(name = "business_scope")
	private String businessScope;

	/**
	 * 业务类型，包括销售和变现
	 * 
	 */
	@Column(name = "business_type")
	private String businessType;

	@Column(name = "description")
	private String description;

	@Column(name = "country")
	private Integer country;

	@Column(name = "business_license")
	private String businessLicense;

	@Column(name = "belongs_sales")
	private Long belongSales;

	@Column(name = "belongs_manager")
	private Long belongManager;

	/**
	 * 客户类型，包括线下、直客、非直客、网盟
	 */
	@Column(name = "customer_type")
	@Enumerated(EnumType.ORDINAL)
	private CustomerType customerType;

	/**
	 * 审核状态，包括未提交、审核驳回、待审核、审核通过
	 */
	@Column(name = "approval_status")
	private Integer approvalStatus;

	/**
	 * 客户状态，包括 0: 未生效、1:已生效、2:作废
	 */
	@Column(name = "company_status")
	private Integer companyStatus;

	@Column(name = "agenct_type")
	@Enumerated(EnumType.ORDINAL)
	private AgentType agentType;

	@Column(name = "agenct_regional")
	private Integer agentRegional;

	@Column(name = "agenct_country")
	private String agentCountry;

	@Column(name = "industry")
	private Integer industry;

	@Column(name = "agenct_company")
	private Long agentCompany;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_operator")
	private Long createOperator;

	@Transient
	private String createOperatorName;

	@Column(name = "last_update_time")
	private Date updateTime;

	@Column(name = "last_update_operator")
	private Long updateOperator;

	@Column(name = "task_info")
	private String taskInfo;

	@Column(name = "data_status")
	@Enumerated(EnumType.ORDINAL)
	private CustomerDataState dataStatus;

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public CompanySize getCompanySize() {
		return companySize;
	}

	public void setCompanySize(CompanySize companySize) {
		this.companySize = companySize;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(Long registerCapital) {
		this.registerCapital = registerCapital;
	}

	public Integer getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		if (StringUtils.isBlank(url) || url.startsWith("http")) {
			return url;
		}
		return "http://" + url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCountry() {
		return country;
	}

	// 这个是？
	public void setCountry(String country) {
		this.country = Integer.valueOf(country);
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Long getBelongSales() {
		return belongSales;
	}

	public void setBelongSales(Long belongSales) {
		this.belongSales = belongSales;
	}

	public Long getBelongManager() {
		return belongManager;
	}

	public void setBelongManager(Long belongManager) {
		this.belongManager = belongManager;
	}

	public CustomerType getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(CustomerType type) {
		this.customerType = type;
	}

	public Integer getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Integer approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Integer getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(Integer companyStatus) {
		this.companyStatus = companyStatus;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public Integer getAgentRegional() {
		return agentRegional;
	}

	public void setAgentRegional(Integer agentRegional) {
		this.agentRegional = agentRegional;
	}

	public String getAgentCountry() {
		return agentCountry;
	}

	public void setAgentCountry(String agentCountry) {
		this.agentCountry = agentCountry;
	}

	public Integer getIndustry() {
		return industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}

	public Long getAgentCompany() {
		return agentCompany;
	}

	public void setAgentCompany(Long agentCompany) {
		this.agentCompany = agentCompany;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTaskInfo() {
		return taskInfo;
	}

	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}

	public CustomerDataState getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(CustomerDataState dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getCreateOperatorName() {
		return createOperatorName;
	}

	public void setCreateOperatorName(String createOperatorName) {
		this.createOperatorName = createOperatorName;
	}
}
