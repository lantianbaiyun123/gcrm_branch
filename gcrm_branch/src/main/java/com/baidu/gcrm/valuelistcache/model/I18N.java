package com.baidu.gcrm.valuelistcache.model;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

public class I18N extends BaseI18nModel{
	private Long id;
	private String keyName;
	private String keyValue;
	private String locale;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
}
