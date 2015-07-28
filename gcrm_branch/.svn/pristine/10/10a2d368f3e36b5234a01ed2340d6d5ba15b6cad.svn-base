package com.baidu.gcrm.materials.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "g_attachment")
public class Attachment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "customer_number")
    private Long customerNumber;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private AttachmentType type;

    private String name;

    private String url;

    @Column(name = "upload_file_name")
    private String tempUrl;

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

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
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
