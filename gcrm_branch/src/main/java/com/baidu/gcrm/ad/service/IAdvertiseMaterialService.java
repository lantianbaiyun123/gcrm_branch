package com.baidu.gcrm.ad.service;

import java.util.Collection;
import java.util.List;

import com.baidu.gcrm.ad.material.vo.MaterialFileVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.google.common.collect.Multimap;

public interface IAdvertiseMaterialService {
    AdvertiseMaterial findById(Long id);

    List<AdvertiseMaterial> findByMaterialApplyId(Long materialApplyId);

    /**
     * 
     * moveToHistory:将广告内容里的物料信息移入历史表. <br/>
     * 
     * @param adContentIds
     * @since JDK 1.6
     */
    void moveToHistory(List<Long> adContentIds);

    void deleteByContentIdIn(List<Long> adContentIds);

    Multimap<Long, MaterialFileVO> getMaterialFilesByAdContentIdIn(Collection<Long> adContentIds);

    AdvertiseMaterial save(AdvertiseMaterial material);

    AdvertiseMaterial findMaterialById(Long id);

    void deleteAdvertiseMaterial(Long id);

    public List<AdvertiseMaterial> findNoApplyFileByContentId(Long contentId);

    public void deleteByAdSolutionContentId(Long contentId);
}
