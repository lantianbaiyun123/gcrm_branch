package com.baidu.gcrm.account.rights.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

/**
 * 岗位与子岗位关系
 * 
 * @author zhanglei35
 * 
 */
@Entity
@Table(name = "g_rights_position_sub")
public class RightsPositionSub implements BaseOperationModel {

	private static final long serialVersionUID = -5044104372706065933L;

	// 自增长主键
	@Id
	@GeneratedValue
	private Long id;

	public enum PositionSubType {
		/** 非直接下级 */
		INDIRECT,
		/** 直接下级 */
		DIRECT;
	}

	// 岗位ID
	@Column(name = "pos_id")
	private Long posId;

	// 子岗位ID
	@Column(name = "pos_sub_id")
	private Long subId;

	// 是否是直接子岗位
	@Column(name = "direct_sub")
	@ModifyCheckIgnore
	@Enumerated(EnumType.ORDINAL)
	private PositionSubType directSub;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_operator")
	private Long createOperator;

	@Column(name = "last_update_time")
	private Date updateTime;

	@Column(name = "last_update_operator")
	private Long updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	public Long getSubId() {
		return subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public PositionSubType getDirectSub() {
		return directSub;
	}

	public void setDirectSub(PositionSubType directSub) {
		this.directSub = directSub;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}

}
