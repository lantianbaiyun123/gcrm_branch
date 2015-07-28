package com.baidu.gcrm.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_position_data_sample")
public class PositionDataSample implements Serializable {

    private static final long serialVersionUID = -3615796494182159155L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "position_number", nullable = false)
    private String positionNumber;

    @Column(name = "date", nullable = false)
    private String date;

    /** 展现量 */
    private Long impressions;

    /** 点击量 */
    private Long clicks;

    /** 独立IP展现量 */
    private Long uv;

    /** 独立IP点击量 */
    @Column(name = "click_uv", nullable = false)
    private Long clickUv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Long getClickUv() {
        return clickUv;
    }

    public void setClickUv(Long clickUv) {
        this.clickUv = clickUv;
    }

}
