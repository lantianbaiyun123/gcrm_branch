package com.baidu.gcrm.account.rights.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.dao.IRightsFunctionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsPositionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsPositionSubRepository;
import com.baidu.gcrm.account.rights.dao.IRightsRoleFunctionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsRoleRepository;
import com.baidu.gcrm.account.rights.dao.IRightsUserPositionRepository;
import com.baidu.gcrm.account.rights.dao.IRightsUserRoleRepository;
import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsPositionSub.PositionSubType;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;

/**
 * 用户功能权限、角色服务类
 * @author zhanglei35
 *
 */
@Service
public class UserRightsServiceImpl implements IUserRightsService {
	@Autowired
	IRightsPositionRepository positionRepository;
	
	@Autowired
	IRightsPositionSubRepository positionSubRepository;
	
	@Autowired
	IRightsUserPositionRepository userPosRepository;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IRightsUserRoleRepository userRoleRepository;
	
	@Autowired
	IRightsRoleFunctionRepository roleFuncRepository;
	
	@Autowired
	IRightsFunctionRepository funcRepository;
	
	@Autowired
	IRightsRoleRepository roleRepository;
	
	@Autowired
	RightsRoleServiceImpl roleService;
	
	@Autowired
    I18nKVService i18nService;
	
	@Override
	public List<User> findDirectLeaderByUcId(Long ucId) {
		List<User> leaderList = new ArrayList<User>();
		List<Long> ucIdList = new ArrayList<Long>();
		List<RightsPosition> posList = userPosRepository.findPosByUcId(ucId);
		for (RightsPosition position : posList) {
			String [] pidArray = position.getPosParentIds().split(",");
			List<Long> posParentIdList = new ArrayList<Long>();
			for (String pid : pidArray) {
				if (!posParentIdList.contains(Long.valueOf(pid))) {
					posParentIdList.add(Long.valueOf(pid));
				}
			}
			if (posParentIdList.size() < 1) {
				continue;
			}
			List<Object[]> resultList = userPosRepository.findPosUserByPosIds(posParentIdList);
			for (Object[] row : resultList) {
				User posUser = (User) row[1];
				// 去除重复用户
				if (!ucIdList.contains(posUser.getUcid())) {
					ucIdList.add(posUser.getUcid());
					leaderList.add(posUser);
				}
			}
		}
		return leaderList;
	}

	@Override
	public List<RightsRole> findUserRolesByUcId(Long ucId) {
		return userRoleRepository.findRolesByUcId(ucId);
	}
	
	@Override
	public List<User> findUsersByRoleId(Long roleId) {
		return userRoleRepository.findUsersByRoleId(roleId);
	}
	
	@Override
	public List<User> findUserByRoleTag(String roleTag) {
		return userRoleRepository.findUsersByRoleTag(roleTag);
	}
	
	@Override
	public List<RightsRole> findUserRolesByUcId(Long ucId, LocaleConstants locale) {
		List<RightsRole> roleList = userRoleRepository.findRolesByUcId(ucId);
		i18nService.fillI18nInfo(roleList, locale);
		return roleList;
	}

	@Override
	public List<RightsPosition> findUserPositionsByUcId(Long ucId) {
		return userPosRepository.findPosByUcId(ucId);
	}
	
	@Override
	public List<RightsPosition> findUserDirectParentPos(Long ucId) {
		List<RightsPosition> posList = userPosRepository.findPosByUcId(ucId);
		List<Long> posParentIdList = new ArrayList<Long>();
		for (RightsPosition position : posList) {
			String [] pidArray = position.getPosParentIds().split(",");
			for (String pid : pidArray) {
				if (!posParentIdList.contains(Long.valueOf(pid))) {
					posParentIdList.add(Long.valueOf(pid));
				}
			}
		}
		if (posParentIdList.size() < 1) {
			return new ArrayList<RightsPosition>();
		}
		return positionRepository.findByPosIds(posParentIdList);
	}
	
	@Override
	public List<RightsPosition> findUserDirectSubPos(Long ucId) {
		List<RightsPosition> resultList = new ArrayList<RightsPosition>();
		List<RightsPosition> tempList = new ArrayList<RightsPosition>();
		List<RightsPosition> posList = userPosRepository.findPosByUcId(ucId);
		for (RightsPosition position : posList) {
			tempList.addAll(positionSubRepository.findSubPosByIdAndType(position.getPosId(), PositionSubType.DIRECT));
		}
		for (RightsPosition pos : tempList) {
			if (!resultList.contains(pos)) {
				resultList.add(pos);
			}
		}
		return resultList;
	}
	
