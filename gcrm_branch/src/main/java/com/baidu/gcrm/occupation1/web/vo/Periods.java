package com.baidu.gcrm.occupation1.web.vo;

import java.util.List;

import com.baidu.gcrm.occupation.helper.DatePeriod;

public class Periods {
    
    private int totalDays;
    
    private List<DatePeriod> datePeriod;
    
    /**
     * 时间范围内，最小的剩余库存，即前端最大可以填写的日投放量
     */
    private Long minStock;
    
    /**
     * 拥有最小库存的时间段
     */
    private String minStockPeriods;
    
    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public List<DatePeriod> getDatePeriod() {
        return datePeriod;
    }

    public void setDatePeriod(List<DatePeriod> datePeriod) {
        this.datePeriod = datePeriod;
    }

    public Long getMinStock() {
        return minStock;
    }

    public void setMinStock(Long minStock) {
        this.minStock = minStock;
    }

    public String getMinStockPeriods() {
        return minStockPeriods;
    }

    public void setMinStockPeriods(String minStockPeriods) {
        this.minStockPeriods = minStockPeriods;
    }
    
}
