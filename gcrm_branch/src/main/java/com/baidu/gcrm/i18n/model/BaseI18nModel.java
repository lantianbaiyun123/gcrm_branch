package com.baidu.gcrm.i18n.model;

import javax.persistence.Transient;

public abstract class BaseI18nModel implements IBaseI18nModel{
	
	@Transient
	String i18nName;
	
	@Override
	public String getI18nName() {
		return i18nName;
	}

	@Override
	public void setI18nName(String i18nName) {
		this.i18nName = i18nName;
	}
	
}
