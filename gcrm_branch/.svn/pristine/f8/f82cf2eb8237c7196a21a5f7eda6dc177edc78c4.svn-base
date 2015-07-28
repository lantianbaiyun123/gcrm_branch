package com.baidu.rigel.crm.rights.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable{
	private static final long serialVersionUID = -8031516283201653039L;
	private Long id;
	private String name;
	private boolean isEnable;
	private String url;
	private String authTag;
	private Integer blankFlag;
	private String appName;

	private List<Menu> children = new ArrayList<Menu>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String text) {
		this.name = text;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String uri) {
		this.url = uri;
	}

	public void addChild(Menu child) {
		this.children.add(child);
	}

	public List<Menu> getChildren() {
		return this.children;
	}

	public String getAuthTag() {
		return authTag;
	}

	public void setAuthTag(String authTag) {
		this.authTag = authTag;
	}

	public Integer getBlankFlag() {
		return blankFlag;
	}

	public void setBlankFlag(Integer blankFlag) {
		this.blankFlag = blankFlag;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
