package com.baidu.gcrm.bpm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.bpm.model.ProcessActivityType;

public interface IProcessActivityTypeRepository extends JpaRepository<ProcessActivityType, Long> {
	ProcessActivityType findByProcessDefId(String processDefId);
}
