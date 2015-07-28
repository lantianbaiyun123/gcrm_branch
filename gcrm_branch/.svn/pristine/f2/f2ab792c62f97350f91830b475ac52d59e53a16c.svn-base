package com.baidu.gcrm.customer.web.helper;

public enum CustomerType {
	offline,
	direct,
	nondirect,
	union;
	
	public int getValue() {
        return this.ordinal();
    }
	
	public static CustomerType valueOf(Integer value){
		if(value == null){
			return null;
		}
		CustomerType[] values = CustomerType.values(); 
		for(CustomerType customerType : values){
			if(customerType.ordinal() == value){
				return customerType;
			}
		}
		return null;
	}
}
