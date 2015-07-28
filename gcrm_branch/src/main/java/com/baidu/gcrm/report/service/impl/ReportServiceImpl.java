package com.baidu.gcrm.report.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.page.IReportPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.report.core.Report;
import com.baidu.gcrm.report.service.IReportService;

/**
 * 
 * @author yangjianguo
 * 
 */
@Service
public class ReportServiceImpl<T> implements IReportService<T> {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private IReportPageQuery<T> reportQuery;

    @Override
    public Page<T> findReportPages(Report<T> report) {
        return reportQuery.getReportPageAfterColumnConverted(entityManager, report);
    }

    public Page<T> findReportPages(Report<T> report, String dataSourceId) {
        return reportQuery.getReportPageAfterColumnConverted(entityManager, report, dataSourceId);
    }

    @Override
    public long getReportSize(Report<T> report) {
        return reportQuery.executeCountQuery(entityManager, report);
    }

    @Override
    public long getReportSize(Report<T> report, String dataSourceId) {
        return reportQuery.executeCountQuery(entityManager, report, dataSourceId);
    }

}
