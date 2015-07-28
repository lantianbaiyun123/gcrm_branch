package com.baidu.gcrm.resource.position.model;

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

import org.springframework.web.multipart.MultipartFile;

import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.i18n.model.BaseI18nModel;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

@Entity
@Table(name = "g_position")
public class Position extends BaseI18nModel implements BaseOperationModel, IPositionModel {

    private static final long serialVersionUID = 1080777082917712581L;

    public enum PositionType {
        channel, area, position;

        public String getValue() {
            return this.name();
        }

        public static PositionType valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            PositionType[] values = PositionType.values();
            for (PositionType currPositionType : values) {
                if (currPositionType.ordinal() == value) {
                    return currPositionType;
                }
            }

            return null;
        }
    }
    
    public enum PositionMaterialType {
        image("图片"), textlink("文字链"), image_and_textlink("文字+图片"), custom("其他");

        private String cnName = null;

        PositionMaterialType(String cnName) {
            this.cnName = cnName;
        }

        public static PositionMaterialType cnValueOf(String cnName) {
            if (PositionMaterialType.image.cnName.equals(cnName)) {
                return PositionMaterialType.image;
            } else if (PositionMaterialType.textlink.cnName.equals(cnName)) {
                return PositionMaterialType.textlink;
            } else if (PositionMaterialType.image_and_textlink.cnName.equals(cnName)) {
                return PositionMaterialType.image_and_textlink;
            } else if (PositionMaterialType.custom.cnName.equals(cnName)) {
                return PositionMaterialType.custom;
            } else {
                return null;
            }
        }

        public static PositionMaterialType valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            PositionMaterialType[] values = PositionMaterialType.values();
            for (PositionMaterialType materialType : values) {
                if (materialType.ordinal() == value) {
                    return materialType;
                }
            }

            return null;
        }
    }

    public enum PositionStatus {
        disable, enable;
    }

    public enum PositionPropertyStatus {
        disable, enable;
    }

    public enum RotationType {
        no("非轮播"), yes("轮播");

        private String cnName = null;

        RotationType(String cnName) {
            this.cnName = cnName;
        }

        public static RotationType cnValueOf(String cnName) {
            if (cnName.equals(RotationType.no.cnName)) {
                return RotationType.no;
            } else if (cnName.equals(RotationType.yes.cnName)) {
                return RotationType.yes;
            } else {
                return null;
            }
        }

        public String getCnName() {
            return this.cnName;
        }

        public boolean isRotation() {
            return this.equals(yes);
        }

        public static RotationType valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            RotationType[] values = RotationType.values();
            for (RotationType rotationType : values) {
                if (rotationType.ordinal() == value) {
                    return rotationType;
                }
            }

            return null;
        }
    }
    
    public enum RotationOrder {
        sequence("顺序轮播"),random("随机轮播");
        
        private String cnName;
        
        private RotationOrder(String cnName) {
            this.cnName = cnName;
        }
        
        public static RotationOrder cnValueOf(String cnName) {
            if (cnName.equals(RotationOrder.sequence.cnName)) {
                return RotationOrder.sequence;
            } else if (cnName.equals(RotationOrder.random.cnName)) {
                return RotationOrder.random;
            } else {
                return null;
            }
        }

        public String getCnName() {
            return this.cnName;
        }
        
        public static RotationOrder valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            RotationOrder[] values = RotationOrder.values();
            for (RotationOrder rotationOrder : values) {
                if (rotationOrder.ordinal() == value) {
                    return rotationOrder;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    private String name;

    @Column(name = "position_number")
    private String positionNumber;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "ad_platform_id")
    private Long adPlatformId;

    @Transient
    private String adPlatformName;

    @Column(name = "site_id")
    private Long siteId;

    @Transient
    private String siteName;

    @Transient
    private Long channelId;

    @Transient
    private String channelName;

    @Transient
    private Long areaId;

    @Transient
    private String areaName;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private PositionType type;

    @Column(name = "rotation_type")
    @Enumerated(EnumType.ORDINAL)
    private RotationType rotationType;

    @Column(name = "rotation_order")
    @Enumerated(EnumType.ORDINAL)
    private RotationOrder rotationOrder;

    @Column(name = "area_required")
    private String areaRequired;

    @Column
    private String size;

    @Column(name = "sales_amount")
    private Integer salesAmount;

    @Column(name = "textlink_length")
    private Integer textlinkLength;

    @Column(name = "cpt_benchmark_price")
    private Double cptBenchmarkPrice;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private PositionStatus status;

    @Column(name = "property_status")
    @Enumerated(EnumType.ORDINAL)
    private PositionPropertyStatus propertyStatus;

    @Column(name = "material_type")
    @Enumerated(EnumType.ORDINAL)
    private PositionMaterialType materialType;

    @Column
    private Long click;

    @Column
    private Long pv;

    @Column(name = "index_str")
    private String indexStr;// 用于级联删除或查看提速

    @Transient
    MultipartFile file;

    @Column(name = "guide_url")
    private String guideUrl;

    @Column(name = "guide_file_name")
    private String guideFileName;

    @Column(name = "guidefile_download_name")
    private String guideFileDownloadName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_operator")
    private Long createOperator;

    @Column(name = "last_update_time")
    private Date updateTime;

    @Column(name = "last_update_operator")
    private Long updateOperator;

    @Column(name = "position_code")
    private String positionCode;

    @Transient
    private List<PositionVO> posotionData;

    @Transient
    private String i18nExtraName;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;

    }

    @Override
    public Long getCreateOperator() {
        return createOperator;
    }

    @Override
    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;

    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;

    }

    @Override
    public Long getUpdateOperator() {
        return updateOperator;
    }

    @Override
    public void setUpdateOperator(Long updateOperator) {
        this.updateOperator = updateOperator;

    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public RotationType getRotationType() {
        return rotationType;
    }

    public void setRotationType(RotationType rotationType) {
        this.rotationType = rotationType;
    }
    
    public RotationOrder getRotationOrder() {
        return rotationOrder;
    }

    public void setRotationOrder(RotationOrder rotationOrder) {
        this.rotationOrder = rotationOrder;
    }

    public String getAreaRequired() {
        return areaRequired;
    }

    public void setAreaRequired(String areaRequired) {
        this.areaRequired = areaRequired;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public PositionStatus getStatus() {
        return status;
    }

    public void setStatus(PositionStatus status) {
        this.status = status;
    }

    public PositionMaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(PositionMaterialType materialType) {
        this.materialType = materialType;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getAdPlatformId() {
        return adPlatformId;
    }

    public void setAdPlatformId(Long adPlatformId) {
        this.adPlatformId = adPlatformId;
    }

    public String getAdPlatformName() {
        return adPlatformName;
    }

    public void setAdPlatformName(String adPlatformName) {
        this.adPlatformName = adPlatformName;
    }

    public PositionType getType() {
        return type;
    }

    public void setType(PositionType type) {
        this.type = type;
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

    public String getIndexStr() {
        return indexStr;
    }

    public void setIndexStr(String indexStr) {
        this.indexStr = indexStr;
    }

    public String getGuideUrl() {
        return guideUrl;
    }

    public void setGuideUrl(String guideUrl) {
        this.guideUrl = guideUrl;
    }

    public String getGuideFileName() {
        return guideFileName;
    }

    public void setGuideFileName(String guideFileName) {
        this.guideFileName = guideFileName;
    }

    public String getGuideFileDownloadName() {
        return guideFileDownloadName;
    }

    public void setGuideFileDownloadName(String guideFileDownloadName) {
        this.guideFileDownloadName = guideFileDownloadName;
    }

    public List<PositionVO> getPosotionData() {
        return posotionData;
    }

    public void setPosotionData(List<PositionVO> posotionData) {
        this.posotionData = posotionData;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getTextlinkLength() {
        return textlinkLength;
    }

    public void setTextlinkLength(Integer textlinkLength) {
        this.textlinkLength = textlinkLength;
    }

    public Double getCptBenchmarkPrice() {
        return cptBenchmarkPrice;
    }

    public void setCptBenchmarkPrice(Double cptBenchmarkPrice) {
        this.cptBenchmarkPrice = cptBenchmarkPrice;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getI18nExtraName() {
        return i18nExtraName;
    }

    public void setI18nExtraName(String i18nExtraName) {
        this.i18nExtraName = i18nExtraName;
    }

    public PositionPropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus(PositionPropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
