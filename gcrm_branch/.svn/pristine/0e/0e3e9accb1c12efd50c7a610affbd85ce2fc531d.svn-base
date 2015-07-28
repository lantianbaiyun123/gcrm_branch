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

@Entity
@Table(name = "g_force_publish_proof")
public class ForcePublishProof implements Serializable {

	private static final long serialVersionUID = -4206273057171378229L;

	public enum ProofType {
		/** 线上审批流 */
		online_approval,
		/** 线下邮件或附件*/
		offline_attachment,
		/** 没有凭证 */
		none
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "p_number")
	private String publishNumber;
	
	@Column(name = "record_id")
	private Long recordId;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private ProofType type;
	
	@Column(name = "approval_url")
	private String approvalUrl;
	
	@Column(name = "file_url")
	private String fileUrl;
	
	@Column(name = "upload_file_name")
	private String uploadFilename;
	
	@Column(name = "download_file_name")
	private String downloadFilename;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "operator")
	private Long operatorId;

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

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public ProofType getType() {
		return type;
	}

	public void setType(ProofType type) {
		this.type = type;
	}

	public String getApprovalUrl() {
		return approvalUrl;
	}

	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUploadFilename() {
		return uploadFilename;
	}

	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}

	public String getDownloadFilename() {
		return downloadFilename;
	}

	public void setDownloadFilename(String downloadFilename) {
		this.downloadFilename = downloadFilename;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

}
