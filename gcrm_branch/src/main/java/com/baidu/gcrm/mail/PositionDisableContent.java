package com.baidu.gcrm.mail;

import java.util.Date;

import com.baidu.gcrm.common.LocaleConstants;

public class PositionDisableContent extends BaseMailContent{
    
    private Long id;
    
    private String operator; //ad content operator
    
    private LocaleConstants locale = LocaleConstants.en_US;
    
    private String positionName;
    
    private Date adContentSubmitTime;
    
    private String adContentNumber;
    
    private String adContentURL;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LocaleConstants getLocale() {
        return locale;
    }

    public void setLocale(LocaleConstants locale) {
        this.locale = locale;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Date getAdContentSubmitTime() {
        return adContentSubmitTime;
    }

    public void setAdContentSubmitTime(Date adContentSubmitTime) {
        this.adContentSubmitTime = adContentSubmitTime;
    }

    public String getAdContentNumber() {
        return adContentNumber;
    }

    public void setAdContentNumber(String adContentNumber) {
        this.adContentNumber = adContentNumber;
    }

    public String getAdContentURL() {
        return adContentURL;
    }

    public void setAdContentURL(String adContentURL) {
        this.adContentURL = adContentURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
