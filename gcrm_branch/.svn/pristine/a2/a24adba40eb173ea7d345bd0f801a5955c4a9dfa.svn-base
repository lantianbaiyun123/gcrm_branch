package com.baidu.gcrm.ad.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepository;
import com.baidu.gcrm.ad.dao.AdvertiseMaterialRepositoryCustom;
import com.baidu.gcrm.ad.material.vo.MaterialFileVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialService;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@Service
public class AdvertiseMaterialService implements IAdvertiseMaterialService {

    @Autowired
    private AdvertiseMaterialRepository adMaterialRepository;

    @Autowired
    private AdvertiseMaterialRepositoryCustom adMaterialRepositoryCustom;

    @Override
    public AdvertiseMaterial findById(Long id) {
        return adMaterialRepository.findOne(id);
    }

    @Override
    public List<AdvertiseMaterial> findNoApplyFileByContentId(Long contentId) {
        return adMaterialRepository.findNoApplyFileByContentId(contentId);
    }

    @Override
    public void deleteByAdSolutionContentId(Long contentId) {
        adMaterialRepository.deleteByAdSolutionContentId(contentId);
    }

    @Override
    public void moveToHistory(List<Long> adContentIds) {
        adMaterialRepositoryCustom.moveToHistory(adContentIds);
    }

    @Override
    public void deleteByContentIdIn(List<Long> adContentIds) {
        adMaterialRepository.deleteByContentIdIn(adContentIds);

    }

    @Override
    public Multimap<Long, MaterialFileVO> getMaterialFilesByAdContentIdIn(Collection<Long> adContentIds) {
        Multimap<Long, MaterialFileVO> fileMap = ArrayListMultimap.create();
        List<MaterialFileVO> materialFiles = adMaterialRepositoryCustom.findMaterialFilesByAdContentIdIn(adContentIds);
        for (MaterialFileVO fileVO : materialFiles) {
            Long adContentId = fileVO.getAdContentId();
            fileMap.put(adContentId, fileVO);
        }
        return fileMap;
    }

    @Override
    public AdvertiseMaterial save(AdvertiseMaterial material) {
        return adMaterialRepository.save(material);
    }

    @Override
    public List<AdvertiseMaterial> findByMaterialApplyId(Long materialApplyId) {
        return adMaterialRepository.findByMaterialApplyId(materialApplyId);
    }

    @Override
    public AdvertiseMaterial findMaterialById(Long id) {
        return adMaterialRepository.findOne(id);
    }

    @Override
    public void deleteAdvertiseMaterial(Long id) {
        adMaterialRepository.delete(id);
    }

}
