package com.baidu.gcrm.bpm.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class ProcessQueryConditionBean {
	private String processDefineId;
	
	private String packageId;
	
	private List<Integer> processState;
	
	private String foreignKey;
	
	private String foreignName;
	
	private String processName;

	public String[] getProperties() {
		List<String> properties = new ArrayList<String>();
		
		if (StringUtils.isNotEmpty(foreignKey)) {
			properties.add("foreignKey ='" + foreignKey + "'");
		} 
		if (StringUtils.isNotEmpty(foreignName)) {
			properties.add("foreignName ='" + foreignName + "'");
		}
		if (StringUtils.isNotEmpty(processDefineId)) {
			properties.add("processDefineId ='" + processDefineId + "'");
		}
		if(StringUtils.isNotBlank(packageId)){
			properties.add("packageId = '" + packageId + "'");
		}
		if(StringUtils.isNotBlank(processName)){
			properties.add("processName = '%" + processName + "'");
		}
		List<Integer> processStates = this.getProcessState();
		if (CollectionUtils.isNotEmpty(processStates)) {
			properties.add("processState in (" + StringUtils.join(processStates, ",") + ")");
		} else {
			properties.add("processState in (0,1,2,3)");
		}
		String[] props = new String[properties.size()];
		return properties.toArray(props);
	}
	
	public String getProcessDefineId() {
		return processDefineId;
	}

	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public List<Integer> getProcessState() {
		return processState;
	}

	public void setProcessState(List<Integer> processState) {
		this.processState = processState;
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

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
}
