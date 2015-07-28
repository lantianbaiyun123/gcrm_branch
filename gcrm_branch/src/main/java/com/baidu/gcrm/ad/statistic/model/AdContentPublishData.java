package com.baidu.gcrm.ad.statistic.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_adcontent_publish_data")
public class AdContentPublishData implements Serializable{

    private static final long serialVersionUID = -243145462302389435L;
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "publish_number")
    private String publishNumber;
    
    @Column(name = "publish_time")
    private Date publishTime;
    
    @Column(name = "site_url")
    private String siteUrl;
    
    @Column(name="customer_number")
    private Long customerNumber;
    
    @Column(name="ad_content_number")
    private String adContentNumber;
    
    @Column(name="position_id")
    private Long positionId;
    
    @Column(name="site_code")
    private String siteCode;
    
    @Column
    private Long click;
    
    @Column
    private Long uv;
    
    @Column(name="create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublishNumber() {
        return publishNumber;
    }

    public void setPublishNumber(String publishNumber) {
        this.publishNumber = publishNumber;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getAdContentNumber() {
        return adContentNumber;
    }

    public void setAdContentNumber(String adContentNumber) {
        this.adContentNumber = adContentNumber;
    }
    
}
