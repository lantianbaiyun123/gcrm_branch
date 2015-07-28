package com.baidu.gcrm.bpm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.bpm.model.ProcessNameI18n;

public interface IProcessNameI18nRepository extends JpaRepository<ProcessNameI18n, Long> {
	List<ProcessNameI18n> findByLocale(String locale);
	
	ProcessNameI18n findByProcessDefIdAndLocale(String processDefId, String locale);
}
