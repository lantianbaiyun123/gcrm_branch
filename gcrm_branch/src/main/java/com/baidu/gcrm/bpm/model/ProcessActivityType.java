package com.baidu.gcrm.bpm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_process_activity_type")
public class ProcessActivityType {
	public enum ActivityType {
		approval,
		operation;
	}
	
	public enum ActivitySubtype {
		approval,
		quote,
		schedule,
		material,
		customer,
		onlineApply;
	}
	
	public enum ParamKey {
		adSolutionId,
		quoteId,
		materialId,
		customerId,
		onlineApplyId;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="process_define_id")
	private String processDefId;
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private ActivityType type;
	
	@Column(name="sub_type")
	@Enumerated(EnumType.STRING)
	private ActivitySubtype subtype;
	
	@Column(name="param_key")
	@Enumerated(EnumType.STRING)
	private ParamKey paramKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public ActivitySubtype getSubtype() {
		return subtype;
	}

	public void setSubtype(ActivitySubtype subtype) {
		this.subtype = subtype;
	}

	public ParamKey getParamKey() {
		return paramKey;
	}

	public void setParamKey(ParamKey paramKey) {
		this.paramKey = paramKey;
	}
	
}
