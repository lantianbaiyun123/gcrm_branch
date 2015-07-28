package com.baidu.gcrm.ad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.material.dao.IMaterialMenuRepository;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.ad.service.IAdvertiseMaterialMenuService;

@Service
public class AdvertiseMaterialMenuServiceImpl implements IAdvertiseMaterialMenuService {
    
    @Autowired
    IMaterialMenuRepository materialMenuRepository;
    
    @Override
    public AdvertiseMaterialMenu findMaterialMenuById(Long id) {
        return materialMenuRepository.findOne(id);
    }
}
