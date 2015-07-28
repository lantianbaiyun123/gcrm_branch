package com.baidu.gcrm.publish.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "g_publish_owner")
public class PublishOwner implements Serializable {

	private static final long serialVersionUID = 2038282473850494299L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "position_id")
	private Long positionId;
	
	private String owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
