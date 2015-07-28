package com.baidu.gcrm.account.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_account")
public class Account implements BaseOperationModel {
	private static final long serialVersionUID = -6008340973116350737L;

	public enum AccountType {
		INNER, OUTER, AGENT;
	}

	public enum AccountStatus {
		// 停用
		DISABLE,
		// 启用
		ENABLE,
		// 未验证邮箱
		UNVERIFY, 
		// 已验证邮箱
		VERIFIED;

		public static AccountStatus valueOf(Integer value) {
			if (value == null) {
				return null;
			}
			AccountStatus[] values = AccountStatus.values();
			for (AccountStatus accountStatus : values) {
				if (accountStatus.ordinal() == value) {
					return accountStatus;
				}
			}
			return null;
		}
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "customer_number")
	private Long customerNumber;

	@Column
	private Long ucid;

	@Column
	private String name;

	@Transient
	private String pwd;

	@Column(name = "utype")
	@Enumerated(EnumType.STRING)
	private AccountType type;

	@Column
	private String email;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private AccountStatus status = AccountStatus.ENABLE;

	@Column(name = "verify_code")
	private String verifyCode;

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

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
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
