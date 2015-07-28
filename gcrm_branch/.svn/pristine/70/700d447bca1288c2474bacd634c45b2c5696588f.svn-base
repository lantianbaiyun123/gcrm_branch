package com.baidu.gcrm.resource.position.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.model.Position.RotationOrder;
import com.baidu.gcrm.resource.position.model.Position.RotationType;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;

@Entity
public class PositionQueryBean implements IPositionModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Transient
    private String name;

    @Column(name = "position_number")
    private String positionNumber;

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

    @Column(name = "textlink_length")
    private Integer textlinkLength;

    @Column(name = "cpt_benchmark_price")
    private Double cptBenchmarkPrice;

    @Column
    private Long click;

    @Column
    private Long pv;

    @Column(name = "sales_amount")
    private Integer salesAmount;

    @Column(name = "guide_file_name")
    private String guideFileName;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private PositionStatus status;

    @Column(name = "material_type")
    @Enumerated(EnumType.ORDINAL)
    private PositionMaterialType materialType;

    @Transient
    private Integer materialTypeValue;

    @Column(name = "index_str")
    private String indexStr;

    @Column
    private String i18nValue;

    @Column
    private String i18nExtraValue;

    @Transient
    List<PositionButtonNumber> queryButtons;

    @Transient
    List<PositionButtonNumber> modifyButtons;

    public enum PositionButtonNumber {
        viewProperty, viewOccupation, viewImage, viewCode, modifyName, batchModifyProperty, addProperty, modifyProperty, addImage, modifyImage, modifyOccupation
    }

    @Transient
    private List<PositionVO> posotionData;

    @Column(name = "position_code")
    private String positionCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public PositionType getType() {
        return type;
    }

    public void setType(PositionType type) {
        this.type = type;
    }

    public RotationType getRotationType() {
        return rotationType;
    }

    public void setRotationType(RotationType rotationType) {
        this.rotationType = rotationType;
    }

    public String getAreaRequired() {
        return areaRequired;
    }

    public void setAreaRequired(String areaRequired) {
        this.areaRequired = areaRequired;
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

    public String getIndexStr() {
        return indexStr;
    }

    public void setIndexStr(String indexStr) {
        this.indexStr = indexStr;
    }

    public List<PositionVO> getPosotionData() {
        return posotionData;
    }

    public void setPosotionData(List<PositionVO> posotionData) {
        this.posotionData = posotionData;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<PositionButtonNumber> getQueryButtons() {
        return queryButtons;
    }

    public void setQueryButtons(List<PositionButtonNumber> queryButtons) {
        this.queryButtons = queryButtons;
    }

    public List<PositionButtonNumber> getModifyButtons() {
        return modifyButtons;
    }

    public void setModifyButtons(List<PositionButtonNumber> modifyButtons) {
        this.modifyButtons = modifyButtons;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getGuideFileName() {
        return guideFileName;
    }

    public void setGuideFileName(String guideFileName) {
        this.guideFileName = guideFileName;
    }

    public String getI18nValue() {
        return i18nValue;
    }

    public void setI18nValue(String i18nValue) {
        this.i18nValue = i18nValue;
    }

    public String getI18nExtraValue() {
        return i18nExtraValue;
    }

    public void setI18nExtraValue(String i18nExtraValue) {
        this.i18nExtraValue = i18nExtraValue;
    }

    public Integer getMaterialTypeValue() {
        return materialTypeValue;
    }

    public void setMaterialTypeValue(Integer materialTypeValue) {
        this.materialTypeValue = materialTypeValue;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public RotationOrder getRotationOrder() {
        return rotationOrder;
    }

    public void setRotationOrder(RotationOrder rotationOrder) {
        this.rotationOrder = rotationOrder;
    }
    
}
