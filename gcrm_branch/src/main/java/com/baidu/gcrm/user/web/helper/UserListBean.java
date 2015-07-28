package com.baidu.gcrm.user.web.helper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 用户列表bean
 * 
 * @author zhanglei35
 * 
 */
@Entity
public class UserListBean {

	@Id
	private Integer id;

	@Column(name = "ucid")
	private Long ucId;
	
	@Column(name = "username")
	private String ucName;

	@Column(name = "realname")
	private String realName;

	@Column(name = "email")
	private String email;

	@Transient
	private String roleName;

	@Column(name = "status")
	private String status;

	@Transient
	private boolean hasDataRights;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getUcId() {
		return ucId;
	}

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

	public String getUcName() {
		return ucName;
	}

	public void setUcName(String ucName) {
		this.ucName = ucName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isHasDataRights() {
		return hasDataRights;
	}

	public void setHasDataRights(boolean hasDataRights) {
		this.hasDataRights = hasDataRights;
	}

}
