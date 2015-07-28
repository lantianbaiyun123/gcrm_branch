package com.baidu.gcrm.report.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baidu.gcrm.common.log.LoggerHelper;

@Entity
public class Hao123ReportBean implements Serializable {

    private static final long serialVersionUID = 30839791672825683L;

    @Id
    private Long id;
    @Transient
    private Long adContentId;
    @Transient
    private Long platformId;
    @Column(name = "site_id")
    private Long siteId;
    @Column(name = "channel_id")
    private Long channelId;
    @Column(name = "area_id")
    private Long areaId;
    @Column(name = "position_id")
    private Long positionId;
    @Column(name = "rotation_type")
    private Integer rotationType;
    @Column(name = "position_number")
    private String postionNumber;
    @Column(name = "advertisers")
    private String advertisers;
    @Column(name = "number")
    private String number;
    @Column(name = "description")
    private String description;
    @Column(name = "industry")
    private Integer industry;
    @Column(name = "actural_start")
    private Date acturalStart;
    @Column(name = "actural_end")
    private Date acturalEnd;
    @Column(name = "customer_quote")
    private Double customerQuote;
    @Column(name = "ratio_mine")
    private Double ratioMine;
    @Column(name = "billing_model_id")
    private Long billingModelId;
    @Transient
    private Object singlePrice;
    @Transient
    private String siteName;
    @Transient
    private String channelName;
    @Transient
    private String areaName;
    @Transient
    private String positionName;
    @Transient
    private String rotationName;
    @Transient
    private String industryName;
    @Transient
    private String billingModelName;
    @Transient
    private Long totalAmount;
    @Transient
    private Integer totalDays;
    @Transient
    private Long accumulatedAmount;
    @Transient
    private Integer accumulatedDays;
    @Column(name = "impressions")
    private Long impressions;
    @Column(name = "clicks")
    private Long clicks;
    @Column(name = "uv")
    private Long uv;
    @Column(name = "click_uv")
    private Long clickUV;
    @Transient
    private String ctr;
    @Transient
    private String cvr;

    public Long getId() {
        return id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public String getPostionNumber() {
        return postionNumber;
    }

    public String getAdvertisers() {
        return advertisers;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public Integer getIndustry() {
        return industry;
    }

    public Date getActuralStart() {
        return acturalStart;
    }

    public Date getActuralEnd() {
        return acturalEnd;
    }

    public Double getCustomerQuote() {
        return customerQuote;
    }

    public Double getRatioMine() {
        return ratioMine;
    }

    public Long getBillingModelId() {
        return billingModelId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Integer getRotationType() {
        return rotationType;
    }

    public void setRotationType(Integer rotationType) {
        this.rotationType = rotationType;
    }

    public void setPostionNumber(String postionNumber) {
        this.postionNumber = postionNumber;
    }

    public void setAdvertisers(String advertisers) {
        this.advertisers = advertisers;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public void setActuralStart(Date acturalStart) {
        this.acturalStart = acturalStart;
    }

    public void setActuralEnd(Date acturalEnd) {
        this.acturalEnd = acturalEnd;
    }

    public void setCustomerQuote(Double customerQuote) {
        this.customerQuote = customerQuote;
    }

    public void setRatioMine(Double ratioMine) {
        this.ratioMine = ratioMine;
    }

    public void setBillingModelId(Long billingModelId) {
        this.billingModelId = billingModelId;
    }

    public Object getSinglePrice() {
        if (singlePrice == null) {
            if (customerQuote != null) {
                return customerQuote;
            } else if (ratioMine != null) {
                NumberFormat nt = DecimalFormat.getPercentInstance();
                nt.setMinimumFractionDigits(2);
                return nt.format(ratioMine.doubleValue() / 100d);
            }
        }
        return singlePrice;
    }

    public void setSinglePrice(Object singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public String getBillingModelName() {
        return billingModelName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public void setBillingModelName(String billingModelName) {
        this.billingModelName = billingModelName;
    }

    public Long getAdContentId() {
        return adContentId;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public Long getAccumulatedAmount() {
        return accumulatedAmount;
    }

    public Integer getAccumulatedDays() {
        return accumulatedDays;
    }

    public Long getImpressions() {
        return impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public Long getUv() {
        return uv;
    }

    public String getCtr() {
        try {
            if (ctr == null && impressions > 0) {
                NumberFormat nt = DecimalFormat.getPercentInstance();
                nt.setMinimumFractionDigits(2);
                return nt.format(clicks.doubleValue() / impressions.doubleValue());
            }
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "获取点击到达率计算出错", e);
        }
        return ctr;
    }

    public String getCvr() {
        return cvr;
    }

    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public void setAccumulatedAmount(Long accumulatedAmount) {
        this.accumulatedAmount = accumulatedAmount;
    }

    public void setAccumulatedDays(Integer accumulatedDays) {
        this.accumulatedDays = accumulatedDays;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr;
    }

    public void setCvr(String cvr) {
        this.cvr = cvr;
    }

    public String getRotationName() {
        return rotationName;
    }

    public void setRotationName(String rotationName) {
        this.rotationName = rotationName;
    }

    public Long getClickUV() {
        return clickUV;
    }

    public void setClickUV(Long clickUV) {
        this.clickUV = clickUV;
    }

}
