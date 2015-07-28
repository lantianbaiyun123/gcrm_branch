package com.baidu.gcrm.amp.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.amp.model.Log;

public interface ILogDao extends JpaRepository<Log, Long>{
	
}
