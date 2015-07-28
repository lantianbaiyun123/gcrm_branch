package com.baidu.gcrm.user.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.user.model.User;

public interface IUserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	
	User findByUcid(Long ucid);
	
	User findByEmail(String email);
	
	List<User> findByUcidIn(Set<Long> ucids);
	
	@Query("select a from User a where a.ucid is not null and status = 1 and (a.username like ?1 or a.realname like ?1) and email <> ?2 ")
	public List<User> findByName(String username, String excludeUserMail);
	
	@Query("select uuapName from User where username in (?1)")
	public List<String> findUuapNameByName(List<String> userName);
	
	List<User> findByUuapName(String uuapName);
	
	@Query("from User where status =?1")
	List<User> findByStatus(int status);
}
