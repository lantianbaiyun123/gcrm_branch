package com.baidu.gcrm.report.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author yangjianguo
 *
 */
public class TransformerConfig {

	private Map<String, TemplateConfig> templateMap = new HashMap<String, TemplateConfig>();
	private List<TemplateConfig> templateConfigs = new ArrayList<TemplateConfig>();
    private ReportConfig reportConfig=null;
    private DataSourceConfig dataSourceConfig=null;
	private String dataSourceId;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public void addTemplateConfig(TemplateConfig tc) {
		templateMap.put(tc.getId(), tc);
		templateConfigs.add(tc);
	}

	public List<TemplateConfig> getTemplateConfigs() {
		return templateConfigs;
	}
	public ReportConfig getReportConfig() {
		return reportConfig;
	}
    public TemplateConfig getTemplateConfig(String templateId){
    	return templateMap.get(templateId);
    }
	public DataSourceConfig getDataSourceConfig() {
		return dataSourceConfig;
	}

	public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
		this.dataSourceConfig = dataSourceConfig;
	}

	public void setReportConfig(ReportConfig reportConfig) {
		this.reportConfig = reportConfig;
	}
 
	public List<ColumnConfig> getDisplayColumnConfigs(){
		return this.getDataSourceConfig().getColumnConfigs();
		
	}
}
