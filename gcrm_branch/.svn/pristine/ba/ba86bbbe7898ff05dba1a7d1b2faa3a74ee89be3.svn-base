package com.baidu.gcrm.resource.adplatform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.resource.adplatform.dao.IAdPlatformSiteRelationRepository;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformSiteRelationService;
import com.baidu.gcrm.resource.site.model.Site;

@Service
public class AdPlatformSiteRelationServiceImpl implements IAdPlatformSiteRelationService {
    
    @Autowired
    IAdPlatformSiteRelationRepository adPlatformRelationRepository;

    @Override
    public void saveAdPlatformSiteRelation(AdPlatform adPlatform,boolean isCreate) {
        List<AdPlatformSiteRelation> addList = new ArrayList<AdPlatformSiteRelation> ();
        Long adPlatformId = adPlatform.getId();
        List<AdPlatformSiteRelation> currRelations = convertRelations(adPlatform);
        if (!isCreate) {
            //get exists relation
            List<AdPlatformSiteRelation> existsRelations = adPlatformRelationRepository.findByAdPlatformIdAndStatus(adPlatformId,
                    AdPlatformSiteRelationStatus.enable);
            Map<Long, AdPlatformSiteRelation> existsRelationMap = getRelationMap(existsRelations);
            Map<Long, AdPlatformSiteRelation> currRelationMap = getRelationMap(currRelations);
            //add
            for (AdPlatformSiteRelation currAdPlatformSiteRelation : currRelations) {
                Long currSiteId = currAdPlatformSiteRelation.getSiteId();
                if (existsRelationMap.get(currSiteId) == null) {
                    addList.add(currAdPlatformSiteRelation);
                }
            }
            
            List<AdPlatformSiteRelation> deleteList = new ArrayList<AdPlatformSiteRelation> ();
            //delete
            for (Map.Entry<Long, AdPlatformSiteRelation> existsRelationEntry : existsRelationMap.entrySet()) {
                AdPlatformSiteRelation existsAdPlatformSiteRelation = existsRelationEntry.getValue();
                Long existsSiteId = existsAdPlatformSiteRelation.getSiteId();
                if (currRelationMap.get(existsSiteId) == null) {
                    existsAdPlatformSiteRelation.setStatus(AdPlatformSiteRelationStatus.disable);
                    deleteList.add(existsAdPlatformSiteRelation);
                }
            }
            
            if (!CollectionUtils.isEmpty(deleteList)) {
                adPlatformRelationRepository.save(deleteList);
            }
            
        } else {
            addList.addAll(currRelations);
        }
        if (!CollectionUtils.isEmpty(addList)) {
            adPlatformRelationRepository.save(addList);
        }
        
    }
    
    private List<AdPlatformSiteRelation> convertRelations(AdPlatform adPlatform) {
        List<AdPlatformSiteRelation> currRelationList = new ArrayList<AdPlatformSiteRelation> ();
        List<Site> sites = adPlatform.getSites();
        for (Site temSite : sites) {
            AdPlatformSiteRelation currAdPlatformSiteRelation = new AdPlatformSiteRelation();
            currAdPlatformSiteRelation.setAdPlatformId(adPlatform.getId());
            currAdPlatformSiteRelation.setSiteId(temSite.getId());
            currAdPlatformSiteRelation.setStatus(AdPlatformSiteRelationStatus.enable);
            currAdPlatformSiteRelation.setCreateOperator(adPlatform.getCreateOperator());
            currAdPlatformSiteRelation.setUpdateOperator(adPlatform.getUpdateOperator());
            currAdPlatformSiteRelation.setCreateTime(adPlatform.getCreateTime());
            currAdPlatformSiteRelation.setUpdateTime(adPlatform.getUpdateTime());
            currRelationList.add(currAdPlatformSiteRelation);
        }
        return currRelationList;
    }
    
    
    private Map<Long, AdPlatformSiteRelation> getRelationMap(List<AdPlatformSiteRelation> relations) {
        Map<Long, AdPlatformSiteRelation> relationMap = new HashMap<Long, AdPlatformSiteRelation> ();
        if (CollectionUtils.isEmpty(relations)) {
            return relationMap;
        }
        for (AdPlatformSiteRelation tempAdPlatformSiteRelation : relations) {
            relationMap.put(tempAdPlatformSiteRelation.getSiteId(), tempAdPlatformSiteRelation);
        }
        return relationMap;
    }

    @Override
    public List<AdPlatformSiteRelation> findByAdPlatformIdAndStatus(
            Long adPlatformId, AdPlatformSiteRelationStatus status) {
        return adPlatformRelationRepository.findByAdPlatformIdAndStatus(adPlatformId, status);
    }

    @Override
    public List<AdPlatformSiteRelation> findByAdPlatformIdInAndStatus(List<Long> adPlatformIds, AdPlatformSiteRelationStatus status) {
        return adPlatformRelationRepository.findByAdPlatformIdInAndStatus(adPlatformIds, status);
    }

    @Override
    public int updateRelationStatusByAdPlatformId(Long adPlatformId,
            AdPlatformSiteRelationStatus status, Date updateTime,
            Long updateOpreator) {
        return adPlatformRelationRepository.updateRelationStatusByAdPlatformId(adPlatformId, status, updateTime, updateOpreator);
    }

    @Override
    public List<AdPlatformSiteRelation> findAll() {
        return adPlatformRelationRepository.findAll();
    }

    @Override
    public List<AdPlatformSiteRelation> findByAdPlatformIdAndSiteId(List<Long> adPlatformIds, List<Long> siteIds) {
        return adPlatformRelationRepository.findByAdPlatformIdInAndSiteIdIn(adPlatformIds, siteIds);
    }
    
}
