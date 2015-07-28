package com.baidu.gcrm.schedule.web.vo;

import java.util.Date;

public class ScheduleDateVO {
    
    private Date date;
    private int totalAmount;
    private boolean isInsertDate;
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    public boolean isInsertDate() {
        return isInsertDate;
    }
    public void setInsertDate(boolean isInsertDate) {
        this.isInsertDate = isInsertDate;
    }
    
}
