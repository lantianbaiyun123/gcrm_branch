package com.baidu.gcrm.ad.material.vo;

import java.io.Serializable;


public class MaterialFileInfo implements Serializable,Cloneable {
   
    private static final long serialVersionUID = -8837329499198470543L;

    private String fileUrl;

    private String uploadFileName;

    private String downloadFileName;

    private Integer picWidth;

    private Integer picHeight;

    private Integer fileSize;
    
    private String downloadUrl;


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public Integer getPicWidth() {
        return picWidth;
    }

    public Integer getPicHeight() {
        return picHeight;
    }

    public Integer getFileSize() {
        return fileSize;
    }


    public Long getUpdateOperator() {
        return updateOperator;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public void setPicWidth(Integer picWidth) {
        this.picWidth = picWidth;
    }

    public void setPicHeight(Integer picHeight) {
        this.picHeight = picHeight;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }


    public void setUpdateOperator(Long updateOperator) {
        this.updateOperator = updateOperator;
    }

    private Long updateOperator;
}
