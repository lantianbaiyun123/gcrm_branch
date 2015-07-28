package com.baidu.gcrm.publish.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "g_publish_record")
public class PublishRecord implements Serializable {

	private static final long serialVersionUID = 3347020094076415715L;

	public enum OperateType {
		publish,
		unpublish,
		material_update,
		terminate,
		force_publish
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "p_number")
	private String publishNumber;
	
	private Date date;
	
	@Column(name = "m_number")
	private String materialNumber;
	
	@Enumerated(EnumType.STRING)
	private OperateType type;
	
	private Long operator;
	@Transient
	private String operatorName;
	
	@Column(name = "plan_date")
	private Date planDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublishNumber() {
		return publishNumber;
	}

	public void setPublishNumber(String publishNumber) {
		this.publishNumber = publishNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public OperateType getType() {
		return type;
	}

	public void setType(OperateType type) {
		this.type = type;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
	
}
