package com.baidu.gcrm.ad.content.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import com.baidu.gcrm.common.model.BaseOperationModel;




@Entity
@Table(name = "g_adcontent_cancel_record")
public  class AdContentCancelRecord implements BaseOperationModel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2738359292086682382L;

	public enum CancelReason {
		/** 合作终止 */
		end,
		/** 竞价后修改 */
		modify,
		/** 主动作废 */
		cancel,
		/** 变更 */
		change,
		/**方案变更*/
		adChange,
		/**位置禁用*/
		disable_position;
		
		
		public static CancelReason valueOf(Integer value){
			if(value == null){
				return null;
			}
			CancelReason[] values = CancelReason.values(); 
			for(CancelReason reason : values){
				if(reason.ordinal() == value){
					return reason;
				}
			}
			return null;
		}
	}

	
	@Id
	@GeneratedValue
	private Long id;
	
	
	/**
	 * 所属广告内容id
	 */
	@Column(name = "advertise_solution_content_id")
	private Long adSolutionContentId;
	
	
	/**
	 * 所属广告方案id
	 */
	@Column(name = "advertise_solution_id")
	private Long adSolutionId;
	
	/**
	 * 广告主名称
	 */
	@Column(name = "advertisers")
	@Size(max = 128)
	private String advertiser;
	/**
	 * 广告内容编号
	 */
	@Column(name="ad_number")
	private String adNumber;
	
	
	@Column(name = "cancel_reason")
	@Enumerated(EnumType.STRING)
	private CancelReason cancelReason;
	
	@Column(name="create_time")
	
	private Date createTime;
	
	@Column(name="create_operator")
	
	private Long createOperator;
	
	@Transient
	private String Operator;
	

	@Column(name="submit_time")
	
	private Date submitTime;
	
	@Column(name="cancel_time")

	private Date cancelTime;
	
	@Column(name="last_update_time")

	private Date updateTime;
	
	@Column(name="last_update_operator")

	private Long updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdSolutionContentId() {
		return adSolutionContentId;
	}

	public void setAdSolutionContentId(Long adSolutionContentId) {
		this.adSolutionContentId = adSolutionContentId;
	}

	public Long getAdSolutionId() {
		return adSolutionId;
	}

	public void setAdSolutionId(Long adSolutionId) {
		this.adSolutionId = adSolutionId;
	}

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
	}

	public String getAdNumber() {
		return adNumber;
	}

	public void setAdNumber(String adNumber) {
		this.adNumber = adNumber;
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

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
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


	public String getOperator() {
		return Operator;
	}

	public void setOperator(String operator) {
		Operator = operator;
	}

	public CancelReason getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(CancelReason cancelReason) {
		this.cancelReason = cancelReason;
	}


}