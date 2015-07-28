package com.baidu.gcrm.report.core;

import com.baidu.gcrm.report.config.ReportConfig;
import com.baidu.gcrm.report.config.ReportConfigCache;


public class Report<T> {
	private ReportContext<T> reportContext;
	private ReportConfig reportConfig;
    private String reportId;
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public ReportContext<T> getReportContext() {
		return reportContext;
	}
   
	public void setReportContext(ReportContext<T> reportContext) {
		this.reportContext = reportContext;
	}

	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}

	public Report(ReportContext<T> reportContext, String reportId) {
		super();
		this.reportContext = reportContext;
		this.reportId = reportId;
		this.reportConfig=ReportConfigCache.getReportConfig(reportId);
	}


}
