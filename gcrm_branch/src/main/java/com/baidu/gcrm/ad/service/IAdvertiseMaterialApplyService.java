package com.baidu.gcrm.ad.service;

import java.util.List;

import com.baidu.gcrm.ad.model.AdvertiseMaterialApply;
import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.common.exception.CRMBaseException;

public interface IAdvertiseMaterialApplyService {

    public AdvertiseMaterialApply save(Long contentId, Long scheduleId) throws CRMBaseException;

    public void save(AdvertiseMaterialApply adMaterialApply);

    /**
     * 请使用findMaterialApplyByContentIdAndState方法，查询广告内容下不同状态的物料单
     * 
     * @param id
     * @return
     */
    public List<AdvertiseMaterialApply> findByAdSolutionContentId(Long id);

    /**
     * 根据物料单状态和广告内容ID查询物料单
     * 
     * @param contentId
     * @param state
     * @return
     */
    public List<AdvertiseMaterialApply> findMaterialApplyByContentIdAndState(Long contentId, MaterialApplyState state);
    
    public List<AdvertiseMaterialApply> findByAdSolutionContentIdAndApplyStateNot(Long contentId, MaterialApplyState state);


}
