package com.baidu.gcrm.resource.adplatform.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_ad_platform_site_relation")
public class AdPlatformSiteRelation implements BaseOperationModel {

    private static final long serialVersionUID = 7332258100222807226L;

    public enum AdPlatformSiteRelationStatus {
        disable, enable;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "ad_platform_id")
    private Long adPlatformId;

    @Column(name = "site_id")
    private Long siteId;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private AdPlatformSiteRelationStatus status = AdPlatformSiteRelationStatus.enable;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_operator")
    private Long createOperator;

    @Column(name = "last_update_time")
    private Date updateTime;

    @Column(name = "last_update_operator")
    private Long updateOperator;

    @Transient
    private String siteName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;

    }

    @Override
    public Long getCreateOperator() {
        return createOperator;
    }

    @Override
    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;

    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;

    }

    @Override
    public Long getUpdateOperator() {
        return updateOperator;
    }

    @Override
    public void setUpdateOperator(Long updateOperator) {
        this.updateOperator = updateOperator;

    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getAdPlatformId() {
        return adPlatformId;
    }

    public void setAdPlatformId(Long adPlatformId) {
        this.adPlatformId = adPlatformId;
    }

    public AdPlatformSiteRelationStatus getStatus() {
        return status;
    }

    public void setStatus(AdPlatformSiteRelationStatus status) {
        this.status = status;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

}
