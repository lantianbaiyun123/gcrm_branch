package com.baidu.gcrm.publish.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_publish")
public class Publish implements BaseOperationModel {
	
	private static final long serialVersionUID = -1642359690500080137L;

	public enum PublishStatus {
		/**	上线中 */
		publishing,
		/**   未上线 */
        unpublish,
		/**	上线下线完成 */
		publish_finish,
		/**	终止 */
		terminate,
		/**	释放导致全部下线 */
		release;
		
		public static PublishStatus valueOf(Integer value) {
		    PublishStatus[] publishStatusValues = PublishStatus.values();
		    for (PublishStatus temPublishStatus : publishStatusValues) {
		        if (temPublishStatus.ordinal() == value.intValue()) {
		            return temPublishStatus;
		        }
		    }
		    
		    return null;
		}
	}
	
	public enum PublishType {
		/**	正常上下线 */
		normal,
		/**	物料审核通过，触发上线 */
		material,
		/**	强制上下线 */
		force
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * 排期单编号，10位随机数字
	 */
	@Column(name = "number")
	private String number;
	
	@Column(name = "online_number")
	private String onlineNumber;
	
	@Column(name = "position_id")
	private Long positionId;
	
	@Column(name = "ad_content_id")
	private Long adContentId;
	
	@Column(name = "as_number")
	private String adSolutionNumber;
	
	@Column(name = "ac_number")
	private String adContentNumber;
	
	@Column(name = "s_number")
	private String scheduleNumber;
	
	@Column(name = "m_number")
	private String materialNumber;
	
	@Column(name = "pos_number")
    private String positionNumber;
	
	@Column(name = "c_number")
    private String customerNumber;
	
	@Enumerated(EnumType.ORDINAL)
	private PublishStatus status;
	
	@Enumerated(EnumType.STRING)
	private PublishType type;
	
	/**
	 * 1表示新上线码，0表示旧上线码
	 */
	private Integer property = Integer.valueOf(1);
	
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "create_operator")
	private Long createOperator;

	@Column(name = "last_update_time")
	private Date updateTime;

	@Column(name = "last_update_operator")
	private Long updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOnlineNumber() {
		return onlineNumber;
	}

	public void setOnlineNumber(String onlineNumber) {
		this.onlineNumber = onlineNumber;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	public String getAdSolutionNumber() {
		return adSolutionNumber;
	}

	public void setAdSolutionNumber(String adSolutionNumber) {
		this.adSolutionNumber = adSolutionNumber;
	}

	public String getAdContentNumber() {
		return adContentNumber;
	}

	public void setAdContentNumber(String adContentNumber) {
		this.adContentNumber = adContentNumber;
	}

	public String getScheduleNumber() {
		return scheduleNumber;
	}

	public void setScheduleNumber(String scheduleNumber) {
		this.scheduleNumber = scheduleNumber;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public PublishStatus getStatus() {
		return status;
	}

	public void setStatus(PublishStatus status) {
		this.status = status;
	}

	public PublishType getType() {
		return type;
	}

	public void setType(PublishType type) {
		this.type = type;
	}

	public Integer getProperty() {
		return property;
	}

	public void setProperty(Integer property) {
		this.property = property;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(Long updateOperator) {
		this.updateOperator = updateOperator;
	}

	@Override
	public String toString() {
		return "Publish [number=" + number + ", adContentNumber=" + adContentNumber + ", scheduleNumber="
				+ scheduleNumber + ", materialNumber=" + materialNumber + ", positionNumber=" + positionNumber
				+ ", status=" + status + ", type=" + type + ", updateTime=" + updateTime + ", updateOperator="
				+ updateOperator + "]";
	}
	
}
