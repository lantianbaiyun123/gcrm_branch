package com.baidu.gcrm.customer.web.helper;

import java.util.List;

import com.baidu.gcrm.common.page.Page;

/**
 * 客户列表查询条件
 *
 */
public class CustomerCondition extends Page<CustomerListBean>{

    private static final long serialVersionUID = -2949776662518168171L;


    public enum QueryType {
        SALES,
        AGENT,
        COMPANY,
        AGENT_CUSTOMER_NUMBER;
    }
    
    private QueryType queryType = QueryType.COMPANY;
    
    private String queryStr;
    
    private Integer agentRegional;
    
    private CustomerApproveState approvalStatus;
    
    private Integer country;
    
    private String businessType;
    
    private CustomerType customerType;
    
  
    /**
	 * 提交人（首页查询）
	 */
	private Long createOperator;
	
	/**
	 * 提交开始时间（首页查询）
	 */
	private String createStartDate;
	/**
	 * 首页查询 增加状态控制
	 */
	private List<Integer> approvalStatusList;
	
	
    public List<Integer> getApprovalStatusList() {
		return approvalStatusList;
	}


	public void setApprovalStatusList(List<Integer> approvalStatusList) {
		this.approvalStatusList = approvalStatusList;
	}


	public Long getCreateOperator() {
		return createOperator;
	}


	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}


	public String getCreateStartDate() {
		return createStartDate;
	}


	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}


	public QueryType getQueryType() {
        return queryType;
    }


    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }


    public String getQueryStr() {
        return queryStr;
    }


    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }


    public Integer getAgentRegional() {
        return agentRegional;
    }


    public void setAgentRegional(Integer agentRegional) {
        this.agentRegional = agentRegional;
    }


    public CustomerApproveState getApprovalStatus() {
        return approvalStatus;
    }


    public void setApprovalStatus(CustomerApproveState approvalStatus) {
        this.approvalStatus = approvalStatus;
    }


    public Integer getCountry() {
        return country;
    }


    public void setCountry(Integer country) {
        this.country = country;
    }


    public String getBusinessType() {
        return businessType;
    }


    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }


    public CustomerType getCustomerType() {
        return customerType;
    }


    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }


    @Override
    public Class<CustomerListBean> getResultClass() {
        return CustomerListBean.class;
    }
}
