package com.baidu.gcrm.stock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_stock")
public class Stock implements Serializable {

	private static final long serialVersionUID = -3669661027131693022L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "position_date_id")
	private Long positionDateId;
	
	@Column(name = "total_stock")
	private Long totalStock;
	
	@Column(name = "occupied_stock")
	private Long occupiedStock; // 根据其他类型广告占用情况变化
	
	@Column(name = "real_occupied_stock")
    private Long realOccupiedStock; // 自身实际占用，不受其他类型占用影响

    @Column(name = "billing_model_id")
	private Long billingModelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPositionDateId() {
		return positionDateId;
	}

	public void setPositionDateId(Long positionDateId) {
		this.positionDateId = positionDateId;
	}

	public Long getTotalStock() {
		return totalStock;
	}

	public void setTotalStock(Long totalStock) {
		this.totalStock = totalStock;
	}

	public Long getOccupiedStock() {
		return occupiedStock;
	}

	public void setOccupiedStock(Long occupiedStock) {
		this.occupiedStock = occupiedStock;
	}

	public Long getBillingModelId() {
		return billingModelId;
	}

	public void setBillingModelId(Long billingModelId) {
		this.billingModelId = billingModelId;
	}
	
	public Long getRealOccupiedStock() {
        return realOccupiedStock;
    }

    public void setRealOccupiedStock(Long realOccupiedStock) {
        this.realOccupiedStock = realOccupiedStock;
    }

	@Override
	public String toString() {
		return "Stock [id=" + id + ", posDateId=" + positionDateId + ", totalStock=" + totalStock
				+ ", realOccupiedStock=" + realOccupiedStock
				+ ", occupiedStock=" + occupiedStock + ", billingId=" + billingModelId + "]";
	}
	
}
