package com.baidu.gcrm.report.config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * @author yangjianguo
 *
 */
public class DataSourceConfig {
	String id;
	String uri;
	QueryConfig queryConfig;
	List<InputBinding> inputBindings = new LinkedList<InputBinding>();
	List<ColumnConfig> columnConfigs = new LinkedList<ColumnConfig>();
	Map<String, ColumnConfig> columnConfigMap = new HashMap<String, ColumnConfig>();

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<ColumnConfig> getColumnConfigs() {
		return columnConfigs;
	}

	public Map<String, ColumnConfig> getColumnConfigMap() {
		return columnConfigMap;
	}

	public QueryConfig getQueryConfig() {
		return queryConfig;
	}

	public void setQueryConfig(QueryConfig queryConfig) {
		this.queryConfig = queryConfig;
	}

	public String getSql() {
		return queryConfig.getQuery();
	}

	public void addQueryConfig(QueryConfig queryConfig) {
		this.queryConfig = queryConfig;
	}

	public List<InputBinding> getInputBindings() {
		return inputBindings;
	}

	public void setInputBindings(List<InputBinding> inputBinding) {
		this.inputBindings = inputBinding;
	}

	public void addInputBinding(InputBinding inputBinding) {
		inputBindings.add(inputBinding);
	}

	public void addColumnConfig(ColumnConfig columnConfig) {
		columnConfigs.add(columnConfig);
		columnConfigMap.put(columnConfig.getField1(), columnConfig);
	}
}