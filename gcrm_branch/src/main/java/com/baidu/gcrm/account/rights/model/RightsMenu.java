package com.baidu.gcrm.account.rights.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

/**
 * 新权限菜单
 * 
 * @author zhanglei35
 * 
 */
@Entity
@Table(name = "g_rights_menu")
public class RightsMenu implements BaseOperationModel {

	private static final long serialVersionUID = -5044104372706065933L;

	// 自增长主键
	@Id
	@GeneratedValue
	private Long id;

	// 菜单ID
	@Column(name = "menu_id")
	private Long menuId;

	// 菜单名(唯一标识)
	@Column(name = "menu_name")
	private String menuName;

	// 菜单URL
	@Column(name = "menu_url")
	private String menuURL;

	// 功能英文名
	@Column(name = "func_tag")
	private String funcTag;

	// 父菜单ID
	@Column(name = "parent_menu_id")
	private Long parentMenuId;

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

	public String getFuncTag() {
		return funcTag;
	}

	public void setFuncTag(String funcTag) {
		this.funcTag = funcTag;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuURL() {
		return menuURL;
	}

	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}

	public Long getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
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
