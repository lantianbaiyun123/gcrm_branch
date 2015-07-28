package com.baidu.gcrm.contact.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_contact_person")
public class ContactPerson implements BaseOperationModel{
	
	
	private static final long serialVersionUID = -4593841666915785561L;
	
	public enum LegalPersonStatus {
		DISABLE {
            @Override
            public int getWeight() {
                return 1;
            }
        },
		ENABLE {
            @Override
            public int getWeight() {
                return 0;
            }
        };
		public abstract int getWeight();
	}
	
	public enum DecisionMakerStatus {
		DISABLE {
            @Override
            public int getWeight() {
                return 2;
            }
        },
		ENABLE {
            @Override
            public int getWeight() {
                return 0;
            }
        };
	      public abstract int getWeight();
	}

	public enum GenderClassify {
	    male,
	    female,
	    others;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="customer_number")
	private Long customerNumber;
	
	@Column
	private String name;
	
	@Column
@Enumerated(EnumType.ORDINAL)
	private GenderClassify gender;
	
	@Column(name="position_name")
	private String positionName;
	
	@Column(name="superiors_position")
	private String superiorPosition;
	
	@Column
	private String department;
	
	@Column
	private String mobile;
	
	@Column
	private String telephone;
	
	@Column
	private String email;
	
	@Column(name="is_business_entity")
	@Enumerated(EnumType.ORDINAL)
	private LegalPersonStatus isLegalPerson = LegalPersonStatus.DISABLE;
	
	@Column(name="is_decision_maker")
	@Enumerated(EnumType.ORDINAL)
	private DecisionMakerStatus isDecisionMaker = DecisionMakerStatus.DISABLE;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	
	@Column(name="last_update_time")
	private Date updateTime;
	
	@Column(name="last_update_operator")
	private Long updateOperator;
	
	@Transient
	private boolean noNeedSave = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Long customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GenderClassify getGender() {
		return gender;
	}

	public void setGender(GenderClassify gender) {
		this.gender = gender;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getSuperiorPosition() {
		return superiorPosition;
	}

	public void setSuperiorPosition(String superiorPosition) {
		this.superiorPosition = superiorPosition;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LegalPersonStatus getIsLegalPerson() {
		return isLegalPerson;
	}

	public void setIsLegalPerson(LegalPersonStatus isLegalPerson) {
		this.isLegalPerson = isLegalPerson;
	}

	public DecisionMakerStatus getIsDecisionMaker() {
		return isDecisionMaker;
	}

	public void setIsDecisionMaker(DecisionMakerStatus isDecisionMaker) {
		this.isDecisionMaker = isDecisionMaker;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		
	}

	@Override
	public Long getCreateOperator() {
		return createOperator;
	}

	@Override
	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
		
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		
	}

	@Override
	public Long getUpdateOperator() {
		return updateOperator;
	}

	@Override
	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
		
	}

	public boolean isNoNeedSave() {
		return noNeedSave;
	}

	public void setNoNeedSave(boolean noNeedSave) {
		this.noNeedSave = noNeedSave;
	}
	
	

}
