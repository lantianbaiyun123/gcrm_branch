package com.baidu.gcrm.data.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PositionBean {
	@Id
	private Long id;
	
	@Column(name = "position_number")
	private String positionNumber;
	
	private Integer status;
	
	private Integer type;
	
	@Column(name = "rotation_type")
	private Integer rotationType;
	
    @Column(name = "sales_amount")
    private Integer rotationCount;
	   
	@Column(name = "material_type")
	private Integer materialType;
	
	@Column(name = "area_required")
	private String areaRequired;
	
	private String size;
	
	@Column(name = "index_str")
	private String hierarchy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRotationType() {
		return rotationType;
	}

	public void setRotationType(Integer rotationType) {
		this.rotationType = rotationType;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

	public String getAreaRequired() {
		return areaRequired;
	}

	public void setAreaRequired(String areaRequired) {
		this.areaRequired = areaRequired;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

    public Integer getRotationCount() {
        return rotationCount;
    }

    public void setRotationCount(Integer rotationCount) {
        this.rotationCount = rotationCount;
    }
	
}
