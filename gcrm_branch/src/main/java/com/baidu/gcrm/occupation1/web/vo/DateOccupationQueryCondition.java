package com.baidu.gcrm.occupation1.web.vo;

import com.baidu.gcrm.occupation.helper.DatePeriod;

/**
 * 指定位置某种计费方式在特定时间范围内投放情况查询条件
 * @author anhuan
 *
 */
public class DateOccupationQueryCondition {
    
    private DatePeriod period;
    
    private Long positionId;
    
    private Long billingModelId;
    
    /**
     * 旧内容id，针对内容修改的情况，投放情况需要去掉旧内容的占用库存
     */
    private Long oldContentId;

    public DatePeriod getPeriod() {
        return period;
    }

    public void setPeriod(DatePeriod period) {
        this.period = period;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getBillingModelId() {
        return billingModelId;
    }

    public void setBillingModelId(Long billingModelId) {
        this.billingModelId = billingModelId;
    }

    public Long getOldContentId() {
        return oldContentId;
    }

    public void setOldContentId(Long oldContentId) {
        this.oldContentId = oldContentId;
    }
    
}
