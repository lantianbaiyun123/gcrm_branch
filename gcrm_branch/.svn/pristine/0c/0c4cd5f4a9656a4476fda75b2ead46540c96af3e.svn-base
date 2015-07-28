package com.baidu.gcrm.account.rights.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.account.rights.model.RightsUserRole;
import com.baidu.gcrm.user.model.User;

public interface IRightsUserRoleRepository extends JpaRepository<RightsUserRole, Long> {
	@Modifying
	@Query("Delete From RightsUserRole Where ucId = ?1")
	public void deleteByUcId(Long ucId);
	
	@Query("Select distinct rr From RightsUserRole rur, RightsRole rr Where rur.roleId = rr.roleId And rur.ucId = ?1")
	public List<RightsRole> findRolesByUcId(Long ucId);
	
	@Query("Select distinct rr.roleTag From RightsUserRole rur, RightsRole rr Where rur.roleId = rr.roleId And rur.ucId = ?1")
	public List<String> findRoleTagByUcId(Long ucId);
	
	@Query("Select distinct u From RightsUserRole rur, User u Where u.ucid = rur.ucId And rur.roleId = ?1")
	public List<User> findUsersByRoleId(Long roleId);
	
	@Query("Select distinct u From RightsUserRole rur, User u, RightsRole rr Where u.ucid = rur.ucId And rur.roleId = rr.roleId And rr.roleTag = ?1")
	public List<User> findUsersByRoleTag(String roleTag);
	
	@Query("Select distinct u.ucid From RightsUserRole rur, User u, RightsRole rr Where u.ucid = rur.ucId And rur.roleId = rr.roleId And rr.roleTag = ?1")
	public List<Long> findUcIdsByRoleTag(String roleTag);
}
