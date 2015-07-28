package com.baidu.gcrm.mail;

import java.util.Set;

public class BaseMailContent {
    
    /** 收件人 */
    private Set<String> sendTo; 
    /** 抄送 */
    private Set<String> cc;
    
    public Set<String> getSendTo() {
        return sendTo;
    }
    public void setSendTo(Set<String> sendTo) {
        this.sendTo = sendTo;
    }
    public Set<String> getCc() {
        return cc;
    }
    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

}
