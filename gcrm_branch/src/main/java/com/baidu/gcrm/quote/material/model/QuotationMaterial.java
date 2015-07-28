package com.baidu.gcrm.quote.material.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.baidu.gcrm.log.model.ModifyCheckIgnore;
import com.baidu.gcrm.common.model.BaseOperationModel;

@Entity
@Table(name = "g_quotation_material")
public class QuotationMaterial implements BaseOperationModel,Cloneable {
	private static final long serialVersionUID = -2288042250685389273L;

	@Id
	@GeneratedValue
	@ModifyCheckIgnore
	private Long id;
	
    @Column(name = "quotation_main_id")
    @ModifyCheckIgnore
    private Long quotationMainId;
    
    @Column(name = "file_url")
    @ModifyCheckIgnore
    private String fileUrl;
    
    @Transient
    @ModifyCheckIgnore
    MultipartFile materialFile;
    
    @Column(name = "upload_file_name")
    private String uploadFileName;
    
    @Column(name = "download_file_name")
    @ModifyCheckIgnore
    private String downloadFileName;
	
	@Column(name = "create_time")
	@ModifyCheckIgnore
	private Date createTime;
	
	@Column(name = "create_operator")
	@ModifyCheckIgnore
	private Long createOperator;
	
	@Column(name = "last_update_time")
	@ModifyCheckIgnore
	private Date updateTime;
	
	@Column(name = "last_update_operator")
	@ModifyCheckIgnore
	private Long updateOperator;

	@Transient
    @ModifyCheckIgnore
    String downLoadUrl;
	
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

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public MultipartFile getMaterialFile() {
        return materialFile;
    }

    public void setMaterialFile(MultipartFile materialFile) {
        this.materialFile = materialFile;
    }

	public Long getQuotationMainId() {
		return quotationMainId;
	}

	public void setQuotationMainId(Long quotationMainId) {
		this.quotationMainId = quotationMainId;
	}
	
	public QuotationMaterial clone() {
		QuotationMaterial o = null;
		try {
			o = (QuotationMaterial) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	} 
}
