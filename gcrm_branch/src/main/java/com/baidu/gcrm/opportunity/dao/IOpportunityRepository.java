package com.baidu.gcrm.opportunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.opportunity.model.Opportunity;

public interface IOpportunityRepository extends JpaRepository<Opportunity, Long> {
    
	List<Opportunity> findByCustomerNumber(Long customerNumber);
	
	@Modifying
	@Query("delete from Opportunity where customerNumber=?1")
	void deleteByCustomerNumber(Long customerNumber);
}
