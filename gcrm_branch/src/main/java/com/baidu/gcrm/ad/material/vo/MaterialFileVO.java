package com.baidu.gcrm.ad.material.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.baidu.gcrm.ad.model.AdvertiseMaterial.MaterialFileType;

@Entity
public class MaterialFileVO {
	@Id
	private Long id;
	
	@Column(name = "advertise_solution_content_id")
	private Long adContentId;
	
	@Column(name = "material_file_type")
	private MaterialFileType materialFileType;
	
	@Column(name = "file_url")
	private String fileUrl;
	
	@Column(name = "download_file_name")
	private String filename;
	
	@Column(name = "upload_file_name")
	private String uploadFileName;

	public Long getAdContentId() {
		return adContentId;
	}

	public void setAdContentId(Long adContentId) {
		this.adContentId = adContentId;
	}

	public MaterialFileType getMaterialFileType() {
		return materialFileType;
	}

	public void setMaterialFileType(MaterialFileType materialFileType) {
		this.materialFileType = materialFileType;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	
}
