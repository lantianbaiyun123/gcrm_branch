package com.baidu.gcrm.publish.web.vo;

import java.util.Map;

public class PlatformVO {
    private String name;
    private Integer total;
    
    private Map<String,SiteVO> site;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Map<String, SiteVO> getSite() {
		return site;
	}

	public void setSite(Map<String, SiteVO> site) {
		this.site = site;
	}
}
