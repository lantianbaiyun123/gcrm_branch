package com.baidu.gcrm.ws.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformSiteRelationService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.ws.cms.dto.PositionDTO;
import com.baidu.gcrm.ws.cms.dto.SiteDTO;
import com.baidu.gcrm.ws.cms.service.IResourceService;

@Service
public class ResourceService implements IResourceService{
    
    @Autowired
    IAdPlatformSiteRelationService adPlatformSiteRelationService;
    
    @Autowired
    IPositionService positionService;

    @Override
    public List<SiteDTO> queryAllSite() {
        
        List<AdPlatformSiteRelation> relations = adPlatformSiteRelationService.findAll();
        if (CollectionUtils.isEmpty(relations)) {
            return null;
        }
        List<SiteDTO> siteDTOList = new ArrayList<SiteDTO> ();
        for (AdPlatformSiteRelation tempAdPlatformSiteRelation : relations) {
            SiteDTO temSiteDTO = new SiteDTO();
            temSiteDTO.setId(tempAdPlatformSiteRelation.getSiteId());
            temSiteDTO.setProductId(tempAdPlatformSiteRelation.getAdPlatformId());
            siteDTOList.add(temSiteDTO);
        }
        
        return siteDTOList;
    }

    @Override
    public List<PositionDTO> queryAllPosition() {
        List<Position> positionList = positionService.findAll();
        if (CollectionUtils.isEmpty(positionList)) {
            return null;
        }
        List<PositionDTO> positionDTOList = new ArrayList<PositionDTO> ();
        for (Position temPosition : positionList) {
            PositionDTO temPositionDTO = new PositionDTO();
            temPositionDTO.setId(temPosition.getId());
            temPositionDTO.setSiteId(temPosition.getSiteId());
            temPositionDTO.setProductId(temPosition.getAdPlatformId());
            temPositionDTO.setType(temPosition.getType().ordinal());
            Long parentId = temPosition.getParentId();
            if (parentId != null) {
                temPositionDTO.setParentId(parentId);
            }
            PositionStatus status = temPosition.getStatus();
            if (status != null) {
                temPositionDTO.setStatus(status.ordinal());
            }
            
            positionDTOList.add(temPositionDTO);
        }
        return positionDTOList;
    }
    

}
