package com.baidu.gcrm.customer.web.helper;

import java.util.List;
import java.util.Set;

import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.opportunity.web.vo.OpportunityView;
import com.baidu.gcrm.qualification.model.Qualification;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
import com.baidu.gcrm.valuelistcache.model.Country;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.model.Industry;

public class CustomerView {
	protected Customer customer;
	protected CustomerType customerType;
	protected Industry industry;
	protected CompanySize companySize;
	protected Country country;
	protected AgentType agentType;
	protected AgentRegional agentRegional;
	protected Set<Country> agentCountry;
	protected Customer agentCompany;
	private CustomerApproveState approvalStatus;
	protected CurrencyType currencyType;
	protected User  belongSales;
	protected User belongManager;
	protected boolean hasContract;
	
	/**
	 * 联系人
	 */
	protected List<ContactPerson> contacts;
	/**
	 * 业务机会
	 * 客户类型非代理时填写
	 */
	protected OpportunityView opportunityView;
	/**
	 * 代理商资质
	 * 当客户类型为代理时需填写
	 */
	protected Qualification qualification;
	/**
	 * 客户资质文件
	 */
	protected List<Attachment> attachments;
	/**
	 * 是否可以加签操作
	 */
	boolean isAddPlusOperate;
	
	public boolean isAddPlusOperate() {
        return isAddPlusOperate;
    }

    public void setAddPlusOperate(boolean isAddPlusOperate) {
        this.isAddPlusOperate = isAddPlusOperate;
    }

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public CompanySize getCompanySize() {
		return companySize;
	}

	public void setCompanySize(CompanySize companySize) {
		this.companySize = companySize;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public AgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}

	public AgentRegional getAgentRegional() {
		return agentRegional;
	}

	public void setAgentRegional(AgentRegional agentRegional) {
		this.agentRegional = agentRegional;
	}

	public Set<Country> getAgentCountry() {
		return agentCountry;
	}

	public void setAgentCountry(Set<Country> agentCountry) {
		this.agentCountry = agentCountry;
	}

	public Customer getAgentCompany() {
		return agentCompany;
	}

	public void setAgentCompany(Customer agentCompany) {
		this.agentCompany = agentCompany;
	}

	public CustomerApproveState getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(CustomerApproveState approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public User getBelongSales() {
		return belongSales;
	}

	public void setBelongSales(User belongSales) {
		this.belongSales = belongSales;
	}

    public List<ContactPerson> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactPerson> contacts) {
        this.contacts = contacts;
    }

    public OpportunityView getOpportunityView() {
        return opportunityView;
    }

    public void setOpportunityView(OpportunityView opportunityView) {
        this.opportunityView = opportunityView;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public User getBelongManager() {
        return belongManager;
    }

    public void setBelongManager(User belongManager) {
        this.belongManager = belongManager;
    }

	public boolean isHasContract() {
		return hasContract;
	}

	public void setHasContract(boolean hasContract) {
		this.hasContract = hasContract;
	}
	
}
