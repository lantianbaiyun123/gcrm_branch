package com.baidu.gcrm.common.auth.service;


import java.util.List;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;

/**
 * 用户数据权限接口
 * @author zhanglei35
 *
 */
public interface IUserDataRightService {
	
	/**
	 * 根据用户ucid获取所有下属人员列表
	 * @param ucId
	 * @return
	 */
	public List<User> getSubUserListByUcId(Long ucId);
	
	/**
	 * 根据用户ucid获取所有上级领导列表
	 * @param ucId
	 * @return
	 */
	public List<User> getLeaderListByUcId(Long ucId);
	
	/**
	 * 获取用户有权限的平台列表
	 * @param ucId
	 * @return
	 */
	public List<AdvertisingPlatform> getPlatformListByUcId(Long ucId);
	
	/**
	 * 根据用户名找到对应的角色数据是否属于该用户
	 * @param username 用户名
	 * @param roleTag 角色名：pm/countryAgent
	 * @param dataType 数据类型(平台、站点)
	 * @return
	 */
	public boolean isUserDataByRoleAndDataType(Long ucId, String roleTag, DescriptionType dataType, String platformId);
	
	/**
	 * 获取用户有权限的平台列表(国际化)
	 * @param ucId
	 * @param locale
	 * @return
	 */
	public List<AdvertisingPlatform> getPlatformListByUcId(Long ucId, LocaleConstants locale);
	
	/**
	 * 根据平台ID，获取平台下用户有权限的站点
	 * @param ucId
	 * @param platformId
	 * @param locale
	 * @return
	 */
	@Deprecated
	public List<Site> getSiteListByUcIdAndPlatformId(Long ucId, Long platformId, LocaleConstants locale);
	
	/**
	 * 获取用户有权限的站点列表
	 * @param ucId
	 * @return
	 */
	public List<Site> getSiteListByUcId(Long ucId, LocaleConstants locale);
	
	/**
	 * 获取用户有权限 的区域ID列表
	 * @param ucId
	 * @return
	 */
	public List<String> getRegionalIdListByUcId(Long ucId);
	
	/**
	 * 根据用户的ucid获取用户的业务类型（销售/变现）
	 * @param ucId
	 * @return
	 */
	public List<Integer> getBusinessTypeByUcId(Long ucId);
}
