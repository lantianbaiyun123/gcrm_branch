package com.baidu.gcrm.account.rights.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

/**
 * 用户与角色关系
 * 
 * @author zhanglei35
 * 
 */
@Entity
@Table(name = "g_rights_user_role")
public class RightsUserRole implements BaseOperationModel {

	private static final long serialVersionUID = -5044104372706065933L;

	// 自增长主键
	@Id
	@GeneratedValue
	private Long id;

	// 用户UC ID
	@Column(name = "uc_id")
	private Long ucId;

	// 角色ID
	@Column(name = "role_id")
	private Long roleId;

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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
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
