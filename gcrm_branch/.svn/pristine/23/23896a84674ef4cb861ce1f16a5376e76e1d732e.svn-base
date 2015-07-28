package com.baidu.gcrm.contact.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.contact.model.ContactPerson;

public interface IContactRepository extends JpaRepository<ContactPerson, Long> {
	
	@Modifying
	@Query("update ContactPerson c set c.customerNumber = ?1 where c.customerNumber = ?2")
	int updateCustomerId(Long newCustomerNumber,Long oldCustomerNumber);
	
	List<ContactPerson> findByCustomerNumber(Long customerNumber);
	
	@Modifying
	@Query("delete from ContactPerson where customerNumber = ?1")
	void deleteByCustomerNumber(Long customerNumber);
	
}
