package com.baidu.gcrm.ad.approval.record.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_ad_approval_insert_record")
public class ApprovalInsertRecord implements BaseOperationModel {
	
    private static final long serialVersionUID = 1992124817383334235L;

    @Id
	@GeneratedValue
	private Long id;
    
    @Column(name="approval_record_id")
    private Long approvalRecordId;
    
    @Column(name="adsolution_content_id")
    private Long adsolutionContentId;
    
    @Transient
    private String adSolutionContentNumber;
    
    @Column(name="inserted_adsolution_content_id")
    private Long insertedAdsolutionContentId;
    
    @Transient
    private String insertedAdvertiser;
    
    @Column(name="insert_period")
    private String insertPeriod;
    
    @Column(name="allow_insert")
    private Integer allowInsert;
    
    @Column(name="is_history")
    private Integer isHistory;
	
	@Column(name="create_time")
	private Date createTime;
	
	@Column(name="create_operator")
	private Long createOperator;
	
	@Transient
	private Date updateTime;
	
	@Transient
	private Long updateOperator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public Long getApprovalRecordId() {
        return approvalRecordId;
    }

    public void setApprovalRecordId(Long approvalRecordId) {
        this.approvalRecordId = approvalRecordId;
    }

    public Long getAdsolutionContentId() {
        return adsolutionContentId;
    }

    public void setAdsolutionContentId(Long adsolutionContentId) {
        this.adsolutionContentId = adsolutionContentId;
    }

    public Long getInsertedAdsolutionContentId() {
        return insertedAdsolutionContentId;
    }

    public void setInsertedAdsolutionContentId(Long insertedAdsolutionContentId) {
        this.insertedAdsolutionContentId = insertedAdsolutionContentId;
    }

    public String getInsertPeriod() {
        return insertPeriod;
    }

    public void setInsertPeriod(String insertPeriod) {
        this.insertPeriod = insertPeriod;
    }

    public Integer getAllowInsert() {
        return allowInsert;
    }

    public void setAllowInsert(Integer allowInsert) {
        this.allowInsert = allowInsert;
    }

    public String getInsertedAdvertiser() {
        return insertedAdvertiser;
    }

    public void setInsertedAdvertiser(String insertedAdvertiser) {
        this.insertedAdvertiser = insertedAdvertiser;
    }

    public String getAdSolutionContentNumber() {
        return adSolutionContentNumber;
    }

    public void setAdSolutionContentNumber(String adSolutionContentNumber) {
        this.adSolutionContentNumber = adSolutionContentNumber;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }
	
}
