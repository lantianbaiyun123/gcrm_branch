package com.baidu.gcrm.opportunity.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.opportunity.model.OpportunityPlatform;

public interface IOpportunityPlatformRepository extends JpaRepository<OpportunityPlatform, Long> {
	List<OpportunityPlatform> findByOpportunityId(Long opportunityId);
	//List<OpportunityPlatform> deleteByOpportunityId(Long opportunityId);
	@Modifying
	@Query(value="delete p from g_opportunity_platform p,g_opportunity o where p.opportunity_id=o.id and o.customer_number=?1",nativeQuery=true)
	void deleteOpportunityPlatformByCustomerNumber(Long customerNumber);
}
