package com.baidu.gcrm.account.rights.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.dao.IRightsFunctionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsMenuRepository;
import com.baidu.gcrm.account.rights.dao.IRightsPositionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsPositionSubRepository;
import com.baidu.gcrm.account.rights.dao.IRightsRoleFunctionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsRoleRepository;
import com.baidu.gcrm.account.rights.dao.IRightsUserPositionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsUserRoleRepository;
import com.baidu.gcrm.account.rights.model.RightsFunction;
import com.baidu.gcrm.account.rights.model.RightsMenu;
import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsPositionSub;
import com.baidu.gcrm.account.rights.model.RightsPositionSub.PositionSubType;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.model.RightsRoleFunction;
import com.baidu.gcrm.account.rights.model.RightsUserPosition;
import com.baidu.gcrm.account.rights.model.RightsUserRole;
import com.baidu.gcrm.account.rights.service.IRightsSyncService;
import com.baidu.gcrm.ad.approval.dao.ParticipantRepository;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.model.BaseOperationModel;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;
import com.baidu.rigel.crm.rights.bo.AuthInfo;
import com.baidu.rigel.crm.rights.bo.Menu;
import com.baidu.rigel.crm.rights.bo.PosRoleInfo;
import com.baidu.rigel.crm.rights.bo.Position;
import com.baidu.rigel.crm.rights.bo.RoleInfo;
import com.baidu.rigel.crm.rights.bo.UserPosRole;
import com.baidu.rigel.crm.rights.service.AuthExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.MenuServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewAcctExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewPositionExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewRoleExtServiceWrapper;

/**
 * 新权限数据同步服务
 * 
 * @author zhanglei35
 * 
 */
@Service("rightsSyncServiceImpl")
public class RightsSyncServiceImpl implements IRightsSyncService {
	Logger logger = LoggerFactory.getLogger(RightsSyncServiceImpl.class);
	
	private static Map<String, String> roleTagMap = new HashMap<String, String>();
	static{
		roleTagMap.put("realization_leader", "cash_leader");
		roleTagMap.put("finance", "finance_manager");
		roleTagMap.put("legal_personnel", "law_manager");
		roleTagMap.put("thailand_agent", "countryAgent");
		roleTagMap.put("sales_supervisor", "sales_manager");
		roleTagMap.put("department_director", "dept_manager");
		roleTagMap.put("internationalization_cfo", "global_cfo");
		roleTagMap.put("executive_director", "global_ceo");
		roleTagMap.put("business_personnel", "business_man");
		roleTagMap.put("common_pm", "pm");
	}
	
	private static Map<String, String> roleTagReverseMap = new HashMap<String, String>();

	@Value("#{appproperties['cas.appid']}")
	public Integer appid;

	@Value("#{appproperties['newrights.sysunitid']}")
	public Integer sysunitid;
	
	@Value("#{appproperties['newrights.sysname']}")
	public String sysname;

	@Autowired
	IRightsRoleRepository roleRepository;

	@Autowired
	IRightsPositionRepository positionRepository;

	@Autowired
	IRightsFunctionRepository functionRepository;

	@Autowired
	IRightsMenuRepository menuRepository;
	
	@Autowired
	IRightsPositionSubRepository positionSubRepository;
	
	@Autowired
	IRightsUserPositionRepository userPosRepository;
	
	@Autowired
	IRightsUserRoleRepository userRoleRepository;
	
	@Autowired
	IRightsRoleFunctionRepository roleFuncRepository;
	
	@Autowired
	IUserService userService;
	
	@Autowired
    I18nKVService i18nService;
	
	@Autowired
    IValuelistWithCacheService valuelistCacheService;
	
	@Autowired
    private ITableMetaDataManger talbeManager;
	
	@Autowired
	ParticipantRepository particRepository;
	
