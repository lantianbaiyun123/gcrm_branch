package com.baidu.gcrm.ad.material.vo;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;

/**
 * 物料单列表VO
 * 
 * @author gaofuchun
 * 
 */
public class MaterialApplyVO {
    private Long applyId;
    private String number;
    private Integer applyState;
    private Date createTime;
    private Long createOperator;
    private String createOperatorName;
    private Integer hasRecords;
    private String periodDescription; 

    /**
     * 物料下拉菜单列表
     */
    private List<AdvertiseMaterialMenu> materialMenuList;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
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

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public Integer getHasRecords() {
        return hasRecords;
    }

    public void setHasRecords(Integer hasRecords) {
        this.hasRecords = hasRecords;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public void setMaterialMenuList(List<AdvertiseMaterialMenu> materialMenuList) {
        this.materialMenuList = materialMenuList;
    }
}
