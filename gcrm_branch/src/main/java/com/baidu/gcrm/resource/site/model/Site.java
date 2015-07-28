package com.baidu.gcrm.resource.site.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.baidu.gcrm.i18n.model.BaseI18nModel;

@Entity
@Table(name = "g_site")
public class Site extends BaseI18nModel implements Serializable{
    
    private static final long serialVersionUID = 1080777082917712581L;

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private String code;
    
    /**
     * 相对于北京时区东八区+8的时差，比如日本是+9则time_zone_offset是+1，印尼是+7则time_zone_offset是-1
     */
    @Column(name = "time_zone_offset")
    private String timeZoneOffset;
    
    @Transient
    private String adPlatformName;
    
    @Transient
    private boolean isChecked;
    
    @Transient
    private boolean allowCanceled;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdPlatformName() {
        return adPlatformName;
    }

    public void setAdPlatformName(String adPlatformName) {
        this.adPlatformName = adPlatformName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean isAllowCanceled() {
        return allowCanceled;
    }

    public void setAllowCanceled(boolean allowCanceled) {
        this.allowCanceled = allowCanceled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public String getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(String timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}
    
}
