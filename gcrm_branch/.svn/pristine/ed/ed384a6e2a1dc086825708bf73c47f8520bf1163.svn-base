package com.baidu.gcrm.common.page;

import java.util.List;

import javax.persistence.EntityManager;

import com.baidu.gcrm.report.core.Report;

public interface IReportPageQuery<T> extends IHqlPageQuery<T>, IPageQuery {

    void executePageQuery(EntityManager entityManager, Report<T> report);
    void executePageQuery(EntityManager entityManager, Report<T> report,String dataSourceId);
    long executeCountQuery(EntityManager entityManager, Report<T> report);
    long executeCountQuery(EntityManager entityManager, Report<T> report,String dataSourceId);
    List<T> getReportData(EntityManager entityManager, Report<T> report);
    List<T> getReportData(EntityManager entityManager, Report<T> report,String dataSourceId);
    List<T> getReportDataAfterColumnConverted(EntityManager entityManager, Report<T> report);
    List<T> getReportDataAfterColumnConverted(EntityManager entityManager, Report<T> report,String dataSourceId);
    Page<T> getReportPage(EntityManager entityManager, Report<T> report);
    Page<T> getReportPage(EntityManager entityManager, Report<T> report,String dataSourceId);
    Page<T> getReportPageAfterColumnConverted(EntityManager entityManager, Report<T> report);
    Page<T> getReportPageAfterColumnConverted(EntityManager entityManager, Report<T> report,String dataSourceId);
}
