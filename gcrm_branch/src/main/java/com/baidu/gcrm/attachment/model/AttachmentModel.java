package com.baidu.gcrm.attachment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "g_attachment_modules")
public class AttachmentModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "transaction_record_id")
    private Long transactionRecordId;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private AttachmentAllType type;

    private String name;

    private String url;

    @Column(name = "upload_file_name")
    private String tempUrl;
    @Column(name = "module_name")
    @Enumerated(EnumType.STRING)
    private ModuleNameWithAtta moduleName;
    @Transient
    private String message;// 上传文件时返回客户端的消息

    @Transient
    private String fieldName;

    @Transient
    private byte[] bytes;

    @Transient
    private boolean delete =false;
    
    @Transient
    private String downLoadUrl;


    public enum AttachmentAllType {
        type1,
        type2,
        type3;//not used, can add here
    }
    

    public enum ModuleNameWithAtta{
        adcontentonlineapply;
    }
    
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Long getTransactionRecordId() {
		return transactionRecordId;
	}

	public void setTransactionRecordId(Long transactionRecordId) {
		this.transactionRecordId = transactionRecordId;
	}

	public AttachmentAllType getType() {
		return type;
	}

	public void setType(AttachmentAllType type) {
		this.type = type;
	}

	public ModuleNameWithAtta getModuleName() {
		return moduleName;
	}

	public void setModuleName(ModuleNameWithAtta moduleName) {
		this.moduleName = moduleName;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getTempUrl() {
        return tempUrl;
    }

    public void setTempUrl(String tempUrl) {
        this.tempUrl = tempUrl;
    }

    public String getDownLoadUrl() {
         return downLoadUrl;
    }

    public void setDownLoadUrl(String contextPath,String requestLocal) {
        StringBuilder requestUrlStr = new StringBuilder();
        requestUrlStr.append(contextPath).append(requestLocal).append("/").append(this.id);
        this.downLoadUrl = requestUrlStr.toString();
    }
    
    
}

