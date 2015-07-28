package com.baidu.gcrm.valuelistcache.model;

import java.util.Set;

import com.baidu.gcrm.i18n.model.BaseI18nModel;


public class AgentRegional extends BaseI18nModel {

	private Long id;
	private String name;

	//关联通过agent_country关联country表
	private Set<Country> agentCountries;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Country> getAgentCountries() {
		return agentCountries;
	}
	public void setAgentCountries(Set<Country> agentCountries) {
		this.agentCountries = agentCountries;
	}
}
