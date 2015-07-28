package com.baidu.gcrm.publish.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.publish.dao.IPublishOwnerRepository;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishOwner;
import com.baidu.gcrm.publish.service.IPublishOwnerService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

@Service
public class PublishOwnerServiceImpl implements IPublishOwnerService {

	@Autowired
	IAdSolutionContentRepository adSolutionContentRepository;
	
	@Autowired
    IPublishOwnerRepository publishOwnerDao;
	
	@Autowired
	private IUserDataRightService userDataRightService;
	
	private static final String UCID_SPLIT = ",";
	
	@Override
	public boolean checkPublishPermission(Publish publish, Long ucid) {
		AdSolutionContent content = adSolutionContentRepository.findOne(publish.getAdContentId());
		PublishOwner ownerObj = publishOwnerDao.findByPositionId(content.getChannelId());
		
		List<AdvertisingPlatform> platList = userDataRightService.getPlatformListByUcId(ucid);
		for(AdvertisingPlatform plat : platList){
			if(plat.getId().equals(content.getProductId())){
				return true;
			}
		}
		
		List<Site> siteList = userDataRightService.getSiteListByUcId(ucid, LocaleConstants.zh_CN);
		
		for(Site site : siteList){
			if(site.getId().equals(content.getSiteId())){
				return true;
			}
		}
		if(ownerObj != null){
			String owner = ownerObj.getOwner();
			if (StringUtils.isNotBlank(owner)) {
				if (owner.startsWith(ucid.toString() + UCID_SPLIT)
						|| owner.contains(UCID_SPLIT + ucid.toString() + UCID_SPLIT)) {
					return true;
				}
			}
		}
		return false;
	}

}
