package com.baidu.gcrm.publish.web.vo;

public class PublishProofVO {
	private Long id;
	private String approvalUrl;
	private String downloadFileUrl;
	private String downloadFilename;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApprovalUrl() {
		return approvalUrl;
	}
	public void setApprovalUrl(String approvalUrl) {
		this.approvalUrl = approvalUrl;
	}
	public String getDownloadFilename() {
		return downloadFilename;
	}
	public void setDownloadFilename(String downloadFilename) {
		this.downloadFilename = downloadFilename;
	}
	public String getDownloadFileUrl() {
		return downloadFileUrl;
	}
	public void setDownloadFileUrl(String downloadFileUrl) {
		this.downloadFileUrl = downloadFileUrl;
	}
}
