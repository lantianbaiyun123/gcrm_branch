package com.baidu.gcrm.quote.model;

public enum BusinessType {
	/** 变现 */
	CASH,
	/** 销售 */
	SALE;
	
	public static BusinessType valueOf(Integer value){
		if(value == null){
			return null;
		}
		
		BusinessType[] values = BusinessType.values();
		for(BusinessType val : values ){
			if(val.ordinal() == value){
				return val;
			}
		}
		
		return null;
	}
}
