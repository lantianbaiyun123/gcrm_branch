package com.baidu.gcrm.bpm.web.helper;

import java.util.ArrayList;
import java.util.List;

import com.baidu.gcrm.common.ServiceBeanFactory;

public abstract class StartProcessBean {

	private String processName;

	private String packageId;

	private String processDefineId;

	private String startUser;
	
	private String foreignKey;
	
	private List<ParticipantBean> participants;

	public abstract void validate();

	public StartProcessBean() {
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		try {
			List<String> userNames = new ArrayList<String>();
			userNames.add(startUser);
			userNames = ServiceBeanFactory.getUserService().findUuapNameByName(userNames);
			if(userNames != null && userNames.size()>0){
				startUser =userNames.get(0);
			}
		} catch (Exception e) {
		}
		this.startUser = startUser;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public List<ParticipantBean> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantBean> participants) {
		this.participants = participants;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	@Override
    public String toString() {
	    return "StartProcessBean [processName=" + processName + ", packageId="
	            + packageId + ", processDefineId=" + processDefineId
	            + ", startUser=" + startUser + "]";
    }
}
