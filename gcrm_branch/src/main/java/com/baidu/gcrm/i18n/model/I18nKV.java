package com.baidu.gcrm.i18n.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.common.LocaleConstants;

@Entity
@Table(name = "g_i18n")
public class I18nKV implements java.io.Serializable {
	private static final long serialVersionUID = 2564544323020058611L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="key_name")
	private String key;
	
	@Column(name="key_value")
	private String value;
	
	@Column(name="key_extra_value")
	private String extraValue;

	@Column
	@Enumerated(EnumType.STRING)
	private LocaleConstants locale = LocaleConstants.en_US;
	
	@Transient
	private String indexStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

    public String getIndexStr() {
        return indexStr;
    }

    public void setIndexStr(String indexStr) {
        this.indexStr = indexStr;
    }

    public String getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(String extraValue) {
        this.extraValue = extraValue;
    }
}
