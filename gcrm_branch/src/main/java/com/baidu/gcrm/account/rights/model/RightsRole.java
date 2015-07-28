package com.baidu.gcrm.account.rights.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.i18n.model.BaseI18nModel;

/**
 * 新权限角色
 * 
 * @author zhanglei35
 * 
 */
@Entity
@Table(name = "g_rights_role")
public class RightsRole extends BaseI18nModel implements BaseOperationModel {

	private static final long serialVersionUID = -5044104372706065933L;

	// 自增长主键
	@Id
	@GeneratedValue
	private Long id;

	// 角色ID
	@Column(name = "role_id")
	private Long roleId;

	// 角色中文名
	@Column(name = "role_desc")
	private String roleDesc;

	// 角色英文名
	@Column(name = "role_tag")
	private String roleTag;

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

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleTag() {
		return roleTag;
	}

	public void setRoleTag(String roleTag) {
		this.roleTag = roleTag;
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
