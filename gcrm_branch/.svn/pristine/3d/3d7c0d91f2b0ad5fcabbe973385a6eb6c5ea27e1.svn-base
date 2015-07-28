package com.baidu.gcrm.ad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.baidu.gcrm.ad.material.vo.MaterialFileInfo;
import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

@Entity
@Table(name = "g_advertise_material_menu")
public class AdvertiseMaterialMenu implements BaseOperationModel, Cloneable {

    private static final long serialVersionUID = -262690147684421094L;

    @Id
    @GeneratedValue
    @ModifyCheckIgnore
    private Long id;

    @Column(name = "material_apply_id")
    @ModifyCheckIgnore
    private Long materialApplyId;

    @Column(name = "material_title")
    @Size(max = 256)
    private String materialTitle;

    @Column(name = "material_url")
    @Size(max = 1024)
    private String materialUrl;

    @Column(name = "file_url")
    @ModifyCheckIgnore
    private String fileUrl;

    @Column(name = "upload_file_name")
    private String uploadFileName;

    @Column(name = "download_file_name")
    private String downloadFileName;

    @Column(name = "pic_width")
    private Integer picWidth;

    @Column(name = "pic_height")
    private Integer picHeight;

    @Column(name = "file_size")
    private Integer fileSize;

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
    MultipartFile materialFile;

    @Transient
    @ModifyCheckIgnore
    private MaterialFileInfo material = new MaterialFileInfo();

    @Transient
    @ModifyCheckIgnore
    private String downloadUrl;
    
    @Transient
    private String fileDownloadUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialApplyId() {
        return materialApplyId;
    }

    public void setMaterialApplyId(Long materialApplyId) {
        this.materialApplyId = materialApplyId;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
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

    public MultipartFile getMaterialFile() {
        return materialFile;
    }

    public void setMaterialFile(MultipartFile materialFile) {
        this.materialFile = materialFile;
    }

    public MaterialFileInfo getMaterial() {
        material.setDownloadFileName(downloadFileName);
        material.setUploadFileName(uploadFileName);
        material.setPicHeight(picHeight);
        material.setPicWidth(picWidth);
        material.setFileUrl(fileUrl);
        material.setFileSize(fileSize);
        material.setDownloadUrl(downloadUrl);
        return material;
    }

    public void setMaterial(MaterialFileInfo material) {
        this.material = material;
        this.fileSize = material.getFileSize();
        this.picWidth = material.getPicWidth();
        this.picHeight = material.getPicHeight();
        this.downloadFileName = material.getDownloadFileName();
        this.downloadUrl = material.getDownloadUrl();
        this.fileUrl = material.getFileUrl();
        this.uploadFileName = material.getUploadFileName();

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

    public String getDownloadUrl() {
        return downloadUrl;
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

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }
}
