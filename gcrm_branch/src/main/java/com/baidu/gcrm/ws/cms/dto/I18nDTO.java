package com.baidu.gcrm.ws.cms.dto;

import java.io.Serializable;

/**
 * 国际化信息DTO
 * 
 */
public class I18nDTO implements Serializable{

    private static final long serialVersionUID = 40247409746015171L;
    
    private Long id;
    private String key;
    private String value;
    private String locale;
    
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
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    

}
