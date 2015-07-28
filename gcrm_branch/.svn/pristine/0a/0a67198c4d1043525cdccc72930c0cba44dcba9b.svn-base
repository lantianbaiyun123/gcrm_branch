package com.baidu.gcrm.common.auth.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.dao.IRightsPositionSubRepository;
import com.baidu.gcrm.account.rights.dao.IRightsUserPositionRepository;
import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Service
public class UserDataRightServiceImpl implements IUserDataRightService {
	
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
	
	@Autowired
	private IParticipantService ptcService;
	
	@Autowired
	private ISiteService siteService;
	
	@Value("#{appproperties['cas.appid']}")
	private int appId;
	
	@Autowired
	private IUserRightsService userRightsService;
	
	@Autowired
	private IRightsPositionSubRepository posSubRepository;
	
	@Autowired
	private IRightsUserPositionRepository userPosRepository;
	
	/**
	 * 根据ucId获取用户的岗位的所有子岗位
	 * @param ucId
	 * @return
	 */
	private List<RightsPosition> getSubPositionListByUcId(Long ucId) {
		List<RightsPosition> subPosList = new ArrayList<RightsPosition>();
		List<RightsPosition> posList = userRightsService.findUserPositionsByUcId(ucId);
		for (RightsPosition position : posList) {
		    List<RightsPosition> tempRP =posSubRepository.findSubPosByPosId(position.getPosId());
			subPosList.addAll(tempRP);
		}
		return subPosList;
	}
	
	@Override
	public List<User> getSubUserListByUcId(Long ucId) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return userService.findAll();
		}
		List<RightsPosition> subPosList = this.getSubPositionListByUcId(ucId);
		List<Long> posIds = new ArrayList<Long>();
		for (RightsPosition position : subPosList) {
			posIds.add(position.getPosId());
		}
		if(posIds==null||posIds.size()==0){
		    return null;
		}
		return userPosRepository.findUsersByPosIds(posIds);
	}
	
	@Override
	public List<User> getLeaderListByUcId(Long ucId) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return userService.findAll();
		}
		return userPosRepository.findLeadersByUcId(ucId);
	}

	@Override
	public List<AdvertisingPlatform> getPlatformListByUcId(Long ucId) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return adPlatformService.getAll();
		}
		List<AdvertisingPlatform> result = new LinkedList<AdvertisingPlatform>();
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return result;
		}
		List<String> platformIdList = ptcService.getKeysByUsernameAndDescription(user.getUsername(), Participant.DescriptionType.platform);
		for (String platformId : platformIdList) {
		    AdvertisingPlatform cacheAdvertisingPlatform = adPlatformService.getById(platformId);
            if (cacheAdvertisingPlatform != null) {
                result.add(cacheAdvertisingPlatform);
            }
		}
		return result;
	}
	
	@Override
	public boolean isUserDataByRoleAndDataType(Long ucId, String roleTag, DescriptionType dataType, String platformId) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return true;
		}
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return false;
		}
		Participant participant = ptcService.findByUnameAndPidAndDescAndKey(user.getUsername(), roleTag, dataType, platformId);
		return null == participant ? false : true;
	}

	@Override
	public List<AdvertisingPlatform> getPlatformListByUcId(Long ucId, LocaleConstants locale) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return adPlatformService.getAllByLocale(locale);
		}
		List<AdvertisingPlatform> result = new LinkedList<AdvertisingPlatform>();
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return result;
		}
		List<String> platformIdList = ptcService.getKeysByUsernameAndDescription(user.getUsername(), Participant.DescriptionType.platform);
		for (String platformId : platformIdList) {
		    AdvertisingPlatform cacheAdvertisingPlatform = adPlatformService.getByIdAndLocale(platformId, locale);
			if (cacheAdvertisingPlatform != null) {
			    result.add(cacheAdvertisingPlatform);
			}
		}
		return result;
	}

	@Override
	public List<Site> getSiteListByUcId(Long ucId, LocaleConstants locale) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return siteService.findAll(locale);
		}
		List<Site> result = new LinkedList<Site>();
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return result;
		}
		List<String> siteIdList = ptcService.getKeysByUsernameAndDescription(user.getUsername(), Participant.DescriptionType.site);
		for (String siteId : siteIdList) {
			result.add(siteService.findSiteAndI18nById(Long.valueOf(siteId), locale));
		}
		return result;
	}

	@Override
	public List<String> getRegionalIdListByUcId(Long ucId) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return ptcService.getKeysByUsernameAndDescription(null, DescriptionType.regional);
		}
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return new ArrayList<String>();
		}
		return ptcService.getKeysByUsernameAndDescription(user.getUsername(), DescriptionType.regional);
	}

	@Override
	public List<Integer> getBusinessTypeByUcId(Long ucId) {
		List<String> typeList = new ArrayList<String>();
		List<Integer> result = new ArrayList<Integer>();
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			typeList = ptcService.getKeysByUsernameAndDescription(null, DescriptionType.businessType);
		}else {
			User user = userService.findByUcid(ucId);
			if (null == user) {
				return result;
			}
			typeList = ptcService.getKeysByUsernameAndDescription(user.getUsername(), Participant.DescriptionType.businessType);
		}
		for (String type : typeList) {
			result.add(Integer.valueOf(type));
		}
		return result;
	}
	
	@Override
	public List<Site> getSiteListByUcIdAndPlatformId(Long ucId, Long platformId, LocaleConstants locale) {
		// 管理员查看所有数据
		if (userRightsService.isAdminRole(ucId)) {
			return siteService.findSiteByAdPlatform(platformId, locale);
		}
		User user = userService.findByUcid(ucId);
		if (null == user) {
			return new ArrayList<Site>();
		}
		return siteService.findSiteByAdPlatformAndRights(platformId, locale, user.getUsername());
	}
	
}
