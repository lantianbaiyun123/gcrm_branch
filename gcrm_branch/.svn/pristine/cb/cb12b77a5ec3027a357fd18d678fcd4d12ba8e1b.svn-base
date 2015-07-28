package com.baidu.gcrm.schedule1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;

@Entity
public class ScheduleVO {

    @Id
    private Long id;

    @Column
    private String number;

    @Column(name = "ad_content_id")
    private Long adContentId;
    
    @Column(name = "ad_solution_id")
    private Long adSolutionId;

    @Column(name = "period_description")
    private String periodDescription;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ScheduleStatus status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column
    private String advertisers;
    
    @Column
    private String description;

    @Transient
    private String statusName;
    
    @Column(name = "billing_model_id")
    private Long billingModelId;
    
    @Transient
    private String billingModel;
    
    @Column(name = "contract_number")
    private String contractNumber;
    
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "ad_platform_id")
    private Long adPlatformId;
    
    @Transient
    private String adPlatformName;
    
    @Column(name = "site_id")
    private Long siteId;
    
    @Transient
    private String siteName;
    
    @Column(name = "channel_id")
    private Long channelId;
    
    @Transient
    private String channelName;
    
    @Column(name = "area_id")
    private Long areaId;
    
    @Transient
    private String areaName;
    
    @Column(name = "position_id")
    private Long positionId;
    
    @Transient
    private String positionName;
    
    @Transient
    private String guodai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getAdContentId() {
        return adContentId;
    }

    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(String advertisers) {
        this.advertisers = advertisers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getBillingModelId() {
        return billingModelId;
    }

    public void setBillingModelId(Long billingModelId) {
        this.billingModelId = billingModelId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBillingModel() {
        return billingModel;
    }

    public void setBillingModel(String billingModel) {
        this.billingModel = billingModel;
    }

    public Long getAdPlatformId() {
        return adPlatformId;
    }

    public void setAdPlatformId(Long adPlatformId) {
        this.adPlatformId = adPlatformId;
    }

    public String getAdPlatformName() {
        return adPlatformName;
    }

    public void setAdPlatformName(String adPlatformName) {
        this.adPlatformName = adPlatformName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getGuodai() {
        return guodai;
    }

    public void setGuodai(String guodai) {
        this.guodai = guodai;
    }

    public Long getAdSolutionId() {
        return adSolutionId;
    }

    public void setAdSolutionId(Long adSolutionId) {
        this.adSolutionId = adSolutionId;
    }

}
