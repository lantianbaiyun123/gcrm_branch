package com.baidu.gcrm.common.page;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.report.core.Report;
import com.baidu.gcrm.report.core.ReportManager;

@Service
public abstract class ReportBaseQuery<T> implements IReportPageQuery<T> {
	@Autowired
	private IPageQuery pageQuery;
	@Autowired
	private IHqlPageQuery<T> hqlQuery;
	@Override
	public void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, StringBuilder countStr, List<?> paramList,
			PageWrapper<T> page) {

		hqlQuery.executePageQuery(entityManager, sqlStr, countStr, paramList, page);
		
	}
	@Override
	public void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, StringBuilder countStr,
			Map<String, Object> params, PageWrapper<T> page) {
		// TODO Auto-generated method stub
		hqlQuery.executePageQuery(entityManager, sqlStr, countStr, params, page);
		
	}
	@SuppressWarnings("hiding")
	@Override
	public <T> void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, List<?> paramList, PageWrapper<T> page) {
		// TODO Auto-generated method stub
		pageQuery.executePageQuery(entityManager, sqlStr, paramList, page);
		
	}
	@Override
	public long executeCountQuery(EntityManager entityManager, String sqlStr,
			List<?> paramList) {
		// TODO Auto-generated method stub
		return pageQuery.executeCountQuery(entityManager, sqlStr, paramList);
	}
	
	@Override
	public long executeCountQuery(EntityManager entityManager, Report<T> report) {
		// TODO Auto-generated method stub
		return executeCountQuery(entityManager,ReportManager.getReportSql(report),ReportManager.getInputBindingData(report));
	}
	@Override
	public long executeCountQuery(EntityManager entityManager,
			Report<T> report, String dataSourceId) {
		// TODO Auto-generated method stub
		return executeCountQuery(entityManager,ReportManager.getReportSql(report,dataSourceId),ReportManager.getInputBindingData(report,dataSourceId));
	}
	@SuppressWarnings("hiding")
	@Override
	public <T> void executePageQuery(EntityManager entityManager,
			StringBuilder sqlStr, Map<String, Object> params, Page<T> page) {
		// TODO Auto-generated method stub
		pageQuery.executePageQuery(entityManager, sqlStr, params, page);
		
	}
	@Override
	public long executeCountQuery(EntityManager entityManager, String sqlStr,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return pageQuery.executeCountQuery(entityManager, sqlStr, params);
	}
	@Override
	public List<T> getReportData(EntityManager entityManager, Report<T> report) {
		return getReportPage(entityManager, report).getContent();
	}
	@Override
	public List<T> getReportData(EntityManager entityManager, Report<T> report,
			String dataSourceId) {
		
		return getReportPage(entityManager, report, dataSourceId).getContent();
	}
	@Override
	public List<T> getReportDataAfterColumnConverted(
			EntityManager entityManager, Report<T> report) {
		
		return getReportPageAfterColumnConverted(entityManager, report).getContent();
	}
	@Override
	public List<T> getReportDataAfterColumnConverted(
			EntityManager entityManager, Report<T> report, String dataSourceId) {
		return getReportPageAfterColumnConverted(entityManager, report,dataSourceId).getContent();
	}
	@Override
	public Page<T> getReportPage(EntityManager entityManager, Report<T> report) {
		this.executePageQuery(entityManager, report);
		return report.getReportContext().getPageCritera();
	}
	@Override
	public Page<T> getReportPage(EntityManager entityManager, Report<T> report,
			String dataSourceId) {
		this.executePageQuery(entityManager, report, dataSourceId);
		return report.getReportContext().getPageCritera();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> getReportPageAfterColumnConverted(
			EntityManager entityManager, Report<T> report) {
		this.executePageQuery(entityManager, report);
		return (Page<T>) ReportManager.getPageDataAfterColumnConverted(report.getReportContext().getPageCritera(), report);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> getReportPageAfterColumnConverted(
			EntityManager entityManager, Report<T> report, String dataSourceId) {
		this.executePageQuery(entityManager, report,dataSourceId);
		return (Page<T>) ReportManager.getPageDataAfterColumnConverted(report.getReportContext().getPageCritera(), report, dataSourceId);
	}



}
