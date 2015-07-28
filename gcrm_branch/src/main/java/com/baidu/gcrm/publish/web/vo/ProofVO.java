package com.baidu.gcrm.publish.web.vo;

import org.springframework.web.multipart.MultipartFile;

import com.baidu.gcrm.publish.model.ForcePublishProof.ProofType;


public class ProofVO {
	private String fileUrl;
	
	private String uploadFilename;
	
	private String downloadFilename;
	
	private String approvalUrl;
	
	private ProofType type;
	
	MultipartFile file;
	
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

	public String getApprovalUrl() {
		return approvalUrl;
	}

	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}

	public ProofType getType() {
		return type;
	}

	public void setType(ProofType type) {
		this.type = type;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