	@Override
	public void syncAllRightsPositionsToDB() {
		// 清空所有数据
		positionRepository.deleteAllInBatch();
		positionSubRepository.deleteAllInBatch();
		// 保存所有岗位信息
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<Position> posList = positionService.getAllPositionList(appid);
		List<RightsPosition> rightsPosList = new ArrayList<RightsPosition>();
		List<RightsPositionSub> rightsPosSubList = new ArrayList<RightsPositionSub>();
		for (Position position : posList) {
			RightsPosition rightsPos = new RightsPosition();
			long posId = (long)position.getPosid();
			rightsPos.setPosId(posId);
			rightsPos.setPosName(position.getPosname());
			rightsPos.setPosDelMark(position.getDelmark());
			rightsPos.setPosTag(position.getPostag());
			rightsPos.setPosParentIds(position.getPosparentids());
			genOperProperty(rightsPos);
			rightsPosList.add(rightsPos);
			
			// 保存岗位与子岗位的层级关系
			List<Position> subPosList = positionService.getAllSonPositionList(posId, false, -1, false, appid);
			List<Position> directPosList = positionService.getDirectSonPositionList(posId, false, false, appid);
			for (Position subPos : subPosList) {
				RightsPositionSub rightsPosSub = new RightsPositionSub();
				rightsPosSub.setPosId(posId);
				rightsPosSub.setSubId((long)subPos.getPosid());
				PositionSubType directSub = directPosList.contains(subPos) ? PositionSubType.DIRECT : PositionSubType.INDIRECT;
				rightsPosSub.setDirectSub(directSub);
 				genOperProperty(rightsPosSub);
				rightsPosSubList.add(rightsPosSub);
			}
		}
		positionRepository.save(rightsPosList);
		positionSubRepository.save(rightsPosSubList);
	}

	@Override
	public void syncAllRightsRolesToDB() {
		roleRepository.deleteAllInBatch();
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		List<RightsRole> rightsRoleList = new ArrayList<RightsRole>();
		List<RoleInfo> roleList = roleServiceWrapper.getRolesBatch(appid, sysunitid, null);
		for (RoleInfo roleInfo : roleList) {
			RightsRole rightsRole = new RightsRole();
			rightsRole.setRoleDesc(roleInfo.getDescr());
			String roleTagStr = roleInfo.getRoletag();
			roleTagStr = roleTagMap.containsKey(roleTagStr) ? roleTagMap.get(roleTagStr) : roleTagStr;
			if (!roleTagStr.equals(roleInfo.getRoletag())) {
				roleTagReverseMap.put(roleTagStr, roleInfo.getRoletag());
			}
			rightsRole.setRoleTag(roleTagStr);
			rightsRole.setRoleId((long)roleInfo.getRoleid());
			genOperProperty(rightsRole);
			rightsRoleList.add(rightsRole);
		}
		roleRepository.save(rightsRoleList);
		for (RightsRole role : rightsRoleList) {
			List<LocaleVO> i18nDataList = new ArrayList<LocaleVO>();
			LocaleVO localeUS = new LocaleVO();
			localeUS.setLocale(LocaleConstants.en_US);
			localeUS.setValue(role.getRoleTag());
			LocaleVO localeCN = new LocaleVO();
			localeCN.setLocale(LocaleConstants.zh_CN);
			localeCN.setValue(role.getRoleDesc());
			i18nDataList.add(localeUS);
			i18nDataList.add(localeCN);
			i18nService.deleteById(RightsRole.class, role.getId());
	        i18nService.save(RightsRole.class, role.getId(), i18nDataList);
	        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_rights_role"));
	        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_i18n"));
		}
	}

	/**
	 * 新权限接口未准备好
	 */
	@Override
	public void syncAllRightsFuncToDB() {
		functionRepository.deleteAllInBatch();
		AuthExtServiceWrapper authService = ServiceBeanFactory.getAuthService();
		List<RightsFunction> funcList = new ArrayList<RightsFunction>();
		List<AuthInfo> authInfos = authService.getAuthListByUnitId((long)sysunitid);
		for (AuthInfo authInfo : authInfos) {
			RightsFunction function = new RightsFunction();
			function.setFuncId((long)authInfo.getAuthid());
			function.setFuncDesc(authInfo.getDescr());
			function.setFuncTag(authInfo.getAuthtag());
			function.setFuncURL(authInfo.getAuthvalue());
			genOperProperty(function);
			funcList.add(function);
		}
		functionRepository.save(funcList);
	}
	
