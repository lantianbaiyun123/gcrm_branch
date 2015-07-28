package com.baidu.gcrm.resource.site.web.vo;

import com.baidu.gcrm.common.LocaleConstants;

public class I18nVO  {
	
    private Long i18nId;
    
	private Long id;

	private String value;
	
	private LocaleConstants locale;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    public LocaleConstants getLocale() {
        return locale;
    }

    public void setLocale(LocaleConstants locale) {
        this.locale = locale;
    }

    public Long getI18nId() {
        return i18nId;
    }

    public void setI18nId(Long i18nId) {
        this.i18nId = i18nId;
    }

}
