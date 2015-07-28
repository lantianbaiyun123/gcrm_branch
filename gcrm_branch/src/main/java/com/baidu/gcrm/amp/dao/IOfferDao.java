package com.baidu.gcrm.amp.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.amp.model.Offer;

public interface IOfferDao extends JpaRepository<Offer, Long>,JpaSpecificationExecutor<Offer>{
	
	@Query("update Offer  set status = ?1 where id = ?2")
	int updateOfferStatus(String status,Long id);
	
	@Query("update Offer  set offerId = ?1 where id = ?2")
	int updateOfferBusinessId(String businessId,Long id);
	
}
