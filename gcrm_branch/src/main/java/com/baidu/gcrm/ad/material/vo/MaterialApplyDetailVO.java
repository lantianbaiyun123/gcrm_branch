package com.baidu.gcrm.ad.material.vo;

import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;

/**
 * 物料单详情VO
 * 
 * @author zhanglei35
 * 
 */
public class MaterialApplyDetailVO {
    private AdvertiseMaterialApply materialApply;
    private Integer applyState;
    private Integer materialSaveType;

    public AdvertiseMaterialApply getMaterialApply() {
        return materialApply;
    }

    public void setMaterialApply(AdvertiseMaterialApply materialApply) {
        this.materialApply = materialApply;
    }

    public Integer getMaterialSaveType() {
        return materialSaveType;
    }

    public void setMaterialSaveType(Integer materialSaveType) {
        this.materialSaveType = materialSaveType;
    }

    public Integer getApplyState() {
        return applyState;
    }

    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

}
