package com.baidu.gcrm.ad.content.model;

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

import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.log.model.ModifyCheckIgnore;

/**
 * 广告内容表，广告方案与广告内容一对多
 * 
 */
@Entity
@Table(name = "g_advertise_solution_content")
public class AdSolutionContent implements BaseOperationModel {

    private static final long serialVersionUID = 2254793751718049915L;

    /**
     * 审批状态
     * 
     */
    public enum ApprovalStatus {
        /** 待提交 */
        saving,
        /** 审核驳回 */
        refused,
        /** 审核中 */
        approving,
        /** 审核完成 */
        approved,
        /** 已生效 */
        effective,
        /** 已作废 */
        cancel;

        public static ApprovalStatus valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            ApprovalStatus[] values = ApprovalStatus.values();
            for (ApprovalStatus status : values) {
                if (status.ordinal() == value) {
                    return status;
                }
            }
            return null;
        }
    }

    public enum ContentType {
        /** 创建 */
        create,
        /** 修改 */
        update;

        public static ContentType valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            ContentType[] values = ContentType.values();
            for (ContentType type : values) {
                if (type.ordinal() == value) {
                    return type;
                }
            }
            return null;
        }

    }

    @Id
    @GeneratedValue
    @ModifyCheckIgnore
    private Long id;

    /**
     * 所属广告方案id
     */
    @Column(name = "advertise_solution_id")
    @ModifyCheckIgnore
    private Long adSolutionId;

    /**
     * 广告主名称
     */
    @Column(name = "advertisers")
    @Size(max = 128)
    private String advertiser;

    @Column(name = "advertiser_id")
    private Long advertiserId;

    @Column(name = "description")
    @Size(max = 512)
    private String description;

    /**
     * 广告方案审批状态
     */
    @Column(name = "approval_status")
    @Enumerated(EnumType.STRING)
    @ModifyCheckIgnore
    private ApprovalStatus approvalStatus;
    /**
     * 广告内容类型
     */
    @Column(name = "content_type")
    @Enumerated(EnumType.STRING)
    @ModifyCheckIgnore
    private ContentType contentType;

    @Column(name = "site_id")
    private Long siteId;

    @Transient
    @ModifyCheckIgnore
    private String siteName;

    @Column(name = "product_id")
    private Long productId;

    @Transient
    @ModifyCheckIgnore
    private String productName;

    @Column(name = "channel_id")
    @ModifyCheckIgnore
    private Long channelId;

    @Transient
    @ModifyCheckIgnore
    private String channelName;

    @Column(name = "area_id")
    @ModifyCheckIgnore
    private Long areaId;

    @Transient
    @ModifyCheckIgnore
    private String areaName;

    @Column(name = "position_id")
    private Long positionId;

    @Transient
    @ModifyCheckIgnore
    private String positionName;

    @Column(name = "position_guide_url")
    @Size(max = 256)
    @ModifyCheckIgnore
    private String guideUrl;

    /**
     * 广告投放时间段
     */
    @Column(name = "period_description")
    @Size(max = 512)
    private String periodDescription;

    @Column(name = "total_days")
    @ModifyCheckIgnore
    private Integer totalDays;

    @Column(name = "schedule_id")
    @ModifyCheckIgnore
    private Long scheduleId;

    @Transient
    @ModifyCheckIgnore
    private Integer materialType;

    @Column(name = "material_file_type")
    private Integer materialFileType;// 0:图片,1:flash;2:代码,null:纯文本链

    @Column(name = "material_embed_code")
    @ModifyCheckIgnore
    private Integer materialEmbedCode;

    @Column(name = "material_embed_code_content")
    @Size(max = 10000)
    private String materialEmbedCodeContent;

    @Column(name = "material_title")
    @Size(max = 256)
    private String materialTitle;

    @Column(name = "material_url")
    @Size(max = 1024)
    private String materialUrl;

    @Column(name = "task_info")
    @ModifyCheckIgnore
    private String taskInfo;

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

    @Column(name = "number")
    @ModifyCheckIgnore
    private String number;
    @Column(name = "old_content_id")
    @ModifyCheckIgnore
    private Long oldContentId;

    @Column(name = "submit_time")
    @ModifyCheckIgnore
    private Date submitTime;

    @Column(name = "po_num")
    @ModifyCheckIgnore
    private String poNum;

    @Transient
    private boolean isChangedPosition;

    @Column(name = "modify_status")
    @Enumerated(EnumType.ORDINAL)
    @ModifyCheckIgnore
    private ModifyStatus modifyStatus;

    @Column(name = "approval_date")
    @ModifyCheckIgnore
    private Date approvalDate;

    @Column(name = "monitor_url")
    private String monitorUrl;

    @Transient
    @ModifyCheckIgnore
    private List<AdvertiseMaterialApply> materialApplyList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdSolutionId() {
        return adSolutionId;
    }

    public void setAdSolutionId(Long adSolutionId) {
        this.adSolutionId = adSolutionId;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public String getPeriodDescription() {
        return periodDescription;
    }

    public void setPeriodDescription(String periodDescription) {
        this.periodDescription = periodDescription;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getMaterialEmbedCode() {
        return materialEmbedCode;
    }

    public void setMaterialEmbedCode(Integer materialEmbedCode) {
        this.materialEmbedCode = materialEmbedCode;
    }

    public String getMaterialEmbedCodeContent() {
        return materialEmbedCodeContent;
    }

    public void setMaterialEmbedCodeContent(String materialEmbedCodeContent) {
        this.materialEmbedCodeContent = materialEmbedCodeContent;
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

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isChangedPosition() {
        return isChangedPosition;
    }

    public void setChangedPosition(boolean isChangedPosition) {
        this.isChangedPosition = isChangedPosition;
    }

    public Long getOldContentId() {
        return oldContentId;
    }

    public void setOldContentId(Long oldContentId) {
        this.oldContentId = oldContentId;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    /**
     * @return the modifyStatus
     */
    public ModifyStatus getModifyStatus() {
        return modifyStatus;
    }

    /**
     * @param modifyStatus the modifyStatus to set
     */
    public void setModifyStatus(ModifyStatus modifyStatus) {
        this.modifyStatus = modifyStatus;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }

    public Integer getMaterialFileType() {
        return materialFileType;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMaterialFileType(Integer materialFileType) {
        this.materialFileType = materialFileType;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public List<AdvertiseMaterialApply> getMaterialApplyList() {
        return materialApplyList;
    }

    public void setMaterialApplyList(List<AdvertiseMaterialApply> materialApplyList) {
        this.materialApplyList = materialApplyList;
    }
}
