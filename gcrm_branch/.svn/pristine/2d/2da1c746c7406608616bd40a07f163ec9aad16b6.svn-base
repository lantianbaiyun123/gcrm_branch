package com.baidu.gcrm.ad.web.utils;

import java.util.Date;

import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.model.Contract.ContractCategory;
import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.common.page.Page;

/**
 * 客户列表查询条件
 *
 */
public class ContractCondition extends Page<Contract>{
    
    private static final long serialVersionUID = -8500774875348852944L;

    public enum QueryType {
       contractNumber,
       belongsSales;
    }
    
    private QueryType queryType = QueryType.contractNumber;
    
    private String queryStr;
    
    /**
     * 生效时间（开始）
     */
    private Date startDate;
    /**
     * 生效时间（结束）
     */
    private Date endDate;
    
    private ContractState contractState;
 
    private ContractCategory contractCategory;
    
    private Long customerId;
    
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

    public ContractState getContractState() {
        return contractState;
    }

    public void setContractState(ContractState contractState) {
        this.contractState = contractState;
    }

    public ContractCategory getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(ContractCategory contractCategory) {
        this.contractCategory = contractCategory;
    }


    @Override
    public Class<Contract> getResultClass() {
        return Contract.class;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
