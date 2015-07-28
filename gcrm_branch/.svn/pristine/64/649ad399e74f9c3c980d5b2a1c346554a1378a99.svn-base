package com.baidu.gcrm.publish.web.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baidu.gcrm.publish.model.Publish.PublishStatus;
@Entity
public class PublishDoneListVO {
    @Id
    private Long id;
    @Column(name = "number")
	private String number;//编号
    @Column(name = "ac_number")
	private String adContentNumber;//内容编号
    @Column(name = "ac_id")
    private Long adContentId;
    public Long getAdContentId() {
        return adContentId;
    }
    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }
    @Column(name = "advertisers")
	private String advertisers;//广告主
    @Column(name = "description")
	private String adContentDesc;//内容描述
	@Transient
	private String positionName;//位置描述
    @Column(name = "product_id")
	private Long productId;//平台
    @Column(name = "channel_id")
	private Long channelId;//频道
    @Column(name = "area_id")
	private Long areaId;//区域
    @Column(name = "position_id")
	private Long positionId;//位置
    @Column(name = "site_id")
	private Long siteId;//站点
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
	private PublishStatus  publishStatus;
    @Transient
	private Date operateDate;
	private String scheduleNumber;//排期单编号
	private Long scheduleId;//排期单id
    @Column(name = "online_number")
	private String onlineNumber;//上线码
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getAdContentNumber() {
        return adContentNumber;
    }
    public void setAdContentNumber(String adContentNumber) {
        this.adContentNumber = adContentNumber;
    }
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
    public String getAdvertisers() {
        return advertisers;
    }
    public void setAdvertisers(String advertisers) {
        this.advertisers = advertisers;
    }
    public String getAdContentDesc() {
        return adContentDesc;
    }
    public void setAdContentDesc(String adContentDesc) {
        this.adContentDesc = adContentDesc;
    }
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getChannelId() {
        return channelId;
    }
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    public Long getPositionId() {
        return positionId;
    }
    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }
    public Long getSiteId() {
        return siteId;
    }
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
    public PublishStatus getPublishStatus() {
        return publishStatus;
    }
    public void setPublishStatus(PublishStatus publishStatus) {
        this.publishStatus = publishStatus;
    }
    public Date getOperateDate() {
        return operateDate;
    }
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
    public String getScheduleNumber() {
        return scheduleNumber;
    }
    public void setScheduleNumber(String scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }
    public Long getScheduleId() {
        return scheduleId;
    }
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    public String getOnlineNumber() {
        return onlineNumber;
    }
    public void setOnlineNumber(String onlineNumber) {
        this.onlineNumber = onlineNumber;
    }
	 
	
}
