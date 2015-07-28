package com.baidu.gcrm.schedule1.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "g_schedules")
public class Schedules implements Serializable {

    private static final long serialVersionUID = -126898164610736770L;

    /**
     * 排期单状态
     * 
     */
    public enum ScheduleStatus {
        /** 确认 */
        confirmed,
        /** 锁定 */
        locked,
        /** 释放 */
        released;

        public static ScheduleStatus valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            ScheduleStatus[] values = ScheduleStatus.values();
            for (ScheduleStatus status : values) {
                if (status.ordinal() == value) {
                    return status;
                }
            }
            return null;
        }

        public boolean isConfirmed() {
            return this.equals(confirmed);
        }

        public boolean isLocked() {
            return this.equals(locked);
        }

        public boolean isReleased() {
            return this.equals(released);
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 排期单编号，8位随机数字
     */
    @Column(name = "number")
    private String number;

    /**
     * 广告内容id
     */
    @Column(name = "ad_content_id")
    private Long adContentId;

    /**
     * 位置id，对应唯一的位置
     */
    @Column(name = "position_id")
    private Long positionId;

    /**
     * 广告投放时间段
     */
    @Column(name = "period_description")
    @Size(max = 2000)
    private String periodDescription;

    /**
     * 排期单状态
     */
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private ScheduleStatus status;

    @Column(name = "completed")
    private Integer completed;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "lock_time")
    private Date lockTime;

    @Column(name = "release_time")
    private Date releaseTime;

    @Column(name = "create_operator")
    private Long createOperator;
    
    @Column(name = "billing_model_id")
    private Long billingModelId;

    /**
     * 广告主
     */
    @Transient
    private String advertisers;
    /**
     * 广告内容
     */
    @Transient
    private String description;

    @Transient
    private String statusName;

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

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
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

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Long getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;
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

}
