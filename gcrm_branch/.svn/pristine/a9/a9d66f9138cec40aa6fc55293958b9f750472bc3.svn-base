package com.baidu.gcrm.customer.web.helper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baidu.gcrm.common.auth.RequestThreadLocal;

/**
 * 
 * 列表显示bean
 * 
 */
@Entity
public class CustomerListBean {

    @Id
    private Integer id;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "business_type")
    private String businessType;
    @Transient
    private String businessTypeName;

    @Column(name="customer_type")
    private Integer customerType;
    @Transient
    private String customerTypeName;
    
    private Integer country;

    @Transient
    private String countryName;

    @Column(name = "agenct_regional")
    private Integer agentRegional;

    @Transient
    private String agentRegionalName;

    @Transient
    private Integer agentCompany;

    private String agentCompanyName;

    @Column(name = "belongs_sales")
    private Long belongSales;

    private String belongSalesName;

    @Column(name = "approval_status")
    private Integer approvalStatus;

    @Column(name = "company_status")
    private Integer status;
    
    @Transient
    private CustomerState customerState;

    public CustomerState getCustomerState() {
    	if(status != null){
    		customerState = CustomerState.valueOf(status);
    	}
		return customerState;
	}

	public void setCustomerState(CustomerState customerState) {
		this.customerState = customerState;
	}

	@Transient
    private String approvalStatusName;

    @Transient
    private Map<String,Boolean> buttonRole;
    /**
     * 首页查询用
     */
    private String taskInfor;
   
    @Column(name = "create_time")
    private Date createTime;
    @Column(name="create_operator")
    private Long createOperator;
    /**
     * 是否提交审批guo
     */
    @Transient
    private boolean hasBeenApproved;
    
    public boolean getHasBeenApproved(){
        
        if(createTime ==null){
            return false;
        } 
        return true;
    }
    
    public String getTaskInfor() {
		return taskInfor;
	}

	public void setTaskInfor(String taskInfo) {
		this.taskInfor = taskInfo;
	}

	private enum ButtonType {
        show {

            @Override
            public boolean isDisable() {
                return true;
            }
        },
        changeSaler {

            @Override
            public boolean isDisable() {
				Integer cutomerStatus = getCustomer().getStatus();
                if (CustomerState.waiting_take_effect.equals(CustomerState.valueOf(cutomerStatus))
                        || CustomerState.have_taken_effect.equals(CustomerState.valueOf(cutomerStatus)))
                    return true;
                return false;
            }
        }, // 銷售轉悠
        withdraw {

            @Override
            public boolean isDisable() {
				Long currentUser = RequestThreadLocal.getLoginUserId();
				boolean isOwner = currentUser.equals(getCustomer().getCreateOperator()) ? true : currentUser.equals(getCustomer().getBelongSales()) ? true : false;

				Integer approveStatus = getCustomer().getApprovalStatus();
                if (CustomerApproveState.approving.equals(CustomerApproveState.valueOf(approveStatus)))
                    return isOwner;
                return false;
            }
        }, // 撤回
        discard {

            @Override
            public boolean isDisable() {
				Long currentUser = RequestThreadLocal.getLoginUserId();
                boolean isOwner = currentUser.equals(getCustomer().getCreateOperator()) ? true : currentUser.equals(getCustomer().getBelongSales()) ? true : false;
				return changeSaler.isDisable() && isOwner;
            }
        }, // 作廢
        recovery {

            @Override
            public boolean isDisable() {
                Long currentUser =RequestThreadLocal.getLoginUserId();
                boolean isOwner = currentUser.equals(getCustomer().getCreateOperator()) ? true : currentUser.equals(getCustomer().getBelongSales()) ? true : false;
                Integer cutomerStatus = getCustomer().getStatus();
                if (CustomerState.disabled.equals(CustomerState.valueOf(cutomerStatus)))
                    return isOwner;
                return false;
            }
        }, // 恢復
        reminders {

            @Override
            public boolean isDisable() {
                return withdraw.isDisable();
            }
        }, // 催办
        addAccount {
            @Override
            public boolean isDisable() {
                Integer approveStatus = getCustomer().getApprovalStatus();
                if (CustomerApproveState.approved.equals(CustomerApproveState.valueOf(approveStatus)))
                    return true;
                return false;

            }
        };// 添加账户
        public abstract boolean isDisable();

        private CustomerListBean customer;

        public void setCustomer(CustomerListBean customer) {
             this.customer = customer;
        }
        
        public CustomerListBean getCustomer(){
            return  this.customer;
        }
    }

    public Map<String,Boolean> getButtonRole() {
        Map<String,Boolean> result = new HashMap<String, Boolean>();
        ButtonType[] buttonTypes = ButtonType.values();

        for (ButtonType buttonType : buttonTypes) {
            buttonType.setCustomer(this);
            result.put(buttonType.toString(), buttonType.isDisable());
        }
        return result ;

    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getAgentRegional() {
        return agentRegional;
    }

    public void setAgentRegional(Integer agentRegional) {
        this.agentRegional = agentRegional;
    }

    public String getAgentRegionalName() {
        return agentRegionalName;
    }

    public void setAgentRegionalName(String agentRegionalName) {
        this.agentRegionalName = agentRegionalName;
    }

    public Integer getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(Integer agentCompany) {
        this.agentCompany = agentCompany;
    }

    public String getAgentCompanyName() {
        return agentCompanyName;
    }

    public void setAgentCompanyName(String agentCompanyName) {
        this.agentCompanyName = agentCompanyName;
    }

    public Long getBelongSales() {
        return belongSales;
    }

    public void setBelongSales(Long belongSales) {
        this.belongSales = belongSales;
    }

    public String getBelongSalesName() {
        return belongSalesName;
    }

    public void setBelongSalesName(String belongSalesName) {
        this.belongSalesName = belongSalesName;
    }

    public Integer getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Integer approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatusName() {
        return approvalStatusName;
    }

    public void setApprovalStatusName(String approvalStatusName) {
        this.approvalStatusName = approvalStatusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
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

}
