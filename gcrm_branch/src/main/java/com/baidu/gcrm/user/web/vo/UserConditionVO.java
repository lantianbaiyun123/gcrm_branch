package com.baidu.gcrm.user.web.vo;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.user.web.helper.UserListBean;

/**
 * 用户列表查询条件VO
 * @author zhanglei35
 *
 */
public class UserConditionVO extends Page<UserListBean> {
	private static final long serialVersionUID = 737015540810943962L;

	private String realName;

	private String email;

	private String ucName;

	private Long roleId;

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

	public String getUcName() {
		return ucName;
	}

	public void setUcName(String ucName) {
		this.ucName = ucName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public Class<UserListBean> getResultClass() {
		return UserListBean.class;
	}
}
