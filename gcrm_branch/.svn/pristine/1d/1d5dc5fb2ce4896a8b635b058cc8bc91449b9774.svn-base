package com.baidu.gcrm.account.rights.service;

import java.util.List;

import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.vo.RightsUrlVO;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.user.model.User;

/**
 * 用户功能权限接口
 * @author zhanglei35
 *
 */
public interface IUserRightsService {
	
	/**
	 * 查询用户的直接上级用户
	 * @param ucId
	 * @return List<User> 上级用户
	 */
	public List<User> findDirectLeaderByUcId(Long ucId);

	/**
	 * 查询用户的所有角色数据
	 * @param ucId
	 * @return
	 */
	public List<RightsRole> findUserRolesByUcId(Long ucId);
	
	/**
	 * 查询用户的所有角色（国际化）
	 * @param ucId
	 * @param locale
	 * @return
	 */
	public List<RightsRole> findUserRolesByUcId(Long ucId, LocaleConstants locale);
	
	/**
	 * 根据角色ID查找用户列表
	 * @param roleId
	 * @return
	 */
	public List<User> findUsersByRoleId(Long roleId);
	
	/**
	 * 根据岗位Tag查找用户列表
	 * @param posTag
	 * @return
	 */
	public List<User> findUserByPosTag(String posTag);
	
	/**
	 * 根据角色英文名返回用户列表
	 * @param roleTag
	 * @return
	 */
	public List<User> findUserByRoleTag(String roleTag);

	/**
	 * 查询用户的所有岗位数据
	 * @param ucId
	 * @return
	 */
	public List<RightsPosition> findUserPositionsByUcId(Long ucId);
	
	/**
	 * 查询用户的上级岗位
	 * @param ucId
	 * @return
	 */
	public List<RightsPosition> findUserDirectParentPos(Long ucId);
	
	/**
	 * 查询用户的下级岗位
	 * @param ucId
	 * @return
	 */
	public List<RightsPosition> findUserDirectSubPos(Long ucId);

	/**
	 * 查询用户的所有权限的URL Code
	 * @param ucId
	 * @return
	 */
	public RightsUrlVO findUserUrlVOs(Long ucId);
	
	/**
	 * 判断是否是系统管理员
	 * @param ucId
	 * @return
	 */
	public boolean isAdminRole(Long ucId);
	
	/**
	 * 查询用户的所有有权限的URL
	 * @param ucId
	 * @return
	 */
	public List<String> findUserUrls(Long ucId);
	
	/**
	 * 查找所有功能URL
	 * @return
	 */
	public List<String> findAllFuncUrls();
	
	/**
	 * 获取系统的所有角色
	 * @return
	 */
	public List<RightsRole> findSysAllRole();
	
	/**
	 * 获取系统的所有角色
	 * @return
	 */
	public List<RightsRole> findSysAllRole(LocaleConstants locale);
	
	/**
	 * 根据角色名获取对应的岗位人员
	 * @param roleTag
	 * @param ucId
	 * @return
	 */
	public List<User> findPosUserByRoleTag(ParticipantConstants roleTag, Long ucId);
	
	/**
	 * 递归查找user的指定岗位的领导
	 * @param ucId
	 * @param posTagList
	 * @return
	 */
	public List<User> findLeaderByUcIdAndPosTag(Long ucId, List<String> posTagList);
	
	/**
	 * 查找仅能自己修改的功能code列表
	 * @return
	 */
	public List<String> findOwnerOperFuncs();
}
