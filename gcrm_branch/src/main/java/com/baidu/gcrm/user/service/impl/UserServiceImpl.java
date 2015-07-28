package com.baidu.gcrm.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.account.model.Account.AccountStatus;
import com.baidu.gcrm.account.rights.dao.IRightsUserRoleRepository;
import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.approval.dao.IParticipantRepositoryCustom;
import com.baidu.gcrm.ad.approval.dao.ParticipantRepository;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.web.helper.BusinessType;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.dao.IUserRepository;
import com.baidu.gcrm.user.dao.IUserRepositoryCustom;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.user.web.helper.UserListBean;
import com.baidu.gcrm.user.web.vo.DataRightsVO;
import com.baidu.gcrm.user.web.vo.UserConditionVO;
import com.baidu.gcrm.user.web.vo.UserDataRightVO;
import com.baidu.gcrm.user.web.vo.UserDetailInfoVO;
import com.baidu.gcrm.user.web.vo.UserVO;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CodeCacheServiceImpl;

/**
 * 系统内部用户服务类
 * @author zhanglei35
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IUserRepositoryCustom userRepositoryCustomer;
	
	@Autowired
	ParticipantRepository participantRepository;
	
	@Autowired
	private IParticipantRepositoryCustom participantRepositoryCustom;
	
	@Autowired
	private IRightsUserRoleRepository userRoleRepository;
	
	@Autowired
	private I18nKVService i18nService;
	
	@Autowired
	private AdvertisingPlatformServiceImpl adPlatformService;
	
	@Autowired
	private CodeCacheServiceImpl codeCacheServiceImpl;
	
	@Autowired
	private ISiteService siteService;
	
	@Autowired
	private IUserRightsService rightsService;
	
	@Value("#{appproperties['newrights.url']}")
	public String newRightsURL;
	
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	public User findByUcid(Long ucid){
		return userRepository.findByUcid(ucid);
	}

    @Override
    public Map<Long, User> findByUcidIn(Set<Long> ucids) {
        List<User> users = userRepository.findByUcidIn(ucids);
        Map<Long, User> userMap = new HashMap<Long, User> ();
        if (CollectionUtils.isEmpty(users)) {
            return userMap;
        }
        for (User temUser : users) {
            userMap.put(temUser.getUcid(), temUser);
        }
        return userMap;
    }

	@Override
	public List<User> findByName(String username) {
	    if(username.startsWith("%")&& username.equals("%")&&username.length()>1){
	        
	    } else {
	        username = "%"+username+"%";
	    }
	    
	    String systemUserMailToExclude = GcrmConfig.getMailFromUser();
		return userRepository.findByName(username, systemUserMailToExclude);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public Map<Long, User> findAllUsersMap() {
	    List<User> users = findAll();
	    Map<Long, User> userMap = new HashMap<Long, User> ();
        if (CollectionUtils.isEmpty(users)) {
            return userMap;
        }
        for (User temUser : users) {
            userMap.put(temUser.getUcid(), temUser);
        }
        return userMap;
	}
	
	public boolean isUsernameExists(User user){
		User sameNameUser = userRepository.findByUsername(user.getUsername());
		if (null != sameNameUser) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void saveUser(User user){
		userRepository.save(user);
	}
	
	
	@Override
	public Page<UserListBean> findByCondition(UserConditionVO condition, LocaleConstants locale) {
		Page<UserListBean> result = userRepositoryCustomer.findByCondition(condition);
		processUserListPage(result, locale);
		return result;
	}
	
	/**
	 * 设置用户
	 * @param page
	 * @param locale
	 */
	private void processUserListPage(Page<UserListBean> page, LocaleConstants locale) {
		List<UserListBean> userList = page.getContent();
		List<UserListBean> contentList = new ArrayList<UserListBean>();
		if (null == userList || userList.size() < 1) {
			return;
		}
		for (UserListBean userBean : userList) {
			// 设置用户是否设置数据权限
			List<Participant> partList = participantRepository.findByUsername(userBean.getUcName());
			boolean hasDataRights = partList.size() > 0 ? true : false;
			userBean.setHasDataRights(hasDataRights);
			// 获取用户的所有角色
			StringBuilder roleBuilder = new StringBuilder();
			List<RightsRole> roleList = userRoleRepository.findRolesByUcId(userBean.getUcId());
			i18nService.fillI18nInfo(roleList, locale);
			for (RightsRole rightsRole : roleList) {
				roleBuilder.append(rightsRole.getI18nName());
				roleBuilder.append(",");
			}
			if (null != roleBuilder && roleBuilder.length() > 0) {
				userBean.setRoleName(roleBuilder.substring(0, roleBuilder.length() - 1));
			}
			userBean.setStatus(AccountStatus.valueOf(Integer.valueOf(userBean.getStatus())).toString());
			contentList.add(userBean);
		}
		page.setContent(contentList);
	}
	
	@Override
	public void findUserDetailInfo(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale) {
		User user = findByUcid(ucId);
		if (null == user) {
			return;
		}
		// 用户基本信息、账号信息
		UserVO userVO = new UserVO();
		userVO.setUcid(user.getUcid());
		userVO.setUsername(user.getUsername());
		userVO.setRealName(user.getRealname());
		userVO.setEmail(user.getEmail());
		userVO.setDept(user.getDept());
		userVO.setSupervisor(user.getSupervisor());
		userVO.setPhoneNumber(user.getPhonenumber());
		userDetail.setUserVO(userVO);
		// 用户的角色信息
		setUserRoleNames(userDetail, ucId, locale);
		// 用户的岗位信息
		setUserParentAndSubPos(userDetail, ucId, locale);
		// 数据权限信息
		setUserDataRights(userDetail, user.getUsername(), locale);
		//  修改角色URL
		userDetail.setNewRightsURL(newRightsURL);
	}
	
	@Override
	public void setUserParentAndSubPos(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale) {
		List<RightsPosition> posList = rightsService.findUserPositionsByUcId(ucId);
		List<RightsPosition> directParentPosList = rightsService.findUserDirectParentPos(ucId);
		List<RightsPosition> directSubPosList = rightsService.findUserDirectSubPos(ucId);
		StringBuilder posBuilder = new StringBuilder();
		for (RightsPosition pos : posList) {
			posBuilder.append(pos.getPosName());
			posBuilder.append(",");
		}
		if (posBuilder.length() > 0) {
			userDetail.setPosition(posBuilder.substring(0, posBuilder.length() - 1));
		}
		StringBuilder parentPosBuilder = new StringBuilder();
		for (RightsPosition pos : directParentPosList) {
			parentPosBuilder.append(pos.getPosName());
			parentPosBuilder.append(",");
		}
		if (parentPosBuilder.length() > 0) {
			userDetail.setPosParent(parentPosBuilder.substring(0, parentPosBuilder.length() - 1));
		}
		StringBuilder subPosBuilder = new StringBuilder();
		for (RightsPosition pos : directSubPosList) {
			subPosBuilder.append(pos.getPosName());
			subPosBuilder.append(",");
		}
		if (subPosBuilder.length() > 0) {
			userDetail.setPosSub(subPosBuilder.substring(0, subPosBuilder.length() - 1));
		}
	}
	
	@Override
	public void setUserRoleNames(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale) {
		StringBuilder roleNameBuilder = new StringBuilder();
		List<RightsRole> roleList = rightsService.findUserRolesByUcId(ucId, locale);
		for (RightsRole rightsRole : roleList) {
			roleNameBuilder.append(rightsRole.getI18nName());
			roleNameBuilder.append(",");
		}
		if (roleNameBuilder.length() > 0) {
			userDetail.setRoleName(roleNameBuilder.substring(0, roleNameBuilder.length() - 1));
		}
	}
	
	@Override
	public void setUserDataRights(UserDetailInfoVO userDetail, String username, LocaleConstants locale) {
		boolean hasDataRights = false;
		// 业务类型
		List<String> businessTypeIds = participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.businessType);
		StringBuilder busiTypeBuilder = new StringBuilder();
		for (String typeId : businessTypeIds) {
			Code code = codeCacheServiceImpl.getCodeByTypeAndValueAndLocale("quotationMain.businessType", typeId, locale.toString());
			busiTypeBuilder.append(code.getI18nName());
			busiTypeBuilder.append(",");
		}
		if (busiTypeBuilder.length() > 0) {
			userDetail.setBusinessTypes(busiTypeBuilder.substring(0, busiTypeBuilder.length() - 1));
			hasDataRights = true;
		}
		// 平台
		StringBuilder platformBuilder = new StringBuilder();
		List<String> platformIds = participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.platform);
		for (String platformId : platformIds) {
			AdvertisingPlatform platform = adPlatformService.getByIdAndLocale(platformId, locale);
			platformBuilder.append(platform.getI18nName());
			platformBuilder.append(",");
		}
		if(platformBuilder.length() > 0){
			userDetail.setPlatforms(platformBuilder.substring(0, platformBuilder.length() - 1));
			hasDataRights = true;
		}
		// 站点
		List<String> siteIds = participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.site);
		StringBuilder siteBuilder = new StringBuilder();
		for (String siteId : siteIds) {
			Site site = siteService.findSiteAndI18nById(Long.valueOf(siteId), locale);
			siteBuilder.append(site.getI18nName());
			siteBuilder.append(",");
		}
		if (siteBuilder.length() > 0) {
			userDetail.setSites(siteBuilder.substring(0, siteBuilder.length() - 1));
			hasDataRights = true;
		}
		userDetail.setHasDataRights(hasDataRights);
	}
	
	@Override
	public DataRightsVO findUserDataRightsVO(String username, String roleTag, LocaleConstants locale) {
		boolean isByRole = true;
		// 不通过角色查找数据权限
		if (StringUtils.isEmpty(roleTag)) {
			isByRole = false;
		}
		DataRightsVO dataRightsVO = new DataRightsVO();
		List<UserDataRightVO> platformRightList = new ArrayList<UserDataRightVO>();
		List<UserDataRightVO> siteRightList = new ArrayList<UserDataRightVO>();
		List<UserDataRightVO> busiTypeRightList = new ArrayList<UserDataRightVO>();
		// 投放平台
		List<AdvertisingPlatform> adPlatformList = adPlatformService.getAllByLocale(locale);
		List<String> userPfIds = isByRole ? participantRepositoryCustom.getKeysByUnameAndDescAndPartId(username, DescriptionType.platform, roleTag) 
				                          : participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.platform);
		for (AdvertisingPlatform adPlatform : adPlatformList) {
			UserDataRightVO pfRightVO = new UserDataRightVO();
			pfRightVO.setRightsId(adPlatform.getId());
			pfRightVO.setRightsName(adPlatform.getI18nName());
			boolean hasRights = userPfIds.contains(String.valueOf(adPlatform.getId())) ? true : false;
			pfRightVO.setHasRights(hasRights);
			platformRightList.add(pfRightVO);
		}
		dataRightsVO.setPlatformRights(platformRightList);
		// 站点
		List<Site> siteList = siteService.findAll(locale);
		List<String> userSiteIds = isByRole ? participantRepositoryCustom.getKeysByUnameAndDescAndPartId(username, DescriptionType.site, roleTag)
				                            : participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.site);
		for (Site site : siteList) {
			UserDataRightVO siteRightVO = new UserDataRightVO();
			siteRightVO.setRightsId(site.getId());
			siteRightVO.setRightsName(site.getI18nName());
			boolean hasRights = userSiteIds.contains(String.valueOf(site.getId())) ? true : false;
			siteRightVO.setHasRights(hasRights);
			siteRightList.add(siteRightVO);
		}
		dataRightsVO.setSiteRights(siteRightList);
		// 业务类型
		List<String> businessTypeIds = isByRole ? participantRepositoryCustom.getKeysByUnameAndDescAndPartId(username, DescriptionType.businessType, roleTag)
				                                : participantRepositoryCustom.getKeysByUsernameAndDescription(username, DescriptionType.businessType);
		for (BusinessType busiType : BusinessType.values()) {
			Code code = codeCacheServiceImpl.getCodeByTypeAndValueAndLocale("quotationMain.businessType", String.valueOf(busiType.ordinal()), locale.toString());
			UserDataRightVO busiTypeRightVO = new UserDataRightVO();
			busiTypeRightVO.setRightsId(Long.valueOf(code.getCodeValue()));
			busiTypeRightVO.setRightsName(code.getI18nName());
			boolean hasRights = businessTypeIds.contains(code.getCodeValue()) ? true : false;
			busiTypeRightVO.setHasRights(hasRights);
			busiTypeRightList.add(busiTypeRightVO);
		}
		dataRightsVO.setBusiTypeRights(busiTypeRightList);
		return dataRightsVO;
	}
	
	@Override
	public void saveUserDataRights(List<DataRightsVO> rightsList) {
		if (null == rightsList || rightsList.size() < 1 || null == rightsList.get(0)) {
			return;
		}
		participantRepository.deleteByUsername(rightsList.get(0).getUsername());  // 清空原来的数据权限
		for (DataRightsVO dataRightsVO : rightsList) {
			String username = dataRightsVO.getUsername();
			String roleTag = dataRightsVO.getRoleTag();   // 如果不根据角色区分，则为空
			
			List<Participant> dataRightsList = new ArrayList<Participant>();
			// 业务类型
			List<UserDataRightVO> busiTypeList = dataRightsVO.getBusiTypeRights();
		    addDataRights(dataRightsList, busiTypeList, username, DescriptionType.businessType, roleTag);
			// 投放平台
			List<UserDataRightVO> pfRightList = dataRightsVO.getPlatformRights();
			addDataRights(dataRightsList, pfRightList, username, DescriptionType.platform, roleTag);
			// 站点
			List<UserDataRightVO> siteRightList = dataRightsVO.getSiteRights();
			addDataRights(dataRightsList, siteRightList, username, DescriptionType.site, roleTag);
			participantRepository.save(dataRightsList);
		}
	}
	
	public Account getAccountByUcId(Long ucId){
		Account account =  null;
		User user = this.findByUcid(ucId);
		if(user !=null){
			account = new Account();
			account.setUcid(user.getUcid());
			account.setEmail(user.getEmail());
			account.setName(user.getRealname());
		}
		return account;
	}
	/**
	 * VO到PO的转换
	 * @param dataRightsList
	 * @param dataRightVOs
	 * @param username
	 * @param descType
	 */
	private void addDataRights(List<Participant> dataRightsList, List<UserDataRightVO> dataRightVOs, String username, DescriptionType descType, String roleTag){
//		boolean isPm = false;
//		boolean isAgent = false;
//		if (descType.equals(DescriptionType.platform) || descType.equals(DescriptionType.site)) {
//			User user = findByUsername(username);
//			List<RightsRole> roleList = rightsService.findUserRolesByUcId(user.getUcid());  // 获取用户角色，判断是否是PM或国代
			
//			for (RightsRole role : roleList) {
//				System.out.println("roleTag: " +role.getRoleTag());
//				if (role.getRoleTag().equals(ParticipantConstants.pm_leader.toString())) {
//					isPm = true;
//				}
//				if (role.getRoleTag().equals(ParticipantConstants.countryAgent.toString())) {
//					isAgent = true;
//				}
//			}
//		}
//		System.out.println("isPm: " + isPm);
//		System.out.println("isAgent: " + isAgent);
		for (UserDataRightVO row: dataRightVOs) {
			if (!row.isHasRights()) {
				continue;
			}
			Participant busiTypeRight = new Participant();
			// 如果不限制数据权限的角色，则保存PM和平台，国代和站点的关系
//			if (StringUtils.isEmpty(roleTag)) {
//				if (isPm && descType.equals(DescriptionType.platform)) {
//					busiTypeRight.setParticipantId(ParticipantConstants.pm.toString());
//				}
//				if (isAgent && descType.equals(DescriptionType.site)) {
//					busiTypeRight.setParticipantId(ParticipantConstants.countryAgent.toString());
//				}
//				busiTypeRight.setParticipantId();
//			}
//			// 否则保存角色和数据权限的关系
//			else {
//				System.out.println("save role data rights....");
//				busiTypeRight.setParticipantId(roleTag);
//			}
			busiTypeRight.setParticipantId(roleTag);
			busiTypeRight.setDescription(descType);
			busiTypeRight.setKey(String.valueOf(row.getRightsId()));
			busiTypeRight.setUsername(username);
			dataRightsList.add(busiTypeRight);
		}
	}
	
	public List<String> findUuapNameByName(List<String> userName){
		List<String> userNames = userRepository.findUuapNameByName(userName);
		if(userNames == null){
			userNames = new ArrayList<String>();
		}
		return userNames;
	}

    public User findUserByUuapName(String uuapName) {
        List<User> userList = userRepository.findByUuapName(uuapName);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
    
    @Override
	public List<User> findAllByStatus(int status){
	    return userRepository.findByStatus(status);
	}
}
