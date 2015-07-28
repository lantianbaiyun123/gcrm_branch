package com.baidu.gcrm.ad.approval.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="g_participant")
public class Participant {
	
	public enum DescriptionType {
		platform,
		regional,
		businessType,
		site;
	}
	
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(name="process_define_id")
	private String processDefineId;
	
	@Column(name="participant_id")
	private String participantId;
	
	private String username;
	
	@Column(name="key_str")
	private String key;
	
	@Enumerated(EnumType.STRING)
	private DescriptionType description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProcessDefineId() {
		return processDefineId;
	}
	public void setProcessDefineId(String processDefineId) {
		this.processDefineId = processDefineId;
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public DescriptionType getDescription() {
		return description;
	}
	public void setDescription(DescriptionType description) {
		this.description = description;
	}
	
}