	@Override
	public void syncUserAllDataToDB(Long ucId) {
		if (!isUcUser(ucId)) {
			return;
		}
		System.out.println("------------------------------user "+ ucId + " sync begin---------------------------");
		userPosRepository.deleteByUcId(ucId);
		userRoleRepository.deleteByUcId(ucId);
		User user = userService.findByUcid(ucId);
		NewPositionExtServiceWrapper positionService = ServiceBeanFactory.getNewPositionService();
		List<RightsUserPosition> userPosList = new ArrayList<RightsUserPosition>();
		List<RightsUserRole> userRoleList = new ArrayList<RightsUserRole>();
		List<UserPosRole> posRoleListByAppid = positionService.getPosRoleListByUcid(ucId, appid);
		List<Long> roleIdList = new ArrayList<Long>();
		boolean isPM = false;
		boolean isCountryAgent = false;
		for (UserPosRole upr : posRoleListByAppid) {
			// 同步用户的岗位关系数据
			RightsUserPosition userPos = new RightsUserPosition();
			userPos.setPosId(upr.getPosid());
			userPos.setUcId(ucId);
			genOperProperty(userPos);
			userPosList.add(userPos);
			// 同步用户的角色关系数据
			List<PosRoleInfo> posRoleInfoList = upr.getRoleinfo();
			for (PosRoleInfo posRoleInfo : posRoleInfoList) {
				RightsUserRole userRole = new RightsUserRole();
				Long roleId = posRoleInfo.getRoleid();
				if (roleIdList.contains(roleId)) {
					continue;
				}
				userRole.setRoleId(roleId);
				userRole.setUcId(ucId);
				genOperProperty(userRole);
				userRoleList.add(userRole);
				roleIdList.add(roleId);
			}
		}
		// 更新数据权限表的角色信息
	/*	List<Participant> particList = new ArrayList<Participant>();
		if (isPM) {
			List<Participant> pmList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.platform);
			for (Participant row : pmList) {
				row.setParticipantId(ParticipantConstants.pm_leader.toString());
				particList.add(row);
			}
			// 不是国代则清除原有数据
			if (!isCountryAgent) {
				List<Participant> siteList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.site);
				for (Participant row : siteList) {
					row.setParticipantId("");
					particList.add(row);
				}
			}
		}else if (isCountryAgent) {
			List<Participant> siteList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.site);
			for (Participant row : siteList) {
				row.setParticipantId(ParticipantConstants.countryAgent.toString());
				particList.add(row);
			}
			// 不是PM则清除原有数据
			if (!isPM) {
				List<Participant> platformList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.platform);
				for (Participant row : platformList) {
					row.setParticipantId("");
					particList.add(row);
				}
			}
		}else {   // 既不是PM也不是国代
			List<Participant> platformList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.platform);
			List<Participant> siteList = particRepository.findByUsernameAndDescription(user.getUsername(), DescriptionType.site);
			for (Participant row : platformList) {
				row.setParticipantId("");
				particList.add(row);
			}
			for (Participant row : siteList) {
				row.setParticipantId("");
				particList.add(row);
			}
		}
		*/
		userPosRepository.save(userPosList);
		userRoleRepository.save(userRoleList);
	}
	
	@Override
	public void syncUserDataRights(Long ucId, String username) {
		List<String> roleIdList = userRoleRepository.findRoleTagByUcId(ucId);
		if (null == roleIdList || roleIdList.size() < 1) {
			return;
		}
		particRepository.deleteByUnameAndNotExistPartIds(username, roleIdList);
	}
	
	@Override
	public void syncAllUserDataToDB() {
		List<User> userList = userService.findAll();
		for (User user : userList) {
			syncUserAllDataToDB(user.getUcid());
			System.out.println("------------------------------user "+ user.getUcid()+" sync done---------------------------");
		}
	}
	
	/**
	 * 待新权限获取所有功能的接口准备好后废弃此方法
	 */
	@Override
	@Deprecated
	public void syncAllFuncToDB(){
		functionRepository.deleteAllInBatch();
		List<RightsRole> roleList = roleRepository.findAll();
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		Set<Long> funcIdSet = new HashSet<Long>();
		List<RightsFunction> funcList = new ArrayList<RightsFunction>();
		for (RightsRole rightsRole : roleList) {
			List<AuthInfo> authList = roleServiceWrapper.getAuthListByRoleTag(rightsRole.getRoleTag(), appid);
			for (AuthInfo authInfo : authList) {
				RightsFunction function = new RightsFunction();
				function.setFuncId((long)authInfo.getAuthid());
				function.setFuncTag(authInfo.getAuthtag());
				function.setFuncDesc(authInfo.getDescr());
				function.setFuncURL(authInfo.getAuthvalue());
				genOperProperty(function);
				if (!funcIdSet.contains(function.getFuncId())) {
					funcList.add(function);
					funcIdSet.add(function.getFuncId());
				}
			}
		}
		functionRepository.save(funcList);
	}

