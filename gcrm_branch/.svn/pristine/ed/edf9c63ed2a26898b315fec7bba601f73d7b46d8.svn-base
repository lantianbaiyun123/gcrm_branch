package com.baidu.gcrm.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 位置在某一天的CPM总库存
 * 
 * @author anhuan
 * 
 */
@Entity
public class PositionDateStock {

    @Id
    private Long id;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_stock")
    private Long totalStock;

    @Column(name = "billing_model_id")
    private Long billingModelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Long totalStock) {
        this.totalStock = totalStock;
    }

    public Long getBillingModelId() {
        return billingModelId;
    }

    public void setBillingModelId(Long billingModelId) {
        this.billingModelId = billingModelId;
    }
}
