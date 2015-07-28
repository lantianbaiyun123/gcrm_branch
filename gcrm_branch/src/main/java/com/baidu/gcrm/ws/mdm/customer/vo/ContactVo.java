package com.baidu.gcrm.ws.mdm.customer.vo;

import java.util.ArrayList;
import java.util.List;

public class ContactVo extends MasterDataBase {
/**
 * 联系人ID
 */
    private Long contactId;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人类型（业务主键）
     */
    private Integer contactType;
    /**
     * 部门
     */
    private String dept;
    /**
     * 职务
     */
    private String post;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 是否决策人
     */
    private Integer isDecisionMaker;
    /**
     * 是否法人
     */
    private Integer isLegal;
    
    List<CommunicateVo> communicateList;
    
    public Long getContactId() {
        return contactId;
    }
    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public Integer getContactType() {
        return contactType;
    }
    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public Integer getGender() {
        return gender;
    }
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public Integer getIsDecisionMaker() {
        return isDecisionMaker;
    }
    public void setIsDecisionMaker(Integer isDecisionMaker) {
        this.isDecisionMaker = isDecisionMaker;
    }
    public Integer getIsLegal() {
        return isLegal;
    }
    public void setIsLegal(Integer isLegal) {
        this.isLegal = isLegal;
    }
    public List<CommunicateVo> getCommunicateList() {
        return communicateList;
    }
    public void setCommunicateList(List<CommunicateVo> communicateList) {
        this.communicateList = communicateList;
    }
    
    public void addCommunicate(CommunicateVo communicateVo){
        if(communicateList ==null){
            communicateList = new ArrayList<CommunicateVo>();
        }
        communicateList.add(communicateVo);
    }
}
