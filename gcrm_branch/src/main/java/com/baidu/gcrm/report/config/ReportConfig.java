package com.baidu.gcrm.report.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 每个<report/> 对应一个此实例
 * @author yangjianguo
 *
 */
public class ReportConfig {
	String id;
	String name;

	Map<String, DataSourceConfig> datasourceConfigs = new HashMap<String, DataSourceConfig>();
	List<TransformerConfig> transformerConfigs = new ArrayList<TransformerConfig>();

	public DataSourceConfig getDataSourceConfig(String id) {

		return (DataSourceConfig) datasourceConfigs.get(id);

	}

	public DataSourceConfig getDefaultDataSourceConfig() {

		return (DataSourceConfig) getDataSourceConfigs().get(0);

	}

	public List<DataSourceConfig> getDataSourceConfigs() {

		return new ArrayList<DataSourceConfig>(datasourceConfigs.values());

	}

	public TransformerConfig getDefaultTransformerConfig(){
		
		return transformerConfigs.get(0);
	}
	
	public TransformerConfig getTransformerConfig(String transformerId){
		for(TransformerConfig config:transformerConfigs){
			if(transformerId.equals(config.getId())){
				return config;
			}
		}
		return null;
	}
	public void addDataSourceConfig(DataSourceConfig conf) {
		datasourceConfigs.put(conf.getId(), conf);
	}

	public void addTransformerConfig(TransformerConfig conf) {

		transformerConfigs.add(conf);
		String dataSourceId=conf.getDataSourceId();
		DataSourceConfig dsc=getDataSourceConfig(dataSourceId);
		conf.setDataSourceConfig(dsc);
		conf.setReportConfig(this);
	}

	public List<TransformerConfig> getTransformerConfigs() {
		return transformerConfigs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
