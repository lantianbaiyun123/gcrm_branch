package com.baidu.gcrm.resource.adplatform.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.i18n.model.BaseI18nModel;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.site.model.Site;

@Entity
@Table(name = "g_advertising_platform")
public class AdPlatform extends BaseI18nModel implements BaseOperationModel{
    
    private static final long serialVersionUID = -6765467664140844643L;

    public enum AdPlatformStatus {
        disable,
        enable;
    }
    
    public enum AdPlatformBusinessType {
        liquidate,
        sales;
        
        public static AdPlatformBusinessType valueOf(Integer value) {
            if(value == null){
                return null;
            }
            AdPlatformBusinessType[] values = AdPlatformBusinessType.values(); 
            for(AdPlatformBusinessType adBusinessType : values){
                if(adBusinessType.ordinal() == value.intValue()){
                    return adBusinessType;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue
    private Long id;
    
    @Transient
    private String enName;
    
    @Transient
    private String cnName;
    
    @Transient
    private String siteNames;
    
    @Transient
    private List<Site> sites;
    
    @Transient
    private List<LocaleVO> i18nData;
    
    @Transient
    private int siteCount;
    
    @Column
    @Enumerated(EnumType.ORDINAL)
    private AdPlatformStatus status;
    
    @Column(name="business_type")
    @Enumerated(EnumType.ORDINAL)
    private AdPlatformBusinessType businessType;
    
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_operator")
    private Long createOperator;

    @Column(name = "last_update_time")
    private Date updateTime;

    @Column(name = "last_update_operator")
    private Long updateOperator;

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

    public AdPlatformStatus getStatus() {
        return status;
    }

    public void setStatus(AdPlatformStatus status) {
        this.status = status;
    }

    public String getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(String siteNames) {
        this.siteNames = siteNames;
    }

    public int getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(int siteCount) {
        this.siteCount = siteCount;
    }

    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public List<LocaleVO> getI18nData() {
        return i18nData;
    }

    public void setI18nData(List<LocaleVO> i18nData) {
        this.i18nData = i18nData;
    }

    public AdPlatformBusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(AdPlatformBusinessType businessType) {
        this.businessType = businessType;
    }
    
}
