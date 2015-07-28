package com.baidu.gcrm.ws.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同执行信息DTO
 *
 */
public class ContractExecuteInfoDTO implements Serializable{
    
    private static final long serialVersionUID = 3284847824838375955L;

    private Long startOperator;//上线人,ucid
    
    private Long endOperator;//下线人,ucid
    
	private Date startDate;//上线时间
	
	private Date endDate;//下线日期

    public Long getStartOperator() {
        return startOperator;
    }

    public void setStartOperator(Long startOperator) {
        this.startOperator = startOperator;
    }

    public Long getEndOperator() {
        return endOperator;
    }

    public void setEndOperator(Long endOperator) {
        this.endOperator = endOperator;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
	

}
