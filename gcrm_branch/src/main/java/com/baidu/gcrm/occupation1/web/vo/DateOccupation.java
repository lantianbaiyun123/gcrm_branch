package com.baidu.gcrm.occupation1.web.vo;

import java.util.Date;

public class DateOccupation {

    private Date date;
    
    /* 无剩余流量 */
    private boolean isFull = false;
    
    /* 已占用库存，针对CPT */
    private long occupied;
    
    /* 剩余库存，针对CPM */
    private long remained;
    
    /* 竞争流量人数，针对CPA、CPS、CPC */
    private int biddingCount;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean isFull) {
        this.isFull = isFull;
    }

    public long getOccupied() {
        return occupied;
    }

    public void setOccupied(long occupied) {
        this.occupied = occupied;
    }

    public long getRemained() {
        return remained;
    }

    public void setRemained(long remained) {
        this.remained = remained;
    }

    public int getBiddingCount() {
        return biddingCount;
    }

    public void setBiddingCount(int biddingCount) {
        this.biddingCount = biddingCount;
    }
    
}
