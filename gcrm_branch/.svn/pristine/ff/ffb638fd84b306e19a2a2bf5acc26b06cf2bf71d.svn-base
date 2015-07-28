package com.baidu.gcrm.mail.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.mail.IMailContent;

public class BaseMailContent implements IMailContent {

    /** 收件人 */
    private Set<String> sendTo; 
    /** 抄送 */
    private Set<String> cc;
    
    private LocaleConstants locale;
    
    private String subjectKey;
   
    private String templateKey;
    
    private  Map<String, Object> valueMap;
    
    @Override
    public Set<String> getSendTo() {
        return sendTo;
    }

    @Override
    public LocaleConstants getLocale() {
        return locale;
    }

    @Override
    public String getSubjectKey() {
        return subjectKey;
    }

    @Override
    public String getTemplateKey() {
        return templateKey;
    }

    @Override
    public Set<String> getCc() {
        return cc;
    }

    @Override
    public Map<String, Object> getValueMap() {
        return valueMap;
    }

    public void setSendTo(Set<String> sendTo) {
        this.sendTo = sendTo;
    }

    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    public void setLocale(LocaleConstants locale) {
        this.locale = locale;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public void setTemplateKey(String templateKey) {
        this.templateKey = templateKey;
    }

    public void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }
    
   public void putValue4Template(String key,Object value){
      if(StringUtils.isBlank(key)){
          return;
      }
       if(this.valueMap ==null){
           valueMap = new HashMap<String,Object>();
       }
       
       valueMap.put(key, value);
   }
    
}
