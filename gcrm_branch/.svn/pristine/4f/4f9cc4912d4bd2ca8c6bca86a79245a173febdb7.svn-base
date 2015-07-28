package com.baidu.gcrm.account.rights.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsRoleFunction;

public interface IRightsRoleFunctionRepository extends JpaRepository<RightsRoleFunction, Long> {
	@Modifying
	@Query("Delete From RightsRoleFunction Where roleId = ?1")
	public void deleteByRoleId(Long roleId);
	
	@Query("Select distinct(rf.funcTag) From RightsRoleFunction rrf, RightsFunction rf, RightsUserRole rur "
			+ "Where rf.funcId = rrf.funcId And rrf.roleId = rur.roleId And rur.ucId = ?1")
	public List<String> findFuncCodesByUcId(Long ucId);
	
	@Query(value = "SELECT m.menu_name, m_p.menu_name top FROM g_rights_role_func rf "
			+ "INNER JOIN g_rights_user_role ur ON ur.role_id = rf.role_id  "
			+ "INNER JOIN g_rights_func f ON f.func_id = rf.func_id "
			+ "INNER JOIN g_rights_menu m ON m.func_tag = f.func_tag "
			+ "LEFT JOIN g_rights_menu m_p ON m_p.menu_id = m.parent_menu_id "
			+ "WHERE ur.uc_id = ?1 AND m.menu_name is not null ", nativeQuery = true)
	public List<Object[]> findMenuCodesByUcId(Long ucId);
	
	@Query("Select distinct(rm.menuURL) From RightsRoleFunction rrf, RightsFunction rf, RightsMenu rm, RightsUserRole rur "
			+ "Where rm.funcTag = rf.funcTag And rf.funcId = rrf.funcId And rrf.roleId = rur.roleId And rur.ucId = ?1 ")
	public List<String> findMenuURLsByUcId(Long ucId);
	
	@Query("Select distinct(rf.funcURL) From RightsRoleFunction rrf, RightsFunction rf, RightsUserRole rur "
			+ " Where rf.funcId = rrf.funcId And rrf.roleId = rur.roleId And rur.ucId = ?1 ")
	public List<String> findFuncURLsByUcId(Long ucId);
	
}
