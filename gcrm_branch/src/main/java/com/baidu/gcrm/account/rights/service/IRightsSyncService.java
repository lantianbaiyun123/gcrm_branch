package com.baidu.gcrm.account.rights.service;

/**
 * 新权限数据同步服务
 * 
 * @author zhanglei35
 * 
 */
public interface IRightsSyncService {

	/**
	 * 同步新权限的所有岗位(以及岗位与所有子岗位的关系)数据到DB
	 */
	public void syncAllRightsPositionsToDB();

	/**
	 * 同步新权限的所有角色数据到DB
	 */
	public void syncAllRightsRolesToDB();

	/**
	 * 同步新权限的所有功能数据到DB
	 */
	public void syncAllRightsFuncToDB();

	/**
	 * 同步用户在新权限的岗位、角色关系数据到DB
	 * @param ucId
	 */
	public void syncUserAllDataToDB(Long ucId);
	
	/**
	 * 当用户角色变化时，更新数据权限，清空不存在的角色的数据权限
	 * @param ucId
	 */
	public void syncUserDataRights(Long ucId, String username);
	
	/**
	 * 同步所有用户的在新权限的岗位、角色关系数据到DB
	 */
	public void syncAllUserDataToDB();
	
	/**
	 * 同步新权限所有的功能数据到DB
	 */
	public void syncAllFuncToDB();
	
	/**
	 * 同步新权限所有的菜单数据到DB（包含菜单与子菜单的关系）
	 */
	public void syncAllMenuToDB();
	
	/**
	 * 同步角色的所有菜单、功能到DB
	 */
	public void syncRoleAllMenuFuncToDB(String roleTag, Long roleId);
	
	/**
	 * 同步所有角色的关联数据到DB
	 */
	public void syncAllRoleDataToDB();
	
	/**
	 * 同步所有数据到DB
	 */
	public void syncAllDataToDB();

	/**
	 * 同步新权限的所有岗位数据到Cache
	 */
	public void syncAllRightsPositionsToCache();

	/**
	 * 同步新权限的所有角色数据到Cache
	 */
	public void syncAllRightsRolesToCache();

	/**
	 * 同步新权限的所有功能数据到Cache
	 */
	public void syncAllRightsFuncToCache();

	/**
	 * 同步用户在新权限的岗位、角色关系数据到Cache
	 * @param ucId
	 */
	public void syncUserAllDataToCache(Long ucId);
	
	
	/**
	 * 同步所有用户的在新权限的岗位、角色关系数据到Cache
	 */
	public void syncAllUserDataToCache();
	
	/**
	 * 同步新权限所有的功能数据到Cache
	 */
	public void syncAllFuncToCache();
	
	/**
	 * 同步新权限所有的菜单数据到Cache（包含菜单与子菜单的关系）
	 */
	public void syncAllMenuToCache();
	
	/**
	 * 同步角色的所有菜单、功能到Cache
	 */
	public void syncRoleAllMenuFuncToCache(Long roleId);
	
	/**
	 * 同步所有角色的关联数据到Cache
	 */
	public void syncAllRoleDataToCache();
	
	/**
	 * 同步所有数据到Cache
	 */
	public void syncAllDataToCache();
}
