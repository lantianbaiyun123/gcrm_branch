package com.baidu.gcrm.publish.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_publish_date")
public class PublishDate implements Serializable, Comparable<PublishDate> {

    private static final long serialVersionUID = 4139783661068672812L;

    public enum PublishPeriodStatus {
        not_start, ongoing, end
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "p_number")
    private String publishNumber;

    @Column(name = "plan_start")
    private Date planStart;

    @Column(name = "plan_end")
    private Date planEnd;

    @Column(name = "actural_start")
    private Date acturalStart;

    @Column(name = "actural_end")
    private Date acturalEnd;

    @Column(name = "start_operator")
    private Long startOperator;

    @Column(name = "end_operator")
    private Long endOperator;

    @Enumerated(EnumType.STRING)
    private PublishPeriodStatus status;

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

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(Date planEnd) {
        this.planEnd = planEnd;
    }

    public Date getActuralStart() {
        return acturalStart;
    }

    public void setActuralStart(Date acturalStart) {
        this.acturalStart = acturalStart;
    }

    public Date getActuralEnd() {
        return acturalEnd;
    }

    public void setActuralEnd(Date acturalEnd) {
        this.acturalEnd = acturalEnd;
    }

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

    public PublishPeriodStatus getStatus() {
        return status;
    }

    public void setStatus(PublishPeriodStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PublishDate [id=" + id + ", number=" + publishNumber + ", planStart=" + planStart + ", planEnd="
                + planEnd + ", acturalStart=" + acturalStart + ", acturalEnd=" + acturalEnd + ", status=" + status
                + "]";
    }

    @Override
    public int compareTo(PublishDate pd) {
        if (planStart != null) {
            return planStart.compareTo(pd.getPlanStart());
        }
        return 0;
    }
}