	@Override
	public void syncAllMenuToDB() {
		menuRepository.deleteAllInBatch();
		MenuServiceWrapper menuService = ServiceBeanFactory.getMenuService();
		List<RightsMenu> rightsMenus = new ArrayList<RightsMenu>();
		List<Menu> menuList = menuService.getAllEnabledMenuByAppName(sysname);
		for (Menu menu : menuList) {
			RightsMenu rightsMenu = new RightsMenu();
			rightsMenu.setMenuId(menu.getId());
			rightsMenu.setMenuName(menu.getName());
			rightsMenu.setMenuURL(menu.getUrl());
			rightsMenu.setFuncTag(menu.getAuthTag());
			genOperProperty(rightsMenu);
			rightsMenus.add(rightsMenu);
			// 与子菜单关联关系
			List<Menu> menuSubList = menu.getChildren();
			for (Menu menuSub : menuSubList) {
				RightsMenu menu_sub = new RightsMenu();
				menu_sub.setMenuId(menuSub.getId());
				menu_sub.setMenuName(menuSub.getName());
				menu_sub.setMenuURL(menuSub.getUrl());
				menu_sub.setFuncTag(menuSub.getAuthTag());
				menu_sub.setParentMenuId(rightsMenu.getMenuId());
				genOperProperty(menu_sub);
				rightsMenus.add(menu_sub);
			}
		}
		menuRepository.save(rightsMenus);
	}

	@Override
	public void syncRoleAllMenuFuncToDB(String roleTag, Long roleId) {
		roleFuncRepository.deleteByRoleId(roleId);
		NewRoleExtServiceWrapper roleServiceWrapper = ServiceBeanFactory.getRoleService();
		List<RightsRoleFunction> roleFuncList = new ArrayList<RightsRoleFunction>();
		List<AuthInfo> authList = roleServiceWrapper.getAuthListByRoleTag(roleTag, appid);
		for (AuthInfo authInfo : authList) {
			RightsRoleFunction roleFunc = new RightsRoleFunction();
			roleFunc.setRoleId(roleId);
			roleFunc.setFuncId((long)authInfo.getAuthid());
			roleFunc.setFuncTag(authInfo.getAuthtag());
			roleFunc.setFuncURL(authInfo.getAuthvalue());
			genOperProperty(roleFunc);
			roleFuncList.add(roleFunc);
		}
		roleFuncRepository.save(roleFuncList);
	}
	
	@Override
	public void syncAllRoleDataToDB() {
		List<RightsRole> roleList = roleRepository.findAll();
		for (RightsRole rightsRole : roleList) {
			String roleTag = rightsRole.getRoleTag();
			if (roleTagReverseMap.containsKey(roleTag)) {
				roleTag = roleTagReverseMap.get(roleTag);
			}
			syncRoleAllMenuFuncToDB(roleTag, rightsRole.getRoleId());
		}
	}

	@Override
	public void syncAllDataToDB() {
		try {
			syncAllRightsPositionsToDB();
			System.out.println("------------------------------position sync done---------------------------");
			syncAllRightsRolesToDB();
			System.out.println("------------------------------role sync done---------------------------");
			syncAllFuncToDB();
			System.out.println("------------------------------func sync done---------------------------");
			syncAllMenuToDB();
			System.out.println("------------------------------menu sync done---------------------------");
			syncAllUserDataToDB();
			System.out.println("------------------------------user data sync done---------------------------");
			syncAllRoleDataToDB();
			System.out.println("------------------------------role data sync done---------------------------");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void syncAllRightsPositionsToCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public void syncAllRightsRolesToCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public void syncAllRightsFuncToCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public void syncUserAllDataToCache(Long ucId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void syncAllUserDataToCache() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void syncAllFuncToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncAllMenuToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncRoleAllMenuFuncToCache(Long roleId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void syncAllRoleDataToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncAllDataToCache() {
		// TODO Auto-generated method stub
		
	}

	private void genOperProperty(BaseOperationModel model){
		model.setCreateOperator(0L);
		model.setUpdateOperator(0L);
		model.setCreateTime(new Date());
		model.setUpdateTime(new Date());
	}
	
	/**
	 * 判断是否是UC用户
	 * @return
	 */
	private boolean isUcUser(Long ucId){
		NewAcctExtServiceWrapper acctExtServiceWrapper = ServiceBeanFactory.getAcctExtService();
		if (null == acctExtServiceWrapper.getUserByUcid(ucId, appid)) {
			return false;
		}
		return true;
	}

}
