package com.baidu.gcrm.data.bean;

public class MaterialAttachment {
	private String url;
	
	private String filename;
	
	public MaterialAttachment() {
		super();
	}

	
	public MaterialAttachment(String url, String filename) {
		super();
		this.url = url;
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
