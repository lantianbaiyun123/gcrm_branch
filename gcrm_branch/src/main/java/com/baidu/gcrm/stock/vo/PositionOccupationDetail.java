package com.baidu.gcrm.stock.vo;

import java.util.Date;
import java.util.List;

/**
 * 资源位排期情况
 * @author anhuan
 *
 */
public class PositionOccupationDetail {
    
    private Long positionId;
    
    private Date from;
    
    private Date to;
    
    private boolean rotation;
    
    private int totalCount;
    
    private List<AdContentBriefDTO> schedules;

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<AdContentBriefDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<AdContentBriefDTO> schedules) {
        this.schedules = schedules;
    }
    
}
