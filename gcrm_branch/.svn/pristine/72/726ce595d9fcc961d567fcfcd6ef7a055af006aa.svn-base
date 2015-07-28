package com.baidu.gcrm.personalpage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.personalpage.model.OperateReport;

public interface IOperateReportRepository extends
		JpaRepository<OperateReport, Long> {
    
	public OperateReport findByOperateTypeAndReportType(String operateType,String reportType);
	@Query("select a from OperateReport a order by a.operateType,a.orderNumber")
	public List<OperateReport> findAll();
}
