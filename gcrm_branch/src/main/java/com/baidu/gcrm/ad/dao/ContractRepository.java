package com.baidu.gcrm.ad.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.ad.model.Contract;

public interface ContractRepository extends JpaRepository<Contract, String>{
	
	List<Contract> findByNumberLikeAndCustomerIdAndState(String number, Long customerId,Contract.ContractState state, Pageable page);
	
	@Query("select c from Contract c,AdvertiseSolution a where c.number=a.contractNumber and a.id=?1")
	Contract findByAdSolutionId(Long adSolutionId);
	
	List<Contract> findByCustomerId(Long customerId);
}