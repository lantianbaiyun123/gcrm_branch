package com.baidu.gcrm.report.service;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.report.core.Report;

public interface IReportService<T> {
	public Page<T> findReportPages(Report<T> report);
	public Page<T> findReportPages(Report<T> report,String dataSourceId);
	public long getReportSize(Report<T> report);
	public long getReportSize(Report<T> report,String dataSourceId);
}
