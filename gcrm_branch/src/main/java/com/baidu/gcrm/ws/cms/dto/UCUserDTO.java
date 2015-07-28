package com.baidu.gcrm.ws.cms.dto;

import java.io.Serializable;

/**
 * UC用户信息DTO
 * 
 */
public class UCUserDTO implements Serializable{

    private static final long serialVersionUID = -6945986255008565047L;
    
    private Long ucId;//ucid
    private String userName;//用户名
    private String realName;//真实姓名
    private String email;//email
    private String phoneNumber;//电话号码
    private String status;//状态，0禁用，1启用
    
    public Long getUcId() {
        return ucId;
    }
    public void setUcId(Long ucId) {
        this.ucId = ucId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
}
