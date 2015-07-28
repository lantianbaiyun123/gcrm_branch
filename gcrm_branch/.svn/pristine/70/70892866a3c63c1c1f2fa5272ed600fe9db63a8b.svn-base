package com.baidu.gcrm.report.config;

import java.util.Collection;
import java.util.Map;
/**
 * 缓存report配置，也可重新读取
 * @author yangjianguo
 *
 */
public class ReportConfigCache {

	public static void reset() {
		nonPubConfigCache = null;

		init();
	}

	public static ReportConfig getReportConfig(String rptId)

	{
		init();

		return (ReportConfig) nonPubConfigCache.get(rptId);

	}

	public static void main(String[] args) {
		ReportConfig rc = getReportConfig("hao123report");
		System.out.println(rc.getDataSourceConfig("ds1").getSql());
	}

	public static Collection<ReportConfig> getReportConfigs() {
		init();
		return nonPubConfigCache.values();

	}

	private static void init() {
		if (nonPubConfigCache == null) {
			synchronized (lockObject) {
				if (nonPubConfigCache == null) {
					nonPubConfigCache = new ReportConfigReader()
							.loadXMLConfigFromReportList();
				}
			}
		}
	}

	private static Object lockObject = new Object();
	private static Map<String, ReportConfig> nonPubConfigCache = null;
}
