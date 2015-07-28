package com.baidu.gcrm.qualification.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.qualification.model.Qualification;

public interface IQualificationDao extends JpaRepository<Qualification, Long>{
	
	@Modifying
	@Query("update Qualification q set q.customerNumber = ?1 where q.customerNumber = ?2")
	int updateCustomerNumber(Long newCustomerNumber,Long olCcustomerNumber);
	
	Qualification findByCustomerNumber(Long customerNumber);
	
	@Modifying
	@Query("delete from Qualification where customerNumber=?1")
	int deleteByCustomerNumber(Long customerNumber);
	
}