	public boolean isAdminRole(Long ucId){
		List<RightsRole> userRoleList = findUserRolesByUcId(ucId);
		// 如果用户包含管理员权限，则返回所有URL
		boolean isAdmin = false;
		for (RightsRole role : userRoleList) {
			if (role.getRoleTag().contains("system_admin")) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}

	@Override
	public RightsUrlVO findUserUrlVOs(Long ucId) {
		if (isAdminRole(ucId)) {
			return new RightsUrlVO();
		}
		RightsUrlVO rightsUrlVO = new RightsUrlVO();
		List<Object[]> resultList = roleFuncRepository.findMenuCodesByUcId(ucId);
		List<String> menuCodeList = new ArrayList<String>();
		for (Object[] row : resultList) {
			if (null != row[0] && !menuCodeList.contains(row[0])) {
				menuCodeList.add(row[0].toString());
			}
			if (null != row[1] && !menuCodeList.contains(row[1])) {
				menuCodeList.add(row[1].toString());
			}
		}
		rightsUrlVO.setMenuCodeList(menuCodeList);
		List<String> funcCodeList = roleFuncRepository.findFuncCodesByUcId(ucId);
		rightsUrlVO.setButtonCodeList(funcCodeList);
		return rightsUrlVO;
	}
	
	@Override
	public List<String> findUserUrls(Long ucId) {
		if (isAdminRole(ucId)) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
//		result.addAll(roleFuncRepository.findMenuURLsByUcId(ucId));  只有非菜单才会被拦截器拦截
		result.addAll(roleFuncRepository.findFuncURLsByUcId(ucId));
		return result;
	}
	
	@Override
	public List<String> findAllFuncUrls() {
		return funcRepository.findFuncURLs();
	}
	
	@Override
	public List<RightsRole> findSysAllRole() {
		List<RightsRole> roleList = roleService.getAll().size() < 1 ? roleRepository.findAll() : roleService.getAll();
		return roleList;
	}
	
	@Override
	public List<RightsRole> findSysAllRole(LocaleConstants locale) {
		List<RightsRole> roleList = roleService.getAll().size() < 1 ? roleRepository.findAll() : roleService.getAll();
		i18nService.fillI18nInfo(roleList, locale);
		return roleList;
	}
	
	@Override
	public List<User> findUserByPosTag(String posTag) {
		RightsPosition pos = positionRepository.findByPosTag(posTag);
		if (null == pos) {
			return new ArrayList<User>();
		}
		List<Long> posIds = new ArrayList<Long>();
		posIds.add(pos.getPosId());
		return userPosRepository.findUsersByPosIds(posIds);
	}

	@Override
	public List<User> findPosUserByRoleTag(ParticipantConstants roleTag, Long ucId) {
		List<Long> roleUcIds = userRoleRepository.findUcIdsByRoleTag(roleTag.toString());
		return findLeaderByUcIdAndRoleUser(ucId, roleUcIds);
	}
	
	private List<User> findLeaderByUcIdAndRoleUser(Long ucId, List<Long> roleUcIds){
		List<RightsPosition> posList = findUserDirectParentPos(ucId);
		if (null == posList || posList.size() < 1) {
			return new ArrayList<User>();
		}
		System.out.println("-------------------------" + posList.get(0).getPosTag() +"    " +posList.get(0).getPosName() +"-------------------------");
		List<User> directLeader = findUserByPosTag(posList.get(0).getPosTag());
		if (null == directLeader || directLeader.size() < 1) {
			return new ArrayList<User>();
		}
		if (roleUcIds.contains(directLeader.get(0).getUcid())) {
			return directLeader;
		}
		return findLeaderByUcIdAndRoleUser(directLeader.get(0).getUcid(), roleUcIds);
	}
	
	@Override
	public List<User> findLeaderByUcIdAndPosTag(Long ucId, List<String> posTagList) {
		List<RightsPosition> posList = findUserDirectParentPos(ucId);
		if (null == posList || posList.size() < 1) {
			return new ArrayList<User>();
		}
		System.out.println("-------------------------" + posList.get(0).getPosTag() +"    " +posList.get(0).getPosName() +"-------------------------");
		List<User> directLeader = findUserByPosTag(posList.get(0).getPosTag());
		if (null == directLeader || directLeader.size() < 1) {
			return new ArrayList<User>();
		}
		if (posTagList.contains(posList.get(0).getPosTag())) {
			return directLeader;
		}
		return findLeaderByUcIdAndPosTag(directLeader.get(0).getUcid(), posTagList);
	}
	
	@Override
	public List<String> findOwnerOperFuncs() {
		String ownerOperFuncs =  GcrmConfig.getConfigValueByKey("dataRight.owner_oper_funcs");
		return Arrays.asList(ownerOperFuncs.split(","));
	}
}
