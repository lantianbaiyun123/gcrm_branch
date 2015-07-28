package com.baidu.gcrm.qualification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.qualification.model.CustomerResource;

public interface ICustomerResourceDao extends JpaRepository<CustomerResource, Long>{
		
	List<CustomerResource> findByAgentQualificationId(Long agentQualificationId);
	
	@Modifying
	@Query("delete from CustomerResource where agentQualificationId=?1")
	int deleteByAgentQualificationId(Long agentQualificationId);
}