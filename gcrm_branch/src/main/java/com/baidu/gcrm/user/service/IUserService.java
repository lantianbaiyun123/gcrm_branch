package com.baidu.gcrm.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.gcrm.account.model.Account;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.web.helper.UserListBean;
import com.baidu.gcrm.user.web.vo.DataRightsVO;
import com.baidu.gcrm.user.web.vo.UserConditionVO;
import com.baidu.gcrm.user.web.vo.UserDetailInfoVO;

public interface IUserService {
	
	public User findByUsername(String username);
	
	public User findByUcid(Long ucid);
	
	Map<Long,User> findByUcidIn(Set<Long> ucids);

	public List<User> findByName(String username);
	/**
	* 功能描述：   获取全部人员
	* 创建人：yudajun    
	* 创建时间：2014-5-7 下午5:45:58   
	* 修改人：yudajun
	* 修改时间：2014-5-7 下午5:45:58   
	* 修改备注：   
	* 参数： @return
	* @version
	 */
	public List<User> findAll();
	
	Map<Long, User> findAllUsersMap();
	
	/**
	 * 保存用户信息
	 * @author zhanglei35
	 * @param user
	 * @throws CRMBaseException
	 */
	public void saveUser(User user);
	/**
	 * 判断用户名是否存在
	 * @author zhanglei35
	 * @param user
	 * @return
	 */
	public boolean isUsernameExists(User user);
	
	/**
	 * 分页查询
	 * @author zhanglei35
	 * @param condition
	 * @param locale
	 * @return
	 */
	Page<UserListBean> findByCondition(UserConditionVO condition, LocaleConstants locale);
	
	/**
	 * 设置用户的数据权限
	 * @author zhanglei35
	 * @param username
	 * @return
	 */
	public void setUserDataRights(UserDetailInfoVO userDetail, String username, LocaleConstants locale);
	
	/**
	 * 设置用户的所有角色
	 * @author zhanglei35
	 * @param userDetail
	 * @param ucId
	 * @param locale
	 */
	public void setUserRoleNames(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale);
	
	/**
	 * 设置用户的上级、下级岗位
	 * @author zhanglei35
	 * @param userDetail
	 * @param ucId
	 * @param locale
	 */
	public void setUserParentAndSubPos(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale);
	
	/**
	 * 查询用户详细信息
	 * @author zhanglei35
	 * @param userDetail
	 * @param ucId
	 * @param locale
	 */
	public void findUserDetailInfo(UserDetailInfoVO userDetail, Long ucId, LocaleConstants locale);
	
	/**
	 * 查询所有数据权限，以及用户拥有的权限
	 * @author zhanglei35
	 * @param username
	 * @return
	 */
	public DataRightsVO findUserDataRightsVO(String username, String roleTag, LocaleConstants locale);
	
	/**
	 * 保存用户数据权限
	 * @param dataRightsVO
	 */
	public void saveUserDataRights(List<DataRightsVO> dataRightsList);
	
	public Account getAccountByUcId(Long ucId);
	
	public List<String> findUuapNameByName(List<String> userName);
	
	public User findUserByUuapName(String uuapName);
	
	public List<User> findAllByStatus(int status);
}
