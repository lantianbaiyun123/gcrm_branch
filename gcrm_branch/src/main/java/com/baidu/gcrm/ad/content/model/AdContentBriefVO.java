package com.baidu.gcrm.ad.content.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;

/**
 * 精简的广告内容信息，包括广告主、操作人、计费方式、投放时间和状态
 * 
 * @author anhuan
 * 
 */
@Entity
public class AdContentBriefVO {

    /* 广告内容id */
    @Id
    private Long id;

    @Column(name = "advertisers")
    private String advertiser;

    @Column(name = "operator")
    private Long operator;

    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;
    
    @Column(name = "billing_model_id")
    private Long billingModelId;
    
    @Column(name = "advertise_solution_id")
    private Long adSolutionId;
    
    /* 广告内容编号 */
    @Column(name = "number")
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getBillingModelId() {
        return billingModelId;
    }

    public void setBillingModelId(Long billingModelId) {
        this.billingModelId = billingModelId;
    }

    public Long getAdSolutionId() {
        return adSolutionId;
    }

    public void setAdSolutionId(Long adSolutionId) {
        this.adSolutionId = adSolutionId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
