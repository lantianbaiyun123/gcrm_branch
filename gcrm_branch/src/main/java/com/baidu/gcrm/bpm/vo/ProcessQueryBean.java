package com.baidu.gcrm.bpm.vo;

import com.baidu.gcrm.bpm.web.helper.ProcessStatus;


public class ProcessQueryBean {
	/** 包id */
	private String packageId;

	/** 流程id */
	private String processId;

	/** 流程名称 */
	private String processName;
	
	/** 流程发起时间 */
	private String startTime;
	
	/** 流程发起者 */
	private String createUser;
	
	/** 流程发起者 */
	private String createUserRealName;
	
	private String foreignKey;
	
	private String foreignName;
	
	private ProcessStatus status;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserRealName() {
		return createUserRealName;
	}

	public void setCreateUserRealName(String createUserRealName) {
		this.createUserRealName = createUserRealName;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getForeignName() {
		return foreignName;
	}

	public void setForeignName(String foreignName) {
		this.foreignName = foreignName;
	}

	public ProcessStatus getStatus() {
		return status;
	}

	public void setStatus(ProcessStatus status) {
		this.status = status;
	}
	
}
