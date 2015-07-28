package com.baidu.gcrm.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_publish_date_statistics")
public class PublishDateStatistics implements Serializable {

	private static final long serialVersionUID = -8153221511935230176L;
	
	@Id
    @GeneratedValue
    private Long id;
	
	@Column(name = "publish_number", nullable = false)
	private String publishNumber;

    @Column(name = "ad_content_id", nullable = false)
    private Long adContentId;
    
    @Column(name = "publish_date_id", nullable = false)
    private Long publishDateId;
    
    private Long impressions;

    private Long clicks;

    private Long uv;

    @Column(name = "click_uv", nullable = false)
    private Long clickUv;

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

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	public Long getPublishDateId() {
		return publishDateId;
	}

	public void setPublishDateId(Long publishDateId) {
		this.publishDateId = publishDateId;
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

	@Override
	public String toString() {
		return "PublishDateStatistics [id=" + id + ", adContentId=" + adContentId + ", publishDateId=" + publishDateId
				+ ", impressions=" + impressions + ", clicks=" + clicks + ", uv=" + uv + ", clickUv=" + clickUv + "]";
	}
    
}
