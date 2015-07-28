package com.baidu.gcrm.bpm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.LocaleConstants;

@Entity
@Table(name = "g_activity_name_i18n")
public class ActivityNameI18n {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="process_define_id")
	private String processDefId;
	
	@Column(name="activity_id")
	private String activityId;
	
	@Column(name="activity_name")
	private String activityName;
	
	@Column
    @Enumerated(EnumType.STRING)
	private LocaleConstants locale;

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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public LocaleConstants getLocale() {
		return locale;
	}

	public void setLocale(LocaleConstants locale) {
		this.locale = locale;
	}
	
}
