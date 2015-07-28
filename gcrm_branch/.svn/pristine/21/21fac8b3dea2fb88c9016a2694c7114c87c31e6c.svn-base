package com.baidu.gcrm.amp.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.amp.model.Subscribe;

public interface ISubscribeDao extends JpaRepository<Subscribe, Long>{
	
	@Query(value="update g_subscribe set status = ?1 where id = ?2",nativeQuery=true)
	int updateSubscribeStatus(String status,Long id);
	
}
