package com.baidu.gcrm.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_adcontent_data_sample")
public class ADContentDataSample implements Serializable {

	private static final long serialVersionUID = -8924669221278092045L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "ad_content_id", nullable = false)
	private Long adContentId;
	
	@Column(name = "date", nullable = false)
	private String date;
	
	/**	展现量 */
	private Long impressions;
	
	/**	点击量 */
	private Long clicks;
	
	/**	独立IP展现量 */
	private Long uv;
	
	/**	独立IP点击量 */
	@Column(name = "click_uv", nullable = false)
	private Long clickUv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
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
