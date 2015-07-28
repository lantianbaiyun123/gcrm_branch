package com.baidu.gcrm.account.rights.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

/**
 * 新权限岗位
 * 
 * @author zhanglei35
 * 
 */
@Entity
@Table(name = "g_rights_position")
public class RightsPosition implements BaseOperationModel {

	private static final long serialVersionUID = -5044104372706065933L;

	// 自增长主键
	@Id
	@GeneratedValue
	private Long id;

	// 岗位ID
	@Column(name = "pos_id")
	private Long posId;

	// 岗位中文名
	@Column(name = "pos_name")
	private String posName;

	// 岗位英文名
	@Column(name = "pos_tag")
	private String posTag;

	// 岗位状态（启用、禁用）
	@Column(name = "pos_del_mark")
	private Integer posDelMark;

	// 父岗位，逗号隔开
	@Column(name = "pos_parent_ids")
	private String posParentIds;

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

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosTag() {
		return posTag;
	}

	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}

	public Integer getPosDelMark() {
		return posDelMark;
	}

	public void setPosDelMark(Integer posDelMark) {
		this.posDelMark = posDelMark;
	}

	public String getPosParentIds() {
		return posParentIds;
	}

	public void setPosParentIds(String posParentIds) {
		this.posParentIds = posParentIds;
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
