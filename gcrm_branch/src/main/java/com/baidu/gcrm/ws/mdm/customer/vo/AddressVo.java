package com.baidu.gcrm.ws.mdm.customer.vo;

public class AddressVo  extends MasterDataBase{

    private Integer addressId;
    private Integer addressType;
    private String addressDetail;
    public Integer getAddressId() {
        return addressId;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public Integer getAddressType() {
        return addressType;
    }
    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }
    public String getAddressDetail() {
        return addressDetail;
    }
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    
}
