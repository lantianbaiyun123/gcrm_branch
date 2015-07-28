package com.baidu.gcrm.common;

public class AutoSuggestBean {
	private String id;
	private String tag;
	private String name;

	public AutoSuggestBean(String id, String tag, String name) {
	    this.id = id;
	    this.tag = tag;
	    this.name = name;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
