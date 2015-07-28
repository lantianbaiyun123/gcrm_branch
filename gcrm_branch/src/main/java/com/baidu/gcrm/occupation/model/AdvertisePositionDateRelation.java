package com.baidu.gcrm.occupation.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告内容与指定位置投放时间的映射
 *
 */
@Entity
@Table(name="g_adcontent_position_date_relation")
public class AdvertisePositionDateRelation implements Serializable{

	private static final long serialVersionUID = -2782658387358992917L;

	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 广告内容id
	 */
	@Column(name = "ad_content_id")
	private Long adContentId;
	
	/**
	 * 位置投放表id
	 */
	@Column(name = "position_occ_id")
	private Long positionOccId;
	
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

	public Long getPositionOccId() {
		return positionOccId;
	}

	public void setPositionOccId(Long positionOccId) {
		this.positionOccId = positionOccId;
	}
}
