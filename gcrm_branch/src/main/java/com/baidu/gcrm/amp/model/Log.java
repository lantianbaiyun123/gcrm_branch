package com.baidu.gcrm.amp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_logs")
public class Log implements java.io.Serializable {
	

	private static final long serialVersionUID = 4742081202462952867L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column
	private String content;
	
	@Column
	private String ip;
	
	@Column(name="operate_time")
	private Date operateTime;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}


}

