package com.baidu.gcrm.common.page;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.report.config.QueryConfig;
import com.baidu.gcrm.report.core.Report;
import com.baidu.gcrm.report.core.ReportManager;

@Service
public class ReportPageQuery<T> extends ReportBaseQuery<T> {
	@Override
	public  void executePageQuery(EntityManager entityManager, Report<T> report) {
		this.executePageQuery(entityManager, report, report.getReportConfig().getDefaultDataSourceConfig().getId());
	}

	@Override
	public void executePageQuery(EntityManager entityManager, Report<T> report,
			String dataSourceId) {
		QueryConfig dc=report.getReportConfig().getDataSourceConfig(dataSourceId).getQueryConfig();
		String sql=ReportManager.getReportSql(report, dataSourceId);
		Page<T> page=report.getReportContext().getPageCritera();
		if(dc.isNativesql()){
			this.executePageQuery(entityManager, new StringBuilder(sql), ReportManager.getInputBindingData(report, dataSourceId),page);
		}else{
			this.executePageQuery(entityManager, new StringBuilder(sql), new StringBuilder(getCountSql(sql)), ReportManager.getInputBindingData(report, dataSourceId),page);
		}
		page.setTotalCount(page.getTotal());
	}
	
	 private static String getCountSql(String realSql) {
	        StringBuilder countSql = new StringBuilder();
	        String lowerCaseSql = realSql.toLowerCase();
	        countSql.append("select count(*) ");
	        String otherSectionSql = "";
	        if (realSql.indexOf("order by") != -1) {
	            otherSectionSql = realSql.substring(lowerCaseSql.indexOf("from"), lowerCaseSql.indexOf("order by"));
	        } else {
	            otherSectionSql = realSql.substring(lowerCaseSql.indexOf("from"));
	        }

	        countSql.append(otherSectionSql);
	        return StringUtils.trim(countSql.toString());
	    }

}
