package com.baidu.gcrm.report.config;

public class QueryConfig {

	private String query;
	private boolean nativesql=true;
	private boolean dynamic=false;

	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public boolean isNativesql() {
		return nativesql;
	}

	public void setNativesql(boolean nativesql) {
		this.nativesql = nativesql;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
