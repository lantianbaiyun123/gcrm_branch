package com.baidu.gcrm.ad.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.model.AdvertiseQuotation;

public interface AdvertiseQuotationRepository extends JpaRepository<AdvertiseQuotation, Long>{
	
	public AdvertiseQuotation findByAdSolutionContentId(Long id);
	
	List<AdvertiseQuotation> findByAdSolutionContentIdIn(List<Long> ids);
	
	@Modifying
	@Query("delete from AdvertiseQuotation where adSolutionContentId = ?1")
	public void deleteByAdSolutionContentId(Long adSolutionContentId);
	
	@Modifying
	@Query("delete from AdvertiseQuotation where adSolutionContentId in (?1)")
	void deleteByAdSolutionContentIdIn(List<Long> adSolutionContentIds);

	@Query("select q from AdvertiseQuotation q, AdSolutionContent c where q.adSolutionContentId = c.id"
	        + " and q.billingModelId between 4 and 5 and c.approvalStatus not in ('saving','refused','cancel')")
	List<AdvertiseQuotation> findByBillingModelAndContentStatus();
}
