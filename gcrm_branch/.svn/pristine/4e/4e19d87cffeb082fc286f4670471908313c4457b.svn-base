package com.baidu.gcrm.ws.mdm.customer.vo;

import java.util.ArrayList;
import java.util.List;

public class CustomerBaseVo  extends MasterDataBase{
    /**
     * 客户名称
     */
    private String custName;
    /**
     * 客户类型
     */
    private Integer custType;
    /**
     * 客户状态
     */
    private Integer custStatus;
    /**
     * 所属国家
     */
    private Integer country;
    
    
    private CustomerCompanyVo custCompany;
    
    private List<BusinessTypeVo> businessTypeList;
    
    private List<SiteUrlVo> urlList;
    
    private List<ContactVo> contactList;
    
    private List<AddressVo> addressList;
    
    private List<TradeVo> tradeList;
    
    private List<AcctVo> acctList;
    /**
     * 描述
     */
    private String remark;
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public Integer getCustType() {
        return custType;
    }
    public void setCustType(Integer custType) {
        this.custType = custType;
    }
    public Integer getCustStatus() {
        return custStatus;
    }
    public void setCustStatus(Integer custStatus) {
        this.custStatus = custStatus;
    }
    public Integer getCountry() {
        return country;
    }
    public void setCountry(Integer country) {
        this.country = country;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public CustomerCompanyVo getCustCompany() {
        return custCompany;
    }
    public void setCustCompany(CustomerCompanyVo custCompany) {
        this.custCompany = custCompany;
    }
    public List<BusinessTypeVo> getBusinessTypeList() {
        return businessTypeList;
    }
    public void setBusinessTypeList(List<BusinessTypeVo> businessTypeList) {
        this.businessTypeList = businessTypeList;
    }
    public List<SiteUrlVo> getUrlList() {
        return urlList;
    }
    public void setUrlList(List<SiteUrlVo> urlList) {
        this.urlList = urlList;
    }
    public List<ContactVo> getContactList() {
        return contactList;
    }
    public void setContactList(List<ContactVo> contactList) {
        this.contactList = contactList;
    }
    public List<AddressVo> getAddressList() {
        return addressList;
    }
    public void setAddressList(List<AddressVo> addressList) {
        this.addressList = addressList;
    }
    public List<TradeVo> getTradeList() {
        return tradeList;
    }
    public void setTradeList(List<TradeVo> tradeList) {
        this.tradeList = tradeList;
    }
    public List<AcctVo> getAcctList() {
        return acctList;
    }
    public void setAcctList(List<AcctVo> acctList) {
        this.acctList = acctList;
    }
    
   public void addContact(ContactVo contactVo){
       if(this.contactList ==null){
           contactList = new ArrayList<ContactVo>();
       }
       contactList.add(contactVo);
   }
    
}
