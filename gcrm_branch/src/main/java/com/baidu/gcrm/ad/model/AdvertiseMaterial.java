package com.baidu.gcrm.ad.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

@Entity
@Table(name = "g_advertise_material")
public class AdvertiseMaterial implements BaseOperationModel, Cloneable {

    private static final long serialVersionUID = 2160954061409457791L;

    public enum MaterialFileType {
        /** 物料图片 */
        IMAGE,
        /** 物料嵌入代码 */
        EMBED_CODE;

        public static MaterialFileType valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            MaterialFileType[] values = MaterialFileType.values();
            for (MaterialFileType val : values) {
                if (val.ordinal() == value) {
                    return val;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue
    @ModifyCheckIgnore
    private Long id;

    @Column(name = "advertise_solution_content_id")
    @ModifyCheckIgnore
    private Long adSolutionContentId;

    @Column(name = "file_url")
    @ModifyCheckIgnore
    private String fileUrl;

    @Transient
    private String fileDownloadUrl;

    @Transient
    @ModifyCheckIgnore
    MultipartFile materialFile;

    @Column(name = "upload_file_name")
    private String uploadFileName;

    @Column(name = "download_file_name")
    @ModifyCheckIgnore
    private String downloadFileName;

    @Transient
    @ModifyCheckIgnore
    private String downloadUrl;

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

    @Column(name = "material_apply_id")
    @ModifyCheckIgnore
    private Long materialApplyId;

    @Column(name = "material_file_type")
    @Enumerated(EnumType.ORDINAL)
    @ModifyCheckIgnore
    private MaterialFileType materialFileType;

    @Column(name = "pic_width")
    private Integer picWidth;

    @Column(name = "pic_height")
    private Integer picHeight;

    @Column(name = "file_size")
    private Integer fileSize;

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

    public Long getAdSolutionContentId() {
        return adSolutionContentId;
    }

    public void setAdSolutionContentId(Long adSolutionContentId) {
        this.adSolutionContentId = adSolutionContentId;
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

    public Long getMaterialApplyId() {
        return materialApplyId;
    }

    public void setMaterialApplyId(Long materialApplyId) {
        this.materialApplyId = materialApplyId;
    }

    public MaterialFileType getMaterialFileType() {
        return materialFileType;
    }

    public void setMaterialFileType(MaterialFileType materialFileType) {
        this.materialFileType = materialFileType;
    }

    public AdvertiseMaterial clone() {
        AdvertiseMaterial o = null;
        try {
            o = (AdvertiseMaterial) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getDownloadUrl() {
        return downloadUrl;
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

    public Integer getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(Integer picWidth) {
        this.picWidth = picWidth;
    }

    public Integer getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(Integer picHeight) {
        this.picHeight = picHeight;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

}
