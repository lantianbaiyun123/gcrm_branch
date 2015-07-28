package com.baidu.gcrm.ad.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

@Entity
@Table(name = "g_advertise_material_apply")
public class AdvertiseMaterialApply implements BaseOperationModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public enum MaterialApplyState {
        // 待提交
        create,
        // 审核中
        submit,
        // 审核通过
        confirm,
        // 已作废
        cancel,
        // 被驳回
        refused;
    }

    @Id
    @GeneratedValue
    @ModifyCheckIgnore
    private Long id;

    @Column(name = "advertise_solution_content_id")
    @ModifyCheckIgnore
    private Long adSolutionContentId;

    @Column(name = "schedule_id")
    @ModifyCheckIgnore
    private Long scheduleId;

    @Column(name = "number")
    @ModifyCheckIgnore
    private String number;

    @Column(name = "apply_state")
    @Enumerated(EnumType.STRING)
    @ModifyCheckIgnore
    private MaterialApplyState applyState;

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

    @Column(name = "material_title")
    @Size(max = 256)
    private String materialTitle;

    @Column(name = "material_url")
    @Size(max = 1024)
    private String materialUrl;

    @Column(name = "task_info")
    @ModifyCheckIgnore
    private String taskInfo;

    @Column(name = "material_file_type")
    private Integer materialFileType;// 0:图片,1:flash;2:代码,null:纯文本链

    @Column(name = "material_embed_code_content")
    @Size(max = 10000)
    private String materialEmbedCodeContent;

    @Column(name = "monitor_url")
    private String monitorUrl;

    /**
     * 物料投放时间段
     */
    @Column(name = "period_description")
    @Size(max = 512)
    private String periodDescription;

    /**
     * 物料下拉菜单列表
     */
    @Transient
    @ModifyCheckIgnore
    private List<AdvertiseMaterialMenu> materialMenuList;

    /**
     * 主物料文件列表
     */
    @Transient
    @ModifyCheckIgnore
    private List<AdvertiseMaterial> materialList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdSolutionContentId() {
        return adSolutionContentId;
    }

    public void setAdSolutionContentId(Long adSolutionContentId) {
        this.adSolutionContentId = adSolutionContentId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public MaterialApplyState getApplyState() {
        return applyState;
    }

    public void setApplyState(MaterialApplyState applyState) {
        this.applyState = applyState;
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

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public Integer getMaterialFileType() {
        return materialFileType;
    }

    public String getMaterialEmbedCodeContent() {
        return materialEmbedCodeContent;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMaterialFileType(Integer materialFileType) {
        this.materialFileType = materialFileType;
    }

    public void setMaterialEmbedCodeContent(String materialEmbedCodeContent) {
        this.materialEmbedCodeContent = materialEmbedCodeContent;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public List<AdvertiseMaterialMenu> getMaterialMenuList() {
        return materialMenuList;
    }

    public List<AdvertiseMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialMenuList(List<AdvertiseMaterialMenu> materialMenuList) {
        this.materialMenuList = materialMenuList;
    }

    public void setMaterialList(List<AdvertiseMaterial> materialList) {
        this.materialList = materialList;
    }

}
