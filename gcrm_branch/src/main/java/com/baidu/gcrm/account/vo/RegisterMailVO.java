package com.baidu.gcrm.account.vo;

import java.util.Set;

public class RegisterMailVO {
	// 收件人
	private Set<String> sendTo;
	// 抄送人
	private Set<String> cc;
	// 操作人
	private String operator;
	// 帐号名
	private String username;
	// 默认密码
	private String password;
	// 广告投放数据平台网址
	private String url;
	// 　邮箱验证码
	private String verifyCode;

	public Set<String> getSendTo() {
		return sendTo;
	}

	public void setSendTo(Set<String> sendTo) {
		this.sendTo = sendTo;
	}

	public Set<String> getCc() {
		return cc;
	}

	public void setCc(Set<String> cc) {
		this.cc = cc;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
