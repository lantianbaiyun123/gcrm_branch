package com.baidu.gcrm.account.rights.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsFunction;

public interface IRightsFunctionRepository extends JpaRepository<RightsFunction, Long> {
	@Query("Select distinct(funcURL) From RightsFunction")
	public List<String> findFuncURLs();
}
